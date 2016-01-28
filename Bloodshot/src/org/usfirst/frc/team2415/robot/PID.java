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
    	
    	double out = proportional(error) + integral(error) + derivative(error);
    	
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
    	
//    	if(integral.size() < (int)Robot.driveSubsystem.getFreshRate()*INTEGRAL_TIMEFRAME)
//    		integral.add(intInstance);
//    	else{
//    		integral.remove(0);
//    		integral.add(intInstance);
//    	}
//    	
//    	double sum = 0;
//    	for(int i=0; i<integral.size(); i++)
//  		sum += integral.get(i);
    	return i*integralError;
    }
    
    private double derivative(double error) {
    	
    	double diff = error-lastError;
    	return d * diff/elapsedTime;
    }
}
