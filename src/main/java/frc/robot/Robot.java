// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.control.Scheduler;
import org.littletonrobotics.urcl.URCL;


public class Robot extends TimedRobot {
    public static final Scheduler scheduler = new Scheduler();

    @Override
    public void robotInit() {
        DataLogManager.start();
        URCL.start();
    }

    public void teleopInit() {
        scheduler.forceEnd();
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
