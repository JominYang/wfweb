package com.wfcsu.wfweb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wfcsu.wfweb.common.ConnDB;
import com.wfcsu.wfweb.dao.IAdminDao;
import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;


public class AdminDao implements IAdminDao {

	public int addMember(MemberVo vo) {
		System.out.println("AdminDao....addMember...");
		int count = 0;
		ConnDB mydb = new ConnDB();
		System.out.println("Daomembername." + vo.getMember_name());
		String sql_addMember = "insert into wf_member values(?,?,'wfcsu',?,?,?,?,?,?,?,2,?)";
		Object[] params = { vo.getMember_no(), vo.getMember_name(),
				vo.getMember_class(), vo.getMember_phone(),
				vo.getMember_email(), vo.getMember_qq(), vo.getMember_birth(),
				vo.getMember_room(), vo.getMember_home(), vo.getMember_state() };

		count = mydb.executeUpdate(sql_addMember, params);
		
		mydb.free();
		return count;
	}

	// 查询队员表
	public ArrayList<MemberVo> getMember(int page, int rows, String order,
			String sort, MemberVo vo) {
		ArrayList list = new ArrayList();
		String sql = "SELECT * FROM wf_member WHERE 1=1 ";
		if (!"".equals(vo.getMember_state()) && vo.getMember_state() != null) {
			sql += "AND member_state = '" + vo.getMember_state() + "'";
		}
		if(!"".equals(vo.getMember_no()) && vo.getMember_no() != null) {
			sql += " AND member_no LIKE '%%" + vo.getMember_no().trim() + "%%'";
		}
		if(!"".equals(vo.getMember_name()) && vo.getMember_name() != null) {
			sql += " AND member_name LIKE '%%" + vo.getMember_name().trim() + "%%'";
		}
		if (sort != null && order != null) {
			sql += " ORDER BY " + sort + " " + order;
		}
		if (page != 0 && rows != 0) {
			sql += " LIMIT " + (page - 1) * rows + "," + rows;
		}

		try {
			list = ConnDB.getVo(sql, null, MemberVo.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	// 计算队员数量
	public int countMember(MemberVo vo) {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM wf_member WHERE 1=1 ";
		if (!"".equals(vo.getMember_state()) && vo.getMember_state() != null) {
			sql += "AND member_state = '" + vo.getMember_state() + "'";
		}
		if(!"".equals(vo.getMember_no()) && vo.getMember_no() != null) {
			sql += " AND member_no LIKE '%%" + vo.getMember_no().trim() + "%%'";
		}
		if(!"".equals(vo.getMember_name()) && vo.getMember_name() != null) {
			sql += " AND member_name LIKE '%%" + vo.getMember_name().trim() + "%%'";
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

	// 删除队员
	public boolean delMember(String memberNo) {
		boolean flag = false;
		String sql = "DELETE FROM wf_member WHERE MEMBER_NO = ?";
		System.out.println(memberNo); 
		Object[] params = { memberNo };
		ConnDB mydb = new ConnDB();
		if (mydb.executeUpdate(sql, params) != 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
	
	public ArrayList<MemberVo> getMemberStatistic() {
		
		ArrayList list = new ArrayList();
		String sql = "SELECT * FROM wf_member WHERE MEMBER_STATE = '在队' order by member_no asc";
		
		try {
			list = ConnDB.getVo(sql, null, MemberVo.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
		
	}

	public ArrayList extrawork(StatisticAttenVo vo, String begin_time,String end_time) {
		ArrayList list =new ArrayList();
		//两个sql语句不创建两个连接的话，连接释放会与问题
		ConnDB mydb = new ConnDB();
		ConnDB mydb1 = new ConnDB();
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sql = "SELECT IF(BACK_DATE!='',BACK_DATE,EXTRA_DATE) DATE,IF(COUNT1+COUNT2!='',COUNT1+COUNT2,IF(COUNT1!='',COUNT1,COUNT2)) COUNT FROM (SELECT * FROM (select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) count1 from wf_attendance where BACK_TIME>? AND BACK_TIME<? AND (ATTEN_MEMBER1=? OR ATTEN_MEMBER2=?) GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')) a RIGHT JOIN (SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_DATE1>? AND EXTRA_DATE1<? AND EXTRA_NO = ? GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d')) b ON a.BACK_DATE = b.extra_date union SELECT * FROM (select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) count1 from wf_attendance where BACK_TIME>? AND BACK_TIME<? AND (ATTEN_MEMBER1=? OR ATTEN_MEMBER2=?) GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')) a LEFT JOIN (SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_DATE1>? AND EXTRA_DATE1<? AND EXTRA_NO = ? GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d')) b ON a.BACK_DATE = b.extra_date) bb ORDER BY DATE";
		String sql1 = "SELECT sum(count) sum FROM (SELECT IF(BACK_DATE!='',BACK_DATE,EXTRA_DATE) DATE,IF(COUNT1+COUNT2!='',COUNT1+COUNT2,IF(COUNT1!='',COUNT1,COUNT2)) COUNT FROM (SELECT * FROM (select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) count1 from wf_attendance where BACK_TIME>?  AND BACK_TIME<? AND (ATTEN_MEMBER1=? OR ATTEN_MEMBER2=?) GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')) a RIGHT JOIN (SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_DATE1>? AND EXTRA_DATE1<? AND EXTRA_NO = ? GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d')) b ON a.BACK_DATE = b.extra_date union SELECT * FROM (select DATE_FORMAT(BACK_TIME,'%Y-%c-%d') BACK_DATE,count(*) count1 from wf_attendance where BACK_TIME>? AND BACK_TIME<? AND (ATTEN_MEMBER1=? OR ATTEN_MEMBER2=?) GROUP BY DATE_FORMAT(BACK_TIME,'%Y-%c-%d')) a LEFT JOIN (SELECT DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d') extra_date,count(*) extra_count,SUM(EXTRA_HOURS) count2 FROM wf_extrawork WHERE EXTRA_DATE1>? AND EXTRA_DATE1<? AND EXTRA_NO = ? GROUP BY DATE_FORMAT(EXTRA_DATE1,'%Y-%c-%d')) b ON a.BACK_DATE = b.extra_date) bb ORDER BY DATE) cc";
		Object[] params = {begin_time,end_time,vo.getMember(),vo.getMember(),begin_time,end_time,vo.getMember(),begin_time,end_time,vo.getMember(),vo.getMember(),begin_time,end_time,vo.getMember()};
		Object[] params1 = {begin_time,end_time,vo.getMember(),vo.getMember(),begin_time,end_time,vo.getMember(),begin_time,end_time,vo.getMember(),vo.getMember(),begin_time,end_time,vo.getMember()};
		StatisticAttenVo voN = null;
	    try {
	    	 rs = mydb.executeQuery(sql, params);
			while(rs.next()){
				voN = new StatisticAttenVo();
				voN.setMember(vo.getMember());
				voN.setDate(rs.getString("date"));
				voN.setCount(rs.getString("COUNT"));
				list.add(voN);
			}
			rs1 = mydb1.executeQuery(sql1, params1);
			while(rs1.next()){
				voN = new StatisticAttenVo();
				voN.setMember(vo.getMember());
				voN.setDate("总计");
				voN.setCount(rs1.getString("sum"));
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
	
	public ArrayList duty(StatisticAttenVo vo, String begin_time,String end_time) {
		ArrayList list =new ArrayList();
		ConnDB mydb = new ConnDB();
		ConnDB mydb1 = new ConnDB();
		
		String sql = "SELECT DUTY_DATE,SUM(OPTION_VALUE2) sum FROM (SELECT * FROM wf_duty WHERE DUTY_MEMBER =? AND DUTY_DATE>? AND DUTY_DATE<?) a LEFT JOIN wf_option b ON a.DUTY_ORDER=b.OPTION_VALUE3 GROUP BY DUTY_DATE ORDER BY DUTY_DATE";
		String sql1 = "SELECT sum(sum) sums FROM (SELECT DUTY_DATE,SUM(OPTION_VALUE2) sum FROM (SELECT * FROM wf_duty WHERE DUTY_MEMBER =? AND DUTY_DATE>? AND DUTY_DATE<?) a LEFT JOIN wf_option b ON a.DUTY_ORDER=b.OPTION_VALUE3 GROUP BY DUTY_DATE ORDER BY DUTY_DATE) c";
		Object[] params = {vo.getMember(),begin_time,end_time};
		
		StatisticAttenVo voN = null;
	    try {
	    	ResultSet rs = mydb.executeQuery(sql, params);
			while(rs.next()){
				voN = new StatisticAttenVo();
				voN.setDate(rs.getString("DUTY_DATE"));
				voN.setCount(rs.getString("sum"));
				list.add(voN);
			}
			ResultSet rs1 = mydb1.executeQuery(sql1, params);
			while(rs1.next()) {
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

	public boolean updateStartOfMonth(String start_month) {
		boolean flag = false;
		String sql = "UPDATE wf_option SET OPTION_VALUE="+start_month+" WHERE OPTION_NAME='BEGIN_OF_MONTH'";
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, null)>0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}

	public boolean dutyTime(String d, String dh,String times) {
		boolean flag =false;
		String sql = "UPDATE wf_option SET OPTION_VALUE='"+d+"',OPTION_VALUE2="+dh+" WHERE OPTION_NAME='"+times+"'";
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, null)>0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
}
