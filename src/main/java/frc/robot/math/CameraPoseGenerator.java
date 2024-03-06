package frc.robot.math;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import java.util.Optional;

public class CameraPoseGenerator {
    PhotonPoseEstimator estimator;
    public CameraPoseGenerator(PhotonCamera camera, Transform3d transform) {
        estimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2024Crescendo), PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, camera, transform );
    }
    public Optional<EstimatedRobotPose> update() {
        return estimator.update();
    }
}
