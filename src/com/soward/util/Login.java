package com.soward.util;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.soward.db.*;
public class Login  extends HttpServlet {


    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException, IOException {


        HttpSession session = request.getSession();
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0
        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server			


        // information from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ////System.out.println("ajaxTest: "+ajaxTest);

//      if(ajaxTest.equals("getMembers")){
        response.setContentType("text/html");
        DB db = new DB();
        try {
            Connection conn = db.openConnection();
            String sql = "select * from users where user_name='"+username+"' and user_pass= md5('"+password+"')";
            //System.out.println(sql);
            Statement stm = conn.createStatement();
            ResultSet rest = stm.executeQuery(sql);
            if(rest.next()){
                String userid = rest.getString("user_pid");
                String userrole = rest.getString("user_role");
                session.setAttribute("Uid", userid);
                session.setAttribute("username", username);
                session.setAttribute("userrole", userrole);
                if(userrole.equals( "stakeAdmin" )||userrole.equals( "stakeUser" )){
                    response.sendRedirect("stakeIndex.jsp");
                }else{
                    response.sendRedirect("index.jsp");
                }
                String ipid =session.getId();
                String ipadd =request.getRemoteAddr();
                System.out.print("HomeTeaching HIT: " + ipadd);
                Date d1 = new Date();
                System.out.print(" Time: " + d1);
                System.out.println("  loggedInUser: " + username);
                

            }
            else{
//              HttpSession sess = request.getSession(false);
//              request.getSession().invalidate();
//              if(sess != null){
//              sess =null;
//              }
                session.setAttribute("message", "<font color=red size=2>Invalid username or password.</font>");
                response.sendRedirect("home.jsp?message=Invalid Login");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: "+ e);
            e.printStackTrace();
        }
    }
}
