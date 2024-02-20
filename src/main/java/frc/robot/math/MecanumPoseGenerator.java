package frc.robot.math;

import edu.wpi.first.math.geometry.Pose2d;

import java.util.ArrayList;

public class MecanumPoseGenerator {
    private MecanumDriveOdometryWrapper odometry;
    private ArrayList<CameraPoseGenerator> cameras = new ArrayList<>();
    public MecanumPoseGenerator(MecanumDriveOdometryWrapper odometry) {
        this.odometry = odometry;
    }
    public void addCamera(CameraPoseGenerator camera) {
        cameras.add(camera);
    }
    public Pose2d periodic() {
        odometry.periodic();
        for (CameraPoseGenerator camera : cameras) {
            camera.update().ifPresent(estimatedRobotPose -> odometry.addPose(estimatedRobotPose.estimatedPose.toPose2d(), estimatedRobotPose.timestampSeconds));
        }
        return odometry.getPose();
    }
}
