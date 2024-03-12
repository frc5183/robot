package frc.robot.control.command;

import frc.robot.subsystem.Subsystem;

public class Command2Wrapper extends Command {
    public edu.wpi.first.wpilibj2.command.Command command;

    public Command2Wrapper(edu.wpi.first.wpilibj2.command.Command command) {
        this.command = command;
        command.getRequirements().forEach(subsystem -> {
            if (subsystem.getClass().getGenericSuperclass() != Subsystem.class) return;
            require((Subsystem) subsystem);
        });
    }

    @Override
    public String getName() {
        return command.getName();
    }

    @Override
    public void start() {
        command.initialize();
    }

    @Override
    public void run() {
        command.execute();
    }

    @Override
    public void clean() {
        command.end(false);
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }
}
