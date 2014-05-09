package com.wfcsu.wfweb.action;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * title:动态调用客户端传递的方法名
 * @author Administrator
 *
 */

public class DispatcherAction implements Action{
	
	//
	public ActionForword execute(ActionForm form,ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		
		String method = request.getParameter(mapping.getParameter());
		
		Object obj = null;
		
		try {
			Method m = this.getClass().getMethod( method, new Class[]{ActionForm.class,ActionMapping.class,HttpServletRequest.class,HttpServletResponse.class});
			
			//调用方法
			obj = m.invoke( this, new Object[]{form,mapping,request,response} );
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return (ActionForword)obj;
	}

}
