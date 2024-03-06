package frc.robot.control.command;

import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.subsystem.GenericMecanumDrive;

public class ShutUpWatchdog extends Command {
    private GenericMecanumDrive drive;
    private AutonomousTupleControl tuple = new AutonomousTupleControl(0, 0);
    private AutonomousSingleControl single = new AutonomousSingleControl(0);
    public ShutUpWatchdog(GenericMecanumDrive drive) {
        this.drive=drive;
    }

    @Override
    public String getName() {
        return "Shut Up Watchdog";
    }

    @Override
    public void start() {

    }

    @Override
    public void run() {
        drive.drive(tuple, single);
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
