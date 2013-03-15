/**
 * Title: Districts.java
 * Description: HomeTeaching
 * Date: Jul 2, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.soward.db.DB;
import com.soward.exception.DNFException;

public class Districts {
    public final static String TABLE_NAME = "districts";
    private static final String DISTRICT_COMPS_COL_NAMES = " pid, dist_num, comp_pid ";
    String pid;
    String distNumber;
    String districtLeader;
    ArrayList<Comps> compList;
//  String distName;
    /**
     * @param pid
     * @param compList
     * @param distName
     */
    public Districts(String pid, ArrayList<Comps> compList) {
        this.pid = pid;
        this.compList = compList;
//      this.distName = distName;
    }
    public Districts(){
//      this.pid = new String();
        this.compList = new ArrayList<Comps>();
//      this.distName = new String();
    }
//  public Districts fetch(String pid){
//  return null;
//  }
    /**
     * fetch all the districts by getting the distName and getting
     * each group with the given distName
     */
    public ArrayList<Districts> getAll(){
        ArrayList<Districts> distList = new ArrayList<Districts>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from "+TABLE_NAME;
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                Districts cl = new Districts();
                cl.fetchCompsInDist(rset.getString("dist_num"));
                cl.setPid(rset.getString("pid"));
                distList.add(cl);
            }
            conn.close();
            stm.close();
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (DNFException dnf) {
            dnf.printStackTrace();
        }
        return distList;
    }
    
    public static HashMap<String, Districts> getDistrictHashByDistNum(){
        HashMap<String, Districts> distHM = new HashMap<String, Districts>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from "+TABLE_NAME;
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                Districts cl = new Districts();
                cl.setDistNumber( rset.getString("dist_num"));
                cl.setPid(rset.getString("pid"));
                cl.setDistrictLeader( rset.getString( "dist_leader" ) );
                distHM.put( cl.getDistNumber(), cl );
            }
            conn.close();
            stm.close();
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distHM;
    }
    //get all districts
    public ArrayList<Districts> fetchAll(){ 
        ArrayList<Districts> distList = new ArrayList<Districts>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from "+TABLE_NAME;
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                Districts cl = new Districts();
                cl.setDistNumber( rset.getString("dist_num"));
                cl.setPid(rset.getString("pid"));
                cl.setDistrictLeader( rset.getString( "dist_leader" ) );
                distList.add(cl);
            }
            conn.close();
            stm.close();
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distList;
    }	
    
    public String updateDist(String distPid,String districtLeader){
        String message = "successfully updated dl.";
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "update districts set dist_leader='"+districtLeader+"' where pid='"+distPid+"'";
            stm.executeUpdate( sql );
            conn.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            message = e.getMessage();
        }
        return message;
    }
    
    /*
     * Get all the comps of given district 
     */
    public void fetchForDistNumber(int distNum){
        if(distNum>0){
            com.soward.db.DB db = new DB();
            try {
                Connection conn = db.openConnection();
                Statement stm = conn.createStatement();
                String sql = "select * from district_comps " +
                "where dist_num='"+distNum+"'";
                ResultSet rset = stm.executeQuery(sql);
                while (rset.next()) {
                    Comps com = new Comps();
                    com.fetch(rset.getString("comp_pid"));
                    this.compList.add(com);
                }
                conn.close();
                stm.close();
                rset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            catch (DNFException dnf) {
                dnf.printStackTrace();
            }
        }
    }
    /*
     * @param newCompList
     * @param district Number
     * create new dist with comp list, by first removing any comps in 
     * district, setting each comp's has_district to false
     * and then adding the new comps.
     */
    public void createDists(ArrayList<String> compList, String distNum){
        com.soward.db.DB db = new DB();
        boolean isNum = false;
        int dn = 0;
        try{
            dn = Integer.parseInt(distNum);
            isNum = true;
        }catch(Exception e){
            //not numeric
        }
        //check that the distNum is a number greater than 0
        if(distNum!=null&&isNum&&dn>0){
            try {
                Connection conn = db.openConnection();
                Statement stm = conn.createStatement();
                if(compList!=null&&!compList.isEmpty()){
                    //remove comps from district
                    removeComps(distNum);
                    stm.executeUpdate("delete from district_comps where dist_num= '"+distNum+"'");
                    for(String cop: compList){
                        //first remove all in district where district number=?
                        //then add the new group
                        stm.executeUpdate("insert into district_comps ("+Districts.DISTRICT_COMPS_COL_NAMES+") values(null, '"+distNum+"', '"+cop+"')");
                        stm.executeUpdate("update comps set has_district='TRUE' where pid='"+cop+"'");
                    }
                    conn.close();
                    stm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void removeComps(String distNum) {
        try {
            com.soward.db.DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            //set comps hasdistrict is set to false
            stm.executeUpdate("update comps set has_district='FALSE' where pid in (select comp_pid from district_comps where dist_num='"+distNum+"')");
            //remove comps from district_comps
            stm.executeUpdate("delete from district_comps where dist_num= '"+distNum+"'");
            conn.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     * see if the comp already has a dist assigned.
     */
    private boolean assertStorable(ArrayList<String> compList2) {
        com.soward.db.DB db = new DB();
        boolean canAdd = true;
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            // dont have the distpid, have to get it and
            // if this is the last comp in district, remove
            // district also
            String sql = "select pid from comps where pid='"+compList2.get(0)+"' and has_district='TRUE'";
            ResultSet rset = stm.executeQuery(sql);
            int size = 0;
            String distPid = "";
            while(rset.next()){
                canAdd = false;   
            }
            rset.close();
            conn.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canAdd;
    }
    /*
     * @param compPid String
     * Remove comp from district
     */
    public void removeCompFromDist(String cmpPid){
        ArrayList<Member>eldList = new ArrayList<Member>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            //set default district number
            stm.executeUpdate("update district_comps set dist_num = 0 where comp_pid='"+cmpPid+"'");
            //set has comp to false for the removed comp
            stm.executeUpdate("update comps set has_district='FALSE' where pid='"+cmpPid+"'");
            conn.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        HashMap<String, String> distCompList = Districts.fetchDistrictNumberCompPidHash();
        System.out.println(distCompList.size());
    }
    //fetch hashmap<district number, district leader name>
    public static HashMap<String, String> fetchDLHash(){
        ArrayList<Districts> distList = new Districts().fetchAll();
        HashMap<String, String> hm = new HashMap<String, String>();
        for(Districts dist: distList){
            hm.put( dist.getDistNumber(), dist.getDistrictLeader() );
        }
        return hm;
    }
    //fetch district number from comp_pid
    public static HashMap<String, String> fetchDistrictNumberCompPidHash(){
        com.soward.db.DB db = new DB();
        HashMap<String, String> distCompList = new HashMap<String, String>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery("SELECT * FROM district_comps , districts  where districts.dist_num = district_comps.dist_num");
            while (rset.next()) {
                distCompList.put( rset.getString("comp_pid"), rset.getString("dist_leader") );
            }
            conn.close();
            stm.close();
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distCompList;
    }
    /*
     * fetches for the pid
     */
    public void fetchCompsInDist(String pid)throws DNFException{
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery("select * from district_comps where dist_num='"+pid+"'");
            while (rset.next()) {
                Comps com = new Comps();
                com.fetch(rset.getString("comp_pid"));
                this.compList.add(com);
            }
            conn.close();
            stm.close();
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (DNFException dnf) {
            dnf.printStackTrace();
        }
    }
    /**
     * @return the compList
     */
    public ArrayList<Comps> getCompList() {
        return compList;
    }
    /**
     * @param compList the compList to set
     */
    public void setCompList(ArrayList<Comps> compList) {
        this.compList = compList;
    }
    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }
    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }
    /**
     * @return the distNumber
     */
    public String getDistNumber() {
        return distNumber;
    }
    /**
     * @param distNumber the distNumber to set
     */
    public void setDistNumber(String distNumber) {
        this.distNumber = distNumber;
    }
    public String getDistrictLeader() {
        return districtLeader;
    }
    public void setDistrictLeader( String districtLeader ) {
        this.districtLeader = districtLeader;
    }
}
