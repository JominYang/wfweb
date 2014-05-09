package com.wfcsu.wfweb.action;

import java.util.HashMap;
import java.util.Map;



public class ActionMapping {
	private String path;
	private String type;
	private String parameter;
	private String name;
	private Map<String,ActionForword> map=new HashMap<String,ActionForword>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ActionForword getForword(String name) {
		return map.get(name);
	}
	public void setForword( ActionForword forword) {
		map.put(forword.getName(),forword);
	}

	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	
}
