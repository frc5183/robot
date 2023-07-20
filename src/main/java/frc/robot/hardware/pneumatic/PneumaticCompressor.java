package frc.robot.hardware.pneumatic;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.wpilibj.PneumaticsBase;

/**
 * Represents a Pneumatic Air Compressor on the Robot
 */
public class PneumaticCompressor {
    private final Compressor compressor;
    public PneumaticCompressor(Compressor compressor) {
        this.compressor=compressor;
    }
    public PneumaticCompressor(PneumaticsBase base) {
        compressor = base.makeCompressor();
    }

    /**
     * Enables the Compressor using a Digital Pressure switch
     */
    public void enable() {compressor.enableDigital();}

    /**
     * Enables the Compressor using an Analog Pressure Switch
     * @param low minimum air (will always run below)
     * @param high maximum air (will never run above, and will not run till it reaches low again)
     */
    public void enableAnalog(double low, double high) {
        compressor.enableAnalog(low, high);
    }

    /**
     * Disables the Compressor
     */
    public void disable() {
        compressor.disable();
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
}
