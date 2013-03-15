

<%@page import="com.soward.util.KeyRequestUtil"%>
<%@page import="com.soward.util.KeysUtil"%>
<%@page import="com.soward.object.Keys"%><jsp:directive.page import="com.soward.object.Districts"/>
<%@include file="stakeJspsetup.jsp"%>
<%@include file="stakeMenu.jsp"%> 
<%@page import="com.soward.object.StakeObjects"%>
<%@page import="com.soward.util.StakeObjectUtil"%>
<%@page import="com.soward.object.KeyRequest"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/_CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript"> var cal = new CalendarPopup(); </SCRIPT>
<%
	Calendar cal = Calendar.getInstance();
  	cal.set(Calendar.HOUR_OF_DAY, 0) ;
  	cal.set(Calendar.MINUTE, 0) ;
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
    
	
	String startDate = sdf.format( cal.getTime() );
  	cal.set( Calendar.HOUR_OF_DAY, 23 ) ;
  	cal.set( Calendar.MINUTE, 59 ) ;
	String endDate = sdf.format( cal.getTime() );
	
   String message = request.getParameter( "message" );
   String delPid = request.getParameter( "del" );
	if(delPid!=null){
		KeyRequestUtil.deleteId(delPid); 
	}
   
   ArrayList<KeyRequest> allReqs        = (ArrayList)KeyRequestUtil.getAll();
   List<KeyRequest> searchList   = null; 
   List<StakeObjects> leaders         = StakeObjectUtil.getObjsForType("leader");
   List<StakeObjects> wards           = StakeObjectUtil.getObjsForType("ward");
   List<StakeObjects> reqrStatus      = StakeObjectUtil.getObjsForType("reqrStatus");
   List<StakeObjects> keyTypes        = StakeObjectUtil.getObjsForType("keyType");
   String searchRequests              = request.getParameter("searchRequests");
   
   String dateOne = request.getParameter("dateOne");
   String dateTwo = request.getParameter("dateTwo"); 
   
   KeyRequest kreq = new KeyRequest();
   kreq.setBishop(request.getParameter("bishop"));
   kreq.setWard(request.getParameter("ward"));
   kreq.setReqrStatus(request.getParameter("reqrStatus"));
   kreq.setReqrName(request.getParameter("reqrName"));
   boolean showList = false;
   if(searchRequests!=null){
       searchList = KeyRequestUtil.search(kreq, dateOne, dateTwo);
       showList = true;
           }

	if(dateTwo==null&&dateOne==null){
	    dateTwo=endDate;
	    dateOne=startDate;
	}
