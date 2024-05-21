package frc.robot.hardware.pneumatic;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsBase;

/**
 * Represents a physical DoubleSolenoid.
 * Named TupleSolenoid to avoid name conflicts with WPILib
 * The difference between a Single Solenoid and a Double Solenoid is that a Double Solenoid holds its state when power is lost, while a Single Solenoid reverts to a default state.
 * NEITHER SOLENOID WILL PREVENT ALL AIRFLOW
 */
public class TupleSolenoid {
    private final DoubleSolenoid solenoid;

    public TupleSolenoid(DoubleSolenoid s) {
        solenoid = s;
    }
    public TupleSolenoid(PneumaticsBase base, int idForward, int idReverse) {
        solenoid = base.makeDoubleSolenoid(idForward, idReverse);
    }

    /**
     * Represents the Possible States a Double Solenoid can be in
     */
    public enum TupleSolenoidMode {
        FORWARD, REVERSE, OFF
    }

    /**
     * Sets the state of the TupleSolenoid
     * @param mode what TupleSolenoidMode to change the TupleSolenoid to
     */
    public void set (TupleSolenoidMode mode) {
        switch (mode) {
            case REVERSE:
                solenoid.set(DoubleSolenoid.Value.kReverse);
                break;
            case FORWARD:
                solenoid.set(DoubleSolenoid.Value.kForward);
                break;
            case OFF:
                solenoid.set(DoubleSolenoid.Value.kOff);
                break;
        }
    }
}
