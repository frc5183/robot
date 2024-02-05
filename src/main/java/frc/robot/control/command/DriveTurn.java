package frc.robot.control.command;

import frc.robot.control.enumeration.TurnMode;
import frc.robot.control.tuple.AutonomousTupleControl;
import frc.robot.subsystem.GenericTankDrive;

public class DriveTurn extends Command {
    private double angle;
    private final double maxSpeed;
    private final TurnMode mode;
    private final GenericTankDrive driveTrain;
    private final AutonomousTupleControl control = new AutonomousTupleControl(0, 0);

    private boolean finished = false;

    public DriveTurn(double angle, double maxSpeed, TurnMode mode, GenericTankDrive driveTrain) {
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
    }

    @Override
    public void run() {
        control.updateValue2(maxSpeed);
        driveTrain.arcadeDrive(control);

        double currentAngle = driveTrain.getGyroscope().getDegrees();
        if (angle < 0) {
            currentAngle = -currentAngle;
        }
        double needed = angle - currentAngle;

        if (Math.abs(needed) < 1) {
            control.updateValue(0, 0);
            driveTrain.arcadeDrive(control);
            finished = true;
        }
    }

    @Override
    public void clean() {
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
