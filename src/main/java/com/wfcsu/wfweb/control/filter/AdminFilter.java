package com.wfcsu.wfweb.control.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wfcsu.wfweb.vo.MemberVo;


public class AdminFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpSession session = request.getSession();
		System.out.println("adminFilter");
		
		MemberVo loginer = (MemberVo) session.getAttribute("loginVo");
		if (loginer != null) {
			String permission = loginer.getMember_permission();
			if("0".equals(permission)) {
				chain.doFilter(sRequest, sResponse);
			} else if("1".equals(permission)) {
				request.setAttribute("error", "你没有足够的权限！请联系管理员！");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
		}else{
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
	}
	public void init(FilterConfig arg0) throws ServletException {

	}

}
