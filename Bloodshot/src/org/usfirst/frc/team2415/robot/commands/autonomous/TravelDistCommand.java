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
    	
    	pidLeft.setDeadBandValues(-0.045, 0.055);
    	pidRight.setDeadBandValues(-0.045, 0.055);
    	
    	pidLeft.setOutputRange(-.5, .5);
    	pidRight.setOutputRange(-.5, .5);
    	
    	this.distance = (distance/(2*Math.PI*WHEEL_RADIUS))*TICKS_PER_REV;
    }

    protected void initialize() {
    	Robot.driveSubsystem.releaseBrake();
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
    	
    	if(Math.abs(leftErr)/distance < 0.02 && Math.abs(rightErr)/distance < 0.02) isDone = true;
    	
    	Robot.driveSubsystem.setMotors(leftOut, -rightOut);
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.brake();
    	System.out.println("Drive straight has ended");
    }

    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.brake();
    	System.out.println("Drive straight was interrupted");
    }
}
