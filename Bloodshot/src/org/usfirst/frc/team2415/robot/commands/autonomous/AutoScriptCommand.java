package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScriptCommand extends CommandGroup {
    
    public  AutoScriptCommand() {
    	Robot.driveSubsystem.resetYaw();
    	addSequential(new MoveCommand(2,10));
    	addSequential(new MoveCommand(-4,-3));
    	addSequential(new MoveCommand(7,-1));
    	addSequential(new MoveCommand(-5,-6));
    }
}
