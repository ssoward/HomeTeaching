package com.soward.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.soward.db.DB;
import com.soward.object.LeaderKeys;

public class LeaderKeysUtil {


    public static LeaderKeys getForId(int pid){
        com.soward.db.DB db = new DB();
        LeaderKeys so = new LeaderKeys();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from leaderKeys where pid="+pid;
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                so.setPid( rset.getInt("pid"));
                so.setLeaderId( rset.getString("leaderId"));
                so.setKeyId(rset.getString(       "keyId"));    
                so.setKeyCount(rset.getInt(        "keyCount"));     

            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return so;
    }
    public static String save(LeaderKeys lk){
        ArrayList<LeaderKeys> al = new ArrayList<LeaderKeys>();
        al.add( lk );
        return saveAll( al );
    }

    public static String saveAll(ArrayList<LeaderKeys> soList){
        String msg = "";
        try{
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            for(LeaderKeys so: soList){
                String sql = "insert into leaderKeys (pid,"+
                "leaderId, keyId, keyCount) values(null,"+
                "'"+so.getLeaderId()+"', "+
                "'"+so.getKeyId   ()+"', "+
                "'"+so.getKeyCount()+"' )";
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
    public static String update(LeaderKeys so){
        String msg = "";
        try{
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "update leaderKeys set "+
            "leaderId ='"+so.getLeaderId ()+"', "+
            "keyId    = '"+so.getKeyId      ()+"', "+
            "keyCount     = '"+so.getKeyCount       ()+"' "+
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
            String sql = "delete from leaderKeys where pid = "+pid;
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
    public static HashMap<String, ArrayList<LeaderKeys>> getHashForLeadersId(){
        List<LeaderKeys> arrayLk = getAll();
        HashMap<String, ArrayList<LeaderKeys>> lkHash = new HashMap<String, ArrayList<LeaderKeys>>();
        for(LeaderKeys kk: arrayLk){
            if(lkHash.containsKey( kk.getLeaderId() )){
                lkHash.get( kk.getLeaderId() ).add( kk );
            }else{
                ArrayList al = new ArrayList();
                al.add( kk );
                lkHash.put( kk.getLeaderId(), al );

            }
        }
        return lkHash;
    }
    public static List<LeaderKeys> getAll(){
        List<LeaderKeys> kList = new ArrayList<LeaderKeys>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from leaderKeys";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                LeaderKeys so = new LeaderKeys();
                so.setPid( rset.getInt("pid"));
                so.setLeaderId( rset.getString("leaderId"));
                so.setKeyId(rset.getString(       "keyId"));    
                so.setKeyCount(rset.getInt(        "keyCount"));    
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
    public static List<LeaderKeys> getAllForLeaderId(String leaderId){
        List<LeaderKeys> kList = new ArrayList<LeaderKeys>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from leaderKeys where leaderId="+leaderId;
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                LeaderKeys so = new LeaderKeys();
                so.setPid( rset.getInt("pid"));
                so.setLeaderId( rset.getString("leaderId"));
                so.setKeyId(rset.getString(       "keyId"));    
                so.setKeyCount(rset.getInt(        "keyCount"));    
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
