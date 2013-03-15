<%@page import="com.soward.object.HTNotes"%>
<%@page import="com.soward.util.Family"%>
<%@page import="java.util.ArrayList"%>
<jsp:directive.page import="com.soward.object.Comps"/>
<jsp:directive.page import="com.soward.util.StringUtil"/>

<%
	java.util.Date now = new java.util.Date();
	String Uid = (String) session.getAttribute("Uid");
	String username = (String) session.getAttribute("username");
	//System.out.println(Uid);
	if (Uid == null) {
		request.getSession().invalidate();
		if (session != null) {
			session = null;
		}
		response.sendRedirect("home.jsp?message=Please Login");
	}
%>
<html>
<head>
<META HTTP-EQUIV="imagetoolbar" CONTENT="no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Harvest Hills 9th Ward HT</title>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="css/chromestyle.css" />
</head>
<div id="contents">
<div id="main">
<body style="background-image: url(images/prophet.jpg);
 background-position: center;
 background-repeat: no-repeat;
 background-attachment: fixed;">
<center>
<%
   String message = request.getParameter( "message" );
   String famPid = request.getParameter( "famPid" );
   String compPid = request.getParameter( "compPid" );
   String notePid = request.getParameter( "notePid" );
   String delNote = request.getParameter( "delNote" );
   String famName = request.getParameter( "famName" );
   
   String note = "";
  //message = (String)session.getAttribute("message");
   HTNotes htn = new HTNotes();
   if(delNote!=null){
     message = htn.removeNote(delNote);
   }
   if(notePid!=null){
     HTNotes htnn = new HTNotes();
     htnn.fetch(notePid);
     note = htnn.getNote();

   }
   ArrayList<HTNotes> notesList = htn.fetchAll();
 
%>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr align=center>
  <td>Harvest Hills 9th Ward Home Teaching Notes. <br>
  Notes with no family specified pertain to the companionship.</td>
  </tr><tr>
  <td>&nbsp;  
  </td>
  </tr>
	<tr align=center>
	<%
		if ( message != null ) {
		%>
		<td><font color=red><%=message%></font></td>
		<% }else{ %>
	<td> </td>
		<% } %>

  </tr>
	
	<tr>
		<td align="" valign="top">
		<table class="sortable" id="viewClients" border="1" align="center" cellpadding="3" cellspacing="0">
			<tr>
				<th>&nbsp;Date&nbsp;</th>
				<th>&nbsp;Note&nbsp;</th>
				<th>&nbsp;User&nbsp;</th>
				<th>&nbsp;Family&nbsp;</th>
				<th>&nbsp;Comp&nbsp;</th>
			</tr>
			<%
			int count = 1;
            boolean flip = false;
            Family famm = new Family();
            Comps comps = new Comps();
			if ( notesList != null ) {
			for ( HTNotes temp : notesList ) {
			   famm.fetch(temp.getFamPid());
			   comps.fetch(temp.getCompPid());
        String tDate = temp.getDate();
        String tNote = temp.getNote();
        famName = "";
        if(StringUtil.isSet(famm.getName())){
          famName=famm.getName()+", "+famm.getFirstName();
        }
        if(tDate.length()>10){
          tDate = tDate.substring(0,10);
        }
 
           if(flip){
			%>
			<tr bgcolor="#eeeeee">
          <%}else{%>
			<tr bgcolor="#ffffff">
          <%}%>
				<td><%=tDate%>&nbsp;</td>
				<td><%=tNote%>&nbsp;</td>
				<td><%=temp.getUsername()%>&nbsp;</td>
				<td><%=famName%>&nbsp;</td>
				<td><%=comps.getCompNames()%>&nbsp;</td>
			</tr>
			<%
			count++;           
            flip = !flip;
			}
			            }
			%>
		</table>
    <tr><td><br><br></td></td>
    <tr><td>Enter ctrl p to print.</td></tr>
      <tr valign=bottom align=center><td>
        <form>
        <input type=button value="Close" onClick="javascript:window.close();">
        </form> 
      </td></tr>
      

		</td>
	</tr>
</table>


