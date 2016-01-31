package org.usfirst.frc.team2415.robot;

public class PID {
	double p, i, d;
	private double lastError, elapsedTime;
	private long lastTime;
	
	private double integralError = 0;
	
	public PID(double p, double i, double d){
		this.p = p;
		this.i = i;
		this.d = d;
        lastTime = System.currentTimeMillis();

	}
	
	public double pidOut(double error){
    	long time = System.currentTimeMillis();
		
    	elapsedTime = (time - lastTime)/1000.0;
    	
    	double powerP = proportional(error);
    	double powerI = integral(error);
    	double powerD = derivative(error);
    	System.out.println(powerP + ",\t" + powerI + ",\t" + powerD);
    	
    	double out = powerP + powerI + powerD;
    	
    	lastError = error;
    	lastTime = time;
		return out;
	}
	
	private double proportional(double error) {
    	return p * error;
    }
    
    private double integral(double error) {
    	if(lastError == 0) return 0;
    	integralError += .5*(error+lastError)*elapsedTime;
    	return i*integralError;
    }
    
    private double derivative(double error) {
    	if(elapsedTime <= 0) return 0;
    	double diff = error-lastError;
    	return d * diff/elapsedTime;
    }
}
