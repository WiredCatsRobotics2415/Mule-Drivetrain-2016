package org.usfirst.frc.team2415.robot.commands.drive;

import org.usfirst.frc.team2415.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDriveCommand extends Command {

    public ArcadeDriveCommand() {
        requires(Robot.driveSubystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double leftY = -Robot.gamepad.leftY();
    	double rightX = Robot.gamepad.rightX();
    	
    	double left = leftY + rightX;
    	double right =  leftY - rightX;
    	
    	//Robot.driveSubystem.setMotors(left, -right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubystem.stop();
    }
}
