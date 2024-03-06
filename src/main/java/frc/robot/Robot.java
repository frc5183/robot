// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
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
import frc.robot.subsystem.GenericMecanumDrive;
import frc.robot.subsystem.GenericSpinner;


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
    private AutonomousButtonMapper shoot, highIntake, flip, lowIntake;
    private SendableChooser<String> autoChooser = new SendableChooser<>();
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
        gyro = new ADISAxisGyroscope(new ADIS16448_IMU(), SingleAxisGyroscope.Axis.ROLL); // Roll because vertical Roborio
        gyro.calibrate();
        GenericMecanumDrive.MecanumMode mode = Config.mecanumMode;
        drive = new GenericMecanumDrive(leftRear.getEncodedMotor(), leftFront.getEncodedMotor(), rightFront.getEncodedMotor(), rightRear.getEncodedMotor(), Config.GearboxRatio, Config.WheelDiameter, gyro, mode, Config.mecanumWheels, new Pose2d()) ;
        floorMotor = new SparkMaxMotor(Config.floorMotor, CANSparkLowLevel.MotorType.kBrushless);
        intakeMotor = new TalonSRXMotor(Config.intakeMotor);
        shooterMotor = new TalonSRXMotor(Config.shooterMotor);
        elevatorMotor = new TalonFXMotor(Config.elevatorMotor);
        shooter = new GenericSpinner(shooterMotor);
        intake = new GenericSpinner(intakeMotor);
        elevator = new GenericSpinner(elevatorMotor);
        floor = new GenericSpinner(floorMotor);
        autoChooser.setDefaultOption("Simple", "S");
        autoChooser.addOption("Red Left", "RL");
        autoChooser.addOption("Red Middle" , "RM");
        autoChooser.addOption("Red Right", "RR");
        autoChooser.addOption("Blue Left", "BL");
        autoChooser.addOption("Blue Middle", "BM");
        autoChooser.addOption("Blue Right", "BR");
        autoChooser.addOption("Custom", "C");
        SmartDashboard.putData("Autonomous Program", autoChooser);
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
    }

    @Override
    public void teleopPeriodic()
    {

        scheduler.run();
        shoot.periodic();
        highIntake.periodic();
        flip.periodic();
        lowIntake.periodic();
    }
    @Override
    public void autonomousInit() {
        String chosen = autoChooser.getSelected();
        Command basicShoot = Config.shoot(shooter, intake);
        Command basic = new CommandGroup(
                basicShoot,
                new TimedConsumerCommand(floor, Config.revTime+Config.postRevTime)
        );
        switch (chosen) {
            case "S":
              //  scheduler.scheduleCommand(basic);
                break;
            case "RL":
                break;
            case "RM":
                break;
            case "RR":
                break;
            case "BL":
                break;
            case "BM":
                break;
            case "BR":
                break;
            case "C":
                break;
        }
        scheduler.scheduleCommand(new ShutUpWatchdog(drive));
    }
    @Override
    public void autonomousPeriodic() {
        scheduler.run();
    }
    public void periodic() {
        drive.periodic();
        shooter.periodic();
        intake.periodic();
        elevator.periodic();
        floor.periodic();
    }
    public void simulationInit() {
        sim = REVPhysicsSim.getInstance();
        sim.addSparkMax(leftFront.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(rightFront.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(leftRear.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(rightRear.getTrueRawMotor(), DCMotor.getNEO(1));
        sim.addSparkMax(floorMotor.getTrueRawMotor(), DCMotor.getNEO(1));
    }
    public void simulationPeriodic() {
        sim.run();
    }
}
