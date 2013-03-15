/**
 * Title: NewFamily.java
 * Description: HomeTeaching
 * Date: Jul 4, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.db.DB;
import com.soward.domain.FamilyDomain;
import com.soward.domain.MemberDomain;
import com.soward.object.Member;

public class NewFamily extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			String Uid = (String) session.getAttribute( "Uid" );
			String username = (String) session.getAttribute( "username" );
			// System.out.println(Uid);
			if ( Uid == null ) {
				request.getSession().invalidate();
				if ( session != null ) {
					session = null;
				}
				response.sendRedirect( "home.jsp?message=Please Login" );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException {

		HttpSession session = request.getSession();
		try {
			String username = (String) session.getAttribute( "username" );
			String Uid = (String) session.getAttribute( "Uid" );
			if ( Uid == null ) {
				request.getSession().invalidate();
				if ( session != null ) {
					session = null;
				}
				response.sendRedirect( "home.jsp?mustLogin=true" );
			}
			Family fam = new Family();
			String message = "";
			String fathersFirstName  = request.getParameter( "fathersFirstName" );
			String fatherMemRole     = request.getParameter( "fatherMemRole" );
			String fathersMiddleName = request.getParameter( "fathersMiddleName" );
			String fathersDOB         = request.getParameter( "fathersDOB" );
			String mothersDOB         = request.getParameter( "mothersDOB" );
			String mothersFirstName  = request.getParameter( "mothersFirstName" );
			String mothersMiddleName = request.getParameter( "mothersMiddleName" );
			String lastName          = request.getParameter( "lastName" );
			String addr1             = request.getParameter( "addr1" );
			String addr2             = request.getParameter( "addr2" );
			String email             = request.getParameter( "email" );
			String phone             = request.getParameter( "phone" );
			String cell              = request.getParameter( "cell" );
			String childList         = request.getParameter( "childList" );

			if(fathersFirstName  ==null){ fathersFirstName  = "";}
			if(fatherMemRole     ==null){ fatherMemRole     = "";}
			if(fathersMiddleName ==null){ fathersMiddleName = "";}
			if(mothersFirstName  ==null){ mothersFirstName  = "";}
			if(mothersMiddleName ==null){ mothersMiddleName = "";}
			if(lastName          ==null){ lastName          = "";}
			if(addr1             ==null){ addr1             = "";}
			if(addr2             ==null){ addr2             = "";}
			if(email             ==null){ email             = "";}
			if(phone             ==null){ phone             = "";}
			if(cell              ==null){ cell              = "";}
			if(childList         ==null){ childList         = "";}
			//set father data
			if(StringUtil.areSet(fathersFirstName, lastName)){
				Member father = new Member();
				father.setMem_role(fatherMemRole);
				father.setFam_role(FamilyDomain.FATHER);
				//if father is an elder set hascomp to false
				if(father.getFam_role().equals(MemberDomain.ELDER)){
					father.setHas_comp("FALSE");
				}
				father.setAddr1(addr1);
				father.setAddr2(addr2);
				father.setBirth(fathersDOB);
				father.setFirstName(fathersFirstName);
				father.setMiddleName(fathersMiddleName);
				father.setLastName(lastName);
				father.setEmail(email);
				father.setPhone(phone);
				father.setCell(cell);
				fam.setFather(father);

			}
			//set mother data
			if(StringUtil.areSet(mothersFirstName, lastName)){
				Member mother = new Member();
				mother.setFam_role(FamilyDomain.MOTHER);
				mother.setAddr1(addr1);
				mother.setAddr2(addr2);
				mother.setBirth(mothersDOB);
				mother.setFirstName(mothersFirstName);
				mother.setMiddleName(mothersMiddleName);
				mother.setLastName(lastName);
				mother.setEmail(email);
				mother.setPhone(phone);
				mother.setCell(cell);
				fam.setMother(mother);

			}
			//get all the children
			try{
				if(childList!=null){
					int cl = Integer.parseInt(childList);
					for(int i = 0; i<cl; i++){
						Member child = new Member();
						String childFirstName = request.getParameter("child"+i);
						String childGender = request.getParameter("childGender"+i);
						String sonsPreisthood = request.getParameter("sonsPreisthood"+i);
						String childAge = request.getParameter("childDOB"+i);
						// if firstname.length is less than 1 the a 
						// child input box was created but not filled out.
						// omit these cases.
						if(childFirstName!=null&&childFirstName.length()>0){
							if(StringUtil.isSet(childFirstName)){
								child.setFirstName(childFirstName);
							}
							if(StringUtil.isSet(childAge)){
								child.setBirth(childAge);
							}
							if(StringUtil.isSet(childGender)){
								if(StringUtil.isSet(sonsPreisthood)){
									child.setMem_role(sonsPreisthood);
									if(child.getFam_role().equals(MemberDomain.ELDER)){
										child.setHas_comp("FALSE");
									}
								}
								if(childGender.equals("boy")){
									child.setFam_role(FamilyDomain.SON);
								}
								else{
									child.setFam_role(FamilyDomain.DAUGHTER);
								}
							}
							child.setAddr1(addr1);
							child.setLastName(lastName);
							child.setAddr2(addr2);
							child.setPhone(phone);
							fam.siblings.add(child);
						}

					}
				}

			}catch(Exception ex){
				ex.printStackTrace();
			}
			fam.store();


			session.setAttribute( "message",
			"<font color=red size=2>User added.</font>" );
			response.sendRedirect( "newFamily.jsp?message="+lastName+" successfully added." );
		} catch ( Exception e ) {
			session.setAttribute( "message",
			"<font color=red size=2>User added.</font>" );
			response.sendRedirect( "newFamily.jsp?message=Invalid Input.\n Family not added: "+e.toString());
			e.printStackTrace();
		}
	}
	public static void main(String args[]){

	}
}
