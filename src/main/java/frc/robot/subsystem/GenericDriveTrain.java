package frc.robot.subsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Tuple2;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.MotorGroup;

public class GenericDriveTrain extends Subsystem {
    private final DifferentialDrive drive;
    private final MotorGroup left;
    private final MotorGroup right;
    private final XboxController controller;
    private final SingleAxisGyroscope gyroscope;
    private TupleControl tupleControl;

    public GenericDriveTrain(MotorGroup left, MotorGroup right, XboxController controller, SingleAxisGyroscope gyroscope, TupleControl tupleControl) {
        this.drive = new DifferentialDrive(left, right);
        this.left = left;
        this.right = right;
        this.controller = controller;
        this.gyroscope = gyroscope;
        this.tupleControl = tupleControl;

        this.left.setSafety(true);
        this.right.setSafety(true);
    }

    /**
     * @see DifferentialDrive#arcadeDrive(double, double)
     * @see TupleControl#getValue()
     */
    public void arcadeDrive() {
        drive.arcadeDrive(tupleControl.getValue().getVal1(), tupleControl.getValue().getVal2());
    }

    /**
     * @see DifferentialDrive#arcadeDrive(double, double, boolean)
     * @see TupleControl#getValue()
     * @param squared Whether to square the inputs
     */
    public void arcadeDrive(boolean squared) {
        drive.arcadeDrive(tupleControl.getValue().getVal2(), tupleControl.getValue().getVal1(), squared);
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
    public void setTupleControl(TupleControl control) {
        tupleControl=control;
    }
    public TupleControl getTupleControl() {
        return tupleControl;
    }

}
