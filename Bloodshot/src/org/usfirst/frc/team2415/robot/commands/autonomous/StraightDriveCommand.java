package org.usfirst.frc.team2415.robot.commands.autonomous;

import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.Util.DataAnalyzer;
import org.usfirst.frc.team2415.robot.Util.PID;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StraightDriveCommand extends Command {
	
	private int leftStart, rightStart;
	private double distance, leftErr, rightErr;
	private double stdErrLeft = 0, stdErrRight = 0;
	private PID pidLeft, pidRight;
	boolean isDone = false;
	private static final double STEADY_STATE_TOLERANCE = .05,
								SAMPLE_SIZE = 6;
	
	private ArrayList<Double> leftSamples, rightSamples;
	
    public StraightDriveCommand(double distance) {
    	
    	pidLeft = new PID(0.01, 0,0.0015);
    	pidRight = new PID(0.01, 0, 0.0015);
    	
    	pidLeft.setDeadBandValues(-0.045, 0.055);
    	pidRight.setDeadBandValues(-0.045, 0.055);
    	
    	pidLeft.setOutputRange(-.5, .5);
    	pidRight.setOutputRange(-.5, .5);
    	
    	this.distance = (distance/(2*Math.PI*DriveSubsystem.WHEEL_RADIUS))*DriveSubsystem.TICKS_PER_REV;
    }

    protected void initialize() {
    	Robot.driveSubsystem.releaseBrake();
    	Robot.driveSubsystem.resetEncoders();
    	leftStart = Robot.driveSubsystem.getLeft();
    	rightStart = Robot.driveSubsystem.getRight();
    }

    protected void execute() {
    	leftErr =  -distance - (Robot.driveSubsystem.getLeft() - leftStart);
    	rightErr = -distance - (Robot.driveSubsystem.getRight() - rightStart);
    	
    	if(leftSamples.size() >= SAMPLE_SIZE){
    		leftSamples.remove(0);
    		leftSamples.add(leftErr);
    		stdErrLeft = DataAnalyzer.stdError(leftSamples);
    	}else leftSamples.add(leftErr);

    	if(rightSamples.size() >= SAMPLE_SIZE){
    		rightSamples.remove(0);
    		rightSamples.add(rightErr);
    		stdErrRight = DataAnalyzer.stdError(rightSamples);
    	}else rightSamples.add(rightErr);
    	
    	if(Math.abs(leftErr/distance) < STEADY_STATE_TOLERANCE &&
    			Math.abs(rightErr/distance) < STEADY_STATE_TOLERANCE) isDone = true;
    	
    	if(stdErrLeft <= STEADY_STATE_TOLERANCE && stdErrRight <= STEADY_STATE_TOLERANCE && 
    			stdErrLeft != 0 && stdErrRight != 0) isDone = true;
    	
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
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.brake();
    }

    protected void interrupted() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.brake();
    }
}
