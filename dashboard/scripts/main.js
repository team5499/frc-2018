"use strict";

var MessageHandler = require('./MessageHandler');
var Chart = require('chart.js');

var autoselector = document.getElementById("autoselector");
var two_cube = document.getElementById("two_cube");
var timeout = document.getElementById("timeout");
var set_timeout = document.getElementById("set_timeout");



var batteryvoltage_canvas_context = document.getElementById("batteryvoltage").getContext('2d');
var batteryvoltage_graph = new Chart(batteryvoltage_canvas_context, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Battery Voltage',
            data: [],
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255,99,132,1)',
            borderWidth: 2,
            pointRadius: 0,
            lineTension: 0
        }]
    },
    options: {
        responsive: false,
        animation: {
            duration: 0
        },
        layout: {
            padding: {
                left: 0,
                right: 0,
                top: 0,
                bottom: 0
            }
        },
        scales: {
            xAxes: [{
                gridLines: {
                    display: false
                },
                ticks: {
                    display: false
                }
            }],
            yAxes: [{
                gridLines: {
                    display: false
                },
                ticks: {
                    suggestedMax: 14,
                    suggestedMin: 0
                }
            }]
        }
    }
});

window.onload = function() {
    var handler = new MessageHandler("ws://10.54.99.2:5804/dashboard/main");
    handler.connect(run);
}

function run(handler) {
    handler.addKeyListener("battvoltage", function() {
        var value = handler.getProperty("battvoltage");
        var time = handler.getProperty("current_time");
        batteryvoltage_graph.data.datasets[0].data.push({x: parseFloat(time), y: parseFloat(value)});
        batteryvoltage_graph.data.labels.push(parseFloat(time));
        if(batteryvoltage_graph.data.datasets[0].data.length > 125) {
            batteryvoltage_graph.data.datasets[0].data.splice(0, 1);
        }
        if(batteryvoltage_graph.data.labels.length > 125) {
            batteryvoltage_graph.data.labels.splice(0, 1);
        }
        batteryvoltage_graph.update();
    });

    autoselector.value = handler.getProperty("automode");
    two_cube.value = handler.getProperty("cubemode");


    handler.addKeyListener("automode", function() {
        autoselector.value = handler.getProperty("automode");
    });
    handler.addKeyListener("cubemode", function() {
        two_cube.value = handler.getProperty("cubemode");
    });
    handler.addKeyListener("timeout", function() {
        timeout.value = handler.getProperty("timeout");
    });


    autoselector.addEventListener("change", function() {
        console.log("setting automode:" + autoselector.value);
        handler.setProperty("automode", autoselector.value);
    }, false);
    two_cube.addEventListener("change", function() {
        console.log("setting cubemode:" + two_cube.value);
        handler.setProperty("cubemode", two_cube.value);
    }, false);
    set_timeout.addEventListener("click", function(){
        handler.setProperty("timeout", timeout.value);
    }, false);
}