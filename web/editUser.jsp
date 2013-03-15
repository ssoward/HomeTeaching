<%@include file="jspsetup.jsp"%>
<SCRIPT LANGUAGE="JavaScript">                       
  function checkValid(){                                                                  
    if(document.editUserForm.userPass1.value!=document.editUserForm.userPass2.value||
       document.editUserForm.userPass1.value==''||
       document.editUserForm.userPass1.value.length<4||
       document.editUserForm.loginName.value==''||
       document.editUserForm.loginName.value.length<4){
      alert('Invalid Input. Possible problems:\n 1. No login name provided.\n 2. No password provided. \n 3. Passwords dont match or are less than 4 characters.');
    }
    else{                               
       document.editUserForm.submit();      
    }
  }                                                                                       
</script>
<%@include file="menu.jsp"%>

<%
            String message = request.getParameter( "message" );
            String editUserPid = request.getParameter( "pid" );
            User eu = new User();
            eu.fetch( editUserPid );

            //message = (String)session.getAttribute("message");
%>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td align="" valign="top">
		<table border=0>
			<tr>
				<form name="editUserForm" method="post" action="UserUtil">
				<input type="hidden" name="userFuncType" value="update">
				<input type="hidden" name="updatePid" value="<%=editUserPid %>">
				<th colspan=2>Edit User</th>
			</tr>
			<tr>
				<td>Login Name:</td>
				<td><input type="text" name="loginName"
					value="<%=eu.getName() %>" /></td>
			</tr>
			<tr>
				<td>First Name:</td>
				<td><input type="text" name="userFirst"
					value="<%=eu.getFirst() %>" /></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" name="userLast"
					value="<%=eu.getLast() %>" /></td>
			</tr>
			
			<tr>
				<td>Email:</td>
				<td><input type="text" name="userEmail"
					value="<%=eu.getEmail() %>" /></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type="text" name="userPhone"
					value="<%=eu.getPhone() %>" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="userPass1" value="" /></td>
			</tr>
			<tr>
				<td>Reenter Password:</td>
				<td><input type="password" name="userPass2" value="" /></td>
			</tr>
			<input type="hidden" name="editUserPid" value="<%=editUserPid %>" />
			<tr>
				<td align=right><input type="button" onclick="checkValid()"
					value="Update" /></td>
			
			</form>
			<form name="cancel" method="post" action="./viewUser.jsp">
				<td align=right><input type="submit" value="Cancel" /></td>
			</tr>
			</form>
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

