package org.usfirst.frc.team2415.robot.commands.autonomous;

import java.util.ArrayList;
import java.util.Iterator;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * <p>Autonomous Command used for correctly centering the robot along a dersired degree of yaw rotation</p>
 * <p>Currently WIP</p>
 *
 */
public class GyroTurnCommand extends Command {
	
	private double desiredYaw;
	private boolean isDone = false;

	private static final double STEADY_STATE_TOLERANCE = .02,
								SAMPLE_SIZE = 6;
	
	private PID pid;
	
	private ArrayList<Double> samples;
	
    public GyroTurnCommand(double desiredYaw) {
        requires(Robot.driveSubsystem);
        this.desiredYaw = desiredYaw;
        pid = new PID(0.015, 0, 0.0015);
        
        pid.setDeadBandValues(-0.07, 0.07);
        pid.setOutputRange(-.5, .5);
        
        samples = new ArrayList<Double>();
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.releaseBrake();
    }
    
    protected void execute() {
    	double error = desiredYaw - Robot.driveSubsystem.getYaw(),
    		   stdError = 0;
    	
    	if(error > 180) error -= 360;
    	else if(error < -180) error += 360;
    	
    	if(samples.size() >= SAMPLE_SIZE){
    		samples.remove(0);
    		samples.add(error);
    		stdError = stdError(samples);
    	}else samples.add(error);
    	
    	if(Math.abs(error/desiredYaw) < STEADY_STATE_TOLERANCE) isDone = true;
    	if(Math.abs(stdError) <= STEADY_STATE_TOLERANCE && stdError != 0) isDone = true;
    	
    	double power = pid.pidOut(error);
    	Robot.driveSubsystem.setMotors(power, power);
    }
    
    private double mean(ArrayList<Double> vals){
    	double sum = 0;
    	for (Iterator<Double> it = vals.iterator(); it.hasNext();){
    		sum += it.next();
    	}
    	return sum/vals.size();
    }
    
    private double stdDeviation(ArrayList<Double> vals){
    	double m = mean(vals), sum = 0;
    	for (Iterator<Double> it = vals.iterator(); it.hasNext();){
    		sum += Math.pow(it.next() - m, 2);
    	}
    	return sum/vals.size();
    }
    
    private double stdError(ArrayList<Double> vals){
    	return stdDeviation(vals)/vals.size();
    }
    
    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.brake();
    }

    protected void interrupted() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.brake();
    }
}
