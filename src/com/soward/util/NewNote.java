/**
 * Title: NewNote.java
 * Description: HT
 * Date: Jul 19, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.object.HTNotes;
import com.soward.object.HTStat;

public class NewNote extends HttpServlet {
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
            String famPid = request.getParameter( "famPid" );
            String famNote = request.getParameter( "famNote" );
            String compPid = request.getParameter( "compPid" );
            String note = request.getParameter( "note" );
            String username = (String) session.getAttribute("username");
            //get all assigned families
            HTNotes htn = new HTNotes();
            htn.setCompPid(compPid);
            htn.setFamPid(famPid);
            htn.setNote(note);
            htn.setUsername(username);
            htn.store();
            session.setAttribute( "message",
            "<font color=red size=2>User added.</font>" );
            //if fampid is null then this commit is for 
            //a companionship only and not for a family.
            if(famNote!=null){
                response.sendRedirect( "homeTeachingNotes.jsp?famPid="+famPid+"&compPid="+compPid+"&message=Note successfully saved." );
            }else{
                response.sendRedirect( "compNotes.jsp?famPid="+famPid+"&compPid="+compPid+"&message=Note successfully saved." );
                
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            response.sendRedirect( "homeTeachingNotes.jsp?message=Note failed." );
        }
    }
    public static void main(String args[]){

    }
}
