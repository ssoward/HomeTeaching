package com.soward.object;

public class Keys {
    int pid;
    String keyRequestId;
    String keyType;
    String keyNum;
    

    
    public Keys(  ) {
        this.pid = 0;
        this.keyRequestId = "";
        this.keyType = "";
        this.keyNum = "";
    }
    public int getPid() {
        return pid;
    }
    public void setPid( int pid ) {
        this.pid = pid;
    }
    public String getKeyRequestId() {
        return keyRequestId;
    }
    public void setKeyRequestId( String keyRequestId ) {
        this.keyRequestId = keyRequestId;
    }
    public String getKeyType() {
        return keyType;
    }
    public void setKeyType( String keyType ) {
        this.keyType = keyType;
    }
    public String getKeyNum() {
        return keyNum;
    }
    public void setKeyNum( String keyNum ) {
        this.keyNum = keyNum;
    }
}
