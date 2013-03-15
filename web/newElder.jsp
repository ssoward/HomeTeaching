<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>

<%
String message = request.getParameter("message");
//message = (String)session.getAttribute("message");

%>
 <table width="754" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr> <td align="" valign="top">
<table>
<tr><td>&nbsp;</td><tr>
  <tr>
  <form method="post" action="NewElder">
    <th colspan=2>New Elder</th>
  </tr>
   <tr> <td>First         </td><td><input maxlength="40" size=40 type="text" name="firstName"/></td>     </tr>         
   <tr> <td>Last          </td><td><input maxlength="40" size=40 type="text" name="lastName" /></td>      </tr>
   <tr> <td>Address       </td><td><input maxlength="40" size=40 type="text" name="addr1"    /></td>          </tr>
   <tr> <td>Address       </td><td><input value="Saratoga Springs, UT. 84045" maxlength="40" size=40 type="text" name="addr2"    /></td>          </tr>
   <tr> <td>Email         </td><td><input maxlength="40" size=40 type="text" name="email"    /></td>         </tr>
   <tr> <td>Phone         </td><td><input maxlength="40" size=40 type="text" name="phone"    /></td>         </tr>
   <tr> <td>Cell          </td><td><input maxlength="40" size=40 type="text" name="cell"    /></td>         </tr>
   <tr> <td>Age           </td><td><input maxlength="40" size=40 type="text" name="age" /></td>      </tr>
<tr><td>&nbsp;</td><tr>
   <tr><td colspan=5 align=right><input type="submit" value="Add Elder"/></td></tr>
   </form>                                
</table>

</td>
<% if(message!=null){%>
<td><%=message %></td>
  <%}%>
</tr>
</table>
