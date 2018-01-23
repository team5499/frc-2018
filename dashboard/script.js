"use strict";
var echo = document.getElementById("echo");
var out = document.getElementById("out");

echo.onclick = function() {
    var exampleSocket = new WebSocket("ws://localhost:5803/dash/echo");
    exampleSocket.onopen = function (event) {
        console.log("connected!");
        exampleSocket.send("Here's some text that the server is urgently awaiting!");
        return false;
    };
    exampleSocket.onerror = function (event) {
        console.log("error!!!");
        console.log(event.data);
    };
    exampleSocket.onmessage = function (event) {
        out.innerHTML = event.data;
        return false;
    };
    exampleSocket.onclose = function (event) {
        console.log("closed!!");
    };
}