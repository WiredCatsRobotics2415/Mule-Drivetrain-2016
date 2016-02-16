package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScriptCommand extends CommandGroup {
    
    public  AutoScriptCommand() {
    	Robot.driveSubsystem.resetYaw();
    	addSequential(new MoveCommand(10, 2));
    	addSequential(new MoveCommand(-7, 4));
    	addSequential(new MoveCommand(9, 3));
    	addSequential(new MoveCommand(-12, -9));
    }
}
