package frc.robot.hardware.encoder;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.StatusSignal;
import frc.robot.Logger;

/**
 * An implementation of Encoder for the physical CANCoders
 */
public class CANEncoder extends Encoder {
    private final CANcoder encoder;
    private final StatusSignal<Double> signal;
    public CANEncoder(int id) {
        encoder = new CANcoder(id);
        signal = encoder.getPosition();

        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId() + "/devId", id);
        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId() + "/signal", this.signal.getValue());

        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId(), "New CANEncoder created with device ID: " + id);
    }
    public CANEncoder(int id, String canbus) {
        encoder = new CANcoder(id, canbus);
        signal = encoder.getPosition();

        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId() + "/devId", id);
        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId() + "/bus", canbus);
        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId() + "/signal", this.signal.getValue());

        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId(), "New CANEncoder created with device ID: " + id + " and bus: " + canbus);
    }
    @Override
    public double getUnitsRadians() {
        signal.refresh();
        return (signal.getValue()) * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        signal.refresh();
        return signal.getValue();
    }

    @Override
    public double getUnitsDegrees() {
        signal.refresh();
        return signal.getValue();
    }

    @Override
    public void reset() {
        encoder.setPosition(0);

        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId() + "/signal", this.signal.getValue());

        Logger.append(Logger.LogType.HardwareEncoder, "can/" + this.getId(), "Encoder reset.");
    }
}
