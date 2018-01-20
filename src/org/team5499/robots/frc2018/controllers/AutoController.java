package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.commands.Routine;

public class AutoController extends BaseController{

    private Routine center, left, right;

    public AutoController() {
        super();
        left = new Routine();
        
    }

    @Override
    public void start() {
        System.out.println("Auto Controller started!");
    }

    @Override
    public void handle() {
    }


}