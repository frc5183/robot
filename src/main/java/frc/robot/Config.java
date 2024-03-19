package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.control.AutonomousButtonMapper;
import frc.robot.control.command.*;
import frc.robot.control.enumeration.Button;
import frc.robot.control.enumeration.StickMode;
import frc.robot.control.single.HalfStick;
import frc.robot.control.single.SingleControl;
import frc.robot.control.tuple.CombinedTuple;
import frc.robot.control.tuple.JoystickTuple;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.VictorSPXMotor;
import frc.robot.math.MecanumDriveOdometryWrapper;
import frc.robot.math.curve.*;
import frc.robot.subsystem.GenericMecanumDrive;
import frc.robot.subsystem.GenericSpinner;
import frc.robot.subsystem.Subsystem;

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
    public static final double GearboxRatio = 12.75;


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
    public static final GenericMecanumDrive.MecanumMode mecanumMode = GenericMecanumDrive.MecanumMode.POLAR;
    public static final double maxAutonDriveSpeed = 0.42;
    public static final MecanumDriveOdometryWrapper.MecanumWheelPositions mecanumWheels = new MecanumDriveOdometryWrapper.MecanumWheelPositions(
            new Translation2d(10.125, 9),
            new Translation2d(-10.125, 9),
            new Translation2d(10.125, -9),
            new Translation2d(-10.125, -9)
    );

    /**
     * Max velocity in meters per second.
     */
    public static final double maxVelocity = 3.37;
    /**
     * Radians per second
     */
    public static final double maxRotationalVelocity = 8.69;
    public static final ExponentialCurve dCurve = new ExponentialCurve();
    public static final LinearCurve dLinear = new LinearCurve().setIntercept(0).setSlope(1);
    public static final double maxDriveSpeed = 1.0;
    public static final Curve driveCurve = new LimitedCurve(dCurve, maxDriveSpeed);

    static {
        dCurve.setExaggeration(50);
    }

    public static final double driveBaseRadius = Math.sqrt(Math.pow(Config.mecanumWheels.frontLeft.getY(), 2) + Math.pow(Config.mecanumWheels.frontLeft.getX(), 2));

    public static final Curve elevatorCurve = new TimedCurve(.2, .4);

    public static final SingleControl botX = new HalfStick(StickMode.LEFTY, dLinear).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botY = new HalfStick(StickMode.LEFTX, dLinear).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botTurn = new HalfStick(StickMode.RIGHTX, driveCurve).setXboxController(controllerManager.getFirstController());
    //public static final TupleControl translateBot = new CombinedTuple(botX, botY).setXboxController(controllerManager.getFirstController());
    public static final TupleControl translateBot = new JoystickTuple(new Joystick(0)).setXboxController(controllerManager.getFirstController());
    public static final SingleControl botIntake = new HalfStick(StickMode.TRIGGER, driveCurve).setXboxController(controllerManager.getSecondController());
    public static final SingleControl botElevator = new HalfStick(StickMode.HATY, elevatorCurve).setXboxController(controllerManager.getSecondController());


    public static final double revTime = 2.0;
    public static final double postRevTime = 0.4;

    public static Command shoot(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
        new RunSpinner(shooter, false, revTime + postRevTime),
        new DelayedCommand(new RunSpinner(intake, true, postRevTime), revTime, true),
                new ConsumerCommand(intake)
        );
    }
    public static Command slowShoot(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
                new RunSpinner(shooter, false, revTime/2 + postRevTime),
                new DelayedCommand(new RunSpinner(intake, true, postRevTime), revTime, true),
                new ConsumerCommand(intake)
        );
    }
    public static final double highIntakeTime = 1.0;
    public static Command highIntake(GenericSpinner shooter, GenericSpinner intake) {
        return new CommandGroup(
                new RunSpinner(intake, false, highIntakeTime),
                new RunSpinner(shooter, true, highIntakeTime)
        );
    }
    public static final double FlipTime = 0.4;
    public static final double FlipSpeed = 0.7;
    public static final double FlipSpacing = 0.2;
    public static Command flipIntake(GenericSpinner floor) {
        return new SwitchSpinner(floor, FlipTime, FlipSpeed, true);
    }
    public static Command cancelShoot(GenericSpinner intake, GenericSpinner shooter) {
        Robot.scheduler.runClear((Subsystem s) ->(s.getClass() == GenericSpinner.class && ((GenericSpinner) s).getMotor().getClass() == VictorSPXMotor.class));
        return new CommandGroup(
                new ConsumerCommand(intake),
                new ConsumerCommand(shooter)
        );
    }
    public static final double LowIntakeTime = 1.5;
    public static Command lowIntake(GenericSpinner intake) {
        return new RunSpinner(intake, false, LowIntakeTime);
    }
    public static Command lowOuttake(GenericSpinner intake) { return new RunSpinner(intake, true, LowIntakeTime);}
    public static Command resetHeading(SingleAxisGyroscope gyro) {
        return new HeadingReset(gyro);
    }

    public static AutonomousButtonMapper shootButton(GenericSpinner shooter, GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> shoot(shooter, intake), controllerManager.getSecondController(), Button.A, 2.0);
    }

    public static AutonomousButtonMapper slowShootButton(GenericSpinner shooter, GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> slowShoot(shooter, intake), controllerManager.getSecondController(), Button.X, 2.0);
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
    public static AutonomousButtonMapper lowOuttakeButton(GenericSpinner intake) {
        return new AutonomousButtonMapper(() -> lowOuttake(intake), controllerManager.getSecondController(), Button.SELECT, FlipSpacing);
    }
    public static AutonomousButtonMapper cancelShootButton(GenericSpinner intake, GenericSpinner shooter) {
        return new AutonomousButtonMapper(() -> cancelShoot(intake, shooter), controllerManager.getSecondController(), Button.X, 2.0);
    }
    public static AutonomousButtonMapper resetHeadingButton(SingleAxisGyroscope gyro) {
        return new AutonomousButtonMapper(() -> resetHeading(gyro), controllerManager.getFirstController(), Button.A, 2.0);
    }
}
