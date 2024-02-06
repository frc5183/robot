package frc.robot.hardware.motor;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Logger;

/**
 * A wrapper class around WPI_VictorSPX to make it compatible with other motor types
 * @apiNote This class is ONLY available with Phoenix5, and will not be available with Phoenix6.
 */
public class VictorSPXMotor extends PhoenixMotor {
    final WPI_VictorSPX motor;
    public VictorSPXMotor(int id) {
        motor = new WPI_VictorSPX(id);
        super.motor = motor;

        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/id", motor.getDeviceID());

        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId(), "New VictorSPXMotor created with motor id: " + motor.getDeviceID());
    }

    public void setVoltage(double outputVolts) {
        motor.setVoltage(outputVolts);
        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/outVoltage", outputVolts);
    }

    public WPI_VictorSPX getRawMotor() {
        return motor;
    }

    public WPI_VictorSPX getRawMasterMotor() {
        return getRawMotor();
    }

    public void periodic() {
        motor.feed();
    }

    public void setSafety(boolean on) {
        motor.setSafetyEnabled(on);
        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/safety", on);
    }

    @Override
    public void disable() {
        motor.disable();
        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/disabled", true);
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/stopped", true);
    }

    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/inverted", inverted);
    }

    public void setInverted(InvertType inverted) {
        motor.setInverted(inverted);
        Logger.append(Logger.LogType.HardwareMotor, "victorSPX/" + getId() + "/inverted", inverted.toString());
    }
}
