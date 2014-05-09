package com.wfcsu.wfweb.service;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;


public interface IAdminService {
	
	public int addMember(MemberVo vo);
	public ArrayList<MemberVo> getMember(int page, int rows, String order, String sort,MemberVo vo);
	public int countMember(MemberVo vo);
	public void delMember(String nos);
	public ArrayList<MemberVo> getMemberStatistic();
	public ArrayList extrawork(StatisticAttenVo vo,String yearmonth); //加班和工资
	public ArrayList duty(StatisticAttenVo vo,String yearmonth); //值班
	public boolean updateStartOfMonth(String start_month);
	public boolean dutyTime(String d1,String d2,String d3,String d4,String d5,String dh1,String dh2,String dh3,String dh4);
}
