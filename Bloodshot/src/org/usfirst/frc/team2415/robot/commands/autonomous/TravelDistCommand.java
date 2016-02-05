package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TravelDistCommand extends Command {
	
	private int leftStart, rightStart, leftReading, rightReading;
	private double distance, leftErr, rightErr;
	private PID pidLeft, pidRight;
	boolean isDone = false;
	private static final double STEADY_STATE_TOLERANCE = .05;
	private boolean direction = true;
	
    public TravelDistCommand(double distance) {
    	
    	direction = distance >= 0;
    	
    	pidLeft = new PID(0.01, 0,0.0015);
    	pidRight = new PID(0.01, 0, 0.0015);
    	
    	pidLeft.setDeadBandValues(-0.045, 0.055);
    	pidRight.setDeadBandValues(-0.045, 0.055);
    	
    	pidLeft.setOutputRange(-.5, .5);
    	pidRight.setOutputRange(-.5, .5);
    	
    	this.distance = Math.abs((distance/(2*Math.PI*DriveSubsystem.WHEEL_RADIUS))*DriveSubsystem.TICKS_PER_REV);
    }

    protected void initialize() {
    	Robot.driveSubsystem.releaseBrake();
    	Robot.driveSubsystem.resetEncoders();
    	System.out.println(distance);
    }

    protected void execute() {
    	leftErr =  -distance - (direction ? 1:-1) * (Robot.driveSubsystem.getLeft() - leftStart);
    	rightErr = -distance - (direction ? 1:-1) * (Robot.driveSubsystem.getRight() - rightStart);
    	
    	double leftOut = pidLeft.pidOut(leftErr);
    	double rightOut = pidRight.pidOut(rightErr);
    	
    	if ( leftOut > .5) leftOut = .5;    
    	if ( leftOut < -.5) leftOut = -.5;
    	if ( rightOut > .5) rightOut = .5;
    	if ( rightOut < -.5) rightOut = -.5;
    	
    	if(Math.abs(leftErr)/distance < STEADY_STATE_TOLERANCE &&
    			Math.abs(rightErr)/distance < STEADY_STATE_TOLERANCE) isDone = true;
    	
    	Robot.driveSubsystem.setMotors((direction ? 1:-1) * leftOut, (direction ? 1:-1) * -rightOut);
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.brake();
    	System.out.println("Drive straight has ended");
    }

    protected void interrupted() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.brake();
    	System.out.println("Drive straight was interrupted");
    }
}
