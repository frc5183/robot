package frc.robot.hardware.encoder;

import com.revrobotics.*;
import frc.robot.hardware.motor.SparkMaxMotor;

/**
 * An Implementation of Encoder for NEO motors
 */
public class NEOEncoder extends Encoder {
    private final RelativeEncoder encoder;
    private final double count = 0;
    public NEOEncoder(SparkMaxMotor spark) {
        encoder = spark.getTrueRawMotor().getEncoder();

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
    public void reset() {
    }
}
