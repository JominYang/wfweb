package com.wfcsu.wfweb.vo;

import com.wfcsu.wfweb.action.ActionForm;


public class ExtraWorkVo extends ActionForm{

	String extra_id;
	String extra_add_member;
	String extra_no;
	String extra_date1;
	String extra_date2;
	String extra_hours;
	String extra_content;
	String extra_state;
	String verify_no;
	String query_id;
	String extra_members_name;
	String[] extra_nos;
	public String[] getExtra_nos() {
		return extra_nos;
	}
	public void setExtra_nos(String[] extra_nos) {
		this.extra_nos = extra_nos;
	}
	public void setExtra_nos(String extra_no) {
	}
	public String getExtra_members_name() {
		return extra_members_name;
	}
	public void setExtra_members_name(String extra_members_name) {
		this.extra_members_name = extra_members_name;
	}
	public String getQuery_id() {
		return query_id;
	}
	public void setQuery_id(String query_id) {
		this.query_id = query_id;
	}
	public String getExtra_id() {
		return extra_id;
	}
	public void setExtra_id(String extra_id) {
		this.extra_id = extra_id;
	}
	public String getExtra_add_member() {
		return extra_add_member;
	}
	public void setExtra_add_member(String extra_add_member) {
		this.extra_add_member = extra_add_member;
	}
	
	public String getExtra_no() {
		return extra_no;
	}
	public void setExtra_no(String extra_no) {
		this.extra_no = extra_no;
	}
	public String getExtra_date1() {
		return extra_date1;
	}
	public void setExtra_date1(String extra_date1) {
		this.extra_date1 = extra_date1;
	}
	public String getExtra_date2() {
		return extra_date2;
	}
	public void setExtra_date2(String extra_date2) {
		this.extra_date2 = extra_date2;
	}
	public String getExtra_hours() {
		return extra_hours;
	}
	public void setExtra_hours(String extra_hours) {
		this.extra_hours = extra_hours;
	}
	public String getExtra_content() {
		return extra_content;
	}
	public void setExtra_content(String extra_content) {
		this.extra_content = extra_content;
	}
	public String getExtra_state() {
		return extra_state;
	}
	public void setExtra_state(String extra_state) {
		this.extra_state = extra_state;
	}
	public String getVerify_no() {
		return verify_no;
	}
	public void setVerify_no(String verify_no) {
		this.verify_no = verify_no;
	}
}
