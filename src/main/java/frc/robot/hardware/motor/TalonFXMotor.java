package frc.robot.hardware.motor;
import com.ctre.phoenix6.hardware.TalonFX;

/**
 * A wrapper class around WPI_TalonFX to make it compatible with other motor types
 */
public class TalonFXMotor extends PhoenixMotor {
    private final TalonFX talonMotor;
    public TalonFXMotor(int id) {
        talonMotor = new TalonFX(id);
        super.motor = talonMotor;
    }

    public void set(double speed) {
        talonMotor.set(speed);
    }
    @Override
    public void setVoltage(double outputVolts) {
        talonMotor.setVoltage(outputVolts);
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
    }
    public void setInverted(boolean inverted) {
        talonMotor.setInverted(inverted);
    }
    public boolean getInverted() {
        return talonMotor.getInverted();
    }

    @Override
    public void disable() {
        talonMotor.disable();
    }
    @Override
    public void stopMotor() {
        talonMotor.stopMotor();
    }
}
