
<jsp:directive.page import="com.soward.util.Family"/>
<jsp:directive.page import="com.soward.object.Districts"/><%@include file="jspsetup.jsp"%>

<%@page import="com.soward.domain.MemberDomain"%>

<%@include file="menu.jsp"%>

<%
            String message = request.getParameter( "message" );
            String editMemberPid= request.getParameter( "pid" );
            String distPid= request.getParameter( "distPid" );
            String districtLeader= request.getParameter( "districtLeader" );
            Districts dists = new Districts();
            if(distPid!=null&&districtLeader!=null){
            	dists.updateDist(distPid, districtLeader); 
            }
            ArrayList<Districts> distList = dists.fetchAll();
            
           

            //message = (String)session.getAttribute("message");
%>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td align="" valign="top">
    <br>
    <br>
    <br>
    <center>
		<table border=1 bgcolor="#ffffff">
			<tr><th>District Num</th><th>District Leader</th><th>Update</th></tr>
				<% for(Districts dist: distList){ %>
			<form name="editMemberForm" method="post" action="./createDLs.jsp">
			<tr>
				<td>
					<%=dist.getDistNumber() %>
				</td>
				<td>
				<input type="hidden" name="distPid" value="<%=dist.getPid() %>">
					<input type=text value="<%=dist.getDistrictLeader() %>" name="districtLeader">
				</td>
				<td>
					<input type="submit" value="save">
				</td>
			</tr>
			</form>
			<%} %>
		</table>
		</td>
		<%
		if ( message != null ) {
		%>
		<td><%=message%></td>
		<%
		}
		%>
	</tr>
</table>

