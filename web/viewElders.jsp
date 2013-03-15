<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>

<%
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   MemberUtil cc = new MemberUtil();
   ArrayList<Member> elders = cc.getAllElders();
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
			if ( elders != null ) {
			for ( Member temp : elders ) {
           if(flip){
			%>
			<tr bgcolor="#eeeeee">
          <%}else{%>
			<tr bgcolor="#ffffff">
          <%}%>
				<td>&nbsp;<%=count%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFirstName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getAddr1() + " " + temp.getAddr2()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getPhone()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getCell()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getBirth()%>&nbsp;</td>
				<td>&nbsp;<a class="top_menu" href="./editMember.jsp?pid=<%=temp.getPid()%>">edit</a>&nbsp;</td>
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

