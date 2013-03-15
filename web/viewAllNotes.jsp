<%@page import="com.soward.object.HTNotes"%>
<%@page import="com.soward.util.Family"%>
<%@page import="java.util.ArrayList"%>

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
   Family famm = new Family();
   famm.fetch(famPid);
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
  <form method="post" action="NewNote">
	<tr align=center>
  <td>View All Notes.  <b><%=famm.getName()+" family" %> </b> 
  </td>
  </tr><tr>
  <td>&nbsp;  
  </td>
  </tr>
	<tr align=center>
	<td colspan=2><textarea name="note" onclick="this.value=''" cols=35 rows=10><%=note%></textarea> </td>
  </tr>
	<tr align=center>
	<%
		if ( message != null ) {
		%>
		<td><font color=red><%=message%></font></td>
		<% }else{ %>
	<td> </td>
		<% } %>

	<td> </td>
	<input type="hidden" name="famPid" value="<%=famPid %>">
	<input type="hidden" name="compPid" value="<%=compPid %>">
	<td>
	<!-- <input type=submit value=Save> -->
	</td>
  </tr>
	
  </form>
	<tr>
		<td align="" valign="top">
		<table class="sortable" id="viewClients" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;Date&nbsp;</th>
				<th>&nbsp;Note&nbsp;</th>
				<th>&nbsp;User&nbsp;</th>
				<th>&nbsp;View&nbsp;</th>
				<th>&nbsp;Remove&nbsp;</th>
			</tr>
			<%
			int count = 1;
            boolean flip = false;
			if ( notesList != null ) {
			for ( HTNotes temp : notesList ) {
        String tDate = temp.getDate();
        String tNote = temp.getNote();
        if(tDate.length()>10){
          tDate = tDate.substring(0,10);
        }
        if(tNote.length()>10){
          tNote = tNote.substring(0,10);
        }
 
           if(flip){
			%>
			<tr bgcolor="#eeeeee">
          <%}else{%>
			<tr bgcolor="#ffffff">
          <%}%>
				<td>&nbsp;<%=tDate%>&nbsp;</td>
				<td>&nbsp;<%=tNote+"..."%>&nbsp;</td>
				<td>&nbsp;<%=temp.getUsername()%>&nbsp;</td>
				<td>&nbsp;<a href="./viewAllNotes.jsp?compPid=<%=compPid%>&famPid=<%=temp.getFamPid()%>&notePid=<%=temp.getPid()%>">view</a></td>
				<td>&nbsp;<a href="./viewAllNotes.jsp?compPid=<%=compPid%>&famPid=<%=temp.getFamPid()%>&delNote=<%=temp.getPid()%>">remove</a></td>
			</tr>
			<%
			count++;           
            flip = !flip;
			}
			            }
			%>
		</table>
    <tr><td><br><br></td></td>
      <tr valign=bottom align=center><td>
        <form>
        <input type=button value="Close" onClick="javascript:window.close();">
        </form> 
      </td></tr>
      

		</td>
	</tr>
</table>


