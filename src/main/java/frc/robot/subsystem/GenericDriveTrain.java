package frc.robot.subsystem;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.MotorGroup;

public class GenericDriveTrain extends Subsystem {
    private DifferentialDrive drive;
    private MotorGroup left;
    private MotorGroup right;
    private XboxController controller;
    private Encoder encoder;
    private SingleAxisGyroscope gyroscope;

    public GenericDriveTrain(MotorGroup left, MotorGroup right, XboxController controller, Encoder encoder, SingleAxisGyroscope gyroscope) {
        this.drive = new DifferentialDrive(left, right);
        this.left = left;
        this.right = right;
        this.controller = controller;
        this.encoder = encoder;
        this.gyroscope = gyroscope;

        this.left.setSafety(true);
        this.right.setSafety(true);
    }

    /**
     * @see DifferentialDrive#arcadeDrive(double, double)
     * @see TupleControl#getValue()
     * @param tupleControl The tuple control to use
     */
    public void arcadeDrive(TupleControl tupleControl) {
        drive.arcadeDrive(tupleControl.getValue().getVal1(), tupleControl.getValue().getVal2());
    }

    /**
     * @see DifferentialDrive#arcadeDrive(double, double, boolean)
     * @see TupleControl#getValue()
     * @param tupleControl The tuple control to use
     * @param squared Whether to square the inputs
     */
    public void arcadeDrive(TupleControl tupleControl, boolean squared) {
        drive.arcadeDrive(tupleControl.getValue().getVal2(), tupleControl.getValue().getVal1(), squared);
    }

    /**
     * @param speed The speed to set the left and right motor groups to
     */
    public void setSpeed(double speed) {
        left.set(speed);
        right.set(speed);
    }

    /**
     * @return The average speed of the left and right motor groups
     */
    public double getSpeed() {
        return (left.get() + right.get()) / 2;
    }

    public DifferentialDrive getDrive() {
        return drive;
    }

    public void setDrive(DifferentialDrive drive) {
        this.drive = drive;
    }

    public MotorGroup getLeft() {
        return left;
    }

    public void setLeft(MotorGroup left) {
        this.left = left;
    }

    public MotorGroup getRight() {
        return right;
    }

    public void setRight(MotorGroup right) {
        this.right = right;
    }

    public XboxController getController() {
        return controller;
    }

    public void setController(XboxController controller) {
        this.controller = controller;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public SingleAxisGyroscope getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(SingleAxisGyroscope gyroscope) {
        this.gyroscope = gyroscope;
    }
}
