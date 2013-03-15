package com.soward.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.soward.db.DB;
import com.soward.object.Keys;

public class KeysUtil {



    public static ArrayList<Keys> getForRequestId(String requestId){
        com.soward.db.DB db = new DB();
        ArrayList<Keys> kList = new ArrayList<Keys>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from churchkeys where keyRequestId='"+requestId+"'";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                Keys so = new Keys();
                so.setPid( rset.getInt("pid"));
                so.setKeyRequestId( rset.getString("keyRequestId"));
                so.setKeyType(rset.getString(       "keyType"));    
                so.setKeyNum(rset.getString(        "keyNum")); 
                kList.add( so );
            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return kList;
    }
    public static Keys getForId(int pid){
        com.soward.db.DB db = new DB();
        Keys so = new Keys();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from churchkeys where pid="+pid;
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                so.setPid( rset.getInt("pid"));
                so.setKeyRequestId( rset.getString("keyRequestId"));
                so.setKeyType(rset.getString(       "keyType"));    
                so.setKeyNum(rset.getString(        "keyNum"));     

            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return so;
    }
    //    public static String saveForRequest(HttpServletRequest request){
    //        Keys so = new Keys();
    //        String isNew = request.getParameter( "newKeys");
    //        boolean update = false;
    //        if(isNew!=null&&isNew.equals( "false" )){
    //            try{
    //                so.setId( Integer.parseInt((String) request.getParameter( "oldPid")));
    //                update = true;
    //            }catch(Exception e){
    //
    //            }
    //        }
    //    
    //        so.setKeyType(request.getParameter(    "keyType"));    
    //        so.setKeyNum(request.getParameter(     "keyNum"));     
    //        try {
    //            if(update){
    //                return update(so);
    //            }else{
    //                return save(so);
    //            }
    //        }catch(Exception e){
    //            e.printStackTrace();
    //        }
    //        return "An error ocurred in saveForRequest.";
    //    }
    public static String save(ArrayList<Keys> soList, String requestId){
        String msg = "";
        try{
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            for(Keys so: soList){
                String sql = "insert into churchkeys (pid,"+
                "keyRequestId, keyType, keyNum) values(null,"+
                "'"+requestId+"', "+
                "'"+so.getKeyType      ()+"', "+
                "'"+so.getKeyNum       ()+"' )";
                stm.executeUpdate( sql);
            }
            msg = "Saved successful.";
            conn.close();
            stm.close();
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }
    public static String update(Keys so){
        String msg = "";
        try{
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "update churchkeys set "+
            "keyRequestId ='"+so.getKeyRequestId ()+"', "+
            "keyType    = '"+so.getKeyType      ()+"', "+
            "keyNum     = '"+so.getKeyNum       ()+"' "+
            "where id = "+so.getPid();
            stm.executeUpdate( sql);
            msg = "Update Successful.";
            conn.close();
            stm.close();
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }
    public static String deleteId(String pid){
        String msg = "";
        try{
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "delete from churchkeys where pid = "+pid;
            stm.executeUpdate( sql);
            msg = "Delete Successful.";
            conn.close();
            stm.close();
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }
    public static List<Keys> getAll(){
        List<Keys> kList = new ArrayList<Keys>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from churchkeys";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                Keys so = new Keys();
                so.setPid( rset.getInt("pid"));
                so.setKeyRequestId( rset.getString("keyRequestId"));
                so.setKeyType(rset.getString(       "keyType"));    
                so.setKeyNum(rset.getString(        "keyNum"));    
                kList.add( so );
            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return kList;
    }
}
