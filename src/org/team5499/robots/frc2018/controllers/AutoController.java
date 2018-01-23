package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.commands.Routine;
import org.team5499.robots.frc2018.commands.pid.*;

public class AutoController extends BaseController {

    private Routine center, left, right, test;
    private Routine currentRoutine;

    private String data = "";

    public AutoController() {
        super();
        test = new Routine();
        test.addCommand(new DriveCommand(10, 10));
    }

    @Override
    public void start() {
        System.out.println("Auto Controller started!");
        currentRoutine.start();
    }

    @Override
    public void handle() {
        currentRoutine.handle();
    }

    public void loadGameData(String data) { 
        this.data = data;
        System.out.println("Data loaded: " + data);
    }

}