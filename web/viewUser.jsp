<%@include file="jspsetup.jsp"%>

<%@include file="menu.jsp"%>

<%
            String message = request.getParameter( "message" );
            String remove = request.getParameter( "remove" );
            User user = new User();
			if(remove!=null){
				User rem = new User();
				rem.fetch(remove);
				if(rem.canEdit(session)){
					rem.remove();
				}
				else{
					if(session!=null){
					String currentUser = (String)session.getAttribute("username");
					%>
					<SCRIPT LANGUAGE="JavaScript">                       
     				 alert('User: <%=currentUser%> does not have permissions to perform this action.\n Please see your system admin.');
					</script>                 
					
					<%
					}
				}
			}
            ArrayList<User> uu = user.getAllUser();
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
				<th>&nbsp;Login&nbsp;</th>
				<th>&nbsp;Role&nbsp;</th>
				<th>&nbsp;First Name&nbsp;</th>
				<th>&nbsp;Last Name&nbsp;</th>
				<th>&nbsp;Email&nbsp;</th>
				<th>&nbsp;Phone&nbsp;</th>
				<th>&nbsp;Edit&nbsp;</th>
				<th>&nbsp;Remove&nbsp;</th>
			</tr>
			<%
			                if ( uu != null ) {
			                for ( User temp : uu ) {
			%>
			<tr>
				<td>&nbsp;<%=temp.getName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getRole()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getFirst()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getLast()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getEmail()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getPhone()%>&nbsp;</td>
				<%if(temp.canEdit(session)){ %>
				<td>&nbsp;<a class="top_menu" href="./editUser.jsp?pid=<%=temp.getPid()%>">edit</a>&nbsp;</td>
				<td>&nbsp;<a class="top_menu" href="./viewUser.jsp?remove=<%=temp.getPid()%>">remove</a>&nbsp;</td>
				<%}else{ %>
				<td>&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;</td>
				<%} %>
			</tr>
			<%
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
