package frc.robot.hardware.servo;

/**
 * A wrapper class around Servo.
 * @see edu.wpi.first.wpilibj.Servo
 */
public class Servo {
    private final edu.wpi.first.wpilibj.Servo servo;

    /**
     * @param port the PWM port the servo is connected to
     */
    public Servo(int port) {
        servo = new edu.wpi.first.wpilibj.Servo(port);
    }

    /**
     * Sets the position of the servo.
     * @param position the speed to set the servo to, should be between 0 and 1
     * @see edu.wpi.first.wpilibj.Servo#set(double)
     */
    public void set(double position) {
        servo.set(Math.max(0, Math.min(1, position)));
    }

    /**
     * @return the position of the servo
     * @see edu.wpi.first.wpilibj.Servo#get()
     */
    public double get() {
        return servo.get();
    }

    /**
     * Sets the angle of the servo.
     * @param angle the angle to set the servo to, should be between 0 and 180
     * @see edu.wpi.first.wpilibj.Servo#setAngle(double)
     */
    public void setAngle(double angle) {
        servo.setAngle(Math.max(0, Math.min(180, angle)));
    }

    /**
     * @return the angle of the servo
     * @see edu.wpi.first.wpilibj.Servo#getAngle()
     */
    public double getAngle() {
        return servo.getAngle();
    }

    /**
     * Disables the servo until the next set() call.
     * @see edu.wpi.first.wpilibj.Servo#setDisabled()
     */
    public void disable() {
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
