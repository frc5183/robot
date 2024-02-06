package frc.robot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import frc.robot.Logger;

/**
 * A wrapper for a single axis of the 3 axis ADIS16448_IMU
 */
public class ADISAxisGyroscope extends SingleAxisGyroscope {
    public final ADIS16448_IMU gyro;
    public final Axis axis;

    public ADISAxisGyroscope(ADIS16448_IMU gyro, Axis axis) {
        this.gyro = gyro;
        this.axis = axis;

        Logger.append(Logger.LogType.HardwareGyro, "adis/" + getId() + "/axis", axis.toString());
        Logger.append(Logger.LogType.HardwareGyro, "adis/" + getId(), "New ADIS16448 Gyroscope created with port: " + gyro.getPort() + " and axis: " + axis.name());
    }
    @Override
    public double getDegrees() {
        switch (axis) {
            case YAW:
                return gyro.getGyroAngleZ();
            case PITCH:
                return gyro.getGyroAngleY();
            case ROLL:
                return gyro.getGyroAngleX();
            default:
                return 0;
        }
    }
    @Override
    public double getRadians() {
        return getDegrees() * Math.PI / 180;
    }

    @Override
    public double getRotations() {
        return getDegrees() / 360;
    }
    @Override
    public Rotation2d getRotation2D() {
        return Rotation2d.fromRadians(getRadians());
    }

    @Override
    public void calibrate() {
        gyro.calibrate();
        Logger.append(Logger.LogType.HardwareGyro, "adis/" + getId(), "Gyroscope calibrated");
    }

    @Override
    public void reset() {
        gyro.reset();
        Logger.append(Logger.LogType.HardwareGyro, "adis/" + getId(), "Gyroscope reset");
    }
}
