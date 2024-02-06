package frc.robot.subsystem;

import java.util.UUID;

/**
 * An abstract class that all Subsystems should implement. By doing so, the Subsystem will be compatible with the Scheduler
 */
public abstract class Subsystem {
    private final UUID id = UUID.randomUUID();

    /**
     * Get the ID of the Subsystem
     * @return the ID of the Subsystem
     */
    public UUID getId() {
        return id;
    }
}
