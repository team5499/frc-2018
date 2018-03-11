"use strict";

var MessageHandler = require('./MessageHandler');
var Chart = require('chart.js');

var autoselector = document.getElementById("autoselector");
var dropselector = document.getElementById("dropselector");
var two_cube = document.getElementById("two_cube");



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
    var handler = new MessageHandler("ws://roborio-5499-frc.local:5804/dashboard/main");
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



    handler.addKeyListener("automode", function() {
        autoselector.value = handler.getProperty("automode");
    });
    handler.addKeyListener("dropmode", function() {
        dropselector.value = handler.getProperty("dropmode");
    });
    handler.addKeyListener("cubemode", function() {
        dropselector.value = handler.getProperty("cubemode");
    });


    autoselector.addEventListener("change", function() {
        console.log("setting automode:" + autoselector.value);
        handler.setProperty("automode", autoselector.value);
    }, false);
    dropselector.addEventListener("change", function() {
        console.log("setting dropmode:" + dropselector.value);
        handler.setProperty("dropmode", dropselector.value);
    }, false);
    two_cube.addEventListener("change", function() {
        console.log("setting cubemode:" + two_cube.value);
        handler.setProperty("cubemode", two_cube.value);
    }, false);
}