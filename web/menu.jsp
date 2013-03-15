
<jsp:directive.page import="com.soward.object.User"/>          <tr><td colspan="8"> 
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr> <td align="left" valign="top">
<div class="chromestyle" id="chromemenu">
<ul>
<li><a href="./index.jsp">Home</a></li>
<li><a href="#" rel="dropmenu5">Admin</a></li>	
<li><a href="#" rel="dropmenu1">Members</a></li>
<li><a href="#" rel="dropmenu2">Companions</a></li>
<li><a href="#" rel="dropmenu3">Districts</a></li>
<li><a href="#" rel="dropmenu4">Assignments</a></li>
<li><a href="#" rel="dropmenu6">Stats</a></li>
<li><a href="#" rel="dropmenu7">Utils</a></li>
</ul>
</div>


<!--1st drop down menu -->                                                   
<div id="dropmenu1" class="dropmenudiv">
<a href="./newFamily.jsp">New Family</a>
<a href="./newMember.jsp">New Member</a>
<a href="./viewFamilies.jsp">Set EQ Families</a>
<a href="./viewAllMembers.jsp">View All Members</a>
<a href="./viewElders.jsp">View Elders</a>
<a href="./memberSearch.jsp">Member Search</a>
</div>

<!--2nd drop down menu -->                                                   
<div id="dropmenu2" class="dropmenudiv">
<a href="./createComps.jsp">Create Comps</a>
<a href="./viewComps.jsp">View Comp</a>
</div>

<!--3rd drop down menu -->                                                   
<div id="dropmenu3" class="dropmenudiv">
<a href="./createDistricts.jsp">Create Districts</a>
<a href="./viewDistricts.jsp">View Districts</a>
<a href="./createDLs.jsp">Create DL</a>
</div>

<!--4th drop down menu -->                                                   
<div id="dropmenu4" class="dropmenudiv">
<a href="./createAssignments.jsp">Create Assignments</a>
<a href="./viewAssignments.jsp">View Assignments</a>
</div>

<!--5th drop down menu -->                                                   
<div id="dropmenu5" class="dropmenudiv">
<a href="./viewUser.jsp">Users</a>
<%if(User.isAdmin(session)){ %>
<a href="./newUser.jsp">New User</a>
<a href="./editPages.jsp?page=1">Edit Home Page</a>
<%} %>
</div>

<!--6th drop down menu -->                                                   
<div id="dropmenu6" class="dropmenudiv">
<a href="./collectStats.jsp">Collect Stats</a>
<a href="./viewStats.jsp">View Stats</a>
</div>

<!--7th drop down menu -->                                                   
<div id="dropmenu7" class="dropmenudiv">
<a href="./wardInfo.jsp">Ward Info</a>
<a href="./sendEmail.jsp">Send Email</a>
</div>
<script type="text/javascript">

cssdropdown.startchrome("chromemenu")

</script>
</td>
</tr>
</table>
</td><tr>
