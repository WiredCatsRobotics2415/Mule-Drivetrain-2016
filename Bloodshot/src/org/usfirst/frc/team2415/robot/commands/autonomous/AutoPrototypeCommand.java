package org.usfirst.frc.team2415.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.PIDController;;

/**
 *
 */
public class AutoPrototypeCommand extends CommandGroup {
    
	double GEARING = 7.2;
	
    public  AutoPrototypeCommand() {
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
//    	addSequential(new TurnCommand(Math.PI/2, 0, (70/GEARING)*Math.PI*3.75 * .15));
//    	addSequential(new WaitCommand(10));
//    	System.out.println("Move 1 done");
    	addSequential(new TravelDistCommand(60));
//    	addSequential(new WaitCommand(10));
//    	System.out.println("Move 2 done");
//    	addSequential(new TurnCommand(-Math.PI/2, 0, -(70/GEARING)*Math.PI*3.75 * .15));
//    	addSequential(new WaitCommand(1));
//    	System.out.println("Move 3 done");
    }
}
