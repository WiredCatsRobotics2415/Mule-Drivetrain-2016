package org.usfirst.frc.team2415.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveCommand extends CommandGroup {
	
	private final double F = 12;
	double x, y, thetaT, thetaF, distance;
	
	public MoveCommand(double x, double y){
		this.x = x*F;
    	this.y = y*F;
    	distance = Math.sqrt(this.x*this.x + this.y*this.y);
    	thetaT = -Math.toDegrees(Math.atan2(this.x, this.y));
    	
    	addSequential(new GyroTurnCommand(thetaT));
    	addSequential(new TravelDistCommand(distance));

	}
	
    public MoveCommand(double x, double y, double theta) {
    	this.x = x*F;
    	this.y = y*F;
    	distance = Math.sqrt(this.x*this.x + this.y*this.y);
    	thetaT = -Math.toDegrees(Math.atan2(this.x, this.y));
    	thetaF = theta - thetaT;
    	
    	addSequential(new GyroTurnCommand(thetaT));
    	addSequential(new TravelDistCommand(distance));
    	addSequential(new GyroTurnCommand(thetaF));
    }
}
