package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScriptCommand extends CommandGroup {
    
    public  AutoScriptCommand() {
    	Robot.driveSubsystem.resetYaw();
    	addSequential(new TurnCommand(90));
    	addSequential(new WaitCommand(1));
    	
    	addSequential(new TurnCommand(-90));
    	addSequential(new WaitCommand(1));
    	
    	addSequential(new TurnCommand(45));
    	addSequential(new WaitCommand(1));
    	
    	addSequential(new TurnCommand(-45));
    }
}
