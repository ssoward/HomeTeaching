package com.soward.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.soward.db.DB;
import com.soward.domain.FamilyDomain;
import com.soward.domain.MemberDomain;
import com.soward.object.Member;

public class NinthWardDBMigration {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        NinthWardDBMigration.runHTUpdate();
    }
    public static void runHTUpdate(){
        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            String sql = "";
            sql = "select * from test";// where Preferred_Name like '%oward%'";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery(sql);
            HashMap<String, HashMap<String, String>> htList = new HashMap<String, HashMap<String, String>>();
            while (rset.next()) {
                String preferredName = rset.getString( "Full_Name" );
                String Head_of_House = rset.getString("Head_of_House");

                String lastName = preferredName.substring( 0,preferredName.indexOf( ',' ));

                String Preferred_Name                    = rset.getString("Preferred_Name");
                String Address_City                      = rset.getString("Address_City");
                String Address_Street_1                  = rset.getString("Address_Street_1");
                String Address_Street_2                  = rset.getString("Address_Street_2");
                String Age                               = rset.getString("Age");
                String Birth_Date                        = rset.getString("Birth_Date");
                String Email_Address                     = rset.getString("Email_Address");
                String Full_Name                         = rset.getString("Full_Name");
                String Home_Teaching_Companions          = rset.getString("Home_Teaching_Companions");
                String Household_Position                = rset.getString("Household_Position");
                String Is_Home_Teacher                   = rset.getString("Is_Home_Teacher");
                String Priesthood                        = rset.getString("Priesthood");
                String Primary_Phone_Number              = rset.getString("Primary_Phone_Number");
                String Priesthood_Office                 = rset.getString("Priesthood_Office");
                String Spouse_Name                       = rset.getString("Spouse_Name");
                String Confirmation_Date                 = rset.getString("Confirmation_Date");
                String Birthday                          = rset.getString("Birthday");
                String Callings                          = rset.getString("Callings");
                String Has_Children                      = rset.getString("Has_Children");
                String Head_of_House_and_Spouse          = rset.getString("Head_of_House_and_Spouse");
                String Home_Teachers                     = rset.getString("Home_Teachers");
                String Home_Teachers_with_phone_numbers  = rset.getString("Home_Teachers_with_phone_numbers");
                String Is_Married                        = rset.getString("Is_Married");
                String Is_Member                         = rset.getString("Is_Member");
                String Is_Single                         = rset.getString("Is_Single");
                String Is_Widowed                        = rset.getString("Is_Widowed");
                String Sex                               = rset.getString("Sex");
                String Spouse_of_Head_of_House           = rset.getString("Spouse_of_Head_of_House");
                //System.out.println(Head_of_House+" --> "+ Home_Teachers);
                if(htList.containsKey( Home_Teachers)){
                    htList.get( Home_Teachers ).put( Head_of_House, "");
                }else{
                    HashMap alist = new HashMap();
                    alist.put( Head_of_House, "" );
                    htList.put( Home_Teachers, alist );
                }
            }
            Set set = htList.keySet();
            Iterator iter = set.iterator();
            while(iter.hasNext()){
                String key = (String)iter.next();
                System.out.println(key);
                HashMap temp= htList.get( key );
                Set sett = temp.keySet();
                Iterator iterr = sett.iterator();
                while(iterr.hasNext()){
                    System.out.println("    -"+iterr.next());
                }
            }
            stm.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void runMigration(){
//      sql used to upload memebers into a table, need to add columns to the test table.
//      LOAD DATA INFILE '/Users/scottsoward/Desktop/AllMembers.csv' INTO TABLE test FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;
        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            String sql = "";
            sql = "select * from test";// where Preferred_Name like '%oward%'";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery(sql);
            int count = 0;
            String headName = "";
            
            ArrayList<Member> siblings = new ArrayList<Member>();
            HashMap<String, Family> famHash = new HashMap<String, Family>();
            while (rset.next()) {
                String preferredName = rset.getString( "Full_Name" );
                String Head_of_House = rset.getString("Head_of_House");
                String firstAndMiddle = preferredName.substring( preferredName.indexOf( ',' )+2, preferredName.length());
                String middleName = "";
                String firstName = "";
                //check to see if name has middle name
                if(firstAndMiddle.indexOf( " " )>0){
                    middleName = firstAndMiddle.substring( firstAndMiddle.indexOf( " " )+1, firstAndMiddle.length());
                    firstName = firstAndMiddle.substring( 0, firstAndMiddle.indexOf( " " ));
                }
                else{
                    firstName = firstAndMiddle;
                }


//                if(!headName.equalsIgnoreCase( Head_of_House )){
//                    //ignore first case
//                    if(headName.length()>0){
//                        //store family
//                        family.store();
//                        count++;
//                        //reset variables
//                        family = new Family();
//                        siblings = new ArrayList<Member>();
//                    }
//                    headName = Head_of_House;
//
//                }
                String lastName = preferredName.substring( 0,preferredName.indexOf( ',' ));

                String Preferred_Name                    = rset.getString("Preferred_Name");
                String Address_City                      = rset.getString("Address_City");
                String Address_Street_1                  = rset.getString("Address_Street_1");
                String Address_Street_2                  = rset.getString("Address_Street_2");
                String Age                               = rset.getString("Age");
                String Birth_Date                        = rset.getString("Birth_Date");
                String Email_Address                     = rset.getString("Email_Address");
                String Full_Name                         = rset.getString("Full_Name");
                String Home_Teaching_Companions          = rset.getString("Home_Teaching_Companions");
                String Household_Position                = rset.getString("Household_Position");
                String Is_Home_Teacher                   = rset.getString("Is_Home_Teacher");
                String Priesthood                        = rset.getString("Priesthood");
                String Primary_Phone_Number              = rset.getString("Primary_Phone_Number");
                String Priesthood_Office                 = rset.getString("Priesthood_Office");
                String Spouse_Name                       = rset.getString("Spouse_Name");
                String Confirmation_Date                 = rset.getString("Confirmation_Date");
                String Birthday                          = rset.getString("Birthday");
                String Callings                          = rset.getString("Callings");
                String Has_Children                      = rset.getString("Has_Children");
                String Head_of_House_and_Spouse          = rset.getString("Head_of_House_and_Spouse");
                String Home_Teachers                     = rset.getString("Home_Teachers");
                String Home_Teachers_with_phone_numbers  = rset.getString("Home_Teachers_with_phone_numbers");
                String Is_Married                        = rset.getString("Is_Married");
                String Is_Member                         = rset.getString("Is_Member");
                String Is_Single                         = rset.getString("Is_Single");
                String Is_Widowed                        = rset.getString("Is_Widowed");
                String Sex                               = rset.getString("Sex");
                String Spouse_of_Head_of_House           = rset.getString("Spouse_of_Head_of_House");

                Member famMem = new Member();
                famMem.setFirstName( firstName );
                famMem.setMiddleName( middleName );
                famMem.setLastName( lastName );
                if(Sex.equalsIgnoreCase( "male")){

                    if(Priesthood_Office.equalsIgnoreCase( "High Priest" )){
                        famMem.setMem_role(MemberDomain.HIGHPRIEST);
                    }
                    if(Priesthood_Office.equalsIgnoreCase( "Elder" )){
                        famMem.setMem_role(MemberDomain.ELDER);
                    }
                    if(Priesthood_Office.equalsIgnoreCase( "Priest" )){
                        famMem.setMem_role(MemberDomain.PRIEST);
                    }
                    if(Priesthood_Office.equalsIgnoreCase( "Teacher" )){
                        famMem.setMem_role(MemberDomain.TEACHER);
                    }
                    if(Priesthood_Office.equalsIgnoreCase( "Deacon" )){
                        famMem.setMem_role(MemberDomain.DEACON);
                    }
                    if(Priesthood_Office.equalsIgnoreCase( "Unordained" )){
                        famMem.setMem_role(MemberDomain.UNORDAINED);
                    }

                    //need to set the famrole and the rest

                    famMem.setHas_comp("FALSE");
                }
                else{

                }
                famMem.setAddr1(Address_Street_1);
                famMem.setAddr2("Saratoga Springs, UT 84045");
                famMem.setBirth(Birth_Date);
                famMem.setEmail(Email_Address);
                famMem.setPhone(Primary_Phone_Number);
                Family family = new Family();
                if(famHash.containsKey( Head_of_House )){
                    family = famHash.get( Head_of_House );
                }
                //check for Head of house
                if(Household_Position.equalsIgnoreCase( "Head of Household" )){
                    if(Sex.equalsIgnoreCase( "male")){
                        famMem.setFam_role(FamilyDomain.FATHER);
                        family.setFather( famMem );
                    }else{
                        famMem.setFam_role(FamilyDomain.MOTHER);
                        family.setMother( famMem );
                    }
                }
                if(Household_Position.equalsIgnoreCase( "Spouse" )){
                    if(Sex.equalsIgnoreCase( "male")){
                        famMem.setFam_role(FamilyDomain.FATHER);
                        family.setFather( famMem );
                    }else{
                        famMem.setFam_role(FamilyDomain.MOTHER);
                        family.setMother( famMem );
                    }
                }
                if(Household_Position.equalsIgnoreCase( "Other" )){
                    if(Sex.equalsIgnoreCase( "male")){
                        famMem.setFam_role(FamilyDomain.SON);
                        family.getSiblings().add( famMem );
                    }else{
                        famMem.setFam_role(FamilyDomain.DAUGHTER);
                        family.getSiblings().add( famMem );
                    }
                }
                famHash.put( Head_of_House, family );
            }
            Set set = famHash.keySet();
            Iterator iter = set.iterator();
            while(iter.hasNext()){
                ((Family)famHash.get( iter.next())).store();
                count++;
            }
            System.out.println("saved: "+count+" famlies");
            stm.close();
            conn.close();
        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
