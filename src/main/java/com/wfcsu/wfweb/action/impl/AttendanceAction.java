package com.wfcsu.wfweb.action.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wfcsu.wfweb.action.ActionForm;
import com.wfcsu.wfweb.action.ActionForword;
import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.action.DispatcherAction;
import com.wfcsu.wfweb.service.IAdminService;
import com.wfcsu.wfweb.service.IAttendanceService;
import com.wfcsu.wfweb.service.impl.AdminService;
import com.wfcsu.wfweb.service.impl.AttendanceService;
import com.wfcsu.wfweb.vo.AttendanceVo;
import com.wfcsu.wfweb.vo.JsonVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;

import net.sf.json.JSONObject;


public class AttendanceAction extends DispatcherAction{

	private IAttendanceService service;

	public AttendanceAction() {
		service = new AttendanceService();
	}

	// 添加出勤单
	public ActionForword add(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AttendanceVo vo = (AttendanceVo)form;
		String center = request.getParameter("toCenter");
		if("更多备注".equals(vo.getAtten_remark())){
			vo.setAtten_remark("");
		}
		if("拿到中心".equals(center)){
			vo.setAtten_remark("拿到中心。"+vo.getAtten_remark());
		}
		
		JsonVo jVo = new JsonVo();
		if(service.add(vo) > 0){
			jVo.setSuccess(true);
			jVo.setMsg("添加出勤单成功!");
			JSONObject jsonObj = new JSONObject().fromObject(jVo);
			String jsonS = jsonObj.toString();
			out.write(jsonS);
		}
		else{
			jVo.setSuccess(false);
			jVo.setMsg("系统错误，添加出勤单失败，请稍后重试!");
			JSONObject jsonObj = new JSONObject().fromObject(jVo);
			String jsonS = jsonObj.toString();
			out.write(jsonS);		
			}
		return null;
		
	  }
	
	
	// 查询出勤单
	public ActionForword get(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String no = request.getParameter("no");
		String client_name = request.getParameter("client_name");
		String get_member = request.getParameter("get_member");
		String atten_member = request.getParameter("atten_member");
		String register_time = request.getParameter("register_time");
	    String attendanceState = request.getParameter("attendanceState");
	    String attendance_state = "";
		if (!"".equals(attendanceState) && attendanceState != null) {
			try {
				attendance_state = new String(attendanceState.getBytes("iso-8859-1"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (!"".equals(get_member) && get_member != null) {
			try {
				get_member = new String(get_member.getBytes("iso-8859-1"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String sPage = request.getParameter("page");
		String sRows = request.getParameter("rows");
		String order = request.getParameter("order");
		String sort = request.getParameter("sort");
		int page = 0;
		int rows = 0;
		if (sPage != null && sRows != null) {
			page = Integer.parseInt(sPage);
			rows = Integer.parseInt(sRows);
		}
        AttendanceVo vo = new AttendanceVo();
        vo.setAtten_state(attendance_state);
        vo.setAtten_member1(atten_member);
        vo.setRegister_time(register_time);
        vo.setGet_member(get_member);
        vo.setClient_name(client_name);
        
        
        
    	int total = service.count(vo,no);
		ArrayList list = service.get(page, rows, order, sort, vo,no);
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);

		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);

		return null;
	}
	
	//删除出勤单
	public ActionForword del(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonVo jVo = new JsonVo();
		String nos = request.getParameter("nos");

		service.del(nos);
		jVo.setSuccess(true);

		JSONObject jsonObj = new JSONObject().fromObject(jVo);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	
	
	//修改出勤单
	public ActionForword edit(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AttendanceVo vo = (AttendanceVo)form;
		String center = request.getParameter("toCenter");
		if("更多备注".equals(vo.getAtten_remark())){
			vo.setAtten_remark("");
		}
		if("拿到中心".equals(center)){
			vo.setAtten_remark("拿到中心。"+vo.getAtten_remark());
		}
		
		JsonVo j = new JsonVo();
		if(service.edit(vo)) {
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("修改失败！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//拿单出勤
	public ActionForword goToDeal(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		System.out.println("gotodealaction");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AttendanceVo vo = (AttendanceVo)form;
		String get_member = null;
		if("net".equals(request.getParameter("net"))) {
			get_member = request.getParameter("get_member");
			if (!"".equals(get_member) && get_member != null) {
				try {
					get_member = new String(get_member.getBytes("iso-8859-1"),
							"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			vo.setGet_member(get_member);
		}
		
		JsonVo j = new JsonVo();
		if(service.goToDeal(vo)) {
			j.setSuccess(true);
			j.setMsg("拿单成功！请尽快处理！");
		} else {
			j.setSuccess(false);
			j.setMsg("拿单失败！请稍后重试！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//还单
	public ActionForword back(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AttendanceVo vo = (AttendanceVo)form;
		vo.setAttendance_no(request.getParameter("attendance_no"));
		
		JsonVo j = new JsonVo();
		if(service.back(vo)) {
			j.setSuccess(true);
			j.setMsg("还单成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("还单失败！请稍后重试！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//作废
	public ActionForword voidIt(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String attendance_no = request.getParameter("attendance_voidno");
		
		JsonVo j = new JsonVo();
		if(service.voidIt(attendance_no)) {
			j.setSuccess(true);
			j.setMsg("此单作废成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("此单作废失败！请稍后重试！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//作废还原
	public ActionForword backToNotDeal(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String attendance_no = request.getParameter("attendance_no");
		
		JsonVo j = new JsonVo();
		if(service.backToNotDeal(attendance_no)) {
			j.setSuccess(true);
			j.setMsg("此单还原成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("此单还原失败！请稍后重试！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	
	//客户网上报单
	public ActionForword addByClient(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		AttendanceVo vo = (AttendanceVo)form;
		if("请选择".equals(vo.getClient_address_a())||"请选择".equals(vo.getClient_address_b())||"".equals(vo.getClient_name())||"".equals(vo.getClient_phone())||"".equals(vo.getProblem_describe())) {
			request.setAttribute("error", "请填写完整信息！<br/>以便我们能准确及时的处理您的问题！");
			
		}else {
			if(service.addByClient(vo) > 0){
				request.setAttribute("msg", "恭喜您！报单成功！请耐心等待！");
			}
			else{
				request.setAttribute("error", "非常遗憾！系统错误！请重试或联系我们！");
			}
		}
		return mapping.getForword("online");
		
	  }
	//修改出勤单--得到数据
	public ActionForword getSinger(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String attendance_no = request.getParameter("attendance_no");
		AttendanceVo vo = new AttendanceVo();
		
		vo = service.get(attendance_no);
		
		JSONObject jsonObj = new JSONObject().fromObject(vo);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		
		return null;
	}
	
	
	public ActionForword editClient(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AttendanceVo vo = (AttendanceVo)form;
		vo.setAttendance_no(request.getParameter("attendance_no"));
		String register = vo.getRegister();
		if(register != null && !("".equals(register))){
			String dater = register.substring(0,11);
			String timer = register.substring(11,register.length());
			vo.setRegister_time(timer);
		}
		JsonVo j = new JsonVo();
		if(service.editClient(vo)) {
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("修改失败！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//我的出勤单
	public ActionForword my(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String member_no = request.getParameter("member_no");
		String member_name = request.getParameter("member_name");
		if (!"".equals(member_name) && member_name != null) {
			try {
				member_name = new String(member_name.getBytes("iso-8859-1"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		MemberSo so = service.my(member_no,member_name);
		Map map = new HashMap();
		List list = new ArrayList();
		list.add(so);
		map.put("total", "1");
		map.put("rows", list);
		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//每月排名
	public ActionForword rank(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		ArrayList list = null;
		Map map = new HashMap();
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		list = service.rank();
		map.put("total", "1");
		map.put("rows", list);
		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//每月统计
	public ActionForword permonth(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		StatisticAttenVo vo = new StatisticAttenVo();
		vo.setMember(member_no);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList list = null;
		Map map = new HashMap();
		list = service.statisticByMonth(vo);
		map.put("total", "1");
		map.put("rows", list);
		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//登记网上报单，转为正式出勤单
	public ActionForword changeType(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AttendanceVo vo = (AttendanceVo)form;
		HttpSession session = request.getSession();
		String dutyer = (String)session.getAttribute("loginName");
		vo.setDuty_member(dutyer);
	
		JsonVo j = new JsonVo();
		j.setSuccess(true);
		if(service.changeType(vo)) {
			j.setMsg("登记为正式出勤单成功！");
		} else {
			j.setMsg("登记失败,请稍后再试！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}

	//统计出勤单
	public ActionForword statisticsAtten(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter o = null;
		try {
			o = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String yearmonth = request.getParameter("yearmonth");
		if("".equals(yearmonth)||yearmonth==null) {
			yearmonth = 0+"";
		}
		
		StatisticAttenVo vo = new StatisticAttenVo();
		StatisticAttenVo voN = new StatisticAttenVo();
		IAdminService adminService = new AdminService();
		ArrayList memberList = adminService.getMemberStatistic();
		Map memberMap = new HashMap();
		Map out = new HashMap();
		for(int i=0;i<memberList.size();i++) {
			memberMap.put("l"+(i+1), ((MemberVo)memberList.get(i)).getMember_name());
		}
		memberMap.put("date", " ");
		ArrayList listAll = new ArrayList();
		listAll.add(memberMap);
		
		MemberVo memberVo;
		
		Map mapAll = new HashMap();
		for(int j=0;j<memberList.size();j++) {
			memberVo = (MemberVo)memberList.get(j);
			vo.setMember(memberVo.getMember_no());
			ArrayList listN = service.statisticAtten(vo,yearmonth);
			
			for(int k=0;k<listN.size();k++) {
				voN = (StatisticAttenVo)listN.get(k);
				boolean containsKey = mapAll.containsKey(voN.getDate());
				if(containsKey) {
					((HashMap)mapAll.get(voN.getDate())).put("l"+(j+1), voN.getCount());
				}else {
					Map mapOne = new HashMap();
					mapOne.put("date", voN.getDate());
					mapOne.put("l"+(j+1), voN.getCount());
					mapAll.put(voN.getDate(), mapOne);
				}
			}
		}
		//hashmap sort
		List<Map.Entry<String, Integer>> sort =new ArrayList<Map.Entry<String, Integer>>(mapAll.entrySet());
		Collections.sort(sort, new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		});
		for (int l=0; l<sort.size(); l++) {
		    Entry<String, Integer> entry = sort.get(l);
		    listAll.add(entry.getValue());
		}

		out.put("total", "1");
		out.put("rows", listAll);
		JSONObject jsonObj = new JSONObject().fromObject(out);
		String jsonS = jsonObj.toString();
		o.write(jsonS);
		return null;
	}
}
