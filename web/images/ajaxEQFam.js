    function createRequestObject() {
        var tmpXmlHttpObject;
        if (window.XMLHttpRequest) {
            tmpXmlHttpObject = new XMLHttpRequest();

        } else if (window.ActiveXObject) {
            tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return tmpXmlHttpObject;
    }

    var http = createRequestObject();
    var ccc = '';
    function makeGetRequest(famPid, count, num) {
          ccc = count;
          http.open('get', "AjaxEQFam?famPid="+famPid+"&oper="+num);
          http.onreadystatechange = processResponse;
          http.send(null);
    }

    function processResponse() {
        if(http.readyState == 4){
            var response = http.responseText;
            document.getElementById("description"+ccc).innerHTML = response;
        }
    }
