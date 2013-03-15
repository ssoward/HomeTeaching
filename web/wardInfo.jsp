
<jsp:directive.page import="com.soward.domain.ReportsDomain"/><%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%> 

			<%
            String message = request.getParameter( "message" );
            String directory = request.getParameter("directory");
            if(directory!=null&& StringUtil.isSet(directory)){
            	//message += "<br> <a href=\"reports/directory.html\">view html</a>"+
            		//		"<br> <a href=\"reports/directory.pdf\">view pdf</a>";
            }
            %>
<tr><td>
<br>
<b>REPORTS ARE CURRENTLY A WORK IN PROGRESS</b>
<br>
All reports and content are generated dynamically
<br>
<b>Ward Info:</b>
<ul>
<li><a href=<%="./ReportsUtil?reportType="+ReportsDomain.DIRECTORY%>>Ward Directory</a></li>
<%
		if ( directory != null&&directory.equals("true") ) {
		%>
		<ul>
		<li><a href="./reports/wardDir.pdf">PDF</a></li>
		<li><a href="./reports/wardDir.html">HTML</a></li>
		</ul>
		<%
		}
		%>
<li><a href="./reports/Announcements.doc">Elder Quorum Flyer </a> </li>
</ul>
<%
		if ( message != null ) {
		%>
		<br><%=message%></br>
		<%
		}
		%>


