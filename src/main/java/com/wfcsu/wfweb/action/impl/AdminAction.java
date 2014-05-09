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
import com.wfcsu.wfweb.service.impl.AdminService;
import com.wfcsu.wfweb.vo.JsonVo;
import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class AdminAction extends DispatcherAction {

	private IAdminService service;

	public AdminAction() {
		service = new AdminService();
	}
	public ActionForword startM(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter o = null;
		try {
			o = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String start_month = request.getParameter("start_month");
		
		JsonVo j = new JsonVo();
		
		if(service.updateStartOfMonth(start_month)) {
			j.setSuccess(true);
			j.setMsg("更新成功！");
		}else {
			j.setSuccess(true);
			j.setMsg("更新失败！请重试");
		}
		
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		o.write(jsonS);
		return null;
	}
	public ActionForword dutyTime(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter o = null;
		try {
			o = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String d1 = request.getParameter("duty_time1");
		String d2 = request.getParameter("duty_time2");
		String d3 = request.getParameter("duty_time3");
		String d4 = request.getParameter("duty_time4");
		String d5 = request.getParameter("duty_time5");
		String dh1 = request.getParameter("duty_time_hours1");
		String dh2 = request.getParameter("duty_time_hours2");
		String dh3 = request.getParameter("duty_time_hours3");
		String dh4 = request.getParameter("duty_time_hours4");
		
		JsonVo j = new JsonVo();
		
		if(service.dutyTime(d1,d2,d3,d4,d5,dh1,dh2,dh3,dh4)) {
			j.setSuccess(true);
			j.setMsg("更新成功！");
		}else {
			j.setSuccess(true);
			j.setMsg("更新失败！请重试");
		}
		
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		o.write(jsonS);
		return null;
	}
	//统计当月加班和出勤总和
	public ActionForword extrawork(ActionForm form, ActionMapping mapping,
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
			ArrayList listN = service.extrawork(vo,yearmonth);
			
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
	//统计值班
	public ActionForword duty(ActionForm form, ActionMapping mapping,
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
			ArrayList listN = service.duty(vo,yearmonth);
			
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
	public ActionForword addMember(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MemberVo vo = (MemberVo) form;
		JsonVo jVo = new JsonVo();

		if (service.addMember(vo) > 0) {
			jVo.setSuccess(true);
			jVo.setMsg("添加队员[" + vo.getMember_name() + "]成功!");

			JSONObject jsonObj = new JSONObject().fromObject(jVo);
			String jsonS = jsonObj.toString();
			out.write(jsonS);
		} else {
			jVo.setSuccess(false);
			jVo.setMsg("系统错误，添加队员[" + vo.getMember_name() + "]失败，请稍后重试!");

			JSONObject jsonObj = new JSONObject().fromObject(jVo);
			String jsonS = jsonObj.toString();
			out.write(jsonS);
		}
		return null;
	}

	// 查询队员表
	public ActionForword getMember(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String member_no = request.getParameter("member_no");
		String member_name = request.getParameter("member_name");
		String memberState = request.getParameter("memberState");
		String member_state = "";
		if (!"".equals(memberState) && memberState != null) {
			try {
				member_state = new String(memberState.getBytes("iso-8859-1"),
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
		MemberVo vo = new MemberVo();
		vo.setMember_state(member_state);
		vo.setMember_name(member_name);
		vo.setMember_no(member_no);

		int total = service.countMember(vo);
		List list = service.getMember(page, rows, order, sort, vo);
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);

		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);

		return null;
	}
	
	//得到所有在对队员名字
	public ActionForword getMemberName(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		System.out.println("getMemberName......");
		HttpSession session = request.getSession();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MemberVo vo = new MemberVo();
		vo.setMember_state("在队");
		List list = service.getMember(0, 0, null, null, vo);
		session.setAttribute("memberList3D", list);//存入session中
		JSONArray jsonArray = new JSONArray().fromObject(list);
		String jsonS = jsonArray.toString();
		out.write(jsonS);
		
		//return mapping.getForword("member");
		return null;
	}
	//删除队员
	public ActionForword delMember(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonVo jVo = new JsonVo();
		String nos = request.getParameter("nos");
		System.out.println(nos+"nnnos");

		service.delMember(nos);
		jVo.setSuccess(true);

		JSONObject jsonObj = new JSONObject().fromObject(jVo);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		
		return null;
	}
}
