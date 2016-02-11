package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSquareCommand extends CommandGroup {
    
    public  AutoSquareCommand() {
    	Robot.driveSubsystem.resetYaw();
    	addSequential(new AdvanceAutoScriptCommand(1,1));
    	addSequential(new AdvanceAutoScriptCommand(-1,1));
    	addSequential(new AdvanceAutoScriptCommand(1,1));
    	addSequential(new AdvanceAutoScriptCommand(-1,1));
    }
}
