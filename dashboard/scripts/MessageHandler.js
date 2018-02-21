"use strict";

var dashpacket = require('./DashPacket_pb');

class MessageHandler {
    constructor(addr) {
        this.addr = addr;
        this.curr_message = new dashpacket.DashPacket();
        this.callback = undefined;
        this.outgoing_message = new dashpacket.DashPacket();
        this.datasocket = undefined;
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
        if(this.indexOfKey(key) === -1) {
            return undefined;
        }
        return this.curr_message.getParametersList()[this.indexOfKey(key)].getValue();
    }

    setProperty(key, property) {
        this.outgoing_message = this.curr_message.cloneMessage();
        if(this.indexOfKey(key) === -1) {
            var tmp_array = this.outgoing_message.getParametersList();
            var property = new dashpacket.DashPacket.param();
            property.setKey(key);
            property.setValue(property);
            tmp_array.push(property);
            this.outgoing_message.setParametersList(tmp_array);
        } else {
            var tmp_array = this.outgoing_message.getParametersList();
            var property = new dashpacket.DashPacket.param();
            property.setKey(key);
            property.setValue(property);
            tmp_array[this.indexOfKey(key)] = property;
            this.outgoing_message.setParametersList(tmp_array);
        }
        this.datasocket.send(this.outgoing_message.serializeBinary());
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