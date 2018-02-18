"use strict";

var dashpacket = require('./DashPacket_pb');

class MessageHandler {
    constructor(addr) {
        this.addr = addr;
        this.curr_message = undefined;
        this.callback = undefined;

        /*
        this.indexOfKey = function(key) {
            for(var i = 0;i < this.curr_message.getParametersSize(); i++) {
                if(this.curr_message.getParametersList()[i].getKey() == key) {
                    return i;
                }
            }

            return -1;
        }

        this.getValue = function(key) {

        }
        */
    }


    set currMessage(value) {
        this.curr_message = value;
    }


    m_error(event) {
        var ref = this;
        return function(event) { 
            console.log("There was a connection error!");
            console.log("The error was:");
            console.log(event.data);
            ref.datasocket.close();
        }
    }

    m_open() {
        var ref = this;
        return function(event) {
            console.log("Connection opened with this data:" + event.data);
            ref.callback();
        }
    }

    m_close(event) {
        var ref = this;
        return function(event) { 
            console.log("Connection closed with this data:" + event.data);
        }
    }

    m_message(event) {
        var ref = this;
        return function(event) { 
            var reader = new FileReader();
            reader.readAsArrayBuffer(event.data);
            reader.addEventListener("loadend", function(e)
            {
                var buffer = new Uint8Array(e.target.result);  // arraybuffer object
                ref.currMessage = dashpacket.DashPacket.deserializeBinary(buffer);
            });
            return false;
        }
    }

    indexOfKey(key) {
        for(var i = 0;i < this.curr_message.getParametersList().length; i++) {
            if(this.curr_message.getParametersList()[i].getKey() == key) {
                return i;
            }
        }

        return -1;
    }

    getProperty(key) {
        return this.curr_message.getParametersList()[this.indexOfKey(key)].getValue();
    }

    connect(callback) {
        console.log("attempting to connect");
        this.callback = callback;
        this.datasocket = new WebSocket(this.addr);
        this.datasocket.binaryType = 'blob';
        this.datasocket.onerror = this.m_error();
        //this.datasocket.onopen = this.open;
        this.datasocket.onopen = this.m_open();
        this.datasocket.onclose = this.m_close();
        this.datasocket.onmessage = this.m_message();
    }


}

var out = document.getElementById("out");

window.onload = function() {
    var handler = new MessageHandler("ws://roborio-5499-frc.local:5804/dashboard/main");
    handler.connect(function() {
        window.setInterval(function() {
            out.innerHTML = handler.getProperty("battvoltage");
        }, 100);
    });
}