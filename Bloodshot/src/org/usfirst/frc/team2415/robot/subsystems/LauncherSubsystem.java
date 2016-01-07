package org.usfirst.frc.team2415.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

import org.usfirst.frc.team2415.robot.RobotMap;
import org.usfirst.frc.team2415.robot.commands.launcher.RestingCommand;

import java.lang.Enum;

import java.util.Enumeration;
import java.util.Vector;
/**
 *
 */
public class LauncherSubsystem extends Subsystem {
	
	public enum Solenoids {	TOP_LEFT_FIRE, TOP_LEFT_ACCU,
							TOP_RIGHT_FIRE, TOP_RIGHT_ACCU,
							BOT_LEFT_FIRE, BOT_LEFT_ACCU,
							BOT_RIGHT_FIRE, BOT_RIGHT_ACCU,
							ALL_FIRE, ALL_ACCU
	}
	
	private Solenoid[] fireSolenoids;
	private Solenoid[] accuSolenoids;
	
	public LauncherSubsystem(){
		fireSolenoids = accuSolenoids = new Solenoid[4];
		
		for(int i=0; i<4; i++){
			fireSolenoids[i] = new Solenoid(RobotMap.PCM_ID, RobotMap.FIRE_SOLENOIDS[i]);
			accuSolenoids[i] = new Solenoid(RobotMap.PCM_ID, RobotMap.ACCUMULATE_SOLENOIDS[i]);
		}
	}
	
    public void initDefaultCommand() {
    	this.setDefaultCommand(new RestingCommand());
    }
    
    public void fire(Solenoids soleID){
    	switch(soleID){
	    	case ALL_FIRE:
	    		for(Solenoid sole : fireSolenoids) sole.set(true);
	    		break;
	    	case TOP_LEFT_FIRE:
	    		fireSolenoids[2].set(true);
	    		break;
	    	case TOP_RIGHT_FIRE:
	    		fireSolenoids[0].set(true);
	    		break;
	    	case BOT_LEFT_FIRE:
	    		fireSolenoids[3].set(true);
	    		break;
	    	case BOT_RIGHT_FIRE:
	    		fireSolenoids[1].set(true);
	    		break;
	    	default:
	    		System.out.println("WARNING (fire): An invalid Solenoid ID was entered. Please check the code for any errors.");
    	}
    }
    
    public void accumulate(Solenoids soleID){
    	switch(soleID){
	    	case ALL_ACCU:
	    		for(Solenoid sole : accuSolenoids) sole.set(true);
	    		break;
	    	case TOP_LEFT_ACCU:
	    		accuSolenoids[2].set(true);
	    		break;
	    	case TOP_RIGHT_ACCU:
	    		accuSolenoids[0].set(true);
	    		break;
	    	case BOT_LEFT_ACCU:
	    		accuSolenoids[3].set(true);
	    		break;
	    	case BOT_RIGHT_ACCU:
	    		accuSolenoids[1].set(true);
	    		break;
	    	default:
	    		System.out.println("WARNING (accumulate): An invalid Solenoid ID was entered. Please check the code for any errors.");
    	}
    }
    
    public void closeFire(){
    	for(Solenoid sole : fireSolenoids) sole.set(false);
    }
    
    public void closeAccu(){
    	for(Solenoid sole : accuSolenoids) sole.set(false);
    }
}

