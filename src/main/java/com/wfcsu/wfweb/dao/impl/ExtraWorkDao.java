package com.wfcsu.wfweb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wfcsu.wfweb.common.ConnDB;
import com.wfcsu.wfweb.dao.IExtraWorkDao;
import com.wfcsu.wfweb.vo.ExtraWorkVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;


public class ExtraWorkDao implements IExtraWorkDao{
	
	public int addView(ExtraWorkVo vo) {
		int count = 0;
		if(vo.getExtra_state()==null||" ".equals(vo.getExtra_state())) {
			vo.setExtra_state("未审核");
		}
		ConnDB mydb = new ConnDB();
		String sql_addExtraView = "insert into wf_extrawork(EXTRA_ADD_MEMBER,EXTRA_NO,EXTRA_DATE1,EXTRA_DATE2,EXTRA_HOURS,EXTRA_CONTENT,EXTRA_STATE,VERIFY_NO,QUERY_ID,EXTRA_MEMBERS_NAME) values(?,'1',?,?,?,?,?,null,?,?)";
		Object[] params = {vo.getExtra_add_member(),vo.getExtra_date1(),vo.getExtra_date2(),vo.getExtra_hours(),vo.getExtra_content(),vo.getExtra_state(),vo.getQuery_id(),vo.getExtra_members_name()};
		count = mydb.executeUpdate(sql_addExtraView, params);
		mydb.free();
		return count;
	}
	public int addSingle(ExtraWorkVo vo) {
		int count = 0;
		ConnDB mydb = new ConnDB();
		if(vo.getExtra_state()==null||" ".equals(vo.getExtra_state())) {
			vo.setExtra_state("未审核");
		}
		String sql_addExtraSingle = "insert into wf_extrawork(EXTRA_NO,EXTRA_DATE1,EXTRA_DATE2,EXTRA_HOURS,EXTRA_STATE,VERIFY_NO,QUERY_ID,EXTRA_MEMBERS_NAME,EXTRA_CONTENT) values(?,?,?,?,?,null,?,'1',?)";
		Object[] params = {vo.getExtra_no(),vo.getExtra_date1(),vo.getExtra_date2(),vo.getExtra_hours(),vo.getExtra_state(),vo.getQuery_id(),vo.getExtra_content()};

		count = mydb.executeUpdate(sql_addExtraSingle, params);
		
		mydb.free();
		return count;
	}

