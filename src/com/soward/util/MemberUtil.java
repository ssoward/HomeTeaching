/**
 * Title: MemberUtil.java
 * Description: HomeTeaching
 * Date: Jun 30, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.soward.db.DB;
import com.soward.domain.MemberDomain;
import com.soward.exception.DNFException;
import com.soward.object.Member;

public class MemberUtil {
    private static final String COL_NAMES = "( pid, comp1, comp2, comp3, has_district)";
    public MemberUtil(){}

    public static void main(String[] agrs){
        MemberUtil.fetchCompPidForFamilyPid( "916");
    }
    /**
     * Get just the compPid for this familyPid
     * @param famPid String
     * @return String compPid
     */public static String fetchCompPidForFamilyPid(String famPid){
         String tcompPid = "";
         com.soward.db.DB db = new DB();
         if(famPid!=null&&famPid.length()>0){
             try {
                 Connection conn = db.openConnection();
                 Statement stm = conn.createStatement();
                 String sql = "select * from assignments where pid = " +
                 "(SELECT assignments_pid FROM assign_comps where fam_pid = "+famPid+")";
                 ResultSet rset = stm.executeQuery(sql);
                 while (rset.next()) {
                     tcompPid = rset.getString("comp_pid");
                 }
                 conn.close();
                 stm.close();
                 rset.close();
             }catch(Exception e){
                 e.printStackTrace();
             }
         }
         return tcompPid;
     }

     /**
      * get all elders from db.
      */
     public ArrayList<Member> getAllElders(){
         ArrayList<Member>eldList = new ArrayList<Member>();
         com.soward.db.DB db = new DB();
         try {
             Connection conn = db.openConnection();
             Statement stm = conn.createStatement();
             ResultSet rset = stm.executeQuery("select * from member where mem_role='"+MemberDomain.ELDER+"'");
             while (rset.next()) {
                 Member cl = new Member();
                 cl.setPid(rset.getString("pid"));
                 cl.setFirstName(rset.getString("first_name"));
                 cl.setMiddleName(rset.getString("middle_name"));
                 cl.setLastName(rset.getString("last_name"));
                 cl.setAddr1(rset.getString("addr1"));
                 cl.setAddr2(rset.getString("addr2"));
                 cl.setEmail(rset.getString("email"));
                 cl.setPhone(rset.getString("phone"));
                 cl.setBirth(rset.getString("birth"));
                 cl.setCell(rset.getString("cell"));
                 cl.setFam_role(rset.getString("fam_role"));
                 cl.setFamilypid(rset.getString("fam_pid"));
                 cl.setMem_role(rset.getString("mem_role"));
                 eldList.add(cl);
             }
             conn.close();
             stm.close();
             rset.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return eldList;
     }
     /**
      * get all elders from db.
      */
     public static ArrayList<Member> getAllMembers(){
         ArrayList<Member>eldList = new ArrayList<Member>();
         com.soward.db.DB db = new DB();
         try {
             Connection conn = db.openConnection();
             Statement stm = conn.createStatement();
             ResultSet rset = stm.executeQuery("select * from member");
             while (rset.next()) {
                 Member cl = new Member();
                 cl.setPid(rset.getString("pid"));
                 cl.setFirstName(rset.getString("first_name"));
                 cl.setMiddleName(rset.getString("middle_name"));
                 cl.setLastName(rset.getString("last_name"));
                 cl.setAddr1(rset.getString("addr1"));
                 cl.setAddr2(rset.getString("addr2"));
                 cl.setEmail(rset.getString("email"));
                 cl.setPhone(rset.getString("phone"));
                 cl.setBirth(rset.getString("birth"));
                 cl.setCell(rset.getString("cell"));
                 cl.setFam_role(rset.getString("fam_role"));
                 cl.setFamilypid(rset.getString("fam_pid"));
                 cl.setMem_role(rset.getString("mem_role"));
                 eldList.add(cl);
             }
             conn.close();
             stm.close();
             rset.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return eldList;
     }
     /**
      * get all instances of the string from member table
      * returns list of lists
      */
     public static ArrayList<ArrayList<String>> getCanidateSearch(String searchCan){
         ArrayList<ArrayList<String>>AL = new ArrayList<ArrayList<String>>();
         String sql = "SELECT * FROM member m where first_name like '%"+searchCan+"%' || last_name like '%"+searchCan+"%'";       
         com.soward.db.DB db = new DB();
         try {
             Connection conn = db.openConnection();
             Statement stm = conn.createStatement();
             ResultSet rset = stm.executeQuery(sql);
             while (rset.next()) {
                 ArrayList<String> member = new ArrayList<String>();
                 member.add( rset.getString("pid"));
                 member.add( rset.getString("first_name"));
                 member.add( rset.getString("last_name"));
                 member.add( rset.getString("phone"));
                 member.add( rset.getString("cell"));
                 member.add( rset.getString("addr1")+" "+rset.getString("addr2"));
                 AL.add( member );
             }
         }
         catch(Exception e){
             e.printStackTrace();
         }
         return AL;
     }
     /*
      * //update db tbl comps and set has_comp=TRUE
      */
     public void disolve(String cmpPid){
         ArrayList<Member>eldList = new ArrayList<Member>();
         com.soward.db.DB db = new DB();
         try {
             Connection conn = db.openConnection();
             Statement stm = conn.createStatement();
             Statement stm2 = conn.createStatement();
             //get all comps in this 
             String updateHasComp = "select * from comps where pid='"+cmpPid+"'";

             ResultSet rset = stm.executeQuery(updateHasComp);
             if(rset.next()){
                 String sql = "";
                 try{
                     if((rset.getString("comp1"))!=null&&(rset.getString("comp1").length()>1)){
                         sql = "update member set has_comp='FALSE' where pid='"+rset.getString("comp1")+"'";
                         stm2.executeUpdate(sql);
                     }
                 }catch(Exception e1){
                     //do nothing, has no comp
                     System.out.println("sql: "+sql);
                     e1.printStackTrace();
                 }
                 try{
                     if((rset.getString("comp2"))!=null&&(rset.getString("comp2").length()>1)){
                         sql = "update member set has_comp='FALSE' where pid='"+rset.getString("comp2")+"'";
                         stm2.executeUpdate(sql);
                     }
                 }catch(Exception e1){
                     //do nothing, has no comp
                     System.out.println("sql: "+sql);
                     e1.printStackTrace();
                 }
                 try{
                     if((rset.getString("comp3"))!=null&&(rset.getString("comp3").length()>1)&&
                             !rset.getString("comp3").equalsIgnoreCase( "--" )){
                         sql = "update member set has_comp='FALSE' where pid='"+rset.getString("comp3")+"'";
                         stm2.executeUpdate(sql);
                     }
                 }catch(Exception e1){
                     //do nothing, has no comp
                     System.out.println("sql: "+sql);
                     e1.printStackTrace();
                 }
             }
             stm.executeUpdate("delete from comps where pid='"+cmpPid+"'");
             //removing comp releases families, update fams to has hometeacher=false
             stm.executeUpdate("update family set has_ht='FALSE' where pid in (select fam_pid from assign_comps where assignments_pid in (select pid from assignments where comp_pid='"+cmpPid+"'))");
             //remove reference in mapping talbe assign_comps
             stm.execute("delete from assign_comps where assignments_pid in (select pid from assignments where comp_pid='"+cmpPid+"')");
             //f remove assingments 
             stm.executeUpdate("delete from assignments where comp_pid='"+cmpPid+"'");
             stm.executeUpdate("delete from district_comps where comp_pid='"+cmpPid+"'");
             conn.close();
             stm.close();
             stm2.close();
             rset.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     /*
      * //update db tbl comps and set has_comp=TRUE
      */
     public String updateComps(ArrayList<String> compList){
         ArrayList<Member>eldList = new ArrayList<Member>();
         String compPid = "";
         com.soward.db.DB db = new DB();
         try {
             if(!compList.isEmpty()&&canCreateComps(compList.get(0))){
                 Connection conn = db.openConnection();
                 Statement stm = conn.createStatement();
                 for(String memPid: compList){
                     String sql = "update member set has_comp='TRUE' where pid='"+memPid+"'";
                     stm.executeUpdate(sql);
                 }
                 try{
                     compList.get(2);				
                 }catch(Exception e){
                     //no third comp
                     compList.add("--");
                 }
                 stm.executeUpdate("insert into comps "+MemberUtil.COL_NAMES+" values(null, '"+compList.get(0)+"', '"+compList.get(1)+"', '"+compList.get(2)+"', 'FALSE')");
                 ResultSet keys = stm.getGeneratedKeys();
                 
                 if(keys.next()){
                     compPid = keys.getString(1);
                 }
                 //set default district
                 stm.executeUpdate("insert into district_comps (pid, dist_num, comp_pid) values(null,0, '"+compPid+"')");
                 keys.close();
                 conn.close();
                 stm.close();
             }
             //comp already exists get compPid
             else{
                 Connection conn = db.openConnection();
                 Statement stm = conn.createStatement();
                 ResultSet rset = stm.executeQuery("select pid from comps where comp1 = '" +compList.get(0)+"'");
                 while (rset.next()) {
                     compPid = rset.getString( "pid" );
                 }
                 conn.close();
                 stm.close();
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return compPid;
     }
     /**
      * verify that the user did not refresh page 
      * inadvertanty resend request to create comp
      * -this check is done by checking to see if 
      * memeber has a comp set to true.
      */
     private boolean canCreateComps(String memPid) {
         Member mem = new Member();
         boolean hasComp = false;
         try {
             mem.fetch(memPid);
             hasComp = (mem.getHas_comp().equalsIgnoreCase("FALSE"));
         } catch (DNFException e) {
             e.printStackTrace();
         }
         return hasComp;
     }
//   select * from member where  mem_role='0001' and has_comp='FALSE'
     public ArrayList<Member> getNonCompElders(){
         ArrayList<Member>eldList = new ArrayList<Member>();
         com.soward.db.DB db = new DB();
         try {
             Connection conn = db.openConnection();
             Statement stm = conn.createStatement();
             ResultSet rset = stm.executeQuery("select * from member where mem_role " +
                     "in ('"+MemberDomain.ELDER+"'," +
                     "'"+MemberDomain.DEACON+"', " +
                     "'"+MemberDomain.PRIEST+"', " +
                     "'"+MemberDomain.TEACHER+"') and has_comp='FALSE' order by last_name ");
             while (rset.next()) {
                 Member cl = new Member();
                 cl.setPid(rset.getString("pid"));
                 cl.setFirstName(rset.getString("first_name"));
                 cl.setMiddleName(rset.getString("middle_name"));
                 cl.setLastName(rset.getString("last_name"));
                 cl.setAddr1(rset.getString("addr1"));
                 cl.setAddr2(rset.getString("addr2"));
                 cl.setEmail(rset.getString("email"));
                 cl.setPhone(rset.getString("phone"));
                 cl.setBirth(rset.getString("birth"));
                 cl.setCell(rset.getString("cell"));
                 cl.setFam_role(rset.getString("fam_role"));
                 cl.setFamilypid(rset.getString("fam_pid"));
                 cl.setMem_role(rset.getString("mem_role"));
                 eldList.add(cl);
             }
             conn.close();
             stm.close();
             rset.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return eldList;
     }
}
