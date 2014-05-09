package com.wfcsu.wfweb.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyTools {
	//比较两个时间的大小 时间格式为：HH:MM:SS
	public static int timeComparer(String s,String d) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(sdf.parse(s));
			c2.setTime(sdf.parse(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int result = c1.compareTo(c2);
		if(result>0) {
			return 1;
		}else if(result<0) {
			return -1;
		}else {
			return 0;
		}
	}
}
