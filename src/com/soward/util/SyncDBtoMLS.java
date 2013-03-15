package com.soward.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

import com.soward.db.DB;
import com.soward.domain.FamilyDomain;
import com.soward.domain.MemberDomain;
import com.soward.object.Comps;
import com.soward.object.Member;

public class SyncDBtoMLS {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        SyncDBtoMLS.deleteTableData();
        List<Member> mList = SyncDBtoMLS.createMembers();
        List<Family> fList = SyncDBtoMLS.createFamilies(mList);
        SyncDBtoMLS.createHTComps();
    }
    private static List<Family> createFamilies( List<Member> list ) {
        //maps all members based on fampid
        Map<String,ArrayList<Member>> famMap = new HashMap<String, ArrayList<Member>>();
        for(Member mem: list){
            if(famMap.containsKey( mem.getFamilypid() )){
                famMap.get( mem.getFamilypid() ).add( mem );
            }else{
                ArrayList al = new ArrayList();
                al.add( mem );
                famMap.put( mem.getFamilypid(), al );
            }
        }
        Set<String> set = famMap.keySet();
        Iterator<String> iter = set.iterator();
        while(iter.hasNext()){
            Family fam = new Family();
            for(Member mem: famMap.get( iter.next() )){
                if(mem.getFam_role().equalsIgnoreCase( FamilyDomain.FATHER )){
                    fam.setFather( mem );
                }
                else if(mem.getFam_role().equalsIgnoreCase( FamilyDomain.MOTHER )){
                    fam.setMother( mem );
                }
                else if(mem.getFam_role().equalsIgnoreCase( FamilyDomain.SON )||
                        mem.getFam_role().equalsIgnoreCase( FamilyDomain.DAUGHTER )){
                    fam.getSiblings().add( mem );
                }else{
                    System.out.println(mem.getFam_role());
                }
            }
            fam.store();
        }
        return null;

    }
    public static Properties genPropFieldsList(String path) throws IOException {
        Properties props = new Properties();
        String[] sList = null;
        props.load(new FileInputStream(path));
        return props;
    }
    public static List<Member> createMembers(){
        List<Member> mList = new ArrayList<Member>();
        try {
            System.out.println("Start: transformCSVToMap");
            //            Properties properties = AppUtil.genPropFieldsList("src/main/resources/wex.fields.properties");
            //            String test001 = "./src/test/resources/csvmock/TransactionswithHotelData.csv";
            //            String test002 = "./src/test/resources/csvmock/TransactionswithPLOGData.csv";

            Properties properties = genPropFieldsList("uploadData/mem.fields.properties");
            File test001 = new File("./uploadData/");
            List<InputStream> iList = new ArrayList<InputStream>();
            List<String> fn = new ArrayList<String>();
            String[] fs = test001.list();
            for (String file: fs){
                if(file.contains( "Membership" )){
                    fn.add( file );
                    FileInputStream inp = new FileInputStream("./uploadData/"+file);
                    iList.add( inp );
                }
            }
            int hh = 0;
            try {
                for(InputStream ii: iList ){
                    List<Map<String,String>> map001 = transformCSVToList(ii,properties);
                    int cc = 0;
                    for(Map mm: map001){
                        Member mem = convertListToOrigTrans( mm );
                        mem = fixMember(mem, mm);
                        mList.add( mem );
                        cc++;
                    }
                    hh++;
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            System.out.println("Done: transformCSVToMap");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return mList;
    }
    ///need to tweek this method and create comps and then assignments/
    public static List<Comps> createHTComps(){
        List<Comps> mList = new ArrayList<Comps>();
        List<Member> missingMem = new ArrayList<Member>();
        Map<String, ArrayList<String>> compsFams = new HashMap<String, ArrayList<String>>();
        Comps cu = new Comps();
        MemberUtil mu = new MemberUtil();

        try {
            System.out.println("Start: transformCSVToMap");
            //            Properties properties = AppUtil.genPropFieldsList("src/main/resources/wex.fields.properties");
            //            String test001 = "./src/test/resources/csvmock/TransactionswithHotelData.csv";
            //            String test002 = "./src/test/resources/csvmock/TransactionswithPLOGData.csv";

            Properties properties = genPropFieldsList("uploadData/mem.fields.properties");
            File test001 = new File("./uploadData/");
            List<InputStream> iList = new ArrayList<InputStream>();
            List<String> fn = new ArrayList<String>();
            String[] fs = test001.list();
            for (String file: fs){
                if(file.contains( "HomeTeaching" )){
                    fn.add( file );
                    FileInputStream inp = new FileInputStream("./uploadData/"+file);
                    iList.add( inp );
                }
            }
            int hh = 0;
            try {

                Map<String, Member> eldersHash = getMemHash();
                Map<String, Member> eldersLPhone = getMemHashLastPhone();
                Map<String, Member> eldersFPhone = getMemHashFirstPhone();
                for(InputStream ii: iList ){
                    List<Map<String,String>> map001 = transformCSVToList(ii,properties);
                    int cc = 0;
                    for(Map<String, String> mm: map001){
                        String qu = mm.get( "Quorum" );
                        if(qu.length()>1&&qu.contains( "Elders" )){
                            String[] fullName1 = null;
                            String[] fullName2 = null;
                            String[] fullName3 = null;
                            Member mem1 = new Member();
                            Member mem2 = new Member();
                            Member mem3 = new Member();

                            // 1==========
                            if(mm.containsKey( "Home_Teacher_1" )){
                                fullName1 = mm.get( "Home_Teacher_1" ).split( "," );
                                String temp = fullName1[1].trim();
                                if(temp.contains( " " )){
                                    temp = temp.substring( 0, temp.indexOf( " " ) );
                                }
                                mem1.setFirstName( temp );
                                mem1.setLastName( fullName1[0].trim() );
                                mem1.setPhone( mm.get( "HT_1_Phone" ).trim() );
                            }
                            // 2==========
                            if(mm.containsKey( "Home_Teacher_2" )){
                                fullName2 = mm.get( "Home_Teacher_2" ).split( "," );
                                String temp = fullName2[1].trim();
                                if(temp.contains( " " )){
                                    temp = temp.substring( 0, temp.indexOf( " " ) );
                                }
                                mem2.setFirstName( temp );
                                mem2.setLastName( fullName2[0].trim() );
                                mem2.setPhone( mm.get( "HT_2_Phone" ).trim() );
                            }
                            if(mm.containsKey( "Home_Teacher_3" )){
                                fullName3 = mm.get( "Home_Teacher_3" ).split( "," );
                                String temp = fullName3[1].trim();
                                if(temp.contains( " " )){
                                    temp = temp.substring( 0, temp.indexOf( " " ) );
                                }
                                mem3.setFirstName( temp );
                                mem3.setLastName( fullName3[0].trim() );
                                mem3.setPhone( mm.get( "HT_3_Phone" ).trim() );
                            }
                            Member memFound1 = eldersHash.get( mem1.getFirstName()+mem1.getLastName());
                            Member memFound2 = eldersHash.get( mem2.getFirstName()+mem2.getLastName());
                            if(memFound1==null){
                                memFound1 = eldersLPhone.get( mem1.getLastName()+mem1.getPhone() );
                            }
                            if(memFound1==null){
                                memFound1 = eldersFPhone.get( mem1.getFirstName()+mem1.getPhone() );
                            }
                            if(memFound1==null){
                                missingMem.add( mem1 );
                            }
                            //                            for(Member miss: missingMem){
                            //                                System.out.println(miss.getFirstName());
                            //                            }
                            //second comp
                            if(memFound2==null){
                                memFound2 = eldersLPhone.get( mem2.getLastName()+mem2.getPhone() );
                            }
                            if(memFound2==null){
                                memFound2 = eldersFPhone.get( mem2.getFirstName()+mem2.getPhone() );
                            }
                            if(memFound2==null){
                                missingMem.add( mem2 );
                                //System.out.println(mem2.getFirstName()+" "+mem2.getLastName());
                            }
                            ArrayList<String> cL = new ArrayList<String>();
                            cL.add( memFound1.getPid() );
                            cL.add( memFound2.getPid() );
                            //save comps
                            String compPid = mu.updateComps(cL);
                            Comps cco = new Comps();

                            Map<String, Family> famHash = getFamHash();
                            String[] hhKey = mm.get( "Household" ).split( "," );
                            String lk = hhKey[0].trim();
                            String fk = hhKey[1].trim();
                            if(fk.contains( " " )){
                                fk = fk.substring( 0,fk.indexOf( " " ) );
                            }
                            if(famHash.containsKey( lk+fk )){
                                Family famFound = famHash.get( lk+fk );
                                if(compsFams.containsKey( compPid )){
                                    compsFams.get( compPid ).add( famFound.getPid());
                                }else{
                                    ArrayList<String> sdf = new ArrayList<String>();
                                    sdf.add( famFound.getPid() );
                                    compsFams.put( compPid, sdf);
                                }
                                //cco.createAssignment( sdf, compPid); 
                                //System.out.println(memFound1.getFirstName()+ " "+memFound2.getFirstName());
                            }else{
                                //System.out.println(lk+fk);
                            }



                            //                            System.out.print(mem1.getFirstName()+" "+mem1.getLastName()+" "+mem1.getPhone());
                            //                            System.out.print(" ---- ");
                            //                            System.out.print(mem2.getFirstName()+" "+mem2.getLastName()+" "+mem2.getPhone());
                            //                            System.out.print(" ---- ");
                            //                            System.out.println(mem3.getFirstName()+" "+mem3.getLastName()+" "+mem3.getPhone());
                            cc++;
                            //System.out.println(mm);
                        }
                    }
                    hh++;
                }
                //commit all assignments
                Set set = compsFams.keySet();
                Iterator<String> iter = set.iterator();
                while(iter.hasNext()){
                    String cPid = iter.next();
                    //System.out.println(cPid+" === "+compsFams.get( cPid ));
                    cu.createAssignment( compsFams.get( cPid ), cPid); 
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            System.out.println("Done: transformCSVToMap");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return mList;
    }
    //    {2-Street_1=, Priesthood=Elder, Baptized=09 Jan 1983, 2-State/Prov=, Phone_1=801-766-4206, Postal=84045, D/P=, City=Saratoga Springs, Mission=Yes, 2-Country=, Married=Married, Street_1=169 Apple Ave, State/Prov=Utah, 2-City=, Street_2=, Sealed_to_Prior=No, HH_Order=1, HH_Position=Head of Household, E-mail_Address=, Phone_2=, Confirmed=09 Jan 1983, Sex=Male, Indiv_ID=356, 2-Zip=, MRN=000-2372-2525, 2-Street_2=, Rec_Exp=Apr 2010, Ward_Geo_Code=HH9D14, HofH_ID=111, Full_Name=Soward, Scott Louis, Sealed_to_Spous=Yes, 2-D/P=, Spouse_Member=Yes, Preferred_Name=Soward, Scott, Stake_Geo_Code=HH9D14, Country=United States, Endowed=25 Oct 1994, Birth=30 Apr 1974}
    //    {2-Street_1=, Priesthood=, Baptized=01 Aug 1987, 2-State/Prov=, Phone_1=801-766-4206, Postal=84045, D/P=, City=Saratoga Springs, Mission=No, 2-Country=, Married=Married, Street_1=169 Apple Ave, State/Prov=Utah, 2-City=, Street_2=, Sealed_to_Prior=No, HH_Order=2, HH_Position=Spouse, E-mail_Address=, Phone_2=, Confirmed=01 Aug 1987, Sex=Female, Indiv_ID=357, 2-Zip=, MRN=000-0406-2698, 2-Street_2=, Rec_Exp=Aug 2008, Ward_Geo_Code=HH9D14, HofH_ID=111, Full_Name=Soward, Margaret Ann, Sealed_to_Spous=Yes, 2-D/P=, Spouse_Member=Yes, Preferred_Name=Soward, Maggie, Stake_Geo_Code=HH9D14, Country=United States, Endowed=03 Sep 2004, Birth=29 Jun 1979}
    //    {2-Street_1=, Priesthood=, Baptized=, 2-State/Prov=, Phone_1=801-766-4206, Postal=84045, D/P=, City=Saratoga Springs, Mission=No, 2-Country=, Married=Single, Street_1=169 Apple Ave, State/Prov=Utah, 2-City=, Street_2=, Sealed_to_Prior=No, HH_Order=3, HH_Position=Other, E-mail_Address=, Phone_2=, Confirmed=, Sex=Female, Indiv_ID=358, 2-Zip=, MRN=001-2421-0516, 2-Street_2=, Rec_Exp=, Ward_Geo_Code=HH9D14, HofH_ID=111, Full_Name=Soward, Isabel Alexis, Sealed_to_Spous=N/A, 2-D/P=, Spouse_Member=N/A, Preferred_Name=Soward, Isabel Alexis, Stake_Geo_Code=HH9D14, Country=United States, Endowed=, Birth=27 Dec 2001}
    //    {2-Street_1=, Priesthood=Unordained, Baptized=, 2-State/Prov=, Phone_1=801-766-4206, Postal=84045, D/P=, City=Saratoga Springs, Mission=No, 2-Country=, Married=Single, Street_1=169 Apple Ave, State/Prov=Utah, 2-City=, Street_2=, Sealed_to_Prior=No, HH_Order=4, HH_Position=Other, E-mail_Address=, Phone_2=, Confirmed=, Sex=Male, Indiv_ID=359, 2-Zip=, MRN=001-2421-0532, 2-Street_2=, Rec_Exp=, Ward_Geo_Code=HH9D14, HofH_ID=111, Full_Name=Soward, Isaac Scott, Sealed_to_Spous=N/A, 2-D/P=, Spouse_Member=N/A, Preferred_Name=Soward, Isaac Scott, Stake_Geo_Code=HH9D14, Country=United States, Endowed=, Birth=07 Jul 2003}
    //    {2-Street_1=, Priesthood=Unordained, Baptized=, 2-State/Prov=, Phone_1=801-766-4206, Postal=84045, D/P=, City=Saratoga Springs, Mission=No, 2-Country=, Married=Single, Street_1=169 Apple Ave, State/Prov=Utah, 2-City=, Street_2=, Sealed_to_Prior=No, HH_Order=5, HH_Position=Other, E-mail_Address=, Phone_2=, Confirmed=, Sex=Male, Indiv_ID=360, 2-Zip=, MRN=001-3599-5787, 2-Street_2=, Rec_Exp=, Ward_Geo_Code=HH9D14, HofH_ID=111, Full_Name=Soward, Noah Wilford, Sealed_to_Spous=N/A, 2-D/P=, Spouse_Member=N/A, Preferred_Name=Soward, Noah Wilford, Stake_Geo_Code=HH9D14, Country=United States, Endowed=, Birth=09 Apr 2006}
    //    {2-Street_1=, Priesthood=, Baptized=, 2-State/Prov=, Phone_1=801-766-4206, Postal=84045, D/P=, City=Saratoga Springs, Mission=No, 2-Country=, Married=Single, Street_1=169 Apple Ave, State/Prov=Utah, 2-City=, Street_2=, Sealed_to_Prior=No, HH_Order=6, HH_Position=Other, E-mail_Address=, Phone_2=, Confirmed=, Sex=Female, Indiv_ID=361, 2-Zip=, MRN=001-4552-6874, 2-Street_2=, Rec_Exp=, Ward_Geo_Code=HH9D14, HofH_ID=111, Full_Name=Soward, Eliza Jane, Sealed_to_Spous=N/A, 2-D/P=, Spouse_Member=N/A, Preferred_Name=Soward, Eliza Jane, Stake_Geo_Code=HH9D14, Country=United States, Endowed=, Birth=31 Jul 2007}

    private static Map<String, Member> getMemHash() {
        MemberUtil mu = new MemberUtil();
        ArrayList<Member> memL = mu.getNonCompElders();
        Map<String, Member> mHash = new HashMap<String, Member>();
        for(Member mem: memL){
            String key = mem.getFirstName()+mem.getLastName();
            //System.out.println(key);
            mHash.put( key, mem );
        }
        return mHash;
    }

    private static Map<String, Family> getFamHash() {
        Family fu = new Family();

        ArrayList<Family> memL = fu.getAllFamWithOutHT();
        Map<String, Family> mHash = new HashMap<String, Family>();
        for(Family mem: memL){
            String key = mem.getName()+mem.getFamFirstName();
            //            System.out.println(key);
            mHash.put( key, mem );
        }
        return mHash;
    }
    private static Map<String, Member> getMemHashLastPhone() {
        MemberUtil mu = new MemberUtil();
        ArrayList<Member> memL = mu.getNonCompElders();
        Map<String, Member> mHash = new HashMap<String, Member>();
        for(Member mem: memL){
            String key = mem.getLastName()+mem.getPhone();
            //            System.out.println(key);
            mHash.put( key, mem );
        }
        return mHash;
    }
    private static Map<String, Member> getMemHashFirstPhone() {
        MemberUtil mu = new MemberUtil();
        ArrayList<Member> memL = mu.getNonCompElders();
        Map<String, Member> mHash = new HashMap<String, Member>();
        for(Member mem: memL){
            String key = mem.getFirstName()+mem.getPhone();
            //            System.out.println(key);
            mHash.put( key, mem );
        }
        return mHash;
    }
    private static Member fixMember( Member mem, Map<String, String> mm ) {
        String name = mm.get( "firstName" );
        //set first, last, and middle name
        if(name!=null&&name.length()>0){
            if(name.contains( "," )){
                int ll = name.indexOf( "," );
                String last = name.substring( 0, ll );
                mem.setLastName( last );
                name = name.substring( ll+1, name.length() );
                name = name.trim();
                if(name.contains( " " )){
                    int ff = name .indexOf( " " );
                    String first = name.substring( 0, ff );
                    mem.setFirstName( first );
                    name = name.substring( ff, name.length() );
                    name = name.trim();
                    if(name.length()>0){
                        mem.setMiddleName( name );
                    }
                }else{
                    mem.setFirstName( name );
                }
            }
        }
        //fix address
        String state = mm.get( "state");
        String zip = mm.get( "zip");
        mem.setAddr2( mem.getAddr2()+" UT, "+ zip );
        //fix family member roles
        //0003 -> son
        //0004 -> daughter
        //0002 -> wife
        //0001 -> HH
        String sex = mm.get( "sex" );
        String role = mem.getFam_role();
        if(sex.equalsIgnoreCase( "Female" )){
            if(role.equalsIgnoreCase( "Spouse" )){
                mem.setFam_role( FamilyDomain.MOTHER );
            }else if(role.equalsIgnoreCase( "Other" )){
                mem.setFam_role( FamilyDomain.DAUGHTER );
            }else if(role.equalsIgnoreCase( "Head of Household" )){
                mem.setFam_role( FamilyDomain.MOTHER );
            }
        }
        else if(sex.equalsIgnoreCase( "Male" )){
            if(role.equalsIgnoreCase( "Head of Household" )){
                mem.setFam_role( FamilyDomain.FATHER );
            }else if(role.equalsIgnoreCase( "Other" )){
                mem.setFam_role( FamilyDomain.SON );
            }
        }
        String ordin = mem.getMem_role();
        //fix ordination
        if(ordin.equalsIgnoreCase( "Priest" )){
            mem.setMem_role( MemberDomain.PRIEST );
        }
        else if(ordin.equalsIgnoreCase( "Elder" )){
            mem.setMem_role( MemberDomain.ELDER );
        }
        else if(ordin.equalsIgnoreCase( "Unordained" )){
            mem.setMem_role( MemberDomain.UNORDAINED );
        }
        else if(ordin.equalsIgnoreCase( "Deacon" )){
            mem.setMem_role( MemberDomain.DEACON );
        }
        else if(ordin.equalsIgnoreCase( "Deacon" )){
            mem.setMem_role( MemberDomain.DEACON );
        }
        else if(ordin.equalsIgnoreCase( "High Priest" )){
            mem.setMem_role( MemberDomain.HIGHPRIEST );
        }
        else if(ordin.equalsIgnoreCase( "Teacher" )){
            mem.setMem_role( MemberDomain.TEACHER );
        }

        return mem;
    }
    /**
     * convert the list of maps<string,string> to one transaction
     **/
    public static Member convertListToOrigTrans(Map<String,String> transMap) throws Exception{
        Member bean = new Member();
        populateObject( bean, transMap);
        return bean;
    }

    /**
     * convert the list of maps<string,string> to one object
     **/
    public  static <T> T populateObject(T bean, Map<String,String> transMap) throws Exception{
        org.apache.commons.beanutils.BeanUtils.populate(bean, transMap);
        return bean;
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
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteTableData(){
        
        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            Statement stm = conn.createStatement();
            stm.executeUpdate( "delete from assign_comps" );
            stm.executeUpdate( "delete from assignments" );
            stm.executeUpdate( "delete from comps" );
            stm.executeUpdate( "delete from district_comps" );
            stm.executeUpdate( "delete from family" );
            stm.executeUpdate( "delete from member" );
            conn.close();
            stm.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Reads input stream and a properties file and maps the prop list with 
     * the first line in the inputstream (the columns of the csv)
     * returns list of maps like <transKeys, transValues>
     * @param InputStream inputList, Properties props
     * @return Map<String, Map<String,String>>
     * @throws Exception
     */
    public static List<Map<String,String>> transformCSVToList(InputStream fis, Properties properties) throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(fis));
        String [] nextLine;
        boolean headers = true;
        HashMap<String, String> colNames = new HashMap<String,String>();
        List<Map<String,String>> transMap = new ArrayList<Map<String,String>>();
        while ((nextLine = reader.readNext()) != null) {
            int cc = 0;
            if(headers){
                for(String ss: nextLine){
                    colNames.put(cc+"", ss);
                    cc++;
                }
                headers = false;
            }else{
                HashMap<String, String> transHash = new HashMap<String,String>();
                //begin a new transaction
                for(String ss: nextLine){
                    String col = colNames.get(cc+"");
                    cc++;
                    //remove spaces in csv columns for comparison to prop file
                    col = col.replaceAll(" ", "");
                    //skip columns that are empty
                    if(col.length()<1){
                        continue;
                    }
                    //csv is inconsistant with properties
                    if(col==null||!properties.containsKey(col)){
                        return null;
                    }
                    String prop = properties.getProperty(col);
                    String[] values = ss.split("\\|");
                    try {
                        String[] props = prop.split("\\|");
                        int bb = 0;
                        for(String pro: props){
                            if(pro.length()>1){
                                //csv is inconsistant with properties
                                if(values.length<=bb){
                                    System.out.println("8878888");
                                    return null;
                                }
                                transHash.put(pro, values[bb]);
                                bb++;
                            }
                        }
                    } catch ( Exception e ) {
                        //System.out.println("prop: "+prop+" col: "+col);
                        throw e;//.printStackTrace();
                    }
                }
                transMap.add(transHash);
            }
        }
        return transMap;
    }

}
