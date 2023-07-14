package frc.robot.control.enumeration;

/**
 * Represents the mode in which a button should be used
 */
public enum ButtonStyle {
    /**
     * Represents a button whose state is simply represented by whether it is pressed down or not
     */
    SWITCH,
    /**
     * Represents a button whose state will toggle
     * every time the button is released,
     * allowing state to be changed and saved
     * without the need to hold down a button
     */
    SET
}
