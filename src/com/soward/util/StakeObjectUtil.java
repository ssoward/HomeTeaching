package com.soward.util;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.soward.db.DB;
import com.soward.object.StakeObjects;

public class StakeObjectUtil {
  
    
    public static List<StakeObjects> getObjsForType(String type){
        com.soward.db.DB db = new DB();
        List<StakeObjects> soList = new ArrayList<StakeObjects>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from stakeObjects where type='"+type+"'";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                StakeObjects so = new StakeObjects();
                so.setId( rset.getString("id"));
                so.setKey( rset.getString("key"));
                so.setValue( rset.getString("value"));
                so.setType( rset.getString("type"));
                soList.add(so);
            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return soList;
    }
    public static List<StakeObjects> getAll(){
        com.soward.db.DB db = new DB();
        List<StakeObjects> soList = new ArrayList<StakeObjects>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from stakeObjects order by type";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                StakeObjects so = new StakeObjects();
                so.setId( rset.getString("id"));
                so.setKey( rset.getString("key"));
                so.setValue( rset.getString("value"));
                so.setType( rset.getString("type"));
                soList.add(so);
            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return soList;
    }
    public static void save(StakeObjects so){
        com.soward.db.DB db = new DB();
        List<StakeObjects> soList = new ArrayList<StakeObjects>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "insert into stakeObjects "+//(id,key,value,type)"+
                " values (null, '"+
                so.getKey().replace( "'", "''" )+"', '"+
                so.getValue().replace( "'", "''" )+"', '"+
                so.getType().replace( "'", "''" )+"')";
            stm.executeUpdate(sql);
            conn.close();
            stm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteForId(String id){
        com.soward.db.DB db = new DB();
        List<StakeObjects> soList = new ArrayList<StakeObjects>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "delete from stakeObjects where id="+id;
            stm.executeUpdate(sql);
            conn.close();
            stm.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
