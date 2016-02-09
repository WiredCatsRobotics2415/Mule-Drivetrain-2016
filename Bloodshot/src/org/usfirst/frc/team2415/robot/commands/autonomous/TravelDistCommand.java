package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.MotionProfile;
import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TravelDistCommand extends Command {
	
	private MotionProfile motion;
	private PID pid;
	
	private final double ACCEL = 160;
	private final double TOP_POWER_TO_VEL = 0.0112711, BOT_POWER_TO_VEL = 0.0109026, MOTOR_LOAD = 1;
	
	private boolean isDone = false;
	
	private long startTime, lastTime;
	
	private double distance, startLeft, startRight, currVel, finalVel, vMax, acc;
	
    public TravelDistCommand(double distance, double finalVel, double vMax, double acc) {
        requires(Robot.driveSubsystem);
        this.vMax = (vMax/(2*DriveSubsystem.WHEEL_RADIUS*Math.PI))*DriveSubsystem.TICKS_PER_REV;
        this.finalVel = (finalVel/(2*DriveSubsystem.WHEEL_RADIUS*Math.PI))*DriveSubsystem.TICKS_PER_REV;
        this.currVel = 0;//(WHEEL_RADIUS/2)*(Robot.driveSubsystem.getLeftVel()*(1/120.) + Robot.driveSubsystem.getRightVel()*(1/120.)); 
        this.acc = (acc/(2*DriveSubsystem.WHEEL_RADIUS*Math.PI))*DriveSubsystem.TICKS_PER_REV;
        pid = new PID(0.015, 0, 0.0015);
        
        pid.setDeadBandValues(-0.07, 0.07);
        pid.setOutputRange(-.5, .5);
        
        this.distance = (distance/(2*DriveSubsystem.WHEEL_RADIUS*Math.PI))*DriveSubsystem.TICKS_PER_REV;
        
//    	System.out.println("leftStart: " + startLeft + ",\trightStart: " + startRight);
    }

    protected void initialize() {
    	startTime = lastTime = System.currentTimeMillis();
        Robot.driveSubsystem.resetEncoders();
        startLeft = Robot.driveSubsystem.getLeft();
        startRight = Robot.driveSubsystem.getRight();
        motion = new MotionProfile(distance, currVel, finalVel, vMax, acc);
        System.out.println(motion.time1 + "\t" + motion.time2 + "\t" + motion.time3);
    }

    protected void execute() {
    	double timeStep = (System.currentTimeMillis() - lastTime)/1000.0,
    			runTime = (System.currentTimeMillis() - startTime)/1000.0;
    	
    	if(runTime < motion.time1) currVel += acc*timeStep;
    	if(runTime >= motion.time1 && runTime < motion.time2) currVel = vMax;
    	if(runTime >= motion.time2 && runTime < motion.time3) currVel -= acc*timeStep;
    	if(runTime >= motion.time3){
    		currVel = finalVel;
    		isDone = true;
    	}
    	
    	double leftVel = (1/DriveSubsystem.WHEEL_RADIUS)*(currVel/DriveSubsystem.TICKS_PER_REV);
    	double rightVel = (1/DriveSubsystem.WHEEL_RADIUS)*(currVel/DriveSubsystem.TICKS_PER_REV);
    	
    	double leftPower = (leftVel > 0) ? TOP_POWER_TO_VEL*leftVel : BOT_POWER_TO_VEL*leftVel;
    	double rightPower = (rightVel > 0) ? TOP_POWER_TO_VEL*rightVel : BOT_POWER_TO_VEL*rightVel;
    	
    	Robot.driveSubsystem.setMotors(leftPower, rightPower);
    	
    	double leftErr = distance + (Robot.driveSubsystem.getLeft() - startLeft);
    	double rightErr = distance + (Robot.driveSubsystem.getRight() - startRight);
    	
//    	System.out.println("left: " + leftErr + "\tright: " + rightErr);
    	
    	leftPower *= MOTOR_LOAD;	
    	rightPower *= MOTOR_LOAD;
    	
    	double pidLeft = pid.pidOut(leftErr);
    	double pidRight = pid.pidOut(rightErr);
    	
    	leftPower += pidLeft;
    	rightPower += pidRight;
    	
    	Robot.driveSubsystem.setMotors(-leftPower, rightPower);
    	lastTime = System.currentTimeMillis();
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
//    	int count = 0;
//    	for(int i=0; i<100; i++){
//			Robot.driveSubsystem.setMotors(1, -1);
//    	}
//		while(count < 100){
//			Robot.driveSubsystem.setMotors(0, 0);
//			count++;
//		}
    }

    protected void interrupted() {
    }
}