package com.storeorderingsystem.storeorderingsystem.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DateUtils {

	public static Date createDateFromDateString(String dateString){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        if(StringUtils.hasText(dateString)){
           try{
               date = format.parse(dateString);
           } catch (ParseException e) {
               date = new Date();
           }
        }else{
            date = new Date();
        }
        return date;
    }
	
    public static boolean isDateGreatThanTwoYears(Date date){
    	
 
    	GregorianCalendar calToday = new GregorianCalendar();
    	GregorianCalendar twoYearAgoTodayAtMidnight = new GregorianCalendar(calToday.get(Calendar.YEAR) - 2, 
    			calToday.get(Calendar.MONTH), calToday.get(Calendar.DATE));
    	return true;
     }
    
}