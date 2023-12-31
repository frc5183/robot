package frc.robot.hardware.motor;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * A wrapper class around WPI_TalonFX to make it compatible with other motor types
 */
public class TalonFXMotor extends PhoenixMotor {
    private final WPI_TalonFX talonMotor;
    public TalonFXMotor(int id) {
        talonMotor = new WPI_TalonFX(id);
        super.motor = talonMotor;
    }

    public void set(double speed) {
        talonMotor.set(speed);
    }
    @Override
    public void setVoltage(double outputVolts) {
        talonMotor.setVoltage(outputVolts);
    }

    public WPI_TalonFX getRawMotor() {
        return talonMotor;
    }
    public WPI_TalonFX getRawMasterMotor() {
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
    public void setInverted(InvertType inverted) {
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
