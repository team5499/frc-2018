package org.team5499.robots.frc2018.exceptions;

public class OutsideBoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public OutsideBoundException(String message){
        super(message);
    }

}