package com.wfcsu.wfweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.wfcsu.wfweb.dao.IExtraWorkDao;
import com.wfcsu.wfweb.dao.ILoginDao;
import com.wfcsu.wfweb.dao.impl.ExtraWorkDao;
import com.wfcsu.wfweb.dao.impl.LoginDao;
import com.wfcsu.wfweb.service.IExtraWorkService;
import com.wfcsu.wfweb.vo.ExtraWorkVo;
import com.wfcsu.wfweb.vo.MemberSo;


public class ExtraWorkService implements IExtraWorkService{

	private IExtraWorkDao dao;
	public ExtraWorkService(){
		dao = new ExtraWorkDao();
	}
	
	public int add(ExtraWorkVo vo,String[] extra_nos) {
		int count = 0;
		int flag = 0;
		
		for (String no : extra_nos) {
			vo.setExtra_no(no);
	    	count = dao.addSingle(vo);
		}
		if(count != 0) {
			String names = dao.getExtraMemberName(extra_nos);
			vo.setExtra_members_name(names);
			flag = dao.addView(vo);
		}
		return flag;
	}
	public ArrayList get(int page, int rows, String order, String sort,String startDate, String endDate,String state,String no,String extra_no,String extra_add_no) {
		return dao.get(page, rows, order, sort,startDate,endDate,state,no,extra_no,extra_add_no);
	}
	
	public boolean del(String nos) {
		boolean b = false;
		if (nos != null && !"".equals(nos)) {
			for(String no: nos.split(",")) {
				b = dao.del(no);
			}
		}
		return b;
	}

	public ExtraWorkVo getOne(String queryId) {
		ExtraWorkVo vo = dao.getOne(queryId);
		return vo;
	}
	
	public boolean edit(ExtraWorkVo vo) {
		int count = 0;
		int count2 = 0;
		if(dao.del(vo.getQuery_id())) {
			for (String no : vo.getExtra_nos()) {
				vo.setExtra_no(no);
		    	count = dao.addSingle(vo);
			}
		}
		if(count != 0) {
			String names = dao.getExtraMemberName(vo.getExtra_nos());
			vo.setExtra_members_name(names);
			count2 = dao.addView(vo);
		}
		return count2>0?true:false;
	}
	public boolean check(String extra_id,String pass) {
		return dao.check(extra_id,pass);
	}
	public ArrayList rank() {
		String month_begin = null;
		ILoginDao getTime = new LoginDao();
		month_begin = getTime.getTheStartOfMonth();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yy = sdf.format(new Date()).substring(0, 5);
		String mm = sdf.format(new Date()).substring(5, 7);
		String dd = sdf.format(new Date()).substring(8, 10);
		if(Integer.parseInt(dd) <= Integer.parseInt(month_begin)) {
			mm = Integer.parseInt(mm) -1 + "";
		}
		String date = yy+mm+"-"+month_begin;
		return dao.rank(date);
	}
	public MemberSo my(String member_no) {
		String month_begin = null;
		ILoginDao getTime = new LoginDao();
		month_begin = getTime.getTheStartOfMonth();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yy = sdf.format(new Date()).substring(0, 5);
		String mm = sdf.format(new Date()).substring(5, 7);
		String dd = sdf.format(new Date()).substring(8, 10);
		if(Integer.parseInt(dd) <= Integer.parseInt(month_begin)) {
			mm = Integer.parseInt(mm) -1 + "";
		}
		String date = yy+mm+"-"+month_begin;
		return dao.my(member_no, date);
	}

	public ArrayList perMonth(String member_no) {
		return dao.perMonth(member_no);
	}

	public int count(String startDate, String endDate, String state, String no,
			String extra_no, String extra_add_no) {
		return dao.count(startDate, endDate, state, no, extra_no, extra_add_no);
	}

	public ArrayList statistic(String member_no,String yearmonth) {
		ILoginDao getTime = new LoginDao();
		String month_begin = getTime.getTheStartOfMonth();
		if ("0".equals(yearmonth)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yyyy = sdf.format(new Date()).substring(0, 5);
			String mm = sdf.format(new Date()).substring(5, 7);
			String dd = sdf.format(new Date()).substring(8, 10);
			String mms = mm;
			String mme = null;
			
			if (Integer.parseInt(dd) <= Integer.parseInt(month_begin)) {
				mms = Integer.parseInt(mm) - 1 + "";
			}
			String begin_time = yyyy + mms + "-" + month_begin;
			mme = Integer.parseInt(mms) + 1 + "";
			String month_end = Integer.parseInt(month_begin) - 1 + "";
			String end_time = yyyy + mme + "-" + month_end;
			return dao.statistic(member_no,begin_time,end_time);
		}else {
			String yyyy = yearmonth.substring(0, 5);
			String mm = yearmonth.substring(5, 7);
			String begin_time = yyyy + mm + "-" + month_begin;
			String month_end = Integer.parseInt(month_begin) - 1 + "";
			String mme = Integer.parseInt(mm) + 1 +"";
			String end_time = yyyy + mme + "-" + month_end;
			
			return dao.statistic(member_no, begin_time, end_time);
		}
	}
	
}
