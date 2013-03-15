<%@page import="com.soward.util.KeyRequestUtil"%>
<%@page import="com.soward.enums.StakeObjectsTypes"%><jsp:directive.page import="com.soward.object.Districts"/>
<%@include file="stakeJspsetup.jsp"%>
<%@include file="stakeMenu.jsp"%> 
<%@page import="com.soward.object.StakeObjects"%>
<%@page import="com.soward.util.StakeObjectUtil"%>
<%@page import="com.soward.object.KeyRequest"%>
<%@page import="com.soward.object.Comps"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<style type="text/css">
  .scrollTable{ 
    overflow: auto; 
      width: 100%; 
        height: 400px;  
          padding: 0px; 
            margin: 0px;  
            }
  </style>
<%
   String message = request.getParameter( "message" );
   String newValue = request.getParameter( "newValue" );
   String newType = request.getParameter( "newType" );
   String delItem = request.getParameter( "del" );
   if(delItem!=null&&delItem.length()>0){ 
    StakeObjectUtil.deleteForId(delItem); 
   }
   if(newValue!=null&&newValue.length()>0){
    StakeObjects so = new StakeObjects();
    so.setValue(newValue);
    so.setKey(StringUtil.bouncyCase(newValue));
    so.setType(newType);
    StakeObjectUtil.save(so); 
   }
   if(newType==null){
       newType = StakeObjectsTypes.WARD.dbName();
   }
   ArrayList<StakeObjects> comps = (ArrayList)StakeObjectUtil.getAll();
%>
<td align="center">
<br/>
Edit the stake data objects for the various lists.
</td>
</tr>
<tr><td align="center">
    <table border ="0" width="80%">
      <tr><td valign="top" width="40%">
    <br/>
    <br/>
    <form action="./stakeKeyRequestItem.jsp" method="post">
		<table bgcolor="#eeeeee"  border="1" align="center" cellpadding="9" cellspacing="0">
      <tr><td>
		<table bgcolor="#eeeeee"  border="0" align="center" cellpadding="9" cellspacing="0">
      <tr><td> New Value: </td><td>
        <input type="text" name="newValue" value="">
      </td></tr>
      <tr><td> Value Type: </td><td>
         <select name="newType">
         	<% for(StakeObjectsTypes stko: StakeObjectsTypes.values()){ 
                   if(stko.dbName().equals(newType)){
                    %>
	           <option selected="selected" value="<%=stko.dbName() %>"  ><%=stko.displayName() %> </option> 
                    <%}else{%>                    
	           <option value="<%=stko.dbName() %>"  ><%=stko.displayName() %> </option> 
            <%}}%>         	
           </selct>
         </td>
       </tr>
       <tr><td colspan="2" align="right"> 
           <input type="submit" value="Save Element" name="Save Element">
       </td></tr>
     </table>
      </td></tr>
     </table>


   </form>
 </td>
<td >&nbsp;
  <table border="1" width="100%" bgcolor="#eeeeee">
    <tr><td> &nbsp;Update List:&nbsp;</td><td>
         <select name="newType" onChange="(window.location='./stakeKeyRequestItem.jsp?newType='+this.value);">
         	<% for(StakeObjectsTypes stko: StakeObjectsTypes.values()){ 
                   if(stko.dbName().equals(newType)){
                    %>
	           <option selected="selected" value="<%=stko.dbName() %>"  ><%=stko.displayName() %> </option> 
                    <%}else{%>                    
	           <option value="<%=stko.dbName() %>"  ><%=stko.displayName() %> </option> 
            <%}}%>         	
           </selct>
           </td><tr>
           <tr><td colspan="2">

 <div class="scrollTable">

		<table bgcolor="#eeeeee"  width="100%" class="sortable" id="createComps" border="1" align="center" cellpadding="3" cellspacing="0">

			<tr>
			
				<th valign="top">#     </th>
				<th valign="top">Value </th>
				<th valign="top">Type   </th>
				<th valign="top">   </th>
			</tr>
			<%
            boolean flip = false;
			int count = 1;
			if ( comps != null ) {
			for ( StakeObjects temp : comps ) {
			    if(temp.getType().equals(newType)){
					if(flip){
					%>
					<tr bgcolor="#eeeeee">
		            <%}else{%>
			    	<tr bgcolor="#ffffff">
				    <%}%>
						<td valign="top">&nbsp;<%=count%>&nbsp;</td>
						<td valign="top">&nbsp;<%=temp.getValue()%>&nbsp;</td>
						<td valign="top">&nbsp;<%=StakeObjectsTypes.valueOf(temp.getType().toUpperCase()).displayName()%>&nbsp;</td>
						<td valign="top">&nbsp;<%="<a href=\"./stakeKeyRequestItem.jsp?newType="+newType+"&del="+temp.getId()+"\" onclick=\"return confirm('Delete this Item?')\"><img border=\"0\" src=\"./images/delete.gif\"></a>" %></td>
						
					</tr>
					<%
					count++;  
					flip = !flip;
					}
            }}
			%>
		</table>
  </div>

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

