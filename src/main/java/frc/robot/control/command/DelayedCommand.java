package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class DelayedCommand extends Command{
    private Command command;
    private double delay;
    private boolean finished;
    private boolean interrupt;
    private Timer timer = new Timer();
    public DelayedCommand(Command command, double delay, boolean interrupt) {
        this.command = command;
        this.delay = delay;
        this.interrupt=interrupt;
    }

    @Override
    public String getName() {
        return "Delayed Command " + command.getName();
    }

    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void run() {
        if (timer.hasElapsed(delay)) {
            if (interrupt) {
                Robot.scheduler.interrupt(command);
            } else {
                Robot.scheduler.scheduleCommand(command);
            }
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
