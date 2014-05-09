package com.wfcsu.wfweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wfcsu.wfweb.common.MyTools;
import com.wfcsu.wfweb.dao.ILoginDao;
import com.wfcsu.wfweb.dao.impl.LoginDao;
import com.wfcsu.wfweb.service.ILoginService;
import com.wfcsu.wfweb.vo.MemberVo;

public class LoginService implements ILoginService{
	
	 private ILoginDao dao;
	   public LoginService(){ 
		   dao = new LoginDao();
	   }

	public MemberVo login(String no, String psw) {
		return dao.login(no, psw);
	}

	public String signIn(String member_no,String number) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date()).substring(0, 10);
		String time = sdf.format(new Date()).substring(11, 19);
		//先判断是值班时间
		if(MyTools.timeComparer(dao.getDutyTime("DUTY_TIME1"),time)==-1&&MyTools.timeComparer(dao.getDutyTime("DUTY_TIME5"), time)==1) {
			//1、签到成功   1.已到值班时间，签到成功 2.前一个班已提前结束值班，签到成功
			//判断前一个班是否结束
			String before = dao.judgeBeforeDutyer();
			System.out.println("before....."+before);
			if("".equals(before)||before==null) {
				//判断这个班是否已经有人签 了？
				String current = dao.judgeCurrentDutyer(number,date);
				if("".equals(current)||current==null) {
					//没人
					if(dao.signIn(member_no,number,date)) {
						return "success";
					}else {
						return "error";//"没人有值班的权限，这个班没人值不一定是签到的时间可以签到，但是就是要出错";
					}
					
				} else {
					//有人了
					return current;//"这个班已经签过了，虽然他现在不是值班队员权限"+current;
				}
			}else {
				//前一个班没结束 判断是否应该是我值班的时间段，并判断没有人签这个班
				if("1".equals(number)) {
					String current=null;
					if(MyTools.timeComparer(dao.getDutyTime("DUTY_TIME1"),time)==-1&&MyTools.timeComparer(dao.getDutyTime("DUTY_TIME2"), time)==1) {
						current = dao.judgeCurrentDutyer(number,date);
						if("".equals(current)||current==null) {
							if(dao.signIn(member_no,number,date)) {
								return "success";
							}else {
								return "error";//"有人有值班的权限，他到时间了，这个班没人值而且是签到的时间，但是就是要出错";
							}
						} else{
							return current;//"这个班他正在值"+current+".......你还签什么";
						}
					} else {
						return "notTime";//"不是这个班签到时间，有人现在有权限"+dao.judgeBeforeDutyer();
					}
				}else if("2".equals(number)) {
					String current=null;
					if(MyTools.timeComparer(dao.getDutyTime("DUTY_TIME2"),time)==-1&&MyTools.timeComparer(dao.getDutyTime("DUTY_TIME3"), time)==1) {
						current = dao.judgeCurrentDutyer(number,date);
						if("".equals(current)||current==null) {
							if(dao.signIn(member_no,number,date)) {
								return "success";
							}else {
								return "error";//"有人有值班的权限，他到时间了，这个班没人值而且是签到的时间，但是就是要出错";
							}
						} else{
							return current;//"这个班他正在值"+current+".......你还签什么";
						}
					} else {
						return "notTime";//"不是这个班签到时间要提前签到则她还没下班"+dao.judgeBeforeDutyer();
					}
				}else if("3".equals(number)) {
					String current=null;
					if(MyTools.timeComparer(dao.getDutyTime("DUTY_TIME3"),time)==-1&&MyTools.timeComparer(dao.getDutyTime("DUTY_TIME4"), time)==1) {
						current = dao.judgeCurrentDutyer(number,date);
						if("".equals(current)||current==null) {
							if(dao.signIn(member_no,number,date)) {
								return "success";
							}else {
								return "error";//"有人有值班的权限，他到时间了，这个班没人值而且是签到的时间，但是就是要出错";
							}
						} else{
							return current;//"这个班他正在值"+current+".......你还签什么";
						}
					} else {
						return "notTime";//"不是这个班签到时间要提前签到则她还没下班"+dao.judgeBeforeDutyer();
					}
				}else if("4".equals(number)) {
					String current=null;
					if(MyTools.timeComparer(dao.getDutyTime("DUTY_TIME4"),time)==-1&&MyTools.timeComparer(dao.getDutyTime("DUTY_TIME5"), time)==1) {
						current = dao.judgeCurrentDutyer(number,date);
						if("".equals(current)||current==null) {
							if(dao.signIn(member_no,number,date)) {
								return "success";
							}else {
								return "error";//"有人有值班的权限，他到时间了，这个班没人值而且是签到的时间，但是就是要出错";
							}
						} else{
							return current;//"这个班他正在值"+current+".......你还签什么";
						}
					} else {
						return "notTime";//"不是这个班签到时间要提前签到则她还没下班"+dao.judgeBeforeDutyer();
					}
				}else {
					return "error";
				}
				
			}
		}else {
			return "notDuryTime";//"网服不会没日没夜的哦";
		}
	}
	public String signOut(String member_no,String number) {
		return null;
	}
	public String getCurrentDutyer() {
		return dao.judgeBeforeDutyer();
	}

	public boolean signOut() {
		return dao.signOut();
	}
}
