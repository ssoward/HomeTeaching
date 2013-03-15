

<html>
<head>

<title>Bundled Roles</title>

<link href="scripts/trakit.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="scripts/trakitmenu.js"></script>
<script language="JavaScript" type="text/javascript">


function addArrayBun(theSel){
  var selLength = theSel.length;
  var i;
  var getAll = "";
  for(i=selLength-1; i>=0; i--){
    var text = theSel.options[i].name;
    var value= theSel.options[i].value;
    getAll = getAll + i+"update" + "="+ value+"&";
  }
  window.location = "./editBundles.jsp?bunLength="+selLength+"&"+getAll
}


function addOption(theSel, theText, theValue){
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex){ 
  var selLength = theSel.length;
  if(selLength>0){
    theSel.options[theIndex] = null;
  }
}

function moveOptions(theSelFrom, theSelTo){
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  var i;

  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--){
    if(theSelFrom.options[i].selected){
      var testText =  theSelFrom.options[i].text;
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }

  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--){
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
}

</script>
</head>
<BODY marginwidth=0 marginheight=0 topmargin=0 leftmargin=0 valign=top width=100% >
<center>
<br>
<br>
<br>
    <table  class=tablebk border=1 cellpadding=0 cellspacing=0>
       
    <tr class=columnhd><th>All Elders</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Companion</th></tr>
    <tr><td>
    </td></tr>
    <form action="editBundles.jsp" method="post">
    <tr>
      <td>
      <select name="sel2" size="10" multiple="multiple">
      
      <option value="265">Scott</option>
      
      <option value="266">Mike</option>
      
      <option value="267">Larry</option>
      
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
      <td align="center" colspan=4>
      <input type="button" value="Update" onclick="addArrayBun(this.form.sel1);"/>

      </table><br><br>
      </form>

      </td></tr>

      
</body>
</html>


