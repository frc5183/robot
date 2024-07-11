package frc.robot.control.command;

import frc.robot.Config;
import frc.robot.subsystem.GenericTankDrive;

public class TeleopArcade extends Command {
    private GenericTankDrive drive;
    public TeleopArcade(GenericTankDrive drive) {
        this.drive=drive;
        require(drive);
    }

    @Override
    public String getName() {
        return "Teleop Arcade Drive";
    }

    @Override
    public void start() {

    }

    @Override
    public void run() {
        drive.arcadeDrive(Config.translateBot, false);
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
