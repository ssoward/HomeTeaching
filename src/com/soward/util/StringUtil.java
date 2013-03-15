/**
 * Title: StringUtil.java
 * Description: HomeTeaching
 * Date: Jul 4, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StringUtil {

    /*
     * check that the string is not null
     * and has length greater than 0
     * @param strCandidate any string to test
     * @return boolean true if valid string ie. not null and with a length greater than 0
     */
    public static boolean isSet(String strCandidate) {
        boolean set = false;
        set = strCandidate!=null;
        set = strCandidate.length()>0;
        return set;
    }
    /*
     * check that the string is not null
     * and has length greater than 0
     * @param strCandidate any string to test
     * @return boolean true if valid string ie. not null and with a length greater than 0
     */
    public static boolean areSet(String str1, String str2) {
        boolean set = false;
        set = (isSet(str1)&&isSet(str2));
        return set;
    }
    public static String toReportString( double arg, int decimalPlaces ) {
        String f = "##0%";
        for ( int i = 0; i < decimalPlaces; i++ ) {
            f += ( i == 0 ) ? ".0" : "0";
        }
        return ( new DecimalFormat( f ) ).format( arg );
    }
    public static ArrayList<String> getMonths(){
        ArrayList<String> mm = new ArrayList<String>();
        mm.add( "Jan" );
        mm.add("Feb"); 
        mm.add("Mar");
        mm.add("Apr");
        mm.add("May");
        mm.add("Jun");
        mm.add("Jul");
        mm.add("Aug");
        mm.add("Sep");
        mm.add("Oct");
        mm.add("Nov");
        mm.add("Dec");
        return mm;
    }
    public static String bouncyCase(String input){
        StringTokenizer tok = new StringTokenizer(input);
        String total = "";
        if(tok.hasMoreTokens()){
            total = tok.nextToken().toLowerCase();
        }
        while(tok.hasMoreTokens()){

            total += StringUtil.capitalize( tok.nextToken());
        }
        return total;

    }
    public static String capitalize(String input)
    {
        if (input.length() == 0)
            return input;

        char ch = input.charAt(0);

        if (Character.isUpperCase(ch))
            return input;

        return String.valueOf(Character.toUpperCase(ch)) + input.substring(1);
    }
    public static void main(String args[]){
        System.out.println(StringUtil.bouncyCase( "scott soward" ));
    }



}
