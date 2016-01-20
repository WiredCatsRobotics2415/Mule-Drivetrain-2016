package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurnCommand extends Command {
	
	private float amount_turn_in_degrees;
	private PID turnPID;

    public AutoTurnCommand(float turn) {
        requires(Robot.driveSubsystem);
        turnPID = Robot.driveSubsystem.rotationalPID;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialize phase 1: Turn");
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.resetYaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double rotation = turnPID.getPIDAngleOutput(Robot.driveSubsystem.getYaw(), 
    												amount_turn_in_degrees,
    												Robot.driveSubsystem.getWrappedYaw(amount_turn_in_degrees));
    	double left = rotation;
    	double right = -rotation;
    	Robot.driveSubsystem.setMotors(left, right); //i really hope this is right
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	System.out.println("Booty");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);
    }
}
