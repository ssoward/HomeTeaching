<%@page import="com.soward.util.KeyRequestUtil"%>

<%@page import="com.soward.util.LeaderKeysUtil"%>
<%@page import="com.soward.object.LeaderKeys"%>
<%@page import="com.soward.enums.StakeObjectsTypes"%>
<%@page import="com.soward.object.StakeObjects"%><jsp:directive.page import="com.soward.object.Districts"/>
<%@include file="stakeJspsetup.jsp"%>
<%@include file="stakeMenu.jsp"%> 
<%@page import="com.soward.util.StakeObjectUtil"%>
<%@page import="com.soward.object.KeyRequest"%>
<%@page import="com.soward.object.Comps"%>
<script type="text/javascript" src="js/comboBox.js"></script>


<% 
	List<StakeObjects> leaders = StakeObjectUtil.getObjsForType(StakeObjectsTypes.LEADER.dbName());
	String message = "";
	HashMap<String, ArrayList<LeaderKeys>> lHash = LeaderKeysUtil.getHashForLeadersId();
	String leaderId = request.getParameter("leaderId");
	List<LeaderKeys> currLead = null;
	if(leaderId!=null){
	    currLead = LeaderKeysUtil.getAllForLeaderId(leaderId);
	}
%>

<td align="center">
<br/>
Update key count for leaders.
</td>
</tr>
<tr><td>
    <br/>
    <br/>
		<table bgcolor="#eeeeee"  border="1" align="center" cellpadding="9" cellspacing="0">
				<tr><td>
			<%if(currLead!=null){ %>
				<table bgcolor="#eeeeee" class="sortable" id="createComps" border="1" align="center" cellpadding="3" cellspacing="0">
			<tr>
				<th valign="top">Leader   </th>
				<th valign="top">Keys/Count</th>
			</tr>
			<%
				int count= 1;
				for(StakeObjects lead: leaders){ 
				ArrayList<LeaderKeys> lk = lHash.get(lead.getKey());
			%>
				<tr>
				<td><%=count%></td>
				<td><%="<a href=\"./editLeaderKeys.jsp?leaderId="+lead.getId()+"\" ><img border=\"0\" src=\"./images/edit.gif\"></a>" %></td>
				<td><%=lead.getValue() %></td>
				<td>
				<%if(lk!=null){ %>
					<%for(LeaderKeys kk: lk){ %>
						<%=kk.getKeyId() %>	
						<%=kk.getKeyCount() %>	
					<%} %>
				<%} count++;%>
				</td>				
				</tr>
			<%} %>
			</table>
				
			<%} %>
				</td>
		<td >&nbsp;
		<table bgcolor="#eeeeee" class="sortable" id="createComps" border="1" align="center" cellpadding="3" cellspacing="0">
			<tr>
				<th valign="top">#          </th>
				<th valign="top">Edit     </th>
				<th valign="top">Leader   </th>
				<th valign="top">Keys/Count</th>
			</tr>
			<%
				int count= 1;
				for(StakeObjects lead: leaders){ 
				ArrayList<LeaderKeys> lk = lHash.get(lead.getKey());
			%>
				<tr>
				<td><%=count%></td>
				<td><%="<a href=\"./editLeaderKeys.jsp?leaderId="+lead.getId()+"\" ><img border=\"0\" src=\"./images/edit.gif\"></a>" %></td>
				<td><%=lead.getValue() %></td>
				<td>
				<%if(lk!=null){ %>
					<%for(LeaderKeys kk: lk){ %>
						<%=kk.getKeyId() %>	
						<%=kk.getKeyCount() %>	
					<%} %>
				<%} count++;%>
				</td>				
				</tr>
			<%} %>
			</table>
      </td></tr>
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

