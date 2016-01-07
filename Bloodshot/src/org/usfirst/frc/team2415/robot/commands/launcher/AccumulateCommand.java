package org.usfirst.frc.team2415.robot.commands.launcher;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.subsystems.LauncherSubsystem.Solenoids;

/**
 *
 */
public class AccumulateCommand extends Command {
	
	Solenoids soleID;
	long startTime;
	boolean isDone = false;
	
    public AccumulateCommand(Solenoids soleID) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.launcherSubsystem);
        this.soleID = soleID;
        startTime = System.currentTimeMillis();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.launcherSubsystem.accumulate(soleID);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if((System.currentTimeMillis() - startTime) / 1000.0 >= 300) isDone = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.launcherSubsystem.closeFire();
    	Robot.launcherSubsystem.closeAccu();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.launcherSubsystem.closeFire();
    	Robot.launcherSubsystem.closeAccu();
    }
}
