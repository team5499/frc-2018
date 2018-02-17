"use strict";

requirejs.config({
    //By default load any module IDs from js/lib
    baseUrl: 'node_modules',
    //except, if the module ID starts with "app",
    //load it from the js/app directory. paths
    //config is relative to the baseUrl, and
    //never includes a ".js" extension since
    //the paths config could be for a directory.
});

// Start the main app logic.
requirejs(['google_protobuf'],
function(protobuf) {
    
});

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