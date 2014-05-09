package com.wfcsu.wfweb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wfcsu.wfweb.common.ConnDB;
import com.wfcsu.wfweb.dao.IAttendanceDao;
import com.wfcsu.wfweb.vo.AttendanceVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;


public class AttendanceDao implements IAttendanceDao{
	//增加出勤单
	public int add(AttendanceVo vo) {
		int count = 0;
		ConnDB mydb = new ConnDB();
		String sql_addAttendance = "insert into wf_attendance(CLIENT_NAME,CLIENT_PHONE,CLIENT_ADDRESS_A,CLIENT_ADDRESS_B,CLIENT_ADDRESS_C," +
				"REGISTER_TIME,PROBLEM_DESCRIBE,COMPUTER_TYPE,SYSTEM_TYPE,DUTY_MEMBER,ATTEN_STATE,ATTEN_TYPE,ATTEN_REMARK)" +
				"values(?,?,?,?,?,?,?,?,?,?,'未处理','正式',?)";
		Object[] params = {vo.getClient_name(),vo.getClient_phone(),vo.getClient_address_a(),vo.getClient_address_b(),vo.getClient_address_c(),
				           vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),
				           vo.getDuty_member(),vo.getAtten_remark()};

		count = mydb.executeUpdate(sql_addAttendance, params);
		mydb.free();

