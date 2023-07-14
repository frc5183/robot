package frc.robot.control.enumeration;

/**
 * Represents an available "Stick" or "Axis" on an XBOX 360 Controller
 * "Sticks" should be represented by any input that could vary from -1 to 1
 */
public enum StickMode {
    /**
     * Represents a single axis of one of the two available joysticks on the XBOX 360 Controller
     */
    LEFTX, LEFTY, RIGHTX, RIGHTY,
    /**
     * Represents an adaptation of the DPAD/HAT's left and right buttons to become a digital axis
     */
    HATX,
    /**
     * Represents an adaptation of the DPAD/HAT's up and down buttons to become a digital axis
     */
    HATY
}
