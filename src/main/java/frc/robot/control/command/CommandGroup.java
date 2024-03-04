package frc.robot.control.command;

import frc.robot.subsystem.Subsystem;

import java.util.ArrayList;

public class CommandGroup extends Command{
    private ArrayList<Command> commands = new ArrayList<>();
    public CommandGroup(Command... commands) {
        for (Command c : commands) {
            this.commands.add(c);
            for (Subsystem s : c.subsystemList) {
                require(s);
            }
        }
    }
    @Override
    public void start() {
        for (Command c : commands) {
            c.start();
        }
    }

    @Override
    public void run() {
        for (Command c : commands) {
            if (!c.isFinished()) {
                c.run();
            }
        }
    }

    @Override
    public void clean() {
        for (Command c : commands) {
            c.clean();
        }
    }

    @Override
    public boolean isFinished() {
        return commands.stream().allMatch(Command::isFinished);
    }
}
