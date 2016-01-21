
package org.usfirst.frc.team2415.robot;

import java.io.BufferedWriter;
import java.io.IOException;

import org.usfirst.frc.team2415.robot.commands.AutoStraightDriveCommand;
import org.usfirst.frc.team2415.robot.commands.AutoTurnCommand;
import org.usfirst.frc.team2415.robot.commands.GyroAutonomousTestCommand;
import org.usfirst.frc.team2415.robot.resetcommands.ResetEncodersCommand;
import org.usfirst.frc.team2415.robot.resetcommands.ResetYawCommand;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import com.kauailabs.nav6.frc.IMU;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

	public static DriveSubsystem driveSubsystem;

	public static WiredCatGamepad gamepad;

	private GyroAutonomousTestCommand gyroTest;

	private IMU imu;
	
	private final static String[] GRIP_ARGS = new String[] {
        "/usr/local/frc/JRE/bin/java", "-jar",
        "/home/lvuser/grip.jar", "/home/lvuser/project.grip" };
	
    private final NetworkTable grip = NetworkTable.getTable("grip");
	

	// private Compressor compressor;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();

		try {
            Runtime.getRuntime().exec(GRIP_ARGS);
        } catch (IOException e) {
            e.printStackTrace();
        }

		gamepad = new WiredCatGamepad(0);
		/*
		 * compressor = new Compressor(RobotMap.PCM_ID); //Testing testing 123
		 * SmartDashboard.putBoolean("Is Compressor On?", compressor.enabled());
		 */
		driveSubsystem = new DriveSubsystem();

		gyroTest = new GyroAutonomousTestCommand();

		SmartDashboard.putData(Scheduler.getInstance());

		SmartDashboard.putData("Reset Encoders", new ResetEncodersCommand());
		SmartDashboard.putData("Reset Yaw", new ResetYawCommand());
		
		SmartDashboard.putData("AutoTurnCommand", new AutoTurnCommand(90f));
		SmartDashboard.putData("AutoStraightDriveCommand", new AutoStraightDriveCommand(5f));
		
		Robot.driveSubsystem.debugAutoStraightDriveCommand = SmartDashboard.getBoolean("Write AutoStraightDrive to Flashdrive?", false);
		

		

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateStatus();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		Robot.driveSubsystem.resetEncoders();
	}

	/**
	 * This function is called periodically during autonomous
	 */
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
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		updateStatus();
		for (double area : grip.getNumberArray("targets/area", new double[0])) {
            System.out.println("Got contour with area=" + area);
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void updateStatus() {
		Robot.driveSubsystem.updateStatus();
		
	}
}
