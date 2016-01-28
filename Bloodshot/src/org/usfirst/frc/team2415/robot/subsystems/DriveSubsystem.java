package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;
import org.usfirst.frc.team2415.robot.commands.ArcadeDriveCommand;

import com.kauailabs.nav6.frc.IMU;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final float DISTANCE_PER_PULSE = 1;
	
	private final int BAUD_RATE = 57600;
	private final byte REFRESH_RATE = 50;
	
	private CANTalon leftTalOne, rightTalOne, rightTalTwo;
	private Encoder rightEncoder, leftEncoder;
	private IMU imu;
	
	public DriveSubsystem(){
		leftTalOne = new CANTalon(RobotMap.LEFT_TALON_ZERO);
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
    	//setDefaultCommand(new ArcadeDriveCommand());
    	
    }
    
    /**
     * Stops all motors from spinning
     */
    public void stop(){
    	leftTalOne.set(0);
    	rightTalOne.set(0);
    	rightTalTwo.set(0);
    }
    
    /**
     * Sets the velocity at which the motors of the drivetrain spin
     * @param left	velocity of the left motors of the drivetrain on a scale from -1 to 1
     * @param right	velocity of the right motors of the drivetrain on a scale from -1 to 1
     */
    public void setMotors(double left, double right){
    	leftTalOne.set(left);
    	rightTalOne.set(right);
    	rightTalTwo.set(right);
    }
    
    /**
     * Gives the current value at which the left motors are set
     * @return velocity of the left motors of the drivetrain on a scale from -1 to 1
     */
    public double getLeftTal(){
    	return leftTalOne.get();
    }
    
    /**
     * Gives the current value at which the right motors are set
     * @return velocity of the right motors of the drivetrain on a scale from -1 to 1
     */
    public double getRightTal(){
    	return rightTalOne.get();
    }
    
    /**
     * Gives the distance traveled by the right motors in relation to the point at which the right encoder is reset
     * @return inches traveled from the reset point of the right encoder
     */
    public double getRightDist(){
    	return rightEncoder.get();
    }
    
    /**
     * Gives the distance traveled by the left motors in relation to the point at which the left encoder is reset
     * @return inches traveled from the reset point of the left encoder
     */
    public double getLeftDist(){
    	return leftEncoder.get();
    }
    
    public double getLeftVel(){
    	return leftEncoder.getRate();
    }
    
    public double getRightVel(){
    	return rightEncoder.getRate();
    }
    
    /**
     * Re-zeros the encoders for both sides of the drivetrain
     */
    public void resetEncoders(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    /**
     * Gives the current degree of rotation about the vertical axis
     * @return returns the current yaw value between -180 and 180 degrees
     */
    public double getYaw(){
    	return imu.getYaw();
    }
    
    /**
     * Gives the current degree of elevation
     * @return returns the current pitch value between -180 and 180 degrees
     */
    public double getPitch(){
    	return imu.getPitch();
    }
    
    /**
     * Gives the current degree of rotation about midline of the robot that runs front to back
     * @return returns the current yaw value between -180 and 180 degrees
     */
    public double getRoll(){
    	return imu.getRoll();
    }
    
    /**
     * Re-calibrates the IMU's reference point for 0 degrees of rotation in yaw
     */
    public void resetYaw(){
    	imu.zeroYaw();
    }
    
    /**
     * Updates data on SmartDashboard for electronics within the Drive Subsystem
     */
	public void updateStatus() {
		SmartDashboard.putNumber("Left Encoder", getLeftDist());
		SmartDashboard.putNumber("Right Encoder", getRightDist());
		SmartDashboard.putNumber("Yaw", getYaw());
		SmartDashboard.putNumber("Right Talon", getRightTal());
		SmartDashboard.putNumber("Left Talon", getLeftTal());
		
	}
	
	public int getFreshRate(){
		return REFRESH_RATE;
	}
}

