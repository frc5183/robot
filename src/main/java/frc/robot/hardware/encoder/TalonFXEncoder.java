package frc.robot.hardware.encoder;

import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
/**
 * An implementation of Encoder for TalonFX Motors
 * Accepts any raw TalonFX motor including CAN_TalonFX
 */
public class TalonFXEncoder extends Encoder {
    private final TalonFXSensorCollection encoder;
    public TalonFXEncoder(TalonFX tx) {
        encoder = tx.getSensorCollection();
    }
    @Override
    public double getUnitsRadians() {
        return (encoder.getIntegratedSensorPosition() / 1024) * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        return (encoder.getIntegratedSensorPosition() / 2048);
    }

    @Override
    public double getUnitsDegrees() {
        return (encoder.getIntegratedSensorPosition() / 2048) * 360;
    }

    @Override
    public void reset() {
        encoder.setIntegratedSensorPosition(0, 0);
    }
}
