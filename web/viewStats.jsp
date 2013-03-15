<%@page import="com.soward.util.Family"%>
<%@page import="com.soward.object.HTStat"%>
<%@page import="com.soward.object.HTNotes"%>
<jsp:directive.page import="com.soward.object.Districts"/>
<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/bv.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/ajaxStats.js"></script>
<%
   String title = "To display stats, select year. ";
   String message = request.getParameter( "message" );
   String month = request.getParameter( "month" );
   String year = request.getParameter( "year" );
   HashMap<String, String> distCompList = Districts.fetchDistrictNumberCompPidHash();
   HTStat hts = new HTStat();
   ArrayList<Family> families = new ArrayList<Family>();
   int monthInt = 100;
   int years = hts.yy-2;
   ArrayList<String> months = StringUtil.getMonths();
  //set default year value to current
  if(year==null&&month==null){
    year = hts.yy+"";
    //month = hts.mm+"";
  }
   if(month!=null&&year!=null){
   	session.setAttribute( "month", month); 
   	session.setAttribute( "year", year); 
//   	families = hts.getAllFamForDate(month, year);
   	families = hts.getAllFamiliesForDate(month, year);
    monthInt = Integer.parseInt(month);
   	}
%>

<%@page import="com.soward.object.Comps"%>
<table width="754" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr> <td>&nbsp; </td> </tr> 
	<tr align="center"> <td>
  <%for(int c = 0; c<4;c++){%>
    <a href="./viewStats.jsp?year=<%=years+c%>"><%=years+c%></a>&nbsp;
    <%}%>
  </td>
	<tr> <td>&nbsp;</td> </tr>
	<tr align="center"> <td>
  <%if(year!=null){
      title = "To display stats for "+year+" select a month.";
      for(int i=0; i< months.size(); i++){%>
    <a href="./viewStats.jsp?month=<%=i%>&year=<%=year%>"><%=months.get(i)%>&nbsp;</a>
    <%}}
    if(monthInt!=100){
      title = "Home Teaching stats for <font color=red>"+months.get(monthInt)+"</font> "+year+".";
    }
    %>
  </td> </tr>
	<tr> <td>&nbsp;</td> </tr>
	<tr> <td colspan=5>
  &nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font size=4><b><%=title%></b></font><p></td> <tr>
		<td align="center" valign="top">
				<%
			  int count = 1;
        boolean flip = false;
			  if ( families != null&&year!=null&&month!=null ) {
			  HTStat totStat = hts.getAllStats(families);
        %>
        <div id="statsDescription">
        
        <table border=0 bgcolor="#ffffff" cellpadding="2" cellspacing="2" >
        <tr><td colspan=3> <hr></td></tr><tr><td>
        &nbsp;</td><td><li>Total EQ families: </td><td align=right> <%=totStat.getTotFam()            %> </td></tr><tr><td>
        &nbsp;</td><td><li>Total families taught: </td><td align=right> <%=totStat.getTotFamHTaught()     %> </td></tr><tr><td>
        &nbsp;</td><td><li>Total families without HTs: </td><td align=right> <%=totStat.getTotFamWithOutHT()   %> </td></tr><tr><td>
        &nbsp;</td><td><li>Total families with HTs: </td><td align=right> <%=totStat.getTotFamWithHT()      %> </td></tr><tr><td>
        &nbsp;</td><td><li>Total HT by OTHER: </td><td align=right> <%=totStat.getTotFamHTaughtOther()     %> </td></tr><tr><td>
        &nbsp;</td><td><li>Total elders not contacted: </td><td align=right> <%=totStat.getTotCompsNotContacted()    %> </td></tr><tr><td>
        &nbsp;</td><td><li>Percent HT with HTers: </td><td align=right> <%=totStat.getTotPercentHTOfFamsWithHT()      %> </td></tr><tr><td>
        &nbsp;</td><td><li>Percent HT: </td><td align=right> <%=totStat.getTotPercentHT()      %> </td></tr>
        <tr><td colspan=3> <hr></td></tr><tr><td align=right colspan=3>
        <input type=button class="bodyBoldOrange" value="Refresh Stats" onclick="this.value='Loading...';this.disabled=true;makeGetRequestStatsSum();"/></td></tr>
        </table>
        </div>
        </td></tr>
        <tr><td colspan="100%">
        <table width="100%" border=0><tr><td>

        <table border=0 cellspacing=1 cellpadding=1><tr><td colspan=2>COLOR KEY</td>
        </tr><tr><td bgcolor=lightblue>&nbsp;&nbsp;&nbsp;</td><td>HOME TAUGHT</td>
        </tr><tr><td bgcolor=pink>&nbsp;&nbsp;&nbsp;</td><td>NOTE ADDED</td>
        </tr><tr><td bgcolor=tan>&nbsp;&nbsp;&nbsp;</td><td>NOT CONTACTED</td>
        </tr><tr><td bgcolor=lightgreen>&nbsp;&nbsp;&nbsp;</td><td>HT BY OTHER</td>
        </tr></table>

        </td> <td align="right">
        
        <table>
        <tr align=right> <td align=right colspan=9>
        	<% if(eqPresident||devUser){ %> 
                <br>&nbsp;<input name="" type=button class="bodyBoldOrange" value="View notes." onclick="jsfPopupPage('./viewAllNotes.jsp', 400, 400);" >
			<%} %>
                </td>
			  </tr>
        <tr align=right> <td align=right colspan=9> 
        	<% if(eqPresident||devUser){ %> 
                <input name="" type=button value="Print notes." class="bodyBoldOrange" onclick="jsfPopupPage('./printAllNotes.jsp', 1000, 800);" >
                </td>
			<%} %>
			  </tr>
        </table>

        </td></tr></table>
        </td>

        </tr>
        <tr>

		<td align="center" valign="top">
		<table border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;DL&nbsp;</th>
				<th>&nbsp;Home Teachers&nbsp;</th>
				<th>&nbsp;Family&nbsp;</th>
				<th>&nbsp;Address&nbsp;</th>
				<th>&nbsp;Phone&nbsp;</th>
				<th>&nbsp;Stat&nbsp;</th>
				<th>&nbsp;Notes&nbsp;</th>
			</tr>
	<%
			for ( Family temp : families ) {
        	  if(flip){
      			%>
      			<tr bgcolor="#dddddd" align=left valign=top>
                <%}else{%>
      			<tr bgcolor="#ffffff" align=left valign=top>
                <%}
        	  	String thisComp = "";
              thisComp = "<table border=\"0\" bgcolor=\"#eeefff\" width=\"100%\">";
        	  	Comps cmpps = temp.getComps();
        	  	if(StringUtil.isSet(cmpps.getComp1().getLastName())){
        	  		thisComp +="<tr align=left><td>&nbsp;"+ cmpps.getComp1().getLastName()+
        	  		", "+cmpps.getComp1().getFirstName()+"</td><td>"+cmpps.getComp1().getPhone()+"</td></tr>";
        	  	}
        	  	if(StringUtil.isSet(cmpps.getComp2().getLastName())){
        	  		thisComp += "<tr align=left><td>&nbsp;"+cmpps.getComp2().getLastName()+
        	  		", "+cmpps.getComp2().getFirstName()+"</td><td>"+cmpps.getComp2().getPhone()+"</td></tr>";
        	  	}
        	  	if(StringUtil.isSet(cmpps.getComp3().getLastName())){
        	  		thisComp += "<tr align=left><td>&nbsp;"+cmpps.getComp3().getLastName()+
        	  		", "+cmpps.getComp3().getFirstName()+"</td><td>"+cmpps.getComp3().getPhone()+"</td></tr>";
        	  	}
              thisComp +="</table>";
              if(!cmpps.hasComps()){
                thisComp = "<font color=red>No Hometeachers Assigned</font>";
              }
        	  %>
        	  	<td align="center"><%=distCompList.get(cmpps.getPid())!=null?distCompList.get(cmpps.getPid()):"<font color=red>No District</font>" %></td>
				<td bgcolor="#eeefff"><%=thisComp%></td>
				<td><table border=0 width="100%"><tr align=left><td><b><%=temp.getName()%></b></td></tr>
            <%if(StringUtil.isSet(temp.getFather().getFirstName())){%>
            <tr align=left><td>&nbsp;&nbsp;<%=temp.getFather().getFirstName()%></td></tr>
			      <%}if(StringUtil.isSet(temp.getMother().getFirstName())){  %>
            <tr align=left><td>&nbsp;&nbsp;<%=temp.getMother().getFirstName()%></td></tr>
			      <%}for(Member kid: temp.getSiblings()){ %>
            <tr align=left><td>&nbsp;&#149;&nbsp;<%=kid.getFirstName()%></td></tr>
			      <%}%></table></td>
				<td><br>&nbsp;<%=temp.getAddr1()%>&nbsp;
				<br>&nbsp;<%=temp.getAddr2()%>&nbsp;</td>
				<td align=center><br>&nbsp;<%=temp.getFather().getPhone()%>&nbsp;</td>

				<%
          String checkedYs = "";
          String checkedNo = "";
          String checkedNn = "";
          String checkedOther = "";
          String tdColor = "<td width=\"50\">";
          if(temp.getHtStat().getHomeTaught().equals("YES")){ 
            checkedYs="checked"; 
          	tdColor = "<td width=\"50\" bgcolor=\"lightblue\">";
          }
          else if(temp.getHtStat().getHomeTaught().equals("OTHER")){ 
            checkedOther="checked"; 
          	tdColor = "<td width=\"50\" bgcolor=\"lightgreen\">";
          }
          else if(temp.getHtStat().getHomeTaught().equals("NO")){ 
             checkedNo="checked"; 
          }else{
          	checkedNn="checked";
          	tdColor = "<td width=\"50\" bgcolor=\"tan\">";
          }%><%=tdColor %>
              <table border=0><tr><td>
				YES</td><td><input type=radio id="<%="anchor"+count%>" <%=checkedYs%> name="<%="updateStatAjax"+count%>" onclick="makeGetRequest(<%=temp.getHtStat().getPid()%>, <%=count%>,1);">
        </td></tr><tr><td>
				OTHER</td><td><input type=radio id="<%="anchor"+count%>" <%=checkedOther%> name="<%="updateStatAjax"+count%>" onclick="makeGetRequest(<%=temp.getHtStat().getPid()%>, <%=count%>,4);">
        </td></tr><tr><td>
				NO</td><td><input type=radio id="<%="anchor"+count%>" <%=checkedNo%> name="<%="updateStatAjax"+count%>" onclick="makeGetRequest(<%=temp.getHtStat().getPid()%>, <%=count%>,2);">
        </td></tr><tr><td>
				NONE</td><td><input type=radio id="<%="anchor"+count%>" <%=checkedNn%> name="<%="updateStatAjax"+count%>" onclick="makeGetRequest(<%=temp.getHtStat().getPid()%>, <%=count%>,3);">
        </td></tr><tr><td colspan=2>
        <div id="<%="description"+count%>"></div>
        </td></tr></table>
        </td>
        <td>
				<%
        String buttonClass = temp.isHasNote() ? "class=alertInput" : "class=nonAlertInput" ;
          %>
          	<% if(eqPresident||devUser){ %> 
       <br>&nbsp;<input name="" <%=buttonClass%> type=button value="Notes" onclick="jsfPopupPage('./homeTeachingNotes.jsp?compPid=<%=cmpps.getPid() %>&famName=<%=temp.getName()%>&famPid=<%=temp.getPid() %>', 400, 400);" >
			<%} %>
       </td>
			</tr>
      <tr>
      <td colspan="8">
		<%=hts.getHTStatDisplay(temp.getHTStatList(), month, year) %>      
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
<%
  String pageLocation = request.getParameter("pageLocation");
  if(pageLocation!=null){%>
<script type="text/javascript" >
  function lf(){ 
    document.getElementById("<%=pageLocation%>").focus();                   
  } 
  onload=lf(); 
  </script>                                 
  <%}%>
