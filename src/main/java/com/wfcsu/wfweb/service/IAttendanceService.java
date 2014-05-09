package com.wfcsu.wfweb.service;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.AttendanceVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;

public interface IAttendanceService {

	public int add(AttendanceVo vo);
	public ArrayList get(int page, int rows, String order,
			String sort, AttendanceVo vo,String no);
	public int count(AttendanceVo vo,String no);
	public void del(String attendanceNo);
	public boolean edit(AttendanceVo vo);
	public boolean goToDeal(AttendanceVo vo);
	public boolean back(AttendanceVo vo);
	public boolean voidIt(String attendance_no);
	public boolean backToNotDeal(String attendance_no);
	public MemberSo my(String member_no,String member_name);
	public ArrayList rank();
	public int addByClient(AttendanceVo vo);
	
	public AttendanceVo get(String attendance_no);
	public boolean editClient(AttendanceVo vo);
	public boolean changeType(AttendanceVo vo);
	public ArrayList statisticAtten(StatisticAttenVo vo,String yearmonth);
	public ArrayList statisticAttenAll(StatisticAttenVo vo);
	public ArrayList statisticByMonth(StatisticAttenVo vo);
}


