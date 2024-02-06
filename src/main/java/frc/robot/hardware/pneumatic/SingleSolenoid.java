package frc.robot.hardware.pneumatic;

import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Logger;

import java.util.UUID;

/**
 * Represents a physical Single Solenoid.
 * Named SingleSolenoid to avoid name conflicts with WPILib
 * The difference between a Single Solenoid and a Double Solenoid is that a Double Solenoid holds its state when power is lost, while a Single Solenoid reverts to a default state.
 * NEITHER SOLENOID WILL PREVENT ALL AIRFLOW
 */
public class SingleSolenoid {
    private final UUID id = UUID.randomUUID();
    private final Solenoid solenoid;

    public SingleSolenoid(Solenoid s) {
        solenoid = s;
        Logger.append(Logger.LogType.HardwarePneumatics, "singleSolenoid/" + this.getId(), "New SingleSolenoid created with solenoid channel: " + this.solenoid.getChannel());
    }
    public SingleSolenoid(PneumaticsBase base, int id) {
        solenoid = base.makeSolenoid(id);
        Logger.append(Logger.LogType.HardwarePneumatics, "singleSolenoid/" + this.getId(), "New SingleSolenoid created with solenoid channel: " + this.solenoid.getChannel());
    }

    /**
     * Sets the state of the Single Solenoid
     * @param on what direction the Solenoid should flow
     */
    public void set(boolean on) {
        solenoid.set(on);
        Logger.append(Logger.LogType.HardwarePneumatics, "singleSolenoid/" + this.getId() + "/state", on);
    }

    public boolean get() {
        return solenoid.get();
    }

    /**
     * @return the ID of the SingleSolenoid
     */
    public UUID getId() {
        return id;
    }
}
