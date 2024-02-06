package frc.robot.hardware.motor;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Logger;

/**
 * A wrapper class around WPI_TalonFX to make it compatible with other motor types
 */
public class TalonFXMotor extends PhoenixMotor {
    private final TalonFX talonMotor;
    public TalonFXMotor(int id) {
        talonMotor = new TalonFX(id);
        super.motor = talonMotor;

        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/id", talonMotor.getDeviceID());

        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId(), "New TalonFXMotor created with motor id: " + talonMotor.getDeviceID());
    }

    public void set(double speed) {
        talonMotor.set(speed);
        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/speed", speed);
    }

    @Override
    public void setVoltage(double outputVolts) {
        talonMotor.setVoltage(outputVolts);
        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/outVoltage", outputVolts);
    }

    public TalonFX getRawMotor() {
        return talonMotor;
    }

    public TalonFX getRawMasterMotor() {
        return talonMotor;
    }

    public void periodic() {
        talonMotor.feed();
    }

    public double get() {
        return talonMotor.get();
    }

    public void setSafety(boolean on) {
        talonMotor.setSafetyEnabled(on);
        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/safety", on);
    }

    public void setInverted(boolean inverted) {
        talonMotor.setInverted(inverted);
        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/inverted", inverted);
    }

    public boolean getInverted() {
        return talonMotor.getInverted();
    }

    @Override
    public void disable() {
        talonMotor.disable();
        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/disabled", true);
    }

    @Override
    public void stopMotor() {
        talonMotor.stopMotor();
        Logger.append(Logger.LogType.HardwareMotor, "talonFX/" + getId() + "/stopped", true);
    }
}
