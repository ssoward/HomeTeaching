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
    function makeGetRequest(wordId, count, num) {
          ccc = count;
          http.open('get', "AjaxStats?htstatPid="+wordId+"&oper="+num);
          http.onreadystatechange = processResponse;
          http.send(null);
    }

    function processResponse() {
        if(http.readyState == 4){
            var response = http.responseText;
            document.getElementById("description"+ccc).innerHTML = response;
        }
    }
    function makeGetRequestStatsSum() {
          http.open('get', "AjaxStatsSum");
          http.onreadystatechange = processResponseStatsSum;
          http.send(null);
    }

    function processResponseStatsSum() {
        if(http.readyState == 4){
            var response = http.responseText;
            document.getElementById("statsDescription").innerHTML = response;
        }
    }
