"use strict";

var MessageHandler = require('./MessageHandler');
var Chart = require('chart.js');

var reset = document.getElementById("reset");
var update_distance_constants = document.getElementById("update_distance_constants");
var kDistP = document.getElementById("kDistP");
var kDistI = document.getElementById("kDistI");
var kDistD = document.getElementById("kDistD");

var update_angle_constants = document.getElementById("update_angle_constants");
var kAngleP = document.getElementById("kAngleP");
var kAngleI = document.getElementById("kAngleI");
var kAngleD = document.getElementById("kAngleD");

var update_turn_constants = document.getElementById("update_turn_constants");
var kTurnP = document.getElementById("kTurnP");
var kTurnI = document.getElementById("kTurnI");
var kTurnD = document.getElementById("kTurnD");

var update_arm_constants = document.getElementById("update_arm_constants");
var kArmP = document.getElementById("kArmP");
var kArmI = document.getElementById("kArmI");
var kArmD = document.getElementById("kArmD");

var pot_value = document.getElementById("pot_value");
var arm_angle = document.getElementById("arm_angle");
var set_vertical = document.getElementById("set_vertical");
var set_horizontal = document.getElementById("set_horizontal");

var sonic_value = document.getElementById("sonic_value");
var cube_distance = document.getElementById("cube_distance");
var set_zero = document.getElementById("set_zero");
var set_one_foot = document.getElementById("set_one_foot");

