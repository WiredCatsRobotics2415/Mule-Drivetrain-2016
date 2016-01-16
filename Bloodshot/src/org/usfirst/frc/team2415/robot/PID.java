package org.usfirst.frc.team2415.robot;

public class PID {
	
	private float kP, kI, kD;
	private double intergralCounter = 0;
	private double previous = 0;
	
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
		intergralCounter += (desired - current);
		return kI*intergralCounter;
	}
	
	private double derivative(double current){
		double derivative = current - this.previous;
		current = this.previous;
		return kD*derivative;
	}
	
	public double getPIDOutput(double current, double desired){
		return proportional(current, desired) + intergral(current, desired) + derivative(current);
	}
}
