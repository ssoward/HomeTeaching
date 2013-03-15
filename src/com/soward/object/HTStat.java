/**
 * Title: HTStat.java
 * Description: HT
 * Date: Jul 18, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.soward.db.DB;
import com.soward.exception.DNFException;
import com.soward.exception.RuleException;
import com.soward.util.Family;
import com.soward.util.MemberUtil;
import com.soward.util.StringUtil;

public class HTStat {
    public String compPid;

    public String pid;

    public String month;

    public String year;

    public String famPid;

    public String homeTaught;

    public Family fam;

    // public variables for
    // stats for list of families
    public String totFam;

    public String totFamWithOutHT;

    public String totFamWithHT;

    public String totFamHTaught;

    public String totFamHTaughtOther;

    public String totPercentHT;

    public String totPercentHTOfFamsWithHT;

    public String totCompsNotContacted;

    private String TABLE_NAME = "ht_stats";

    private Calendar calNow = Calendar.getInstance();

    private SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd" );

    public int mm = calNow.get( Calendar.MONTH );

    public int yy = calNow.get( Calendar.YEAR );

    private String TABLE_COLS = "pid, fam_pid, comp_pid, ht_month, ht_year, ht_stat";

    public HTStat() {
        this.pid = "";
        this.compPid = "";
        this.month = "";
        this.year = "";
        this.famPid = "";
        this.homeTaught = "";
    }

    /**
     * @param compPid
     * @param date
     * @param noteList
     * @param famPid
     * @param homeTaught
     */
    public HTStat( String compPid, String month, String year, ArrayList<String> noteList, String famPid, String homeTaught ) {
        this.compPid = compPid;
        this.month = month;
        this.year = year;
        this.famPid = famPid;
        this.homeTaught = homeTaught;
    }

    public String getHTStatDisplay( HashMap<String, HTStat> list, String month, String year ) {
        String table = "";
        Set<String> sss = list.keySet();
        table = "<table width=100% border=0 cellpadding=1 cellspacing=1><tr>";
        try {
            mm = Integer.parseInt( month );
            yy = Integer.parseInt( year );
            yy--;
            for ( String ttt : list.keySet() ) {
            }
            for ( int i = 0; i < 12; i++ ) {
                if ( mm + 1 > 11 ) {
                    mm = 0;
                    yy++;
                } else {
                    mm++;
                }
                // this is a pain b/c months start from 0-11...
                int mplus = mm + 1;
                int yplus = yy;
                if ( list.containsKey( mm + "" + yy ) && ( ( (HTStat) list.get( mm + "" + yy ) ).getHomeTaught().equalsIgnoreCase( "YES" ) ) ) {

                    table += "<td align=center bgcolor=\"lightblue\">" + mplus + "/" + yplus + "</td>";
                } 
                else if ( list.containsKey( mm + "" + yy ) && ( ( (HTStat) list.get( mm + "" + yy ) ).getHomeTaught().equalsIgnoreCase( "OTHER" ) ) ) {
                    
                    table += "<td align=center bgcolor=\"lightgreen\">" + mplus + "/" + yplus + "</td>";
                } 
                
                else {
                    table += "<td align=center bgcolor=\"#bbbbbb\">" + mplus + "/" + yplus + "</td>";

                }
            }
            table += "</tr></table>";
        } catch ( Exception e ) {
            // invalid strings for month&year
            e.printStackTrace();
            table = "<table></table>";
        }
        return table;
    }

    /**
     * @return htstat object for given fampid
     */
    public void fetchForFamPidAndDate( String famPid, String monthh, String yearr ) {
        DB db = new DB();

        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM ht_stats where fam_pid='" + famPid + "' and ht_month='" + monthh + "' and ht_year='" + yearr + "'";// join
            // user_roles
            // on
            ResultSet rset = stm.executeQuery( sql );
            this.setFamPid( famPid );
            while ( rset.next() ) {
                this.setPid( rset.getString( "pid" ) );
                this.setCompPid( rset.getString( "comp_pid" ) );
                this.setMonth( rset.getString( "ht_month" ) );
                this.setYear( rset.getString( "ht_year" ) );
                this.setHomeTaught( rset.getString( "ht_stat" ) );
            }
            //if not ht exist create new with pid
            if(!StringUtil.isSet(this.getPid())){
                this.setCompPid( MemberUtil.fetchCompPidForFamilyPid(famPid));
                this.setMonth( monthh );
                this.setYear( yearr );
                this.setHomeTaught( "NONE" );
                this.store();
            }
            rset.close();
            stm.close();
            conn.close();
        } catch ( SQLException e ) {
            e.printStackTrace();

        } catch ( RuleException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @return htstat object for given fampid
     */
    public void fetch( String pid ) {
        DB db = new DB();

        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM ht_stats where pid='" + pid + "'";
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                this.setPid( rset.getString( "pid" ) );
                this.setFamPid( rset.getString( "fam_pid" ) );
                this.setCompPid( rset.getString( "comp_pid" ) );
                this.setMonth( rset.getString( "ht_month" ) );
                this.setYear( rset.getString( "ht_year" ) );
                this.setHomeTaught( rset.getString( "ht_stat" ) );
            }
            rset.close();
            stm.close();
            conn.close();
        } catch ( SQLException e ) {
            e.printStackTrace();

        }

    }

    /*
     * get stats for arraylist of families
     */
    public HTStat getAllStats( ArrayList<Family> fammList ) {
        double totFam = fammList.size();
        int totFamWOHT = 0;
        double totFamWHT = 0;
        double totFamHT = 0;
        double totFamHTOther = 0;
        int tottotCompsNotContacted = 0;
        HTStat famListStat = new HTStat();
        famListStat.setTotFam( totFam + "" );
        for ( Family faa : fammList ) {
            // check to see if fam has HT
            if ( faa.getComps().hasComps() ) {
                totFamWHT++;
            } else {
                totFamWOHT++;
            }
            if ( faa.getHtStat().getHomeTaught().equals( "OTHER" ) ) {
                totFamHTOther++;
            } 
            if ( faa.getHtStat().getHomeTaught().equals( "YES" )||faa.getHtStat().getHomeTaught().equals( "OTHER" ) ) {
                totFamHT++;
            } 
            else if ( faa.getHtStat().getHomeTaught().equals( "NONE" ) ) {
                tottotCompsNotContacted++;
            }
        }
        famListStat.setTotFamWithOutHT( totFamWOHT + "" );
        famListStat.setTotFamWithHT( totFamWHT + "" );
        famListStat.setTotFamHTaught( ( totFamHT ) + "" );
        famListStat.setTotFamHTaughtOther( totFamHTOther + "" );
        famListStat.setTotCompsNotContacted( tottotCompsNotContacted + "" );
        if ( totFam > 0 ) {
            famListStat.setTotPercentHT( ( StringUtil.toReportString( totFamHT / fammList.size(), 2 ) ) + "" );
            famListStat.setTotPercentHTOfFamsWithHT( ( StringUtil.toReportString( totFamHT / totFamWHT, 2 ) ) + "" );
        }
        return famListStat;

    }

    // most recent method for getting family list using hashmaps.
    public ArrayList<Family> getAllFamiliesForDate( String monthh, String yearr ) {
        ArrayList<Family> famList = new ArrayList<Family>();
        HashMap<String, Comps> compList = getCompHashMap();

        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from member mem, family fam left join assign_comps on " + "fam.pid=assign_comps.fam_pid left join "
            + "assignments assi on assign_comps.assignments_pid=assi.pid " + "left join comps on assi.comp_pid=comps.pid "
            // + "left join ht_stats on fam.pid=ht_stats.fam_pid "
            + "where mem.fam_pid = fam.pid and fam.eq_fam='TRUE' " + "order by fam.pid";

            //System.out.println(sql);
            ResultSet rset = stm.executeQuery( sql );
            // update familyPid to determine when a family starts/stops

            HashMap<String, ArrayList<Member>> famHash = new HashMap<String, ArrayList<Member>>();
            boolean getComps = true;
            while ( rset.next() ) {
                String famPid = rset.getString( "fam_pid" );
                Member tt = new Member();
                tt.setPid( rset.getString( "pid" ) );
                tt.setFirstName( rset.getString( "first_name" ) );
                tt.setLastName( rset.getString( "last_name" ) );
                tt.setAddr1( rset.getString( "addr1" ) );
                tt.setAddr2( rset.getString( "addr2" ) );
                tt.setEmail( rset.getString( "email" ) );
                tt.setBirth( rset.getString( "birth" ) );
                tt.setPhone( rset.getString( "phone" ) );
                tt.setCell( rset.getString( "cell" ) );
                tt.setFam_role( rset.getString( "fam_role" ) );
                tt.setFamilypid( rset.getString( "fam_pid" ) );
                tt.setMem_role( rset.getString( "mem_role" ) );
                tt.setMiddleName( rset.getString( "middle_name" ) );
                tt.setHas_comp( rset.getString( "has_comp" ) );
                if(!famHash.containsKey( famPid )){
                    ArrayList<Member> al = new ArrayList<Member>();
                    al.add( tt );
                    famHash.put( famPid,al);
                }else{
                    famHash.get( famPid ).add( tt );                      
                }
            }
            Set<String> set = famHash.keySet();
            Iterator<String> iter = set.iterator();
            //iterate through the hash of <famPids, array<Members>> 
            //and create families
            while(iter.hasNext()){
                String key = iter.next();
                ArrayList<Member> al = famHash.get( key );
                Family tempFam = new Family();
                String famPidd = "";
                for(Member tt: al){
                    if ( tt.isFather() ) {
                        tempFam.setFather( tt );
                    } else if ( tt.isMother() ) {
                        tempFam.setMother( tt );
                    } else {
                        tempFam.siblings.add( tt );
                    }

                    tempFam.setPid( key );
                    famPidd = key;
                }
                if ( compList.containsKey( famPidd ) ) {
                    tempFam.setComps( (Comps) compList.get( famPidd ) );
                }
                famList.add( tempFam );
            }

            // result ended, add last family
            famList = getHTListForFamList( famList, monthh , yearr );
            famList = getHasNoteForFamList( famList, monthh, yearr );
            // get ht_stat
            rset.close();
            conn.close();
            stm.close();
        } catch ( SQLException sqle ) {
            sqle.printStackTrace();
        }
        return alphabetizeList(famList);
