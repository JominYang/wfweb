package com.wfcsu.wfweb.mapping.impl;

import java.util.HashMap;
import java.util.Map;

import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.mapping.IActionConfig;




public class ActionConfig implements IActionConfig{
	private Map<String,ActionMapping>map=new HashMap<String,ActionMapping>();
	private Map<String,String>actionForm=new HashMap<String,String>();
	public ActionMapping getActionMapping(String path) {
		return map.get(path);
	}

	public void setActionMapping(ActionMapping mapping) {
		 map.put(mapping.getPath(),mapping);
	}

	public void setActionForm(String name, String type) {
		actionForm.put(name,type);
		
	}

	public String getActionForm(String name) {
		return actionForm.get(name);
	}



}
