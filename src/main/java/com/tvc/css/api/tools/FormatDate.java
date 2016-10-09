package com.tvc.css.api.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 * @author mike 
 *
 */
public class FormatDate {
    /**
     * 将字符串转换成日期
     * @param ss
     * @throws ParseException 
     */
	public Date stringTodate(String ss) throws ParseException{
		DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date  date=format.parse(ss);
		return date;
	}

}
