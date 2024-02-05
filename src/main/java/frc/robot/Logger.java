package frc.robot;

import edu.wpi.first.util.datalog.*;
import edu.wpi.first.wpilibj.DataLogManager;

import java.util.Arrays;

public class Logger {
    public enum LogType {
        Control,
        HardwareEncoder,
        HardwareGyro,
        HardwareMotor,
        HardwarePneumatics,
        HardwareSensor,
        Subsystems,
        Other;

        public String getLogPath() {
            switch (this) {
                case Control:
                    return "control";
                case HardwareEncoder:
                    return "hardware/encoder";
                case HardwareGyro:
                    return "hardware/gyro";
                case HardwareMotor:
                    return "hardware/motor";
                case HardwarePneumatics:
                    return "hardware/pneumatics";
                case HardwareSensor:
                    return "hardware/sensor";
                case Subsystems:
                    return "subsystems";
                case Other:
                    return "other";
                default:
                    throw new IllegalArgumentException("Invalid LogType");
            }
        }
    }

    public static void append(LogType logType, String path, boolean value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new BooleanLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, boolean[] value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new BooleanArrayLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, double value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new DoubleLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, double[] value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new DoubleArrayLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, float value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new FloatLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, float[] value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new FloatArrayLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, int value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new IntegerLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, int[] value) {
        long[] longValues = new long[value.length];
        for (int i = 0; i < value.length; i++) {
            longValues[i] = value[i];
        }

        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new IntegerArrayLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(longValues);
        }
    }

    public static void append(LogType logType, String path, long value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new IntegerLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, long[] value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new IntegerArrayLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, String value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new StringLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, String[] value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new StringArrayLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }

    public static void append(LogType logType, String path, byte[] value) {
        if (Arrays.asList(Config.enabledLogs).contains(logType)) {
            new RawLogEntry(DataLogManager.getLog(), logType.getLogPath()).append(value);
        }
    }
}
