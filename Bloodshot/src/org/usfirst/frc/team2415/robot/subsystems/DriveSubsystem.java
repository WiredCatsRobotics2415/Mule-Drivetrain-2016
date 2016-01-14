package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;
import org.usfirst.frc.team2415.robot.commands.drive.ArcadeDriveCommand;

import com.kauailabs.nav6.frc.IMU;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final float DISTANCE_PER_PULSE = 1/45.5f;
	
	private final int BAUD_RATE = 57600;
	
	private final byte REFRESH_RATE = 50;
	
	private CANTalon leftTalOne, leftTalTwo, rightTalOne, rightTalTwo;
	private Encoder rightEncoder, leftEncoder;
	private IMU imu;
	
	public DriveSubsystem(){
		leftTalOne = new CANTalon(RobotMap.LEFT_TALON_ZERO);
		leftTalTwo = new CANTalon(RobotMap.LEFT_TALON_ONE);
		rightTalOne = new CANTalon(RobotMap.RIGHT_TALON_ZERO);
		rightTalTwo = new CANTalon(RobotMap.RIGHT_TALON_ONE);
		
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER[0],RobotMap.RIGHT_ENCODER[1]);
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER[0],RobotMap.LEFT_ENCODER[1]);
		
		resetEncoders();
		leftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		rightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		
		SerialPort imuSerialPort = new SerialPort(BAUD_RATE, SerialPort.Port.kMXP);
		imu = new IMU(imuSerialPort, REFRESH_RATE);
		imu.zeroYaw();
		
		
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
    
    public void setMotors(double left, double right){
    	leftTalOne.set(left);
    	leftTalTwo.set(left);
    	rightTalOne.set(right);
    	rightTalTwo.set(right);
    }
    
    public double getLeftTal(){
    	return leftTalOne.get();
    }
    
    public double getRightTal(){
    	return rightTalOne.get();
    }
    
    public double getRightEncoder(){
    	return rightEncoder.get();
    }
    
   
    public double getLeftEncoder(){
    	return leftEncoder.get();
    }
    
    public void resetEncoders(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public double getYaw(){
    	return imu.getYaw();
    }
    
    public double getPitch(){
    	return imu.getPitch();
    }
    
    public double getRoll(){
    	return imu.getRoll();
    }
}

