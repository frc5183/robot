package frc.robot.hardware.pneumatic;

import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.Solenoid;
/**
 * Represents a physical Single Solenoid.
 * Named SingleSolenoid to avoid name conflicts with WPILib
 * The difference between a Single Solenoid and a Double Solenoid is that a Double Solenoid holds its state when power is lost, while a Single Solenoid reverts to a default state.
 * NEITHER SOLENOID WILL PREVENT ALL AIRFLOW
 */
public class SingleSolenoid {
    private final Solenoid solenoid;

    public SingleSolenoid(Solenoid s) {
        solenoid = s;
    }
    public SingleSolenoid(PneumaticsBase base, int id) {
        solenoid = base.makeSolenoid(id);
    }

    /**
     * Sets the state of the Single Solenoid
     * @param on what direction the Solenoid should flow
     */
    public void set(boolean on) {
        solenoid.set(on);
    }
    public boolean get() {
        return solenoid.get();
    }

}
