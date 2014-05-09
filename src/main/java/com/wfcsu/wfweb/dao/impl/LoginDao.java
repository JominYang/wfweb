package com.wfcsu.wfweb.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wfcsu.wfweb.common.ConnDB;
import com.wfcsu.wfweb.dao.ILoginDao;
import com.wfcsu.wfweb.vo.MemberVo;



public class LoginDao implements ILoginDao{

	public MemberVo login(String no, String psw) {
		ConnDB mydb = new ConnDB();
		String sql = "SELECT * FROM WF_MEMBER WHERE MEMBER_NO=? AND MEMBER_PSW=?";
        Object[] params = {no,psw};
        MemberVo vo = null;
		try {
			ArrayList list = ConnDB.getVo(sql, params, MemberVo.class);
			vo = (MemberVo) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return vo;
	}

	public String getTheStartOfMonth() {
		String day = "";
		String sql = "SELECT OPTION_VALUE FROM wf_option WHERE OPTION_NAME = 'BEGIN_OF_MONTH'";
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		try {
			while(rs.next()) {
				day = rs.getString("OPTION_VALUE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return day;
	}
	//��ǰ�Ƿ�������ֵ��Ȩ��
	public String judgeBeforeDutyer() {
		String member_name=null;
		String sql="SELECT MEMBER_NAME FROM (SELECT OPTION_VALUE member from wf_option WHERE OPTION_NAME='CURRENT_DUTY_MEMBER') a LEFT JOIN wf_member b ON a.member=b.MEMBER_NO";
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		try {
			while(rs.next()) {
				member_name = rs.getString("MEMBER_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return member_name;
	}

	public String getDutyTime(String number) {
		String time = null;
		String sql = "SELECT OPTION_VALUE FROM WF_OPTION WHERE OPTION_NAME=?";
		Object[] params = {number};
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, params);
		try {
			while(rs.next()) {
				time = rs.getString("OPTION_VALUE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return time;
	}
	public boolean signIn(String member_no,String number,String date) {
		boolean flag = false;
		String sql = "UPDATE wf_option SET OPTION_VALUE = ? WHERE OPTION_NAME='CURRENT_DUTY_MEMBER'";
		String sql2 = "INSERT INTO wf_duty(DUTY_DATE,DUTY_ORDER,DUTY_MEMBER) VALUES(?,?,?)";
		Object[] params = {member_no};
		Object[] params2 = {date,number,member_no};
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params)>0) {
			ConnDB mydb2 = new ConnDB();
			if(mydb2.executeUpdate(sql2, params2)>0) {
				flag = true;
			}
		}
		mydb.free();
		return flag;
	}
	//��ǰ����Ƿ���ǩ
	public String judgeCurrentDutyer(String number, String date) {
		String member_name=null;
		String sql="SELECT MEMBER_NAME FROM (SELECT DUTY_MEMBER from wf_duty WHERE DUTY_DATE=? AND DUTY_ORDER=?) a LEFT JOIN wf_member b ON a.DUTY_MEMBER=b.MEMBER_NO";
		Object[] params = {date,number};
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, params);
		try {
			while(rs.next()) {
				member_name = rs.getString("MEMBER_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return member_name;
	}
	public boolean signOut() {
		Boolean flag = false;
		String sql = "UPDATE wf_option SET OPTION_VALUE=NULL WHERE OPTION_NAME='CURRENT_DUTY_MEMBER'";
		ConnDB mydb = new ConnDB();
		
		if(mydb.executeUpdate(sql, null)>0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
}
