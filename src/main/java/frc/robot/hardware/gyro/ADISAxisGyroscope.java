package frc.robot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;

/**
 * A wrapper for a single axis of the 3 axis ADIS16448_IMU
 */
public class ADISAxisGyroscope extends SingleAxisGyroscope {
    private double offset=0;
    public final ADIS16448_IMU gyro;
    public final Axis axis;

    public ADISAxisGyroscope(ADIS16448_IMU gyro, Axis axis) {
        this.gyro = gyro;
        this.axis = axis;
    }
    @Override
    public double getDegrees() {
        switch (axis) {
            case YAW:
                return -gyro.getGyroAngleZ()+offset;
            case PITCH:
                return -gyro.getGyroAngleY()+offset;
            case ROLL:
                return -gyro.getGyroAngleX()+offset;
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
    public double getVelocityRadiansPerSecond() {
        double conversion = 0.01745;
        switch (axis) {
            case YAW:
                return gyro.getGyroRateZ() * conversion;
            case PITCH:
                return gyro.getGyroRateY() * conversion;
            case ROLL:
                return gyro.getGyroRateX() * conversion;
            default:
                return 0;
        }
    }

    @Override
    public void setOffset(double offset) {
        this.offset=offset;
    }

    @Override
    public void calibrate() {
        gyro.calibrate();
    }

    @Override
    public void reset() {
        gyro.reset();
    }
}
