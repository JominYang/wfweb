package com.wfcsu.wfweb.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfcsu.wfweb.mapping.IActionConfig;
import com.wfcsu.wfweb.mapping.IParser;
import com.wfcsu.wfweb.mapping.impl.Parser;






public class ActionServlet extends HttpServlet {
	private RequestProcessor processor;
	public void init()  {

		String file=this.getInitParameter("config");
		file=this.getServletContext().getRealPath(file);
		IParser parser=new Parser(file);
		IActionConfig actionconfig=parser.parser();
		processor=new RequestProcessor(actionconfig);
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processor.processor(request, response);
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	

}
