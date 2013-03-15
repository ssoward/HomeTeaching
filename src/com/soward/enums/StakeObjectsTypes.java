package com.soward.enums;




public enum StakeObjectsTypes {

    WARD ( "ward", "Ward"),
    AUX ( "aux", "Aux"),
    BLDSNUM ( "bldsNum", "Blds Num"),
    LEADER ( "leader", "Leader"),
    REQR ("reqr", "Requestor"),
    CALLING("calling", "Calling"),
    REQRSTATUS("reqrStatus", "Request Status"),
    KEYTYPE("keyType", "Key Type");

    private String dbName;
    private String displayName;

    StakeObjectsTypes( String value, String disName ) {
        this.dbName = value;
        this.displayName = disName;
    }
    //returns eg. Murray
    public String displayName() { 
        return displayName; 
    }
    public String dbName() { 
        return dbName; 
    }
    
    //returns eg. 101
    //output: dbName: Products name: MURRAY account: 101 displayName: Murray
    public static void main( String args[] ) {
        for(StakeObjectsTypes lname : StakeObjectsTypes.values()){
            System.out.println( "dbName: "+lname.dbName()+" | name: "+lname.name()+" | displayName: "+lname.displayName() );
        }
//        dbName: ward | name: WARD | displayName: Ward
//        dbName: aux | name: AUX | displayName: Aux
//        dbName: bldsNum | name: BLDSNUM | displayName: Blds Num
//        dbName: leader | name: LEADER | displayName: Leader
//        dbName: reqr | name: REQR | displayName: Requestor
//        dbName: calling | name: CALLING | displayName: Calling
//        dbName: reqrStatus | name: REQRSTATUS | displayName: Request Status
//        dbName: keyType | name: KEYTYPE | displayName: Key Type
        
    }


}
