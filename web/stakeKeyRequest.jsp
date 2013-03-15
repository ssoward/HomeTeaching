   	<script language="JavaScript">
<!--
		var indexKeys = 0;
		function addKeyTypeNum() 
		{
			document.getElementById("addKeyTypeNum"+indexKeys).value = "1";
			document.getElementById("addedProviderDateStatusSpan"+indexKeys).style.display = "inline";
			indexKeys++;
		}
		var indexKTN = 0;
		function addKeysTypeNum() 
		{
			document.getElementById("addKeysTypeNum"+indexKTN).value = "1";
			document.getElementById("addedKeySpan"+indexKTN).style.display = "inline";
			indexKTN++;
		}
		function deleteKey(deleteIndex) 
		{
			document.getElementById("addKeysTypeNum"+deleteIndex).value = "0";
			document.getElementById("addedKeySpan"+deleteIndex).style.display = "none";
		}
// -->
</script>
<%@include file="stakeJspsetup.jsp"%>
<%@include file="stakeMenu.jsp"%> 
<%@page import="com.soward.object.StakeObjects"%>
<%@page import="com.soward.util.StakeObjectUtil"%>
<%@page import="com.soward.object.KeyRequest"%>
<%@page import="com.soward.util.KeyRequestUtil"%>
<%@page import="com.soward.object.Keys"%>
<%@page import="com.soward.util.KeysUtil"%>
<jsp:directive.page import="com.soward.object.Districts"/>
<%
  List<StakeObjects> leaders  = StakeObjectUtil.getObjsForType("leader");
  List<StakeObjects> bldsNum  = StakeObjectUtil.getObjsForType("bldsNum");
  List<StakeObjects> callings = StakeObjectUtil.getObjsForType("calling");
  List<StakeObjects> wards    = StakeObjectUtil.getObjsForType("ward");
  List<StakeObjects> aux      = StakeObjectUtil.getObjsForType("aux");
  List<StakeObjects> reqrStatus      = StakeObjectUtil.getObjsForType("reqrStatus");
  List<StakeObjects> keyTypes   = StakeObjectUtil.getObjsForType("keyType");
  String msg = null;
  String pid = request.getParameter("pid");
  String delKey = request.getParameter("delKey");
  if(delKey!=null){
      msg = KeysUtil.deleteId(delKey);
  }
  String newKeyRequest = request.getParameter("newKeyRequest");
  if(newKeyRequest!=null){
     msg = KeyRequestUtil.saveForRequest(request); 
  }
  KeyRequest kreq = new KeyRequest();
  boolean newKeyReq = false;
  Calendar cal = Calendar.getInstance();
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
  if(pid!=null){
    kreq = KeyRequestUtil.getForId(pid);
   }
  if(kreq==null||kreq.getId()<1){
  	newKeyReq = true;
	  kreq.setCreatedDate(sdf.format(cal.getTime()));
  }else{
  }
%>




