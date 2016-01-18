package org.usfirst.frc.team2415.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFlashDrive {

	/**
	 * Creates the BufferedWriter. You have to create the variable in the class you're going to use it in however.
	 * @param filename this is the name of the file you want to create. no need to add the ".csv"
	 * @param name_of_writer this is the name of the BufferedWriter that is being created
	 */
	public void createBufferedWriter(String filename, BufferedWriter name_of_writer) {
		
		try {
    		name_of_writer = new BufferedWriter(new FileWriter(new File("/V/" + filename + ".csv")));
    	} catch (IOException e) {
    		try {
    			name_of_writer = new BufferedWriter(new FileWriter(new File("/U/" + filename + ".csv")));
    		} catch (IOException f) {
    			f.printStackTrace();
    		}
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Writes to the BufferedWriter you created.
	 * @param currentValue the current sensor output (encoder distance, gyro angle, potentiometer voltage, etc.)
	 * @param desiredValue the desired sensor output
	 * @param name_of_writer the name of the writer you wish to write to
	 */
	public void writeToFile(double currentValue, double desiredValue, BufferedWriter name_of_writer) {
		try {
			name_of_writer.write(currentValue + ",");
			name_of_writer.write(desiredValue + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Flushes and closes the BufferedWriter.
	 * @param name_of_writer the name of the BufferedWriter you wish to close.
	 */
	public void flushAndClose(BufferedWriter name_of_writer) {
		try {
    		System.out.println("I am about to flush the toilet");
			name_of_writer.flush();
			name_of_writer.close();
			System.out.println("The dookie is down the drain");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
