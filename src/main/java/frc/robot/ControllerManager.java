package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class ControllerManager {
    private static final XboxController firstController = new XboxController(Config.FirstControllerID);
    private static final XboxController secondController = new XboxController(Config.SecondControllerID);
    
    public XboxController getFirstController() {
        return firstController;
    }
    public XboxController getSecondController() {
        return secondController;
    }
}
