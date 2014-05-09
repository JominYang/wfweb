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

public class DutyerFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpSession session = request.getSession();
		
		String dutyer = (String) session.getAttribute("dutyer");
		if(dutyer!=null&&!"".equals(dutyer)) {
			chain.doFilter(sRequest, sResponse);
		}else {
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
