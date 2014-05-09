package com.wfcsu.wfweb.service;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.ExtraWorkVo;
import com.wfcsu.wfweb.vo.MemberSo;

public interface IExtraWorkService {
	public int add(ExtraWorkVo vo,String[] extra_nos);
	public ArrayList get(int page, int rows, String order, String sort,String startDate, String endDate,String state,String no,String extra_no,String extra_add_no);
	public int count(String startDate, String endDate,String state,String no,String extra_no,String extra_add_no);
	public boolean del(String queryId);
	public ExtraWorkVo getOne(String queryId);
	public boolean edit(ExtraWorkVo vo);
	public boolean check(String extra_id,String pass);
	public ArrayList rank();
	public MemberSo my(String member_no);
	public ArrayList perMonth(String member_no);
	public ArrayList statistic(String member_no,String yearmonth);
		
}
