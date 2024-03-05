package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.control.AutonomousButtonMapper;
import frc.robot.control.command.*;
import frc.robot.control.curve.ExponentialCurve;
import frc.robot.control.enumeration.Button;
import frc.robot.control.enumeration.StickMode;
import frc.robot.control.single.HalfStick;
import frc.robot.control.single.SingleButton;
import frc.robot.control.single.SingleControl;
import frc.robot.control.single.SingleTwoPhaseButton;
import frc.robot.control.tuple.CombinedTuple;
import frc.robot.control.tuple.TupleControl;
import frc.robot.math.MecanumDriveOdometryWrapper;
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
    public static final int WheelDiameter = 0;

    /**
     * Represents the gearbox ratio of the drivetrain.
     */
    public static final int GearboxRatio = 0;


    /**
     * Represent the IDs of the main drive motors
     */
    public static final int frontLeftMotorID = 0;
    public static final int rearLeftMotorID = 0;
    public static final int frontRightMotorID = 0;
    public static final int rearRightMotorID = 0;

    /**
     * Represents the ID of the Shooter launch Motor
     */
    public static final int shooterMotor = 0;
    /**
     * Represents the ID of the Floor Motor
     */
    public static final int floorMotor = 0;
    /**
     * Represents the ID of the Intake Motor
     */
    public static final int intakeMotor = 0;
    /**
     * Represents the ID of the Elevator Motor
     */
    public static final int elevatorMotor = 0;
    /**
     * Mecanum Settings
     */
    public static final GenericMecanumDrive.MecanumMode mecanumMode = GenericMecanumDrive.MecanumMode.ABSOLUTE;
    public static final MecanumDriveOdometryWrapper.MecanumWheelPositions mecanumWheels = new MecanumDriveOdometryWrapper.MecanumWheelPositions(
            new Translation2d(10.125, 9),
            new Translation2d(-10.125, 9),
            new Translation2d(10.125, -9),
            new Translation2d(-10.125, -9)
    );
    public static final ExponentialCurve driveCurve = new ExponentialCurve();
    static {
        driveCurve.setExaggeration(100);
    }
    public static final SingleControl botX = new HalfStick(StickMode.LEFTY, driveCurve).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botY = new HalfStick(StickMode.LEFTX, driveCurve).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botTurn = new HalfStick(StickMode.RIGHTX, driveCurve).setXboxController(controllerManager.getFirstController());
    public static final TupleControl translateBot = new CombinedTuple(botX, botY).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botIntake = new HalfStick(StickMode.TRIGGER, driveCurve).setXboxController(controllerManager.getSecondController());
    public static final SingleControl botElevator = new HalfStick(StickMode.LEFTY, driveCurve).setXboxController(controllerManager.getSecondController());
    public static final SingleControl botFloor = new HalfStick(StickMode.RIGHTX, driveCurve).setXboxController(controllerManager.getSecondController());


    public static final double revTime = 2.0;
    public static final double postRevTime = 2.0;

    public static Command shoot(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
        new RunSpinner(shooter, false, revTime + postRevTime),
        new TimedConsumerCommand(intake, revTime),
        new DelayedCommand(new RunSpinner(intake, false, postRevTime), revTime)
        );
    }
    public static final double highIntakeTime = 3;
    public static Command highIntake(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
                new RunSpinner(intake, true, highIntakeTime),
                new RunSpinner(shooter, true, highIntakeTime)
        );
    }
    public static final double FlipTime = 0.4;
    public static final double FlipSpeed = 1;
    public static final double FlipSpacing = 1;
    public static Command flipIntake(GenericSpinner floor) {
        return new SwitchSpinner(floor, FlipTime, FlipSpeed, true, FlipSpacing);
    }

    public static AutonomousButtonMapper shootButton(GenericSpinner shooter, GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> shoot(shooter, intake), controllerManager.getSecondController(), Button.A);
    }
    public static AutonomousButtonMapper highIntakeButton(GenericSpinner shooter, GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> highIntake(shooter, intake), controllerManager.getSecondController(), Button.B);
    }
    public static AutonomousButtonMapper flipIntakeButton(GenericSpinner floor) {
        return new AutonomousButtonMapper(() -> flipIntake(floor), controllerManager.getSecondController(), Button.RIGHTBUMPER);
    }


}
