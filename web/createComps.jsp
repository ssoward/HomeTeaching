<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<script type="text/javascript" src="js/comboBox.js"></script>
<%
   String message = request.getParameter( "message" );
   String dpid = request.getParameter( "dpid" );
   String cmpLen = request.getParameter( "cmpLen" );
   Comps cc = new Comps();
   MemberUtil nonComp = new MemberUtil();
   //get new comps
   if(dpid!=null){ 
     nonComp.disolve(dpid);
     dpid=null;
   }
   else if(cmpLen!=null){
	   try{
		   ArrayList<String> cL = new ArrayList<String>();
		   int comSum = Integer.parseInt(request.getParameter( "cmpLen" ));
		   String cpid0 = request.getParameter( "0update" );
		   String cpid1 = request.getParameter( "1update" );
		   cL.add(cpid0);
		   cL.add(cpid1);
		   String cpid2 ="";
		   if(comSum==3){
		   cpid2 = request.getParameter( "2update" );
		   cL.add(cpid2);
		   }
		   //max of 3 comps
		   if(comSum>3){%>
		   <SCRIPT LANGUAGE="JavaScript">                       
     			alert('Currently we only allow up to 3 elders per companionship.');
		   </script>   
		   <%}
     	   //update db tbl comps and set has_comp=TRUE
		   else{
			   nonComp.updateComps(cL);					   
		   }
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
   
   ArrayList<Member> nonCompList = nonComp.getNonCompElders();
   //message = (String)session.getAttribute("message");
%>

<%@page import="com.soward.object.Comps"%>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
	<td valign=top>&nbsp;
  <table   border=0 cellpadding=1 cellspacing=1> 
    <tr><th>All Elders</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Companion</th></tr>
    <tr><td>
    </td></tr>
    <form action="createComps.jsp" method="post">
    <tr>
      <td>
      <select name="sel2" size="20" multiple="multiple">
      <%for(Member nComp: nonCompList){ %>
      
      <option value="<%=nComp.getPid() %>"><%=nComp.getLastName()+", "+nComp.getFirstName() %></option>
      <%} %>
    </select>
      </td>
      </td>
      <td align="center" valign="middle">

      <input type="button" value="&lt;--"
      onclick="moveOptions(this.form.sel1, this.form.sel2);" /><br />
      <input type="button" value="--&gt;"
      onclick="moveOptions(this.form.sel2, this.form.sel1);" />
      </td>
    <td>
    <select name="sel1" size="10" multiple="multiple">
      
      
    </select>


      </tr><tr>
      <td align="right" colspan=4>
      <input type="button" value="Create" onclick="addArrayBun(this.form.sel1);"/>

      </form>
      </table>
  </td>
<%
   
   ArrayList<Comps> comps = cc.getAll();
%>
		<td align="" valign="top">&nbsp;
		<table class="sortable" id="createComps" border="1" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<th>&nbsp;#&nbsp;</th>
				<th>&nbsp;Comp1&nbsp;</th>
				<th>&nbsp;Comp2&nbsp;</th>
				<th>&nbsp;Comp3&nbsp;</th>
				<th>&nbsp;Disolve Comp&nbsp;</th>
			</tr>
			<%
      boolean flip = false;
			int count = 1;
			if ( comps != null ) {
			for ( Comps temp : comps ) {
			%>
          <%if(flip){ %>
          <tr bgcolor="#eeeeee">
            <%}else{%>
          <tr bgcolor="#ffffff">
            <%}
            	String cc1 = temp.getComp1().getLastName();	cc1 = cc1.length()>0?cc1+", "+temp.getComp1().getFirstName():"";
            	String cc2 = temp.getComp2().getLastName();	cc2 = cc2.length()>0?cc2+", "+temp.getComp2().getFirstName():"";
            	String cc3 = temp.getComp3().getLastName();	cc3 = cc3.length()>0?cc3+", "+temp.getComp3().getFirstName():"";
            
            %>
	
				<td>&nbsp;<%=count%>&nbsp;</td>
				<td>&nbsp;<%=cc1%>&nbsp;</td>
				<td>&nbsp;<%=cc2%>&nbsp;</td>
				<td>&nbsp;<%=cc3%>&nbsp;</td>
				<td>&nbsp;<a class="top_menu" href="./createComps.jsp?dpid=<%=temp.getPid()%>">disolve</a>&nbsp;</td>
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

