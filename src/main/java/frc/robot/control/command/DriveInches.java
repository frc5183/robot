package frc.robot.control.command;

import frc.robot.control.command.enumeration.DriveDirection;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.encoder.Encoder;
import frc.robot.subsystem.GenericDriveTrain;

import static frc.robot.Config.GearboxRatio;
import static frc.robot.Config.WheelDiameter;

/**
 * A command moving the bot in one direction for a certain distance (in inches)
 */
public class DriveInches extends Command {
    private final double distance;
    private final double maxSpeed;
    private final GenericDriveTrain driveTrain;
    private TupleControl oldControl;
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
    public DriveInches(double distance, double maxSpeed, DriveDirection direction, GenericDriveTrain driveTrain, Encoder encoder) {
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
    }

    @Override
    public void start() {
        oldControl = driveTrain.getTupleControl();
        driveTrain.setTupleControl(autonomousControl);
    }

    @Override
    public void run() {
        autonomousControl.updateValue2(maxSpeed);
        driveTrain.arcadeDrive(true);

        // Note: needs to be tested: a negative may need to be added to get it to work correctly
        double radians = encoder.getUnitsRadians();
        double radiansAfterGearbox = radians * GearboxRatio;
        double needed = radiansAfterGearbox * WheelDiameter;
        double neededRound = (double) ((int) (needed * 100)) / 100.0;

        if ((direction == DriveDirection.FORWARD && neededRound >= distance) || (direction == DriveDirection.BACKWARD && neededRound <= distance)) {
            finished = true;
        }
    }

    @Override
    public void clean() {
        driveTrain.setTupleControl(oldControl);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
