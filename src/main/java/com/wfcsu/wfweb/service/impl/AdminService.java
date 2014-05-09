package com.wfcsu.wfweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.wfcsu.wfweb.dao.IAdminDao;
import com.wfcsu.wfweb.dao.ILoginDao;
import com.wfcsu.wfweb.dao.impl.AdminDao;
import com.wfcsu.wfweb.dao.impl.LoginDao;
import com.wfcsu.wfweb.service.IAdminService;
import com.wfcsu.wfweb.vo.MemberVo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;

public class AdminService implements IAdminService{
	
	private IAdminDao dao;
	public AdminService(){
		dao = new AdminDao();
	}

	public int addMember(MemberVo vo) {
		return dao.addMember(vo);
	}

	public ArrayList<MemberVo> getMember(int page, int rows, String order,
			String sort, MemberVo vo) {
		return dao.getMember(page, rows, order, sort, vo);
	}

	public int countMember(MemberVo vo) {
		return dao.countMember(vo);
	}

	public void delMember(String nos) {
		if (nos != null && !"".equals(nos)) {
			for(String no: nos.split(",")) {
				dao.delMember(no);
			}
		}
	}
	public ArrayList<MemberVo> getMemberStatistic() {
		return dao.getMemberStatistic();
	}
	public ArrayList extrawork(StatisticAttenVo vo,String yearmonth) {
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
			return dao.extrawork(vo, begin_time,end_time);
		}else {
			String yyyy = yearmonth.substring(0, 5);
			String mm = yearmonth.substring(5, 7);
			String begin_time = yyyy + mm + "-" + month_begin;
			String month_end = Integer.parseInt(month_begin) - 1 + "";
			String mme = Integer.parseInt(mm) + 1 +"";
			String end_time = yyyy + mme + "-" + month_end;
			
			return dao.extrawork(vo, begin_time,end_time);
		}
	}

	public ArrayList duty(StatisticAttenVo vo,String yearmonth) {
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
			return dao.extrawork(vo, begin_time,end_time);
		}else {
			String yyyy = yearmonth.substring(0, 5);
			String mm = yearmonth.substring(5, 7);
			String begin_time = yyyy + mm + "-" + month_begin;
			String month_end = Integer.parseInt(month_begin) - 1 + "";
			String mme = Integer.parseInt(mm) + 1 +"";
			String end_time = yyyy + mme + "-" + month_end;
			
			return dao.extrawork(vo, begin_time,end_time);
		}
	}

	public boolean updateStartOfMonth(String start_month) {
		return dao.updateStartOfMonth(start_month);
	}

	public boolean dutyTime(String d1, String d2, String d3, String d4,
			String d5, String dh1, String dh2, String dh3, String dh4) {
		boolean flag = false;
		if(dao.dutyTime(d1, dh1, "DUTY_TIME1")&&dao.dutyTime(d2, dh2, "DUTY_TIME2")&&dao.dutyTime(d3, dh3, "DUTY_TIME3")&&dao.dutyTime(d4, dh4, "DUTY_TIME4")&&dao.dutyTime(d5, "null", "DUTY_TIME5")){
			flag = true;
		}
		return flag;
	}
}
