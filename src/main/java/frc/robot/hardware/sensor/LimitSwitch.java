package frc.robot.hardware.sensor;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Logger;

import java.util.UUID;

public class LimitSwitch {
    private final UUID id = UUID.randomUUID();
    private final DigitalInput limiter;
    private final boolean reversed;
    /**
     * @param id The digital IO port that the limit switch is on
     * @param reversed whether the limit switch is reversed
     */
    public LimitSwitch(int id, boolean reversed) {
        limiter= new DigitalInput(id);
        this.reversed = reversed;

        Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId() + "/channel", limiter.getChannel());
        Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId() + "/reversed", reversed);

        Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId(), "New LimitSwitch created with channel: " + limiter.getChannel() + " and reversed? " + (reversed ? "yes" : "no"));
    }

    /**
     * @param id The digital IO port that the limit switch is on
     */
    public LimitSwitch(int id) {
        limiter=new DigitalInput(id);
        this.reversed =false;

        Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId() + "/channel", limiter.getChannel());
        Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId() + "/reversed", reversed);

        Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId(), "New LimitSwitch created with channel: " + limiter.getChannel());
    }

    public boolean isClosed() {
        if (reversed) {
            Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId() + "/closed", !limiter.get());
            return !limiter.get();
        } else {
            Logger.append(Logger.LogType.HardwareSensor, "limitSwitch/" + this.getId() + "/closed", limiter.get());
            return limiter.get();
        }
    }

    public UUID getId() {
        return id;
    }
}
