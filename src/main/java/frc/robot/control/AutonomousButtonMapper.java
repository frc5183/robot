package frc.robot.control;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Robot;
import frc.robot.control.command.Command;
import frc.robot.control.enumeration.Button;

public class AutonomousButtonMapper {
    public interface CommandSource {
        public Command getCommand();
    }
    private CommandSource commandSource;
    private Button button;
    private boolean state = false;
    private XboxController xbox;
    private boolean finished;
    public AutonomousButtonMapper(CommandSource commandSource, XboxController xbox, Button button) {
        this.commandSource = commandSource;
        this.xbox = xbox;
        this.button = button;
    }
    public void periodic() {
        if (Button.getButtonValue(button, xbox) && !state) {
            Robot.scheduler.interrupt(commandSource.getCommand());
            state = true;
        }
        state = Button.getButtonValue(button, xbox);
    }
}
