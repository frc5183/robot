package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystem.Subsystem;

import java.util.List;

public class TimedConsumerCommand extends Command{
    private double start;
    private boolean complete = false;
    private double time;
    public TimedConsumerCommand(List<Subsystem> subsystems, double time) {
        for (Subsystem s : subsystems) {
            require(s);
        }
        this.time = time;
    }
    public TimedConsumerCommand(Subsystem subsystem, double time) {
        require(subsystem);
        this.time = time;
    }
    @Override
    public void start() {
        start = Timer.getFPGATimestamp();
    }

    @Override
    public void run() {
        if (Timer.getFPGATimestamp() - start > time) {
            complete=true;
        }
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return complete;
    }
}
