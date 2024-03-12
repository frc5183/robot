package frc.robot.subsystem;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Config;
import frc.robot.Tuple2;
import frc.robot.control.single.SingleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.EncodedMotor;
import frc.robot.hardware.motor.Motor;
import frc.robot.math.AccelerationLimiter;
import frc.robot.math.MecanumDriveOdometryWrapper;

public class GenericMecanumDrive extends Subsystem {

    public enum MecanumMode {
        RELATIVE, ABSOLUTE, POLAR
    }
    private final MecanumDrive drive;
    private MecanumMode mode;
    private final Motor leftRear;
    private final Motor leftFront;
    private final Motor rightFront;
    private final Motor rightRear;
    private final SingleAxisGyroscope gyroscope;
    private final MecanumDriveOdometryWrapper wrapper;
    private final AccelerationLimiter limiter = new AccelerationLimiter(0.8);
    public GenericMecanumDrive(EncodedMotor leftRear, EncodedMotor leftFront, EncodedMotor rightFront, EncodedMotor rightRear, double gearboxRatio, double wheelDiameter, SingleAxisGyroscope gyroscope, MecanumMode mode, MecanumDriveOdometryWrapper.MecanumWheelPositions wheelPositions, Pose2d start) {
        this.leftRear = leftRear.getMotor();
        this.leftFront = leftFront.getMotor();
        this.rightFront = rightFront.getMotor();
        this.rightRear = rightRear.getMotor();
        this.gyroscope = gyroscope;
        this.mode = mode;
        this.drive = new MecanumDrive(leftFront.getMotor(), leftRear.getMotor(), rightFront.getMotor(), rightRear.getMotor());
        this.wrapper= new MecanumDriveOdometryWrapper(leftRear, leftFront, rightFront, rightRear, gearboxRatio, wheelDiameter, gyroscope, mode, wheelPositions, start);
       // drive.setDeadband(0.08);

        AutoBuilder.configureHolonomic(
                this.getOdometry()::getPose,
                this.getOdometry()::resetPose,
                this.getOdometry()::getRobotChassisSpeeds,
                this::drive,
                new HolonomicPathFollowerConfig(
                        //todo
                        new PIDConstants(5.0, 0.0, 0.0),
                        new PIDConstants(5.0, 0.0, 0.0),
                        4.5,
                        0.4,
                        new ReplanningConfig()
                ),
                () -> {
                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) return alliance.get() == DriverStation.Alliance.Red;
                    return false;
                },
                this
        );
    }
    public void periodic() {
        leftRear.periodic();
        leftFront.periodic();
        rightFront.periodic();
        rightRear.periodic();
        this.wrapper.periodic();
        drive.feedWatchdog();
        drive.feed();
    }
    public void drive(TupleControl translate, SingleControl rotate) {
        if (mode == MecanumMode.RELATIVE) {
            drive.driveCartesian(translate.getValue().getVal1(), -translate.getValue().getVal2(), rotate.getValue());
        } else if (mode == MecanumMode.ABSOLUTE) {
            drive.driveCartesian(translate.getValue().getVal1(), -translate.getValue().getVal2(), rotate.getValue(), gyroscope.getRotation2D());
        } else {
            Tuple2<Double> trans = translate.getValue();
            double angle = trans.getVal2();
            double magnitude = Config.driveCurve.curve(trans.getVal1());
            magnitude = limiter.limit(magnitude);
            drive.driveCartesian(-magnitude * Math.cos(angle), -magnitude * Math.sin(angle), rotate.getValue(), gyroscope.getRotation2D());
        }
    }
    public void drive(ChassisSpeeds speeds) {
        //todo
    }
    public MecanumDriveOdometryWrapper getOdometry() {
        return wrapper;
    }
    public MecanumMode getMode() {
        return mode;
    }
    public void setMode(MecanumMode mode) {
        this.mode=mode;
    }
}
