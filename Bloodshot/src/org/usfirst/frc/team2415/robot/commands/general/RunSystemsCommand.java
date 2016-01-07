package org.usfirst.frc.team2415.robot.commands.general;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.commands.drive.ArcadeDriveCommand;
import org.usfirst.frc.team2415.robot.commands.launcher.FireCommand;
import org.usfirst.frc.team2415.robot.subsystems.LauncherSubsystem.Solenoids;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class RunSystemsCommand extends CommandGroup {
    
	int counter = 0;
	
    public  RunSystemsCommand() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
    	//Run forward, then backward, 
    	//then clockwise point turn, 
    	//then counterclockwise point turn 
    	switch (counter) {
	    	case 1: Robot.driveSubystem.setMotors(1, 1);
	    		break;
	    	case 30: Robot.driveSubystem.setMotors(-1, -1);
	    		break;
	    	case 60: Robot.driveSubystem.setMotors(1, -1);
	    		break;
	    	case 90: Robot.driveSubystem.setMotors(-1, 1);
	    		break;
	    	case 120: new FireCommand(Solenoids.TOP_LEFT_FIRE);
	    		break;
	    	case 150: new FireCommand(Solenoids.TOP_RIGHT_FIRE);
    			break;
	    	case 180: new FireCommand(Solenoids.BOT_LEFT_FIRE);
    			break;
	    	case 210: new FireCommand(Solenoids.BOT_RIGHT_FIRE);
				break;
    	}
    	
    	counter++;
    	

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	requires(Robot.launcherSubsystem);
    	requires(Robot.driveSubystem);
    }
}
