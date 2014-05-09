package com.wfcsu.wfweb.service;

import com.wfcsu.wfweb.vo.MemberVo;


public interface ILoginService {

	public MemberVo login(String no, String psw);
	public String signIn(String member_no,String number);
	public String getCurrentDutyer();
	public boolean signOut();
}
