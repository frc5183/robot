package frc.robot.control.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import frc.robot.Config;
import frc.robot.control.single.AutonomousSingleControl;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.math.MecanumDriveOdometryWrapper;
import frc.robot.subsystem.GenericMecanumDrive;

import static java.lang.Double.NaN;

public class RelativeMecanumMove extends Command{
    public static final double precision = 1; // INCHES
    public static final double speed = 1; // SPEED
    private final Distance unit;
    private final GenericMecanumDrive drive;
    private final MecanumDriveOdometryWrapper odometry;
    private final AutonomousTupleControl translate;
    private final AutonomousSingleControl rotate;
    private boolean isFinished=false;
    private Pose2d startPose;
    private final double tX;
    private final double tY;
    private final double tTheta;
    public RelativeMecanumMove(GenericMecanumDrive drive, MecanumDriveOdometryWrapper wrapper, Distance unit, double tX, double tY, double tTheta) {
        this.drive=drive;
        this.odometry=wrapper;
        this.unit=unit;
        this.tX=tX;
        this.tY=tY;
        while (tTheta >= 180) {
            tTheta -= 360;
        }
        while (tTheta < -180) {
            tTheta += 360;
        }
        this.tTheta=tTheta;
        translate = new AutonomousTupleControl(0, 0);
        rotate = new AutonomousSingleControl(0);
        require(drive);
    }
    private double curve(double x) {
        return (x/Math.abs(x))*(Math.sqrt(1-(Math.abs(x))));
    }

    @Override
    public String getName() {
        return "Relative Mecanum Move";
    }

    @Override
    public void start() {
        System.out.println("STARTING");
        Pose2d startPoseMetric = odometry.getPose();
        startPose = Convert(startPoseMetric);
    }
    private Pose2d Convert(Pose2d pose) {
        Measure<Distance> xBase = Units.Meters.of(pose.getX());
        Measure<Distance> yBase = Units.Meters.of(pose.getY());
        double x = xBase.in(unit);
        double y = yBase.in(unit);
        double theta = normalize(pose.getRotation());
        return new Pose2d(x, y, Rotation2d.fromDegrees(theta));
    }
    private double precision(double t) {
        return (double) ((int) (t * precision)) / precision;
    }
    private double normalize(Rotation2d rotation) {
        double degrees = rotation.getDegrees();
        while (degrees >= 360) {
            degrees -= 360;
        }
        while (degrees < 0) {
            degrees += 360;
        }
        return degrees;
    }
    @Override
    public void run() {
        Pose2d currentPose = Convert(odometry.getPose());
        Transform2d deltaPose = currentPose.minus(startPose); // Change in position
        double x = -precision(deltaPose.getX());
        double y = -precision(deltaPose.getY());
        double theta = precision(deltaPose.getRotation().unaryMinus().getDegrees()); // Individual Components
        if (x==0) {
            x=0.0001; // Avoid division by zero
        }
        if (y==0) {
            y = 0.0001; // Avoid division by zero
        }
        if (theta<0) {
            theta+=360; // Normalize
        }
        if (theta==0) {
            theta=0.001; // Avoid division by zero
        }
        double xSpeed=Math.abs(curve(x/tX)* Config.maxAutonDriveSpeed); // Get curved speed
        double ySpeed=Math.abs(curve(y/tY) * Config.maxAutonDriveSpeed);
        if (tX < 0) {
            xSpeed*=-1; // Reverse direction
        }
        if (tY < 0) {
            ySpeed*=-1; // Reverse direction
        }
        if (tX >= tY) {
            ySpeed*=Math.abs(tY/tX); // Adjust speed accordingly
        } else {
            xSpeed*=Math.abs((tX/tY));
        }
        if (tX == 0) {
            xSpeed=0; // No required movement check
        }
        if (tY == 0) {
            ySpeed=0; // No required movement check
        }
        if ((x > tX && tX > 0) || (x<tX && tX <0)) {
            xSpeed=0; // Check if movement is complete
        }
        if ((y > tY && tY > 0) || (y<tY && tY<0)) {
            ySpeed=0; // Check if movement is complete
        }
        if (theta>180) {
            theta=360-theta; // Shortest path
        }
        double thetaSpeed = Math.abs(curve(theta/tTheta));
        if (theta/tTheta > 0.38) {
            thetaSpeed=0;
        }
        if (tTheta < 0) {
            thetaSpeed*=-1; // Reverse direction
        }
        if (tTheta == 0) {
            thetaSpeed=0; // No required movement check
        }
        if (Math.abs(theta) > Math.abs(tTheta)) {
            thetaSpeed=0; // Check if movement is complete
        }
        if ((xSpeed==0 || Double.isNaN(xSpeed)) && (ySpeed == 0 || Double.isNaN(ySpeed)) && (thetaSpeed==0 || Double.isNaN(thetaSpeed))) {
            isFinished=true; // Check if movement is complete
        }
        translate.updateValue1(xSpeed);
        translate.updateValue2(ySpeed);
        rotate.setValue(thetaSpeed);
        drive.drive(translate, rotate);
    }

    @Override
    public void clean() {
        System.out.println("Finishing");
        translate.updateValue(0, 0);
        rotate.setValue(0);
        drive.drive(translate, rotate);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
