package com.wfcsu.wfweb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wfcsu.wfweb.common.ConnDB;
import com.wfcsu.wfweb.dao.IMemberDao;
import com.wfcsu.wfweb.vo.MemberVo;

public class MemberDao implements IMemberDao{

	public MemberVo get(String member_no) {
		String sql = "SELECT * FROM wf_member WHERE member_no = ?";
		Object[] params = {member_no };
		MemberVo vo = null;
		try {
			ArrayList list = ConnDB.getVo(sql, params, MemberVo.class);
			vo = (MemberVo) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return vo;
	}
	
	
	public boolean edit(MemberVo vo) {
		boolean flag = false;
		String sql = "UPDATE wf_member SET member_no=?,member_name=?,member_psw=?,member_class=?,member_phone=?,member_email=?,member_qq=?,member_birth=?,member_room=?,member_home=?,member_permission=?,member_state=? WHERE member_no = ?";
		Object[] params = {vo.getMember_no(),vo.getMember_name(),vo.getMember_psw(),vo.getMember_class(),vo.getMember_phone(),vo.getMember_email(),vo.getMember_qq(),vo.getMember_birth(),vo.getMember_room(),vo.getMember_home(),vo.getMember_permission(),vo.getMember_state(),vo.getMember_no()};
		
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}

	public ArrayList<MemberVo> get(int page, int rows, String order,
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

	public int add(MemberVo vo) {
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

	public int count(MemberVo vo) {
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

	public boolean del(String memberNo) {
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
	
	//队员更新信息
	public boolean updateInfo(MemberVo vo) {
		boolean flag = false;
		String sql = "UPDATE wf_member SET member_class=?,member_phone=?,member_email=?,member_qq=?,member_birth=?,member_room=?,member_home=? WHERE member_no = ?";
		Object[] params = {vo.getMember_class(),vo.getMember_phone(),vo.getMember_email(),vo.getMember_qq(),vo.getMember_birth(),vo.getMember_room(),vo.getMember_home(),vo.getMember_no()};
		ConnDB mydb = new ConnDB();
		
		if(mydb.executeUpdate(sql, params) > 0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}

	public boolean changePwd(String no, String newpwd) {
		boolean flag = false;
		String sql = "UPDATE wf_member SET member_psw=? WHERE member_no=?";
		Object[] params = {newpwd,no};
		ConnDB mydb = new ConnDB();
		if(mydb.executeUpdate(sql, params)>0) {
			flag = true;
		}
		mydb.free();
		return flag;
	}
}
