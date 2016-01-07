package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;
import org.usfirst.frc.team2415.robot.commands.drive.ArcadeDriveCommand;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	Talon leftTal, rightTal;
	
	public DriveSubsystem(){
		leftTal = new Talon(RobotMap.LEFT_TALONS);
		rightTal = new Talon(RobotMap.RIGHT_TALONS);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDriveCommand());
    }
    
    public void stop(){
    	leftTal.set(0);
    	rightTal.set(0);
    }
    
    public void setMotors(double left, double right){
    	leftTal.set(left);
    	rightTal.set(right);
    }
    
    public double getLeftTal(){
    	return leftTal.get();
    }
    
    public double getRightTal(){
    	return rightTal.get();
    }
}

