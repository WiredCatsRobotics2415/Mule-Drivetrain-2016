package org.usfirst.frc.team2415.robot;

import java.util.ArrayList;
import java.util.Iterator;

public class PID {
	double p, i, d;
	private double lastError, elapsedTime;
	private long lastTime, derrLastTime;
	
	private double integralError = 0;
	
	private boolean derrCalced = false;
	private double lowerDB = 0;
	private double upperDB = 0;
	
	
	public PID(double p, double i, double d){
		this.p = p;
		this.i = i;
		this.d = d;
		
        lastTime = derrLastTime = System.currentTimeMillis();

	}
	
	public double pidOut(double error){
    	long time = System.currentTimeMillis();
		
    	elapsedTime = (time - lastTime)/1000.0;
    	
    	double powerP = proportional(error);
    	double powerI = integral(error);
    	double powerD = derivative(error, time);
//    	System.out.println(powerP + ",\t" + powerI + ",\t" + powerD);
    	
    	double out = powerP + powerI + powerD;
    	
    	out = (out)*(1 - ((out < 0) ? lowerDB : upperDB)) + ((out < 0) ? lowerDB : upperDB);
    	
    	lastError = error;
    	lastTime = time;
    	
		return out;
	}
	
	public void setDeadBand(double lower, double upper){
		this.lowerDB = lower;
		this.upperDB = upper;
	}
	
	private double proportional(double error) {
    	return p * error;
    }
    
    private double integral(double error) {
    	if(lastError == 0) return 0;
    	integralError += .5*(error+lastError)*elapsedTime;
    	return i*integralError;
    }
    
    private double derivative(double error, long time) {

    	if(elapsedTime <= 0) return 0;
    	double diff = error - lastError; 
    	return d * diff/elapsedTime;
    	
    }
}
