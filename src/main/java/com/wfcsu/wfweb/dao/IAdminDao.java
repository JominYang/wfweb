package com.wfcsu.wfweb.dao;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;


public interface IAdminDao {
	
	//增加队员
	public int addMember(MemberVo vo);
	//查询队员信息表
	public ArrayList<MemberVo> getMember(int page, int rows, String order, String sort,MemberVo vo);
	//计算队员数量
	public int countMember(MemberVo vo);
	//删除队员
	public boolean delMember(String memberNo);
	
	public ArrayList<MemberVo> getMemberStatistic();
	public ArrayList extrawork(StatisticAttenVo vo,String begin_time,String end_time);
	public ArrayList duty(StatisticAttenVo vo,String begin_time,String end_time);
	public boolean updateStartOfMonth(String start_month);
	public boolean dutyTime(String d, String dh,String times);

}
