package frc.robot.hardware.encoder;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.StatusSignal;
import frc.robot.Logger;

/**
 * An implementation of Encoder for TalonFX Motors
 * Accepts any raw TalonFX motor including CAN_TalonFX
 */
public class TalonFXEncoder extends Encoder {
    private final TalonFX motor;
    private final StatusSignal<Double> signal;
    public TalonFXEncoder(TalonFX tx) {
        signal = tx.getPosition();
        motor = tx;

        Logger.append(Logger.LogType.HardwareEncoder, "/talonFX/" + this.getId() + "/motor", this.motor.getDeviceID());
        Logger.append(Logger.LogType.HardwareEncoder, "/talonFX/" + this.getId() + "/signal", this.signal.getValue());
    }
    @Override
    public double getUnitsRadians() {
        signal.refresh();
        return signal.getValue() * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        signal.refresh();
        return signal.getValue();
    }

    @Override
    public double getUnitsDegrees() {
        signal.refresh();
        return signal.getValue() * 360;
    }

    @Override
    public void reset() {
        motor.setPosition(0);
        Logger.append(Logger.LogType.HardwareEncoder, "/talonFX/" + this.getId() + "/signal", this.signal.getValue());

        Logger.append(Logger.LogType.HardwareEncoder, "/talonFX/" + this.getId(), "Encoder reset.");
    }
}
