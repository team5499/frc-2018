package org.team5499.robots.frc2018.controllers;

public class TestController extends BaseController {

    private String data;

    public TestController() {
        super();
        data = "";
    }

    @Override
    public void start() {

    }

    @Override
    public void handle() {

    }

    @Override
    public void loadGameData(String data) {
        this.data = data;
    }

}