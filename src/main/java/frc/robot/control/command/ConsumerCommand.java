package frc.robot.control.command;

import frc.robot.subsystem.Subsystem;

import java.util.List;

public class ConsumerCommand extends Command
{
    public ConsumerCommand(List<Subsystem> subsystems) {
        for (Subsystem s : subsystems) {
            require(s);
        }
    }
    public ConsumerCommand(Subsystem subsystem) {
        require(subsystem);
    }
    @Override
    public void start() {
    }

    @Override
    public void run() {

    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
