package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Config;
import frc.robot.Tuple2;
import frc.robot.control.Scheduler;
import frc.robot.control.command.enumeration.DriveDirection;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.encoder.Encoder;
import frc.robot.subsystem.GenericDriveTrain;
import org.jetbrains.annotations.Nullable;

import static frc.robot.Config.GearboxRatio;
import static frc.robot.Config.WheelDiameter;

/**
 * A command moving the bot in one direction for a certain distance (in inches)
 */
public class DriveInches extends Command {
    private final double distance;
    private final double maxSpeed;
    private final GenericDriveTrain driveTrain;

    private final Scheduler scheduler;
    private final @Nullable Command after;
    private boolean finished = false;
    private TupleControl oldControl;
    private final DriveDirection direction;
    private AutonomousTupleControl autonomousControl = new AutonomousTupleControl(0, 0);
    private Encoder encoder;

    /**
     * A command moving the bot in one direction for a certain distance (in inches)
     * @param distance Distance to move the bot (in inches)
     * @param maxSpeed Maximum speed to move the bot at
     * @param direction Direction to move the bot in (forward or backward)
     * @param driveTrain DriveTrain to move
     * @param after Command to run after this command finishes (null to run no command)
     */
    public DriveInches(double distance, double maxSpeed, DriveDirection direction, GenericDriveTrain driveTrain, Scheduler scheduler, Encoder encoder, @Nullable Command after) {
        if (direction == DriveDirection.BACKWARD) {
            this.distance = -distance;
        } else {
            this.distance = distance;
        }
        this.encoder=encoder;
        this.direction=direction;
        this.maxSpeed = maxSpeed;
        this.driveTrain = driveTrain;
        this.scheduler = scheduler;
        this.after = after;
    }

    @Override
    public void start() {
        oldControl = driveTrain.getTupleControl();
        driveTrain.setTupleControl(autonomousControl);
    }

    @Override
    public void run() {
        autonomousControl.updateValue2((direction==DriveDirection.FORWARD) ? maxSpeed : -maxSpeed);
        driveTrain.arcadeDrive(true);

        // Note: needs to be tested: a negative may need to be added to get it to work correctly
        double radians = encoder.getUnitsRadians();
        // this is stuff in robot map
        double radiansAfterGearbox = radians * GearboxRatio;
        double needed = radiansAfterGearbox * WheelDiameter;
        double neededRound = (double) ((int) (needed * 100)) / 100.0;

        if ((direction==DriveDirection.FORWARD && neededRound >= distance) || (direction==DriveDirection.BACKWARD && neededRound <= distance)) {
            finished = true;
        }
    }

    @Override
    public void clean() {
        driveTrain.setSpeed(0);
        driveTrain.setTupleControl(oldControl);
        if (after != null) scheduler.scheduleCommand(after);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
