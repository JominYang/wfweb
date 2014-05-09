package com.wfcsu.wfweb.dao;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.ExtraWorkVo;
import com.wfcsu.wfweb.vo.MemberSo;

public interface IExtraWorkDao {
	
	public int addView(ExtraWorkVo vo);
	public int addSingle(ExtraWorkVo vo);
	public String getExtraMemberName(String[] extra_nos);
	public ArrayList get(int page, int rows, String order, String sort,String startDate, String endDate,String state,String no,String extra_no,String extra_add_no);
	public int count(String startDate, String endDate,String state,String no,String extra_no,String extra_add_no);
	public boolean del(String queryId);
	public ExtraWorkVo getOne(String queryId);
	public boolean check(String extra_id,String pass);
	public ArrayList rank(String begin_time);
	public MemberSo my(String member_no,String date);
	public ArrayList perMonth(String member_no);
	public ArrayList statistic(String member_no,String begin_time,String end_time);
	
}
