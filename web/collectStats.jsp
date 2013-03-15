
<jsp:directive.page import="com.soward.object.HTNotes"/><%@include file="jspsetup.jsp"%>
<%@page import="com.soward.util.Family"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.object.Districts"%>
<%@page import="com.soward.object.HTStat"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<script type="text/javascript" src="js/ajaxStats.js"></script>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/bv.css" rel="stylesheet" type="text/css" media="all" />
<%
  Calendar calNow = Calendar.getInstance();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
  int mm = 1000;//calNow.get(Calendar.MONTH);
  int yy = calNow.get(Calendar.YEAR);
  HTNotes htn = new HTNotes();
  String year = request.getParameter( "year" );
  boolean yearIsSelected = false;
  if(year!=null){
    yearIsSelected = true;
    }else{
      year = yy+"";
    }
  int years = yy-2;
  String monthSelected = request.getParameter("month");
  String [] s = {"Jan", "Feb", "Mar", "Apr",
   "May","Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
   "Dec"};
  ArrayList<String> months = StringUtil.getMonths();
  if(monthSelected!=null&&StringUtil.isSet(monthSelected)){
      try{
          mm = Integer.parseInt(monthSelected);
      }
      catch (Exception e){
          //do nothing, monthSelected was not an int.
      }
  }
  String month = "";
  if(mm<15){
      month = s[mm];
  }
  String today = formatter.format(calNow.getTime());

   String message = request.getParameter( "message" );
   String displayFams = "disabled";
   String title = "disabled";
   String distNum = request.getParameter( "distNum" );
   String compPid = request.getParameter( "compPid" );
   int distNumber = 0;
   String one ="";
   String two ="";
   String thr ="";
   String fou ="";
   String fiv ="";
   String six ="";

   Comps cc = new Comps();
   Districts dist = new Districts();
   MemberUtil nonComp = new MemberUtil();
   boolean districtSelected = false;
   if(compPid!=null&&!compPid.equals("null")){
      cc.fetch(compPid);
     title = "Assignments for "+cc.getComp1().getFirstName();
     if((cc.getComp2().getFirstName()).length()>1){
        title +=", "+cc.getComp2().getFirstName();
     }
     if((cc.getComp3().getFirstName()).length()>1){
        title +=", "+cc.getComp3().getFirstName();
     }
      //enable famlist if comppid is selected
     displayFams = "";
    }

   if(distNum!=null&&!distNum.equals("null")){
     districtSelected = true;
     try{
       distNumber = Integer.parseInt(distNum);
       dist.fetchForDistNumber(distNumber);
       //set radio button to which was 
       //most recently set
      switch(distNumber){
        case 1: one = "CHECKED";
        break;
        case 2: two = "CHECKED";
        break;
        case 3: thr = "CHECKED";
        break;
        case 4: fou = "CHECKED";
        break;
        case 5: fiv = "CHECKED";
        break;
        case 6: six = "CHECKED";
        break;
        default: one="CHECKED";
      }
     }catch(Exception e){
       //non number value do nothing.
     }
     }else {
     message = "Please pick a District.";
     }
   
   ArrayList<Comps> compList = cc.getAll();
   //message = (String)session.getAttribute("message");
%>

<script language="JavaScript">			
function getDistrict(){
       document.getDist.submit();      
  }                                                                                       
function getCompFams(str){
  window.location = "./collectStats.jsp?compPid="+str+"&distNum=<%=distNum%>&year=<%=year%>&month=<%=mm%>";
}

</script>

<%@page import="com.soward.object.Comps"%>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
  <br>
  <tr> <tr align="center">
  <%for(int c = 0; c<4;c++){%>
  <a href="./collectStats.jsp?compPid=<%=compPid%>&year=<%=years+c%>&month=<%=mm%>&distNum=<%=distNum%>"><%=years+c%></a>&nbsp;
    <%}%>
  </td>
