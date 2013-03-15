/**
 * Title: Comps.java
 * Description: HomeTeaching
 * Date: Jul 1, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import com.soward.db.DB;
import com.soward.domain.MemberDomain;
import com.soward.exception.DNFException;
import com.soward.util.Family;
import com.soward.util.HomeTeachingUtils;
import com.soward.util.StringUtil;

public class Comps {
    private static final String COL_NAMES = "pid, comp1, comp2, comp3, has_district, assign_pid ";

    public String comp1Pid;

    public String comp2Pid;

    public String comp3Pid;

    public Member comp1;

    public Member comp2;

    public Member comp3;

    public String pid;

    public String assignPid;

    public ArrayList<Family> famList;

    private String district;

    /**
     * @param comp1Pid
     * @param comp2Pid
     * @param comp3Pid
     * @param comp1
     * @param comp2
     * @param comp3
     * @param pid
     */
    public Comps() {
        this.comp1Pid = new String();
        this.comp2Pid = new String();
        this.comp3Pid = new String();
        this.comp1 = new Member();
        this.comp2 = new Member();
        this.comp3 = new Member();
        this.pid = new String();
        this.assignPid = new String();
        this.famList = new ArrayList<Family>();
    }

    public boolean hasComps() {
        boolean hasComps = false;
        if ( ( StringUtil.isSet( comp1.getFirstName() ) ) || ( StringUtil.isSet( comp1.getLastName() ) )
                || ( StringUtil.isSet( comp2.getFirstName() ) ) || ( StringUtil.isSet( comp3.getLastName() ) )
                || ( StringUtil.isSet( comp3.getFirstName() ) ) || ( StringUtil.isSet( comp3.getLastName() ) ) ) {
            hasComps = true;
        }
        return hasComps;
    }

    public ArrayList<Comps> getViewAssignments() {
        DB db = new DB();
        String sql = "select pid from comps";
        ArrayList<Comps> cccList = new ArrayList<Comps>();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                Comps ccc = new Comps();
                ccc.fetch( rset.getString( "pid" ) );
                ccc.fetchFamilyList();
                // ccc.setDistrict(rset.getString( "dist_num" ));
                cccList.add( ccc );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return cccList;
    }

    /**
     * get all the comp names in this compaionship
     * 
     * @return string
     */
    public String getCompNames() {
        String names = "";
        if ( StringUtil.isSet( this.getComp1().getLastName() ) ) {
            names += "1. "+this.getComp1().getLastName() + ", " + this.getComp1().getFirstName();
        }
        if ( StringUtil.isSet( this.getComp2().getLastName() ) ) {
            names += "<br>2. "+this.getComp2().getLastName() + ", " + this.getComp2().getFirstName();
        }
        if ( StringUtil.isSet( this.getComp3().getLastName() ) ) {
            names += "<br>3. "+this.getComp3().getLastName() + ", " + this.getComp3().getFirstName();
        }
        return names;
    }

    /*
     * @param newCompList @param district Number Assign comp family list
     */
    public void createAssignment( ArrayList<String> familyList, String compNum ) {
        com.soward.db.DB db = new DB();
        boolean isNum = false;
        int dn = 0;
        try {
            dn = Integer.parseInt( compNum );
            isNum = true;
        } catch ( Exception e ) {
            // not numeric
        }
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            if ( familyList != null && !familyList.isEmpty() ) {
                // first remove current families associated with comp
                // stm.executeUpdate("update family set has_ht='FALSE' where pid
                // in (select fam_pid from assign_comps where assignments_pid in
                // (select pid from assignments where comp_pid=
                // '"+compNum+"'))");
                // stm.executeUpdate("delete from assign_comps where
                // assignments_pid in (select pid from assignments where
                // comp_pid= '"+compNum+"')");
                // stm.executeUpdate("delete from assignments where comp_pid=
                // '"+compNum+"'");
                String key = "";
                ResultSet rset = stm.executeQuery( "select pid from assignments where comp_pid='" + compNum + "'" );
                if ( rset.next() ) {
                    key = rset.getString( "pid" );
                    rset.close();
                } else {
                    stm.executeUpdate( "insert into assignments (pid, comp_pid) values(null, '" + compNum + "')" );
                    ResultSet keys = stm.getGeneratedKeys();
                    if ( keys.next() ) {
                        key = keys.getString( 1 );
                    }
                    keys.close();
                }
                for ( String famPid : familyList ) {
                    stm.executeUpdate( "update family set has_ht='TRUE' where pid='" + famPid + "'" );
                    stm.executeUpdate( "insert into assign_comps (pid, assignments_pid, fam_pid)" + "values(null, '" + key + "', '" + famPid + "')" );
                }
                conn.close();
                stm.close();
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    /*
     * remove family from compaionship @param family pid Number
     */
    public void removeFam( String famPid ) {
        com.soward.db.DB db = new DB();
        boolean isNum = false;
        int dn = 0;
        try {
            dn = Integer.parseInt( famPid );
            isNum = true;
        } catch ( Exception e ) {
            // not numeric
        }
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            // first remove current families associated with comp
            stm.executeUpdate( "update family set has_ht='FALSE' where pid='" + famPid + "'" );
            stm.executeUpdate( "delete from assign_comps where fam_pid='" + famPid + "'" );
            conn.close();
            stm.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    /*
     * get all comps who are not in a district
     */
    public ArrayList<Comps> getAllCompsWithOutDistrict() {
        ArrayList<Comps> compList = new ArrayList<Comps>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( "select " + Comps.COL_NAMES + " from comps where has_district='FALSE'" );// group
                                                                                                                        // by
                                                                                                                        // pid
                                                                                                                        // desc");
            while ( rset.next() ) {
                Comps cl = new Comps();
                Member com1 = new Member();
                Member com2 = new Member();
                Member com3 = new Member();
                com1.fetch( rset.getString( "comp1" ) );
                com2.fetch( rset.getString( "comp2" ) );
                com3.fetch( rset.getString( "comp3" ) );
                cl.setPid( rset.getString( "pid" ) );
                cl.setAssignPid( rset.getString( "assign_pid" ) );
                cl.setComp1( com1 );
                cl.setComp2( com2 );
                if ( com3 == null ) {
                    cl.setComp3( new Member() );
                } else {
                    cl.setComp3( com3 );
                }
                compList.add( cl );
            }
            conn.close();
            stm.close();
            rset.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( DNFException dnf ) {
            dnf.printStackTrace();
        }
        return compList;
    }

    /*
     * get all families associated with this companionship
     */
    public ArrayList<Family> getAllFamForComps( String compPid ) {
        ArrayList<Family> familyList = new ArrayList<Family>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select fam_pid from assign_comps where assignments_pid in " + "(select pid from assignments where comp_pid='" + compPid
                    + "')";
            ResultSet rset = stm.executeQuery( sql );// group by pid desc");
            while ( rset.next() ) {
                Family cl = new Family();
                cl.fetch( rset.getString( "fam_pid" ) );
                familyList.add( cl );
            }
            conn.close();
            stm.close();
            rset.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( DNFException dnf ) {
            dnf.printStackTrace();
        }
        return familyList;
    }

    /*
     * get all comps
     */
    public ArrayList<Comps> getAll() {
        ArrayList<Comps> compList = new ArrayList<Comps>();
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( "select " + Comps.COL_NAMES + " from comps" );// group
                                                                                                // by
                                                                                                // pid
                                                                                                // desc");
            while ( rset.next() ) {
                Comps cl = new Comps();
                Member com1 = new Member();
                Member com2 = new Member();
                Member com3 = new Member();
                com1.fetch( rset.getString( "comp1" ) );
                com2.fetch( rset.getString( "comp2" ) );
                com3.fetch( rset.getString( "comp3" ) );
                cl.setPid( rset.getString( "pid" ) );
                cl.setComp1( com1 );
                cl.setComp2( com2 );
                if ( com3 == null ) {
                    cl.setComp3( new Member() );
                } else {
                    cl.setComp3( com3 );
                }
                compList.add( cl );
            }
            conn.close();
            stm.close();
            rset.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( DNFException dnf ) {
            dnf.printStackTrace();
        }
        return HomeTeachingUtils.alphabetizeCompList( compList );
    }

    /**
     * @return the comp1
     */
    public Member getComp1() {
        return comp1;
    }

    /**
     * @param comp1
     *            the comp1 to set
     */
    public void setComp1( Member comp1 ) {
        this.comp1 = comp1;
    }

    /**
     * @return the comp1Pid
     */
    public String getComp1Pid() {
        return comp1Pid;
    }

    /**
     * @param comp1Pid
     *            the comp1Pid to set
     */
    public void setComp1Pid( String comp1Pid ) {
        this.comp1Pid = comp1Pid;
    }

    /**
     * @return the comp2
     */
    public Member getComp2() {
        return comp2;
    }

    /**
     * @param comp2
     *            the comp2 to set
     */
    public void setComp2( Member comp2 ) {
        this.comp2 = comp2;
    }

    /**
     * @return the comp2Pid
     */
    public String getComp2Pid() {
        return comp2Pid;
    }

    /**
     * @param comp2Pid
     *            the comp2Pid to set
     */
    public void setComp2Pid( String comp2Pid ) {
        this.comp2Pid = comp2Pid;
    }

    /**
     * @return the compPid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param compPid
     *            the compPid to set
     */
    public void setPid( String compPid ) {
        this.pid = compPid;
    }

    /**
     * @return the comp3
     */
    public Member getComp3() {
        return comp3;
    }

    /**
     * @param comp3
     *            the comp3 to set
     */
    public void setComp3( Member comp3 ) {
        this.comp3 = comp3;
    }

    public void fetch( String pid ) throws DNFException {
        com.soward.db.DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "select * from comps where pid='" + pid + "'";
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                this.setPid( rset.getString( "pid" ) );
                Member c1 = new Member();
                Member c2 = new Member();
                Member c3 = new Member();
                c1.fetch( rset.getString( "comp1" ) );
                this.comp1 = c1;
                c2.fetch( rset.getString( "comp2" ) );
                this.comp2 = c2;
                c3.fetch( rset.getString( "comp3" ) );
                this.setAssignPid( rset.getString( "assign_pid" ) );
                this.comp3 = c3;
                this.comp1Pid = ( this.comp1.getPid() );
                this.comp2Pid = ( this.comp2.getPid() );
                if ( this.comp3 == null ) {
                    this.comp3 = new Member();
                } else {
                    this.comp3Pid = ( this.comp3.getPid() );
                }

            }
            conn.close();
            stm.close();
            rset.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } catch ( DNFException dnf ) {
            dnf.printStackTrace();
        }

    }

    /**
     * @return the comp3Pid
     */
    public String getComp3Pid() {
        return comp3Pid;
    }

    /**
     * @param comp3Pid
     *            the comp3Pid to set
     */
    public void setComp3Pid( String comp3Pid ) {
        this.comp3Pid = comp3Pid;
    }

    /**
     * @return the assignPid
     */
    public String getAssignPid() {
        return assignPid;
    }

    /**
     * @param assignPid
     *            the assignPid to set
     */
    public void setAssignPid( String assignPid ) {
        this.assignPid = assignPid;
    }

    /**
     * @return the famList
     */
    public ArrayList<Family> getFamList() {
        return famList;
    }

    /**
     * @param famList
     *            the famList to set
     */
    public void setFamList( ArrayList<Family> famList ) {
        this.famList = famList;
    }

    /**
     * sets list of families for this Comps
     */
    public void fetchFamilyList() {
        com.soward.db.DB db = new DB();
        String sql = "SELECT  assign_comps.fam_pid FROM  assign_comps, " + " comps, assignments where assignments.comp_pid=comps.pid "
                + " and comps.pid='" + this.pid + "' " + " and assign_comps.assignments_pid=assignments.pid";
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                Family fff = new Family();
                fff.fetch( rset.getString( "fam_pid" ) );
                this.getFamList().add( fff );
            }
            conn.close();
            stm.close();
            rset.close();
        } catch ( SQLException e ) {
            System.out.println( "SQL: " + sql );
            e.printStackTrace();
        } catch ( DNFException e ) {
            e.printStackTrace();
        }
    }

    /**
     * @return void sets this.comps
     * @param famPid
     */
    public void fetchForFamPid( String famPid ) {
        com.soward.db.DB db = new DB();
        String sql = "select * from comps where pid in (select comp_pid from assignments where pid in"
                + "(select assignments_pid  from assign_comps where fam_pid='" + famPid + "'))";
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                this.setPid( rset.getString( "pid" ) );
                Member c1 = new Member();
                Member c2 = new Member();
                Member c3 = new Member();
                c1.fetch( rset.getString( "comp1" ) );
                this.comp1 = c1;
                c2.fetch( rset.getString( "comp2" ) );
                this.comp2 = c2;
                c3.fetch( rset.getString( "comp3" ) );
                this.setAssignPid( rset.getString( "assign_pid" ) );
                this.comp3 = c3;
                this.comp1Pid = ( this.comp1.getPid() );
                this.comp2Pid = ( this.comp2.getPid() );
                if ( this.comp3 == null ) {
                    this.comp3 = new Member();
                } else {
                    this.comp3Pid = ( this.comp3.getPid() );
                }

            }
            conn.close();
            stm.close();
            rset.close();
        } catch ( SQLException e ) {
            System.out.println( "SQL: " + sql );
            e.printStackTrace();
        } catch ( DNFException e ) {
            e.printStackTrace();
        }

    }

    public void setDistrict( String string ) {
        this.district = string;
    }

    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

}
