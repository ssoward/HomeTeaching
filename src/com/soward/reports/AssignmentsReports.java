/**
 * Title:        AssignmentsReports.java
 * Description:  AssignmentsReports.java 
 * Copyright:    Copyright (c)  2007
 * Company:      Meridias Capital Inc.
 * @author 		 Scott Soward     
 */
package com.soward.reports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import com.soward.db.DB;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class AssignmentsReports {
    public void createAssignmentReport() {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {

            JasperReport sample002 = JasperCompileManager
                    .compileReport( "reports/assignments.xml" );
            HashMap params = new HashMap();
            params.put( "scott", "some param value 001" );

            DB db = new DB();
            Connection conn = db.openConnection();

            jasperPrint = JasperFillManager
                    .fillReport( sample002, params, conn );
            JasperExportManager.exportReportToPdfFile( jasperPrint,
                    "reports/assignments.pdf" );
            JasperViewer.viewReport( jasperPrint );
            // JasperExportManager.exportReportToHtmlFile(jasperPrint,
            // "reports/sample.002.html");
            conn.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        AssignmentsReports ar = new AssignmentsReports();
        ar.createAssignmentReport();
    }
}