%>
		<%@page import="com.soward.object.Comps"%>
	<td align="center">&nbsp;
    <table border="1">
      <tr><td>
      <form name="myform" method="post" action="./stakeKeyRequestList.jsp" >
		<table bgcolor="#eeeeee" border="0" align="center" cellpadding="3" cellspacing="3">
			<tr><td colspan="4" align="center">
          <b>
				Search Current Request
      </b>
			</td></tr>
			<tr> 
			<td>
                  Request Status:
                  </td><td>
                    <select name="reqrStatus">
                    <option></option>
                    <%for(StakeObjects gkv: reqrStatus){
                    	if(kreq.getReqrStatus().equals(gkv.getKey())){
                    %>
                    <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}else{%>                    
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
            </td>
			<td>
                  Request Organization:
                  </td><td>
                    <select name="ward">
                    <option></option>
                    <%for(StakeObjects gkv: wards){
                    	if(kreq.getWard().equals(gkv.getKey())){
                    %>
                    <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}else{%>                    
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
            </td>
            </tr>
			<tr> 
			<td>
                  Request Leader:
                  </td><td>
                    <select name="bishop">
                    <option></option>
                    <%for(StakeObjects gkv: leaders){
                    	if(kreq.getBishop().equals(gkv.getKey())){
                    %>
                    <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}else{%>                    
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
            </td>
			<td>
                  Requester:
                  </td><td>
                    <select name="reqrName">
                    <option></option>
                    <%for(KeyRequest gkv: allReqs){
                    	if(kreq.getReqrName().equals(gkv.getReqrName())){
                    %>
                    <option selected="selected" value="<%=gkv.getReqrName()%>"><%=gkv.getReqrName()%></option>
                    <%}else{%>                    
                    <option value="<%=gkv.getReqrName()%>"><%=gkv.getReqrName()%></option>
                    <%}}%>
                  </select>

            </td>
            </tr>
            <tr>
            	 <td colspan="4"><table border=0> <tr>
            	<TD align=left>Start date: </td><td>
            	<A HREF="#" onClick="cal.select(document.forms['<%="myform"%>'].<%="dateOne"%>,'<%="anchor"%>','MM-dd-yyyy 00:00'); return false;" 
            	NAME="<%="anchor"%>" ID="<%="anchor"%>">
            	<img width="25" src="images/calendar.jpg" border="0"/></A>&nbsp;</TD>
      			<TD align=left><input size=20 type="text" value="<%=dateOne%>" name="dateOne">
            </td>
           </tr>
           <tr>
            	<TD align=right>  End date: </td><td>
            	<A HREF="#" onClick="cal.select(document.forms['<%="myform"%>'].<%="dateTwo"%>,'<%="anchor"%>','MM-dd-yyyy 23:59'); return false;" 
            	NAME="<%="anchor"%>" ID="<%="anchor"%>">
            	<img width="25" src="images/calendar.jpg" border="0"/></A>&nbsp;</TD>
      			<TD align=left>
      			<input size=20 type="text" value="<%=dateTwo%>" name="dateTwo">
      			</td>
            </tr>
            </table>
            </td>
            </tr>
            <tr><td>
            <input type="submit" value="Search">
            </td>
            </tr>
			<tr><td>
			<input type="hidden" value="true" name="searchRequests">
			</td></tr>
				
		</table>
            </td> </tr>
		</table>
		</fom>

		
	</td>	
	
		<%if(showList){ %>
    </tr><tr>
	<td >&nbsp;
		<table bgcolor="#eeeeee" class="sortable" id="createComps" border="1" align="center" cellpadding="3" cellspacing="0">
			<tr>
				<th valign="top">#          </th>
				<th valign="top">Edit/Delete     </th>
				<th valign="top">ReqrName   </th>
				<th valign="top">ReqrCalling</th>
				<th valign="top">CreatedDate</th>
				<th valign="top">Rec Name   </th>
        <!--
				<th valign="top">RecPhone   </th>
				<th valign="top">RecAux     </th>
				<th valign="top">RecCalling </th>
        -->
				<th valign="top">Ward       </th>
				<th valign="top">Status       </th>
        <!--
				<th valign="top">BldNum     </th>
				<th valign="top">Bishop     </th>
        -->
				<th valign="top">Keys     </th>
				<th valign="top">Username   </th>
			</tr>
			<%
            boolean flip = false;
			int count = 1;
			if ( searchList != null ) {
			for ( KeyRequest temp : searchList ) {
			if(flip){
			%>
			<tr bgcolor="#eeeeee">
            <%}else{%>
	    	<tr bgcolor="#ffffff">
		    <%}%>
				<td valign="top">&nbsp;<%=count%>&nbsp;</td>
       	<td align="center" valign="top">
				<%="<a href=\"./stakeKeyRequest.jsp?pid="+temp.getId()+"\" ><img border=\"0\" src=\"./images/edit.gif\"></a>" %>
				&nbsp;
				<%="<a href=\"./stakeKeyRequestList.jsp?del="+temp.getId()+"\" onclick=\"return confirm('Delete this Item?')\"><img border=\"0\" src=\"./images/delete.gif\"></a>" %>
				</td>
				
				<td valign="top">&nbsp;<%=temp.getReqrName   ()%>&nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getReqrCalling()%>&nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getCreatedDate()%>&nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getRecFirst   ()+"&nbsp;"+temp.getRecLast    ()%>&nbsp;</td>
        <!--
				<td valign="top">&nbsp;<%=temp.getRecPhone   ()%>&nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getRecAux     ()%>&nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getRecCalling ()%>&nbsp;</td>
        -->
				<td valign="top">&nbsp;<%=temp.getWard       ()%>&nbsp;</td>
        <!--
				<td valign="top">&nbsp;<%=temp.getBldNum     ()%>&nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getBishop     ()%>&nbsp;</td>
        -->
		<td valign="top">&nbsp;<%=temp.getReqrStatus      ()%>&nbsp;</td>
        <td valign="top">&nbsp;
          <table border="0" valign="top" cellpadding="0" cellspacing="1">
          <%
          ArrayList<Keys> kList = KeysUtil.getForRequestId(temp.getId()+"");
          for(Keys tKey: kList){%>
          <tr><td>
              <%=tKey.getKeyType()+"</td><td>&nbsp; -&nbsp; "+tKey.getKeyNum()%>
          </td></tr>
          <%}%>
        </table>
          &nbsp;</td>
				<td valign="top">&nbsp;<%=temp.getUsername()  %>&nbsp;</td>
			
			</tr>
			<%
			count++;  
			flip = !flip;
				}
            }
			%>
		</table>
		<%}else{ %>
    <tr><td>
        <br/>
        <br/>
    <center>
      <font color="red">Select search criteria</font>
    </center>
    </td></tr>
		<%} %>
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

