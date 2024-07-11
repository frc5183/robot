// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.control.Scheduler;
import frc.robot.control.command.TeleopArcade;
import frc.robot.hardware.gyro.ADISAxisGyroscope;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.MotorGroup;
import frc.robot.hardware.motor.VictorSPXMotor;
import frc.robot.subsystem.GenericTankDrive;
import org.littletonrobotics.urcl.URCL;


public class Robot extends TimedRobot {
    public static final Scheduler scheduler = new Scheduler();

    private VictorSPXMotor leftTopMotor;
    private VictorSPXMotor leftBottomMotor;
    private MotorGroup leftMotors;

    private VictorSPXMotor rightTopMotor;
    private VictorSPXMotor rightBottomMotor;
    private MotorGroup rightMotors;

    private ADISAxisGyroscope gyro;

    private GenericTankDrive drive;

    @Override
    public void robotInit() {
        DataLogManager.start();
        URCL.start();

        leftTopMotor = new VictorSPXMotor(57);
        leftBottomMotor = new VictorSPXMotor(58);
        leftMotors = new MotorGroup(leftTopMotor, leftBottomMotor);

        rightTopMotor = new VictorSPXMotor(55);
        rightBottomMotor = new VictorSPXMotor(56);
        rightMotors = new MotorGroup(rightTopMotor, rightBottomMotor);

        // pitch is correct axis i think?
        gyro = new ADISAxisGyroscope(new ADIS16448_IMU(), SingleAxisGyroscope.Axis.PITCH);
        gyro.calibrate();

        drive = new GenericTankDrive(leftMotors, rightMotors, gyro);
    }

    public void teleopInit() {
        scheduler.forceEnd();

        TeleopArcade teleopArcade = new TeleopArcade(drive);
        scheduler.scheduleCommand(teleopArcade);
    }

    @Override
    public void teleopPeriodic() {
        scheduler.run();
    }

    @Override
    public void autonomousInit() {
        scheduler.forceEnd();
    }

    @Override
    public void autonomousPeriodic() {
        scheduler.run();
    }

    public void robotPeriodic() {}

    public void simulationInit() {}

    public void simulationPeriodic() {}
}
