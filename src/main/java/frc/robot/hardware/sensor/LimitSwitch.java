package frc.robot.hardware.sensor;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch {
    private final DigitalInput limiter;
    private final boolean reverse;
    /**
     * @param id The digital IO port that the limit switch is on
     */
    public LimitSwitch(int id, boolean reverse) {
        limiter= new DigitalInput(id);
        this.reverse=reverse;
    }
    public LimitSwitch(int id) {
        limiter=new DigitalInput(id);
        this.reverse=false;
    }
    public boolean isClosed() {
        if (reverse) {
            return !limiter.get();
        } else {
            return limiter.get();
        }
    }
}
