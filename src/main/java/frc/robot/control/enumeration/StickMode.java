package frc.robot.control.enumeration;

import edu.wpi.first.wpilibj.XboxController;

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
    HATY,
    /**
     * Represents a combination of the two triggers on the XBOX 360 Controller
     */
    TRIGGER;
    /**
     * Used to get the value of a StickMode using an XboxController
     * @param mode the StickMode to be evaluated
     * @param xbox the XboxController to retrieve the value from
     * @return the value of the StickMode
     */
    public static double getStickValue(StickMode mode, XboxController xbox) {
        switch(mode) {
            case LEFTX:
                return xbox.getLeftX();
            case LEFTY:
                return xbox.getLeftY();
            case RIGHTX:
                return xbox.getRightX();
            case RIGHTY:
                return xbox.getRightY();
            case HATY:
                int x = xbox.getPOV();
                if (x == 0 || x == 45 || x == 315) {
                    return 1;
                } else if (x == 180 || x == 135 || x == 225) {
                    return -1;
                } else {
                    return 0;
                }
            case HATX:
                int y = xbox.getPOV();
                if (y == 90 || y == 45 || y == 135) {
                    return 1;
                } else if (y == 270 || y == 315 || y == 225) {
                    return -1;
                } else {
                    return 0;
                }
            case TRIGGER:
                return xbox.getLeftTriggerAxis() - xbox.getRightTriggerAxis();
            default:
                return 0;
        }
    }
}
