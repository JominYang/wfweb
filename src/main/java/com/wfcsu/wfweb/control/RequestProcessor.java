package com.wfcsu.wfweb.control;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfcsu.wfweb.action.Action;
import com.wfcsu.wfweb.action.ActionForm;
import com.wfcsu.wfweb.action.ActionForword;
import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.mapping.IActionConfig;



public class RequestProcessor {
	private IActionConfig actionConfig;
	public RequestProcessor(IActionConfig actionConfig){
		this.actionConfig=actionConfig ;
		
	}
	public void processor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String path = request.getRequestURI();
		path = path.substring( path.lastIndexOf( "/" )+1,path.lastIndexOf( "." ) );
		
		//检验请求的路径 是否在mvc-config.xml文件中配置 如果没有配置，那么直接返回404
		ActionMapping mapping = actionConfig.getActionMapping( path );
		if( mapping == null ){
			
			try {
				response.sendRedirect( "error404.html" );
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ActionForm form = this.builderActioForm( actionConfig.getActionForm( mapping.getName()));
		//System.out.println(actionConfig.getActionForm( mapping.getName()));
		if(form!=null){
			this.setFormParam( form, request );
			
		}
		
		Action action = builderAction(mapping.getType());
		ActionForword forword=action.execute(form, mapping, request, response);
		if(forword!=null){
			if(forword.isRedirect()){	
			  response.sendRedirect(forword.getPath());
			}else{
				  request.getRequestDispatcher(forword.getPath()).forward(request, response);
			}
		}
	 }
	
	private void setFormParam(ActionForm form, HttpServletRequest request) {
			Field []fields = form.getClass().getDeclaredFields();
		
			for( int i = 0; i < fields.length;i++ ){
			
			String f = fields[i].getName();
		
			//获取当前表单中的数据
			String param = request.getParameter( f );
			
			if( param != null ){//获取表单中对应的数据
				
				//通过对应的set方法设置表单VO中的属性值  username setUsername
				
				String method = "set"+ (""+f.charAt( 0 )).toUpperCase()+f.substring( 1 );
				try {
					Method m = form.getClass().getMethod( method, new Class[]{String.class});
					
					//执行
					m.invoke( form, param );
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}	
	}
	private ActionForm builderActioForm(String type) {
		
		
		ActionForm form= null;
		if( type == null )
			return null;
		try {
			form =(ActionForm)Class.forName(type).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return form;
	}
	private Action builderAction( String type ){
		
		Action action = null;
			
			try {
				
				action = (Action)Class.forName(type).newInstance();
			
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return action;
	}
}
