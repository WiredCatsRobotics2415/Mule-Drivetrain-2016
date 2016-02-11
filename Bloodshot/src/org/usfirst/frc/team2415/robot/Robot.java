
package org.usfirst.frc.team2415.robot;

<<<<<<< HEAD
<<<<<<< HEAD
import org.usfirst.frc.team2415.robot.commands.autonomous.AutoScriptCommand;
=======
import org.usfirst.frc.team2415.robot.commands.autonomous.AdvanceAutoScriptCommand;
import org.usfirst.frc.team2415.robot.commands.autonomous.AutoSquareCommand;
>>>>>>> parent of bbf4b90... Autonomous Tested on Carpet
=======
import org.usfirst.frc.team2415.robot.commands.autonomous.AutoSquareCommand;
import org.usfirst.frc.team2415.robot.commands.autonomous.SimpleAutoScriptCommand;
>>>>>>> parent of 56c02ed... Change distance traveled in a square
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
	public static IntakeSubsystem intakeSubsystem;
	
	public static WiredCatGamepad gamepad;
	
<<<<<<< HEAD
<<<<<<< HEAD
	private AutoScriptCommand auto;
=======
	private AutoSquareCommand square;
>>>>>>> parent of bbf4b90... Autonomous Tested on Carpet
=======
	private AutoSquareCommand square;
	private SimpleAutoScriptCommand simAuto;
>>>>>>> parent of 56c02ed... Change distance traveled in a square

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		
		driveSubsystem = new DriveSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		
		SmartDashboard.putData(Scheduler.getInstance());
		
		SmartDashboard.putData("Reset Encoders", new ResetEncodersCommand());
		SmartDashboard.putData("Reset Yaw", new ResetYawCommand());
		
		
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//updateStatus();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	square = new AutoSquareCommand();
    	square.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    private boolean checkedAuto = false;
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
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
