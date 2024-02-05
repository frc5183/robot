package frc.robot.hardware.encoder;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.StatusSignal;
/**
 * An implementation of Encoder for the physical CANCoders
 */
public class CANEncoder extends Encoder {
    private final CANcoder encoder;
    private final StatusSignal<Double> signal;
    public CANEncoder(int id) {
        encoder = new CANcoder(id); signal = encoder.getPosition();
    }
    public CANEncoder(int id, String canbus) {
        encoder = new CANcoder(id, canbus); signal = encoder.getPosition();
    }
    @Override
    public double getUnitsRadians() {
        signal.refresh(); return (signal.getValue()) * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        signal.refresh(); return signal.getValue();
    }

    @Override
    public double getUnitsDegrees() {
        signal.refresh(); return signal.getValue();
    }

    @Override
    public void reset() {
        encoder.setPosition(0);
    }
}
