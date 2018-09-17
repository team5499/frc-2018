package org.team5499.robots.frc2018.commands.exceptions;

public class IncompletePathException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public IncompletePathException(String message){
        super(message);
    }

}