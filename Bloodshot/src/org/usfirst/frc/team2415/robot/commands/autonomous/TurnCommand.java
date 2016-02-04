package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.MotionProfile;
import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnCommand extends Command {
	private int leftStart, rightStart, leftReading, rightReading;
	private double distance, leftErr, rightErr;
	private PID pidLeft, pidRight;
	boolean isDone = false;
	
    public TurnCommand(double angle) {
    	pidLeft = new PID(0.01, 0,0.0015);
    	pidRight = new PID(0.01, 0, 0.0015);
    	
    	pidLeft.setDeadBandValues(-0.07, 0.07);
    	pidRight.setDeadBandValues(-0.07, 0.07);
    	
    	pidLeft.setOutputRange(-.25, .25);
    	pidRight.setOutputRange(-.25, .25);
    	
    	double radians = Math.toRadians(angle);
    	double arcLength = (DriveSubsystem.WHEEL_TRACK/2)*radians;
    	
    	distance = ((arcLength)/(2*Math.PI*DriveSubsystem.WHEEL_RADIUS))*DriveSubsystem.TICKS_PER_REV;
    }

    protected void initialize() {
    	Robot.driveSubsystem.releaseBrake();
    	Robot.driveSubsystem.resetEncoders();
    	System.out.println(distance);
    }

    protected void execute() {
    	leftErr =  -distance - (Robot.driveSubsystem.getLeft() - leftStart);
    	rightErr = -distance + (Robot.driveSubsystem.getRight() - rightStart);
    	
    	System.out.println("left: " + leftErr + "\tright: " + rightErr);
    	
    	double leftOut = pidLeft.pidOut(leftErr);
    	double rightOut = pidRight.pidOut(rightErr);
    	
    	if ( leftOut > .25) leftOut = .25;    
    	if ( leftOut < -.25) leftOut = -.25;
    	if ( rightOut > .25) rightOut = .25;
    	if ( rightOut < -.25) rightOut = -.25;
    	
    	if(Math.abs(leftErr)/distance < 0.02 && Math.abs(rightErr)/distance < 0.02) isDone = true;
    	
    	Robot.driveSubsystem.setMotors(leftOut, rightOut);
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.brake();
    	System.out.println("Turn has ended");
    }

    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.brake();
    	System.out.println("Turn was interrupted");
    }
}
