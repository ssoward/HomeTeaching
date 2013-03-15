/**
 * Title: HTNotes.java
 * Description: HT
 * Date: Jul 19, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.soward.db.DB;

public class HTNotes {
    public String pid;
    public String famPid;
    public String compPid;
    public String note;
    public String date;
    public String username;
    public ArrayList<String> noteList;


    public String homeTaught;

    public final static String TABLE_NAME = "ht_fam_notes";

    private final String TABLE_COLS = "pid, fam_pid, comp_pid, note, user, date";

    /**
     * @param pid
     * @param famPid
     * @param compPid
     * @param note
     * @param date
     * @param homeTaught
     * @param table_name
     * @param table_cols
     */
    public HTNotes() {
        this.pid = "";
        this.famPid = "";
        this.compPid = "";
        this.note = "";
        this.date = "";
        this.homeTaught = "";
        this.noteList = new ArrayList<String>();
    }
    /**
     * store HTNotes 
     */
    public void store(){
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "insert into " +TABLE_NAME+
            " ("+TABLE_COLS+")" +
            " values(null,"
            +"'"+ ( this.famPid.replaceAll( "'", "&#39" ) ) + "'" + ","
            +"'"+ ( this.compPid.replaceAll( "'", "&#39" ) )  + "'" + ","
            +"'"+ ( this.note.replaceAll( "'", "&#39" ) )     + "'" + ","
            +"'"+ ( this.username.replaceAll( "'", "&#39" ) )     + "'" + ","
            +" now())";
            Statement stm = conn.createStatement();
            stm.executeUpdate( sql );
            stm.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public ArrayList<HTNotes> fetchForFamPid(String familyPid){
        ArrayList<HTNotes> htn = new ArrayList<HTNotes>();
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "select * from "+TABLE_NAME+" where fam_pid='"+familyPid+"' order by pid desc";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while(rset.next()){
                HTNotes temp = new HTNotes();
                temp.setPid(rset.getString("pid"));
                temp.setFamPid(rset.getString("fam_pid"));
                temp.setCompPid(rset.getString("comp_pid"));
                temp.setNote(rset.getString("note"));
                temp.setDate(rset.getString("date"));
                temp.setUsername(rset.getString("user"));
                htn.add(temp);				
            }
            stm.close();
            rset.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htn;
    }
    //fetch using the companionship pid
    public ArrayList<HTNotes> fetchForCompPid(String compPid){
        ArrayList<HTNotes> htn = new ArrayList<HTNotes>();
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "select * from "+TABLE_NAME+" where comp_pid='"+compPid+"' and fam_pid='null' order by pid desc";
//            System.out.println(sql);
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while(rset.next()){
                HTNotes temp = new HTNotes();
                temp.setPid(rset.getString("pid"));
                temp.setFamPid(rset.getString("fam_pid"));
                temp.setCompPid(rset.getString("comp_pid"));
                temp.setNote(rset.getString("note"));
                temp.setDate(rset.getString("date"));
                temp.setUsername(rset.getString("user"));
                htn.add(temp);				
            }
            stm.close();
            rset.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htn;
    }
    public ArrayList<HTNotes> fetchAll(){
        ArrayList<HTNotes> htn = new ArrayList<HTNotes>();
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "select * from "+TABLE_NAME+" order by pid desc";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while(rset.next()){
                HTNotes temp = new HTNotes();
                temp.setPid(rset.getString("pid"));
                temp.setFamPid(rset.getString("fam_pid"));
                temp.setCompPid(rset.getString("comp_pid"));
                temp.setNote(rset.getString("note"));
                temp.setDate(rset.getString("date"));
                temp.setUsername(rset.getString("user"));
                htn.add(temp);				
            }
            stm.close();
            rset.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htn;
    }
    //fetch all notes for family and date
    public static ArrayList<HTNotes> fetchForFamPidAndDate(String familyPid, String dateRange){
        ArrayList<HTNotes> htn = new ArrayList<HTNotes>();
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "select * from "+TABLE_NAME+" where fam_pid='"+familyPid+"' " +
            "and date > '"+dateRange+"%' order by pid desc";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while(rset.next()){
                HTNotes temp = new HTNotes();
                temp.setPid(rset.getString("pid"));
                temp.setFamPid(rset.getString("fam_pid"));
                temp.setCompPid(rset.getString("comp_pid"));
                temp.setNote(rset.getString("note"));
                temp.setDate(rset.getString("date"));
                temp.setUsername(rset.getString("user"));
                htn.add(temp);				
            }
            stm.close();
            rset.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htn;
    }
    public String removeNote(String notePid){
        String done = "Remove note failed. Please contact system admin.";
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "delete from "+TABLE_NAME+" where pid='"+notePid+"'";
            Statement stm = conn.createStatement();
            stm.executeUpdate( sql );
            stm.close();
            conn.close();
            done = "Note removed successfully.";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return done;
    }
    //@returns true if note exists for current
    //month for specified family
    public static boolean hasNoteForCurrentMonth(String fam_pid, String dateRange){
        boolean hasNote = !((fetchForFamPidAndDate(fam_pid, dateRange)).isEmpty());
        return hasNote;
    }

    public void fetch(String pid){
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "select * from "+TABLE_NAME+" where pid='"+pid+"'";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while(rset.next()){
                this.setPid(rset.getString("pid"));
                this.setFamPid(rset.getString("fam_pid"));
                this.setCompPid(rset.getString("comp_pid"));
                this.setNote(rset.getString("note"));
                this.setDate(rset.getString("date"));
                this.setUsername(rset.getString("user"));
            }
            stm.close();
            rset.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the compPid
     */
    public String getCompPid() {
        return compPid;
    }

    /**
     * @param compPid the compPid to set
     */
    public void setCompPid(String compPid) {
        this.compPid = compPid;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the famPid
     */
    public String getFamPid() {
        return famPid;
    }

    /**
     * @param famPid the famPid to set
     */
    public void setFamPid(String famPid) {
        this.famPid = famPid;
    }

    /**
     * @return the homeTaught
     */
    public String getHomeTaught() {
        return homeTaught;
    }

    /**
     * @param homeTaught the homeTaught to set
     */
    public void setHomeTaught(String homeTaught) {
        this.homeTaught = homeTaught;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
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
     * @return the noteList
     */
    public ArrayList<String> getNoteList() {
        return noteList;
    }
    /**
     * @param noteList the noteList to set
     */
    public void setNoteList( ArrayList<String> noteList ) {
        this.noteList = noteList;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername( String username ) {
        this.username = username;
    }


}
