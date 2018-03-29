"use strict";
window.onload = function() {
    var output = document.getElementById("receive");
    var ws = new WebSocket("ws://roborio-5499-frc.local:5809");
    ws.onopen = function(event) {
        console.log("Open!");
    }
    ws.onmessage = function(event) {
        var scroll = false;
        if(output.scrollHeight - output.scrollTop < 750) {
            scroll = true;
        }
        var lines = event.data.split(/\r\n|\r|\n/g);
        for(var i = 0;i < lines.length; i++) {
            output.innerHTML += lines[i];
        }
        //console.log((output.scrollTop + 600) + ":" + output.scrollHeight);
        if(scroll) {
            output.scrollTop = output.scrollHeight;
        }
    }
    ws.onerror = function(event) {
        console.log("error!");
    }
}