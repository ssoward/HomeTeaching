package com.soward.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SchedulerMaker {
    private static String bishop = "Bishop Miller";
    private static String firstC = "Jon Pitcher";
    private static String seconC = "Jeremy Brunner";
    static List<String> al = Arrays.asList( seconC, bishop, firstC );
    static List<String> fasting = Arrays.asList( firstC, seconC );
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    
    public static void createAnualSchedule(){
        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );
        int tithing = 0;
        int fast = 1;
        int month = 0;
        String currCond = null;
        int currMon = 0;
        System.out.println("Date,Conducting,Tithing");
        while(cal.get( Calendar.YEAR )<2011){
            if(currCond==null||currMon!=cal.get( Calendar.MONTH )){
                currCond = al.get( month );
                currMon = (cal.get( Calendar.MONTH ));
                month++;
            }
            String tithingMember = al.get(tithing);
            if(cal.get( Calendar.DAY_OF_MONTH ) < 8 ){
                fast = fast==0?1:0;
                tithingMember = "Fast: "+fasting.get( fast );
            }else
                tithing = tithing == 0?2:0;
                
            System.out.println(sdf.format( cal.getTime() )+","+ currCond+","+tithingMember);
            month = month==3?0:month;
            cal.add( Calendar.WEEK_OF_MONTH, 1 );
        }
    }
    public static void main(String args[]){
        SchedulerMaker.createAnualSchedule();
    }

}
