package com.wfcsu.wfweb.action.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wfcsu.wfweb.action.ActionForm;
import com.wfcsu.wfweb.action.ActionForword;
import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.action.DispatcherAction;
import com.wfcsu.wfweb.service.ILoginService;
import com.wfcsu.wfweb.service.impl.LoginService;
import com.wfcsu.wfweb.vo.JsonVo;
import com.wfcsu.wfweb.vo.MemberVo;

import net.sf.json.JSONObject;

public class LoginAction extends DispatcherAction {

	private ILoginService service;
	private String msg;

	public LoginAction() {
		service = new LoginService();
	}
	//队员登录
	public ActionForword login(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		String loginNo = request.getParameter("no");
		String psw = request.getParameter("psw");
		HttpSession session = request.getSession();
		MemberVo vo = service.login(loginNo, psw);

		if (vo != null) {
			msg = "登录成功!";
			request.setAttribute("msg", msg);
			session.setAttribute("loginVo", vo);
			session.setAttribute("loginNo", vo.getMember_no());
			session.setAttribute("loginName", vo.getMember_name());
			if (vo.getMember_name().equals(service.getCurrentDutyer())) {
				System.out.println("It's my duty!");
				session.setAttribute("dutyer", vo.getMember_name());
			}
			if ("0".equals(vo.getMember_permission())) {
				return mapping.getForword("admin");
			} else if ("1".equals(vo.getMember_permission())) {
				return mapping.getForword("member");
			} else {
				msg = "登录失败!请重试!";
				request.setAttribute("msg", msg);
				return mapping.getForword("error");
			}
		} else {
			msg = "登录失败!请重试!";
			request.setAttribute("msg", msg);
			return mapping.getForword("error");
		}
	}

	//队员注销
	public ActionForword logOut(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return mapping.getForword("logout");

	}

	// 值班签到
	public ActionForword signIn(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String member_no = request.getParameter("member_no");
		String number = request.getParameter("number");
		JsonVo j = new JsonVo();
		Boolean flag = false;
		j.setSuccess(true);
		if(!"1".equals(number)&&!"2".equals(number)&&!"3".equals(number)&&!"4".equals(number)) {
			j.setMsg("请选择值班！");
		} else {
			String signIn = service.signIn(member_no, number);
			String currentDutyer = service.getCurrentDutyer();

			if ("success".equals(signIn)) {
				j.setMsg("值班签到成功，请刷新页面！");
				flag = true;
			} else if ("error".equals(signIn)) {
				j.setMsg("值班签到失败，请稍后重试！");
			}  else if ("notTime".equals(signIn)) {
				j.setMsg("未到签到时间,[" + currentDutyer + "]正在值班！");
			} else if ("notDuryTime".equals(signIn)) {
				j.setMsg("不是网服值班时间！");
			} else {
				j.setMsg("值班签到失败,[" + signIn + "]已经签到！");
			}
		}

		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		
		if(flag) {
			String memeber_name = (String) session.getAttribute("loginName");
			session.setAttribute("dutyer", memeber_name);
		}
			out.write(jsonS);
			return null;
	}

	// 值班结束，签出
	public ActionForword signOut(ActionForm form, ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.removeAttribute("dutyer");
		JsonVo j = new JsonVo();
		j.setSuccess(true);
		if (service.signOut()) {
			j.setMsg("下班成功，请刷新页面！");
		} else {
			j.setMsg("出现了一点小问题，请刷新页面！");
		}

		JSONObject jsonObj = new JSONObject().fromObject(j);
		String jsonS = jsonObj.toString();
		out.write(jsonS);
		return null;
	}
}
