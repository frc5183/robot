package frc.robot.subsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Tuple2;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.MotorGroup;

public class GenericDriveTrain extends Subsystem {
    private final DifferentialDrive drive;
    private final MotorGroup left;
    private final MotorGroup right;
    private final XboxController controller;
    private final SingleAxisGyroscope gyroscope;

    public GenericDriveTrain(MotorGroup left, MotorGroup right, XboxController controller, SingleAxisGyroscope gyroscope) {
        this.drive = new DifferentialDrive(left, right);
        this.left = left;
        this.right = right;
        this.controller = controller;
        this.gyroscope = gyroscope;

        this.left.setSafety(true);
        this.right.setSafety(true);
    }

    /**
     * @see DifferentialDrive#arcadeDrive(double, double)
     * @param input Tuple2<Double> with val1 being the xSpeed and val2 being the zRotation
     */
    public void arcadeDrive(Tuple2<Double> input) {
        drive.arcadeDrive(input.getVal1(), input.getVal2());
    }

    /**
     * @see DifferentialDrive#arcadeDrive(double, double, boolean)
     * @param input Tuple2<Double> with val1 being the xSpeed and val2 being the zRotation
     * @param squared Whether to square the inputs
     */
    public void arcadeDrive(Tuple2<Double> input, boolean squared) {
        drive.arcadeDrive(input.getVal1(), input.getVal2(), squared);
    }

    public void setSpeed(double speed) {
        left.set(speed);
        right.set(speed);
    }

    public DifferentialDrive getDrive() {
        return drive;
    }

    public MotorGroup getLeft() {
        return left;
    }

    public MotorGroup getRight() {
        return right;
    }

    public XboxController getController() {
        return controller;
    }

    public SingleAxisGyroscope getGyroscope() {
        return gyroscope;
    }
}
