package frc.robot;

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
