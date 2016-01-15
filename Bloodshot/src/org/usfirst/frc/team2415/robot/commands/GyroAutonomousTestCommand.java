package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroAutonomousTestCommand extends Command {
	
	private double startingYaw;

    public GyroAutonomousTestCommand() {
        requires(Robot.driveSubsystem);
        startingYaw = Robot.driveSubsystem.getYaw();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(startingYaw);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.stop();
    }
}
