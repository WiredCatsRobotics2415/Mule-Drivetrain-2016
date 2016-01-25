package org.usfirst.frc.team2415.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private CANTalon IntakeMotor;
	private CANTalon SpinMotor;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setIntakeMotor(double intakeMotor){
    	IntakeMotor.set(intakeMotor);
    }
    public void setSpinMotor(double spinMotor){
    	SpinMotor.set(spinMotor);
    }
    
    public void stopIntakeMotor(){
    	IntakeMotor.set(0);
    }
    public void stopSpinMotor(){
    	SpinMotor.set(0);
    }
}

