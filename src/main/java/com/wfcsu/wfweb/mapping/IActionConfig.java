package com.wfcsu.wfweb.mapping;

import com.wfcsu.wfweb.action.ActionMapping;



public interface IActionConfig {
	public void setActionMapping(ActionMapping mapping);
	public ActionMapping getActionMapping(String path);
	public void setActionForm(String name, String type);
	public String getActionForm(String name);
}