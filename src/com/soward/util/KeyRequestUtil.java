package com.soward.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.soward.db.DB;
import com.soward.object.KeyRequest;
import com.soward.object.Keys;
import com.soward.object.StakeObjects;

public class KeyRequestUtil {



    public static KeyRequest getForId(String id){
        com.soward.db.DB db = new DB();
        KeyRequest so = new KeyRequest();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from keyRequest where id='"+id+"'";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                so.setId( rset.getInt("id"));
                so.setCreatedDate(rset.getString(   "createdDate"));
                so.setReqrName(rset.getString(      "reqrName"));   
                so.setReqrCalling(rset.getString(   "reqrCalling"));
                so.setRecFirst(rset.getString(      "recFirst"));   
                so.setRecLast(rset.getString(       "recLast"));    
                so.setRecPhone(rset.getString(      "recPhone"));   
                so.setRecAux(rset.getString(        "recAux"));     
                so.setRecCalling(rset.getString(    "recCalling")); 
                so.setWard(rset.getString(          "ward"));       
                so.setBldNum(rset.getString(        "bldNum"));     
                so.setBishop(rset.getString(        "bishop"));     
                so.setUsername(rset.getString(        "username"));     
                so.setReqrStatus( rset.getString( "reqrStatus"));     
                so.setKeys( KeysUtil.getForRequestId( id+"" ));  

            }
            conn.close();
            stm.close();
            rset.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return so;
    }
    public static String saveForRequest(HttpServletRequest request){
        KeyRequest so = new KeyRequest();
        String isNew = request.getParameter( "newKeyRequest");
        boolean update = false;
        if(isNew!=null&&isNew.equals( "false" )){
            try{
                so.setId( Integer.parseInt((String) request.getParameter( "oldPid")));
                update = true;
            }catch(Exception e){

            }
        }
        so.setCreatedDate(request.getParameter("createdDate"));
        so.setReqrName(request.getParameter(   "reqrName"));   
        so.setReqrCalling(request.getParameter("reqrCalling"));
        so.setRecFirst(request.getParameter(   "recFirst"));   
        so.setRecLast(request.getParameter(    "recLast"));    
        so.setRecPhone(request.getParameter(   "recPhone"));   
        so.setRecAux(request.getParameter(     "recAux"));     
        so.setRecCalling(request.getParameter( "recCalling")); 
        so.setWard(request.getParameter(       "ward"));       
        so.setBldNum(request.getParameter(     "bldNum"));     
        so.setBishop(request.getParameter(     "bishop"));     
        so.setReqrStatus( request.getParameter( "reqrStatus"));     
        so.setUsername((String)request.getSession().getAttribute( "username" ));   

        try {

            ArrayList<Keys> newKeyList = new ArrayList();
            for(int i=0; i<100; i++)
            {
                String addedKeyType = request.getParameter("addedKeyType"+i);
                String addedKeyNum = request.getParameter("addedKeyNum"+i);
                if(addedKeyNum != null && addedKeyNum.length()>0 ||
                        addedKeyType != null && addedKeyType.length()>0     )
                {
                    Keys newKey = new Keys();
                    newKey.setKeyRequestId( so.getId()+"" );
                    newKey.setKeyType( addedKeyType );
                    newKey.setKeyNum( addedKeyNum );
                    newKeyList.add(newKey);
                }
            }
            so.setKeys( newKeyList);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        //        so.setKeyType(request.getParameter(    "keyType"));    
        //        so.setKeyNum(request.getParameter(     "keyNum"));     
        try {
            if(update){
                return update(so);
            }else{
                return save(so);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "An error ocurred in saveForRequest.";
    }
    public static String save(KeyRequest so){
        String msg = "";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try{
            com.soward.db.DB db = new DB();
            conn = db.openConnection();
            String sql = "insert into keyRequest (id,"+
            "reqrName ,reqrCalling ,createdDate ,recFirst ,recLast ,recPhone"+
            ",recAux ,recCalling ,ward ,bldNum ,bishop ,reqrStatus, username )"+ 
            " values(null, ?, ?, now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, so.getReqrName     () );
            pstmt.setString( 2, so.getReqrCalling  () );
            pstmt.setString( 3, so.getRecFirst     () );
            pstmt.setString( 4, so.getRecLast      () ); 
            pstmt.setString( 5, so.getRecPhone     () ); 
            pstmt.setString( 6, so.getRecAux       () ); 
            pstmt.setString( 7, so.getRecCalling   () ); 
            pstmt.setString( 8, so.getWard         () ); 
            pstmt.setString( 9, so.getBldNum       () ); 
            pstmt.setString( 10,so.getBishop       () ); 
            pstmt.setString( 11,so.getReqrStatus   () ); 
            pstmt.setString( 12,so.getUsername     () ); 

            pstmt.executeUpdate();
            ResultSet rset = pstmt.getGeneratedKeys();

            if(rset.next()){
                so.setId( rset.getInt( 1 ) );
            }
            KeysUtil.save( so.getKeys(), so.getId()+"" );
            msg = "Saved successful.";
            conn.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        finally{
            try {
                conn.close(); 
                pstmt.close();
            } catch ( SQLException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return msg;
    }
    public static String update(KeyRequest so){
        String msg = "";
        try{
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "update keyRequest set "+

            "reqrName   = '"+so.getReqrName     ()+"', "+ 
            "reqrCalling= '"+so.getReqrCalling  ()+"', "+
            "createdDate= '"+so.getCreatedDate  ()+"', "+
            "recFirst   = '"+so.getRecFirst     ()+"', "+
            "recLast    = '"+so.getRecLast      ()+"', "+
            "recPhone   = '"+so.getRecPhone     ()+"', "+
            "recAux     = '"+so.getRecAux       ()+"', "+
            "recCalling = '"+so.getRecCalling   ()+"', "+
            "ward       = '"+so.getWard         ()+"', "+
            "bldNum     = '"+so.getBldNum       ()+"', "+
            "reqrStatus     = '"+so.getReqrStatus       ()+"', "+
            "bishop     = '"+so.getBishop       ()+"' "+
            "where id = "+so.getId();
            stm.executeUpdate( sql);
            KeysUtil.save( so.getKeys(), so.getId()+"" ); 
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
            String sql = "delete from keyRequest where id = "+pid;
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
    public static List<KeyRequest> getAll(){
        List<KeyRequest> kList = new ArrayList<KeyRequest>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from keyRequest";
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                KeyRequest so = new KeyRequest();
                so.setId( rset.getInt("id"));
                so.setCreatedDate(rset.getString(   "createdDate"));
                so.setReqrName(rset.getString(      "reqrName"));   
                so.setReqrCalling(rset.getString(   "reqrCalling"));
                so.setRecFirst(rset.getString(      "recFirst"));   
                so.setRecLast(rset.getString(       "recLast"));    
                so.setRecPhone(rset.getString(      "recPhone"));   
                so.setRecAux(rset.getString(        "recAux"));     
                so.setRecCalling(rset.getString(    "recCalling")); 
                so.setWard(rset.getString(          "ward"));       
                so.setBldNum(rset.getString(        "bldNum"));     
                so.setBishop(rset.getString(        "bishop"));     
                so.setReqrStatus(rset.getString( "reqrStatus"));     
                so.setUsername(rset.getString(       "userName")); 
                so.setKeys( KeysUtil.getForRequestId( so.getId()+"" ));
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
    public static List<KeyRequest> search(KeyRequest kreq, String dateOne, String dateTwo){
        List<KeyRequest> kList = new ArrayList<KeyRequest>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat sdff = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            Date dOne = sdff.parse( dateOne );
            Date dTwo = sdff.parse( dateTwo );
            String sql = "select * from keyRequest where createdDate>'"+sdf.format( dOne )+"' and createdDate<'"+sdf.format( dTwo )+"'";
            if(kreq.getBishop().length()>0){
                sql += " and bishop ='"+kreq.getBishop()+"'";
            }
            if(kreq.getReqrName().length()>0){
                sql += " and reqrName = '"+kreq.getReqrName()+"'";
            }
            if(kreq.getWard().length()>0){
                sql += " and ward = '"+kreq.getWard()+"'";
            }
            if(kreq.getReqrStatus().length()>0){
                sql += " and reqrStatus = '"+kreq.getReqrStatus()+"'";
            }
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                KeyRequest so = new KeyRequest();
                so.setId( rset.getInt("id"));
                so.setCreatedDate(rset.getString(   "createdDate"));
                so.setReqrName(rset.getString(      "reqrName"));   
                so.setReqrCalling(rset.getString(   "reqrCalling"));
                so.setRecFirst(rset.getString(      "recFirst"));   
                so.setRecLast(rset.getString(       "recLast"));    
                so.setRecPhone(rset.getString(      "recPhone"));   
                so.setRecAux(rset.getString(        "recAux"));     
                so.setRecCalling(rset.getString(    "recCalling")); 
                so.setWard(rset.getString(          "ward"));       
                so.setBldNum(rset.getString(        "bldNum"));     
                so.setBishop(rset.getString(        "bishop"));     
                so.setReqrStatus(rset.getString( "reqrStatus"));     
                so.setUsername(rset.getString(       "userName")); 
                so.setKeys( KeysUtil.getForRequestId( so.getId()+"" ));
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
