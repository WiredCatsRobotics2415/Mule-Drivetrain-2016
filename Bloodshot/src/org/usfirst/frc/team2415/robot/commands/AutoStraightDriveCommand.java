package org.usfirst.frc.team2415.robot.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.WriteToFlashDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoStraightDriveCommand extends Command {

	private PID rotationalPID;
	private PID yawPID;
	private float desiredDistance;
	private BufferedWriter lengthWriter;
	private BufferedWriter yawWriter;
	
	
	
    public float getDesiredDistance() {
		return desiredDistance;
	}

	public AutoStraightDriveCommand(float desiredDistance) {
        requires(Robot.driveSubsystem);
        this.desiredDistance = desiredDistance;
        this.rotationalPID = Robot.driveSubsystem.rotationalPID;
        this.yawPID = Robot.driveSubsystem.yawPID;
        
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialize phase 1: power up the bass cannon");
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.resetYaw();
    	
    	Robot.writeToFlashDrive.createBufferedWriter("lengthData", lengthWriter);
    	Robot.writeToFlashDrive.createBufferedWriter("YawData", yawWriter);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = rotationalPID.getPIDOutput(Robot.driveSubsystem.getLeftEncoder(), desiredDistance);
    	double rotation = yawPID.getPIDOutput(Robot.driveSubsystem.getYaw(), 0);
    	if (speed > .90) speed = .90;
    	if (speed < -.90) speed = -.90;
    	double left = speed - rotation;
    	double right = speed + rotation;
    	Robot.driveSubsystem.setMotors(left, -right);
    	
    	Robot.writeToFlashDrive.writeToFile(Robot.driveSubsystem.getLeftEncoder(), desiredDistance, lengthWriter);
    	Robot.writeToFlashDrive.writeToFile(Robot.driveSubsystem.getYaw(), 0, yawWriter);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
        //return Robot.driveSubsystem.getLeftEncoder() >= this.desiredDistance*0.95;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	System.out.println("Fire.");	
    	
    	Robot.writeToFlashDrive.flushAndClose(lengthWriter);
    	Robot.writeToFlashDrive.flushAndClose(yawWriter);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);	
    	
    	Robot.writeToFlashDrive.flushAndClose(lengthWriter);
    	Robot.writeToFlashDrive.flushAndClose(yawWriter);
    }
}
