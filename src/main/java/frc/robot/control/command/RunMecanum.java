package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Config;
import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.subsystem.GenericMecanumDrive;

public class RunMecanum extends Command {
    private GenericMecanumDrive drive;
    private AutonomousTupleControl control = new AutonomousTupleControl(0, 0);
    private AutonomousSingleControl rotation = new AutonomousSingleControl(0);
    private boolean forwards;
    private double seconds;
    private Timer timer = new Timer();
    private boolean completed = false;

    public RunMecanum(GenericMecanumDrive drive, boolean forwards, double seconds) {
        this.drive = drive;
        this.forwards = forwards;
        this.seconds = seconds;
        require(drive);
    }

    @Override
    public String getName() {
        return "RunMecanum";
    }
    private GenericMecanumDrive.MecanumMode mode;
    @Override
    public void start() {
        mode = drive.getMode();
        drive.setMode(GenericMecanumDrive.MecanumMode.ABSOLUTE);
        timer.start();
    }

    @Override
    public void run() {
        if (timer.hasElapsed(seconds)) {
            completed = true;
            control.updateValue(0, 0);
            drive.drive(control, rotation);
            return;
        }

        control.updateValue1(forwards ? Config.maxAutonDriveSpeed : -Config.maxAutonDriveSpeed);
        drive.drive(control, rotation);
    }

    @Override
    public void clean() {
        drive.setMode(mode);
        control.updateValue(0, 0);
        drive.drive(control, rotation);
    }

    @Override
    public boolean isFinished() {
        return completed;
    }
}
