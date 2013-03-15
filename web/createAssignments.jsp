<%@page import="com.soward.object.Comps"%>
<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<%@page import="com.soward.util.Family"%>


<%
   String message = request.getParameter( "message" );
   String cmpLen = request.getParameter( "cmpLen" );
   String compPid = request.getParameter( "compPid" );
   String dpid = request.getParameter( "dpid" );
   String displayFams = "disabled";
   String title = "Select companionship to assign";
   Comps cc = new Comps();
   Family ff = new Family();
   MemberUtil nonComp = new MemberUtil();
   if(dpid!=null){
     cc.removeFam(dpid);
   }
   if(cmpLen!=null){
	   try{
		   ArrayList<String> cL = new ArrayList<String>();
		   int comSum = Integer.parseInt(request.getParameter( "cmpLen" ));
		   for(int i = 0; i<comSum; i++){
			   String cpPid = request.getParameter( i+"ud" );
			   if(cpPid!=null){
			   	cL.add(cpPid);
			   }
		   }
     	   //update comp with assinged families
		   cc.createAssignment(cL, compPid);					   
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
   if(compPid!=null){
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

   ArrayList<Member> nonCompList = nonComp.getNonCompElders();
   ArrayList<Comps> compList = cc.getAll();
   ArrayList<Family> families = ff.getAllFamWithOutHT();
   //message = (String)session.getAttribute("message");
%>
<script language="JavaScript">
function getCompFams(str){
  window.location = "./createAssignments.jsp?compPid="+str;
}
function loopSelected()
{
  var returnVal = "";
  var selectedArray = new Array();
  var selObj = document.getElementById('sel1');
  var i;
  var count = 0;
  for (i=0; i<selObj.options.length; i++) {
    if (selObj.options[i].selected) {
      returnVal= returnVal+ count+"ud="+ selObj.options[i].value+"&";
      count++;
    }
  }

  window.location = "./createAssignments.jsp?compPid=<%=compPid%>&cmpLen="+count+"&"+returnVal
}

</script>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
    <tr align=center><td colspan=5><br><font size=3><b><%=title%><b></font></td></tr>
	<tr>
	<td valign=top>&nbsp;
  <table   border=0 cellpadding=1 cellspacing=1> 
    <tr><th>All Comps</th><th>Families</th></tr>
    <tr>
      <td>
           <select name="sel2" size="20" onclick="getCompFams(this.value)" >
      <%for(Comps comps: compList){ 
      String companions = comps.getComp1().getLastName();
      if(comps.getComp2().getLastName().length()>2){
    	  companions+=", "+comps.getComp2().getLastName();
      }
      if(comps.getComp3().getLastName().length()>2){
    	  companions+=", " +comps.getComp3().getLastName();
      }
      %>
      
      <option value="<%=comps.getPid()%>">
      <%=companions%></option>
      <%} %>
    </select>
      </td>
    <form name="createAssgn" action="createAssignments.jsp" method="post">
    <td>
    <select id="sel1" size="20" <%=displayFams%> multiple="multiple">
      <%for(Family fam: families){
      String companions = "";
      if(fam.getFather().getLastName().length()>2){
    	  companions+=""+fam.getFather().getLastName();
    	  companions+=", "+fam.getFather().getFirstName();
      if(fam.getMother().getFirstName().length()>2){
    	  companions+=" & " +fam.getMother().getFirstName();
      }
      }
      else if(fam.getMother().getLastName().length()>2){
    	  companions+="" +fam.getMother().getLastName();
    	  companions+=", " +fam.getMother().getFirstName();
      }
      %>
      
      <option value="<%=fam.getPid() %>">
      <%=companions%></option>
      <%} %>
      
    </select>


      </tr><tr>
      <td align="right" colspan=4>
      <input type="button" value="Assign" onclick="loopSelected()"/>

      </form>
      </table>
  </td>
<%
   if(displayFams.length()<1){
   ArrayList<Family> famList = cc.getAllFamForComps(cc.getPid());
%>
		<td align="" valign="top"><br><br>
		<table class="sortable" id="createComps" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;#&nbsp;</th>
				<th colspan=3>&nbsp;Family&nbsp;</th>
				<th>&nbsp;Phone&nbsp;</th>
				<th>&nbsp;Remove Family&nbsp;</th>
			</tr>
			<%
      boolean flip = false;
			int count = 1;
			if ( famList != null ) {
			for ( Family temp : famList ) {
        String famName =  temp.getName();
        if(temp.getFather().getFirstName().length()>0){
          famName+= ", "+ temp.getFather().getFirstName();
        }
        if(temp.getMother().getFirstName().length()>0){
          famName+= ", "+ temp.getMother().getFirstName();
        }
			%>
          <%if(flip){ %>
          <tr bgcolor="#eeeeee">
            <%}else{%>
          <tr bgcolor="#ffffff">
            <%}%>
	
				<td align=center>&nbsp;<%=count%>&nbsp;</td>
				<td colspan=3>&nbsp;<%=famName%>&nbsp;</td>
				<td align=center>&nbsp;<%=temp.getFather().getPhone()%>&nbsp;</td>
				<td align=center>&nbsp;<a class="top_menu" href="./createAssignments.jsp?compPid=<%=compPid%>&dpid=<%=temp.getPid()%>">remove</a>&nbsp;</td>
			</tr>
			<%
			count++;
      flip = !flip;
			}
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

