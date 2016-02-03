package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TravelDistCommand extends Command {
	
	private final double TICKS_PER_REV = 120, WHEEL_RADIUS = 2;
	private int leftStart, rightStart, leftReading, rightReading;
	private double distance, leftErr, rightErr;
	private PID pidLeft, pidRight;
	boolean isDone = false;
	
    public TravelDistCommand(double distance) {
    	pidLeft = new PID(0.01, 0,0.0015);//.00006,0,0);//0/*0.000001*/, 000005, 6);
    	pidRight = new PID(0.01, 0, 0.0015);//.00006,0,0);//0/*0.000001*/, 0.00005, 6);
    	
    	pidLeft.setDeadBand(-0.045, 0.055);
    	pidRight.setDeadBand(-0.045, 0.055);
    	
    	this.distance = (distance/(2*Math.PI*WHEEL_RADIUS))*TICKS_PER_REV;
    }

    protected void initialize() {
    	Robot.driveSubsystem.resetEncoders();
    	System.out.println(distance);
    }

    protected void execute() {
    	leftErr =  -distance - (Robot.driveSubsystem.getLeft() - leftStart);
    	rightErr = -distance - (Robot.driveSubsystem.getRight() - rightStart);
    	
//    	System.out.println("left: " + Robot.driveSubsystem.getLeft() + ",\tright: " + Robot.driveSubsystem.getRight());
    	System.out.println("left: " + leftErr + ",\tright: " + rightErr);
    	
    	double leftOut = pidLeft.pidOut(leftErr);
    	double rightOut = pidRight.pidOut(rightErr);
    	
    	if ( leftOut > .5) leftOut = .5;    
    	if ( leftOut < -.5) leftOut = -.5;
    	if ( rightOut > .5) rightOut = .5;
    	if ( rightOut < -.5) rightOut = -.5;

    	
    	Robot.driveSubsystem.setMotors(leftOut, -rightOut);
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	int count = 0;
    	for(int i=0; i<100; i++){
			Robot.driveSubsystem.setMotors(1, -1);
    	}
		while(count < 100){
			Robot.driveSubsystem.setMotors(0, 0);
			count++;
		}
    }

    protected void interrupted() {
    }
}
