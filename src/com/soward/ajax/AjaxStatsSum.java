/**
 * Title:        AjaxStatsSum.java
 * Description:  AjaxStatsSum.java 
 * Copyright:    Copyright (c)  2007
 * Company:      Meridias Capital Inc.
 * @author 		 Scott Soward     
 */
package com.soward.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.db.DB;
import com.soward.object.HTStat;
import com.soward.util.Family;
public class AjaxStatsSum  extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            String month = (String)session.getAttribute("month");
            String year = (String)session.getAttribute("year");
            response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
            response.setHeader("Pragma","no-cache"); //HTTP 1.0
            response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
            response.setContentType("text/html");
            DB db = new DB();
            PrintWriter out = response.getWriter();
            String output = getOutput( month, year );
            out.println(output);
            out.close();
        }catch (Exception e){
            System.out.println("e: " + e);
        }
    }

    public String getOutput(String month, String year){
        ArrayList<Family> families = new ArrayList<Family>();

        HTStat hts = new HTStat();
        String output =  "";
        families = hts.getAllFamiliesForDate(month, year);
        if ( families != null&&year!=null&&month!=null ) {
            HTStat totStat = hts.getAllStats(families);
            output =         
                "<table border=0 bgcolor=\"#ffffff\" cellpadding=\"2\" cellspacing=\"2\" >"+ 
                "<tr><td colspan=3> <hr></td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total EQ families: </td><td align=right> "+totStat.getTotFam()      +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total families taught: </td><td align=right> "+totStat.getTotFamHTaught()     +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total HT by OTHER: </td><td align=right> "+totStat.getTotFamHTaughtOther()     +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total families without HTs: </td><td align=right> "+totStat.getTotFamWithOutHT()  +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total families with HTs: </td><td align=right> "+totStat.getTotFamWithHT()+" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total elders not contacted: </td><td align=right> "+totStat.getTotCompsNotContacted()+" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Percent HT with HTers: </td><td align=right> "+totStat.getTotPercentHTOfFamsWithHT()+" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Percent HT: </td><td align=right> "+totStat.getTotPercentHT()+" </td></tr>"+
                "<tr><td colspan=3> <hr></td></tr><tr><td colspan=3 align=right>"+
                "<input class=\"bodyBoldOrange\" type=button value=\"Refresh Stats\" onclick=\"this.value='Loading...';this.disabled=true;makeGetRequestStatsSum();\"/></td></tr>"+
                
                "</table>";
            families = hts.getAllFamForDate(month, year);
        }
        return output;
    }
}










