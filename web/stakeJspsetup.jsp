<%@ page import="com.soward.domain.FamilyDomain"%>
<%@ page import="java.sql.*,java.util.*,java.util.Date.*"%>
<%@ page import="com.soward.object.Member"%>
<%@ page import="com.soward.util.MemberUtil"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.TimeZone"%>
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.naming.NamingException"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="javax.servlet.http.HttpServlet"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="javax.sql.DataSource"%>
<%@ page import="com.soward.*"%>
<%@ page import="com.soward.object.User"%>
<%@ page import="com.soward.util.StringUtil"%>
<%@ include file="topLayout.jsp"%>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/bv.css" rel="stylesheet" type="text/css" media="all" />

<%
	if(session==null||request==null){
		response.sendRedirect("home.jsp?message=Please Login");
	}
	java.util.Date now = new java.util.Date();
	String Uid = (String) session.getAttribute("Uid");
	String username = (String) session.getAttribute("username");
	String userrole = (String) session.getAttribute("userrole");
	boolean eqPresident = false;
	boolean devUser = false;
	if(username!=null){
		eqPresident = username.equalsIgnoreCase("paul")?true:false;
		devUser = username.equalsIgnoreCase("scott")?true:false;
	}
	
	//System.out.println(Uid);
	if (Uid == null) {
		request.getSession().invalidate();
		if (session != null) {
			session = null;
		}
		response.sendRedirect("home.jsp?message=Please Login");
	}
	if(!userrole.equals( "stakeAdmin" )&&!userrole.equals( "stakeUser" )){
    	response.sendRedirect("index.jsp");
    }
%>

