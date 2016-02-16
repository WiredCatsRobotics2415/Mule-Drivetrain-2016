package org.usfirst.frc.team2415.robot.commands.autonomous;

import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class InterpolatedMoveCommand extends CommandGroup {
	private double lastX =0, lastY =0, currX, currY, xTotal = 0, yTotal = 0;
    
    public  InterpolatedMoveCommand(double xFinal, int steps) {
    	double xStep = xFinal/steps;
    	for(int i=1; i<steps+1; i++){
    		currX = i*xStep;
    		currY = getY(currX);
    		addSequential(new MoveCommand(currX-lastX, currY-lastY));
    		xTotal += currX-lastX;
    		yTotal += currY-lastY;
    		System.out.println("xChange: " + (currX-lastX) + ",\tyChange:" + (currY-lastY));
    		lastX = currX;
    		lastY = currY;
    	}
    	System.out.println("xTotal: " + xTotal + "\tyTotal: " + yTotal);
    }
    
    private double getY(double x){
    	return (DriveSubsystem.A*Math.pow(x, 3)) + (DriveSubsystem.B*Math.pow(x, 2)) +
    			(DriveSubsystem.C*x) + DriveSubsystem.D;
    }
}
