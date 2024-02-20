package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.subsystem.GenericSpinner;

public class RunSpinner extends Command{
    private GenericSpinner spinner;
    private AutonomousSingleControl control = new AutonomousSingleControl(0);
    private boolean isFinished=false;
    private double seconds;
    private double start;
    private boolean inverted;
    public RunSpinner(GenericSpinner spinner, boolean inverted, double seconds) {
        require(spinner);
        this.spinner = spinner;
        this.inverted = inverted;
    }
    @Override
    public void start() {
        start = Timer.getFPGATimestamp();
    }

    @Override
    public void run() {
        control.setValue(inverted ? -1 : 1);
        spinner.drive(control);
        if (Timer.getFPGATimestamp()-start > seconds) {
            isFinished = true;
        }
    }

    @Override
    public void clean() {
        control.setValue(0);
        spinner.drive(control);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
