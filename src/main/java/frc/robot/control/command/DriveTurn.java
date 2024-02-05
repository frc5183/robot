package frc.robot.control.command;

import frc.robot.Logger;
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

        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/angle", this.angle);
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/maxSpeed", this.maxSpeed);
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/mode", this.mode.name());
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/driveTrain", this.driveTrain.getClass().getName());

        Logger.append(Logger.LogType.Control, "command/drive/turn" + this.getId(), "New DriveTurn created with angle " + this.angle + " and max speed " + this.maxSpeed + " and mode " + this.mode.name() + " and driveTrain " + this.driveTrain.getClass().getName());
    }

    @Override
    public void start() {
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/start", "DriveTurn started.");
        if (this.mode == TurnMode.RELATIVE) {
            this.angle += driveTrain.getGyroscope().getDegrees();
            Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/angle", this.angle);
        }
    }

    @Override
    public void run() {
        control.updateValue2(maxSpeed);
        driveTrain.arcadeDrive(control);

        double currentAngle = driveTrain.getGyroscope().getDegrees();
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/run/currentAngle", currentAngle);
        if (angle < 0) {
            currentAngle = -currentAngle;
            Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/run/currentAngle", currentAngle);
        }
        double needed = angle - currentAngle;
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/run/needed", needed);

        if (Math.abs(needed) < 1) {
            control.updateValue(0, 0);
            driveTrain.arcadeDrive(control);
            finished = true;
        }
        Logger.append(Logger.LogType.Control, "command/drive/turn/" + this.getId() + "/finished", this.finished);
    }

    @Override
    public void clean() {
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
