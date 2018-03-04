"use strict";

var out = document.getElementById("out");
var test = document.getElementById("test");

window.onload = function() {
    var handler = new MessageHandler("ws://roborio-5499-frc.local:5804/dashboard/main");
    handler.connect(function() {
        window.setInterval(function() {
            out.innerHTML = handler.getProperty("battvoltage");
        }, 100);
        test.onclick = function() {
            handler.setProperty("test", "Hello World!");
        }
    });
}