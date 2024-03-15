// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.*;
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
import frc.robot.subsystem.GenericMecanumDrive;
import frc.robot.subsystem.GenericSpinner;
import org.littletonrobotics.urcl.URCL;

import java.sql.Driver;


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
    private PowerDistribution pdp;
    private ADIS16448_IMUSim imuSim;
    private AutonomousButtonMapper shoot, highIntake, flip, lowIntake, lowOuttake, cancelShoot;
    private SendableChooser<String> autoChooser = new SendableChooser<>();
    public static final Scheduler scheduler = new Scheduler();

    private double maxVelocityLeftFront, maxVelocityLeftRear, maxVelocityRightFront, maxVelocityRightRear, maxVelocity;

    @Override
    public void robotInit()
    {
        DataLogManager.start();
        URCL.start();
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
        leftRear.setRamp(0.1);
        rightRear.setRamp(0.1);
        leftFront.setRamp(0.1);
        rightFront.setRamp(0.1);
        CameraServer.startAutomaticCapture(0);
        CameraServer.startAutomaticCapture(1);

        pdp = new PowerDistribution(0, PowerDistribution.ModuleType.kCTRE);

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
        autoChooser.addOption("Amp Full", "AF");
        autoChooser.addOption("Source Full", "SF");
        autoChooser.addOption("Amp Simple", "AS");
        autoChooser.addOption("Middle Simple", "MS");
        autoChooser.addOption("Source Simple", "SS");
        autoChooser.addOption("Get Out Amp", "GOA");
        autoChooser.addOption("Get Out Middle", "GOM");
        autoChooser.addOption("Test Auto", "T");
        //*
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
             //*/
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

        maxVelocityLeftFront = 0;
        maxVelocityLeftRear = 0;
        maxVelocityRightFront = 0;
        maxVelocityRightRear = 0;
        maxVelocity = 0;

        SmartDashboard.putNumber("Max Velocity Left Front", maxVelocityLeftFront);
        SmartDashboard.putNumber("Max Velocity Left Rear", maxVelocityLeftRear);
        SmartDashboard.putNumber("Max Velocity Right Front", maxVelocityRightFront);
        SmartDashboard.putNumber("Max Velocity Right Rear", maxVelocityRightRear);

        // Auto NamedCommands
        NamedCommands.registerCommand("Shoot1", new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
        NamedCommands.registerCommand("Shoot2", new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
        NamedCommands.registerCommand("IntakeOut", new CommandGroup(new ConsumerCommand(drive), new ConsumerCommand(shooter), Config.flipIntake(floor), new ConsumerCommand(intake)));
        NamedCommands.registerCommand("IntakeIn", new CommandGroup(new ConsumerCommand(drive), new ConsumerCommand(shooter), new ConsumerCommand(floor), Config.flipIntake(intake)));
        NamedCommands.registerCommand("RunIntake", new CommandGroup(new ConsumerCommand(floor), Config.lowIntake(intake), new ConsumerCommand(elevator)));

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
        cancelShoot = Config.cancelShootButton(intake, shooter);

        if (autoChooser.getSelected().equals("AF") || autoChooser.getSelected().equals("AS") || autoChooser.getSelected().equals("GOA")) {
            gyro.setOffset(DriverStation.getAlliance().get() == DriverStation.Alliance.Blue ? 45 : -45);
        } else if (autoChooser.getSelected().equals("SS") || autoChooser.getSelected().equals("SF")) {
            gyro.setOffset(DriverStation.getAlliance().get() == DriverStation.Alliance.Blue ? -45 : 45);
        } else {
            gyro.setOffset(0);
        }
    }

    @Override
    public void teleopPeriodic() {
        scheduler.run();
        shoot.periodic();
        highIntake.periodic();
        flip.periodic();
        lowIntake.periodic();
        lowOuttake.periodic();
        cancelShoot.periodic();

        double currentVelocityLeftFront, currentVelocityLeftRear, currentVelocityRightFront, currentVelocityRightRear, currentVelocity;
        currentVelocityLeftFront = leftFront.getEncodedMotor().getEncoder().getVelocityRadiansPerSecond() * (Config.WheelDiameter / (2 * Config.GearboxRatio));
        currentVelocityLeftRear = leftRear.getEncodedMotor().getEncoder().getVelocityRadiansPerSecond() * (Config.WheelDiameter / (2 * Config.GearboxRatio));
        currentVelocityRightFront = rightFront.getEncodedMotor().getEncoder().getVelocityRadiansPerSecond() * (Config.WheelDiameter / (2 * Config.GearboxRatio));
        currentVelocityRightRear = rightRear.getEncodedMotor().getEncoder().getVelocityRadiansPerSecond() * (Config.WheelDiameter / (2 * Config.GearboxRatio));
        currentVelocity = gyro.getVelocityRadiansPerSecond();

        if (currentVelocityLeftFront > maxVelocityLeftFront) {
            maxVelocityLeftFront = currentVelocityLeftFront;
        }

        if (currentVelocityLeftRear > maxVelocityLeftRear) {
            maxVelocityLeftRear = currentVelocityLeftRear;
        }

        if (currentVelocityRightFront > maxVelocityRightFront) {
            maxVelocityRightFront = currentVelocityRightFront;
        }

        if (currentVelocityRightRear > maxVelocityRightRear) {
            maxVelocityRightRear = currentVelocityRightRear;
        }

        if (currentVelocity > maxVelocity) {
            maxVelocity = currentVelocity;
        }

        SmartDashboard.putNumber("Max Velocity Left Front", maxVelocityLeftFront);
        SmartDashboard.putNumber("Max Velocity Left Rear", maxVelocityLeftRear);
        SmartDashboard.putNumber("Max Velocity Right Front", maxVelocityRightFront);
        SmartDashboard.putNumber("Max Velocity Right Rear", maxVelocityRightRear);
        SmartDashboard.putNumber("Max Velocity", maxVelocity);
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
            case "AS":
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "MS":
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "SS":
                scheduler.scheduleCommand(new CommandGroup(Config.shoot(shooter, intake), new ConsumerCommand(drive), new ConsumerCommand(floor)));
                break;
            case "AF":
//                if (DriverStation.getAlliance().isPresent()) {
//                    if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
//                        gyro.setOffset(45);
//                    } else {
//                        gyro.setOffset(-45);
//                    }
//                } else {
//                    gyro.setOffset(45);
//                }
                scheduler.scheduleCommand(new Command2Wrapper(new PathPlannerAuto("AF"), drive));
                break;
            case "MF":
                scheduler.scheduleCommand(new Command2Wrapper(new PathPlannerAuto("MF"), drive));
                break;
            case "SF":
                scheduler.scheduleCommand(new Command2Wrapper(new PathPlannerAuto("SF"), drive));
                break;
            case "GOA":
                scheduler.scheduleCommand(new Command2Wrapper(new PathPlannerAuto("GOA"), drive));
                break;
            case "GOM":
                scheduler.scheduleCommand(new Command2Wrapper(new PathPlannerAuto("GOM"), drive));
                break;
            case "T":
                scheduler.scheduleCommand(new Command2Wrapper(new PathPlannerAuto("Test Auto"), drive));
                break;
            default:
                System.out.println("No known auto selected.");
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

        SmartDashboard.putNumber("Battery Voltage", pdp.getVoltage());
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
