package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.MotionProfile;
import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TravelDistCommand extends Command {
	
	private MotionProfile motion;
	private PID pid;
	
	private final double WHEEL_RADIUS = 3.75/2, ACCEL = 160;
	private final double TOP_POWER_TO_VEL = 0.0112711, BOT_POWER_TO_VEL = 0.0109026, MOTOR_LOAD = 1;
	
	private boolean isDone = false;
	
	private long startTime, lastTime;
	
	private double distance, startLeftDist, startRightDist, currVel, finalVel, vMax, acc;
	
    public TravelDistCommand(double distance, double finalVel, double vMax, double acc) {
        requires(Robot.driveSubsystem);
        this.vMax = vMax/(2*WHEEL_RADIUS*Math.PI);
        this.finalVel = finalVel/(2*WHEEL_RADIUS*Math.PI);
        this.currVel = 0;//(WHEEL_RADIUS/2)*(Robot.driveSubsystem.getLeftVel()*(1/120.) + Robot.driveSubsystem.getRightVel()*(1/120.)); 
        this.acc = acc / (2*WHEEL_RADIUS*Math.PI);
        motion = new MotionProfile(distance, currVel, finalVel, vMax, acc);
        pid = new PID(0.02, 0.002, 0.002);
        
        this.distance = distance*(3.75*Math.PI)/120;
        
    	System.out.println("leftStart: " + startLeftDist + ",\trightStart: " + startRightDist);
    }

    protected void initialize() {
    	startTime = lastTime = System.currentTimeMillis();
        Robot.driveSubsystem.resetEncoders();
        startLeftDist = Robot.driveSubsystem.getLeftDist();
        startRightDist = Robot.driveSubsystem.getRightDist();
    }

    protected void execute() {
    	double timeStep = (System.currentTimeMillis() - lastTime)/1000.0;
    	
    	if((System.currentTimeMillis() - startTime)/1000.0 < motion.time1){
    		currVel += acc*timeStep;
    	}
    	if((System.currentTimeMillis() - startTime)/1000.0 >= motion.time1 && (System.currentTimeMillis() - startTime)/1000.0 < motion.time2) {
    		currVel = vMax;
    	}// currVel = vMax;
    	if((System.currentTimeMillis() - startTime)/1000.0 >= motion.time2 && (System.currentTimeMillis() - startTime)/1000.0 < motion.time3){
    		currVel = vMax;//-= acc*timeStep;
    	}
    	if((System.currentTimeMillis() - startTime)/1000.0 >= motion.time3){
    		currVel = finalVel;
    		isDone = true;
    	}
    	
    	double leftVel = (1/WHEEL_RADIUS)*currVel;
    	double rightVel = (1/WHEEL_RADIUS)*currVel;
    	
    	double leftPower = (leftVel > 0) ? TOP_POWER_TO_VEL*leftVel : BOT_POWER_TO_VEL*leftVel;
    	double rightPower = (rightVel > 0) ? TOP_POWER_TO_VEL*rightVel : BOT_POWER_TO_VEL*rightVel;
    	
    	Robot.driveSubsystem.setMotors(leftPower, rightPower);
    	
    	double leftReading = Robot.driveSubsystem.getLeftDist() - startLeftDist;
    	double rightReading = Robot.driveSubsystem.getRightDist() - startRightDist;

//    	System.out.println("left: " + leftReading + ",\tright: " + rightReading);
    	
    	leftPower *= MOTOR_LOAD;	
    	rightPower *= MOTOR_LOAD;
    	
//    	double pidLeft = pid.pidOut(distance - leftReading);
//    	double pidRight = pid.pidOut(-distance - rightReading);
    	
//    	leftPower += pidLeft;
//    	rightPower += pidRight;
    	
    	Robot.driveSubsystem.setMotors(-leftPower, rightPower);
    	lastTime = System.currentTimeMillis();
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	int count = 0;
    	for(int i=0; i<100; i++){
			Robot.driveSubsystem.setMotors(1, -1);
    	}
		while(count < 100){
			Robot.driveSubsystem.setMotors(0, 0);
			count++;
		}
    }

    protected void interrupted() {
    }
}
