package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;
import org.usfirst.frc.team2415.robot.commands.drive.ArcadeDriveCommand;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	public CANTalon leftTalOne, leftTalTwo, rightTalOne, rightTalTwo;
	
	public DriveSubsystem(){
		leftTalOne = new CANTalon(RobotMap.LEFT_TALON_ZERO);
		leftTalTwo = new CANTalon(RobotMap.LEFT_TALON_ONE);
		rightTalOne = new CANTalon(RobotMap.RIGHT_TALON_ZERO);
		rightTalTwo = new CANTalon(RobotMap.RIGHT_TALON_ONE);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDriveCommand());
    }
    
    public void stop(){
    	leftTalOne.set(0);
    	leftTalTwo.set(0);
    	rightTalOne.set(0);
    	rightTalTwo.set(0);
    }
    /*
    public void setMotors(double left, double right){
    	leftTalOne.set(left);
    	leftTalTwo.set(left);
    	rightTalOne.set(right);
    	rightTalTwo.set(right);
    }
    */
    public double getLeftTal(){
    	return leftTalOne.get();
    }
    
    public double getRightTal(){
    	return rightTalOne.get();
    }
}

