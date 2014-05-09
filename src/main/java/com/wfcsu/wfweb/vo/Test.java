package com.wfcsu.wfweb.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfcsu.wfweb.common.MyTools;


public class Test {
	public static void main(String[] args) {
//		Map m = new HashMap();
//		List l = new ArrayList();
//		
//		l.add("xxx");
//		m.put("l1", l);
//		
//		l = (List) m.get("l1");
//		l.add("yyy");
//		
//		System.out.println("size:"+l.size());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String yy = sdf.format(new Date()).substring(0, 5);
//		String mm = sdf.format(new Date()).substring(5, 7);
//		String dd = sdf.format(new Date()).substring(8, 10);
//		if(Integer.parseInt(dd) <= 18) {
//			mm = Integer.parseInt(mm) -1 + "";
//		}
//		String date = yy+mm+"-"+dd;
//		System.out.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//		System.out.println(sdf.format(new Date()));
//		Calendar c1 = Calendar.getInstance();
//		Calendar c2 = Calendar.getInstance();
//		try {
//			c1.setTime(sdf.parse("22:04:50"));
//			c2.setTime(sdf.parse("22:04:51"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		int result = c1.compareTo(c2);
//		if(result==0) {
//			System.out.println("=");
//		} else if(result <0) {
//			System.out.println("<");
//		} else {
//			System.out.println(">");
//		}
		System.out.println(MyTools.timeComparer("7:11:13", "18:11:12"));
	}
	public int timeComparer(String s,String d) {
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
