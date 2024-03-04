package frc.robot.control.command;

import frc.robot.control.single.SingleControl;
import frc.robot.subsystem.GenericSpinner;

public class TeleopSpinner extends Command{
    private GenericSpinner spinner;
    private SingleControl control;
    public TeleopSpinner(GenericSpinner spinner, SingleControl control) {
        this.spinner=spinner;
        this.control=control;
        require(spinner);
    }
    @Override
    public void start() {

    }

    @Override
    public void run() {
        spinner.drive(control);
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
