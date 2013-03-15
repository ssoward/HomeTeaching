package com.soward.object;

import java.util.ArrayList;
import java.util.Date;

public class KeyRequest {
    
    int id;
    String reqrName;
    String reqrCalling;
    String createdDate;
    String recFirst;
    String recLast;
    String recPhone;
    String recAux;
    String recCalling;
    String ward;
    String bldNum;
    String bishop;
    ArrayList<Keys> keys;
    String username;
    String reqrStatus;
    
    public KeyRequest() {
        this.id = 0;
        this.reqrName = "";
        this.reqrCalling = "";
        this.createdDate = "";
        this.recFirst = "";
        this.recLast = "";
        this.recPhone = "";
        this.recAux = "";
        this.recCalling = "";
        this.ward = "";
        this.bldNum = "";
        this.bishop = "";
        this.keys = new ArrayList<Keys>();
        this.username = ""; 
        this.reqrStatus = "";
            
    }

    public String getReqrStatus() {
        return reqrStatus!=null?reqrStatus:"";
    }
    public void setReqrStatus( String reqrStatus ) {
        this.reqrStatus = reqrStatus;
    }
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }
    
    public String getReqrName() {
        return reqrName!=null?reqrName:"";
    }
    public void setReqrName( String reqrName ) {
        this.reqrName = reqrName;
    }
    public String getReqrCalling() {
        return reqrCalling!=null?reqrCalling:"";
    }
    public void setReqrCalling( String reqrCalling ) {
        this.reqrCalling = reqrCalling;
    }
    public String getCreatedDate() {
        return createdDate!=null?createdDate:"";
    }
    public void setCreatedDate( String createdDate ) {
        this.createdDate = createdDate;
    }
    public String getRecFirst() {
        return recFirst!=null?recFirst:"";
    }
    public void setRecFirst( String recFirst ) {
        this.recFirst = recFirst;
    }
    public String getRecLast() {
        return recLast!=null?recLast:"";
    }
    public void setRecLast( String recLast ) {
        this.recLast = recLast;
    }
    public String getRecPhone() {
        return recPhone!=null?recPhone:"";
    }
    public void setRecPhone( String recPhone ) {
        this.recPhone = recPhone;
    }
    public String getRecAux() {
        return recAux!=null?recAux:"";
    }
    public void setRecAux( String recAux ) {
        this.recAux = recAux;
    }
    public String getRecCalling() {
        return recCalling!=null?recCalling:"";
    }
    public void setRecCalling( String recCalling ) {
        this.recCalling = recCalling;
    }
    public String getWard() {
        return ward!=null?ward:"";
    }
    public void setWard( String ward ) {
        this.ward = ward;
    }
    public String getBldNum() {
        return bldNum!=null?bldNum:"";
    }
    public void setBldNum( String bldNum ) {
        this.bldNum = bldNum;
    }
    public String getBishop() {
        return bishop!=null?bishop:"";
    }
    public void setBishop( String bishop ) {
        this.bishop = bishop;
    }
    public ArrayList<Keys> getKeys() {
        return keys;
    }
    public void setKeys( ArrayList<Keys> keys ) {
        this.keys = keys;
    }
    public String getUsername() {
        return username!=null?username:"";
    }
    public void setUsername( String username ) {
        this.username = username;
    }

}
