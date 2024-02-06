package frc.robot.hardware.gyro;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.robot.Logger;

public class ADXRSGyroscope extends SingleAxisGyroscope{
    private final ADXRS450_Gyro gyro;

    public ADXRSGyroscope(ADXRS450_Gyro Gyro) {
        gyro = Gyro;

        Logger.append(Logger.LogType.HardwareGyro, "adxrs/" + getId() + "/port", gyro.getPort());

        Logger.append(Logger.LogType.HardwareGyro, "adxrs/" + getId(), "New ADXRS450 Gyroscope created with port: " + gyro.getPort());
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

    public void calibrate() {
        gyro.calibrate();
        Logger.append(Logger.LogType.HardwareGyro, "adxrs/" + getId(), "Gyroscope calibrated");
    }

    public void reset() {
        gyro.reset();
        Logger.append(Logger.LogType.HardwareGyro, "adxrs/" + getId(), "Gyroscope reset");
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    // DEGREES PER SECOND
    public double getRate() {
        return gyro.getRate();
    }
}
