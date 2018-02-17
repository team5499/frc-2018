"use strict";

var dashpacket = require('./DashPacket_pb');

class MessageHandler {
    constructor(addr) {
        this.connected = false;
        this.addr = addr;
        this.reader = new FileReader();
        this.curr_message = 0;

        this.error = function(event) {
            console.log("There was a connection error!");
            console.log("The error was:");
            console.log(event.data);
            this.datasocket.close();
            this.connected = false;
            return;
        }
    
        this.open = function(event) {
            console.log("Connection opened with this data:" + event.data);
            isConnected(true);
        }
    
        this.close = function(event) {
            console.log("Connection closed with this data:" + event.data);
            isConnected(false);
        }
    
        this.message = function(event) {
            // Take the received message, and get the data from it
            this.reader.readAsArrayBuffer(event.data);
            reader.addEventListener("loadend", function(e)
            {
                var buffer = new Uint8Array(e.target.result);  // arraybuffer object
                currMessage(dashpacket.DashPacket.deserializeBinary(buffer));
            });
            return false;
        }
    }

    connect() {
        console.log("trying to connect");
        if(this.datasocket != null && isConnected) {
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

    set isConnected(conn) {
        this.connected = conn;
    }

    get currMessage() {
        if(isConnected) {
            return this.curr_message;
        } else {
            return 0;
        }
    }

    set currMessage(curr) {
        this.curr_message = curr;
    }
}

var connect = document.getElementById("connect");
var out = document.getElementById("out");

connect.onclick = function(event) {
    console.log("Pressed");
    var handler = new MessageHandler("ws://roborio-5499-frc.local:5804/dashboard/main");
    handler.connect();
    console.log(handler.isConnected);
}