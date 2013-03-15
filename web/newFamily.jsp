
<%@include file="jspsetup.jsp"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.domain.MemberDomain"%>
<SCRIPT LANGUAGE="JavaScript" SRC="js/_CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript"> var cal = new CalendarPopup(); </SCRIPT>

<script language="JavaScript">			
var nextHiddenIndex = 1;
var nextGenderHiddenIndex = 1;
			
function AddFileInput()
{
  document.getElementById("child" + nextHiddenIndex).style.display = document.all ? "block" : "table-row";
  nextHiddenIndex++;
}
function getBackUp(idCount)
{
    document.getElementById("childPhood" + idCount).style.display = 'none';
}
function getBack(idCount)
{
    document.getElementById("childPhood" + idCount).style.display = document.all ? "block" : "table-row";
}
function checkValid(){                                                                  
    if(document.newFamily.fathersFirstName.value==''||
       document.newFamily.fathersFirstName.length<1||
       document.newFamily.lastName.value==''||
       document.newFamily.lastName.length<1
       ){
      alert('Invalid Input. Possible problems:\n 1. No fathers name provided.\n 2. No last name provided. \n');
    }
    else{                               
       document.newFamily.childList.value=nextHiddenIndex;
       document.newFamily.submit();      
    }
  }                                                                                       
</script>
<%
//set default date for bob
Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int mon  = c.get(Calendar.MONTH);
int day  = c.get(Calendar.DAY_OF_MONTH);
String date = "";
if(mon<10){date+="0"+(mon+1);}else{date+=(mon+1);}
if(day<10){date+="-0"+day;}else{date+="-"+day;}
date+="-"+year;

String message = request.getParameter("message");
MemberDomain md = new MemberDomain();
ArrayList<MemberDomain> mdList = md.getAllList(); 
//message = (String)session.getAttribute("message");

%>
 <table width="754" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr> <td align="" valign="top">
