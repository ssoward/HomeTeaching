/**
 * Title: ParseWardList.java
 * Description: HomeTeaching
 * Date: Jun 27, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.soward.domain.FamilyDomain;
import com.soward.domain.MemberDomain;
import com.soward.object.Member;

public class ParseWardList {
    private boolean test = true;

    public ArrayList<Family> famList = new ArrayList<Family>();

    public ArrayList<Member> kids = new ArrayList<Member>();

    public ParseWardList() {
    }

    public void parseFile() {
        try {
            File filename = new File("c:/EldersList.txt");
            FileReader inputFileReader = new FileReader(filename);
            BufferedReader inputStream = new BufferedReader(inputFileReader);
            // (6,'Scott','Soward','169 Apple ave.','Saratoga Springs, UT.
            // 84045','scott.soward@gmail.com','33','801 766-4206','801 828
            // 0383');
            String inLine = null;
            // List<String> al = new ArrayList<String>();

            String temp = "";
            while ((inLine = inputStream.readLine()) != null) {

                StringTokenizer tok = new StringTokenizer(inLine);
                while (tok.hasMoreTokens()) {
                    String temp1 = tok.nextToken();
                    if (temp1.equals("ccc")) {
                        // System.out.println(temp + " ");
                        StringTokenizer toc = new StringTokenizer(temp);
                        // get each line/family
                        List<String> al = new ArrayList<String>();
                        while (toc.hasMoreTokens()) {
                            al.add(toc.nextToken());
                        }
                        getFamilyData(al);
                        temp = "";
                        al.add(temp);
                    } else {
                        temp += temp1 + " ";
                    }
                }
            }
            inputStream.close();
        } catch (Exception e) {

            System.out.println("IOException:");
            e.printStackTrace();
        }

    }

    private void getFamilyData(List<String> al) {
        kids = new ArrayList<Member>();
        Family fam = new Family();
        String fatherFirst = "";
        String fatherLast = "";
        String fatherSecond = "";
        // get father
        if (!al.isEmpty() && al.size() > 2) {
            int gender = 0;
            fatherLast = al.get(0);
            Member father = new Member();
            Member mother = new Member();
            boolean fath = false;
            if (al.get(2).equalsIgnoreCase("M")) {
                fath = true;
                gender = 2;
                fatherFirst += "" + al.get(1);
                father.setFirstName(fatherFirst);
                father.setLastName(fatherLast);
            } else if (al.get(3).equalsIgnoreCase("M")) {
                fath = true;
                gender = 3;
                fatherFirst += "" + al.get(1);
                fatherSecond += " " + al.get(2);
                father.setFirstName(fatherFirst);
                father.setMiddleName(fatherSecond);
                father.setLastName(fatherLast);
            }
            if (al.get(2).equalsIgnoreCase("F")) {
                gender = 2;
                fatherFirst += "" + al.get(1);
                mother.setFirstName(fatherFirst);
                mother.setLastName(fatherLast);
            } else if (al.get(3).equalsIgnoreCase("F")) {
                gender = 3;
                fatherFirst += "" + al.get(1);
                fatherSecond += " " + al.get(2);
                mother.setFirstName(fatherFirst);
                mother.setMiddleName(fatherSecond);
                mother.setLastName(fatherLast);
            }// end of get father (some cases single sister)
            // set role and gender --do both but based on the fath/moth will
            // only commit on to ArrayList fam.
            father.setMem_role(MemberDomain.ELDER);
            father.setFam_role(FamilyDomain.FATHER);
            mother.setFam_role(FamilyDomain.MOTHER);
            // System.out.println(al.get(gender+1)+" "+al.get(gender+2));
            // get birthday
            father.setBirth(al.get(gender + 1) + " " + al.get(gender + 2));
            mother.setBirth(al.get(gender + 1) + " " + al.get(gender + 2));

            // get street addr1
            String addr1 = "";
            for (int r = gender + 3; r < 10; r++) {
                // skip mothers name
                if (al.get(r).equals("Place") || al.get(r).equals("Avenue") || al.get(r).equals("Court")
                        || al.get(r).equals("Way")) {
                    addr1 += al.get(r) + " ";
                    gender = r + 1;
                    r = 16;
                    break;
                } else {
                    addr1 += al.get(r) + " ";
                }
            }
            mother.setAddr1(addr1);
            father.setAddr1(addr1);
            boolean single = false;
            boolean contin = true;
            String wifeMidd = "";
            String wifeFirt = "";
            int place = gender;
            // get spouse
            for (int r = gender; r < gender + 3; r++) {
                if (al.get(r) != null && contin) {
                    if (al.get(r).equalsIgnoreCase("Saratoga")) {
                        // single individuals.
                        single = true;
                        father.setPhone(al.get(al.size() - 1));
                        father.setAddr2("Saratoga Springs, UT 84045");
                        mother.setPhone(al.get(al.size() - 1));
                        mother.setAddr2("Saratoga Springs, UT 84045");
                        contin = false;
                        // /father.store();
                        fam.setFather(father);
                        fam.store();
                        fam.setMother(mother);
                        fam.store();
                        return;
                        // /mother.store();
                        // System.out.println(al.get(al.size()-1));
                    }
                    // if F then assume its a wife.
                    else if (al.get(r + 1).equals("F") && !single) {
                        // if the head of house hold is not a male
                        // then this is not a wife, but a daughter.
                        if (!fath) {
                            Member son = new Member();
                            son.setFirstName(al.get(r));
                            son.setBirth(al.get(r + 2) + " " + al.get(r + 3));
                            kids.add(son);
                            contin = false;
                            place = r + 4;
                        } else {
                            mother.setFirstName(al.get(r));
                            mother.setBirth(al.get(r + 2) + " " + al.get(r + 3));
                            contin = false;
                            place = r + 4;
                            fam.setMother(mother);
                        }
                    }
                    // could be either a son (or a daughter above line 166)
                    else if (al.get(r + 1).equals("M") && !single) {
                        Member son = new Member();
                        son.setFirstName(al.get(r));
                        son.setBirth(al.get(r + 2) + " " + al.get(r + 3));
                        kids.add(son);
                        contin = false;
                        place = r + 4;
                    } else if (!single) {
                        wifeMidd = al.get(r);
                    }
                }
            }
            // now get remaining data from non-single families
            if (!single) {
                if (!al.get(place).equals("Saratoga") && !kids.isEmpty()) {
                    String bd = kids.get(kids.size() - 1).getBirth();
                    for (int r = place; !al.get(r).equals("Saratoga"); r++) {
                        bd += " " + al.get(r);
                        place = r;
                    }
                    // System.out.println(kids.get(kids.size()-1).getFirstName()+"
                    // "+bd);
                    kids.get(kids.size() - 1).setBirth(bd);
                    father.setAddr2("Saratoga Springs, UT 84045");
                    mother.setAddr2("Saratoga Springs, UT 84045");
                    // //append year onto kids bd.
                    // kids.get(currentKidCount-1).setBirth(kids.get(currentKidCount-1).getBirth()+"
                    // "+al.get(place));
                    // System.out.print(kids.get(currentKidCount-1).getFirstName()+"
                    // - ");
                    // System.out.println(kids.get(currentKidCount-1).getBirth());
                } else {
                    father.setAddr2("Saratoga Springs, UT 84045");
                    mother.setAddr2("Saratoga Springs, UT 84045");
                    place += 3;
                }

                if (fath) {
                    Member ff = fam.getFather();
                    Member mm = fam.getMother();
                    // System.out.println(ff.getFirstName()+"
                    // "+mm.getFirstName()+" *** "+ff.getLastName()+"
                    // "+ff.getBirth()+" "+ff.getAddr1()+" ");
                    // get remaining info
                    // check for a phone number
                    String phn = al.get(place + 1).substring(0, 1);
                    try {
                        Integer.parseInt(phn);
                        father.setPhone(al.get(place + 1));
                        /*******************************************************
                         * done with fam !!!!!!!!!!!!! ***************
                         */
                        fam.setSiblings(kids);
                        fam.setFather(father);
                        fam.setMother(mother);
                        fam.store();
                        return;
                    } catch (Exception e) {
                        // not a number
                        String kName = "";
                        Member kn = new Member();
                        for (int i = place + 1; i < al.size(); i++) {
                            // get son
                            if (al.get(i + 1).equals("F")) {
                                kName += al.get(i);
                                kn.setFam_role(FamilyDomain.DAUGHTER);
                                kn.setFirstName(kName);
                                place = i;
                                i = 100;
                            } else if (al.get(i + 1).equals("M")) {
                                kName += al.get(i);
                                kn.setFam_role(FamilyDomain.SON);
                                kn.setFirstName(kName);
                                place = i;
                                i = 100;
                            } else {
                                kName += al.get(i) + " ";
                            }
                        }
                        // get birthday
                        kn.setBirth(al.get(place + 2) + " " + al.get(place + 3) + " " + al.get(place + 4) + " "
                                + al.get(place + 5));
                        kids.add(kn);
                        // get phone of all families with no more kids or data
                        father.setPhone(al.get(place + 6));
                        place += 7;
                        try {
                            // see if there is #place in the al if not, commit
                            al.get(place);
                            // try{
                            boolean hasPhone = false;
                            try {
                                int cell = Integer.parseInt(al.get(place).substring(0, 2));
                                hasPhone = true;
                            } catch (Exception asdf) {
                            }
                            if (hasPhone) {
                                try{

                                    System.out.println(al.get(place+1));
                                }catch(Exception nnn){
                                    //nothing else!
                                    father.setPhone(al.get(place));
                                    fam.setSiblings(kids);
                                    fam.setFather(father);
                                    fam.setMother(mother);
                                    fam.store();
                                    return;
                                }
                            }
                            // check for email
                            else if (al.get(place).contains("@")) {
                                father.setEmail(al.get(place));
                                mother.setEmail(al.get(place));
                                //								 and another kid...
                                boolean ddd = true;
                                Member kid4 = new Member();
                                place += 1;
                                kid4.setFirstName(al.get(place));
                                String midd3 = "";
                                for (int o = place + 1; o < place + 5; o++) {
                                    if (ddd) {
                                        // son
                                        if (al.get(o).equals("M")) {
                                            kid4.setFam_role(FamilyDomain.SON);
                                            kid4.setMiddleName(midd3);
                                            place = o;
                                            ddd = false;
                                        } else if (al.get(o).equals("F")) {
                                            place = o;
                                            kid4.setMiddleName(midd3);
                                            kid4.setFam_role(FamilyDomain.DAUGHTER);
                                            ddd = false;
                                        } else {
                                            midd3 += al.get(o) + " ";
                                        }
                                    }
                                }
                                place++;
                                kid4.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2)
                                        + " " + al.get(place + 3));
                                kids.add(kid4);
                                try{
                                    al.get(place+4);
                                }catch(Exception edx){
                                    fam.setSiblings(kids);
                                    fam.setFather(father);
                                    fam.setMother(mother);
                                    fam.store();
                                    return;
                                }
                                //								System.out.println(al.get(place+4));
                                boolean hasPh = false;
                                try {
                                    int cell = Integer.parseInt(al.get(place + 4).substring(0, 2));
                                    hasPh = true;
                                } catch (Exception asdf) {
                                    place+=4;
                                    boolean done = true;
                                    Member kid2 = new Member();
                                    kid2.setFirstName(al.get(place));
                                    String midd = "";
                                    for (int o = place + 1; o < place + 5; o++) {
                                        if (done) {
                                            // son
                                            if (al.get(o).equals("M")) {
                                                kid2.setFam_role(FamilyDomain.SON);
                                                kid2.setMiddleName(midd);
                                                place = o;
                                                done = false;
                                            } else if (al.get(o).equals("F")) {
                                                place = o;
                                                kid2.setMiddleName(midd);
                                                kid2.setFam_role(FamilyDomain.DAUGHTER);
                                                done = false;
                                            } else {
                                                midd += al.get(o) + " ";
                                            }
                                        }
                                    }
                                    place++;
                                    kid2.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2) + " "
                                            + al.get(place + 3));
                                    // System.out.println(kid2.getBirth());
                                    kids.add(kid2);
                                    //the following is for only zachary tyler
                                    Member fin = new Member();
                                    fin.setFirstName(al.get(place+4));
                                    fin.setMiddleName(al.get(place+5));
                                    fin.setBirth(al.get(place+6)+" "+al.get(place+7)+" "+al.get(place+8)+" "+al.get(place+9));
                                    kids.add(fin);
                                    //System.out.print(fin.getFirstName()+" "+fin.getMiddleName()+" --"+fin.getBirth());
                                    fam.setSiblings(kids);
                                    fam.setFather(father);
                                    fam.setMother(mother);
                                    fam.store();
                                    return;
                                }
                                if(hasPh){
                                    father.setCell(al.get(place+4));
                                    mother.setCell(al.get(place+4));
                                    //this is the end of data for these so commit
                                    fam.setSiblings(kids);
                                    fam.setFather(father);
                                    fam.setMother(mother);
                                    fam.store();
                                    return;
                                }

                            }//end of email families
                            // else more kids
                            else {
                                boolean done = true;
                                Member kid2 = new Member();
                                kid2.setFirstName(al.get(place));
                                String midd = "";
                                for (int o = place + 1; o < place + 5; o++) {
                                    if (done) {
                                        // son
                                        if (al.get(o).equals("M")) {
                                            kid2.setFam_role(FamilyDomain.SON);
                                            kid2.setMiddleName(midd);
                                            place = o;
                                            done = false;
                                        } else if (al.get(o).equals("F")) {
                                            place = o;
                                            kid2.setMiddleName(midd);
                                            kid2.setFam_role(FamilyDomain.DAUGHTER);
                                            done = false;
                                        } else {
                                            midd += al.get(o) + " ";
                                        }
                                    }
                                }
                                place++;
                                // System.out.print(kid2.getFirstName()+"
                                // "+kid2.getMiddleName()+" --");
                                kid2.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2) + " "
                                        + al.get(place + 3));
                                // System.out.println(kid2.getBirth());
                                kids.add(kid2);
                                // start with next list

                                try {
                                    al.get(place + 4);
                                } catch (Exception ex) {
                                    // commit these, no more data after last
                                    // kid.
                                    fam.setSiblings(kids);
                                    fam.setFather(father);
                                    fam.setMother(mother);
                                    fam.store();
                                    return;
                                    // ex.printStackTrace();
                                }
                                // System.out.println(father.getFirstName()+"
                                // "+al.get(place+4));
                                boolean hasPh = false;
                                try {
                                    int cell = Integer.parseInt(al.get(place + 4).substring(0, 2));
                                    hasPh = true;
                                } catch (Exception asdf) {
                                }
                                if (hasPh) {

                                    // System.out.println(father.getPhone()+"
                                    // ..");
                                    father.setCell(al.get(place + 4));
                                    place += 5;
                                    try {
                                        al.get(place);
                                    } catch (Exception jj) {
                                        // jj.printStackTrace();
                                        fam.setSiblings(kids);
                                        fam.setFather(father);
                                        fam.setMother(mother);
                                        fam.store();
                                        return;
                                    }
                                    // System.out.println(al.get(place));
                                    boolean dn = true;
                                    Member kid3 = new Member();
                                    kid3.setFirstName(al.get(place));
                                    String midd2 = "";
                                    for (int o = place + 1; o < place + 5; o++) {
                                        if (dn) {
                                            // son
                                            if (al.get(o).equals("M")) {
                                                kid3.setFam_role(FamilyDomain.SON);
                                                kid3.setMiddleName(midd2);
                                                place = o;
                                                dn = false;
                                            } else if (al.get(o).equals("F")) {
                                                place = o;
                                                kid3.setMiddleName(midd2);
                                                kid3.setFam_role(FamilyDomain.DAUGHTER);
                                                dn = false;
                                            } else {
                                                midd2 += al.get(o) + " ";
                                            }
                                        }
                                    }
                                    place++;
                                    kid3.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2)
                                            + " " + al.get(place + 3));
                                    kids.add(kid3);
                                    // System.out.println(kid3.getFirstName()+"
                                    // "+kid3.getMiddleName()+"
                                    // "+kid3.getBirth());
                                    place += 4;
                                    try {
                                        System.out.println(al.get(place));
                                    } catch (Exception jj) {
                                        // jj.printStackTrace();
                                        fam.setSiblings(kids);
                                        fam.setFather(father);
                                        fam.setMother(mother);
                                        fam.store();
                                        return;
                                    }

                                }
                                // another kid
                                else {

                                    place += 4;
                                    boolean dn = true;
                                    Member kid3 = new Member();
                                    kid3.setFirstName(al.get(place));
                                    String midd2 = "";
                                    for (int o = place + 1; o < place + 5; o++) {
                                        if (dn) {
                                            // son
                                            if (al.get(o).equals("M")) {
                                                kid3.setFam_role(FamilyDomain.SON);
                                                kid3.setMiddleName(midd2);
                                                place = o;
                                                dn = false;
                                            } else if (al.get(o).equals("F")) {
                                                place = o;
                                                kid3.setMiddleName(midd2);
                                                kid3.setFam_role(FamilyDomain.DAUGHTER);
                                                dn = false;
                                            } else {
                                                midd2 += al.get(o) + " ";
                                            }
                                        }
                                    }
                                    place++;
                                    kid3.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2)
                                            + " " + al.get(place + 3));
                                    kids.add(kid3);
                                    // System.out.println(kid3.getFirstName()+"
                                    // "+kid3.getMiddleName()+"
                                    // "+kid3.getBirth());
                                    try {
                                        al.get(place + 4);
                                    } catch (Exception pp) {
                                        // commit!
                                        fam.setSiblings(kids);
                                        fam.setFather(father);
                                        fam.setMother(mother);
                                        fam.store();
                                        return;
                                    }
                                    // and another kid...
                                    boolean ddd = true;
                                    Member kid4 = new Member();
                                    place += 4;
                                    kid4.setFirstName(al.get(place));
                                    String midd3 = "";
                                    for (int o = place + 1; o < place + 5; o++) {
                                        if (ddd) {
                                            // son
                                            if (al.get(o).equals("M")) {
                                                kid4.setFam_role(FamilyDomain.SON);
                                                kid4.setMiddleName(midd3);
                                                place = o;
                                                ddd = false;
                                            } else if (al.get(o).equals("F")) {
                                                place = o;
                                                kid4.setMiddleName(midd3);
                                                kid4.setFam_role(FamilyDomain.DAUGHTER);
                                                ddd = false;
                                            } else {
                                                midd3 += al.get(o) + " ";
                                            }
                                        }
                                    }
                                    place++;
                                    kid4.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2)
                                            + " " + al.get(place + 3));
                                    kids.add(kid4);
                                    // see if anything else is there!
                                    try {
                                        al.get(place + 4);

                                    } catch (Exception mmm) {
                                        // commit!
                                        fam.setSiblings(kids);
                                        fam.setFather(father);
                                        fam.setMother(mother);
                                        fam.store();
                                        return;
                                    }
                                    // and another kid...
                                    boolean dddd = true;
                                    Member kid5 = new Member();
                                    place += 4;
                                    kid5.setFirstName(al.get(place));
                                    String midd4 = "";
                                    for (int o = place + 1; o < place + 5; o++) {
                                        if (dddd) {
                                            // son
                                            if (al.get(o).equals("M")) {
                                                kid5.setFam_role(FamilyDomain.SON);
                                                kid5.setMiddleName(midd4);
                                                place = o;
                                                dddd = false;
                                            } else if (al.get(o).equals("F")) {
                                                place = o;
                                                kid5.setMiddleName(midd4);
                                                kid5.setFam_role(FamilyDomain.DAUGHTER);
                                                dddd = false;
                                            } else {
                                                midd4 += al.get(o) + " ";
                                            }
                                        }
                                    }
                                    place++;
                                    kid5.setBirth(al.get(place) + " " + al.get(place + 1) + " " + al.get(place + 2)
                                            + " " + al.get(place + 3));
                                    kids.add(kid5);
                                    // System.out.println(kid5.getFirstName()+"
                                    // "+father.getLastName());
                                    try {
                                        al.get(place+4);

                                    } catch (Exception mmm) {
                                        // commit!
                                        fam.setSiblings(kids);
                                        fam.setFather(father);
                                        fam.setMother(mother);
                                        fam.store();
                                        return;
                                    }
                                }

                            }// end of more kids
                        } catch (Exception ex) {
                            // commit these, no data after phone
                            fam.setFather(father);
                            fam.setMother(mother);
                            fam.setSiblings(kids);
                            fam.store();
                            return;
                            // System.out.println(father.getFirstName());
                        }
                    }
                } else {
                    fam.setMother(mother);
                    fam.setSiblings(kids);
                    fam.store();
                    return;
                    // Member ff = fam.getMother();
                    // System.out.println(ff.getFirstName()+" ***
                    // "+ff.getLastName()+" "+ff.getBirth()+" "+ff.getAddr1());
                    // System.out.println(fam.getMother().getBirth());
                }
            }
        }
    }

    public static void main(String[] args) {
        //		ParseWardList pwl = new ParseWardList();
        //		pwl.parseFile();
        Map<String, String> emailMap = new HashMap<String,String>();
        try{
            File filename = new File("/" +
            "Users/scottsoward/deleteMe.txt");
            FileReader inputFileReader = new FileReader(filename);
            BufferedReader inputStream = new BufferedReader(inputFileReader);
            // (6,'Scott','Soward','169 Apple ave.','Saratoga Springs, UT.
            // 84045','scott.soward@gmail.com','33','801 766-4206','801 828
            // 0383');
            String inLine = null;
            // List<String> al = new ArrayList<String>();

            String temp = "";
            while ((inLine = inputStream.readLine()) != null) {

                StringTokenizer tok = new StringTokenizer(inLine);
                while (tok.hasMoreTokens()) {
                    String temp1 = tok.nextToken();
                    System.out.println(temp1);
                    if(temp1!=null&&temp1.trim().length()>0)
                        emailMap.put( temp1.trim(), "" );
                }
            }
            Set<String> set = emailMap.keySet();
            Iterator<String> iter = set.iterator();
            System.out.println("");
            int cc = 0;
            while(iter.hasNext()){
                cc++;
                System.out.print(iter.next()+",");
            }
            System.out.println("");
            System.out.println(cc);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
