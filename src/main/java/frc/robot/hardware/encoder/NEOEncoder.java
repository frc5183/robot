package frc.robot.hardware.encoder;

import frc.robot.Logger;
import frc.robot.hardware.motor.SparkMaxMotor;
import com.revrobotics.SparkAbsoluteEncoder;

/**
 * An Implementation of Encoder for NEO motors
 */
public class NEOEncoder extends Encoder {
    private final SparkAbsoluteEncoder encoder;
    public NEOEncoder(SparkMaxMotor spark) {
        encoder = spark.getTrueRawMotor().getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);

        Logger.append(Logger.LogType.HardwareEncoder, "neo/" + this.getId() + "/motor", spark.getTrueRawMotor().getDeviceId());

        Logger.append(Logger.LogType.HardwareEncoder, "neo/" + this.getId(), "New NEOEncoder created with motor id: " + spark.getTrueRawMotor().getDeviceId());
    }
    @Override
    public double getUnitsRadians() {
        return encoder.getPosition() * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        return encoder.getPosition();
    }

    @Override
    public double getUnitsDegrees() {
        return encoder.getPosition() * 360;
    }

    @Override
    public void reset() {}
}
