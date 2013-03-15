
<jsp:directive.page import="com.soward.object.Districts"/><%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<%
   Comps cc = new Comps();
   String message = request.getParameter( "message" );
   Districts dist = new Districts();
   //message = (String)session.getAttribute("message");
   //remove from district
   String dpid = request.getParameter( "dpid" );
    MemberUtil nonComp = new MemberUtil();
   if(dpid!=null){ 
     nonComp.disolve(dpid);
     dpid=null;
   }
%>

<%
   
   ArrayList<Comps> comps = cc.getAll();
%>
		<%@page import="com.soward.object.Comps"%>
<td align="" valign="top">&nbsp;
		<table class="sortable" id="createComps" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;#&nbsp;</th>
				<th>&nbsp;Comp1&nbsp;</th>
				<th>&nbsp;Phone1&nbsp;</th>
				<th>&nbsp;Comp2&nbsp;</th>
				<th>&nbsp;Phone2&nbsp;</th>
				<th>&nbsp;Comp3&nbsp;</th>
				<th>&nbsp;Phone3&nbsp;</th>
				<th>&nbsp;Disolve Comp&nbsp;</th>
				<th>&nbsp;Comp Notes&nbsp;</th>
			</tr>
			<%
            boolean flip = false;
			int count = 1;
			if ( comps != null ) {
			for ( Comps temp : comps ) {
			if(flip){
			%>
			<tr bgcolor="#eeeeee">
            <%}else{%>
	    	<tr bgcolor="#ffffff">
		    <%}%>
				<td>&nbsp;<%=count%>&nbsp;</td>
				<td>&nbsp;<%=temp.getComp1().getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getComp1().getPhone()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getComp2().getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getComp2().getPhone()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getComp3().getLastName()%>&nbsp;</td>
				<td>&nbsp;<%=temp.getComp3().getPhone()%>&nbsp;</td>
				<td align=center>&nbsp;<a class="top_menu" href="./viewComps.jsp?dpid=<%=temp.getPid()%>">disolve</a>&nbsp;</td>
			    <td align=center> 
			    <%//comp notes 777%>
			    	<% if(eqPresident||devUser){ %> 
        		&nbsp;<input name="" type=button value="Notes" onclick="jsfPopupPage('./compNotes.jsp?compPid=<%=temp.getPid() %>', 400, 400);" >
        		<%} %>
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

