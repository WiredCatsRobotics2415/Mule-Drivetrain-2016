package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSquareCommand extends CommandGroup {
    
    public  AutoSquareCommand() {
    	Robot.driveSubsystem.resetYaw();
    	addSequential(new AdvanceAutoScriptCommand(0,2));
    	addSequential(new AdvanceAutoScriptCommand(-2,0));
    	addSequential(new AdvanceAutoScriptCommand(0,-2));
    	addSequential(new AdvanceAutoScriptCommand(2,0));
    }
}
