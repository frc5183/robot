package frc.robot.hardware.gyro;

import frc.robot.hardware.motor.SparkMaxMotor;
import com.revrobotics.SparkMaxAbsoluteEncoder;
/**
 * An Implementation of Encoder for NEO motors
 */
public class NEOEncoder extends Encoder {
    private SparkMaxAbsoluteEncoder encoder;
    public NEOEncoder(SparkMaxMotor spark) {
        encoder = spark.getTrueRawMotor().getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
    }
    @Override
    public double getUnitsRadians() {
        return encoder.getPosition()*2*Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        return encoder.getPosition();
    }

    @Override
    public double getUnitsDegrees() {
        return encoder.getPosition()*360;
    }

    @Override
    public void reset() {

    }
}
