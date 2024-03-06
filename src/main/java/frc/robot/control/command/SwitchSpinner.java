package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.subsystem.GenericSpinner;

public class SwitchSpinner extends Command{
    private static Boolean last;
    private Timer timer = new Timer();
    private GenericSpinner spinner;
    private boolean complete = false;
    private boolean reversed = false;
    private boolean r;
    private double time;
    private double speed;
    private AutonomousSingleControl control = new AutonomousSingleControl(0);
    public SwitchSpinner(GenericSpinner spinner, double time, double speed, boolean reverse) {
        this.spinner=spinner;
        require(spinner);
        this.time = time;
        this.speed = speed;
        r = reverse;
    }
    @Override
    public void start() {
        if (last == null) {
            reversed=r;
            last = r;
        } else {
            reversed = !last;
            last = !last;
        }
        timer.start();
    }

    @Override
    public void run() {
        if (timer.hasElapsed(time)) {
            complete=true;
        }
        if (reversed) {
            control.setValue(-speed);
        } else {
            control.setValue(speed);
        }

        spinner.drive(control);
    }

    @Override
    public void clean() {
        control.setValue(0);
        spinner.drive(control);
    }

    @Override
    public boolean isFinished() {
        return complete;
    }
}
