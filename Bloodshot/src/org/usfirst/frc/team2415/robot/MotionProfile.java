package org.usfirst.frc.team2415.robot;

public class MotionProfile{
	
	public double time1, time2, time3,
				vel, finalVel, vMax, accel;
	
	public MotionProfile(double displacement, double currVel, double finalVel, double vMax, double accel){
        this.finalVel = finalVel;
        this.vMax = vMax;
        this.accel = accel;

        vel = currVel;
        
    	int dir = displacement > 0 ? 1:-1;
    	
    	time1 = (dir*vMax - currVel)/(accel);
    	double timef_2 = (dir*vMax - finalVel)/(accel);
    	
    	double dist1 = time1*currVel + .5*dir*accel*Math.pow(time1, 2);
    	double distf_2 = timef_2*vMax*dir - .5*dir*accel*Math.pow(timef_2, 2);
    	double dist2_1 = displacement - (dist1 + distf_2);
    	
    	time2 = (dist2_1/(dir*vMax)) + time1;
    	time3 = timef_2 + time2;
	}
	
}