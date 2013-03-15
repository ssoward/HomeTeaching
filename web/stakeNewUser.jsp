<%@include file="stakeJspsetup.jsp"%>

<SCRIPT LANGUAGE="JavaScript">                       

  function checkValid(){                                                                  
    if(document.newUserForm.userPass1.value!=document.newUserForm.userPass2.value||
       document.newUserForm.userPass1.value==''||
       document.newUserForm.userPass1.value.length<4||
       document.newUserForm.loginName.value==''||
       document.newUserForm.loginName.value.length<4){
      alert('Invalid Input. Possible problems:\n 1. No login name provided.\n 2. No password provided. \n 3. Passwords dont match or are less than 4 characters.');
    }
    else{                               
       document.newUserForm.submit();      
    }
  }                                                                                       
</script>                 

<%@include file="stakeMenu.jsp"%>

<%
String message = request.getParameter("message");

%>
 <table width="754" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr> <td align="" valign="top">
<table>
<tr><td>&nbsp;</td><tr>
  <tr>
  <form name="newUserForm" method="post" action="UserUtil">
    <th colspan=2>New User</th>
  </tr>
   <tr> <td>Login Name:         </td><td><input type="text" name="loginName"       /></td>  </tr>         
   <tr> <td>First Name:         </td><td><input type="text" name="userFirst"       /></td>  </tr>         
   <tr> <td>Last Name:          </td><td><input type="text" name="userLast"        /></td>  </tr>         
   <tr> <td>Role:               </td><td>
   <select name="userRole">
   <option value="">Select a role below.</option>
   <option value="stakeUser">stake User</option>
   <option value="stakeAdmin">Stake Admin</option>
   </select>
   </td>  </tr>
   <tr> <td>Email:              </td><td><input type="text" name="userEmail"       /></td>  </tr>
   <tr> <td>Phone:              </td><td><input type="text" name="userPhone"       /></td>  </tr>
   <tr> <td>Password:           </td><td><input type="password" name="userPass1"       /></td>  </tr>
   <tr> <td>Reenter Password:   </td><td><input type="password" name="userPass2"       /></td>  </tr>
<tr><td>&nbsp;</td><tr>
   <tr><td colspan=5 align=right><input type="button" onclick="checkValid()" value="Add User"/></td></tr>
   </form>
</table>

</td>
<% if(message!=null){%>
<td><%=message %></td>
  <%}%>
</tr>
</table>
