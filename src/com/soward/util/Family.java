/**
 * Title: Family.java
 * Description: HomeTeaching
 * Date: Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.soward.domain.MemberDomain;
import com.soward.exception.DNFException;
import com.soward.object.Comps;
import com.soward.object.HTStat;
import com.soward.object.Member;

public class Family {
    public Member father;

    public Member mother;

    public ArrayList<Member> siblings;

    public String pid;

    public String name;

    public String phone;

    public String addr1;

    public String addr2;

    public HTStat htStat;

    public Comps comps;

    public String eq_fam;
    public boolean hasNote;

    public String hasHT;
    public String familyid;
    public HashMap<String, HTStat>HTStatList;

    public Family() {
        this.father = new Member();
        this.mother = new Member();
        this.siblings = new ArrayList<Member>();
        this.pid = new String();
        this.htStat = new HTStat();
        this.comps = new Comps();
        this.eq_fam = "TRUE";
        this.hasHT = "FALSE";
        this.familyid="";
        this.HTStatList = new HashMap<String, HTStat>();

    }

    public Family( Member father, Member mother, ArrayList<Member> siblings ) {
        this.father = father;
        this.mother = mother;
        this.siblings = siblings;
    }

    /**
     * @return the addr1
     */
    public String getAddr1() {
        String famAddr = "";
        if ( this.father.getAddr1().length() > 0 ) {
            famAddr = father.getAddr1();
        } else {
            famAddr = mother.getAddr1();
        }
        return famAddr;
    }

    /**
     * @param addr1
     *            the addr1 to set
     */
    public void setAddr1( String addr1 ) {
        this.addr1 = addr1;
    }

    /**
     * @return the addr2
     */
    public String getAddr2() {
        String famAddr = "";
        if ( this.father.getAddr2().length() > 0 ) {
            famAddr = father.getAddr2();
        } else {
            famAddr = mother.getAddr2();
        }
        return famAddr;
    }

    /**
     * @param addr2
     *            the addr2 to set
     */
    public void setAddr2( String addr2 ) {
        this.addr2 = addr2;
    }

    /**
     * @return the last name
     */
    public String getName() {
        String famAddr = "";
        if ( this.father.getLastName().length() > 0 ) {
            famAddr = father.getLastName();
        } else if ( this.mother.getLastName().length() > 0 ) {
            famAddr = mother.getLastName();
        }else if(!siblings.isEmpty()){
            famAddr = siblings.get( 0 ).getLastName();
        }else{
            famAddr = null;
        }
        return famAddr;
    }
    /**
     * @return the first name
     */
    public String getFamFirstName() {
        String famAddr = "";
        if ( this.father.getFirstName().length() > 0 ) {
            famAddr = father.getFirstName();
        } else {
            famAddr = mother.getFirstName();
        }
        return famAddr;
    }

    /**
     * @return the first name of father or if no father, returns mothers first
     *         name
     */
    public String getFirstName() {
        String famAddr = "";
        if ( this.father.getFirstName().length() > 0 ) {
            famAddr = father.getFirstName();
        } else {
            famAddr = mother.getFirstName();
        }
        return famAddr;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        String famAddr = "";
        if ( this.father.getPhone().length() > 0 ) {
            famAddr = father.getPhone();
        } else {
            famAddr = mother.getPhone();
        }
        return famAddr;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone( String phone ) {
        this.phone = phone;
    }

    /**
     * @return the father
     */
    public Member getFather() {
        return father;
    }

    /**
     * @param father
     *            the father to set
     */
    public void setFather( Member father ) {
        this.father = father;
    }

    /**
     * @return the mother
     */
    public Member getMother() {
        return mother;
    }

    /**
     * @param mother
     *            the mother to set
     */
    public void setMother( Member mother ) {
        this.mother = mother;
    }

    /**
     * @return the siblings
     */
    public ArrayList<Member> getSiblings() {
        return siblings;
    }

    /**
     * @param siblings
     *            the siblings to set
     */
    public void setSiblings( ArrayList<Member> siblings ) {
        this.siblings = siblings;
    }

    /*
     * store fam in db
     */
    public void store() {
        String famNum = this.pid;
        if ( !StringUtil.isSet( this.pid ) ) {
            String famName = "";
            // if pid not set, this is a new family.
            if(father!=null&&father.lastName.length()>0){
                famName =  father.lastName+"_"+father.firstName;
            }
            else if(mother!=null&&mother.lastName.length()>0){
                famName =  mother.lastName+"_"+mother.firstName;
            }
            else {
                famName =  siblings.get( 0 ).lastName+"_"+siblings.get( 0 ).firstName;
            }

            //elder responsiblity if father is anything but highpriest
            famNum = this.saveId( famName, 
                    father.getMem_role().equals( MemberDomain.ELDER )||
                    father.getMem_role().equals( MemberDomain.PRIEST )||
                    father.getMem_role().equals( MemberDomain.TEACHER)||
                    father.getMem_role().equals( MemberDomain.DEACON )||
                    father.getMem_role().equals( MemberDomain.UNORDAINED ));
        }
        //update family table is hasht and eqfam
        else{
            this.updateFam(this.pid);
        }
        father.setFamilypid( famNum );
        mother.setFamilypid( famNum );
//      mother.setAddr1( father.getAddr1() );
//      mother.setAddr2( father.getAddr2() );
//      mother.setPhone( father.getPhone() );
        this.father.store();
        this.mother.store();
        for ( Member kid : this.siblings ) {
            kid.setFamilypid( famNum );
            kid.store();
        }
    }

    public String saveId( String lastName, boolean isElder ) {
        String key = "";
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = null;
            String isEld = isElder?"TRUE":"FALSE";
            String sql = "insert into family (pid, familyid, has_ht, eq_fam) values(null, ?, 'FALSE', '"+isEld+"')";
            pstmt = conn.prepareStatement( sql );
            pstmt.setString( 1, lastName );
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if ( keys.next() ) {
                key = keys.getString( 1 );
            }
            keys.close();
            conn.close();
            pstmt.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return key;
    }
    /**
     * udpates family table with hasHT and eqFam
     * @param famPid
     * @return
     */
    public String updateFam( String famPid ) {
        String key = "";
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            String sql = "update family set familyid='"+this.getFamilyid()+"'" +
            ", has_ht='"+this.getHasHT()+"', eq_fam='"+this.getEq_fam()+"' where pid='"+famPid+"'";

//          System.out.println(sql);
            stm.executeUpdate( sql );
            ResultSet keys = stm.getGeneratedKeys();
            if ( keys.next() ) {
                key = keys.getString( 1 );
            }
            keys.close();
            conn.close();
            stm.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return key;
    }

    /**
     * Used to populate the view family screen to set a family pertaining or not
     * pertaining to the elders quorum
     * 
     * @return ArrayList
     */
    public ArrayList<Family> getAllFamilies() {
        ArrayList<Family> famList = new ArrayList<Family>();
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            String sql = "SELECT * FROM member, family where family.pid=member.fam_pid order by fam_pid";
            // where fam_pid in (SELECT pid FROM family )";

            // System.out.println(sql);
            stm.executeQuery( sql );
            ResultSet rset = stm.executeQuery( sql );
            // update familyPid to determine when a family starts/stops
            String currFamPid = "";
            Family tempFam = new Family();
            while ( rset.next() ) {
                String famPid = rset.getString( "fam_pid" );
                // fampid has changed so add family to array
                // and create new tempFam
                if ( currFamPid.length() > 1 && !currFamPid.equals( famPid ) ) {
                    famList.add( tempFam );
                    tempFam = new Family();
                    // new famPid
                    currFamPid = famPid;
                }
                currFamPid = famPid;
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
                if ( tt.isFather() ) {
                    tempFam.setFather( tt );
                } else if ( tt.isMother() ) {
                    tempFam.setMother( tt );
                } else {
                    tempFam.siblings.add( tt );
                }
                tempFam.setEq_fam( rset.getString( "eq_fam" ) );
                tempFam.setPid( famPid );
                tempFam.setHasHT( rset.getString( "has_ht" ) );
            }
            // result ended, add last family
            famList.add( tempFam );
            rset.close();
            conn.close();
            stm.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return famList;
    }

    public ArrayList<Family> getAll() {
        ArrayList<Family> famList = new ArrayList<Family>();
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            String sql = "select * from member order by fam_pid";
            // where fam_pid in (SELECT pid FROM family )";

            // System.out.println(sql);
            stm.executeQuery( sql );
            ResultSet rset = stm.executeQuery( sql );
            // update familyPid to determine when a family starts/stops
            String currFamPid = "";
            Family tempFam = new Family();
            while ( rset.next() ) {
                String famPid = rset.getString( "fam_pid" );
                // fampid has changed so add family to array
                // and create new tempFam
                if ( currFamPid.length() > 1 && !currFamPid.equals( famPid ) ) {
                    famList.add( tempFam );
                    tempFam = new Family();
                    // new famPid
                    currFamPid = famPid;
                }
                currFamPid = famPid;
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
                if ( tt.isFather() ) {
                    tempFam.setFather( tt );
                } else if ( tt.isMother() ) {
                    tempFam.setMother( tt );
                } else {
                    tempFam.siblings.add( tt );
                }
                tempFam.setPid( famPid );
            }
            // result ended, add last family
            famList.add( tempFam );
            rset.close();
            conn.close();
            stm.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return famList;
    }
    /**
     * Gets all families that pertain to the EQ
     * @return ArrayList
     */
    public ArrayList<Family> getAllEQFamilies() {
        ArrayList<Family> famList = new ArrayList<Family>();
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            String sql = "select * from member where fam_pid in (select pid from family where eq_fam='TRUE') order by fam_pid";
            // where fam_pid in (SELECT pid FROM family )";

            // System.out.println(sql);
            stm.executeQuery( sql );
            ResultSet rset = stm.executeQuery( sql );
            // update familyPid to determine when a family starts/stops
            String currFamPid = "";
            Family tempFam = new Family();
            while ( rset.next() ) {
                String famPid = rset.getString( "fam_pid" );
                // fampid has changed so add family to array
                // and create new tempFam
                if ( currFamPid.length() > 1 && !currFamPid.equals( famPid ) ) {
                    famList.add( tempFam );
                    tempFam = new Family();
                    // new famPid
                    currFamPid = famPid;
                }
                currFamPid = famPid;
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
                if ( tt.isFather() ) {
                    tempFam.setFather( tt );
                } else if ( tt.isMother() ) {
                    tempFam.setMother( tt );
                } else {
                    tempFam.siblings.add( tt );
                }
                tempFam.setPid( famPid );
            }
            // result ended, add last family
            famList.add( tempFam );
            rset.close();
            conn.close();
            stm.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return famList;
    }

    /*
     * get all families that are not assigned ht
     */
    public ArrayList<Family> getAllFamWithOutHT() {
        ArrayList<Family> famList = new ArrayList<Family>();
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            String sql = "select * from member where fam_pid in (SELECT pid FROM family where has_ht='FALSE' )";

            // System.out.println(sql);
            stm.executeQuery( sql );
            ResultSet rset = stm.executeQuery( sql );
            // update familyPid to determine when a family starts/stops
            HashMap<String, ArrayList<Member>> famHash = new HashMap<String, ArrayList<Member>>();
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

                if(famHash.containsKey( famPid )){
                    famHash.get( famPid ).add( tt );
                }else{
                    ArrayList<Member> fa = new ArrayList<Member>();
                    fa.add( tt);
                    famHash.put( famPid, fa );
                }
            }
            Set set = famHash.keySet();
            Iterator iter =  set.iterator();
            while(iter.hasNext()){
                String key = (String)iter.next();
                Family tempFam = new Family();
                for(Member tt: famHash.get( key )){
                    if ( tt.isFather() ) {
                        tempFam.setFather( tt );
                    } else if ( tt.isMother() ) {
                        tempFam.setMother( tt );
                    } else {
                        tempFam.siblings.add( tt );
                    }
                    tempFam.setPid( key );
                }
                famList.add( tempFam );
            }
            rset.close();
            conn.close();
            stm.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return HomeTeachingUtils.alphabetizeFamList( famList );
    }

    public void fetch( String famPid ) throws DNFException {
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            String sql = "select * from member, family where fam_pid ='" + famPid + "' and member.fam_pid=family.pid";
//          select * from member, family where family.pid ='906' and member.fam_pid=family.pid

//          System.out.println(sql);
            stm.executeQuery( sql );
            ResultSet rset = stm.executeQuery( sql );
            while ( rset.next() ) {
                String name = rset.getString( "pid" );
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
                if ( tt.isFather() ) {
                    setFather( tt );
                } else if ( tt.isMother() ) {
                    setMother( tt );
                } else {
                    this.siblings.add( tt );
                }
                this.setPid( famPid );
                this.setHasHT( rset.getString( "has_ht" ) );
            }
            rset.close();
            conn.close();
            stm.close();

        } catch ( SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid( String pid ) {
        this.pid = pid;
    }

    public static void main( String[] args ) {
       ArrayList<Family> famList = new Family().getAllFamWithOutHT();
       for(Family fam: famList){
           System.out.println(fam.getFather().getFirstName());
       }
//        Connection conn;
//        com.soward.db.DB db = new com.soward.db.DB();
//        Statement stm = null;
//        try {
//            conn = db.openConnection();
//            stm = conn.createStatement();
//            String sql = "select fam_pid from ht_stats where ht_year='2008' and ht_month='11' and ht_stat='YES'";
//
//            stm.executeQuery( sql );
//            ResultSet rset = stm.executeQuery( sql );
//            ArrayList<String> al = new ArrayList<String>();
//            while ( rset.next() ) {
//                al.add(rset.getString("fam_pid"));
//
//            }
//            for(String i: al){
//                sql = "update ht_stats set ht_stat='YES' where ht_year='2007' and ht_month='11' and fam_pid='"+i+"'";
//                System.out.println(sql);
//                stm.executeUpdate(sql);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }

//      Family cc = new Family();
//      ArrayList<Family> families = cc.getAll();
//      for ( Family tt : families ) {
//      // System.out.println(tt.getFather().getFirstName()+"
//      // "+tt.getFather().getLastName());
//      System.out.println( tt.getName() );
//      tt.getMother().setLastName( tt.getName() );
//      tt.getMother().store();
//      for ( Member kid : tt.getSiblings() ) {
//      kid.setLastName( tt.getName() );
//      kid.store();
//      }
//      // there are repeats in the viewFamilies.jsp fathers names
//      }

    }

    /**
     * @return the htStat
     */
    public HTStat getHtStat() {
        return htStat;
    }

    /**
     * @param htStat
     *            the htStat to set
     */
    public void setHtStat( HTStat htStat ) {
        this.htStat = htStat;
    }

    /**
     * @return the comps
     */
    public Comps getComps() {
        return comps;
    }

    /**
     * @param comps
     *            the comps to set
     */
    public void setComps( Comps comps ) {
        this.comps = comps;
    }

    /**
     * @return the eq_fam
     */
    public String getEq_fam() {
        return eq_fam;
    }

    /**
     * @param eq_fam
     *            the eq_fam to set
     */
    public void setEq_fam( String eq_fam ) {
        this.eq_fam = eq_fam;
    }

    /**
     * @return String the hasHT
     */
    public String getHasHT() {
        return hasHT;
    }

    /**
     * @return boolean
     */
    public boolean hasHomeTeacher(){
        if(StringUtil.isSet( getHasHT() )){
            if(getHasHT().equalsIgnoreCase( "true" )){
                return true;
            }
        }
        return false;
    }
    /**
     * @param hasHT
     *            the hasHT to set
     */
    public void setHasHT( String hasHT ) {
        this.hasHT = hasHT;
    }

    /**
     * @return the familyid
     */
    public String getFamilyid() {
        if(StringUtil.isSet( this.father.getLastName())){
            familyid = this.father.getLastName();
        }
        else if(StringUtil.isSet( this.mother.getLastName())){
            familyid = this.mother.getLastName();
        }
        return familyid;
    }

    /**
     * @param familyid the familyid to set
     */
    public void setFamilyid( String familyid ) {
        this.familyid = familyid;
    }

    /**
     * @return the hTStatList
     */
    public HashMap<String, HTStat> getHTStatList() {
        return HTStatList;
    }

    /**
     * @param statList the hTStatList to set
     */
    public void setHTStatList( HashMap<String, HTStat> statList ) {
        HTStatList = statList;
    }

    /**
     * @return the hasNote
     */
    public boolean isHasNote() {
        return hasNote;
    }

    /**
     * @param hasNote the hasNote to set
     */
    public void setHasNote( boolean hasNote ) {
        this.hasNote = hasNote;
    }
}
