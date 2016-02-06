package org.usfirst.frc.team2415.robot;

import java.util.ArrayList;
import java.util.Iterator;

public class PID {
	double p, i, d;
	private double lastError, elapsedTime;
	private long lastTime;
	
	private double integralError = 0;
	
	private double[] 	deadbandVals = {0,0},
						positiveRange = {0,0},
						negativeRange = {0,0};
	
	
	public PID(double p, double i, double d){
		this.p = p;
		this.i = i;
		this.d = d;
		
        lastTime = System.currentTimeMillis();

	}
	
	public double pidOut(double error){
		System.out.println(error);
    	long time = System.currentTimeMillis();
		
    	elapsedTime = (time - lastTime)/1000.0;
    	
    	double powerP = proportional(error);
    	double powerI = integral(error);
    	double powerD = derivative(error, time);
    	
    	double out = powerP + powerI + powerD;
    	
    	//System.out.print(out);
    	
    	if(out > 0){
    		out = (out - positiveRange[0])/(positiveRange[1] - positiveRange[0]) * 
    				(positiveRange[1] - deadbandVals[1]) + deadbandVals[1];
    	}else{
    		out = (out - negativeRange[0])/(negativeRange[1] - negativeRange[0]) * 
    				(negativeRange[1] - deadbandVals[0]) + deadbandVals[0];
    	}

    	//System.out.println(",\t" + out);
    	
    	lastError = error;
    	lastTime = time;
    	
		return out;
	}
	
	public void setDeadBandValues(double negative, double positive){
		deadbandVals[0] = negative;
		deadbandVals[1] = positive;
	}
	
	public void setOutputRange(double upper, double lower){
		positiveRange[1] = upper;
		negativeRange[1] = lower;
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
