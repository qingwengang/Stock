package com.ganggang.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String GetStringByDate(Date date){
		return sdf.format(date);
	}
	public static Date GetDateByString(String date) throws ParseException{
		return sdf.parse(date);
	}
	public static Date AddDate(Date date ,int days){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		Date result=c.getTime();
		return result;
	}
	public static void main(String[] args) throws ParseException{
		System.out.println(AddDate(GetDateByString("2014-01-01"), 3));
	}
}