// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.hardware.motor.SparkMaxMotor;
import frc.robot.hardware.motor.VictorSPXMotor;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 * THIS FILE SHOULD NOT BE COMMITTED FROM OTHER BRANCHES TO MASTER
 * THIS FILE WILL DIFFER BETWEEN BRANCHES, INTENTIONALLY!
 */
public class Robot extends TimedRobot
{
    private SparkMaxMotor leftRear;
    private SparkMaxMotor rightRear;
    private SparkMaxMotor leftFront;
    private SparkMaxMotor rightFront;
    private final XboxController controller = new XboxController(0);
    private MecanumDrive drive;

    @Override
    public void robotInit()
    {
        DataLogManager.start();
        DriverStation.startDataLog(DataLogManager.getLog());
        leftRear=new SparkMaxMotor(0, CANSparkLowLevel.MotorType.kBrushless);
        rightRear=new SparkMaxMotor(1, CANSparkLowLevel.MotorType.kBrushless);
        leftFront=new SparkMaxMotor(2, CANSparkLowLevel.MotorType.kBrushless);
        rightFront=new SparkMaxMotor(3, CANSparkLowLevel.MotorType.kBrushless);
        drive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);
    }


    @Override
    public void teleopPeriodic()
    {
        double y = controller.getLeftY();
        double x = controller.getLeftX();
        double turn = controller.getRightX();
        drive.driveCartesian(-y, -x, -turn); // Inversion to follow the right hand rule
    }
    public void periodic() {
        leftRear.periodic();;
        rightRear.periodic();
        leftFront.periodic();
        rightFront.periodic();
    }
}
