<%@page import="com.soward.object.Pages"%>
<%@page import="java.util.Date"%>


<%
   String ipid =session.getId();
   String ipinfo =application.getServerInfo();
   String ipadd =request.getRemoteAddr();
 //  System.out.print("HomeTeaching HIT: " + ipadd);
   Date d1 = new Date();
 //  System.out.println(" Time: " + d1);

%>
<%@ include file="topLayoutLogin.jsp"%>   
    <tr>
     <td align="left" valign="top">
             <table width="100%" border="0" cellspacing="6" cellpadding="0">
               <tr> <td width="154" align="left" valign="top">
                 <table width="154" border="0" cellspacing="0" cellpadding="0">
                   <tr> <td align="left" valign="top">
                     <table width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr>
                         <td align="center" valign="top" class="search-bg">
                           <table width="108" border="0" cellspacing="0" cellpadding="4">
                           <tr> <td height="16" align="left" valign="top">&nbsp;</td> </tr>
                           <tr> <td align="center" valign="top" class="blue_title">User Login</td> </tr>
                           <%@include file="login.jsp"%>
                         </table>
                         </td>
                       </tr>
                     </table>
                     </td>
                   </tr>
                 </table></td>
                 <td align="center" valign="top">
                 <table width="95%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td align="left" valign="top">
                     
                     <%
                     Pages pag = new Pages();
                     Pages currPage = pag.getPageByID( "1" );
                     %>
                     <%=currPage.getPage() %>
                     
                     
                   </tr>
                     <td align="left" valign="top">&nbsp;</td>
                   </tr>
                 </table>
                 </td>
               </tr>
             </table></td>
           </tr>
         </table>
</body>
</html>
