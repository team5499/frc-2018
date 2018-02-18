"use strict";

var out = document.getElementById("out");

function handle_updates(message) {
    out.innerHTML = message.getParametersList()[0].getKey();
}

window.onload = function() {
    var handler = new MessageHandler("ws://roborio-5499-frc.local:5804/dashboard/main");
    handler.connect();
    window.setInterval(function() { console.log(handler.indexOfKey("battvoltage")); }, 100);
}