package com.wfcsu.wfweb.dao;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.MemberVo;


public interface IMemberDao {
	//查询单个队员
	public MemberVo get(String member_no);

	// 查询队员信息表
	public ArrayList<MemberVo> get(int page, int rows, String order,
			String sort, MemberVo vo);

	public boolean edit(MemberVo vo);

	// 增加队员
	public int add(MemberVo vo);

	// 计算队员数量
	public int count(MemberVo vo);

	// 删除队员
	public boolean del(String memberNo);
	public boolean updateInfo(MemberVo vo);
	public boolean changePwd(String no,String newowd);
}
