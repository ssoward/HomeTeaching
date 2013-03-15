
<%@ page import="java.sql.*,java.util.*,java.util.Date.*"%>

<%
   //String ipid =session.getId();
   //String ipinfo =application.getServerInfo();
   //String ipadd =request.getRemoteAddr();
   //System.out.print("Journal HIT login: " + ipadd);
   //java.util.Date d1 = new java.util.Date();
   //System.out.println(" Time: " + d1);

%>


<style>
</style>

<script type="text/javascript" >
function lf(){document.log.username.focus();}
function checkLogin(){
  if(document.log.username.value!=''&& document.log.password.value!=''){
  	document.log.action="Login";  
    document.log.submit();                                                                                                      
  }                                                                                                                                               
  else{                                                                                                                                           
     alert('Invalid new username and password.');                                                                                                
  }                                                                                                                                               
}
</script>
<%
	String message = request.getParameter("message");
	boolean mess = false;
	if(message!=null){
	    mess = true;
	}
    session=null;
            %>
    <FORM name="log" method="get"> 
    <tr> <td height="20" align="left" valign="top"> 
    <font face="Arial" size="2">User Name:</font>
    <br>
    <INPUT class="input-box" TYPE="text" NAME="username">
       </td> </tr>
    <tr> <td height="20" align="left" valign="top"> 
    <font face="Arial" size="2">Password:&nbsp;&nbsp;</font>
    <br>
    <INPUT class="input-box" TYPE=password NAME="password">
       </td> </tr>
    <tr> <td height="20" align="center" valign="top"> 
    <input type=image alt="Enter" src="images/submit-bt.gif"  onclick="checkLogin();" width="83" height="22" border="0" />
       </td> </tr>
       <%if(mess){ %>
       <tr><td><font color=red><%=message%></font></td></tr>
       <%} %>
    </FORM>

<script language="JavaScript"> 
 onLoad=lf();
</script>