var canvas = document.getElementById("error");
var graph = new Chart(canvas, {
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
            label: 'Angle Error',
            data: [],
            backgroundColor: 'rgba(99, 255, 132, 0.2)',
            borderColor: 'rgba(99, 255, 132,1)',
            borderWidth: 2,
            pointRadius: 0,
            lineTension: 0
        },
        {
            label: 'Turn Error',
            data: [],
            backgroundColor: 'rgba(132, 99, 255, 0.2)',
            borderColor: 'rgba(132, 99, 255,1)',
            borderWidth: 2,
            pointRadius: 0,
            lineTension: 0
        },
        {
            label: 'Arm Error',
            data: [],
            backgroundColor: 'rgba(255, 132, 99, 0.2)',
            borderColor: 'rgba(255, 132, 99,1)',
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
    var handler = new MessageHandler("ws://10.54.99.2:5804/dashboard/main");
    handler.connect(run);
}

function run(handler) {
    console.log("Running handler");
    handler.setProperty("automode", "tune");
    kDistP.value = handler.getProperty("kDIST_P");
    kDistI.value = handler.getProperty("kDIST_I");
    kDistD.value = handler.getProperty("kDIST_D");

    kAngleP.value = handler.getProperty("kANGLE_P");
    kAngleI.value = handler.getProperty("kANGLE_I");
    kAngleD.value = handler.getProperty("kANGLE_D");

    kTurnP.value = handler.getProperty("kTURN_P");
    kTurnI.value = handler.getProperty("kTURN_I");
    kTurnD.value = handler.getProperty("kTURN_D");

    kArmP.value = handler.getProperty("kARM_P");
    kArmI.value = handler.getProperty("kARM_I");
    kArmD.value = handler.getProperty("kARM_D");

    handler.addKeyListener("kDIST_P", function() {
        kDistP.value = handler.getProperty("kDIST_P");
    });
    handler.addKeyListener("kDIST_I", function() {
        kDistI.value = handler.getProperty("kDIST_I");
    });
    handler.addKeyListener("kDIST_D", function() {
        kDistD.value = handler.getProperty("kDIST_D");
    });

    handler.addKeyListener("kANGLE_P", function() {
        kAngleP.value = handler.getProperty("kANGLE_P");
    });
    handler.addKeyListener("kANGLE_I", function() {
        kAngleI.value = handler.getProperty("kANGLE_I");
    });
    handler.addKeyListener("kANGLE_D", function() {
        kAngleD.value = handler.getProperty("kANGLE_D");
    });

    handler.addKeyListener("kTURN_P", function() {
        kTurnP.value = handler.getProperty("kTURN_P");
    });
    handler.addKeyListener("kTURN_I", function() {
        kTurnI.value = handler.getProperty("kTURN_I");
    });
    handler.addKeyListener("kTURN_D", function() {
        kTurnD.value = handler.getProperty("kTURN_D");
    });

    handler.addKeyListener("kARM_P", function() {
        kArmP.value = handler.getProperty("kARM_P");
    });
    handler.addKeyListener("kARM_I", function() {
        kArmI.value = handler.getProperty("kARM_I");
    });
    handler.addKeyListener("kARM_D", function() {
        kArmD.value = handler.getProperty("kARM_D");
    });

    handler.addKeyListener("distance_error", function() {
        graph.data.datasets[0].data.push(parseFloat(handler.getProperty("distance_error")));
        graph.data.labels.push(parseFloat(handler.getProperty("current_time")));
        
        graph.update();
    });

    handler.addKeyListener("angle_error", function() {
        graph.data.datasets[1].data.push(parseFloat(handler.getProperty("angle_error")));
        graph.data.labels.push(parseFloat(handler.getProperty("current_time")));
        
        graph.update();
    });

    handler.addKeyListener("turn_error", function() {
        graph.data.datasets[2].data.push(parseFloat(handler.getProperty("turn_error")));
        graph.data.labels.push(parseFloat(handler.getProperty("current_time")));
        
        graph.update();
    });

    handler.addKeyListener("arm_error", function() {
        graph.data.datasets[3].data.push(parseFloat(handler.getProperty("arm_error")));
        graph.data.labels.push(parseFloat(handler.getProperty("current_time")));
        
        graph.update();
    });

    handler.addKeyListener("pot_raw_value", function() {
        pot_value.innerHTML = "Pot Value:" + handler.getProperty("pot_raw_value");
    });

    handler.addKeyListener("arm_angle", function() {
        arm_angle.innerHTML = "Arm Angle:" + handler.getProperty("arm_angle");
    });

    handler.addKeyListener("sonic_raw_value", function() {
        sonic_value.innerHTML = "Sonic Value:" + handler.getProperty("sonic_raw_value");
    });

    handler.addKeyListener("cube_distance", function() {
        cube_distance.innerHTML = "Cube Distance:" + handler.getProperty("cube_distance");
    });

    reset.addEventListener("click", function() {
        for(var i = 0;i < graph.data.datasets.length;i++) {
            graph.data.datasets[i].data = [];
        }
        graph.data.labels = [];

        graph.update();
    }, false);

    update_distance_constants.addEventListener("click", function() {
        handler.setProperty("kDIST_P", kDistP.value);
        handler.setProperty("kDIST_I", kDistI.value);
        handler.setProperty("kDIST_D", kDistD.value);
    }, false);

    update_angle_constants.addEventListener("click", function() {
        handler.setProperty("kANGLE_P", kAngleP.value);
        handler.setProperty("kANGLE_I", kAngleI.value);
        handler.setProperty("kANGLE_D", kAngleD.value);
    }, false);

    update_turn_constants.addEventListener("click", function() {
        handler.setProperty("kTURN_P", kTurnP.value);
        handler.setProperty("kTURN_I", kTurnI.value);
        handler.setProperty("kTURN_D", kTurnD.value);
    }, false);

    update_arm_constants.addEventListener("click", function() {
        handler.setProperty("kARM_P", kArmP.value);
        handler.setProperty("kARM_I", kArmI.value);
        handler.setProperty("kARM_D", kArmD.value);
    }, false);

    set_horizontal.addEventListener("click", function() {
        handler.setProperty("ARM_PARALLEL_SIGNAL", handler.getProperty("pot_raw_value"));
    }, false);

    set_vertical.addEventListener("click", function() {
        handler.setProperty("ARM_PERPENDICULAR_SIGNAL", handler.getProperty("pot_raw_value"));
    }, false);

    set_zero.addEventListener("click", function() {
        handler.setProperty("CUBE_FULLY_INSERTED_RAW_VALUE", handler.getProperty("sonic_raw_value"));
    }, false);

    set_one_foot.addEventListener("click", function() {
        handler.setProperty("CUBE_ONE_FOOT_RAW_VALUE", handler.getProperty("sonic_raw_value"));
    }, false);
}