package frc.robot.hardware.gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Rotation2d;

public class NavXGyro extends SingleAxisGyroscope {
    private double offset=0;
    private final AHRS gyro;
    private final Axis axis;

    public NavXGyro(AHRS gyro, Axis axis) {
        this.gyro = gyro;
        this.axis = axis;
    }

    @Override
    public double getDegrees() {
        switch (axis) {
            case YAW:
                return gyro.getRawGyroZ()+offset;
            case PITCH:
                return gyro.getRawGyroY()+offset;
            case ROLL:
                return gyro.getRawGyroX()+offset;
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
    public void calibrate() {
        gyro.reset();
    }

    @Override
    public void reset() {
        gyro.reset();
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
                return gyro.getRawAccelZ() * conversion;
            case PITCH:
                return gyro.getRawAccelY() * conversion;
            case ROLL:
                return gyro.getRawAccelX() * conversion;
            default:
                return 0;
        }
    }

    @Override
    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public double getOffset() {
        return offset;
    }
}
