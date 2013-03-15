<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.util.Family"%>
<%@page import="com.soward.util.StringUtil"%>

<%@page import="com.soward.util.HomeTeachingUtils"%><script type="text/javascript" src="js/ajaxEQFam.js"></script>

<%
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   Family cc = new Family();
   ArrayList<Family> families = cc.getAllFamilies();
   families = HomeTeachingUtils.alphabetizeFamList(families);
%>

<table width="754" border="0" align="center" cellpadding="7" cellspacing="0">
  <tr bgcolor=white>
	<td align=center colspan=5><font color=darkblue size=2>Set 'EQ Family' to YES if the family will be hometaught by the elders quorum.<br>
	Any family with the count number background set to red does not have  hometeachers assigned.
	</tr>
	<tr> <td>&nbsp;</td> </tr>
	<tr>
		<td align="" valign="top">
		<table class="sortable" id="viewClients" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;#&nbsp;</th>
				<th>&nbsp;Name&nbsp;</th>
				<th>&nbsp;Last&nbsp;</th>
				<th>&nbsp;Address&nbsp;</th>
				<th>&nbsp;Phone&nbsp;</th>
				<th>&nbsp;EQ Family&nbsp;</th>
			</tr>
			<%
			int count = 1;
            boolean flip = false;
			if ( families != null ) {
			for ( Family temp : families ) {
        	  if(flip){
      			%>
      			<tr bgcolor="#eeeeee">
                <%}else{%>
      			<tr bgcolor="#ffffff">
                <%}if(!temp.hasHomeTeacher()){
                	%>
					<td bgcolor="pink">&nbsp;<%=count%>&nbsp;</td>
                	<% 
                	}else{%>
                	
					<td>&nbsp;<%=count%>&nbsp;</td>
					<%} %>
				<td>&nbsp;<%=temp.getName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFirstName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getAddr1() + " " + temp.getFather().getAddr2()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getPhone()%>&nbsp;</td>
				<%if(temp.getEq_fam().equalsIgnoreCase("true")){ %>
				<td bgcolor="lightblue">
				&nbsp;YES
				<input type=radio id="<%="anchor"+count%>" checked name="<%="updateEQFamAjax"+count%>" onclick="makeGetRequest(<%=temp.getPid()%>, <%=count%>,1);">
				<hr>&nbsp;NO
                <input type=radio id="<%="anchor"+count%>" name="<%="updateEQFamAjax"+count%>" onclick="makeGetRequest(<%=temp.getPid()%>, <%=count%>,0);">
                <%}else{ %>
				<td bgcolor="pink">&nbsp;YES
				<input type=radio id="<%="anchor"+count%>" name="<%="updateEQFamAjax"+count%>" onclick="makeGetRequest(<%=temp.getPid()%>, <%=count%>,1);">
				<hr>&nbsp;NO
                <input type=radio id="<%="anchor"+count%>" checked name="<%="updateEQFamAjax"+count%>" onclick="makeGetRequest(<%=temp.getPid()%>, <%=count%>,0);">
                <%} %>
				&nbsp;
				 <div id="<%="description"+count%>"></div>
				</td>
			</tr>
			
			<%
			count++;           
            flip = !flip;
			}
			            }
			%>
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

