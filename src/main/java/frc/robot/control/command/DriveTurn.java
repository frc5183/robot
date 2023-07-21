package frc.robot.control.command;

import frc.robot.control.command.enumeration.TurnMode;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.control.tuple.TupleControl;
import frc.robot.subsystem.GenericDriveTrain;

public class DriveTurn extends Command {
    private double angle;
    private double maxSpeed;
    private TurnMode mode;
    private GenericDriveTrain driveTrain;

    private TupleControl oldControl;
    private AutonomousTupleControl control = new AutonomousTupleControl(0, 0);

    private boolean finished = false;

    public DriveTurn(double angle, double maxSpeed, TurnMode mode, GenericDriveTrain driveTrain) {
        this.angle = angle;
        this.maxSpeed = maxSpeed;
        this.mode = mode;
        this.driveTrain = driveTrain;
    }

    @Override
    public void start() {
        if (this.mode == TurnMode.RELATIVE) {
            this.angle += driveTrain.getGyroscope().getDegrees();
        }

        oldControl = driveTrain.getTupleControl();
        driveTrain.setTupleControl(control);
    }

    @Override
    public void run() {
        double currentAngle = driveTrain.getGyroscope().getDegrees();
        double angleDifference = angle - currentAngle;
        double speed = angleDifference / 180 * maxSpeed;
        control.updateValue(0, speed);
        driveTrain.arcadeDrive();
        if (Math.abs(angleDifference) < 1) {
            finished = true;
        }
    }

    @Override
    public void clean() {
        driveTrain.setTupleControl(oldControl);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
