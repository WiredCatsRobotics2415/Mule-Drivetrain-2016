package org.usfirst.frc.team2415.robot.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoStraightDriveCommand extends Command {

	private PID rotationalPID;
	private float desiredDistance;
	private BufferedWriter writer;
	
	
	
    public float getDesiredDistance() {
		return desiredDistance;
	}

	public AutoStraightDriveCommand(float desiredDistance) {
        requires(Robot.driveSubsystem);
        this.desiredDistance = desiredDistance;
        this.rotationalPID = Robot.driveSubsystem.rotationalPID;
        
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialize phase 1: power up the bass cannon");
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.resetYaw();
    	
    	try {
    		writer = new BufferedWriter(new FileWriter(new File("/V/data2415.csv")));
    	} catch (IOException e) {
    		try {
    			writer = new BufferedWriter(new FileWriter(new File("/U/data2415.csv")));
    		} catch (IOException f) {
    			f.printStackTrace();
    		}
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = rotationalPID.getPIDOutput(Robot.driveSubsystem.getLeftEncoder(), desiredDistance);
    	double rotation = rotationalPID.getPIDOutput(Robot.driveSubsystem.getYaw(), 0);
    	if (speed > .90) speed = .90;
    	if (speed < -.90) speed = -.90;
    	double left = speed - rotation;
    	double right = speed + rotation;
    	Robot.driveSubsystem.setMotors(left, -right);
    	
    	try {
    		System.out.println(writer);
			writer.write(Robot.driveSubsystem.getRightEncoder() + ",");
			writer.write(this.desiredDistance + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
        //return Robot.driveSubsystem.getLeftEncoder() >= this.desiredDistance*0.95;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	System.out.println("Fire.");	
    	try {
    		System.out.println("I am about to flush the toilet");
			writer.flush();
			writer.close();
			System.out.println("The dookie is down the drain");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	try {
    		System.out.println("I am about to flush the toilet");
			writer.flush();
			writer.close();
			System.out.println("The dookie is down the drain");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
