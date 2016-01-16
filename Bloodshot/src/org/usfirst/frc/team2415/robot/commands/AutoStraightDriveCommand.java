package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoStraightDriveCommand extends Command {

	private PID rotationalPID;
	private float desiredDistance;
	
    public AutoStraightDriveCommand(float desiredDistance) {
        requires(Robot.driveSubsystem);
        this.desiredDistance = desiredDistance;
        this.rotationalPID = Robot.driveSubsystem.rotationalPID;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialize phase 1: power up the bass cannon");
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.resetYaw();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = rotationalPID.getPIDOutput(Robot.driveSubsystem.getLeftEncoder(), desiredDistance);
    	double rotation = rotationalPID.getPIDOutput(Robot.driveSubsystem.getYaw(), 0);
    	if (speed > .90) speed = .90;
    	if (speed < -.90) speed = -.90;
    	double left = speed - rotation;
    	double right = speed + rotation;
    	Robot.driveSubsystem.setMotors(-left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	System.out.println("Fire.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);
    }
}
