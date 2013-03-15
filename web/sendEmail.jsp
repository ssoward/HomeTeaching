<%@include file="jspsetup.jsp"%>
<%@page import="com.soward.object.Comps"%>
<%@include file="menu.jsp"%>
<%@page import="com.soward.object.Districts"%>

<script language="JavaScript">			
function getDistrict(){
       document.getDist.submit();      
  }    
  
  var selLength = '';
  function addRecipt(theSel){
  selLength = theSel.length;
  var i;
  var getAll = "";
  for(i=selLength-1; i>=0; i--){
    var text = theSel.options[i].name;
    var value= theSel.options[i].value;
    getAll = getAll + i+"update" + "="+ value+"&";
  }
  if(selLength<1){ alert('No recipients have been selected.'); }
  else if(document.emailForm.fromName.value==''){ alert('Please fill out the From field.'); }
  else if(document.emailForm.subj.value==''){ alert('Please enter a subject.'); }
  else if(document.emailForm.mess.value==''){ alert('Please fill out a message.'); }
  else{
       document.emailForm.button1.disabled=true; 
  	   document.emailForm.method='post';
       document.emailForm.action="EmailServlet?recpLen="+selLength+"&"+getAll+"newCom=true";      
       document.emailForm.submit();  
  }
}                                                                                   
</script>
<script type="text/javascript" src="js/comboBox.js"></script>
<%
   String message = request.getParameter( "message" );
   //message = (String)session.getAttribute("message");
   MemberUtil cc = new MemberUtil();
   ArrayList<Member> elders = cc.getAllElders();
   User loggedInUser = new User();
   loggedInUser.fetch(Uid);
%>

<table width="754" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
	<td valign=top colspan=8>
  <table   border=0 bgcolor="#ffffff" cellpadding=1 cellspacing=1> 
    <tr><th>Select Recipients</th></tr>
    <tr><td>
    </td></tr>
    <form action="EmailServlet" name="emailForm" method="post">
    <tr>
      <td valign="top">
      <select name="sel2" size="15" multiple="multiple">
      <%
			for ( Member temp : elders ) {
        if(temp.getEmail()!=null&&temp.getEmail().length()>0){
          String entry = temp.getFirstName()+", "+temp.getLastName()+" _";
          while(entry.length()<20){
            entry+="_";
          }
          String te = temp.getEmail();
          if(te.length()>20){
            //te = te.substring(0, 20);
          }
            entry+=" "+te;
        %>
      <option value="<%=temp.getPid() %>"> <%=entry%></option> 
          <%}}%>
    </select>
      </td>
          </tr> <tr>
      <td valign="top" align="center" valign="middle">

      <input type="button" value="&lt;--"
      onclick="moveOptions(this.form.sel1, this.form.sel2);" /><br />
      <input type="button" value="--&gt;"
      onclick="moveOptions(this.form.sel2, this.form.sel1);" />
      </td>
      </tr> <tr>
    <td valign="top">
    <select name="sel1" size="5" multiple="multiple">
      
    </select>


      </tr>
		</table>
    </td>
  <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td valign="top"><table border=0 bgcolor="#ffffff" valign="top">
      <tr><th align="center" colspan="3">Create Email</th><tr>
      <tr><td valign="top">From: </td><td><input size="30" type="text" name="fromName" value="<%=loggedInUser.getFirst()+" "+loggedInUser.getLast() %>"></td><tr>
      <tr><td valign="top">Subject:</td><td> <input size="30" type="text" name="subj"></td><tr>
      <tr><td valign="top">Message:</td><td> <textarea cols="35" rows="22" name="mess"></textarea></td><tr>
      <tr> <td align="right" colspan=4> <input type="button" name="button1" value="Send eMail" onclick="addRecipt(this.form.sel1);"/> </td> </tr>
      </table></td>
      </form>

		</td>
	</tr> </table>
		<%
		if ( message != null ) {
		%>
    <br/>
		<font size=3 color=red><%=message%></font>
		<%
		}
		%>

