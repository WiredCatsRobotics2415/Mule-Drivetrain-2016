package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDriveCommand extends Command {

    public ArcadeDriveCommand() {
        requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double leftY = -Robot.gamepad.leftY();
    	double rightX = Robot.gamepad.rightX();
    	
    	double left = leftY - rightX;
    	double right =  leftY + rightX;
    	
    	Robot.driveSubsystem.setMotors(-left, right);

		SmartDashboard.putNumber("Left Encoder", -Robot.driveSubsystem.getLeft());
		SmartDashboard.putNumber("Right Encoder", Robot.driveSubsystem.getRight());
		
		SmartDashboard.putNumber("Yaw", Robot.driveSubsystem.getYaw());
		SmartDashboard.putNumber("Pitch", Robot.driveSubsystem.getPitch());
		SmartDashboard.putNumber("Roll", Robot.driveSubsystem.getRoll());
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
