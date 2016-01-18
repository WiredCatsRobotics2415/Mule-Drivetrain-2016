package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 * <p>Autonomous Command used for correctly centering the robot along a dersired degree of yaw rotation</p>
 * <p>Currently WIP</p>
 *
 */
public class GyroAutonomousTestCommand extends Command {
	
	private double desiredYaw, lastError, elapsedTime, intCumulative;
	
	private long startingTime;
	private double p, i, d;
	private boolean isDone = false;
	
	
    public GyroAutonomousTestCommand(double desiredYaw) {
        requires(Robot.driveSubsystem);
        this.desiredYaw = desiredYaw;
        startingTime = System.currentTimeMillis();
        lastError = 0;
        p = .001;
        i = .01;
        d = .5;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	long time = System.currentTimeMillis();
    	double currYaw = Robot.driveSubsystem.getYaw();
    	elapsedTime = (time - startingTime)/1000.0;
    	
    	double error = desiredYaw - currYaw;
    	//if(error > 180) error -= 360;
    	//else if(error < -180) error += 360;
    	
    	double pid = proportional(error) + integral(error) + derivative(error);
    	System.out.println("Error: " + error + ",\t" + "PID: " + pid);
    	Robot.driveSubsystem.setMotors(pid, pid);
    	lastError = error;
    	
    	startingTime = time;
    	if (currYaw == desiredYaw) isDone = true;

    }
    
    private double proportional(double error) {
    	return p * error;
    }
    
    private double integral(double error) {
    	
    	if(lastError == 0) return 0;
    	intCumulative += .5*(error + lastError)*elapsedTime;
    	return i*intCumulative;
    }
    
    private double derivative(double error) {
    	if(lastError == 0) return 0;
    	return d * ((error-lastError)/elapsedTime);
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
