/**
 * Title:        AjaxStats.java
 * Description:  AjaxStats.java 
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
public class AjaxStats  extends HttpServlet {

    private String outputStr="";
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            String htstatPid = request.getParameter("htstatPid");
            String oper = request.getParameter("oper");
            String month = (String)session.getAttribute("month");
            String year = (String)session.getAttribute("year");
            response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
            response.setHeader("Pragma","no-cache"); //HTTP 1.0
            response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
            response.setContentType("text/html");

            DB db = new DB();
            PrintWriter out = response.getWriter();
            if(htstatPid!=null&&oper!=null){
                HTStat hts = new HTStat();
                hts.fetch( htstatPid );
                hts.setHomeTaught( getOper(oper) );
                hts.store();

                //String output = getOutput( month, year );
                String output = "";
                out.println(outputStr);
                out.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getOper( String oper ) {
        //oper==1-->YES
        //oper==2-->NO
        //oper==3-->NONE
        //oper==4-->OTHER
        String desgOper = "NO";
        this.outputStr = "<font color=red>UPDATED</font>";
        try{
            int x = Integer.parseInt( oper );
            if(x==1){
                desgOper = "YES";
                this.outputStr = "<font color=green>UPDATED</font>";
            }
            if(x==4){
                desgOper = "OTHER";
                this.outputStr = "<font color=green>UPDATED</font>";
            }
            else if(x==3){
                desgOper = "NONE";
                this.outputStr = "<font color=gray>UPDATED</font>";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return desgOper;
    }
    public String getOutput(String month, String year){
        ArrayList<Family> families = new ArrayList<Family>();

        HTStat hts = new HTStat();
        String output =  "";
        families = hts.getAllFamForDate(month, year);
        if ( families != null&&year!=null&&month!=null ) {
            HTStat totStat = hts.getAllStats(families);
            output =         
                "<table border=0 bgcolor=\"#ffffff\" cellpadding=\"2\" cellspacing=\"2\" >"+ 
                "<tr><td colspan=3> <hr></td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total EQ families: </td><td align=right> "+totStat.getTotFam()      +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total families taught: </td><td align=right> "+totStat.getTotFamHTaught()     +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total families without HTs: </td><td align=right> "+totStat.getTotFamWithOutHT()  +" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total families with HTs: </td><td align=right> "+totStat.getTotFamWithHT()+" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Total elders not contacted: </td><td align=right> "+totStat.getTotCompsNotContacted()+" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Percent HT with HTers: </td><td align=right> "+totStat.getTotPercentHTOfFamsWithHT()+" </td></tr><tr><td>"+
                "&nbsp;</td><td><li>Percent HT: </td><td align=right> "+totStat.getTotPercentHT()+" </td></tr>"+
                "<tr><td colspan=3> <hr></td></tr><tr><td colspan=3>"+
                //"<a href=\"./viewStats.jsp\">REFRESH STATS</a></td></tr>"+
                "</table>";
            families = hts.getAllFamForDate(month, year);
        }
        return output;
    }
}










