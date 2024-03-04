package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class DelayedCommand extends Command{
    private Command command;
    private double delay;
    private boolean finished;
    private double start;
    public DelayedCommand(Command command, double delay) {
        this.command = command;
        this.delay = delay;
    }
    @Override
    public void start() {
        start = Timer.getFPGATimestamp();
    }

    @Override
    public void run() {
        if (Timer.getFPGATimestamp() - start > delay) {
            Robot.scheduler.scheduleCommand(command);
            finished = true;
        }
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
