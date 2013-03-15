/**
 * Title:        Reports.java
 * Description:  Reports.java 
 * Copyright:    Copyright (c)  2007
 * Company:      
 * @author 		 Scott Soward     
 */
package com.soward.reports;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import com.soward.db.DB;
import com.soward.domain.ReportsDomain;

public class ReportsUtil extends HttpServlet {
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            String Uid = (String) session.getAttribute( "Uid" );
            if ( Uid == null ) {
                request.getSession().invalidate();
                if ( session != null ) {
                    session = null;
                }
                response.sendRedirect( "home.jsp?message=Please Login" );
            } else {
                doPost( request, response );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        HttpSession session = request.getSession();
        try {
            String message = "";
            String reportType = request.getParameter( "reportType" );

            JasperReport jasperReport;
            JasperPrint jasperPrint;
            try {
                String currDir = System.getProperty( "user.dir" );
                currDir = "/Applications/cat/webapps/HT/";
                // server path
                 currDir = "/home/ssoward/tools/cat/webapps/HT/";
                // System.out.println( "CURRENTDIR: " + currDir );
                if ( reportType.equals( ReportsDomain.DIRECTORY ) ) {
                    JasperReport sample002 = JasperCompileManager.compileReport( currDir + "/reports/wardDir.xml" );
                    HashMap params = new HashMap();
                    params.put( "scott", "some param value 001" );

                    DB db = new DB();
                    Connection conn = db.openConnection();

                    jasperPrint = JasperFillManager.fillReport( sample002, params, conn );
                    JasperExportManager.exportReportToHtmlFile( jasperPrint, currDir + "/reports/wardDir.html" );
                    JasperExportManager.exportReportToPdfFile( jasperPrint, currDir + "/reports/wardDir.pdf" );
                    // viewer will not work b/c it opens
                    // on the server side and not client.
                    // JasperViewer.viewReport( jasperPrint );
                    conn.close();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            session.setAttribute( "message", "<font color=red size=2>Reports generated.</font>" );
            response.sendRedirect( "wardInfo.jsp?message=Reports generated.&directory=true" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main( String args[] ) {

    }
}
