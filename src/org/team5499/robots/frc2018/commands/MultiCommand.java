package org.team5499.robots.frc2018.commands;

import java.util.ArrayList;
import java.util.List;

public class MultiCommand extends BaseCommand {

    private List<BaseCommand> commands = new ArrayList<>();

    public MultiCommand(double to, double st, List<BaseCommand> commands) {
        super(to);
        this.commands = commands;
    }

    @Override
    public void start() {
        super.start();
        for(BaseCommand c : commands) {
            c.start();
        }
    }

    @Override
    public void handle() {
        for(BaseCommand c : commands) {
            if(!c.isFinished()) c.handle();
        }
    }

    @Override
    public boolean isFinished() {
        boolean finished = true;
        for(BaseCommand c : commands) {
            finished &= c.isFinished();
        }
        return (super.isFinished() || finished);
    }

    @Override
    public void reset() {
        super.reset();
        for(BaseCommand c : commands) {
            c.reset();
        }
    }

}