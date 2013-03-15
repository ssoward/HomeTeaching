/**
 * Title: HTStats.java
 * Description: HT
 * Date: Jul 18, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.db.DB;
import com.soward.object.HTStat;

public class NewHTStats extends HttpServlet {
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
			String message = "";
			String famTotal = request.getParameter( "famTotal" );
			String compPid = request.getParameter( "compPid" );
			String distNum = request.getParameter( "distNum" );
			String monthSelected = request.getParameter( "monthSelected" );
			Calendar calNow = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			int mm = calNow.get(Calendar.MONTH);
            if(monthSelected!=null&&StringUtil.isSet( monthSelected )){
                try{
                    mm = Integer.parseInt( monthSelected );
                }catch(Exception e){
                    //not interger
                }
            }
            
			int yy = calNow.get(Calendar.YEAR);
			//get all assigned families
			int famCount = Integer.parseInt(famTotal);
			for(int i = 0; i<famCount; i++){
				String famPid = request.getParameter("famPid"+i);
				String ht = request.getParameter("ht"+i);
				HTStat hts = new HTStat();
				hts.fetchForFamPidAndDate(famPid, mm+"", yy+"");
				hts.setCompPid(compPid);
				hts.setFamPid(famPid);
				hts.setHomeTaught(ht);
				hts.setMonth(mm+"");
				hts.setYear(yy+"");
				hts.store();
			}
			session.setAttribute( "message",
			"<font color=red size=2>User added.</font>" );
			response.sendRedirect( "collectStats.jsp?month="+monthSelected+"&compPid="+compPid+"&distNum="+distNum+"&message=Stats successfully saved." );

		} catch ( Exception e ) {
			e.printStackTrace();
			response.sendRedirect( "collectStats.jsp?message=Stats failed." );
		}
	}
	public static void main(String args[]){

	}
}
