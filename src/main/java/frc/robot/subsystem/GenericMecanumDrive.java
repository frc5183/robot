package frc.robot.subsystem;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Logger;
import frc.robot.control.single.SingleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.Motor;

public class GenericMecanumDrive extends Subsystem {
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
    public GenericMecanumDrive(Motor leftRear, Motor leftFront, Motor rightFront, Motor rightRear, SingleAxisGyroscope gyroscope, MecanumMode mode) {
        this.leftRear = leftRear;
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.rightRear = rightRear;
        this.gyroscope = gyroscope;
        this.mode = mode;
        this.drive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);

        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId() + "/leftRear", this.leftRear.getId().toString());
        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId() + "/leftFront", this.leftFront.getId().toString());
        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId() + "/rightFront", this.rightFront.getId().toString());
        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId() + "/rightRear", this.rightRear.getId().toString());
        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId() + "/gyroscope", this.gyroscope.getId().toString());
        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId() + "/mode", this.mode.toString());

        Logger.append(Logger.LogType.Subsystems, "mecanumDrive/" + this.getId(), "New MecanumDrive created with leftRear " + this.leftRear.getId() + " and leftFront " + this.leftFront.getId() + " and rightFront " + this.rightFront.getId() + " and rightRear " + this.rightRear.getId() + " and gyroscope " + this.gyroscope.getId() + " and mode " + this.mode);

    }
    public void periodic() {
        leftRear.periodic();
        leftFront.periodic();
        rightFront.periodic();
        rightRear.periodic();
    }
    public void drive(TupleControl translate, SingleControl rotate) {
        if (mode == MecanumMode.RELATIVE) {
            drive.driveCartesian(-translate.getValue().getVal1(), -translate.getValue().getVal2(), -rotate.getValue());
        } else {
            drive.driveCartesian(-translate.getValue().getVal1(), -translate.getValue().getVal2(), -rotate.getValue(), gyroscope.getRotation2D());
        }
    }
}
