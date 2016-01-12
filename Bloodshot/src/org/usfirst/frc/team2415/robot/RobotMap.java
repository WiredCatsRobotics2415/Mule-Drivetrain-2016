package org.usfirst.frc.team2415.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int LEFT_TALON_ZERO = 1;
    public static final int LEFT_TALON_ONE = 2;
    public static final int RIGHT_TALON_ZERO = 0;
    public static final int RIGHT_TALON_ONE = 3;
    
    public static final int[] RIGHT_ENCODER = {2,3};
    public static final int[] LEFT_ENCODER = {0,1};
    
    public static final int PCM_ID = 20;
    
}
