package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroAutonomousTestCommand extends Command {
	
	private double desiredYaw;
	private long startingTime;
	private double currentYaw;
	private double p, i, d;
	private boolean isDone = false;
	private double lastError;
	private double elapsedTime;

    public GyroAutonomousTestCommand() {
        requires(Robot.driveSubsystem);
        desiredYaw = 0;
        startingTime = System.currentTimeMillis();
        lastError = 0;
        p = .487;
        i = .362;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elapsedTime = (System.currentTimeMillis() - startingTime)/1000.0;

    	double error = desiredYaw - currentYaw;
    	double pid = proportional(error) + integral(error) + derivative(error);
    	Robot.driveSubsystem.setMotors(pid, pid);
    	lastError = error;
    	
    	startingTime = System.currentTimeMillis();
    	
    	if (desiredYaw == Robot.driveSubsystem.getYaw()) isDone = true;

    }
    
    private double proportional(double error) {
    	currentYaw = Robot.driveSubsystem.getYaw();
    	double proportional = p * error;
    	return proportional;
    }
    
    private double integral(double error) {
    	
    	if(lastError == 0) return 0;

    	double integral = i*.5*(error + lastError)*elapsedTime;
    	return integral;
    }
    
    private double derivative(double error) {
    	
    	if(lastError == 0) return 0;
    	
    	double derivative = d * ((error-lastError)/elapsedTime);
    	
    	return derivative;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
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
