package frc.robot.hardware.encoder;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.StatusSignal;
/**
 * An implementation of Encoder for TalonFX Motors
 * Accepts any raw TalonFX motor including CAN_TalonFX
 */
public class TalonFXEncoder extends Encoder {
    private final TalonFX motor;
    private final StatusSignal<Double> signal;
    private final StatusSignal<Double> velocity;
    public TalonFXEncoder(TalonFX tx) {
        signal = tx.getPosition();
        motor = tx;
        velocity = tx.getVelocity();
    }
    @Override
    public double getUnitsRadians() {
        signal.refresh(); return signal.getValue() * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        signal.refresh();  return signal.getValue();
    }

    @Override
    public double getUnitsDegrees() {
        signal.refresh();  return signal.getValue() * 360;
    }

    @Override
    public double getVelocityRadians() {
        return getVelocityRotations()*2*Math.PI;
    }

    @Override
    public double getVelocityRotations() {
        velocity.refresh(); return velocity.getValue();
    }

    @Override
    public double getVelocityDegrees() {
        return getVelocityRotations()*360;
    }

    @Override
    public void reset() {
        motor.setPosition(0);
    }

    @Override
    public double getVelocity() {
        return motor.getVelocity().getValue();
    }
}