<form name="keyRequestForm" method="post" action="./stakeKeyRequest.jsp">

  <tr><td colspan="8" align="center">
      <br/>
      <br/>
      <table border="1">
        <tr>
          <td>
            <table bgcolor="#98aab6" border="0" cellpadding="5" cellspacing="3">
              	<%if(msg!=null){ %>
                <td colspan="8"><font color="red" size="3"><%=msg %></font></td>
              	<%}else{ %>
                <tr><td colspan="8">&nbsp;&nbsp;&nbsp;</td></tr>
                <%} %>
              <tr>
                <td colspan="8" align="center">
                  <font size="4"> Saratoga Springs Utah North Stake</font>
              </td></tr>
              <tr><td colspan="8" align="center">
                  <font size="3">Key Tracker </font>
              </td></tr>
              <tr>
               <td colspan="4"> </td>
                <td>
                  Date Created:
                  </td><td>
                  <input readonly name="createdDate" type="text" size="10" value="<%=kreq.getCreatedDate()%>" name="todaysDate">
              </td>
                 <tr>
               <td colspan="4"> </td>
                <td>
                  Request Status:
                  </td><td>
                    <select name="reqrStatus">
                    <option></option>
                    <%for(StakeObjects gkv: reqrStatus){
                    	if(kreq.getReqrStatus().equals(gkv.getKey())){
                    %>
                    <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}else{%>                    
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
              </td>

            </tr>
              <tr> <td colspan="4">
                  <b>Requesters Info</b>
                </td>
              </tr>
               
              <tr><td colspan="4">
                  <hr>
              </td></tr>

              <tr><td>
                  Name:
                  </td><td>
                  <input type="text" value="<%=kreq.getReqrName()%>" name="reqrName">
                  </td><td>
                  Calling:
                  </td><td colspan="2">

                  <select name="reqrCalling">
                    <option></option>
                    <%for(StakeObjects gkv: callings){
                    	if(kreq.getReqrCalling().equals(gkv.getKey())){
                    %>
                    <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}else{%>
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
              </td></tr>

              <tr><td colspan="4">
                  <br/>
                  <b>Recipients Info</b>
                  <br/>
                  <hr>
              </td></tr>

              <tr><td>
                  First&nbsp;Name:
                  </td><td>
                  <input type="text" value="<%=kreq.getRecFirst()%>" name="recFirst">
                  </td><td>
                  Last&nbsp;Name:
                  </td><td>
                  <input type="text" value="<%=kreq.getRecLast()%>" name="recLast">
                  </td><td>
                  Phone:
                  </td><td>
                  <input type="text" value="<%=kreq.getRecPhone()%>" name="recPhone">
              </td></tr>

              <tr><td>
                  Auxiliary
                  </td><td>

                  <select name="recAux">
                    <option></option>
                    <%for(StakeObjects gkv: aux){
                    	if(kreq.getRecAux().equals(gkv.getKey())){
                    %>
                    <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}else{%>                    
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
                  </td><td>
                  Calling
                  </td><td colspan="2">
                  <select name="recCalling">
                    <option></option>
                    <%for(StakeObjects gkv: callings){
                    	if(kreq.getRecCalling().equals(gkv.getKey())){
                      %>
                      <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                      <%}else{%>
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
              </td></tr>

              <tr><td colspan="4">
                  <br/>
                  <b>Key Info</b>
                  <br/> 
                  <hr>
              </td></tr>


              <tr>
                <td>
                  Organization:
                  </td><td>
                  <select name="ward">
                    <option></option>
                    <%for(StakeObjects gkv: wards){
                    	if(kreq.getWard().equals(gkv.getKey())){
                       %>
                       <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                       <%}else{%>
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>

                </td>

                <td>
                  Bldg. Num:
                  </td><td>

                  <select name="bldNum">
                    <option></option>
                    <%for(StakeObjects gkv: bldsNum){
                  		if(kreq.getBldNum().equals(gkv.getKey())){
                      %>
                      <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                      <%}else{%>
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
                  </td><td>
                  Leader:
                  </td><td>

                  <select name="bishop">
                    <option></option>
                    <%for(StakeObjects gkv: leaders){
                    	if(kreq.getBishop().equals(gkv.getKey())){
                        %>
                        <option selected="selected" value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                        <%}else{%>
                    <option value="<%=gkv.getKey()%>"><%=gkv.getValue()%></option>
                    <%}}%>
                  </select>
              </td></tr>

                 
                  <input type="hidden" name="newKeyRequest" value="<%=newKeyReq %>">
                  <%if(!newKeyReq){%>
                  <input type="hidden" name="oldPid" value="<%=kreq.getId() %>">
                  <input type="hidden" name="pid" value="<%=kreq.getId() %>">
                  <tr><td>
                      Current Keys:
                  </td></tr>
                  <tr>
                  <td colspan="3" valign="top">
                  <table border="1" valign="top" cellpadding="0" cellspacing="1">
                    <tr>
                      <th>Delete</th><th>Type</th><th>Num</th>
                  </tr>
                 <%
                 ArrayList<Keys> kList = KeysUtil.getForRequestId(kreq.getId()+"");
                 for(Keys tKey: kList){%>
                 <tr><td align="center">
				             <%="<a href=\"./stakeKeyRequest.jsp?delKey="+tKey.getPid()+"&pid="+kreq.getId()+"\" onclick=\"return confirm('Delete this Item?')\"><img border=\"0\" src=\"./images/delete.gif\"></a>" %>
                     </td><td>
                     <%=tKey.getKeyType()+"</td><td> "+tKey.getKeyNum()%>
                 </td></tr>
                 <%}%>
               </table>
                 &nbsp;</td>
                 <%}%>
               </td>
             </tr>


   <tr>
   	<td colspan="2">
      <br/>
      <a href="JavaScript:addKeysTypeNum();" class="buttonText"> Click to add KeyType/KeyNum</a>
    </td>
  </tr>
  <tr>
   	<td colspan="8">
   	<script language="javascript">
    <!--
    for(var i=0; i<100; i++)
    {
      document.write("<span id='addedKeySpan"+i+"' style='display:none'>"+
      "<input type='hidden' name='addKeysTypeNum"+i+"' id='addKeysTypeNum"+i+"' value='0'>"+

      "Key Type:&nbsp;&nbsp;&nbsp;"+
      " <select name='addedKeyType"+i+"' id='addedKeyType"+i+"'>"+
      "              <option></option>                                                 "+
      "              <%for(StakeObjects gkv: keyTypes){%>                              "+
      "                <option value=\"<%=gkv.getKey()%>\"><%=gkv.getValue()%></option>"+
      "              <%}%>                                                             "+
      "            </select>                                                           "+
      "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Key Num:&nbsp;                                                      "+
      "<input type='text' name='addedKeyNum"+i+"' id='addedKeyNum"+i+"' size='20'>"+
      "<a href='JavaScript:deleteKey("+i+");' class='buttonText'>&nbsp;&nbsp;&nbsp;Delete</a><br></span>");
    }
	-->
	</script>
	<br/>
	</td>
   </tr>
<tr><td>
    <!--
  <a href="JavaScript:document.forms[0].submit()" class="buttonText">Save</a>
  -->

                  <input type="submit" value="Save">
              </td></tr>


            </table>	

        </td></tr>
      </table>
  </td></tr>
</form>



