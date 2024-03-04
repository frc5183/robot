package frc.robot.subsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.control.single.SingleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.EncodedMotor;
import frc.robot.hardware.motor.Motor;
import frc.robot.math.MecanumDriveOdometryWrapper;

public class GenericMecanumDrive extends Subsystem{

    public enum MecanumMode {
        RELATIVE, ABSOLUTE
    }
    private final MecanumDrive drive;
    private final MecanumMode mode;
    private final Motor leftRear;
    private final Motor leftFront;
    private final Motor rightFront;
    private final Motor rightRear;
    private final SingleAxisGyroscope gyroscope;
    private final MecanumDriveOdometryWrapper wrapper;
    public GenericMecanumDrive(EncodedMotor leftRear, EncodedMotor leftFront, EncodedMotor rightFront, EncodedMotor rightRear, SingleAxisGyroscope gyroscope, MecanumMode mode, MecanumDriveOdometryWrapper.MecanumWheelPositions wheelPositions, Pose2d start) {
        this.leftRear = leftRear.getMotor();
        this.leftFront = leftFront.getMotor();
        this.rightFront = rightFront.getMotor();
        this.rightRear = rightRear.getMotor();
        this.gyroscope = gyroscope;
        this.mode = mode;
        this.drive = new MecanumDrive(leftFront.getMotor(), leftRear.getMotor(), rightFront.getMotor(), rightRear.getMotor());
        this.wrapper= new MecanumDriveOdometryWrapper(leftRear, leftFront, rightFront, rightRear, gyroscope, mode, wheelPositions, start);
    }
    public void periodic() {
        leftRear.periodic();
        leftFront.periodic();
        rightFront.periodic();
        rightRear.periodic();
        this.wrapper.periodic();
    }
    public void drive(TupleControl translate, SingleControl rotate) {
        if (mode == MecanumMode.RELATIVE) {
            drive.driveCartesian(-translate.getValue().getVal1(), -translate.getValue().getVal2(), -rotate.getValue());
        } else {
            drive.driveCartesian(-translate.getValue().getVal1(), -translate.getValue().getVal2(), -rotate.getValue(), gyroscope.getRotation2D());
        }
    }
    public MecanumDriveOdometryWrapper getOdometry() {
        return wrapper;
    }
    public MecanumMode getMode() {
        return mode;
    }
}
