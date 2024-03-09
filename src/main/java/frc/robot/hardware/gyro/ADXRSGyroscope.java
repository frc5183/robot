package frc.robot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class ADXRSGyroscope extends SingleAxisGyroscope{
    private double offset = 0;
    private final ADXRS450_Gyro gyro;

    public ADXRSGyroscope(ADXRS450_Gyro Gyro) {
        gyro = Gyro;
    }

    @Override
    public double getDegrees() {
        return getAngle();
    }

    @Override
    public double getRadians() {
        return getAngle()*Math.PI / 180;
    }

    @Override
    public double getRotations() {
        return getAngle() / 360;
    }
    @Override
    public Rotation2d getRotation2D() {
        return gyro.getRotation2d();
    }

    @Override
    public void setOffset(double offset) {
        this.offset=offset;
    }

    public void calibrate() {
        gyro.calibrate();
    }

    public void reset() {
        gyro.reset();
    }

    public double getAngle() {
        return gyro.getAngle() + offset;
    }

    // DEGREES PER SECOND
    public double getRate() {
        return gyro.getRate();
    }
}
