/**
 * Title: NewNote.java
 * Description: HT
 * Date: Jul 19, 2007Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.object.HTNotes;
import com.soward.object.Member;

public class EmailServlet extends HttpServlet {
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
            String recpLen = request.getParameter( "recpLen" );
            String mess = request.getParameter( "mess" );
            String toName = request.getParameter( "toName" );
            String fromName = request.getParameter( "fromName" );
            String subj = request.getParameter( "subj" );
            int recpNum = Integer.parseInt( recpLen );
            ArrayList<Member> emailList = new ArrayList<Member>();
            for(int i = 0; i< recpNum; i++){
                Member mem = new Member();
                mem.fetch( request.getParameter( i+"update" ) );
                emailList.add( mem );
            }
            SendEmail se = new SendEmail();
            se.sendMail( emailList, mess, fromName, subj );
            response.sendRedirect( "sendEmail.jsp?message=Emails sent successfully." );
        } catch ( Exception e ) {
            e.printStackTrace();
            response.sendRedirect( "sendEmail.jsp?message=Note failed." );
        }
    }
    public static void main(String args[]){

    }
}
