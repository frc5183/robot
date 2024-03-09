// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.ADIS16448_IMUSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.control.AutonomousButtonMapper;
import frc.robot.control.Scheduler;
import frc.robot.control.command.*;
import frc.robot.hardware.gyro.ADISAxisGyroscope;
import frc.robot.hardware.gyro.SingleAxisGyroscope;
import frc.robot.hardware.motor.SparkMaxMotor;
import frc.robot.hardware.motor.TalonFXMotor;
import frc.robot.hardware.motor.TalonSRXMotor;
import frc.robot.hardware.motor.VictorSPXMotor;
import frc.robot.math.MecanumPoseGenerator;
import frc.robot.subsystem.GenericMecanumDrive;
import frc.robot.subsystem.GenericSpinner;

import java.sql.SQLOutput;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 * THIS FILE SHOULD NOT BE COMMITTED FROM OTHER BRANCHES TO MASTER
 * THIS FILE WILL DIFFER BETWEEN BRANCHES, INTENTIONALLY!
 */
public class Robot extends TimedRobot
{
    private REVPhysicsSim sim;
    private SparkMaxMotor leftRear;
    private SparkMaxMotor rightRear;
    private SparkMaxMotor leftFront;
    private SparkMaxMotor rightFront;
    private int count = 0;
    private final XboxController controller = new XboxController(0);
    private GenericMecanumDrive drive;
    private SparkMaxMotor floorMotor;
    private TalonSRXMotor intakeMotor;
    private TalonSRXMotor shooterMotor;
    private TalonFXMotor elevatorMotor;
    private GenericSpinner shooter;
    private GenericSpinner intake;
    private GenericSpinner elevator;
    private GenericSpinner floor;
    private SingleAxisGyroscope gyro;
    private final ADIS16448_IMU imu = new ADIS16448_IMU();
    private ADIS16448_IMUSim imuSim;
    private AutonomousButtonMapper shoot, highIntake, flip, lowIntake, lowOuttake;
    private SendableChooser<String> autoChooser = new SendableChooser<>();
    private final Timer timer = new Timer();
    public static final Scheduler scheduler = new Scheduler();

