package frc.robot.control.command;

import frc.robot.Config;
import frc.robot.subsystem.GenericMecanumDrive;

public class TeleopMecanum extends Command{
    private GenericMecanumDrive drive;
    public TeleopMecanum(GenericMecanumDrive drive) {
        this.drive=drive;
        require(drive);
    }

    @Override
    public String getName() {
        return "Teleop Mecanum Drive";
    }

    @Override
    public void start() {

    }

    @Override
    public void run() {
        drive.drive(Config.translateBot, Config.botTurn);
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
