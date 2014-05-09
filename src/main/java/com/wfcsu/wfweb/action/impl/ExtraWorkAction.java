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

import com.wfcsu.wfweb.action.ActionForm;
import com.wfcsu.wfweb.action.ActionForword;
import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.action.DispatcherAction;
import com.wfcsu.wfweb.service.IAdminService;
import com.wfcsu.wfweb.service.IExtraWorkService;
import com.wfcsu.wfweb.service.impl.AdminService;
import com.wfcsu.wfweb.service.impl.ExtraWorkService;
import com.wfcsu.wfweb.vo.ExtraWorkVo;
import com.wfcsu.wfweb.vo.JsonVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;

import net.sf.json.JSONObject;


public class ExtraWorkAction extends DispatcherAction{
	
	private IExtraWorkService service;

	public ExtraWorkAction() {
		service = new ExtraWorkService();
	}

	// 添加加班
	public ActionForword add(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = 0;
		ExtraWorkVo vo = (ExtraWorkVo)form;
		String[] extra_nos = request.getParameterValues("extra_members");//得到所有加班人
		vo.setQuery_id(request.getParameter("uid"));//将生成的ID放入VO
		
		JsonVo jVo = new JsonVo();
		
		count = service.add(vo,extra_nos);
		if(count > 0){
			jVo.setSuccess(true);
			jVo.setMsg("添加加班成功!");
			JSONObject jsonObj = new JSONObject().fromObject(jVo);
			String jsonS = jsonObj.toString();
			out.write(jsonS);
		}
		else{
			jVo.setSuccess(false);
			jVo.setMsg("系统错误，添加加班失败，请稍后重试!");

			JSONObject jsonObj = new JSONObject().fromObject(jVo);
			String jsonS = jsonObj.toString();
			out.write(jsonS);
			}
		return null;
	  }
	
	//查询加班表
	public ActionForword get(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String no = request.getParameter("no");//获取no值得到是从哪个页面进入的
		
		String state = request.getParameter("state");
		String extra_no = request.getParameter("extra_no");
		String extra_add_no = request.getParameter("extra_add_no");
		
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

		int total = service.count(startDate, endDate, state, no, extra_no, extra_add_no);
		List list = service.get(page, rows, order, sort,startDate,endDate,state,no,extra_no,extra_add_no);
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);

		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);

		return null;
	}
	
	//删除加班
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
	
	public ActionForword getOne(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String query_id = request.getParameter("query_id");
		ExtraWorkVo vo = new ExtraWorkVo();
		
		vo = service.getOne(query_id);
		JSONObject jsonObj = new JSONObject().fromObject(vo);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		
		return null;
	}
	
	//修改数据
	public ActionForword edit(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ExtraWorkVo vo = (ExtraWorkVo)form;
		String[] extra_nos = request.getParameterValues("extra_nos");
		vo.setExtra_nos(extra_nos);
		vo.setQuery_id(request.getParameter("query_id"));
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
	
	
	
	public ActionForword check(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String query_id = request.getParameter("query_id");
		String pass = request.getParameter("pass");//是通过还是不通过
	    boolean flag = service.check(query_id,pass);
	    
	    
	    
		JsonVo j = new JsonVo();
		if(flag) {
			j.setSuccess(true);
			j.setMsg("审核成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("审核失败！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	
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
	public ActionForword my(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String member_no = request.getParameter("member_no");
		MemberSo so = service.my(member_no);
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
	
	public ActionForword permonth(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String member_no = request.getParameter("member_no");
		List list = service.perMonth(member_no);
		Map map = new HashMap();
		map.put("total", "1");
		map.put("rows", list);
		JSONObject jsonObj = new JSONObject().fromObject(map);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	
	public ActionForword statistic(ActionForm form, ActionMapping mapping,
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
			ArrayList listN = service.statistic(memberVo.getMember_no(),yearmonth);
			
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