//      return famList;

    }
    private ArrayList<Family> alphabetizeList( ArrayList<Family> famList ) {
        ArrayList<String> famAL = new ArrayList<String>();
        HashMap<String, Family>famHash = new HashMap<String, Family>();
        ArrayList<Family> famListAlphabetized = new ArrayList<Family>();
        for(Family tempFam: famList){
            famHash.put( tempFam.getName()+tempFam.getFamFirstName(), tempFam );
            famAL.add(tempFam.getName()+tempFam.getFamFirstName() );
        }
        Collections.sort( famAL );
        for(String familyName: famAL){
            famListAlphabetized.add( famHash.get( familyName ) );
        }
        return famListAlphabetized;
    }

    /*
     * attaches/returns a list of family objs with a boolean
     * of whether or not a note exists for each family.
     */
    private ArrayList<Family> getHasNoteForFamList( ArrayList<Family> fl, String monthh, String yearr ) {
        String dateCurrent = yearr + "-";
        HashMap<String, String> hasNoteList = new HashMap<String, String>();
        ArrayList<Family> famList = new ArrayList<Family>();
        int monthCurrent = 0;
        if ( monthh != null ) {
            monthCurrent = Integer.parseInt( monthh );
            monthCurrent++;
        }
        if ( monthCurrent < 10 ) {
            dateCurrent = yearr + "-0" + monthCurrent;
        } else {
            dateCurrent = yearr + "-" + monthCurrent;
        }
        DB db = new DB();
        Connection conn;
        try {
            conn = db.openConnection();
            String sql = "select * from " + HTNotes.TABLE_NAME + " where date > '" + dateCurrent + "%' order by pid desc";
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                hasNoteList.put( rset.getString( "fam_pid" ), rset.getString( "fam_pid" ) );
            }
            stm.close();
            rset.close();
            conn.close();
        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for ( Family tempFam : fl ) {
            if ( hasNoteList.containsKey( tempFam.getPid() ) ) {
                tempFam.setHasNote(true);

            } else {
                tempFam.setHasNote(false);
            }
            famList.add( tempFam );
        }
        return famList;
    }

    // gets a hashmap of key:fampid and obj:comps
    /**
     * @return hashmap<(String)famPid, (Comps)comps> 
     */
    private HashMap<String, Comps> getCompHashMap() {
        HashMap<String, Comps> compHM = new HashMap<String, Comps>();
        // SELECT mem.* , assc.fam_pid as familyPid FROM comps c, member mem,
        // assignments assi, assign_comps assc
        // where mem.pid in (c.comp1, c.comp2, c.comp3) and c.pid =
        // assi.comp_pid
        // and assc.assignments_pid=assi.pid
        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT  mem.* , assc.fam_pid as familyPid, c.pid as comp_pid FROM comps c, member mem, assignments assi, assign_comps assc "
                + "where mem.pid in (c.comp1, c.comp2, c.comp3) and c.pid = assi.comp_pid "
                + "and assc.assignments_pid=assi.pid order by familyPid asc";
            ResultSet rset = stm.executeQuery( sql );
            String currFamPid = "notSetVarForSettingFirstTime";
            Comps comps = new Comps();
            int compNumber = 1;
            while ( rset.next() ) {
                String newFamPid = rset.getString( "familyPid" );
                Member temp = new Member();
                if ( currFamPid.equals( "notSetVarForSettingFirstTime" ) ) {
                    currFamPid = newFamPid;
                }
                temp.pid = rset.getString( "pid" );
                temp.firstName = rset.getString( "first_name" );
                temp.lastName = rset.getString( "last_name" );
                temp.addr1 = rset.getString( "addr1" );
                temp.addr2 = rset.getString( "addr2" );
                temp.email = rset.getString( "email" );
                temp.birth = rset.getString( "birth" );
                temp.phone = rset.getString( "phone" );
                temp.cell = rset.getString( "cell" );
                temp.fam_role = rset.getString( "fam_role" );
                temp.familypid = rset.getString( "fam_pid" );
                temp.mem_role = rset.getString( "mem_role" );
                temp.middleName = rset.getString( "middle_name" );
                temp.has_comp = rset.getString( "has_comp" );
                if ( !currFamPid.equalsIgnoreCase( newFamPid ) ) {
                    compHM.put( currFamPid, comps );
                    currFamPid = newFamPid;
                    comps = new Comps();
                    compNumber = 2;
                    comps.setComp1( temp );
                    comps.setPid( rset.getString( "comp_pid" ) );
                } else {
                    switch ( compNumber ) {
                        case 1:
                            comps.setComp1( temp );
                            comps.setPid( rset.getString( "comp_pid" ) );
                            break;
                        case 2:
                            comps.setComp2( temp );
                            break;
                        case 3:
                            comps.setComp3( temp );
                            break;
                        default:
                            System.out.println( "error in htstat.java line 318" );
                    }
                    compNumber++;
                }
            }
            // get last entry
            compHM.put( currFamPid, comps );
            //test code
//          Set<String> keys = compHM.keySet();
//          for(String ss: keys){
//          Comps tcomps = (Comps)compHM.get( ss );
//          System.out.println(tcomps.getPid()+" "+tcomps.getComp1().getLastName()+" "+tcomps.getComp1().getFirstName());
//          }
            rset.close();
            stm.close();
            conn.close();
        } catch ( SQLException e ) {
            e.printStackTrace();

        }
        return compHM;
    }

    // in an attempt to speed up the getting of the htstat, hashmaps are used.
    private ArrayList<Family> getHTListForFamList( ArrayList<Family> fl, String monthh, String yearr ) {
        ArrayList<Family> famList = new ArrayList<Family>();
        String monthYear = monthh+yearr;
        // associated with the family is a hashmap of ht_stat objs
        // with month+year as the key, so the following is a hashmap
        // with the fam_pid as key and the list of hashmaps defined
        // first in this comment.
        // fam_pid mmyyyy
        //hashMap<FamPid, hashMap<month_year, htStat>>

        // now populate the ht_stat hm
        String sql = "SELECT * FROM ht_stats order by fam_pid asc";
        try {
            DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            HashMap<String, HTStat> tempHT = new HashMap<String, HTStat>();
//          String currFamPid = "";
//          String key = "";
            //hashMap<FamPid, hashMap<month_year, htStat>>
            HashMap<String, HashMap<String, HTStat>> htHM = new HashMap<String, HashMap<String, HTStat>>();
            //HashMap<String, HashMap<String, HTStat>> tempHM = new HashMap<String, HashMap<String, HTStat>>();
            while ( rset.next() ) {
                String key = "";
                String fmPid = rset.getString( "fam_pid" );
                HTStat hts = new HTStat();
                hts.setPid( rset.getString( "pid" ) );
                hts.setFamPid( fmPid );
                hts.setCompPid( rset.getString( "comp_pid" ) );
                hts.setMonth( rset.getString( "ht_month" ) );
                hts.setYear( rset.getString( "ht_year" ) );
                hts.setHomeTaught( rset.getString( "ht_stat" ) );
                key = hts.getMonth() + hts.getYear();

                if(htHM.containsKey( fmPid )){
                    htHM.get( fmPid ).put( key, hts );
                }else{
                    HashMap<String, HTStat> tq = new HashMap<String, HTStat>();
                    tq.put( key, hts );
                    htHM.put( fmPid, tq );
                }

//              // if this is not the currFamPid
//              // start a new htstat
//              if ( currFamPid.length() < 1 ) {
//              currFamPid = fmPid;
//              }
//              if ( !currFamPid.equalsIgnoreCase( fmPid ) ) {
//              htHM.put( currFamPid, tempHT );
//              tempHT = new HashMap<String, HTStat>();
//              currFamPid = fmPid;
//              }
//              tempHT.put( key, hts );
            }
            // add the last entry
            //htHM.put( currFamPid, tempHT );
            // populate the familylist hashmap
            for ( Family tempFam : fl ) {
                boolean noStat = false;
                if ( htHM.containsKey( tempFam.getPid() ) ) {
                    // set hashmap of htstats
                    HashMap<String, HTStat> hthm = htHM.get( tempFam.getPid() );
                    tempFam.setHTStatList( hthm );
                    // set ht_stat for given month&year
                    if ( hthm.containsKey( monthYear ) ) {
                        tempFam.setHtStat( hthm.get( monthYear ) );
                    } else {
                        noStat =true;

                    }
                }else{
                    noStat = true;
                }
                //this family does not have any ht stats, so add one for this month+year request
                if(noStat){
                    HTStat htst = new HTStat();
                    //no ht stat found, creat new 
                    htst = new HTStat();
                    String tCompPid = MemberUtil.fetchCompPidForFamilyPid(tempFam.getPid());
                    htst.setFamPid( tempFam.getPid() );
                    htst.setCompPid(tCompPid!=null?tCompPid:"");
                    htst.setMonth( monthh );
                    htst.setYear( yearr );
                    htst.setHomeTaught( "NONE" );
                    htst.store();
                    tempFam.setHtStat( htst );
                }
                famList.add( tempFam );
            }
            rset.close();
            stm.close();
            conn.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return famList;
    }

    private ArrayList<HTStat> getHTListForFamPid( String famPid2 ) {
        String sql = "SELECT * FROM ht_stats where fam_pid'" + famPid2 + "'";
        ArrayList<HTStat> hp = new ArrayList<HTStat>();
        try {
            DB db = new DB();
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                HTStat hts = new HTStat();
                hts.setPid( rset.getString( "pid" ) );
                hts.setFamPid( rset.getString( "fam_pid" ) );
                hts.setCompPid( rset.getString( "comp_pid" ) );
                hts.setMonth( rset.getString( "ht_month" ) );
                hts.setYear( rset.getString( "ht_year" ) );
                hts.setHomeTaught( rset.getString( "ht_stat" ) );
                hp.add( hts );
            }
            rset.close();
            stm.close();
            conn.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return hp;
    }

    /**
     * @param monthh
     * @param yearr
     * @return Add both the stats object and the comps to the families via
     *         hashmaps
     * 
     * select * from member mem, family fam left join assign_comps on
     * fam.pid=assign_comps.fam_pid left join assignments assi on
     * assign_comps.assignments_pid=assi.pid left join comps on
     * assi.comp_pid=comps.pid where mem.fam_pid = fam.pid and fam.eq_fam='TRUE'
     */
    public ArrayList<Family> getAllFamForDate( String monthh, String yearr ) {
        HashMap<String, HTStat> hp = new HashMap<String, HTStat>();
        HashMap<String, Comps> compHash = new HashMap<String, Comps>();
        DB db = new DB();
        Family cc = new Family();
        ArrayList<Family> families = cc.getAllEQFamilies();
        Comps cops = new Comps();
        ArrayList<Comps> comps = cops.getAll();
        // check for valid input data
        if ( monthh != null && yearr != null ) {
            for ( Comps ccc : comps ) {
                compHash.put( ccc.getPid(), ccc );
            }
            try {
                Connection conn = db.openConnection();
                Statement stm = conn.createStatement();
                String sql = "SELECT * FROM ht_stats where ht_month='" + monthh + "' and ht_year='" + yearr + "'";
                ResultSet rset = stm.executeQuery( sql );
                while ( rset.next() ) {
                    HTStat hts = new HTStat();
                    hts.setPid( rset.getString( "pid" ) );
                    hts.setFamPid( rset.getString( "fam_pid" ) );
                    hts.setCompPid( rset.getString( "comp_pid" ) );
                    hts.setMonth( rset.getString( "ht_month" ) );
                    hts.setYear( rset.getString( "ht_year" ) );
                    hts.setHomeTaught( rset.getString( "ht_stat" ) );
                    hp.put( hts.getFamPid(), hts );
                }
                // associate the htstat with the family
                for ( Family ff : families ) {
                    if ( hp.containsKey( ff.getPid() ) ) {
                        HTStat gr = hp.get( ff.getPid() );
                        ff.setHtStat( gr );
                        if ( compHash.containsKey( gr.getCompPid() ) ) {
                            ff.setComps( compHash.get( gr.getCompPid() ) );
                        }
                        // there is no comppid in the htstat
                        // but check to see that comps havent
                        // since the htstat creation been
                        // assigned.
                        else {
                            Calendar calNow = Calendar.getInstance();
                            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd" );
                            int mm = calNow.get( Calendar.MONTH );
                            // if the current month is equal to
                            // or less than input 'monthh'
                            // create a new htstat for family
                            // otherwise, do not overwrite old
                            // htstat b/c family did not have
                            // current comp assiated with it.
                            // this way comps are associated with family from
                            // now to inifinite
                            try {
                                int inputMonth = Integer.parseInt( monthh );
                                int inputYear = Integer.parseInt( yearr );
                                if ( inputMonth >= mm && inputYear >= yy ) {
                                    Comps cmp = new Comps();
                                    cmp.fetchForFamPid( ff.getPid() );
                                    ff.setComps( cmp );
                                    // store the comp pid that is currently
                                    // unset
                                    gr.setCompPid( cmp.getPid() );
                                    gr.store();
                                }
                            } catch ( Exception e ) {
                                // bad imput variable monthh
                                // do nothing
                            }
                        }
                    }
                    // no htstat exists. set emtpy htstat
                    // with current comps if comps exists
                    else {
                        Comps cmp = new Comps();
                        cmp.fetchForFamPid( ff.getPid() );
                        HTStat htstat = new HTStat();
                        htstat.setHomeTaught( "NONE" );
                        htstat.setFamPid( ff.getPid() );
                        htstat.setMonth( monthh );
                        htstat.setYear( yearr );
                        ff.setHtStat( htstat );
                        ff.setComps( cmp );
                        try {
                            int inputMonth = Integer.parseInt( monthh );
                            int inputYear = Integer.parseInt( yearr );
                            if ( inputMonth >= mm && inputYear >= yy ) {
                                cmp.fetchForFamPid( ff.getPid() );
                                ff.setComps( cmp );
                                // store the comp pid that is currently unset
                                htstat.setCompPid( cmp.getPid() );
                            }
                        } catch ( Exception e ) {
                            // bad imput variable monthh
                            // do nothing
                        }
                        htstat.store();
                    }
                }
                rset.close();
                stm.close();
                conn.close();
            } catch ( SQLException e ) {
                e.printStackTrace();

            } catch ( RuleException e ) {
                e.printStackTrace();
            }
        }
        return families;
    }

    // to have a stat for a family we only need a valid fampid
    // there may be a visist to a family by someone other than
    // ht companions.
    private void assertStorable() throws RuleException {
        if ( !StringUtil.isSet( this.famPid ) ) {
            throw new RuleException( "HTStat must have a valid famPid: "+this.famPid);
        }

    }

    /**
     * @return the compPid
     */
    public String getCompPid() {
        if ( compPid == null || compPid.length() < 1 ) {
            return "NA";
        }
        return compPid;
    }

    /**
     * @param compPid
     *            the compPid to set
     */
    public void setCompPid( String compPid ) {
        this.compPid = compPid;
    }

    /**
     * @return the famPid
     */
    public String getFamPid() {
        if ( famPid == null ) {
            return "";
        }
        return famPid;
    }

    /**
     * @param famPid
     *            the famPid to set
     */
    public void setFamPid( String famPid ) {
        this.famPid = famPid;
    }

    /**
     * @return the homeTaught
     */
    public String getHomeTaught() {
        if ( homeTaught == null || homeTaught.length() < 1 ) {
            return "";
        }
        return homeTaught;
    }

    /**
     * @param homeTaught
     *            the homeTaught to set
     */
    public void setHomeTaught( String homeTaught ) {
        this.homeTaught = homeTaught;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        if ( month == null || month.length() < 1 ) {
            return mm + "";
        }
        return month;
    }

    /**
     * @param month
     *            the month to set
     */
    public void setMonth( String month ) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public String getYear() {
        if ( year == null || year.length() < 1 ) {
            return yy + "";
        }
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear( String year ) {
        this.year = year;
    }

    public void store() throws RuleException {
        DB db = new DB();
        String sql = "";
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            // if pid exists, update the values with new entry
            if ( StringUtil.isSet( this.getPid() ) ) {
                String sqlUpdate = "update " + TABLE_NAME + " " + "set ht_stat='" + this.homeTaught + "' " + ", comp_pid='" + this.compPid
                + "' , fam_pid='" + this.famPid + "' , ht_month='" + this.month + "' , ht_year='" + this.year + "'" + "where pid='"
                + this.getPid() + "'";
                stm.executeUpdate( sqlUpdate );
            }
            // this is a new htstats and we need to overwrite
            // the current stat that may already be in db.
            else {
                assertStorable();
                String sqlDelete = "delete from " + TABLE_NAME + " where comp_pid='" + this.compPid + "' and fam_pid='" + this.famPid
                + "' and ht_month='" + this.month + "' and ht_year='" + this.year + "'";
                stm.executeUpdate( sqlDelete );
                sql = "insert into " + TABLE_NAME + " (" + TABLE_COLS + ") values(null, '" + this.getFamPid() + "', '" + this.getCompPid() + "','"
                + this.getMonth() + "','" + this.getYear() + "','" + this.getHomeTaught() + "')";
                stm.executeUpdate( sql );
                //set the pid after storing obj
                ResultSet rset = stm.getGeneratedKeys();
                if(rset.next()){
                    this.setPid( rset.getString( 1 ) );
                }
            }
            stm.close();
            conn.close();
        } catch ( SQLException e ) {
            System.out.println( sql );
            e.printStackTrace();
        }
    }

    /**
     * @return the pid
     */
    public String getPid() {
        if ( pid == null ) {
            return "";
        }
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid( String pid ) {
        this.pid = pid;
    }

    /**
     * @return the fam
     */
    public Family getFam() {
        return fam;
    }

    /**
     * @param fam
     *            the fam to set
     */
    public void setFam( Family fam ) {
        this.fam = fam;
    }

    /**
     * @return the totFam
     */
    public String getTotFam() {
        return totFam;
    }

    /**
     * @param totFam
     *            the totFam to set
     */
    public void setTotFam( String totFam ) {
        this.totFam = totFam;
    }

    /**
     * @return the totFamHTaught
     */
    public String getTotFamHTaught() {
        return totFamHTaught;
    }

    /**
     * @param totFamHTaught
     *            the totFamHTaught to set
     */
    public void setTotFamHTaught( String totFamHTaught ) {
        this.totFamHTaught = totFamHTaught;
    }

    /**
     * @return the totFamWithHT
     */
    public String getTotFamWithHT() {
        return totFamWithHT;
    }

    /**
     * @param totFamWithHT
     *            the totFamWithHT to set
     */
    public void setTotFamWithHT( String totFamWithHT ) {
        this.totFamWithHT = totFamWithHT;
    }

    /**
     * @return the totFamWithOutHT
     */
    public String getTotFamWithOutHT() {
        return totFamWithOutHT;
    }

    /**
     * @param totFamWithOutHT
     *            the totFamWithOutHT to set
     */
    public void setTotFamWithOutHT( String totFamWithOutHT ) {
        this.totFamWithOutHT = totFamWithOutHT;
    }

    /**
     * @return the totPercentHT
     */
    public String getTotPercentHT() {
        return totPercentHT;
    }

    /**
     * @param totPercentHT
     *            the totPercentHT to set
     */
    public void setTotPercentHT( String totPercentHT ) {
        this.totPercentHT = totPercentHT;
    }

    /**
     * @return the totPercentHTOfFamsWithHT
     */
    public String getTotPercentHTOfFamsWithHT() {
        return totPercentHTOfFamsWithHT;
    }

    /**
     * @param totPercentHTOfFamsWithHT
     *            the totPercentHTOfFamsWithHT to set
     */
    public void setTotPercentHTOfFamsWithHT( String totPercentHTOfFamsWithHT ) {
        this.totPercentHTOfFamsWithHT = totPercentHTOfFamsWithHT;
    }

    /**
     * @return the totCompsNotContacted
     */
    public String getTotCompsNotContacted() {
        return totCompsNotContacted;
    }

    /**
     * @param totCompsNotContacted
     *            the totCompsNotContacted to set
     */
    public void setTotCompsNotContacted( String totCompsNotContacted ) {
        this.totCompsNotContacted = totCompsNotContacted;
    }

    public static void main( String[] args ) {
        HTStat hts = new HTStat();

        ArrayList<Family> famList = hts.getAllFamiliesForDate( "1", "2009" );
        for ( Family fam : famList ) {
            try {
                System.out.println("-----------------");
                System.out.println( "Comps: " + fam.getComps().getComp1().getFirstName() + " " + fam.getComps().getComp1().getLastName() + "" );
                System.out.println( "Family pid: "+fam.getPid() + " " + fam.getFirstName() + " " + fam.getName()+" " );
                for(Member kid: fam.getSiblings()){
                    System.out.println( "    "+kid.getPid() + " " + kid.getFirstName() + " " + kid.getLastName()+" " );
                }
                System.out.println("  -->"+ fam.getHtStat().getPid());
                System.out.println("-----------------");
            } catch ( Exception e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // break;
            }
        }
        // HashMap hm = hts.getCompHashMap();
        // Set<String> set = hm.keySet();
        // for(String ss: set){
        // System.out.println(((Comps)hm.get( ss )).getComp1().getFirstName());
        //      }
        //HashMap<String, Comps> hello =  hts.getCompHashMap(); 

    }

    public String getTotFamHTaughtOther() {
        return totFamHTaughtOther;
    }

    public void setTotFamHTaughtOther( String totFamHTaughtOther ) {
        this.totFamHTaughtOther = totFamHTaughtOther;
    }

}
