package frc.robot.hardware.servo;

/**
 * A wrapper class around Servo.
 * @see edu.wpi.first.wpilibj.Servo
 */
public class Servo {
    private final edu.wpi.first.wpilibj.Servo servo;
    private boolean inverted = false;

    /**
     * @param port the PWM port the servo is connected to
     */
    public Servo(int port) {
        servo = new edu.wpi.first.wpilibj.Servo(port);
    }

    /**
     * Sets the speed of the servo.
     * If the servo is inverted, the speed will be set to 1 - speed.
     * @param speed the speed to set the servo to
     */
    public void set(double speed) {
        if (inverted) {
            servo.set(1 - speed);
        } else {
            servo.set(speed);
        }
    }

    /**
     * Sets the angle of the servo.
     * @param angle the angle to set the servo to
     * @see edu.wpi.first.wpilibj.Servo#setAngle(double)
     */
    public void setAngle(double angle) {
        servo.setAngle(angle);
    }

    /**
     * @return the position of the servo
     * @see edu.wpi.first.wpilibj.Servo#get()
     */
    public double get() {
        return servo.get();
    }

    /**
     * @return the angle of the servo
     * @see edu.wpi.first.wpilibj.Servo#getAngle()
     */
    public double getAngle() {
        return servo.getAngle();
    }

    /**
     * Sets the servo to be inverted or not.
     * @param inverted true if the servo should be inverted, false otherwise
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    /**
     * @return the inversion state
     */
    public boolean getInverted() {
        return this.inverted;
    }

    /**
     * Toggles the servo enabled status.
     * @see edu.wpi.first.wpilibj.Servo#setDisabled()
     */
    public void toggle() {
        servo.setDisabled();
    }

    /**
     * @return the true hardware servo
     * @see edu.wpi.first.wpilibj.Servo
     */
    public edu.wpi.first.wpilibj.Servo getRaw() {
        return servo;
    }
}
