/**
 * Title:        AjaxEQFam.java
 * Description:  AjaxEQFam.java 
 * Copyright:    Copyright (c)  2007
 * Company:      Meridias Capital Inc.
 * @author 		 Scott Soward     
 */
package com.soward.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.db.DB;
import com.soward.util.Family;

public class AjaxEQFam extends HttpServlet {

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            String famPid = request.getParameter( "famPid" );
            String oper = request.getParameter( "oper" );
            response.setHeader( "Cache-Control", "no-cache" ); // HTTP 1.1
            response.setHeader( "Pragma", "no-cache" ); // HTTP 1.0
            response.setDateHeader( "Expires", 0 ); // prevents caching at the
                                                    // proxy server
            response.setContentType( "text/html" );
            String returnComment = "";
            DB db = new DB();
            PrintWriter out = response.getWriter();
            if ( famPid != null && oper != null ) {
                // System.out.println("fampid: "+famPid);
                // System.out.println("oper: "+oper);
                Family fam = new Family();
                fam.fetch( famPid );
                int setEQ = Integer.parseInt( oper );
                if ( setEQ > 0 ) {
                    returnComment = "<font color=green>&nbsp;&nbsp;updated</font>";
                    fam.setEq_fam( "TRUE" );
                } else {
                    fam.setEq_fam( "FALSE" );
                    returnComment = "<font color=red>&nbsp;&nbsp;updated</font>";
                }
                fam.store();
                out.println( returnComment );
                out.close();
            }
        } catch ( Exception e ) {
            System.out.println( "e: " + e );
        }
    }

}
