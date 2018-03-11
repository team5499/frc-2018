"use strict";

var MessageHandler = require('./MessageHandler');
var Chart = require('chart.js');

var reset_distance = document.getElementById("reset");
var update_constants = document.getElementById("update_constants");
var kDistP = document.getElementById("kDistP");
var kDistI = document.getElementById("kDistI");
var kDistD = document.getElementById("kDistD");
var distance_canvas = document.getElementById("distance_error");
var distance_graph = new Chart(distance_canvas, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Distance Error',
            data: [],
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255,99,132,1)',
            borderWidth: 2,
            pointRadius: 0,
            lineTension: 0
        },
        {
            label: "Setpoint",
            data: [],
            backgroundColor: 'rgba(0, 0, 0, 0)',
            borderColor: 'rgba(20,99,255,1)',
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
                    suggestedMax: 15,
                    suggestedMin: -15
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
    console.log("Running handler");
    handler.setProperty("automode", "tune");
    kDistP.value = handler.getProperty("kDIST_P");
    kDistI.value = handler.getProperty("kDIST_I");
    kDistD.value = handler.getProperty("kDIST_D");

    handler.addKeyListener("kDIST_P", function() {
        kDistP.value = handler.getProperty("kDIST_P");
    });
    handler.addKeyListener("kDIST_I", function() {
        kDistP.value = handler.getProperty("kDIST_I");
    });
    handler.addKeyListener("kDIST_D", function() {
        kDistP.value = handler.getProperty("kDIST_D");
    });
    handler.addKeyListener("distance_error", function() {
        distance_graph.data.datasets[1].data.push(parseFloat(handler.getProperty("distance_setpoint_relative")));
        distance_graph.data.datasets[0].data.push(parseFloat(handler.getProperty("distance_error")));
        distance_graph.data.labels.push(parseFloat(Dashboard.getProperty("current_time")));
        
        distance_graph.update();
    });


    reset_distance.addEventListener("click", function() {
        distance_graph.data.datasets[0].data = [];
        distance_graph.data.datasets[1].data = [];
        distance_graph.data.labels = [];

        distance_graph.update();
    }, false);
    update_constants.addEventListener("click", function() {
        handler.setProperty("kDIST_P", kDistP.value);
        handler.setProperty("kDIST_I", kDistI.value);
        handler.setProperty("kDIST_D", kDistD.value);
    }, false);
}