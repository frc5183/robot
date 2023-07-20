package frc.robot.control.command;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Config;
import frc.robot.Tuple2;
import frc.robot.control.Scheduler;
import frc.robot.control.command.enumeration.DriveDirection;
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

    /**
     * A command moving the bot in one direction for a certain distance (in inches)
     * @param distance Distance to move the bot (in inches)
     * @param maxSpeed Maximum speed to move the bot at
     * @param direction Direction to move the bot in (forward or backward)
     * @param driveTrain DriveTrain to move
     * @param after Command to run after this command finishes (null to run no command)
     */
    public DriveInches(double distance, double maxSpeed, DriveDirection direction, GenericDriveTrain driveTrain, Scheduler scheduler, @Nullable Command after) {
        if (direction == DriveDirection.BACKWARD) {
            this.distance = -distance;
        } else {
            this.distance = distance;
        }

        this.maxSpeed = maxSpeed;
        this.driveTrain = driveTrain;
        this.scheduler = scheduler;
        this.after = after;
    }

    @Override
    public void start() {}

    @Override
    public void run() {
        driveTrain.setSpeed(maxSpeed);
        driveTrain.arcadeDrive(new Tuple2<Double>(maxSpeed, 0D), true);

        double dist = 0;
        double rotations = dist / 2048;
        double radians = rotations * 2 * Math.PI;

        // this is stuff in robot map
        double radiansAfterGearbox = radians * GearboxRatio;
        double needed = radiansAfterGearbox * WheelDiameter;
        double neededRound = (double) ((int) (needed * 100)) / 100;

        if (neededRound >= distance) {
            driveTrain.setSpeed(0);
            finished = true;
        }
    }

    @Override
    public void clean() {
        driveTrain.setSpeed(0);
        if (after != null) scheduler.scheduleCommand(after);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
