package frc.robot.control.command;

import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.subsystem.LimitedSpooler;

public class FlipOutIntake extends Command {
    private LimitedSpooler flipIntake;
    private boolean inverted;
    private boolean isFinished=false;
    private AutonomousSingleControl control = new AutonomousSingleControl(0);
    public FlipOutIntake(LimitedSpooler flipIntake, boolean inverted) {
        require(flipIntake);
        this.flipIntake = flipIntake;
        this.inverted = inverted;
    }
    @Override
    public void start() {
    }
    @Override
    public void run() {
        control.setValue(inverted ? -1 : 1);
        flipIntake.drive(control);
        if (flipIntake.getSpeed() == 0) { // It will be if the limit switch is pressed
            isFinished = true;
        }
    }
    @Override
    public boolean isFinished() {
        return (isFinished=true);
    }
    @Override
    public void clean() {
        control.setValue(0);
        flipIntake.drive(control);
    }
}
