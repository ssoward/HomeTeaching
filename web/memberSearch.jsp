<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.util.Family"%>
<%@page import="com.soward.util.StringUtil"%>
<script type="text/javascript" >
function lf(){document.log.searchCanidate.focus();}
</script>

<%
   String message = request.getParameter( "message" );
   String searchCanidate = request.getParameter( "searchCanidate" );
	ArrayList<ArrayList<String>> potMember = new ArrayList<ArrayList<String>>();
	
	if(searchCanidate!=null){
		potMember = MemberUtil.getCanidateSearch(searchCanidate);
   }
%>

<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td align="center" valign="top">
  <br> <br> <br>
	<form action="./memberSearch.jsp" name="log" method="GET">
  Search for a Member with first or last name:
	<input type=text name=searchCanidate >
	<input type=submit value=Search>
	</form>
	</td>
	</tr>
	<tr>
		<td>
  <br> <br> <br>
		<table class="sortable" id="viewClients" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;Edit&nbsp;</th>
				<th>&nbsp;Name&nbsp;</th>
				<th>&nbsp;Last&nbsp;</th>
				<th>&nbsp;Phone&nbsp;</th>
				<th>&nbsp;Cell&nbsp;</th>
				<th>&nbsp;Address&nbsp;</th>
			</tr>
			<%
            boolean flip = false;
			if ( potMember != null ) {
			for ( ArrayList<String> temp : potMember ) {
        	  if(flip){
      			%>
      			<tr bgcolor="#eeeeee">
                <%}else{%>
      			<tr bgcolor="#ffffff">
                <%}%>
				<td>
				&nbsp;
            <a class="top_menu" href="./editMember.jsp?pid=<%=temp.get(0)%>"><%=temp.get(0)%></a>
            </td>
				<td>&nbsp;<%=temp.get(1)%>&nbsp;</td>
				<td>&nbsp;<%=temp.get(2)%>&nbsp;</td>
				<td>&nbsp;<%=temp.get(3)%>&nbsp;</td>
				<td>&nbsp;<%=temp.get(4)%>&nbsp;</td>
				<td>&nbsp;<%=temp.get(5)%>&nbsp;</td>
			</tr>
			<%
            flip = !flip;
			}}
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
<script language="JavaScript"> 
 onLoad=lf();
</script>
