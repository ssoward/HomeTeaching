
<jsp:directive.page import="com.soward.util.Family"/><%@include file="jspsetup.jsp"%>

<%@page import="com.soward.domain.MemberDomain"%>
<SCRIPT LANGUAGE="JavaScript">                       
  function checkValid(){                                                                  
    if(document.editMemberForm.memberFirst.value==''||
       document.editMemberForm.memberFirst.length<1||
       document.editMemberForm.memberLast.value==''||
       document.editMemberForm.memberLast.length<1
       ){
      alert('Invalid Input. Possible problems:\n 1. No first name provided.\n 2. No last name provided. \n');
    }
    else{                               
       document.editMemberForm.submit();      
    }
  }       
</script>
<%@include file="menu.jsp"%>

<%
            String message = request.getParameter( "message" );
            String editMemberPid= request.getParameter( "pid" );
            Member eu = new Member();
            eu.fetch( editMemberPid );
            FamilyDomain fd = new FamilyDomain();
            ArrayList<FamilyDomain> fdList = fd.getList(eu.getFam_role());
            MemberDomain md = new MemberDomain();
            ArrayList<MemberDomain> mdList = md.getList(eu.getMem_role());
            Family fam = new Family();
            ArrayList<Family> famList = fam.getAll();
            //message = (String)session.getAttribute("message");
%>
<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td align="" valign="top">
		<table border=0>
			<tr>
				<form name="editMemberForm" method="post" action="EditMember">
				<th colspan=2>Edit Member</th>
			</tr>
			<tr>
				<td>First Name:</td>
				<td><input type="text" size=40 name="memberFirst"
					value="<%=eu.getFirstName() %>" /></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" size=40 name="memberLast"
					value="<%=eu.getLastName() %>" /></td>
			</tr>
			<tr>
				<td>Birthday:</td>
				<td><input type="text" size=40 name="memberBirth"
					value="<%=eu.getBirth() %>" /></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type="text" size=40 name="memberPhone"
					value="<%=eu.getPhone() %>" /></td>
			</tr>
			<tr>
				<td>Cell:</td>
				<td><input type="text" size=40 name="memberCell"
					value="<%=eu.getCell() %>" /></td>
			</tr>
	
			<tr>
				<td>Street:</td>
				<td><input type="text" size=40 name="memberAddr1"
					value="<%=eu.getAddr1() %>" /></td>
			</tr>
    	<tr>
				<td>City St Zip:</td>
				<td><input type="text" size=40 name="memberAddr2"
					value="<%=eu.getAddr2() %>" /></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="text" size=40 name="memberEmail"
					value="<%=eu.getEmail() %>" /></td>
			</tr>
			<tr>
				<td>Family Role:</td>
				<td><select name="memberFamRole">
				<%for(FamilyDomain fdd: fdList){ %>
					<option value="<%=fdd.getDomainVal() %>"><%=fdd.getDomainType() %></option>
					<%} %>
					</select>
					</td>
			</tr>
			<tr>
				<td>Member Role:</td>
					<td><select name="memberMemRole">
				<%for(MemberDomain fdd: mdList){ %>
					<option value="<%=fdd.getDomainVal() %>"><%=fdd.getDomainType() %></option>
					<%} %>
					</select>
					</td>
			</tr>
			<tr>
				<td>Member of Fam:</td>
					<td><select name="memberFamPid">
					<option value="<%=eu.getFamilypid() %>"><%=eu.getLastName() %></option>
				<%for(Family tfam: famList){ %>
					<option value="<%=tfam.getPid() %>"><%=tfam.getName() %></option>
					<%} %>
					</select>
					</td>
			</tr>
			<input type="hidden" name="editMemberPid" value="<%=editMemberPid %>" />
			<tr>
				<td align=right><input type="button" onclick="checkValid()"
					value="Update" /></td>
			
			</form>
			<form name="cancel" method="post" action="./viewFamilies.jsp">
				<td align=right><input type="submit" value="Cancel" /></td>
			</tr>
			</form>
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

