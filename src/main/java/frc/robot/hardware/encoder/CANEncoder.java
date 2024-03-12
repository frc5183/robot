package frc.robot.hardware.encoder;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.StatusSignal;
/**
 * An implementation of Encoder for the physical CANCoders
 */
public class CANEncoder extends Encoder {
    private final CANcoder encoder;
    private final StatusSignal<Double> signal;
    private final StatusSignal<Double> velocity;
    public CANEncoder(int id) {
        encoder = new CANcoder(id); signal = encoder.getPosition(); velocity = encoder.getVelocity();
    }
    public CANEncoder(int id, String canbus) {
        encoder = new CANcoder(id, canbus); signal = encoder.getPosition(); velocity = encoder.getVelocity();
    }
    @Override
    public double getUnitsRadians() {
        return (getUnitsRotations()) * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        signal.refresh(); return signal.getValue();
    }

    @Override
    public double getUnitsDegrees() {
        return getUnitsRotations()*360;
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
        encoder.setPosition(0);
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity().getValue();
    }
}
