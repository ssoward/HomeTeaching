/**
 * Title: Member.java
 * Description: HomeTeaching
 * Date: Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.soward.db.DB;
import com.soward.domain.FamilyDomain;
import com.soward.domain.MemberDomain;
import com.soward.exception.DNFException;
import com.soward.util.Family;
import com.soward.util.StringUtil;
import com.soward.util.UserUtil;

public class Member {
    public String pid;

    public String firstName;

    public String middleName;

    public String lastName;

    public String addr1;

    public String addr2;

    public String email;

    public String phone;

    public String cell;

    public String birth;

    public String familypid;

    public String fam_role;
    public String mem_role;
    public String has_comp;
    public int age;

    public final static String TABLE_NAME = "member";

    private static final String COL_NAMES = "(pid, first_name, last_name,addr1, " +
    "addr2, email, birth, cell,fam_role,fam_pid, mem_role, middle_name, phone, has_comp)";


    public Member() {
        this.pid = new String();
        this.firstName = new String();
        this.middleName = new String();
        this.lastName = new String();
        this.addr1 = new String();
        this.addr2 = new String();
        this.email = new String();
        this.phone = new String();
        this.cell = new String();
        this.birth = new String();
        this.familypid = new String();
        this.fam_role = new String();
        this.mem_role = new String();
        this.has_comp = "FALSE";
    }
    /**
     * gets full name of member
     * @returns full name of member
     */
    public String getFullName(){
        String name = "";
        if(this.getFirstName().length()>0){
            name = this.getFirstName();
            if(this.getLastName().length()>0){
                name +=" "+this.getLastName();
            }
        }
        //if firstname is "" attempt to add last
        else if(this.getLastName().length()>0){
            name +=" "+this.getLastName();
        }
        return name;
    }
    /**
     * @return the addr1
     */
    public String getAddr1() {
        return addr1;
    }

    /**
     * @param addr1
     *            the addr1 to set
     */
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    /**
     * @return the addr2
     */
    public String getAddr2() {
        return addr2;
    }

    /**
     * @param addr2
     *            the addr2 to set
     */
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    /**
     * @return the birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * @param birththe
     *            birth to set
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * @return the cell
     */
    public String getCell() {
        return cell;
    }

    /**
     * @param cell
     *            the cell to set
     */
    public void setCell(String cell) {
        this.cell = cell;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
    public void setPid(String pid) {
        this.pid = pid;
    }

    public Member(String pid, String firstName, String lastName, String addr1, String addr2, String email,
            String homePage, String compName, String phone) {

        this.pid = pid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.email = email;
        this.phone = phone;

    }

    /**
     * @return the familypid
     */
    public String getFamilypid() {
        return familypid;
    }

    /**
     * @param familypid
     *            the familypid to set
     */
    public void setFamilypid(String familypid) {
        this.familypid = familypid;
    }

    /**
     * @return the fam_role
     */
    public String getFam_role() {
        return fam_role;
    }

    /**
     * @param fam_role the fam_role to set
     */
    public void setFam_role(String fam_role) {
        this.fam_role = fam_role;
    }

    /**
     * @return the mem_role
     */
    public String getMem_role() {
        return mem_role;
    }

    /**
     * @param mem_role the mem_role to set
     */
    public void setMem_role(String mem_role) {
        this.mem_role = mem_role;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName
     *            the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public boolean isSet() {
        return this.firstName.length() > 1;
    }

    public void store() {
        com.soward.db.DB db = new com.soward.db.DB();
        Connection conn;
        Statement stm = null;
        try {
            conn = db.openConnection();
            stm = conn.createStatement();
            if(this.assertStorable()){
                //check to see that the family pid is set if so do nothing, else
                // create new family entry and get key for familypid.
                //System.out.println("FamPid: "+familypid);
                if(!StringUtil.isSet( this.familypid)){
                    Family newFamily = new Family();
                    String famName = "";
                    // if pid not set, this is a new family.
                    famName =  lastName+"_"+firstName;

                    this.familypid = newFamily.saveId( famName, getMem_role().equals( MemberDomain.ELDER ) );
                }
                //System.out.println("FamPid: "+familypid);
                //check to see if this is an update
                //by checking for a mempid
                //if memberpid exists, do update
                if(!hasPid()){
                    String sql = "insert into "+TABLE_NAME+" " +
                    COL_NAMES +
                    "values(" + null + ","
                    +"'"+ ( firstName.replaceAll( "'", "&#39" ) ) + "'" + ","
                    +"'"+ ( lastName.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( addr1.replaceAll( "'", "&#39" ) )     + "'" + ","
                    +"'"+ ( addr2.replaceAll( "'", "&#39" ) )     + "'" + ","
                    +"'"+ ( email.replaceAll( "'", "&#39" ) )     + "'" + ","
                    +"'"+ ( birth.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( cell.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( fam_role.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( familypid.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( mem_role.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( middleName.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"'"+ ( phone.replaceAll( "'", "&#39" ) ) + "'" + ","
                    +"'"+ ( has_comp.replaceAll( "'", "&#39" ) ) + "'" + ")";
//                  System.out.println(sql);
                    stm.executeUpdate( sql );
                }
                else{
                    String sql = "update "+TABLE_NAME+" set " 
                    +"first_name  ='"+ ( firstName.replaceAll( "'", "&#39" ) ) + "'" + ","
                    +"last_name   ='"+ ( lastName.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"addr1       ='"+ ( addr1.replaceAll( "'", "&#39" ) )     + "'" + ","
                    +"addr2       ='"+ ( addr2.replaceAll( "'", "&#39" ) )     + "'" + ","
                    +"email       ='"+ ( email.replaceAll( "'", "&#39" ) )     + "'" + ","
                    +"birth       ='"+ ( birth.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"cell        ='"+ ( cell.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"fam_role    ='"+ ( fam_role.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"fam_pid     ='"+ ( familypid.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"mem_role    ='"+ ( mem_role.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"middle_name ='"+ ( middleName.replaceAll( "'", "&#39" ) )  + "'" + ","
                    +"phone       ='"+ ( phone.replaceAll( "'", "&#39" ) ) + "'" + ","
                    +"has_comp    ='"+ ( has_comp.replaceAll( "'", "&#39" ) ) + "'" + " " +
                    "where pid='"+pid+"'";
//                  System.out.println(sql);
                    stm.executeUpdate( sql );
                }
                conn.close();
                stm.close();

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private boolean hasPid() {
        boolean hasMemberPid = false;
        if(this.pid!=null&&this.pid.length()>0){
            hasMemberPid = true;
        }
        return hasMemberPid;
    }

    /*
     * fetch for a single pid
     */
    public void fetch(String pid) throws DNFException{
        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM "+TABLE_NAME+" where pid='" + pid + "'";// join user_roles on
            ResultSet rset = stm.executeQuery(sql);
            while (rset.next()) {
                this.pid        = rset.getString("pid"); 
                this.firstName  = rset.getString("first_name"); 
                this.lastName   = rset.getString("last_name"); 
                this.addr1      = rset.getString("addr1"); 
                this.addr2      = rset.getString("addr2"); 
                this.email      = rset.getString("email"); 
                this.birth      = rset.getString("birth"); 
                this.phone      = rset.getString("phone"); 
                this.cell       = rset.getString("cell"); 
                this.fam_role   = rset.getString("fam_role"); 
                this.familypid  = rset.getString("fam_pid"); 
                this.mem_role   = rset.getString("mem_role"); 
                this.middleName = rset.getString("middle_name"); 
                this.has_comp   = rset.getString("has_comp");
            }
            rset.close();
            stm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    private boolean assertStorable() {
        return isSet();
    }
    public ArrayList<Member> getFamAssoc(){
        ArrayList<Member> acco = new ArrayList<Member>();
        return acco;
    }

    /**
     * @return the has_comp
     */
    public String getHas_comp() {
        return has_comp;
    }

    /**
     * @param has_comp the has_comp to set
     */
    public void setHas_comp(String has_comp) {
        this.has_comp = has_comp;
    }

    public boolean isFather() {
        return this.fam_role.equals(FamilyDomain.FATHER);
    }

    public boolean isMother() {
        return this.fam_role.equals(FamilyDomain.MOTHER);
    }

    public String toString(){
        String str = "";
        str += getFirstName();
        str += getMiddleName().length()<1?"":" "+getMiddleName();
        str += getLastName()  .length()<1?"":" "+getLastName();
        str += getAddr1()     .length()<1?"":" "+getAddr1();
        str += getAddr2()     .length()<1?"":" "+getAddr2();
        str += getEmail()     .length()<1?"":" "+getEmail();
        str += getPhone()     .length()<1?"":" "+getPhone();
        str += getCell()      .length()<1?"":" "+getCell();
        str += getBirth()     .length()<1?"":" "+getBirth();
        str += getFamilypid() .length()<1?"":" "+getFamilypid();
        str += getFam_role()  .length()<1?"":" "+getFam_role();
        str += getMem_role()  .length()<1?"":" "+getMem_role();
        str += getHas_comp()  .length()<1?"":" "+getHas_comp();
        return str;
    }
    public int getAge() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        int age = -1;
        try {
            Date date = sdf.parse( getBirth());
            cal.setTime( date );
            age = age( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), cal.get( Calendar.DAY_OF_MONTH ));
        } catch ( ParseException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return age;
    }
    public void setAge( int age ) {
        this.age = age;
    }
    private static int age(int y, int m, int d) {
        Calendar cal = new GregorianCalendar(y, m, d);
        Calendar now = new GregorianCalendar();
        int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
                || (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)))
        {
            res--;
        }
        return res;
    }
}
