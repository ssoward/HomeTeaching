<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.util.Family"%>
<%@page import="com.soward.util.StringUtil"%>

<%
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   Family cc = new Family();
   ArrayList<Family> families = cc.getAll();
%>

<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
	<td>&nbsp;</td>
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
				<th>&nbsp;Cell&nbsp;</th>
				<th>&nbsp;Age&nbsp;</th>
				<th>&nbsp;Edit&nbsp;</th>
			</tr>
			<%
			int count = 1;
            boolean flip = false;
			if ( families != null ) {
			for ( Family temp : families ) {
          if(StringUtil.isSet(temp.getFather().getFirstName())){
        	  if(flip){
      			%>
      			<tr bgcolor="#eeeeee">
                <%}else{%>
      			<tr bgcolor="#ffffff">
                <%}
          %>
				<td>&nbsp;<%=count%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFather().getFirstName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFather().getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFather().getAddr1() + " " + temp.getFather().getAddr2()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFather().getPhone()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFather().getCell()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFather().getBirth()%>&nbsp;</td>
				<td>&nbsp;<a class="top_menu" href="./editMember.jsp?pid=<%=temp.getFather().getPid()%>">edit</a>&nbsp;</td>
			</tr>
			<%}if(StringUtil.isSet(temp.getMother().getFirstName())){
				 if(flip){
				%>
				<tr bgcolor="#eeeeee">
			     <%}else{%>
				<tr bgcolor="#ffffff">
			     <%}		
			%>
				<td>&nbsp;<%=count%>&nbsp;</td>
				<td>&nbsp;<%=temp.getMother().getFirstName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getMother().getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getMother().getAddr1() + " " + temp.getMother().getAddr2()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getMother().getPhone()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getMother().getCell()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getMother().getBirth()%>&nbsp;</td>
				<td>&nbsp;<a class="top_menu" href="./editMember.jsp?pid=<%=temp.getMother().getPid()%>">edit</a>&nbsp;</td>
			</tr>
			<%}for(Member kid: temp.getSiblings()){
				 if(flip){
				%>
				<tr bgcolor="#eeeeee">
			     <%}else{%>
				<tr bgcolor="#ffffff">
			     <%}		
			%>
				<td>&nbsp;<%=count%>&nbsp;</td>
				<td>&nbsp;<%=kid.getFirstName()%>&nbsp;</td>
				<td>&nbsp;<%=kid.getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=kid.getAddr1() + " " + kid.getAddr2()%>&nbsp;</td>
				<td>&nbsp;<%=kid.getPhone()%>&nbsp;</td>
				<td>&nbsp;<%=kid.getCell()%>&nbsp;</td>
				<td>&nbsp;<%=kid.getBirth()%>&nbsp;</td>
				<td>&nbsp;<a class="top_menu" href="./editMember.jsp?pid=<%=kid.getPid()%>">edit</a>&nbsp;</td>
			</tr>
			<%}
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

