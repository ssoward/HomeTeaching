package com.soward.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soward.db.DB;
import com.soward.object.Member;

public class NewMember extends HttpServlet {
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
            String firstName = request.getParameter( "memberFirst" );
            String lastName = request.getParameter( "memberLast" );
            String addr1 = request.getParameter( "memberAddr1" );
            String addr2 = request.getParameter( "memberAddr2" );
            String email = request.getParameter( "memberEmail" );
            String age = request.getParameter( "memberAge" );
            String primPhone = request.getParameter( "memberPhone" );
            String cell = request.getParameter( "memberCell" );
            String fam_role = request.getParameter( "memberFamRole" );
            String mem_role = request.getParameter( "memberMemRole" );
            String familypid = request.getParameter( "memberFamPid" );
            String memberBirth = request.getParameter( "memberBirth" );

            if ( firstName == null ) {
                firstName = "";
            }
            if ( lastName == null ) {
                lastName = "";
            }
            if ( age == null ) {
                age = "";
            }
            if ( email == null ) {
                email = "";
            }
            if ( addr1 == null ) {
                addr1 = "";
            }
            if ( addr2 == null ) {
                addr2 = "";
            }
            if ( primPhone == null ) {
                primPhone = "";
            }
            if ( cell == null ) {
                cell = "";
            }
            if ( fam_role == null ) {
                fam_role = "";
            }
            if ( mem_role == null ) {
                mem_role = "";
            }
            if ( familypid == null ) {
                familypid = "";
            }
            if ( memberBirth == null ) {
                memberBirth = "";
            }

            if ( firstName != null && firstName.length() > 1
                    && lastName != null && lastName.length() > 1 ) {
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
                response.setDateHeader( "Expires", 0 ); // prevents caching at
                // the
                // proxy server
                response.setContentType( "text/html" );
                Member newMem = new Member();
                newMem.setFirstName( firstName );
                newMem.setLastName( lastName );
                newMem.setAddr1( addr1 );
                newMem.setAddr2( addr2 );
                newMem.setEmail( email );
                newMem.setPhone( primPhone );
                newMem.setCell( cell );
                newMem.setMem_role( mem_role );
                newMem.setFam_role( fam_role );
                newMem.setFamilypid( familypid );
                newMem.setBirth( memberBirth );
                newMem.store();
                // DB db = new DB();
                // Connection conn = db.openConnection();
                // String sql = "insert into Member " +
                // "(pid, first_name, last_name,addr1, addr2, email, age, cell,
                // phone)" +
                // "values(" + null + ","
                // +"'"+ ( firstName.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( lastName.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( addr1.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( addr2.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( email.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( age.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( cell.replaceAll( "'", "&#39" ) ) + "'" + ","
                // +"'"+ ( primPhone.replaceAll( "'", "&#39" ) ) + "'" + ")";
                // Statement stm = conn.createStatement();
                // stm.executeUpdate( sql );
                // PrintWriter out = response.getWriter();

                session.setAttribute( "message",
                "<font color=red size=2>User added.</font>" );
                response.sendRedirect( "newMember.jsp?message=" + firstName
                        + " successfully added." );
            } else {
                session.setAttribute( "message",
                "<font color=red size=2>User added.</font>" );
                response.sendRedirect( "newMember.jsp?message=" + firstName
                        + " Invalid Input." );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main( String args[] ) {

    }
}
