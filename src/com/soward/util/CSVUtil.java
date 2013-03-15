package com.soward.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;

public class CSVUtil {
    public static void main(String args[]){
        try {
            Properties properties = genPropFieldsList("uploadData/mem.fields.properties");
            FileInputStream fis = new FileInputStream("./uploadData/Membership.csv");
            CSVUtil.transformCSVToList( fis, properties );
        } catch ( Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static List<Map<String,String>> transformCSVToList(InputStream fis, Properties properties) throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(fis));
        String [] nextLine;
        boolean headers = true;
        HashMap<String, String> colNames = new HashMap<String,String>();
        List<Map<String,String>> transMap = new ArrayList<Map<String,String>>();
        boolean flip = false;
        int count = 1;
        while ((nextLine = reader.readNext()) != null) {
            System.out.println(" ");
            int cc = 0;
            if(headers){
                for(String ss: nextLine){
                    colNames.put(cc+"", ss);
                    cc++;
                }
                headers = false;
            }else{
                for(String ss: nextLine){
                    if(!"Birth".equals (colNames.get(cc+""))){
                        if(ss!=null&&ss.length()>0){
                            System.out.print(""+colNames.get( cc+"" )+": "+ss+" ");
                        }
                    }
                    cc++;
                }
                count++;
            }
            System.out.println("");
            System.out.println("");
        }
        return null;
    }
    public static Properties genPropFieldsList(String path) throws IOException {
        Properties props = new Properties();
        String[] sList = null;
        props.load(new FileInputStream(path));
        return props;
    }
}