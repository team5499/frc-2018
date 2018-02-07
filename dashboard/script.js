"use strict";
goog.require('proto.dashboard.DashPacket');
var connect = document.getElementById("connect");
var out = document.getElementById("out");

connect.onclick = function() {
    var exampleSocket = new WebSocket("ws://roborio-5499-frc.local:5804/dashboard/main");
    exampleSocket.onopen = function (event) {
        console.log("connected!");
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