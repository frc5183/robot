package frc.robot;

import frc.robot.control.curve.ExponentialCurve;
import frc.robot.control.enumeration.StickMode;
import frc.robot.control.single.HalfStick;
import frc.robot.control.single.SingleControl;
import frc.robot.control.tuple.CombinedTuple;
import frc.robot.control.tuple.TupleControl;

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
     * Represents the ID of the Feed Motor
     */
    public static final int feedMotor = 0;
    public static final ExponentialCurve driveCurve = new ExponentialCurve();
    static {
        driveCurve.setExaggeration(100);
    }
    public static final SingleControl botX = new HalfStick(StickMode.LEFTY, driveCurve);
    public static final SingleControl botY = new HalfStick(StickMode.LEFTX, driveCurve);
    public static final SingleControl botTurn = new HalfStick(StickMode.RIGHTX, driveCurve);
    public static final TupleControl translateBot = new CombinedTuple(botX, botY);


    /**
     * Represents the LogTypes that are enabled for the logger.
     */
    public static final Logger.LogType[] enabledLogs = {
        Logger.LogType.Control,
        Logger.LogType.HardwareEncoder,
        Logger.LogType.HardwareGyro,
        Logger.LogType.HardwareMotor,
        Logger.LogType.HardwarePneumatics,
        Logger.LogType.HardwareSensor,
        Logger.LogType.Subsystems,
        Logger.LogType.Other
    };
}
