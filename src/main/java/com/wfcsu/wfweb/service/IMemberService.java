package com.wfcsu.wfweb.service;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.MemberVo;



public interface IMemberService {
	public int add(MemberVo vo);

	public MemberVo get(String member_no);

	public ArrayList<MemberVo> get(int page, int rows, String order,String sort, MemberVo vo);

	public int count(MemberVo vo);

	public void del(String nos);

	public boolean edit(MemberVo vo);
	
	public boolean updateInfo(MemberVo vo);
	public boolean changePwd(String no,String newpwd);
}
