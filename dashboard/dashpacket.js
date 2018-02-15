/**
 * @fileoverview
 * @enhanceable
 * @suppress {messageConventions} JS Compiler reports an error if a variable or
 *     field starts with 'MSG_' and isn't a translatable message.
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!

goog.provide('proto.dashboard.DashPacket');
goog.provide('proto.dashboard.DashPacket.param');

goog.require('jspb.BinaryReader');
goog.require('jspb.BinaryWriter');
goog.require('jspb.Message');


/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.dashboard.DashPacket = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, proto.dashboard.DashPacket.repeatedFields_, null);
};
goog.inherits(proto.dashboard.DashPacket, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.dashboard.DashPacket.displayName = 'proto.dashboard.DashPacket';
}
/**
 * List of repeated fields within this message type.
 * @private {!Array<number>}
 * @const
 */
proto.dashboard.DashPacket.repeatedFields_ = [1];



if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto suitable for use in Soy templates.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     com.google.apps.jspb.JsClassTemplate.JS_RESERVED_WORDS.
 * @param {boolean=} opt_includeInstance Whether to include the JSPB instance
 *     for transitional soy proto support: http://goto/soy-param-migration
 * @return {!Object}
 */
proto.dashboard.DashPacket.prototype.toObject = function(opt_includeInstance) {
  return proto.dashboard.DashPacket.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.dashboard.DashPacket} msg The msg instance to transform.
 * @return {!Object}
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.dashboard.DashPacket.toObject = function(includeInstance, msg) {
  var f, obj = {
    parametersList: jspb.Message.toObjectList(msg.getParametersList(),
    proto.dashboard.DashPacket.param.toObject, includeInstance)
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.dashboard.DashPacket}
 */
proto.dashboard.DashPacket.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.dashboard.DashPacket;
  return proto.dashboard.DashPacket.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.dashboard.DashPacket} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.dashboard.DashPacket}
 */
proto.dashboard.DashPacket.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = new proto.dashboard.DashPacket.param;
      reader.readMessage(value,proto.dashboard.DashPacket.param.deserializeBinaryFromReader);
      msg.addParameters(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.dashboard.DashPacket.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  proto.dashboard.DashPacket.serializeBinaryToWriter(this, writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.dashboard.DashPacket} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.dashboard.DashPacket.serializeBinaryToWriter = function(message, writer) {
  var f = undefined;
  f = message.getParametersList();
  if (f.length > 0) {
    writer.writeRepeatedMessage(
      1,
      f,
      proto.dashboard.DashPacket.param.serializeBinaryToWriter
    );
  }
};



/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.dashboard.DashPacket.param = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.dashboard.DashPacket.param, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.dashboard.DashPacket.param.displayName = 'proto.dashboard.DashPacket.param';
}


if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto suitable for use in Soy templates.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     com.google.apps.jspb.JsClassTemplate.JS_RESERVED_WORDS.
 * @param {boolean=} opt_includeInstance Whether to include the JSPB instance
 *     for transitional soy proto support: http://goto/soy-param-migration
 * @return {!Object}
 */
proto.dashboard.DashPacket.param.prototype.toObject = function(opt_includeInstance) {
  return proto.dashboard.DashPacket.param.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.dashboard.DashPacket.param} msg The msg instance to transform.
 * @return {!Object}
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.dashboard.DashPacket.param.toObject = function(includeInstance, msg) {
  var f, obj = {
    key: jspb.Message.getField(msg, 1),
    value: jspb.Message.getField(msg, 2)
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.dashboard.DashPacket.param}
 */
proto.dashboard.DashPacket.param.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.dashboard.DashPacket.param;
  return proto.dashboard.DashPacket.param.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.dashboard.DashPacket.param} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.dashboard.DashPacket.param}
 */
proto.dashboard.DashPacket.param.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {string} */ (reader.readString());
      msg.setKey(value);
      break;
    case 2:
      var value = /** @type {string} */ (reader.readString());
      msg.setValue(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.dashboard.DashPacket.param.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  proto.dashboard.DashPacket.param.serializeBinaryToWriter(this, writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.dashboard.DashPacket.param} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.dashboard.DashPacket.param.serializeBinaryToWriter = function(message, writer) {
  var f = undefined;
  f = /** @type {string} */ (jspb.Message.getField(message, 1));
  if (f != null) {
    writer.writeString(
      1,
      f
    );
  }
  f = /** @type {string} */ (jspb.Message.getField(message, 2));
  if (f != null) {
    writer.writeString(
      2,
      f
    );
  }
};


/**
 * optional string key = 1;
 * @return {string}
 */
proto.dashboard.DashPacket.param.prototype.getKey = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/** @param {string} value */
proto.dashboard.DashPacket.param.prototype.setKey = function(value) {
  jspb.Message.setField(this, 1, value);
};


proto.dashboard.DashPacket.param.prototype.clearKey = function() {
  jspb.Message.setField(this, 1, undefined);
};


/**
 * Returns whether this field is set.
 * @return {!boolean}
 */
proto.dashboard.DashPacket.param.prototype.hasKey = function() {
  return jspb.Message.getField(this, 1) != null;
};


/**
 * optional string value = 2;
 * @return {string}
 */
proto.dashboard.DashPacket.param.prototype.getValue = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ""));
};


/** @param {string} value */
proto.dashboard.DashPacket.param.prototype.setValue = function(value) {
  jspb.Message.setField(this, 2, value);
};


proto.dashboard.DashPacket.param.prototype.clearValue = function() {
  jspb.Message.setField(this, 2, undefined);
};


/**
 * Returns whether this field is set.
 * @return {!boolean}
 */
proto.dashboard.DashPacket.param.prototype.hasValue = function() {
  return jspb.Message.getField(this, 2) != null;
};


/**
 * repeated param parameters = 1;
 * @return {!Array.<!proto.dashboard.DashPacket.param>}
 */
proto.dashboard.DashPacket.prototype.getParametersList = function() {
  return /** @type{!Array.<!proto.dashboard.DashPacket.param>} */ (
    jspb.Message.getRepeatedWrapperField(this, proto.dashboard.DashPacket.param, 1));
};


/** @param {!Array.<!proto.dashboard.DashPacket.param>} value */
proto.dashboard.DashPacket.prototype.setParametersList = function(value) {
  jspb.Message.setRepeatedWrapperField(this, 1, value);
};


/**
 * @param {!proto.dashboard.DashPacket.param=} opt_value
 * @param {number=} opt_index
 * @return {!proto.dashboard.DashPacket.param}
 */
proto.dashboard.DashPacket.prototype.addParameters = function(opt_value, opt_index) {
  return jspb.Message.addToRepeatedWrapperField(this, 1, opt_value, proto.dashboard.DashPacket.param, opt_index);
};


proto.dashboard.DashPacket.prototype.clearParametersList = function() {
  this.setParametersList([]);
};


