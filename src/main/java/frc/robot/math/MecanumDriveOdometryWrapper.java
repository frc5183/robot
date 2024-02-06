package frc.robot.math;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.EncodedMotor;
import frc.robot.hardware.motor.Motor;
import frc.robot.subsystem.GenericMecanumDrive;

public class MecanumDriveOdometryWrapper {
    public static class MecanumWheelPositions {
        public Translation2d frontLeft;
        public Translation2d frontRight;
        public Translation2d rearLeft;
        public Translation2d rearRight;
        public MecanumWheelPositions(Translation2d frontLeft, Translation2d rearLeft, Translation2d frontRight, Translation2d rearRight) {
            this.frontLeft = frontLeft;
            this.frontRight = frontRight;
            this.rearLeft = rearLeft;
            this.rearRight = rearRight;
        }
    }
    private final GenericMecanumDrive.MecanumMode mode;
    private final EncodedMotor leftRear;
    private final EncodedMotor leftFront;
    private final EncodedMotor rightFront;
    private final EncodedMotor rightRear;
    private final SingleAxisGyroscope gyroscope;
    private final MecanumDriveKinematics kinematics;
    private final MecanumDriveOdometry odometry;
    public MecanumDriveOdometryWrapper(EncodedMotor leftRear, EncodedMotor leftFront, EncodedMotor rightFront, EncodedMotor rightRear, SingleAxisGyroscope gyroscope, GenericMecanumDrive.MecanumMode mode, MecanumWheelPositions wheelPositions) {
        this.leftRear = leftRear;
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.rightRear = rightRear;
        this.gyroscope = gyroscope;
        this.mode = mode;
        kinematics = new MecanumDriveKinematics(wheelPositions.frontLeft, wheelPositions.frontRight, wheelPositions.rearLeft, wheelPositions.rearRight);
        odometry = new MecanumDriveOdometry(kinematics, gyroscope.getRotation2D(), getWheelPositions());
    }
    public MecanumDriveWheelPositions getWheelPositions() {
        return new MecanumDriveWheelPositions(leftFront.getEncoder().getUnitsRotations(), rightFront.getEncoder().getUnitsRotations(), leftRear.getEncoder().getUnitsRotations(), rightRear.getEncoder().getUnitsRotations());
    }
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }
    public void periodic() {
        odometry.update(gyroscope.getRotation2D(), getWheelPositions());
    }
    public void resetPose(Pose2d pose) {
        odometry.resetPosition(gyroscope.getRotation2D(), getWheelPositions(), pose);
    }
}
