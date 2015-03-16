package com.ganggang.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String GetStringByDate(Date date){
		return sdf.format(date);
	}
}