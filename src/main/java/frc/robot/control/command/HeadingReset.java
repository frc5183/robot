package frc.robot.control.command;

import frc.robot.hardware.gyro.SingleAxisGyroscope;

public class HeadingReset extends Command{
    private final SingleAxisGyroscope gyro;
    boolean isFinished=false;
    public HeadingReset(SingleAxisGyroscope gyro) {
        this.gyro = gyro;
    }
    @Override
    public String getName() {
        return "Heading Reset";
    }

    @Override
    public void start() {

    }

    @Override
    public void run() {
        double angle = gyro.getDegrees();
        gyro.setOffset(angle - gyro.getOffset());
        System.out.println("Offset Reset to " + gyro.getOffset());
        isFinished=true;
    }

    @Override
    public void clean() {

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
