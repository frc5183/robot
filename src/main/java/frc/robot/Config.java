package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.control.AutonomousButtonMapper;
import frc.robot.control.command.*;
import frc.robot.math.curve.Curve;
import frc.robot.math.curve.ExponentialCurve;
import frc.robot.math.curve.LimitedCurve;
import frc.robot.control.enumeration.Button;
import frc.robot.control.enumeration.StickMode;
import frc.robot.control.single.HalfStick;
import frc.robot.control.single.SingleControl;
import frc.robot.control.tuple.CombinedTuple;
import frc.robot.control.tuple.TupleControl;
import frc.robot.math.MecanumDriveOdometryWrapper;
import frc.robot.math.curve.TimedCurve;
import frc.robot.subsystem.GenericMecanumDrive;
import frc.robot.subsystem.GenericSpinner;

/**
 * A class made simply to hold configuration for the robot.
 * THIS CLASS WILL VARY BETWEEN BRANCHES AND CHANGES SHOULD NOT BE COMMITTED UNLESS ADDING A NEEDED CONFIG TO THE OVERALL CODEBASE
 */
public class Config {
    /**
     * Represents the ID of the first controller as represented in the Driver Station Configuration
     */
    public static final int FirstControllerID = 0;
    /**
     * Represents the ID of the first controller as represented in the Driver Station Configuration
     */
    public static final int SecondControllerID = 1;
    public static ControllerManager controllerManager = new ControllerManager();
    /**
     * Represents the diameter of the wheels in inches.
     */
    public static final double WheelDiameter = Units.inchesToMeters(6.0);

    /**
     * Represents the gearbox ratio of the drivetrain.
     */
    public static final double GearboxRatio = 12.75 * 6;


    /**
     * Represent the IDs of the main drive motors
     */
    public static final int frontLeftMotorID = 3;
    public static final int rearLeftMotorID = 2;
    public static final int frontRightMotorID = 4;
    public static final int rearRightMotorID = 1;

    /**
     * Represents the ID of the Shooter launch Motor
     */
    public static final int shooterMotor = 5;
    /**
     * Represents the ID of the Floor Motor
     */
    public static final int floorMotor = 9;
    /**
     * Represents the ID of the Intake Motor
     */
    public static final int intakeMotor = 10;
    /**
     * Represents the ID of the Elevator Motor
     */
    public static final int elevatorMotor = 7;
    /**
     * Mecanum Settings
     */
    public static final GenericMecanumDrive.MecanumMode mecanumMode = GenericMecanumDrive.MecanumMode.ABSOLUTE;
    public static final double maxAutonDriveSpeed = 0.5;
    public static final MecanumDriveOdometryWrapper.MecanumWheelPositions mecanumWheels = new MecanumDriveOdometryWrapper.MecanumWheelPositions(
            new Translation2d(10.125, 9),
            new Translation2d(-10.125, 9),
            new Translation2d(10.125, -9),
            new Translation2d(-10.125, -9)
    );
    public static final ExponentialCurve dCurve = new ExponentialCurve();
    public static final double maxDriveSpeed = 1.0;
    public static final Curve driveCurve = new LimitedCurve(dCurve, maxDriveSpeed);
    static {
        dCurve.setExaggeration(300);
    }

    public static final Curve elevatorCurve = new TimedCurve(.2, .4);

    public static final SingleControl botX = new HalfStick(StickMode.LEFTY, driveCurve).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botY = new HalfStick(StickMode.LEFTX, driveCurve).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botTurn = new HalfStick(StickMode.RIGHTX, driveCurve).setXboxController(controllerManager.getFirstController());
    public static final TupleControl translateBot = new CombinedTuple(botX, botY).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botIntake = new HalfStick(StickMode.TRIGGER, driveCurve).setXboxController(controllerManager.getSecondController());
    public static final SingleControl botElevator = new HalfStick(StickMode.HATY, elevatorCurve).setXboxController(controllerManager.getSecondController());


    public static final double revTime = 2.0;
    public static final double postRevTime = 0.4;

    public static Command shoot(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
        new RunSpinner(shooter, false, revTime + postRevTime),
        new DelayedCommand(new RunSpinner(intake, true, postRevTime), revTime, true)
        );
    }
    public static final double highIntakeTime = 0.6;
    public static Command highIntake(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
                new RunSpinner(intake, false, highIntakeTime),
                new RunSpinner(shooter, true, highIntakeTime)
        );
    }
    public static final double FlipTime = 0.3;
    public static final double FlipSpeed = 0.7;
    public static final double FlipSpacing = 1;
    public static Command flipIntake(GenericSpinner floor) {
        return new SwitchSpinner(floor, FlipTime, FlipSpeed, true);
    }
    public static final double LowIntakeTime = 1.5;
    public static Command lowIntake(GenericSpinner intake) {
        return new RunSpinner(intake, false, LowIntakeTime);
    }

    public static AutonomousButtonMapper shootButton(GenericSpinner shooter, GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> shoot(shooter, intake), controllerManager.getSecondController(), Button.A, 2.0);
    }
    public static AutonomousButtonMapper highIntakeButton(GenericSpinner shooter, GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> highIntake(shooter, intake), controllerManager.getSecondController(), Button.B, 2.0);
    }
    public static AutonomousButtonMapper flipIntakeButton(GenericSpinner floor) {
        return new AutonomousButtonMapper(() -> flipIntake(floor), controllerManager.getSecondController(), Button.RIGHTBUMPER, FlipSpacing+FlipTime);
    }

    public static AutonomousButtonMapper lowIntakeButton(GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> lowIntake(intake), controllerManager.getSecondController(), Button.LEFTBUMPER, FlipSpacing);
    }


}
