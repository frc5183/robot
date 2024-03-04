package frc.robot.hardware.motor;

import frc.robot.hardware.encoder.Encoder;

public class EncodedMotor {
    private final Motor motor;
    private final Encoder encoder;
    public EncodedMotor(Motor motor, Encoder encoder) {
        this.motor = motor;
        this.encoder = encoder;
    }
    public Motor getMotor() {
        return motor;
    }
    public Encoder getEncoder() {
        return encoder;
    }
}
