package org.usfirst.frc.team2415.robot.Util;

import java.util.ArrayList;
import java.util.Iterator;

public class DataAnalyzer {
	public static double mean(ArrayList<Double> vals){
    	double sum = 0;
    	for (Iterator<Double> it = vals.iterator(); it.hasNext();){
    		sum += it.next();
    	}
    	return sum/vals.size();
    }
	

    public static double stdDeviation(ArrayList<Double> vals){
    	double m = mean(vals), sum = 0;
    	for (Iterator<Double> it = vals.iterator(); it.hasNext();){
    		sum += Math.pow(it.next() - m, 2);
    	}
    	return sum/vals.size();
    }
    
    public static double stdError(ArrayList<Double> vals){
    	return stdDeviation(vals)/Math.sqrt(vals.size());
    }
    
    
}
