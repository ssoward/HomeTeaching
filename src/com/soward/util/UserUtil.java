package com.soward.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.postgresql.jdbc2.optional.SimpleDataSource;

import com.soward.db.DB;
import com.soward.object.Member;

public class UserUtil extends HttpServlet {
    private boolean update = false;
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

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
            System.out.println( "e: " + e );
        }
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        try {

            String message = "";

            String loginName = request.getParameter( "loginName" );
            String userFirst = request.getParameter( "userFirst" );
            String userLast = request.getParameter( "userLast" );
            String userRole = request.getParameter( "userRole" );
            String userEmail = request.getParameter( "userEmail" );
            String userPhone = request.getParameter( "userPhone" );
            String userPass1 = request.getParameter( "userPass1" );
            String userPass2 = request.getParameter( "userPass2" );
            String stakeUser = request.getParameter( "stakeUser" );

            if(loginName ==null){loginName ="";} 
            if(userFirst ==null){userFirst ="";}
            if(userLast  ==null){userLast  ="";} 
            if(userRole  ==null){userRole  ="dl";} 
            if(userEmail ==null){userEmail ="";}
            if(userPhone ==null){userPhone ="";}
            if(userPass1 ==null){userPass1 ="";}



            String userFuncType = request.getParameter( "userFuncType" );
            String updatePid = request.getParameter( "updatePid" );
            if(userFuncType!=null&&userFuncType.equalsIgnoreCase("update")){
                update = true;
            }

            HttpSession session = request.getSession();
            String Uid = (String) session.getAttribute( "Uid" );
            String username = (String) session.getAttribute( "username" );
            // System.out.println(Uid);
            if ( Uid == null ) {
                request.getSession().invalidate();
                if ( session != null ) {
                    session = null;
                }
                response.sendRedirect( "home.jsp?mustLogin=true" );
            }
            String input = request.getParameter( "input" );
            String possibleSolutions = request.getParameter( "getPoss" );
            response.setHeader( "Cache-Control", "no-cache" ); // HTTP 1.1
            response.setHeader( "Pragma", "no-cache" ); // HTTP 1.0
            response.setDateHeader( "Expires", 0 ); // prevents caching at the
            // proxy server
            response.setContentType( "text/html" );

            DB db = new DB();
            Connection conn = db.openConnection();
            //new user or an update?
            String sql = "";
            if(update){
                sql = "update users set " +
                "user_name =" +"'" + loginName.replaceAll( "'", "&#39" ) + "'" +
                ",user_pass =" + "md5('"+ userPass1.replaceAll( "'", "&#39" ) + "')" +
                ",user_first =" +"'" + userFirst.replaceAll( "'", "&#39" )+ "'" + 
                ",user_last =" +"'" + userLast.replaceAll( "'", "&#39" ) + "'" + 
                ",user_email =" +"'"+ userEmail.replaceAll( "'", "&#39" ) + "'" +
                //",user_role =" +"'" + userRole.replaceAll( "'", "&#39" ) + "'"+
                ",user_phone=" + "'" + userPhone.replaceAll( "'", "&#39" ) + "'" + 
                " where user_pid='"+updatePid+"'";
            }
            else{
                sql = "insert into users "
                    + "(user_pid, user_name ,user_pass ,user_first ,user_last ,user_email ,user_role ,user_phone)"
                    + "values(null," + "'" + loginName.replaceAll( "'", "&#39" ) + "'" + "," + "md5('"
                    + userPass1.replaceAll( "'", "&#39" ) + "')" + "," + "'" + userFirst.replaceAll( "'", "&#39" )
                    + "'" + "," + "'" + userLast.replaceAll( "'", "&#39" ) + "'" + "," + "'"
                    + userEmail.replaceAll( "'", "&#39" ) + "'" + "," + "'" + userRole.replaceAll( "'", "&#39" ) + "'"
                    + "," + "'" + userPhone.replaceAll( "'", "&#39" ) + "'" + ")";
            }
            Statement stm = conn.createStatement();
            stm.executeUpdate( sql );
            stm.close();
            conn.close();


            session.setAttribute( "message", "<font color=red size=2>User added.</font>" );
            if(update){
                session.setAttribute( "message", "<font color=red size=2>User updated successfully.</font>" );
                if(stakeUser!=null){
                    response.sendRedirect( "stakeViewUser.jsp" );
                }else{
                    response.sendRedirect( "viewUser.jsp?message=true" );
                }
            }
            else{
                response.sendRedirect( "newUser.jsp?message=true" );
            }
        } catch ( Exception e ) {
            if(update){
                response.sendRedirect( "viewUser.jsp?message=false" );
            }
            else{
                response.sendRedirect( "newUser.jsp?message=false" );
            }

            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        String sql = "";
        sql = "";
        Family fam = new Family();
        ArrayList<Family> famList = fam.getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        for(Family temp: famList){
            for(Member kid: temp.getSiblings()){
                int age = kid.getAge();
                if(age<18&&age>11){
                    System.out.println(kid.firstName+","+kid.lastName+","+kid.getBirth()+","+age);
                }
            }
        }
    }


}
