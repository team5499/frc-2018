"use strict";

var dashpacket = require('./DashPacket_pb');

module.exports = class MessageHandler {
    constructor(addr) {
        this.addr = addr;
        this.curr_message = new dashpacket.DashPacket();
        this.callback = undefined;
        this.outgoing_message = new dashpacket.DashPacket();
        this.datasocket = undefined;
        this.keylisteners = [];
    }


    setNewMessage(message) {
        this.last_message = this.curr_message.cloneMessage();
        this.curr_message = message;
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
            ref.callback(ref);
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
                var new_message = dashpacket.DashPacket.deserializeBinary(buffer);
                ref.setNewMessage(new_message);
                ref.updateKeyListeners(new_message);
            });
            return false;
        }
    }

    indexOfKey(key, packet) {
        for(var i = 0;i < packet.getParametersList().length;i++) {
            if(packet.getParametersList()[i].getKey() == key) {
                return i;
            }
        }

        return -1;
    }

    getProperty(/*key, packet*/) {
        var packet = undefined;
        var key = arguments[0];
        if(arguments.length > 1) {
            packet = arguments[1];
        } else {
            packet = this.curr_message;
        }
        if(this.indexOfKey(key, packet) === -1) {
            return undefined;
        }
        return packet.getParametersList()[this.indexOfKey(key, packet)].getValue();
    }

    setProperty(key, value) {
        this.outgoing_message = this.curr_message.cloneMessage();
        if(this.indexOfKey(key, this.curr_message) === -1) {
            var tmp_array = this.outgoing_message.getParametersList();
            var property = new dashpacket.DashPacket.param();
            property.setKey(key);
            property.setValue(value);
            tmp_array.push(property);
            this.outgoing_message.setParametersList(tmp_array);
        } else {
            var tmp_array = this.outgoing_message.getParametersList();
            var property = new dashpacket.DashPacket.param();
            property.setKey(key);
            property.setValue(value);
            tmp_array[this.indexOfKey(key, this.curr_message)] = property;
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
        this.datasocket.onopen = this.m_open();
        this.datasocket.onclose = this.m_close();
        this.datasocket.onmessage = this.m_message();
    }

    addKeyListener(key, callback) {
        this.keylisteners.push([key, callback]);
    }

    updateKeyListeners(new_packet) {
        for(var i = 0;i < this.keylisteners.length;i++) {
            if(this.getProperty(this.keylisteners[i][0], this.last_message) === this.getProperty(this.keylisteners[i][0], new_packet)) {
            } else {
                this.keylisteners[i][1]();
            }
        }
    }


}