    @Override
    public void robotInit()
    {
        leftRear=new SparkMaxMotor(Config.rearLeftMotorID, CANSparkLowLevel.MotorType.kBrushless);
        rightRear=new SparkMaxMotor(Config.rearRightMotorID, CANSparkLowLevel.MotorType.kBrushless);
        leftFront=new SparkMaxMotor(Config.frontLeftMotorID, CANSparkLowLevel.MotorType.kBrushless);
        rightFront=new SparkMaxMotor(Config.frontRightMotorID, CANSparkLowLevel.MotorType.kBrushless);
        rightFront.setInverted(true);
        leftFront.setInverted(false);
        rightRear.setInverted(true);
        leftRear.setInverted(false);
        gyro = new ADISAxisGyroscope(imu, SingleAxisGyroscope.Axis.ROLL); // Roll because vertical Roborio
        gyro.calibrate();

        CameraServer.startAutomaticCapture(0);
        CameraServer.startAutomaticCapture(1);

        GenericMecanumDrive.MecanumMode mode = Config.mecanumMode;
        drive = new GenericMecanumDrive(leftRear.getEncodedMotor(), leftFront.getEncodedMotor(), rightFront.getEncodedMotor(), rightRear.getEncodedMotor(), Config.GearboxRatio, Config.WheelDiameter, gyro, mode, Config.mecanumWheels, new Pose2d()) ;
        floorMotor = new SparkMaxMotor(Config.floorMotor, CANSparkLowLevel.MotorType.kBrushless);
        intakeMotor = new TalonSRXMotor(Config.intakeMotor);
        shooterMotor = new TalonSRXMotor(Config.shooterMotor);
        elevatorMotor = new TalonFXMotor(Config.elevatorMotor);
        shooter = new GenericSpinner(shooterMotor, "Shooter");
        intake = new GenericSpinner(intakeMotor, "Intake");
        elevator = new GenericSpinner(elevatorMotor, "Elevator");
        floor = new GenericSpinner(floorMotor, "Floor");
        autoChooser.setDefaultOption("Middle Full", "MF");
        autoChooser.addOption("Left Full", "LF");
        autoChooser.addOption("Right Full", "RF");
        autoChooser.addOption("Left Simple", "LS");
        autoChooser.addOption("Middle Simple", "MS");
        autoChooser.addOption("Right Simple", "RS");
        Sendable motors = builder -> {
            builder.addDoubleProperty("Left Rear", leftRear::get, null);
            builder.addDoubleProperty("Right Rear", rightRear::get, null);
            builder.addDoubleProperty("Left Front", leftFront::get, null);
            builder.addDoubleProperty("Right Front", rightFront::get, null);
            builder.addDoubleProperty("Floor", floorMotor::get, null);
            builder.addDoubleProperty("Intake", intakeMotor::get, null);
            builder.addDoubleProperty("Shooter", shooterMotor::get, null);
            builder.addDoubleProperty("Elevator", elevatorMotor::get, null);
        };
        Sendable pos = new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("Estimated X", () -> drive.getOdometry().getPose().getX(), null);
                builder.addDoubleProperty("Estimated Y", () -> drive.getOdometry().getPose().getY(), null);
                builder.addDoubleProperty("Estimated Heading", () -> drive.getOdometry().getPose().getRotation().getDegrees(), null);
            }
        };
        SmartDashboard.putData("Autonomous Program", autoChooser);
        SmartDashboard.putData("SchedulerBasic", scheduler.getBasicSendable());
        SmartDashboard.putData("SchedulerAdvanced", scheduler.getQueueSendable());
        SmartDashboard.putData("SchedulerActive", scheduler.getActiveSendable());
        SmartDashboard.putData("Motors", motors);
        SmartDashboard.putData("Position", pos);
    }
    @Override
    public void teleopInit()
    {
        scheduler.forceEnd();
        TeleopMecanum teleopMecanum = new TeleopMecanum(drive);
        scheduler.scheduleCommand(teleopMecanum);
        //TeleopSpinner teleopIntake = new TeleopSpinner(intake, Config.botIntake);
        //scheduler.scheduleCommand(teleopIntake);
        TeleopSpinner teleopElevator = new TeleopSpinner(elevator, Config.botElevator);
        scheduler.scheduleCommand(teleopElevator);
        shoot = Config.shootButton(shooter, intake);
        highIntake = Config.highIntakeButton(shooter, intake);
        flip = Config.flipIntakeButton(floor);
        lowIntake = Config.lowIntakeButton(intake);
        lowOuttake = Config.lowOuttakeButton(intake);
    }

    @Override
    public void teleopPeriodic()
    {

        scheduler.run();
        shoot.periodic();
        highIntake.periodic();
        flip.periodic();
        lowIntake.periodic();
        lowOuttake.periodic();
    }
    @Override
    public void autonomousInit() {
        scheduler.forceEnd();
        String chosen = autoChooser.getSelected();
        Command basicShoot = Config.shoot(shooter, intake);
        Command basic = new CommandGroup(
                basicShoot,
                new TimedConsumerCommand(floor, Config.revTime+Config.postRevTime)
        );
        switch (chosen) {
            case "LS":
                gyro.setOffset(-45);
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "MS":
                gyro.setOffset(0);
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "RS":
                gyro.setOffset(45);
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "LF":
                gyro.setOffset(-45);
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                scheduler.scheduleCommand(new CommandGroup(new RunMecanum(drive, false, 1), new ConsumerCommand(floor), new ConsumerCommand(shooter), Config.lowIntake(intake)));
                break;
            case "MF":
                gyro.setOffset(0);
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                scheduler.scheduleCommand(new CommandGroup(new ConsumerCommand(drive), new ConsumerCommand(shooter), Config.flipIntake(floor), new ConsumerCommand(intake)));
                scheduler.scheduleCommand(new CommandGroup(new RunMecanum(drive, false, 1), new ConsumerCommand(floor), new ConsumerCommand(shooter), Config.lowIntake(intake)));
                scheduler.scheduleCommand(new CommandGroup(new ConsumerCommand(drive), new ConsumerCommand(shooter), new ConsumerCommand(floor), Config.lowIntake(intake)));
                scheduler.scheduleCommand(new CommandGroup(new ConsumerCommand(drive), new ConsumerCommand(shooter), Config.flipIntake(floor), new ConsumerCommand(intake)));
                scheduler.scheduleCommand(new CommandGroup(new RunMecanum(drive, true, 1), new ConsumerCommand(floor), new ConsumerCommand(shooter), new ConsumerCommand(intake)));
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "RF":
                gyro.setOffset(45);
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                scheduler.scheduleCommand(new CommandGroup(new RunMecanum(drive, false, 1), new ConsumerCommand(floor), new ConsumerCommand(shooter), Config.lowIntake(intake)));
                break;
        }
        //scheduler.scheduleCommand(new ShutUpWatchdog(drive));
    }
    @Override
    public void autonomousPeriodic() {
        scheduler.run();
    }
    public void robotPeriodic() {
        drive.periodic();
        shooter.periodic();
        intake.periodic();
        elevator.periodic();
        floor.periodic();
        System.out.println(gyro.getDegrees());
    }
    public void simulationInit() {
        sim = REVPhysicsSim.getInstance();
        imuSim = new ADIS16448_IMUSim(imu);
        sim.addSparkMax(leftFront.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(rightFront.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(leftRear.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(rightRear.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(floorMotor.getTrueRawMotor(), DCMotor.getNEO(1));
    }
    public void simulationPeriodic() {
        sim.run();
        Pose2d pose = drive.getOdometry().getPose();
        drive.periodic();
    }
}
