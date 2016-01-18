package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getContourDataCenterX() {
    	return Robot.visionTable.getNumber("centerX", 0);
    }
    
    public double getContourDataCenterY() {
    	return Robot.visionTable.getNumber("centerY", 0);
    }
    
    public double[] getContourDataArea() {
    	return Robot.visionTable.getNumberArray("area", new double [0]);
    }
    
    
    public void updateStatus() {
    	SmartDashboard.putNumber("Center X", Robot.visionTable.getNumber("centerX", 0));
    	SmartDashboard.putNumber("Center Y", Robot.visionTable.getNumber("centerY", 0));
    }
}

