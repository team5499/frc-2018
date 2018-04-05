"use strict";
// distance from front to back of the robot, with bumpers
var config = {
    robot_width: 35.25,
    robot_height: 29.25,
    field_width: 888.0,
    field_height: 360.0,
    field_offset: {
        x: 120.0,
        y: 18.0
    }
}
var ui = {
    field_contailer: document.getElementById("field_container"),
    field: document.getElementById("field"),
    robot: document.getElementById("robot")
}
var pixels_per_inch = 0;
var robot_offset = {
    x: 0,
    y: 0,
    heading: 0
}
var robot_position = {
    x: 0,
    y: 0,
    heading: 0
}
function toRadians (angle) {
    return angle * (Math.PI / 180);
}
function toDegrees (angle) {
    return angle * (180 / Math.PI);
}
function setRobotOffset (x, y, heading) {
    robot_offset.x = x;
    robot_offset.y = y;
    robot_offset.heading = heading;
}
function setRobotPosition (x, y, heading) {
    robot_position.x = x;
    robot_position.y = y;
    robot_position.heading = heading;
    updateRobotSize();
    var robot_coordinates = localToGlobalCoordinates(robot_position.x, robot_position.y);
    ui.robot.style.top = ((robot_coordinates.y + robot_offset.y + config.field_offset.y - (config.robot_height / 2)) * pixels_per_inch) + "px";
    ui.robot.style.left = ((robot_coordinates.x + robot_offset.x + config.field_offset.x - (config.robot_width / 2)) * pixels_per_inch) + "px";
    console.log(toDegrees(robot_position.heading + robot_offset.heading));
    ui.robot.style.transform = "rotate(" + toDegrees(robot_position.heading + robot_offset.heading) + "deg)";
}
function localToGlobalCoordinates (x, y) {
    var new_x = (x * Math.cos(-robot_offset.heading)) + (y * Math.sin(-robot_offset.heading));
    var new_y = -(x * Math.sin(-robot_offset.heading)) + (y * Math.cos(-robot_offset.heading));
    return {
        x: new_x,
        y: new_y
    };
}
function changeRobotPosition (dX, dY, dAngle) {
    setRobotPosition(robot_position.x + dX, robot_position.y + dY, robot_position.heading + dAngle);
}
function updateRobotSize () {
    pixels_per_inch = ui.field.width / config.field_width;
    ui.robot.style.width = (pixels_per_inch * config.robot_width) + "px";
}
window.onload = function() {
    setRobotOffset(0, 0, 1);
    setRobotPosition(0, 0, 0);
}
window.onresize = function() {
    setRobotPosition(robot_position.x, robot_position.y, robot_position.heading);
}