package com.wfcsu.wfweb.dao;

import java.util.ArrayList;

import com.wfcsu.wfweb.vo.AttendanceVo;
import com.wfcsu.wfweb.vo.MemberSo;
import com.wfcsu.wfweb.vo.StatisticAttenVo;



public interface IAttendanceDao {
	
	public int add(AttendanceVo vo);
	public ArrayList get(int page, int rows, String order, String sort,AttendanceVo vo,String no);
	public int count(AttendanceVo vo,String no);
	public boolean del(String attendanceNo);
	public boolean edit(AttendanceVo vo);
	public boolean goToDeal(AttendanceVo vo);
	public boolean back(AttendanceVo vo);
	public boolean voidIt(String attendance_no);
	public boolean backToNotDeal(String attendance_no);
	public MemberSo myStatus(String member_no,String member_name,String bengin_date);
	public ArrayList rank(String begin_time);
	public int addByClient(AttendanceVo vo);
	public AttendanceVo get(String attendance_no);
	public boolean editClient(AttendanceVo vo);
	public boolean changeType(AttendanceVo vo);
	public ArrayList statisticAtten(StatisticAttenVo vo,String begin_time,String end_time);
	public ArrayList statisticAttenAll(StatisticAttenVo vo);
	public ArrayList statisticByMonth(StatisticAttenVo vo);

}
