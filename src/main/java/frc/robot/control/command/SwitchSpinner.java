package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.subsystem.GenericSpinner;

public class SwitchSpinner extends Command{
    private static double lastTime;
    private static Boolean last;
    private GenericSpinner spinner;
    private boolean complete = false;
    private boolean reversed = false;
    private boolean r;
    private double time;
    private double start;
    private double speed;
    private double spacing;
    private AutonomousSingleControl control = new AutonomousSingleControl(0);
    public SwitchSpinner(GenericSpinner spinner, double time, double speed, boolean reverse, double spacing) {
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
    }

    @Override
    public void run() {
        if (Timer.getFPGATimestamp() - lastTime < spacing) {
            start=Timer.getFPGATimestamp();
            control.setValue(0);
            spinner.drive(control);
            return;
        }
        if (Timer.getFPGATimestamp() - start > time) {
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
        lastTime = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {
        return complete;
    }
}
