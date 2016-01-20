package org.usfirst.frc.team2415.robot;

public class PID {
	
	private float kP, kI, kD;
	private double intergralCounter = 0;
	private double previous = 0;
	private double previousTime = 0;
	
	public PID(float p,float i, float d){
		kP = p;
		kI = i;
		kD = d;
	}
	
	public PID(float p,float i){
		this(p, i, 0);
	}
	
	public PID(float p){
		this(p, 0);
	}
	
	private double proportional(double current, double desired){
		return kP*(desired-current);
	}
	
	private double intergral(double current, double desired){
		intergralCounter += (desired - current)/((System.currentTimeMillis()-previousTime)/1000);
		previousTime = System.currentTimeMillis();
		return kI*intergralCounter;
	}
	
	private double intergralAngle(double error){
		intergralCounter += error/((System.currentTimeMillis()-previousTime)/1000);
		previousTime = System.currentTimeMillis();
		return kI*intergralCounter;
	}
	
	private double derivative(double current, double desired){
		double derivative = (desired - current) - previous;
		previous = desired - current;
		System.out.println("Desired - current: " + (desired - current));
		return kD*derivative;
	}
	
	public double getPIDOutput(double current, double desired){
		return proportional(current, desired) + intergral(current, desired) + derivative(current, desired);
	}
	
	public double getPIDAngleOutput(double current,double desired, double error){
		return kP*error + intergralAngle(error) + derivative(current, desired);
	}
}
