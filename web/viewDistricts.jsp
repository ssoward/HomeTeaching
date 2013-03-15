<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.object.Comps"%>
<%@page import="com.soward.object.Districts"%>

<%
   HashMap<String, String> distCompList = Districts.fetchDistrictNumberCompPidHash();
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   String dpid = request.getParameter( "ddpid" );
   Comps cc = new Comps();
   Districts dist = new Districts();
   MemberUtil nonComp = new MemberUtil();
   //get new districts (list of comps pids)
   if(dpid!=null){ 
     dist.removeCompFromDist(dpid);
   }  
%>
<%
   
   ArrayList<Districts> dists = dist.getAll();
%>
		<td align="" valign="top">&nbsp;
		<table class="sortable" id="createDists" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;District&nbsp;</th>
				<th>&nbsp;Companions&nbsp;</th>
				<th>&nbsp;Remove&nbsp;</th>
			</tr>
			<%
			int count = 1;
            boolean flip = false;
			if ( dists != null ) {
				for ( Districts temp1 : dists ) {
				for(Comps temp: temp1.getCompList()){
					//group all comps withing the same district under the same number
          String compan = "<table><tr>";
          if(temp.getComp1().getLastName().length()>1){
            compan +="<td>"+temp.getComp1().getFirstName()+" "+temp.getComp1().getLastName()+"<b> "+temp.getComp1().getPhone()+"</b></td>";
          }
         if(temp.getComp2().getLastName().length()>1){
            compan +="</td><td>&nbsp; "+temp.getComp2().getFirstName()+" "+temp.getComp2().getLastName()+"<b> "+temp.getComp2().getPhone()+"</b></td>";
          }
         if(temp.getComp3().getLastName().length()>1){
            compan +="</td><td>&nbsp; "+temp.getComp3().getFirstName()+" "+temp.getComp3().getLastName()+"<b> "+temp.getComp3().getPhone()+"</b></td>";
          }
          compan += "</tr></table>";
		
			%>
          <%if(flip){ %>
          <tr bgcolor="#eeeeee">
            <%}else{%>
          <tr bgcolor="#ffffff">
            <%}%>
            <td align="center"><%=distCompList.get(temp.getPid()) %></td>
				<td align=left>&nbsp;<%=compan%>&nbsp;</td>
				<%//temp1 is the district and temp is the comp in the dist, remove will just take that comp out of the dist %>
				<td>&nbsp;<a class="top_menu" href="./viewDistricts.jsp?ddpid=<%=temp.getPid()%>">remove</a>&nbsp;</td>
			</tr>
			<%
			}
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