</tr>
		<tr> <td>&nbsp;</td> </tr>
	<tr align="center"> <td colspan=5>
  <%for(int i=0; i< months.size(); i++){%>
  <a href="./collectStats.jsp?compPid=<%=compPid%>&year=<%=year%>&month=<%=i%>&distNum=<%=distNum%>"><%=months.get(i)%>&nbsp;</a>
    <%} 
  HashMap<String, String> dlHash =    Districts.fetchDLHash();
    %>
  </td> </tr>
  <%if(mm>999){ 
      districtSelected = false;
      message = "Please pick a month.";
	} else{
  }
  %>
  <form name="getDist" action="./collectStats.jsp">
	<tr>
	<td valign=top colspan=8>
   Select District
	 <table>
      <tr>
        <td><input type=radio "<%=one%>" value="1" name="distNum" onclick="getDistrict()"><%=dlHash.get("1") %></td>
        <td><input type=radio "<%=two%>" value="2" name="distNum" onclick="getDistrict()"><%=dlHash.get("2") %></td>
        <td><input type=radio "<%=thr%>" value="3" name="distNum" onclick="getDistrict()"><%=dlHash.get("3") %></td></tr>
      <tr><td><input type=radio "<%=fou%>" value="4" name="distNum" onclick="getDistrict()"><%=dlHash.get("4") %></td>
        <td><input type=radio "<%=fiv%>" value="5" name="distNum" onclick="getDistrict()"><%=dlHash.get("5") %></td>
        <td><input type=radio "<%=six%>" value="6" name="distNum" onclick="getDistrict()"><%=dlHash.get("6") %></td></tr>
    </table> 

   <input type="hidden" name="month" value="<%=mm%>">
   <input type="hidden" name="year" value="<%=year%>">
   <input type="hidden" name="compPid" value="<%=compPid%>">
  </td></tr>
  </form>
  <%
  //see if a district has been selected
  if(districtSelected){%>
 <tr> <td valign=top align=center colspan=8><br><font size=4><b>District:<%=" "+dlHash.get(distNum) %> Home Teaching for <font size=4 color=red><%=" "+month+" "+year%></font></b><br></font> </td><tr>
 <tr> <td valign=top align=center><br></td><tr>
  
  <tr>
	<td valign=top>
  <table   border=0 cellpadding=1 cellspacing=1> 
    <tr><th>District Companions</th></tr>
    <tr><td>
    </td></tr>
    <form action="collectStats.jsp" name="cdist" method="post">
    <tr>
    <td>
       <select name="sel2" size="15" onclick="getCompFams(this.value)" >
       <%for(Comps comps: dist.getCompList()){ 
      String companions = comps.getComp1().getLastName();
      if(comps.getComp2().getLastName().length()>2){
    	  companions+=", "+comps.getComp2().getLastName();
      }
      if(comps.getComp3().getLastName().length()>2){
    	  companions+=", " +comps.getComp3().getLastName();
      }
      String index = "";
      if(compPid!=null&&comps.getPid().equals(compPid)){
        index = "-";
      }
      %>
      <option value="<%=comps.getPid() %>">
      <%=index+companions%></option>
      <%} %>     
    </select>
	    <input type="hidden" name="distNum" value="<%=distNumber %>">
      </tr><tr>
      <td align="right" colspan=4>
      </form>
      </table>
  </td>
<%
	}//end of districtSelected
   ArrayList<Family> famList = new ArrayList<Family>();
   if(displayFams.length()<1){
   famList = cc.getAllFamForComps(cc.getPid());
%>
	<form action="NewHTStats" name="htStats" method="post">
		<td align="" valign="top">
    <table width=100%>
    <tr><td>
    <table border=0 bgcolor="#eeefff" with=80%>
    <tr><td>
    <b><%=cc.getComp1().getFirstName()%> <%=cc.getComp1().getLastName()%></b> &nbsp;&nbsp;<%=cc.getComp1().getAddr1()%>&nbsp;&nbsp;<%=cc.getComp1().getAddr2()%></td><td><%=cc.getComp1().getPhone()%>
    </td></tr><tr><td>
    <b><%=cc.getComp2().getFirstName()%> <%=cc.getComp2().getLastName()%></b> &nbsp;&nbsp;<%=cc.getComp2().getAddr1()%>&nbsp;&nbsp;<%=cc.getComp2().getAddr2()%></td><td><%=cc.getComp2().getPhone()%>
    </td>
    <%if(cc.getComp3().getLastName().length()>0){%>
    </tr><tr><td>
    <b><%=cc.getComp3().getFirstName()%> <%=cc.getComp3().getLastName()%></b> &nbsp;&nbsp;<%=cc.getComp3().getAddr1()%>&nbsp;&nbsp;<%=cc.getComp3().getAddr2()%></td><td><%=cc.getComp3().getPhone()%>
    </td>
    <%}%>
    <%//comp notes 777
    
    ArrayList<HTNotes> notesList = htn.fetchForCompPid(compPid);
    String buttonColor = notesList.size()>0 ? "class=alertInput" : "class=nonAlertInput" ;
    %>
    <td>
        &nbsp;<input name="" <%=buttonColor%> type=button value="Comp Notes" onclick="jsfPopupPage('./compNotes.jsp?compPid=<%=compPid %>', 400, 400);" >
    </td>
     </tr>
    <%}
		if ( message != null ) {
		%>
    <tr align=center><td colspan=8>
     <font size=2 color=red>
		  <%=message%>
     </font>
    </td></tr>
		<% } 
    if(famList!=null&&!famList.isEmpty()){ 
    %>
    </table>
    </td></tr>
    <tr><td>
		<table class="sortable" width="95%" id="createComps" border="1" align="left" cellpadding="0" cellspacing="0">
			<tr>
				<th colspan=1>&nbsp;Household&nbsp;</th>
				<th>&nbsp;Family&nbsp;</th>
				<th>&nbsp;HT&nbsp;</th>
				<th>&nbsp;Notes&nbsp;</th>
			</tr>
			<%
      boolean flip = false;
      int famCount = 0;
			for ( Family temp : famList ) {
        String famName = "";
        HTStat hts = new HTStat();
        hts.fetchForFamPidAndDate(temp.getPid(), mm+"", year);
        String radioChecked =  hts.getHomeTaught();
        String yes = "";
        String no = "";
        String nn = "";
        String other = "";
        String yesCheckedAndColored = "";
        if(radioChecked.equals("YES")){
          yes =  "checked";
          yesCheckedAndColored = "bgcolor=lightblue";
        }
        else if(radioChecked.equals("OTHER")){
          other =  "checked";
          yesCheckedAndColored = "bgcolor=lightgreen";
        }
        else if(radioChecked.equals("NO")){
          no = "checked";
        }else{
          nn = "checked";
        }
        
			%>
          <%if(flip){ %>
          <tr align=left bgcolor="#eeeeee">
            <%}else{%>
          <tr align=left bgcolor="#ffffff">
            <%}%>
	
				<td colspan=1>&nbsp;<b><%=temp.getName()%></b>&nbsp;
        <%="<br><ol>"+temp.getAddr1()%>
        <%="<br>"+temp.getAddr2()%>
				<%="<br>"+temp.getPhone()%>
        </td>
				<td colspan=1>
        <%="&nbsp;"+temp.getFather().getFirstName()%>&nbsp;
        <%="<br>&nbsp;"+temp.getMother().getFirstName()%>&nbsp;
        <%for(Member kid: temp.getSiblings()){ %>
        <%="<br>&nbsp;&nbsp;&nbsp;"+kid.getFirstName()%>&nbsp;
          <% }%>
          </td>
				<td colspan=1 <%=yesCheckedAndColored %>>
				
				
							 <table border=0><tr><td>
				YES</td><td><input type=radio id="<%="anchor"+famCount%>" <%=yes%> name="<%="updateStatAjax"+famCount%>" onclick="makeGetRequest(<%=hts.getPid()%>, <%=famCount%>,1);">
        </td></tr><tr><td>
				OTHER</td><td><input type=radio id="<%="anchor"+famCount%>" <%=other%> name="<%="updateStatAjax"+famCount%>" onclick="makeGetRequest(<%=hts.getPid()%>, <%=famCount%>,4);">
        </td></tr><tr><td>
				NO</td><td><input type=radio id="<%="anchor"+famCount%>" <%=no%> name="<%="updateStatAjax"+famCount%>" onclick="makeGetRequest(<%=hts.getPid()%>, <%=famCount%>,2);">
        </td></tr><tr><td>
				NONE</td><td><input type=radio id="<%="anchor"+famCount%>" <%=nn%> name="<%="updateStatAjax"+famCount%>" onclick="makeGetRequest(<%=hts.getPid()%>, <%=famCount%>,3);">
        </td></tr><tr><td colspan=2>
        <div id="<%="description"+famCount%>"></div>
        </td></tr></table>
        
		<td colspan=1 align=center>
		<%
		ArrayList<HTNotes> famNotesList = htn.fetchForFamPid(temp.getPid());
		String inputClass = famNotesList.size()>0 ? "class=alertInput" : "class=nonAlertInput" ;
		%>
		
        <input type="hidden" name="<%="famPid"+famCount%>" value="<%=temp.getPid()%>">
        &nbsp;<input name="" type=button <%=inputClass %> value="Family Notes" onclick="jsfPopupPage('./homeTeachingNotes.jsp?compPid=<%=compPid %>&famPid=<%=temp.getPid() %>', 400, 400);" >
       </td>
			  </tr>
			<%
			famCount++;
      flip = !flip;
			}
			%>
		</table>
        <input type="hidden" name="compPid" value="<%=compPid%>">
        <input type="hidden" name="famTotal" value="<%=famCount%>">
        <input type="hidden" name="distNum" value="<%=distNum%>">
        <input type="hidden" name="monthSelected" value="<%=mm%>">
    </td></tr><tr><td>
		<table width="95%" border="0" align="left" cellpadding="0" cellspacing="1">
      <tr align=right><td colspan=2> 
		<!--  <input name="" type="submit" value="Save Stats" > -->     
      </td><td>&nbsp;</td></td><td>&nbsp;</td></tr>
		</form>
    </td></tr>
		</table>
    </td></tr>
		</table>
		</td>
		<% }else if(compPid!=null&&!compPid.equals("null")){%>
      <tr bgcolor="#ffffff" align=center><td colspan=8>
        <font size=2 color=red>
      No families assigned to this companionship.
        </font>
      </td></tr>
    <%}
%>
	</tr>
</table>

