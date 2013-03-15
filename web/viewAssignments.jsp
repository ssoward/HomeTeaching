<%@include file="jspsetup.jsp"%>
<%@page import="com.soward.util.Family"%>
<%@page import="com.soward.object.Comps"%>
<%@include file="menu.jsp"%>

<%
   Comps cc = new Comps();
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   
%>

<%
   
   ArrayList<Comps> comps = cc.getViewAssignments();// cc.getAll();
%>
<td align="" valign="top">&nbsp;
		<table class="sortable" id="createComps" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;#&nbsp;</th>
				<th>&nbsp;Companions&nbsp;</th>
				<th>&nbsp;Families&nbsp;</th>
			</tr>
			<%
            boolean flip = false;
			int count = 1;
			if ( comps != null ) {
     	for(Comps temp: comps){
					//group all comps withing the same district under the same number
          String compan = "";
          if(temp.getComp1().getLastName().length()>1){
            compan +="&nbsp;"+temp.getComp1().getFirstName()+" "+temp.getComp1().getLastName();
            compan +=" - "+temp.getComp1().getPhone();
          }
         if(temp.getComp2().getLastName().length()>1){
            compan +="<br>"+"&nbsp;"+temp.getComp2().getFirstName()+" "+temp.getComp2().getLastName();
            compan +=" - "+temp.getComp2().getPhone();
          }
         if(temp.getComp3().getLastName().length()>1){
            compan +="<br>"+"&nbsp;"+temp.getComp3().getFirstName()+" "+temp.getComp3().getLastName();
            compan +=" - "+temp.getComp3().getPhone();
          }
	
			if(flip){
			%>
			<tr bgcolor="#eeeeee">
            <%}else{%>
	    	<tr bgcolor="#ffffff">
		    <%}%>
				<td><%="&nbsp;"+count%></td>
				<td><%=compan%></td>
        <% 
        if(!temp.getFamList().isEmpty()){%><td><table width="100%" border=0 cellspacing=2 cellpadding=2><%
        for ( Family tempFamList : temp.getFamList() ) {%>
          <tr>
				    <td><b><%=tempFamList.getName()%><b></td>
				    <td><%=tempFamList.getAddr1()%></td>
				    <td><%=tempFamList.getPhone()%></td>
          </tr>
          <%}%></table></td><%}%>
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

