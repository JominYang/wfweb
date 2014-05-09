package com.wfcsu.wfweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.wfcsu.wfweb.dao.IAttendanceDao;
import com.wfcsu.wfweb.dao.ILoginDao;
import com.wfcsu.wfweb.dao.impl.AttendanceDao;
import com.wfcsu.wfweb.dao.impl.LoginDao;
import com.wfcsu.wfweb.service.IAttendanceService;
import com.wfcsu.wfweb.vo.AttendanceVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;

public class AttendanceService implements IAttendanceService{
	
	private IAttendanceDao dao;
	public AttendanceService(){
		dao = new AttendanceDao();
	}

	public int add(AttendanceVo vo) {
		return dao.add(vo);
	}

	public ArrayList get(int page, int rows, String order,
			String sort, AttendanceVo vo,String no) {
		return dao.get(page, rows, order, sort, vo,no);
	}

	public int count(AttendanceVo vo,String no) {
		return dao.count(vo,no);
	}

	public void del(String attendanceNo) {
		if (attendanceNo != null && !"".equals(attendanceNo)) {
			for(String no: attendanceNo.split(",")) {
				dao.del(no);
			}
		}
		
	}


	public boolean edit(AttendanceVo vo) {
		return dao.edit(vo);
	}
	public boolean goToDeal(AttendanceVo vo) {
		String date = vo.getRegister_time();
		if("".equals(date)||date==null||"null".equals(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(new Date());
			vo.setGet_time(currentTime);
		}
		return dao.goToDeal(vo);
	}

	public boolean back(AttendanceVo vo) {
		return dao.back(vo);
	}
	public boolean voidIt(String attendance_no){
		return dao.voidIt(attendance_no);
	}
	public boolean backToNotDeal(String attendance_no) {
		return dao.backToNotDeal(attendance_no);
	}
	
	
	
	
	public int addByClient(AttendanceVo vo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(new Date());
		vo.setRegister_time(currentTime);
		return dao.addByClient(vo);
	}

	public AttendanceVo get(String attendance_no) {
		return dao.get(attendance_no);
	}

	public boolean editClient(AttendanceVo vo) {
		return dao.editClient(vo);
	}

	public boolean changeType(AttendanceVo vo) {
		return dao.changeType(vo);
	}

	public ArrayList statisticAtten(StatisticAttenVo vo,String yearmonth) {
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
			return dao.statisticAtten(vo, begin_time, end_time);
		}else {
			String yyyy = yearmonth.substring(0, 5);
			String mm = yearmonth.substring(5, 7);
			String begin_time = yyyy + mm + "-" + month_begin;
			String month_end = Integer.parseInt(month_begin) - 1 + "";
			String mme = Integer.parseInt(mm) + 1 +"";
			String end_time = yyyy + mme + "-" + month_end;
			
			return dao.statisticAtten(vo, begin_time, end_time);
		}
	}

	public ArrayList statisticAttenAll(StatisticAttenVo vo) {
		return dao.statisticAttenAll(vo);
	}

	public MemberSo my(String member_no, String member_name) {
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
		return dao.myStatus(member_no, member_name, date);
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

	public ArrayList statisticByMonth(StatisticAttenVo vo) {
		return dao.statisticByMonth(vo);
	}
}