	public String getExtraMemberName(String[] extra_nos) {
		String names = "";
		ConnDB mydb = new ConnDB();
		String sql_getMemberName = "select member_name from wf_member where member_no = ?";
		for(String no : extra_nos){
			Object[] params = {no };
			ResultSet rs = mydb.executeQuery(sql_getMemberName, params);
			try {
				while(rs.next()) {
					String name = rs.getString("member_name");
					names = names + "，" + name;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		mydb.free();
		return names.substring(1);
	}
	
	public ArrayList get(int page, int rows, String order, String sort,String startDate, String endDate,String state,String no,String extra_no,String extra_add_no) {
		ArrayList list = new ArrayList();
		ExtraWorkVo vo = null;
		String sql = "select * from wf_extrawork where";
		
		if(extra_no != null&&!" ".equals(extra_no)) {
			sql += " EXTRA_NO = "+extra_no;
		}else {
			sql += " extra_no = '1'"; 
		}
		if(extra_add_no !=null&&!" ".equals(extra_add_no)) {
			sql += " AND extra_add_member="+extra_add_no;
		}
		if(no == "modify" || "modify".equals(no)) {
			sql +=" AND EXTRA_STATE = '通过'";
		}
		if(no == "check" || "check".equals(no)) {
			if("".equals(state)||state==null) {
				sql += " AND EXTRA_STATE != '通过'";
			}else {
				sql += " AND EXTRA_STATE ='"+state+"'";
			}
		}
		if(no == "nopass" || "nopass".equals(no)) {
			sql += " AND EXTRA_STATE = '未通过'";
		}
		if(no == "nocheck" || "nocheck".equals(no)) {
			sql += " AND EXTRA_STATE = '未审核'";
		}
		
		if(startDate != null&&!"".equals(startDate)&&endDate != null&&!"".equals(endDate)) {
			sql += " AND EXTRA_DATE1 > '"+startDate+"' and EXTRA_DATE1 < '"+endDate+"'";
		}
		if (sort != null && order != null) {
			sql += " ORDER BY " + sort + " " + order;
		}
		if (page != 0 && rows != 0) {
			sql += " LIMIT " + (page - 1) * rows + "," + rows;
		}
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		try {
			while(rs.next()) {
				vo = new ExtraWorkVo();
				vo.setExtra_date1(rs.getString("extra_date1").substring(0, 16));
				vo.setExtra_date2(rs.getString("extra_date2").substring(0, 16));
				vo.setExtra_hours(rs.getString("extra_hours"));
				vo.setExtra_content(rs.getString("extra_content"));
				vo.setExtra_state(rs.getString("extra_state"));
				vo.setExtra_members_name(rs.getString("extra_members_name"));
				vo.setQuery_id(rs.getString("query_id"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		
		return list;
	}
	
	public int count(String startDate, String endDate,String state,String no,String extra_no,String extra_add_no) {
		int count = 0;
		String sql = "select count(*) from wf_extrawork where";
		if(extra_no != null&&!" ".equals(extra_no)) {
			sql += " EXTRA_NO = "+extra_no;
		}else {
			sql += " extra_no = '1'"; 
		}
		if(extra_add_no !=null&&!" ".equals(extra_add_no)) {
			sql += " AND extra_add_member="+extra_add_no;
		}
		if(no == "modify" || "modify".equals(no)) {
			sql +=" AND EXTRA_STATE = '通过'";
		}
		if(no == "check" || "check".equals(no)) {
			if("".equals(state)||state==null) {
				sql += " AND EXTRA_STATE != '通过'";
			}else {
				sql += " AND EXTRA_STATE ='"+state+"'";
			}
		}
		if(no == "nopass" || "nopass".equals(no)) {
			sql += " AND EXTRA_STATE = '未通过'";
		}
		if(no == "nocheck" || "nocheck".equals(no)) {
			sql += " AND EXTRA_STATE = '未审核'";
		}
		if(startDate != null&&!"".equals(startDate)&&endDate != null&&!"".equals(endDate)) {
			sql += " AND EXTRA_DATE1 > '"+startDate+"' and EXTRA_DATE1 < '"+endDate+"'";
		}
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		
		try {
			while(rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return count;
	}
	
	public boolean del(String queryId) {
		boolean flag = false;
		String sql = "DELETE FROM wf_extrawork WHERE QUERY_ID = ?";
		Object[] params = { queryId };
		ConnDB mydb = new ConnDB();
		if (mydb.executeUpdate(sql, params) != 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	
	public ExtraWorkVo getOne(String queryId) {
		ExtraWorkVo vo = null;
		String[] extraNos = null;
		String sql = "SELECT * FROM wf_extrawork WHERE EXTRA_NO = 1 AND QUERY_ID = ?";
		String sql_count = "SELECT count(*) FROM wf_extrawork WHERE EXTRA_NO != 1 AND QUERY_ID = ?";
		String sqll = "SELECT extra_no FROM wf_extrawork WHERE EXTRA_NO != 1 AND QUERY_ID = ?";
		Object[] params = { queryId };
		ConnDB mydb = new ConnDB();
		ConnDB mydb2 = new ConnDB();
		ConnDB mydb3 = new ConnDB();
		
		try {
			int count = 0;
			ResultSet rs = mydb.executeQuery(sql, params);
			while(rs.next()) {
				vo = new ExtraWorkVo();
				vo.setExtra_add_member(rs.getString("extra_add_member"));
				vo.setExtra_date1(rs.getString("extra_date1"));
				vo.setExtra_date2(rs.getString("extra_date2"));
				vo.setExtra_hours(rs.getString("extra_hours"));
				vo.setExtra_content(rs.getString("extra_content"));
				vo.setExtra_state(rs.getString("extra_state"));
				vo.setVerify_no(rs.getString("verify_no"));
				vo.setQuery_id(rs.getString("query_id"));
			}
			ResultSet rs_count = mydb2.executeQuery(sql_count, params);
			while(rs_count.next()) {
				count = rs_count.getInt("count(*)");
			}
			
			extraNos = new String[count];
			int i = 0;
			ResultSet rss = mydb3.executeQuery(sqll, params);
			while(rss.next()) {
				extraNos[i++] = rss.getString("extra_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
			mydb2.free();
			mydb3.free();
		}
		vo.setExtra_nos(extraNos);
		return vo;
	}
	
	public boolean check(String query_id,String pass) {
		boolean flag = false;
		String sql = "";
		if("1".equals(pass)){
			sql = "UPDATE wf_extrawork SET EXTRA_STATE = '通过',VERIFY_NO = 'admin' WHERE QUERY_ID = '"+ query_id+"'";
		}
		else{
			sql = "UPDATE wf_extrawork SET EXTRA_STATE = '未通过',VERIFY_NO = 'admin' WHERE QUERY_ID = '"+ query_id+"'";

		}
		Object[] params = {};
		
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	
	public ArrayList rank(String begin_time) {
		ArrayList list = new ArrayList();
		MemberSo so = null;
		String sql = "SELECT MEMBER_NO,MEMBER_NAME,count,sum FROM (SELECT extra_no,count(*) count,sum(EXTRA_HOURS) sum FROM wf_extrawork WHERE EXTRA_NO!=1 AND EXTRA_STATE='通过' AND EXTRA_DATE1>'"+begin_time+"' GROUP BY EXTRA_NO ORDER BY count desc,sum desc) a left JOIN wf_member b ON a.extra_no = b.MEMBER_NO";
		ConnDB mydb = new ConnDB();
		ResultSet rs = mydb.executeQuery(sql, null);
		try {
			while(rs.next()) {
				so = new MemberSo();
				so.setMember_no(rs.getString("MEMBER_NO"));
				so.setMember_name(rs.getString("MEMBER_NAME"));
				so.setMonth_extrawork_num((rs.getString("COUNT")));
				so.setMonth_extrawork_hours(rs.getString("sum"));
				
				list.add(so);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return list;
	}
	public MemberSo my(String member_no,String date){
		MemberSo so = new MemberSo();
		String sql = null;
		if("wfadmin".equals(member_no)) {
			sql = "SELECT count(*),sum(EXTRA_HOURS) EXTRA_HOURS FROM wf_extrawork WHERE EXTRA_NO!=1 AND EXTRA_STATE='通过' AND EXTRA_DATE1>'"+date+"'";
		}else{
			sql = "SELECT count(*),sum(EXTRA_HOURS) EXTRA_HOURS FROM wf_extrawork WHERE EXTRA_NO = " + member_no+" AND EXTRA_STATE='通过' AND EXTRA_DATE1>'"+date+"'";
		}
		ConnDB mydb = new ConnDB();
		
		try {
			ResultSet rs = mydb.executeQuery(sql, null);
			while(rs.next()) {
				so.setMonth_extrawork_num(rs.getString("count(*)"));
				so.setMonth_extrawork_hours(rs.getString("EXTRA_HOURS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return so;
	}
	public ArrayList perMonth(String member_no){
		ArrayList list = new ArrayList();
		MemberSo so = null;
		String sql = null;
		if("wfadmin".equals(member_no)) {
			sql = "SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_NO!=1 AND EXTRA_STATE='通过' GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c')";
		}else{
			sql = "SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_NO = "+member_no+" AND EXTRA_STATE='通过' GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c')";
		}
		ConnDB mydb = new ConnDB();
		
		try {
			ResultSet rs = mydb.executeQuery(sql, null);
			while(rs.next()) {
				so = new MemberSo();
				so.setMember_no(rs.getString("extra_date"));
				so.setMonth_extrawork_num(rs.getString("extra_count"));
				so.setMonth_extrawork_hours(rs.getString("count2"));
				list.add(so);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mydb.free();
		}
		return list;
	}
	public ArrayList statistic(String member_no,String begin_time,String end_time) {
		ArrayList list = new ArrayList();
		ConnDB mydb = new ConnDB();
		ConnDB mydb1 = new ConnDB();
		
		String sql = "SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_DATE1>? AND EXTRA_DATE1<? AND EXTRA_NO =? GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d')";
		String sql1 = "SELECT sum(count2) sums FROM(SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_DATE1>? AND EXTRA_DATE1<? AND EXTRA_NO =? GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d')) cc";
		Object[] params = {begin_time,end_time,member_no};
		Object[] params1 = {begin_time,end_time,member_no};
		
		StatisticAttenVo voN = null;
	    try {
	    	ResultSet rs = mydb.executeQuery(sql, params);
			while(rs.next()){
				voN = new StatisticAttenVo();
				voN.setMember(member_no);
				voN.setDate(rs.getString("extra_date"));
				voN.setCount(rs.getString("count2"));
				list.add(voN);
			}
			ResultSet rs1 = mydb1.executeQuery(sql1, params1);
			while(rs1.next()){
				voN = new StatisticAttenVo();
				voN.setMember(member_no);
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
}
