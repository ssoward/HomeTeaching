
<jsp:directive.page import="com.soward.object.User"/>          <tr><td colspan="8"> 
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr> <td align="left" valign="top">
<div class="chromestyle" id="chromemenu">
<ul>
<li><a href="./index.jsp">Home</a></li>
<li><a href="#" rel="dropmenu2">Admin</a></li>
<li><a href="#" rel="dropmenu1">Request</a></li>
</ul>
</div>

<!--1st drop down menu -->                                                   
<div id="dropmenu1" class="dropmenudiv">
<a href="./stakeKeyRequest.jsp">Key Request</a>
<a href="./stakeKeyRequestList.jsp">Key Request List</a>
<a href="./stakeKeyRequestItem.jsp">Edit Stake Ojbects</a>
</div>

<!--5th drop down menu -->                                                   
<div id="dropmenu2" class="dropmenudiv">
<a href="./stakeViewUser.jsp">Users</a>
<%if(User.isStakeAdmin(session)){ %>
<a href="./stakeNewUser.jsp">New User</a>
<%} %>
</div>

<script type="text/javascript">

cssdropdown.startchrome("chromemenu")

</script>
</td>
</tr>
</table>
</td><tr>
