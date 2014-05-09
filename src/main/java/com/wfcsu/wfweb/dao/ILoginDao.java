package com.wfcsu.wfweb.dao;

import com.wfcsu.wfweb.vo.MemberVo;


public interface ILoginDao {
	
	public MemberVo login(String no,String psw);//登录
	public String getTheStartOfMonth();//查询月开始与哪一天
	public String judgeBeforeDutyer();//查询当前值班权限在谁那儿
	public String judgeCurrentDutyer(String number,String date);//查询当前值班是否有人签到
	public String getDutyTime(String number);//查询值班时长
	public boolean signIn(String member_no,String number,String date);//值班签到
	public boolean signOut();//值班结束，签出
}
