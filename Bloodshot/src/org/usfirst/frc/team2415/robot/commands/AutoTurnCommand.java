package org.usfirst.frc.team2415.robot.commands;

import java.io.BufferedWriter;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurnCommand extends Command {
	
	private float amount_turn_in_degrees;
	private PID yawPID;
	private BufferedWriter autoTurnWriter;

    public AutoTurnCommand(float turn) {
        requires(Robot.driveSubsystem);
        yawPID = Robot.driveSubsystem.yawPID;
        this.amount_turn_in_degrees = turn;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialize phase 1: Turn");
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.resetYaw();
    	
    	Robot.writeToFlashDrive.createBufferedWriter("autoTurnData", autoTurnWriter);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double rotation = yawPID.getPIDOutput(Robot.driveSubsystem.getYaw(), this.amount_turn_in_degrees);
    	double left = rotation;
    	double right = -rotation;
    	Robot.driveSubsystem.setMotors(left, right);
    	
    	Robot.writeToFlashDrive.writeToFile(Robot.driveSubsystem.getYaw(), this.amount_turn_in_degrees, autoTurnWriter);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);

    	Robot.writeToFlashDrive.flushAndClose(autoTurnWriter);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);

    	Robot.writeToFlashDrive.flushAndClose(autoTurnWriter);
    }
}
