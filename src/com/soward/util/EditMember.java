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

public class EditMember extends HttpServlet {
    private boolean update = false;

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
            System.out.println( "e: " + e );
        }
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        try {

            String message = "";

            String memberFirst = request.getParameter( "memberFirst" );
            String memberLast = request.getParameter( "memberLast" );
            String memberPhone = request.getParameter( "memberPhone" );
            String memberCell = request.getParameter( "memberCell" );
            String memberAddr1 = request.getParameter( "memberAddr1" );
            String memberAddr2 = request.getParameter( "memberAddr2" );
            String memberEmail = request.getParameter( "memberEmail" );
            String memberFamRole = request.getParameter( "memberFamRole" );
            String memberMemRole = request.getParameter( "memberMemRole" );
            String editMemberPid = request.getParameter( "editMemberPid" );
            String memberFamPid = request.getParameter( "memberFamPid" );
            String memberBirth = request.getParameter( "memberBirth" );

            if ( memberFirst == null ) {
                memberFirst = "";
            }
            if ( memberLast == null ) {
                memberLast = "";
            }
            if ( memberPhone == null ) {
                memberPhone = "";
            }
            if ( memberCell == null ) {
                memberCell = "";
            }
            if ( memberAddr1 == null ) {
                memberAddr1 = "";
            }
            if ( memberAddr2 == null ) {
                memberAddr2 = "";
            }
            if ( memberEmail == null ) {
                memberEmail = "";
            }
            if ( memberFamRole == null ) {
                memberFamRole = "";
            }
            if ( memberMemRole == null ) {
                memberMemRole = "";
            }
            if ( editMemberPid == null ) {
                editMemberPid = "";
            }
            if ( memberFamPid == null ) {
                memberFamPid = "";
            }
            if ( memberBirth == null ) {
                memberBirth = "";
            }
            Member mem = new Member();
            mem.fetch( editMemberPid );
            mem.setFirstName( memberFirst );
            mem.setLastName( memberLast );
            mem.setPhone( memberPhone );
            mem.setCell( memberCell );
            mem.setAddr1( memberAddr1 );
            mem.setAddr2( memberAddr2 );
            mem.setEmail( memberEmail );
            mem.setFam_role( memberFamRole );
            mem.setMem_role( memberMemRole );
            mem.setPid( editMemberPid );
            mem.setFamilypid( memberFamPid );
            mem.setBirth( memberBirth );

            String Uid = (String) session.getAttribute( "Uid" );
            // System.out.println(Uid);
            if ( Uid == null ) {
                request.getSession().invalidate();
                if ( session != null ) {
                    session = null;
                }
                response.sendRedirect( "home.jsp?mustLogin=true" );
            }
            mem.store();

            session
                    .setAttribute( "message",
                            "<font color=red size=2>Member successfully updated.</font>" );
            response.sendRedirect( "editMember.jsp?pid=" + mem.getPid()
                    + "&message=Member successfully updated." );
        } catch ( Exception e ) {
            session.setAttribute( "message",
                    "<font color=red size=2>Member failed to update.</font>" );
            response
                    .sendRedirect( "editMember.jsp?message=Member failed to update." );
            e.printStackTrace();
        }
    }
}