		return count;
	}
	//获得所有出勤单信息
	public ArrayList get(int page, int rows, String order,
			String sort, AttendanceVo vo,String no) {
		ArrayList list = new ArrayList();
		String sql = null;
		if("check".equals(no)){
			sql="select *  from wf_attendance  where ATTEN_TYPE = '临时' ";
		}else if("check1".equals(no)){
			sql="SELECT * FROM wf_attendance WHERE ISNULL(GET_MEMBER) AND  ATTEN_TYPE = '临时' ";
		}
		else if("modify1".equals(no)){
			sql="select *  from wf_attendance  where 1=1 ";
			
			if (!"".equals(vo.getAtten_state()) && vo.getAtten_state() != null) {
				sql += " AND ATTEN_STATE = '" + vo.getAtten_state() + "'";
			}
			
			if(!"".equals(vo.getClient_name()) && vo.getClient_name() != null){
				sql += " AND CLIENT_NAME LIKE '%%" + vo.getClient_name().trim() + "%%'";
			}
			if(!"".equals(vo.getAtten_member1()) && vo.getAtten_member1() != null){
				sql += " AND (ATTEN_MEMBER1 LIKE '%%" + vo.getAtten_member1().trim() + "%%' or " +
						" ATTEN_MEMBER2 LIKE '%%" + vo.getAtten_member1().trim() + "%%')";
			}
			if(!"".equals(vo.getGet_member()) && vo.getGet_member() != null){
				sql += " AND GET_MEMBER='" + vo.getGet_member().trim()+"'";
			}
		} else if("modify".equals(no)){
			sql="select *  from wf_attendance  where ATTEN_TYPE = '正式' ";
			
			if (!"".equals(vo.getAtten_state()) && vo.getAtten_state() != null) {
				sql += " AND ATTEN_STATE = '" + vo.getAtten_state() + "'";
			}
			
			if(!"".equals(vo.getClient_name()) && vo.getClient_name() != null){
				sql += " AND CLIENT_NAME LIKE '%%" + vo.getClient_name().trim() + "%%'";
			}
			if(!"".equals(vo.getAtten_member1()) && vo.getAtten_member1() != null){
				sql += " AND (ATTEN_MEMBER1 LIKE '%%" + vo.getAtten_member1().trim() + "%%' or " +
						" ATTEN_MEMBER2 LIKE '%%" + vo.getAtten_member1().trim() + "%%')";
			}
			if(!"".equals(vo.getGet_member()) && vo.getGet_member() != null){
				sql += " AND GET_MEMBER='" + vo.getGet_member().trim()+"'";
			}
		}
		
		
		if (sort != null && order != null) {
			sql += " ORDER BY " + sort + " " + order+",ATTEN_TYPE DESC";
		}
		if (page != 0 && rows != 0) {
			sql += " LIMIT " + (page - 1) * rows + "," + rows;
		}
			
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		AttendanceVo avo = null;
		
		try {
			while(rs.next()) {
				avo = new AttendanceVo();
				avo.setAtten_member1(rs.getString("atten_member1"));
				avo.setAtten_member2(rs.getString("atten_member2"));
				avo.setAtten_remark(rs.getString("atten_remark"));
				avo.setAtten_state(rs.getString("atten_state"));
				avo.setAtten_type(rs.getString("atten_type"));
				avo.setAttendance_no(rs.getString("attendance_no"));
				avo.setBack_duty_member(rs.getString("back_duty_member"));
				avo.setBack_time(rs.getString("back_time"));
				avo.setClient_address_a(rs.getString("client_address_a"));
				avo.setClient_address_b(rs.getString("client_address_b"));
				avo.setClient_address_c(rs.getString("client_address_c"));
				avo.setClient_name(rs.getString("client_name"));
				avo.setClient_phone(rs.getString("client_phone"));
				avo.setComputer_type(rs.getString("computer_type"));
				avo.setDuty_member(rs.getString("duty_member"));
				avo.setProblem_describe(rs.getString("problem_describe"));
				avo.setRegister_time(rs.getString("register_time").substring(0, 16));
				avo.setSolution(rs.getString("solution"));
				avo.setSystem_type(rs.getString("system_type"));
				avo.setGet_member(rs.getString("get_member"));
				avo.setGet_time(rs.getString("get_time"));
				avo.setAddress(avo.getClient_address_a()+avo.getClient_address_b()+avo.getClient_address_c());
				list.add(avo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		
		return list;
	}
	//计算出勤单数量
	public int count(AttendanceVo vo,String no) {
		int total = 0;
	    String sql = null;
	    if("check".equals(no)){
			sql="select count(*) from wf_attendance  where ATTEN_TYPE = '临时' ";
		}else if("check1".equals(no)){
			sql="SELECT count(*) FROM wf_attendance WHERE ISNULL(GET_MEMBER) AND  ATTEN_TYPE = '临时' ";
		}
		else if("modify".equals(no)){
			 sql = "select count(*) from wf_attendance where ATTEN_TYPE = '正式'";
			    if (!"".equals(vo.getAtten_state()) && vo.getAtten_state() != null) {
					sql += " AND ATTEN_STATE = '" + vo.getAtten_state() + "'";
				}
				
				if(!"".equals(vo.getClient_name()) && vo.getClient_name() != null){
					sql += " AND CLIENT_NAME LIKE '%%" + vo.getClient_name().trim() + "%%'";
				}
				if(!"".equals(vo.getAtten_member1()) && vo.getAtten_member1() != null){
					sql += " AND (ATTEN_MEMBER1 LIKE '%%" + vo.getAtten_member1().trim() + "%%' or " +
							" ATTEN_MEMBER2 LIKE '%%" + vo.getAtten_member1().trim() + "%%')";
				}
				if(!"".equals(vo.getGet_member()) && vo.getGet_member() != null){
					sql += " AND GET_MEMBER='" + vo.getGet_member().trim()+"'";
				}
				
		}else if("modify1".equals(no)){
			sql="select COUNT(*) from wf_attendance  where 1=1 ";
			
			if (!"".equals(vo.getAtten_state()) && vo.getAtten_state() != null) {
				sql += " AND ATTEN_STATE = '" + vo.getAtten_state() + "'";
			}
			
			if(!"".equals(vo.getClient_name()) && vo.getClient_name() != null){
				sql += " AND CLIENT_NAME LIKE '%%" + vo.getClient_name().trim() + "%%'";
			}
			if(!"".equals(vo.getAtten_member1()) && vo.getAtten_member1() != null){
				sql += " AND (ATTEN_MEMBER1 LIKE '%%" + vo.getAtten_member1().trim() + "%%' or " +
						" ATTEN_MEMBER2 LIKE '%%" + vo.getAtten_member1().trim() + "%%')";
			}
			if(!"".equals(vo.getGet_member()) && vo.getGet_member() != null){
				sql += " AND GET_MEMBER='" + vo.getGet_member().trim()+"'";
			}
		}
	   
	    ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		try {
			while (rs.next()) {
				total = rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return total;
	}
	//删除出勤单
	public boolean del(String attendanceNo) {
		boolean flag = false;
		String sql = "DELETE FROM wf_attendance WHERE ATTENDANCE_NO = ?";
		Object[] params = { attendanceNo };
		ConnDB mydb = new ConnDB();
		if (mydb.executeUpdate(sql, params) != 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	
	//修改出勤单
	public boolean edit(AttendanceVo vo) {
		boolean flag = false;
		ConnDB mydb = new ConnDB();
		String sql = "UPDATE wf_attendance SET CLIENT_NAME=?,CLIENT_ADDRESS_A=?,CLIENT_ADDRESS_B=?,CLIENT_ADDRESS_C=?,CLIENT_PHONE=?,REGISTER_TIME=?,PROBLEM_DESCRIBE=?,COMPUTER_TYPE=?,SYSTEM_TYPE=?,DUTY_MEMBER=?,GET_MEMBER=?,ATTEN_MEMBER1=?,ATTEN_MEMBER2=?,SOLUTION=?," +
				"BACK_DUTY_MEMBER=?,ATTEN_STATE=?,ATTEN_TYPE='正式',ATTEN_REMARK=?";
		if("".equals(vo.getGet_time())&&"".equals(vo.getBack_time())) {
			sql+=" WHERE attendance_no = ?";
			Object[] params = {vo.getClient_name(),vo.getClient_address_a(),vo.getClient_address_b(),vo.getClient_address_c(),vo.getClient_phone(),vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),vo.getDuty_member(),vo.getGet_member(),vo.getAtten_member1(),vo.getAtten_member2(),vo.getSolution(),
					vo.getBack_duty_member(),vo.getAtten_state(),vo.getAtten_remark(),vo.getAttendance_no()};
			if(mydb.executeUpdate(sql, params) > 0) {
				flag = true;
			}
		}
		if(!"".equals(vo.getGet_time())&&"".equals(vo.getBack_time())) {
			sql+=",GET_TIME=? WHERE attendance_no = ?";
			Object[] params = {vo.getClient_name(),vo.getClient_address_a(),vo.getClient_address_b(),vo.getClient_address_c(),vo.getClient_phone(),vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),vo.getDuty_member(),vo.getGet_member(),vo.getAtten_member1(),vo.getAtten_member2(),vo.getSolution(),
					vo.getBack_duty_member(),vo.getAtten_state(),vo.getAtten_remark(),vo.getGet_time(),vo.getAttendance_no()};
			if(mydb.executeUpdate(sql, params) > 0) {
				flag = true;
			}
		}
		if(!"".equals(vo.getBack_time())&&"".equals(vo.getGet_time())) {
			sql+=",BACK_TIME=? WHERE attendance_no = ?";
			Object[] params = {vo.getClient_name(),vo.getClient_address_a(),vo.getClient_address_b(),vo.getClient_address_c(),vo.getClient_phone(),vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),vo.getDuty_member(),vo.getGet_member(),vo.getAtten_member1(),vo.getAtten_member2(),vo.getSolution(),
					vo.getBack_duty_member(),vo.getAtten_state(),vo.getAtten_remark(),vo.getBack_time(),vo.getAttendance_no()};
			if(mydb.executeUpdate(sql, params) > 0) {
				flag = true;
			}
		}
		if(!"".equals(vo.getGet_time())&&!"".equals(vo.getBack_time())) {
			sql+=",GET_TIME=?,BACK_TIME=?  WHERE attendance_no = ?";
			Object[] params = {vo.getClient_name(),vo.getClient_address_a(),vo.getClient_address_b(),vo.getClient_address_c(),vo.getClient_phone(),vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),vo.getDuty_member(),vo.getGet_member(),vo.getAtten_member1(),vo.getAtten_member2(),vo.getSolution(),
					vo.getBack_duty_member(),vo.getAtten_state(),vo.getAtten_remark(),vo.getGet_time(),vo.getBack_time(),vo.getAttendance_no()};
			if(mydb.executeUpdate(sql, params) > 0) {
				flag = true;
			}
		}
		
		mydb.free();
		return flag;
	}
	//拿单处理
	public boolean goToDeal(AttendanceVo vo) {
		boolean flag = false;
		String sql = "UPDATE wf_attendance SET GET_MEMBER=?,GET_TIME=?,ATTEN_STATE='处理中' WHERE attendance_no = ?";
		Object[] params = {vo.getGet_member(),vo.getGet_time(),vo.getAttendance_no()};
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	//还单处理
	public boolean back(AttendanceVo vo) {
		boolean flag = false;
		String sql = "UPDATE wf_attendance SET ATTEN_MEMBER1=?,ATTEN_MEMBER2=?,ATTEN_STATE='已完成',ATTEN_TYPE='正式',BACK_TIME=?,BACK_DUTY_MEMBER=?,SOLUTION=? WHERE attendance_no = ?";
		Object[] params = {vo.getAtten_member1(),vo.getAtten_member2(),vo.getBack_time(),vo.getBack_duty_member(),vo.getSolution(),vo.getAttendance_no()};
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	//出勤单作废处理
	public boolean voidIt(String attendance_no) {
		boolean flag = false;
		String sql = "UPDATE wf_attendance SET ATTEN_STATE='已作废',ATTEN_MEMBER1=NULL,ATTEN_MEMBER2=NULL WHERE attendance_no = ?";
		Object[] params = {attendance_no};
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	//废单还原
	public boolean backToNotDeal(String attendance_no) {
		boolean flag = false;
		String sql = "UPDATE wf_attendance SET ATTEN_STATE='未处理',GET_MEMBER=NULL,GET_TIME=NULL WHERE attendance_no = ?";
		Object[] params = {attendance_no};
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	//当月某队员出勤单状态（已完成，处理中）
	public MemberSo myStatus(String member_no,String member_name,String bengin_time) {
		MemberSo so = new MemberSo();
		String sql1 = null;
		String sql2 = null;
		if("wfadmin".equals(member_no)){
			sql1 = "SELECT COUNT(*) FROM wf_attendance WHERE ATTEN_STATE='已完成' AND BACK_TIME > '"+bengin_time+"'";
			sql2 = "SELECT COUNT(*) FROM wf_attendance WHERE ATTEN_STATE='处理中' AND GET_TIME > '"+bengin_time+"'";
		}else{
			sql1 = "SELECT COUNT(*) FROM wf_attendance WHERE ATTEN_STATE='已完成' AND BACK_TIME > '"+bengin_time+"' AND (ATTEN_MEMBER1 = "+member_no+" OR ATTEN_MEMBER2="+member_no+") ";
			sql2 = "SELECT COUNT(*) FROM wf_attendance WHERE ATTEN_STATE='处理中' AND GET_TIME > '"+bengin_time+"' AND GET_MEMBER='"+member_name+"'";
		}
		ConnDB mydb = new ConnDB();
		ConnDB mydb2 = new ConnDB();
		
		try {
			ResultSet rs1 = mydb.executeQuery(sql1, null);
			while(rs1.next()) {
				so.setMonth_atten_num(rs1.getString("COUNT(*)"));
				
			ResultSet rs2 = mydb2.executeQuery(sql2, null);
			while(rs2.next()) {
				so.setNot_deal(rs2.getString("COUNT(*)"));
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
			mydb2.free();
		}
		return so;
	}
	//当月出勤量排行
	public ArrayList rank(String bengin_time) {
		ArrayList list = new ArrayList();
		MemberSo so = null;
		String sql = "SELECT MEMBER_NO,MEMBER_NAME,COUNT FROM (SELECT IF(ATTEN_MEMBER1!='',ATTEN_MEMBER1,ATTEN_MEMBER2) ATTEN_MEMBER,IF(COUNTA+COUNTB!='',COUNTA+COUNTB,IF(COUNTA!='',COUNTA,COUNTB)) COUNT FROM (SELECT ATTEN_MEMBER1,COUNT(*) COUNTA FROM wf_attendance WHERE ATTEN_MEMBER1!='' AND BACK_TIME > '"+bengin_time+"' GROUP BY ATTEN_MEMBER1) a RIGHT JOIN (SELECT ATTEN_MEMBER2,COUNT(*) COUNTB FROM wf_attendance WHERE ATTEN_MEMBER2!='' AND BACK_TIME > '"+bengin_time+"' GROUP BY ATTEN_MEMBER2) b ON a.ATTEN_MEMBER1=b.ATTEN_MEMBER2 UNION SELECT IF(ATTEN_MEMBER1!='',ATTEN_MEMBER1,ATTEN_MEMBER2) ATTEN_MEMBER,IF(COUNTA+COUNTB!='',COUNTA+COUNTB,IF(COUNTA!='',COUNTA,COUNTB)) COUNT FROM (SELECT ATTEN_MEMBER1,COUNT(*) COUNTA FROM wf_attendance WHERE ATTEN_MEMBER1!='' AND BACK_TIME > '"+bengin_time+"' GROUP BY ATTEN_MEMBER1) a LEFT JOIN (SELECT ATTEN_MEMBER2,COUNT(*) COUNTB FROM wf_attendance WHERE ATTEN_MEMBER2!='' AND BACK_TIME > '"+bengin_time+"' GROUP BY ATTEN_MEMBER2) b ON a.ATTEN_MEMBER1=b.ATTEN_MEMBER2 ORDER BY COUNT DESC) aa LEFT JOIN wf_member bb ON aa.ATTEN_MEMBER=bb.MEMBER_NO";
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		try {
			while(rs.next()) {
				so = new MemberSo();
				so.setMember_no(rs.getString("MEMBER_NO"));
				so.setMember_name(rs.getString("MEMBER_NAME"));
				so.setMonth_atten_num(rs.getString("COUNT"));
				
				list.add(so);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return list;
	} 
	
	public int addByClient(AttendanceVo vo) {
		int count = 0;
		ConnDB mydb = new ConnDB();
		String sql_addAttendance = "insert into wf_attendance(CLIENT_NAME,CLIENT_PHONE,CLIENT_ADDRESS_A,CLIENT_ADDRESS_B,CLIENT_ADDRESS_C," +
				"REGISTER_TIME,PROBLEM_DESCRIBE,COMPUTER_TYPE,SYSTEM_TYPE,ATTEN_STATE,ATTEN_TYPE,ATTEN_REMARK,MARK)" +
				"values(?,?,?,?,?,?,?,?,?,'未处理','临时',?,80)";
		Object[] params = {vo.getClient_name(),vo.getClient_phone(),vo.getClient_address_a(),vo.getClient_address_b(),vo.getClient_address_c(),
				           vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),vo.getAtten_remark()};
		count = mydb.executeUpdate(sql_addAttendance, params);
		mydb.free();
		
		return count;
	}

	public AttendanceVo get(String attendance_no) {
		String sql = "SELECT * FROM wf_attendance WHERE ATTENDANCE_NO = ?";
		Object[] params = {attendance_no};
		AttendanceVo vo = null;
		try {
			ArrayList list = ConnDB.getVo(sql, params, AttendanceVo.class);
			vo = (AttendanceVo) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	public boolean editClient(AttendanceVo vo) {
		boolean flag = false;
		String sql = "UPDATE wf_attendance SET CLIENT_NAME=?,CLIENT_PHONE=?,REGISTER_TIME=?,PROBLEM_DESCRIBE=?,COMPUTER_TYPE=?,SYSTEM_TYPE=?,DUTY_MEMBER=?," +
				" ATTEN_REMARK=? WHERE attendance_no = ?";
		Object[] params = {vo.getClient_name(),vo.getClient_phone(),vo.getRegister_time(),vo.getProblem_describe(),vo.getComputer_type(),vo.getSystem_type(),vo.getDuty_member(),
				vo.getAtten_remark(),vo.getAttendance_no()};
		
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		
		return flag;
	}
	//登记网上报单
	public boolean changeType(AttendanceVo vo) {
		boolean flag = false;
		String sql = "UPDATE wf_attendance SET ATTEN_TYPE = '正式', DUTY_MEMBER=? WHERE attendance_no = ?";
		Object[] params = {vo.getDuty_member(),vo.getAttendance_no()};
		
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		
		return flag;
	}
	
	//按天统计某队员每天的出勤量
	public ArrayList statisticAtten(StatisticAttenVo vo,String begin_time,String end_time) {
		ArrayList list =new ArrayList();
		ConnDB mydb = new ConnDB();
		ConnDB mydb1 = new ConnDB();
		
		String sql = "select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) from wf_attendance WHERE ATTEN_STATE='已完成' AND BACK_TIME>? AND BACK_TIME<? AND (ATTEN_MEMBER1=? OR ATTEN_MEMBER2=?) GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')";
		String sql1 = "SELECT sum(count) sums FROM(select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) count from wf_attendance WHERE ATTEN_STATE='已完成' AND BACK_TIME>? AND BACK_TIME<? AND (ATTEN_MEMBER1=? OR ATTEN_MEMBER2=?) GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')) cc";
		Object[] params = {begin_time,end_time,vo.getMember(),vo.getMember()};
		Object[] params1 = {begin_time,end_time,vo.getMember(),vo.getMember()};
		
		StatisticAttenVo voN = null;
	    try {
	    	ResultSet rs = mydb.executeQuery(sql, params);
			while(rs.next()){
				voN = new StatisticAttenVo();
				voN.setMember(vo.getMember());
				voN.setDate(rs.getString("BACK_DATE"));
				voN.setCount(rs.getString("COUNT(*)"));
				list.add(voN);
			}
			ResultSet rs1 = mydb1.executeQuery(sql1, params1);
			while(rs1.next()){
				voN = new StatisticAttenVo();
				voN.setMember(vo.getMember());
				voN.setDate("总计");
				voN.setCount(rs1.getString("sums"));
				list.add(voN);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
			mydb1.free();
		}
		return list;
	}
	
	//按月统计某队员每月的出勤量
	public ArrayList statisticByMonth(StatisticAttenVo vo) {
		ArrayList list =new ArrayList();
		ConnDB mydb = new ConnDB();
		String sql = null;
		if("wfadmin".equals(vo.getMember())) {
			sql = "SELECT BACK_DATE,COUNT FROM (select DATE_FORMAT(BACK_TIME,'%Y-%c') BACK_DATE,count(*) COUNT from wf_attendance WHERE ATTEN_STATE='已完成' GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c')) b ORDER BY BACK_DATE DESC";
		}else{
			sql = "SELECT BACK_DATE,COUNT FROM (select DATE_FORMAT(BACK_TIME,'%Y-%c') BACK_DATE,count(*) COUNT from wf_attendance WHERE ATTEN_STATE='已完成' AND (ATTEN_MEMBER1="+vo.getMember()+" OR ATTEN_MEMBER2="+vo.getMember()+") GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c')) b ORDER BY BACK_DATE DESC";
		}
		
		StatisticAttenVo voN = null;
	    try {
	    	ResultSet rs = mydb.executeQuery(sql, null);
			while(rs.next()){
				voN = new StatisticAttenVo();
				voN.setMember(vo.getMember());
				voN.setDate(rs.getString("BACK_DATE"));
				voN.setCount(rs.getString("COUNT"));
				list.add(voN);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return list;
	}

	//按天统计队伍每天的出勤量
	public ArrayList statisticAttenAll(StatisticAttenVo vo) {
		ArrayList list =new ArrayList();
		ConnDB mydb = new ConnDB();
		
		String sql = "select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) from wf_attendance where ATTEN_STATE = '已完成' GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')";
		
		StatisticAttenVo voN = null;
	    try {
	    	ResultSet rs = mydb.executeQuery(sql, null);
			while(rs.next()){
				voN = new StatisticAttenVo();
				voN.setDate(rs.getString("BACK_DATE"));
				voN.setCount(rs.getString("COUNT(*)"));
				list.add(voN);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
