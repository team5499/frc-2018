"use strict";

var dashpacket = require('./DashPacket_pb');

class MessageHandler {
    var connected = false;
    var address = "";
    constructor(addr) {
        this.addr = addr;
        this.reader = new FileReader();
    }

    var error = function(event) {
        console.log("There was a connection error!");
        console.log("The error was:");
        console.log(event.data);
        this.datasocket.close();
        this.connected = false;
        return;
    }

    var open = function(event) {
        console.log("Connection opened with this data:" + event.data);
        this.connected = true;
    }

    var close = function(event) {
        console.log("Connection closed with this data:" + event.data);
        this.connected = false;
    }

    var message = function(event) {
        // Take the received message, and get the data from it
        this.reader.readAsArrayBuffer(event.data);
        reader.addEventListener("loadend", function(e)
        {
            var buffer = new Uint8Array(e.target.result);  // arraybuffer object
            var message = dashpacket.DashPacket.deserializeBinary(buffer);
            console.log(message.getParametersList()[0].getKey() + ":" + message.getParametersList()[0].getValue());
        });
        return false;
    }

    connect() {
        if(this.datasocket != null && this.datasocket.connected()) {
            return;
        }
        this.datasocket = new WebSocket(this.addr);
        this.datasocket.binaryType = 'blob';
        this.datasocket.onerror = this.error;
        this.datasocket.onopen = this.open;
        this.datasocket.onclose = this.close;
        
    }

    get isConnected() {
        return this.connected;
    }
}
var connect = document.getElementById("connect");
var out = document.getElementById("out");

var connect = function() {
    var datasocket = new WebSocket("ws://roborio-5499-frc.local:5804/dashboard/main");
    datasocket.binaryType = 'blob';
    datasocket.onopen = function (event) {
        console.log("connected!");
        return false;
    };
    datasocket.onerror = function (event) {
        console.log("error!!!");
        console.log(event.data);
    };
    datasocket.onmessage = function (event) {
        var reader = new FileReader();
        reader.readAsArrayBuffer(event.data);
        reader.addEventListener("loadend", function(e)
        {
            var buffer = new Uint8Array(e.target.result);  // arraybuffer object
            var message = dashpacket.DashPacket.deserializeBinary(buffer);
            console.log(message.getParametersList()[0].getKey() + ":" + message.getParametersList()[0].getValue());
        });
        return false;
    };
    datasocket.onclose = function (event) {
        console.log("closed!!");
    };
}