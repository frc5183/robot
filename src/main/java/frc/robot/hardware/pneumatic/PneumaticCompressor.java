package frc.robot.hardware.pneumatic;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.wpilibj.PneumaticsBase;
import frc.robot.Logger;

import java.util.UUID;

/**
 * Represents a Pneumatic Air Compressor on the Robot
 */
public class PneumaticCompressor {
    private final UUID id = UUID.randomUUID();
    private final Compressor compressor;
    public PneumaticCompressor(Compressor compressor) {
        this.compressor = compressor;
        Logger.append(Logger.LogType.HardwarePneumatics, "compressor/" + this.getId(), "New PneumaticCompressor created with compressor: " + this.compressor);
    }
    public PneumaticCompressor(PneumaticsBase base) {
        compressor = base.makeCompressor();
        Logger.append(Logger.LogType.HardwarePneumatics, "compressor/" + this.getId(), "New PneumaticCompressor created with compressor: " + this.compressor);
    }

    /**
     * Enables the Compressor using a Digital Pressure switch
     */
    public void enable() {
        compressor.enableDigital();
        Logger.append(Logger.LogType.HardwarePneumatics, "compressor/" + this.getId() + "/enabled", true);
    }

    /**
     * Enables the Compressor using an Analog Pressure Switch
     * @param low minimum air (will always run below)
     * @param high maximum air (will never run above, and will not run till it reaches low again)
     */
    public void enableAnalog(double low, double high) {
        compressor.enableAnalog(low, high);
        Logger.append(Logger.LogType.HardwarePneumatics, "compressor/" + this.getId() + "/analog/low", low);
        Logger.append(Logger.LogType.HardwarePneumatics, "compressor/" + this.getId() + "/analog/high", high);
    }

    /**
     * Disables the Compressor
     */
    public void disable() {
        compressor.disable();
        Logger.append(Logger.LogType.HardwarePneumatics, "compressor/" + this.getId() + "/enabled", false);
    }

    /**
     * @return whether the compressor is enabled.
     */
    public boolean isEnabled() {
        return compressor.isEnabled();
    }

    /**
     * Returns the CompressorConfigType of the compressor
     * @return Compressor configuration data
     */
    public CompressorConfigType getMode() {
        return compressor.getConfigType();
    }

    /**
     * @return the ID of the PneumaticCompressor
     */
    public UUID getId() {
        return id;
    }
}
