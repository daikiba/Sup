var latestKnownXML = 0;
var lastCheckFinished = true;

function refreshClients() {
    setCheckUnfinished();
    var url = "userxml.htm?xml=" + latestKnownXML;
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function isCheckFinished() {
    return true === lastCheckFinished;
}

function setCheckFinished() {
    lastCheckFinished = true;
}

function setCheckUnfinished() {
    lastCheckFinished = false;
}

function addMyInfo() {
    var saveurl = "savemyinfo.htm?id=" + escape(document.getElementById('name').value) +
            "&status=" + escape(document.getElementById('status').value) +
            "&color=" + escape(document.getElementById('color').value);
    updateReq = initRequest();
    updateReq.open("GET", saveurl, true);
    //req.onreadystatechange = checkForUpdates; // will be performed when above is complete
    updateReq.send(null);
    return false;
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') !== -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    if (req.readyState === 4) {
        if (req.status === 200) {
            //alert("Loaded page, ok.");
            parseMessages(req.responseXML);
            setCheckFinished();
        }
        //else {
        //    alert("Couldn't process, status=" + req.status);
        //}
    }
    else {
        //alert("faulty state" + req.readyState);
    }
}

function parseMessages(responseXML) {
    
    // no matches returned
    if (responseXML === null) {
        return false;
    } else {

        var allClients = responseXML.getElementsByTagName("clients")[0];
        latestKnownXML = allClients.childNodes[0].childNodes[0].nodeValue;

        if (allClients.childNodes.length > 0) {
            for (loop = 1; loop < allClients.childNodes.length; loop++) {
                var clientRow = allClients.childNodes[loop];
                var clientID = clientRow.getElementsByTagName("id")[0];
                var clientName = clientRow.getElementsByTagName("name")[0];
                var clientStatus = clientRow.getElementsByTagName("status")[0];
                var clientColor = clientRow.getElementsByTagName("color")[0];
                toCreateDiv(clientID.childNodes[0].nodeValue,
                    clientName.childNodes[0].nodeValue,
                    clientStatus.childNodes[0].nodeValue,
                    clientColor.childNodes[0].nodeValue);
            }
        }
    }
}

function toCreateDiv(userID, userName, userStatus, userColor) {
    var uID = userID.toString();
    var uName = userName.toString();
    var uStatus = userStatus.toString();
    var uColor = userColor.toString();
    currentDate = new Date();
    h = currentDate.getHours();
    m = currentDate.getMinutes();
    s = currentDate.getSeconds();
        
    if (!document.getElementById(uID)) {
        halp = document.createElement('div');
        halp.setAttribute('id', uID);
        halp.setAttribute('class', 'userRow');

        colorBox = document.createElement('div');
        colorBox.setAttribute('class', 'colorBox');
        colorBox.style.backgroundColor = '#' + uColor;

        nameDiv = document.createElement('div');
        nameDiv.setAttribute('class', 'name');

        statusDiv = document.createElement('div');
        statusDiv.setAttribute('class', 'status');

        timeDiv = document.createElement('div');
        timeDiv.setAttribute('class', 'time');

        halp.appendChild(colorBox);
        halp.appendChild(nameDiv);
        halp.appendChild(statusDiv);
        halp.appendChild(timeDiv);

        halp.getElementsByClassName('name')[0].innerHTML = uName;
        halp.getElementsByClassName('status')[0].innerHTML = uStatus;

        document.getElementById('people').appendChild(halp);

    } else {
        halp = document.getElementById(uID);
        var changed = false;
        
        if (halp.getElementsByClassName('name')[0].innerHTML !== uName) {
            changed = true;
            halp.getElementsByClassName('name')[0].innerHTML = uName;
        }
        oldColor = halp.getElementsByClassName('colorBox')[0].style.backgroundColor;
        if (getIntsFromRgb(oldColor).r !== hexToRgb(uColor).r ||
                getIntsFromRgb(oldColor).g !== hexToRgb(uColor).g ||
                getIntsFromRgb(oldColor).b !== hexToRgb(uColor).b) {
            changed = true;
            halp.getElementsByClassName('colorBox')[0].style.backgroundColor = '#' + uColor;
            console.log(uName + " changed color");
        }
        
        if (halp.getElementsByClassName('status')[0].innerHTML !== uStatus) {
            changed = true;
            halp.getElementsByClassName('status')[0].innerHTML = uStatus;
            console.log(uName + " changed status");
        }
        if (changed) {
        halp.getElementsByClassName('time')[0].innerHTML =
                (h < 10 ? '0' + h + ':' : h + ':') +
                (m < 10 ? '0' + m + ':' : m + ':') +
                (s < 10 ? '0' + s : s);
        }
        //
    }
}

function hexToRgb(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}

function getIntsFromRgb(colorString) {
    var result = /^rgb\(([\d]*), ([\d]*), ([\d]*)\)$/i.exec(colorString);
    return result ? {
        r: parseInt(result[1]),
        g: parseInt(result[2]),
        b: parseInt(result[3])
    } : null;
}