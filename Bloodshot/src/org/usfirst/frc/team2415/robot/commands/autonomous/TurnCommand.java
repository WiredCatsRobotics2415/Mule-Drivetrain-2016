package org.usfirst.frc.team2415.robot.commands.autonomous;

import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.Util.DataAnalyzer;
import org.usfirst.frc.team2415.robot.Util.PID;

import edu.wpi.first.wpilibj.command.Command;

/**
 * <p>Autonomous Command used for correctly centering the robot along a dersired degree of yaw rotation</p>
 * <p>Currently WIP</p>
 *
 */
public class TurnCommand extends Command {
	
	private double desiredYaw;
	private boolean isDone = false;

	private static final double STEADY_STATE_TOLERANCE = .02,
								SAMPLE_SIZE = 6;
	
	private PID pid;
	
	private ArrayList<Double> samples;
	
    public TurnCommand(double desiredYaw) {
        requires(Robot.driveSubsystem);
        this.desiredYaw = desiredYaw;
        pid = new PID(0.01, 0.01, 0.0015);
        
        pid.setDeadBandValues(-0.045, 0.055);
        pid.setOutputRange(-.5, .5);
        
        samples = new ArrayList<Double>();
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.releaseBrake();
    	System.out.println(desiredYaw);
    }
    
    protected void execute() {
    	double error = desiredYaw - Robot.driveSubsystem.getYaw(),
    		   stdError = 0;
    	
    	if(error > 180) error -= 360;
    	else if(error < -180) error += 360;
    	
    	if(samples.size() >= SAMPLE_SIZE){
    		samples.remove(0);
    		samples.add(error);
    		stdError = DataAnalyzer.stdError(samples);
    	}else samples.add(error);
    	
    	if(Math.abs(error/desiredYaw) < STEADY_STATE_TOLERANCE) isDone = true;
    	if(stdError <= STEADY_STATE_TOLERANCE && stdError != 0) isDone = true;
    	
    	
    	double power = pid.pidOut(error);
    	if(Math.abs(power) > .5) power = ((power > 0) ? 1:-1) * .5;
    	
    	System.out.println(power);
    	
    	Robot.driveSubsystem.setMotors(power, power);
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
