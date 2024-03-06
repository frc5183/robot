package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.subsystem.GenericSpinner;

public class RunSpinner extends Command{
    private GenericSpinner spinner;
    private AutonomousSingleControl control = new AutonomousSingleControl(0);
    private Timer timer = new Timer();
    private boolean isFinished=false;
    private double seconds;
    private boolean inverted;
    public RunSpinner(GenericSpinner spinner, boolean inverted, double seconds) {
        require(spinner);
        this.spinner = spinner;
        this.inverted = inverted;
        this.seconds=seconds;
    }
    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void run() {
        control.setValue(inverted ? -1 : 1);
        spinner.drive(control);
        if (timer.hasElapsed(seconds)) {
            control.setValue(0);
            spinner.drive(control);
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
