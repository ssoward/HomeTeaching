package com.soward.util;


import java.io.File;
import java.util.*;
/***************************************************************** ******************************
 * This program finds the actual content size of the file not the size of the file in the disk.
 * Eg: Check the 1 Byte text file for its actual content size and the size of that same file in the
 * disk.
 ******************************************************************************* ****************/

public class FindFileSize
{
    static double fileSizeKB;



    public static String getFindFileSize(String str)
    {
        /**Specify the location and name of the file whose size is to be found **/

        File f = new File(str);

        String fileLength = String.valueOf(f.length());
        int fileLengthDigitCount = fileLength.length();
        double fileLengthLong = f.length();
        double decimalVal = 0.0;
        String howBig = "";

        //System.out.println("fileLengthDigitCount is..."+fileLengthDigitCount);

        if(f.length()>0)
        {
            if(fileLengthDigitCount < 5)
            {
                fileSizeKB = Math.abs(fileLengthLong);
                howBig = "Byte(s)";
            }
            else if(fileLengthDigitCount >= 5 && fileLengthDigitCount <=6)
            {
                fileSizeKB = Math.abs((fileLengthLong/1024));
                howBig = "KB";
            }
            else if(fileLengthDigitCount >= 7 && fileLengthDigitCount <= 9)
            {
                fileSizeKB = Math.abs(fileLengthLong/(1024*1024));
                howBig = "MB";
            }
            else if(fileLengthDigitCount >9)
            {
                fileSizeKB = Math.abs((fileLengthLong/(1024*1024*1024)));
                decimalVal = fileLengthLong%(1024*1024*1024);
                howBig = "GB";
            }
        }
       // System.out.println("....bytes....."+fileSizeKB);
        String finalResult = getRoundedValue(fileSizeKB);
        System.out.println("\n....Final Result....."+finalResult+" "+howBig);
        return finalResult;
    }

    private static String getRoundedValue(double decimalVal)
    {
//        System.out.println("\nThe first call......."+decimalVal);

        long beforeDecimalValue = decimalTokenize(decimalVal,1);
        long afterDecimalValue = decimalTokenize(decimalVal,2);
        long decimalValueLength = String.valueOf(afterDecimalValue).length();
        long dividerVal = divider(decimalValueLength-1);
        long dividedValue = afterDecimalValue/dividerVal;
        String finalResult=String.valueOf(beforeDecimalValue)+"."+String.valueOf(dividedValue) ;

//        System.out.println("\nfinalResult......."+finalResult);

        return finalResult;
    }

    private static long divider(long argLength)
    {
        long varDivider=1;

        for(int i=0;i<(argLength-1);i++)
        {
            varDivider=varDivider*10;
        }

        return varDivider;
    }

    private static long decimalTokenize(double decimalVal,int position)
    {

        long returnDecimalVal=0;
        String strDecimalVal="";

        if(decimalVal >0)
            strDecimalVal = String.valueOf(decimalVal);

        if(strDecimalVal.length()>0)
        {
            StringTokenizer decimalToken = new StringTokenizer(strDecimalVal,".");
//          System.out.print("\n String tokenized successfully"+decimalToken.countTokens());
//          int count = decimalToken.countTokens();

            if(position==1)
            {
                returnDecimalVal = Long.parseLong(decimalToken.nextToken());
            }
            else if(position==2)
            {
                decimalToken.nextToken();
                returnDecimalVal = Long.parseLong(decimalToken.nextToken());
            }
        }
        return returnDecimalVal;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        FindFileSize findFileSize = new FindFileSize();
        System.out.println(findFileSize.getFindFileSize( "/Users/scottsoward/Movies/MyGreatMovie.m4v" ));
    }
} 