package frc.robot.control.command;

import frc.robot.Logger;
import frc.robot.control.enumeration.DriveDirection;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.hardware.encoder.Encoder;
import frc.robot.subsystem.GenericTankDrive;

import static frc.robot.Config.GearboxRatio;
import static frc.robot.Config.WheelDiameter;

/**
 * A command moving the bot in one direction for a certain distance (in inches)
 */
public class DriveInches extends Command {
    private final double distance;
    private final double maxSpeed;
    private final GenericTankDrive driveTrain;
    private final DriveDirection direction;
    private final AutonomousTupleControl autonomousControl = new AutonomousTupleControl(0, 0);
    private final Encoder encoder;
    private boolean finished = false;

    /**
     * A command moving the bot in one direction for a certain distance (in inches)
     * @param distance Distance to move the bot (in inches)
     * @param maxSpeed Maximum speed to move the bot at
     * @param direction Direction to move the bot in (forward or backward)
     * @param driveTrain DriveTrain to move
     * @param encoder Encoder to used to measure distance
     */
    public DriveInches(double distance, double maxSpeed, DriveDirection direction, GenericTankDrive driveTrain, Encoder encoder) {
        require(driveTrain);

        if (direction == DriveDirection.BACKWARD) {
            this.distance = -distance;
            this.maxSpeed = -maxSpeed;
        } else {
            this.distance = distance;
            this.maxSpeed = maxSpeed;
        }
        this.encoder = encoder;
        this.direction = direction;
        this.driveTrain = driveTrain;

        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/distance", this.distance);
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/maxSpeed", this.maxSpeed);
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/encoder", this.encoder.getId().toString());
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/direction", this.direction.toString());
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/driveTrain", this.driveTrain.getId().toString());

        Logger.append(Logger.LogType.Control, "command/drive/inches" + this.getId(), "New DriveInches created with distance " + distance + " and max speed " + maxSpeed + " and direction " + direction.toString() + " and encoder " + encoder.getId());
    }

    @Override
    public void start() {
    }

    @Override
    public void run() {
        autonomousControl.updateValue2(maxSpeed);
        driveTrain.arcadeDrive(autonomousControl, true);

        // Note: needs to be tested: a negative may need to be added to get it to work correctly
        double radians = encoder.getUnitsRadians();
        double radiansAfterGearbox = radians * GearboxRatio;
        double needed = radiansAfterGearbox * WheelDiameter;
        double neededRound = (double) ((int) (needed * 100)) / 100.0;

        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/run/radians", radians);
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/run/radians/afterGearbox", radiansAfterGearbox);
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/run/needed", needed);
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/run/needed/rounded", neededRound);

        finished = ((direction == DriveDirection.FORWARD && neededRound >= distance) || (direction == DriveDirection.BACKWARD && neededRound <= distance));
        Logger.append(Logger.LogType.Control, "command/drive/inches/" + this.getId() + "/finished", finished);
    }

    @Override
    public void clean() {
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
