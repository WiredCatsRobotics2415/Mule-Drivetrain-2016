package org.usfirst.frc.team2415.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * <p>Autonomous Command used for correctly centering the robot along a dersired degree of yaw rotation</p>
 * <p>Currently WIP</p>
 *
 */
public class GyroAutonomousTestCommand extends Command {
	
	private double desiredYaw, lastError, elapsedTime;
	
	private long lastTime;
	private boolean isDone = false;
	
	private PID pid;
	
    public GyroAutonomousTestCommand(double desiredYaw) {
        requires(Robot.driveSubsystem);
        this.desiredYaw = desiredYaw;
        lastTime = System.currentTimeMillis();
        lastError = 0;
        double kUlt = 2;
        double p = kUlt * .01;
        double i = kUlt * .001;//.006;
        double d = kUlt * .001;
        pid = new PID(p,i,d, true);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double error = desiredYaw - Robot.driveSubsystem.getYaw();
    	
    	if(error > 180) error -= 360;
    	else if(error < -180) error += 360;
    	
    	double power = pid.pidOut(error);
    }
    
//    private double rollingAverage(ArrayList<Double> data){
//    	double sum = 0;
//    	for (int i = 0; i < data.size(); i++){
//    		sum += data.get(i);
//    	}
//    	return sum/data.size();
//    }

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
