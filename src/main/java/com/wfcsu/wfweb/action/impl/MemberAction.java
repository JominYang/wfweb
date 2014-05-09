package com.wfcsu.wfweb.action.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wfcsu.wfweb.action.ActionForm;
import com.wfcsu.wfweb.action.ActionForword;
import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.action.DispatcherAction;
import com.wfcsu.wfweb.service.ILoginService;
import com.wfcsu.wfweb.service.IMemberService;
import com.wfcsu.wfweb.service.impl.LoginService;
import com.wfcsu.wfweb.service.impl.MemberService;
import com.wfcsu.wfweb.vo.JsonVo;
import com.wfcsu.wfweb.vo.MemberVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class MemberAction extends DispatcherAction {
	public IMemberService service = null;
	public MemberAction() {
		service = new MemberService();
	}
	//得到单个队员的信息
	public ActionForword getSinger(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String member_no = request.getParameter("member_no");
		MemberVo vo = new MemberVo();
		
		vo = service.get(member_no);
		JSONObject jsonObj = new JSONObject().fromObject(vo);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		
		return null;
	}
	
	//得到所有在对队员信息
		public ActionForword getMembersName(ActionForm form, ActionMapping mapping,
				HttpServletRequest request, HttpServletResponse response) { 
			HttpSession session = request.getSession();
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			MemberVo vo = new MemberVo();
			vo.setMember_state("在队");
			List list = service.get(0, 0, null, null, vo);
			
			JSONArray jsonArray = new JSONArray().fromObject(list);
			String jsonS = jsonArray.toString();
			out.write(jsonS);
			
			return null;
		}
		
	//编辑单个队员的信息
	public ActionForword edit(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MemberVo vo = (MemberVo)form;
		JsonVo j = new JsonVo();
		if(service.edit(vo)) {
			j.setSuccess(true);
			j.setMsg("修改[" + vo.getMember_name() + "]成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("修改[" + vo.getMember_name() + "]失败！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//更新个人信息
	public ActionForword updateInfo(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MemberVo vo = (MemberVo)form;
		JsonVo j = new JsonVo();
		if(service.updateInfo(vo)) {
			j.setSuccess(true);
			j.setMsg("个人信息修改成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("修改失败，请重试！");
		}
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
	//更改密码
	public ActionForword changePwd(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) { 
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean flag = false;
		JsonVo j = new JsonVo();
		j.setSuccess(true);
		String no = request.getParameter("member_no");
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		
		ILoginService lser = new LoginService();
		if (lser.login(no, oldpwd) != null) {
			if(service.changePwd(no,newpwd)){
				j.setMsg("密码修改成功，请重新登录试试！");
				flag = true;
			}else{
				j.setMsg("密码修改失败，请重试！");
			}
		} else {
			j.setMsg("老密码输入错误，请重试！");
		}
		
		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		if(flag) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.getForword("logout");
		}else {
			return null;
		}
	}
}
