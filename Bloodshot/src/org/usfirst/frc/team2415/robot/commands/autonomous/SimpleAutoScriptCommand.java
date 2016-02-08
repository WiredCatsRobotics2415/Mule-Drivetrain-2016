package org.usfirst.frc.team2415.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.PIDController;;

/**
 *
 */
public class SimpleAutoScriptCommand extends CommandGroup {
    
	double F = 12;
	
    public  SimpleAutoScriptCommand() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

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
//    	addSequential(new TravelDistCommand(3*F));
//    	addSequential(new WaitCommand(1));
//    	addSequential(new GyroTurnCommand(90));
//    	addSequential(new WaitCommand(1));
//    	addSequential(new TravelDistCommand(.5*F));
//    	addSequential(new WaitCommand(1));
//    	addSequential(new GyroTurnCommand(-90));
//    	addSequential(new WaitCommand(1));
//    	addSequential(new TravelDistCommand(-2*F));
    	addSequential(new TurnCommand(90));
    }
}
