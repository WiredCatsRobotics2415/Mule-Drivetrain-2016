package org.usfirst.frc.team2415.robot.commands;

import org.usfirst.frc.team2415.robot.MotionProfile;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TravelDistCommand extends Command {
	
	private MotionProfile motion;
	
	private final double POWER_TO_VEL_TOP = 0.0112711, POWER_TO_VEL_BOT = 0.0109026,
						ACCEL = 0, V_MAX = 0;
	
	private double currVel, finalVel;
	
	private long startTime;
	
	private boolean isDone = false;
	
    public TravelDistCommand(double distance, double currVel, double finalVel) {
        requires(Robot.driveSubsystem);
        this.currVel = currVel;
        this.finalVel = finalVel;
        motion = new MotionProfile(distance, currVel, finalVel, V_MAX, ACCEL);
        startTime = System.currentTimeMillis();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = (System.currentTimeMillis() - startTime)/1000.0;
    	if(time < motion.time1) currVel += ACCEL*time;
    	if(time > motion.time1 && time <= motion.time2) currVel = V_MAX;
    	if(time >= motion.time2 && time < motion.time3) currVel -= ACCEL*(time-motion.time2);
    	if(time > motion.time3){
    		currVel = finalVel;
    		isDone = true;
    	}
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
