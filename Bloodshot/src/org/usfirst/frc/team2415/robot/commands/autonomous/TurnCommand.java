package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.MotionProfile;
import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnCommand extends Command {
	
	private MotionProfile motion;
	private PID pid;
	
	private final double WHEEL_RADIUS = 0, WHEEL_TRACK = 0, ACCEL = 0;
	private final double TOP_POWER_TO_VEL = 0.0112711, BOT_POWER_TO_VEL = 0.0109026;
	
	private boolean isDone = false;
	
	private long startTime;
	
	private double currVel, finalVel, vMax;
	
    public TurnCommand(double angle, double finalVel, double vMax) {
    	requires(Robot.driveSubsystem);
        this.vMax = vMax;
        this.finalVel = finalVel;
        this.currVel = (WHEEL_RADIUS/(2*WHEEL_TRACK))*(Robot.driveSubsystem.getRightVel() - Robot.driveSubsystem.getLeftVel()); 
        motion = new MotionProfile(angle, currVel, finalVel, vMax, ACCEL);
        pid = new PID(0,0,0, false);
        
        startTime = System.currentTimeMillis();
    }

    protected void initialize() {
    }

    protected void execute() {
    	double time = (System.currentTimeMillis() - startTime)/1000.0;
    	
    	if(time < motion.time1) currVel += ACCEL*time;
    	if(time >= motion.time1 && time < motion.time2) currVel = vMax;
    	if(time >= motion.time2 && time < motion.time3) currVel -= ACCEL*(time-motion.time2);
    	if(time > motion.time3){
    		currVel = finalVel;
    		isDone = true;
    	}
    	
    	double leftVel = -(WHEEL_TRACK/WHEEL_RADIUS)*currVel;
    	double rightVel = (WHEEL_TRACK/WHEEL_RADIUS)*currVel;
    	
    	double leftPower = (leftVel > 0) ? TOP_POWER_TO_VEL*leftVel : BOT_POWER_TO_VEL*leftVel;
    	double rightPower = (rightVel > 0) ? TOP_POWER_TO_VEL*rightVel : BOT_POWER_TO_VEL*rightVel;
    	
    	Robot.driveSubsystem.setMotors(leftPower, rightPower);
    	
    	double leftReading = Robot.driveSubsystem.getLeftVel();
    	double rightReading = Robot.driveSubsystem.getRightVel();
    	
    	leftPower += pid.pidOut(leftVel - leftReading);
    	rightPower += pid.pidOut(rightVel - rightReading);
    	
    	Robot.driveSubsystem.setMotors(leftPower, rightPower);
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