<table>
<tr><td>&nbsp;</td><tr>
  <tr>
  <form method="post" name="newFamily" action="NewFamily">
    <th colspan=2 align=center>New Family</th>
    
  <tr><td colspan=5>If you are entering a single adult or single parent, skip unneeded fields.</td><tr>
  <tr><td>&nbsp;</td><tr>
  </tr>
   <tr> <td>Fathers First           </td><td><input maxlength="40" size=40 type="text" name="fathersFirstName"/></td>    
    <td><select name="fatherMemRole">
    <option value="">Select Ordination</option>
    <%for(MemberDomain temp: mdList){ %>
     <option value="<%=temp.getDomainVal() %>"><%=temp.getDomainType() %></option>
     <%} %>
      </select> </td> </tr>
      <tr> <td>Fathers Middle          </td><td><input maxlength="40" size=40 type="text" name="fathersMiddleName"/></td>
   <td>
   <input size=16 type="text" value="Date of Birth" name="fathersDOB" onfocus="if(document.newFamily.fathersDOB.value=='Date of Birth'){document.newFamily.fathersDOB.value='';};"></td><td>
   <A HREF="#" onClick="cal.select(document.forms['<%="newFamily"%>'].<%="fathersDOB"%>,'<%="anchor"%>','MM-dd-yyyy'); return false;" NAME="<%="anchor"%>" ID="<%="anchor"%>">
   <img width="25" src="images/calendar.jpg"/></A>
   </td>
   
   
   
        </tr>         
   
   <tr> <td>Mothers First Name      </td><td><input maxlength="40" size=40 type="text" name="mothersFirstName"/></td>     </tr>         
   <tr> <td>Mothers Middle          </td><td><input maxlength="40" size=40 type="text" name="mothersMiddleName"/></td> <td>  
   <input size=16 type="text" value="Date of Birth" name="mothersDOB" onfocus="if(document.newFamily.mothersDOB.value=='Date of Birth'){document.newFamily.mothersDOB.value='';};"></td><td>
   <A HREF="#" onClick="cal.select(document.forms['<%="newFamily"%>'].<%="mothersDOB"%>,'<%="anchor"%>','MM-dd-yyyy'); return false;" NAME="<%="anchor"%>" ID="<%="anchor"%>">
   <img width="25" src="images/calendar.jpg"/></A>
   </td></tr>
 
   <tr> <td>Last          </td><td><input maxlength="40" size=40 type="text" name="lastName" /></td>      </tr>
   <tr> <td>Street       </td><td><input maxlength="40" size=40 type="text" name="addr1"    /></td>          </tr>
   <tr> <td>Address       </td><td><input value="Saratoga Springs, UT. 84045" maxlength="40" size=40 type="text" name="addr2"    /></td>          </tr>
   <tr> <td>Email         </td><td><input maxlength="40" size=40 type="text" name="email"    /></td>         </tr>
   <tr> <td>Phone         </td><td><input maxlength="40" size=40 type="text" name="phone"    /></td>         </tr>
   <tr> <td>Cell          </td><td><input maxlength="40" size=40 type="text" name="cell"    /></td>         </tr>
   <%for(int i=0; i<25; i++){ %>
     <%if(i<1){%>
     <tr valign="top" id="<%="child"+i%>" >
     <td>Child <%=(i+1)%>:</td><td><input type=text name="<%="child"+i%>" size=40></td>
   <td>
   <input size=16 type="text" value="Date of Birth" name="<%="childDOB"+i%>" onfocus="if(document.newFamily.fathersDOB.value=='Date of Birth'){document.newFamily.<%="childDOB"+i%>.value='';};"></td><td>
   <A HREF="#" onClick="cal.select(document.forms['<%="newFamily"%>'].<%="childDOB"+i%>,'<%="anchor"%>','MM-dd-yyyy'); return false;" NAME="<%="anchor"%>" ID="<%="anchor"%>">
   <img width="25" src="images/calendar.jpg"/></A>
   </td>
 
     <td>
     <input type="radio" name="<%="childGender"+i%>" value="girl" checked="checked" onclick="getBackUp('0001')">Girl
     <input type="radio" name="<%="childGender"+i%>" value="boy"  onclick="getBack('0001')">Boy</td> 
     <td valign="top" id="childPhood0001" style="display:none">
     <select name="<%="sonsPreisthood"+i%>"> 
      <option value="">Select Ordination</option>
     <%for(MemberDomain temp: mdList){ %>
     <option value="<%=temp.getDomainVal() %>"><%=temp.getDomainType() %></option>
     <%} %> 
     </select> </td> </tr>
    <%}else{%>

    <tr valign="top" id="<%="child"+i%>" style="display:none"><td>
    Child <%=(i+1)%>:</td><td><input type=text name="<%="child"+i%>" size=40></td>
   <td>
   <input size=16 type="text" value="Date of Birth" name="<%="childDOB"+i%>" onfocus="if(document.newFamily.<%="childDOB"+i%>.value=='Date of Birth'){document.newFamily.<%="childDOB"+i%>.value='';};"></td><td>
   <A HREF="#" onClick="cal.select(document.forms['<%="newFamily"%>'].<%="childDOB"+i%>,'<%="anchor"%>','MM-dd-yyyy'); return false;" NAME="<%="anchor"%>" ID="<%="anchor"%>">
   <img width="25" src="images/calendar.jpg"/></A>
   </td>
 
    <td>
    <input type="radio" name="<%="childGender"+i%>" value="girl" checked="checked" onclick="getBackUp(<%=i%>)">Girl
  	<input type="radio" name="<%="childGender"+i%>" value="boy"  onclick="getBack(<%=i%>)">Boy</td>
    <td valign="top" id="<%="childPhood"+i%>" style="display:none">
    <select name="<%="sonsPreisthood"+i%>">
     <option value="">Select Ordination</option>
     <%for(MemberDomain temp: mdList){ %>
     <option value="<%=temp.getDomainVal() %>"><%=temp.getDomainType() %></option>
     <%} %>
     </select> </td>
    </tr>
        <%}}
    %>
<tr><td>&nbsp;</td><tr>
    <input type="hidden" name="childList" value="1">

   <tr><td colspan=2 align=right><input type="button" value="Add More Children" onclick="AddFileInput()"/></td>
   <td colspan=3 align=left><input type="button" onclick="checkValid()" value="Add Family"/></td></tr>
   </form>                                
</table>

</td>
<% if(message!=null){%>
<td><%=message %></td>
  <%}%>
</tr>
</table>
