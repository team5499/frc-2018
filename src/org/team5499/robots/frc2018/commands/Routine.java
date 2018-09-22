package org.team5499.robots.frc2018.commands;

import java.util.ArrayList;
import java.util.List;

import java.io.*;

import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.path_pursuit.Path;

public class Routine {
    private List<BaseCommand> commands = new ArrayList<BaseCommand>();
    private int step_number;

    public Routine() {
        step_number = 0;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
    }

    /*
    public void setCommand(int index, BaseCommand command) {
        commands.set(index, command);
    }
    */

    public BaseCommand getCurrentCommand() {
        return commands.get(step_number);
    }

    public boolean advanceRoutine() {
        if(!(step_number + 1 < commands.size())) return false;
        step_number++;
        System.out.println("Advance routine: " + step_number);
        return true;
    }

    public boolean isFinished() {
        return (!(step_number + 1 < commands.size()) && commands.get(step_number).isFinished());
    }

    public void reset() {
        step_number = 0;
        commands.forEach((command) -> {command.reset();});
    }

    // DONE(i think)
    public static Routine readRoutineFromFile(String path){
        Routine r = new Routine();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine().trim();

            while(line!=null) {

                if (line.substring(0,2).equals("//") || line.equals("")){
                    line = br.readLine().trim();
                    continue;
                }
                
                String[] tempArray = line.split(",");
                String command = tempArray[0];
                double timeout = Double.parseDouble(tempArray[1]);
                BaseCommand bc = null;
                switch (command) {
                    //pid
                    case "ArmCommand":
                    {
                        boolean arg1 = Boolean.parseBoolean(tempArray[2]);
                        boolean arg2 = Boolean.parseBoolean(tempArray[3]);
                        double arg3 = Double.parseDouble(tempArray[4]);
                        bc = new ArmCommand(timeout, arg1, arg2, arg3);
                        break;
                    }
                    case "DriveCommand":
                    {
                        boolean arg1 = Boolean.parseBoolean(tempArray[2]);
                        double arg2 = Double.parseDouble(tempArray[3]);
                        bc = new DriveCommand(timeout, arg1, arg2);
                        break;
                    }                    
                    case "DriveSlowCommand":
                    {
                        boolean arg1 = Boolean.parseBoolean(tempArray[2]);
                        double arg2 = Double.parseDouble(tempArray[3]);
                        bc = new DriveSlowCommand(timeout, arg1, arg2);
                        break;
                    }
                    case "IntakeDriveCommand":
                    {
                        boolean arg1 = Boolean.parseBoolean(tempArray[2]);
                        double arg2 = Double.parseDouble(tempArray[3]);
                        double arg3 = Double.parseDouble(tempArray[4]);
                        boolean arg4 = Boolean.parseBoolean(tempArray[5]);
                        bc = new IntakeDriveCommand(timeout, arg1, arg2, arg3, arg4);
                        break;
                    }
                    case "OuttakeDriveCommand":
                    {
                        boolean arg1 = Boolean.parseBoolean(tempArray[2]);
                        double arg2 = Double.parseDouble(tempArray[3]);
                        bc = new OuttakeDriveCommand(timeout, arg1, arg2);
                        break;
                    }
                    case "TurnCommand":
                    {
                        boolean arg1 = Boolean.parseBoolean(tempArray[2]);
                        double arg2 = Double.parseDouble(tempArray[3]);
                        bc = new TurnCommand(timeout, arg1, arg2);
                        break;
                    }
                    //timed
                    case "TimedArmCommand":
                    {
                        double arg1 = Double.parseDouble(tempArray[2]);
                        bc = new TimedArmCommand(timeout, arg1);
                        break;
                    }
                    case "TimedDriveCommand":
                    {
                        double arg1 = Double.parseDouble(tempArray[2]);
                        bc = new TimedDriveCommand(timeout, arg1);
                        break;
                    }
                    case "TimedTurnCommand":
                    {
                        Double arg1 = Double.parseDouble(tempArray[2]);
                        bc = new TimedTurnCommand(timeout, arg1);
                        break;
                    }
                    //commands
                    
                    case "DrivePathCommand":
                    {
                        String arg1 = tempArray[2];
                        Path p1 = Path.readPathFromFile(arg1);
                        bc = new DrivePathCommand(timeout, p1);
                        break;
                    }
                    
                    case "IntakeCommand":
                    {
                        double arg1 = Double.parseDouble(tempArray[2]);
                        boolean arg2 = Boolean.parseBoolean(tempArray[3]);
                        bc = new IntakeCommand(timeout, arg1, arg2);
                        break;
                    }
                    /* Can't parse Lists
                    case "MultiCommand":
                    {
                        List arg1 = List.parseList(tempArray[2]);
                        bc = new MultiCommand(timeout, arg1);
                        break;
                    }
                    */
                    case "NothingCommand":
                    {
                        bc = new NothingCommand(timeout);
                        break;
                    }
                    case "OuttakeCommand":
                    {
                        double arg1 = Double.parseDouble(tempArray[2]);
                        bc = new OuttakeCommand(timeout, arg1);
                        break;
                    }
                    
                    default:
                        System.out.println("Command " + tempArray[0] + "is not a reconzied auto command. This line could not be parsed");
                        line = br.readLine().trim();
                        continue;
                }
                r.addCommand(bc);
                line = br.readLine().trim();
            }
       }
       catch(Exception e){
           e.printStackTrace();
       }
       return r;
    }
}