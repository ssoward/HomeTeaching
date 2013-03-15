/**
 * Title: Nag.java
 * Description: nag
 * Date: Jun 25, 2007
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

public class Nag extends HttpServlet {

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession session = request.getSession();
        response.setHeader( "Cache-Control", "no-cache" ); // HTTP 1.1
        response.setHeader( "Pragma", "no-cache" ); // HTTP 1.0
        response.setDateHeader( "Expires", 0 ); // prevents caching at the proxy

        String recipCount = request.getParameter( "recipCount" );
        String text = request.getParameter( "text" );
        String fromName = request.getParameter( "fromName" );

        response.setContentType( "text/html" );
        try {
            boolean success = false;
            SendEmail se = new SendEmail();
            String subj = "";
            if ( text.length() > 10 ) {
                subj = text.substring( 0, 9 );
            } else {
                subj = text;
            }
            //se.sendMail( "scott.soward@gmail.com", text, "toName", fromName, subj );
            success = true;
            System.out.println( "sending successful." );
            if ( success ) {
                //session.setAttribute( "message", "<font color=red size=4>Message to " + recip + " sent Successfully.</font>" );
                response.sendRedirect( "index.jsp" );
            } else {
                // HttpSession sess = request.getSession(false);
                // request.getSession().invalidate();
                // if(sess != null){
                // sess =null;
                // }
                session.setAttribute( "message", "<font color=red size=4>Invalid username or password.</font>" );
                response.sendRedirect( "login.jsp" );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        SendEmail se = new SendEmail();
        String mess = "hello this is your message.";
        String toName ="toName";
        String fromName = "fronName";
        String subj = "subject";
        ArrayList<String> al = new ArrayList<String>();
        al.add( "scott.soward@gmail.com" );
        al.add( "scottlarock19@hotmail.com" );
//        se.sendMail( al, mess, toName, fromName, subj );
        System.out.println("done.");
    }
}
