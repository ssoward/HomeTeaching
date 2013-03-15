
<jsp:directive.page import="com.soward.object.HTStat"/><%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>

<%
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   MemberUtil cc = new MemberUtil();
   HTStat hts = new HTStat();
   int monthInt = 100;
   int years = hts.yy-2;
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
				<td>&nbsp;<%=hts.mm%>&nbsp;</td>
				<td>&nbsp;<%=hts.yy%>&nbsp;</td>
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

