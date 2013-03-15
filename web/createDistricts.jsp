<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.object.Districts"%>

<script language="JavaScript">			
function getDistrict(){
       document.getDist.submit();      
  }                                                                                       
</script>
<script type="text/javascript" src="js/comboBox.js"></script>
<%
   String message = request.getParameter( "message" );
   String distNum = request.getParameter( "distNum" );
   String dpid = request.getParameter( "dpid" );
   String cmpLen = request.getParameter( "cmpLen" );
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
   if(distNum!=null){
     districtSelected = true;
     try{
       distNumber = Integer.parseInt(distNum);
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
   }
   //remove from district
   if(dpid!=null){ 
     dist.removeCompFromDist(dpid);
   }
   //get new districts (list of comps pids)
   else if(cmpLen!=null){
	   try{
		   ArrayList<String> cL = new ArrayList<String>();
		   int comSum = Integer.parseInt(request.getParameter( "cmpLen" ));
		   for(int i = 0; i<comSum; i++){
			   String cpPid = request.getParameter( i+"update" );
			   if(cpPid!=null){
			   	cL.add(cpPid);
			   }
		   }
     	   //update db tbl comps and set has_comp=TRUE
		   dist.createDists(cL, distNumber+"");					   
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
   
   ArrayList<Comps> compList = cc.getAllCompsWithOutDistrict();
   dist.fetchForDistNumber(distNumber);
   //message = (String)session.getAttribute("message");
	HashMap<String, String> dlHash =    Districts.fetchDLHash();
%>

<%@page import="com.soward.object.Comps"%>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
  <form name="getDist" action="./createDistricts.jsp">
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
  </td></tr>
  </form>
  <%
  //see if a district has been selected
  if(districtSelected){%>
  <tr> <td valign=top align=center colspan=8><br><font size=4><b>Create District</font><font size=4 color=red><%=" "+dlHash.get(distNum)%></b><br></font> </td><tr>
 <tr> <td valign=top align=center><br></td><tr>
  
  <tr>
	<td valign=top>
  <table   border=0 cellpadding=1 cellspacing=1> 
    <tr><th>All Comps</th><th></th><th>District</th></tr>
    <tr><td>
    </td></tr>
    <form action="createDistricts.jsp" name="cdist" method="post">
    <tr>
      <td>
      <select name="sel2" size="20" multiple="multiple">
      <%for(Comps comps: compList){ 
      String companions = comps.getComp1().getLastName();
      if(comps.getComp2().getLastName().length()>2){
    	  companions+=", "+comps.getComp2().getLastName();
      }
      if(comps.getComp3().getLastName().length()>2){
    	  companions+=", " +comps.getComp3().getLastName();
      }
      %>
      
      <option value="<%=comps.getPid() %>">
      <%=companions%></option>
      <%} %>
    </select>
      </td>
      <td align="center" valign="middle">

      <input type="button" value="&lt;--"
      onclick="moveOptions(this.form.sel1, this.form.sel2);" /><br />
      <input type="button" value="--&gt;"
      onclick="moveOptions(this.form.sel2, this.form.sel1);" />
      </td>
    <td>
    <select name="sel1" size="20" multiple="multiple">
       <%for(Comps comps: dist.getCompList()){ 
      String companions = comps.getComp1().getLastName();
      if(comps.getComp2().getLastName().length()>2){
    	  companions+=", "+comps.getComp2().getLastName();
      }
      if(comps.getComp3().getLastName().length()>2){
    	  companions+=", " +comps.getComp3().getLastName();
      }
      %>
      
      <option value="<%=comps.getPid() %>">
      <%=companions%></option>
      <%} %>     
      
    </select>


	    <input type="hidden" name="distNum" value="<%=distNumber %>">
      </tr><tr>
      <td align="right" colspan=4>
      <input type="button" value="Create" onclick="addDists(this.form.sel1);"/>

      </form>
      </table>
  </td>
<%
   
   ArrayList<Districts> dists = dist.getAll();
%>
		<td align="" valign="top">
    		<table class="sortable" id="createDists" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;&nbsp;</th>
				<th>&nbsp;Companions&nbsp;</th>
				<th>&nbsp;Remove&nbsp;</th>
			</tr>
			<%
			int count = 1;
            boolean flip = false;
			if ( dists != null ) {
				for ( Comps temp : dist.getCompList() ) {
					//group all comps withing the same district under the same number
          String compan = "";
          if(temp.getComp1().getLastName().length()>1){
            compan +=temp.getComp1().getLastName();
          }
         if(temp.getComp2().getLastName().length()>1){
            compan +=", "+temp.getComp2().getLastName();
          }
         if(temp.getComp3().getLastName().length()>1){
            compan +=", "+temp.getComp3().getLastName();
          }
		
			%>
          <%if(flip){ %>
          <tr bgcolor="#eeeeee">
            <%}else{%>
          <tr bgcolor="#ffffff">
            <%}%>
				  <td align=left colspan=1>&nbsp;<%=" "+count%>&nbsp;</td> 
				<td align=left>&nbsp;<%=compan%>&nbsp;</td>
				<%//temp1 is the district and temp is the comp in the dist, remove will just take that comp out of the dist %>
				<td>&nbsp;<a class="top_menu" href="./createDistricts.jsp?dpid=<%=temp.getPid()%>&distNum=<%=distNum%>">remove</a>&nbsp;</td>
			</tr>
			<%
				count++;           
        flip = !flip;
			}
			}
			}//end of districtSelected
      else{%><tr><td>&nbsp;</td></tr><tr align=center><td colspan=5><font size=4>Please select a district.</td><tr><%}
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

