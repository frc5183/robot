package frc.robot.control.enumeration;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Represents the available physical buttons on an XBOX 360 Controller
 */
public enum Button {
    A, B, X, Y, LEFTBUMPER, RIGHTBUMPER, LEFTSTICK, RIGHTSTICK, SELECT, START;

    /**
     * Used to evaluate a Button with an XboxController to retrieve the status of the Physical Button
     * On that Controller. A value of True indicates the button is pressed down.
     * @param button the Button that needs to be evaluated
     * @param xbox The XboxController that is used to retrieve the value
     * @return true if the button is pressed down, false otherwise
     */
    public static boolean getButtonValue(Button button, XboxController xbox) {
        switch (button) {
            case A:
                return xbox.getAButton();
            case B:
                return xbox.getBButton();
            case X:
                return xbox.getXButton();
            case Y:
                return xbox.getYButton();
            case START:
                return xbox.getStartButton();
            case SELECT:
                return xbox.getBackButton();
            case LEFTBUMPER:
                return xbox.getLeftBumper();
            case LEFTSTICK:
                return xbox.getLeftStickButton();
            case RIGHTBUMPER:
                return xbox.getRightBumper();
            case RIGHTSTICK:
                return xbox.getRightStickButton();
            default:
                return false;
        }
    }
}
