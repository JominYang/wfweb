package com.wfcsu.wfweb.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public ActionForword execute(ActionForm form,ActionMapping mapping,HttpServletRequest request, HttpServletResponse response);
}