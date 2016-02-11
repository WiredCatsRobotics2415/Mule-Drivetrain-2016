
package org.usfirst.frc.team2415.robot;

import org.usfirst.frc.team2415.robot.Util.WiredCatGamepad;
import org.usfirst.frc.team2415.robot.commands.autonomous.AutoScriptCommand;
import org.usfirst.frc.team2415.robot.resetcommands.ResetEncodersCommand;
import org.usfirst.frc.team2415.robot.resetcommands.ResetYawCommand;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team2415.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static DriveSubsystem driveSubsystem;
	
	public static WiredCatGamepad gamepad;
	
	private AutoScriptCommand auto;
	
    public void robotInit() {
		
		driveSubsystem = new DriveSubsystem();
		
		SmartDashboard.putData(Scheduler.getInstance());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//updateStatus();
	}

    public void autonomousInit() {
    	auto = new AutoScriptCommand();
    	auto.start();
    }
    
    public void autonomousPeriodic() {
        Scheduler.getInstance().run(); 
    }

    public void teleopInit() {}

    public void disabledInit(){}
    
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void updateStatus() {
    	Robot.driveSubsystem.updateStatus();
    }
}
