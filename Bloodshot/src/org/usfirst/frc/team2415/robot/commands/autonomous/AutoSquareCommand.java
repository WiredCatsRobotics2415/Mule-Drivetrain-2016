package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSquareCommand extends CommandGroup {
    
    public  AutoSquareCommand() {
    	Robot.driveSubsystem.resetYaw();
<<<<<<< HEAD
    	addSequential(new AdvanceAutoScriptCommand(1,1));
    	addSequential(new AdvanceAutoScriptCommand(-1,1));
    	addSequential(new AdvanceAutoScriptCommand(1,1));
    	addSequential(new AdvanceAutoScriptCommand(-1,1));
=======
    	addSequential(new AdvanceAutoScriptCommand(0,2));
    	addSequential(new AdvanceAutoScriptCommand(-2,0));
    	addSequential(new AdvanceAutoScriptCommand(0,-2));
    	addSequential(new AdvanceAutoScriptCommand(2,0));
>>>>>>> parent of 56c02ed... Change distance traveled in a square
    }
}
