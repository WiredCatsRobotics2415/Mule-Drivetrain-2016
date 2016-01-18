
package org.usfirst.frc.team2415.robot;

import java.io.BufferedWriter;
import java.io.IOException;

import org.usfirst.frc.team2415.robot.commands.AutoStraightDriveCommand;
import org.usfirst.frc.team2415.robot.commands.GyroAutonomousTestCommand;
import org.usfirst.frc.team2415.robot.resetcommands.ResetEncodersCommand;
import org.usfirst.frc.team2415.robot.resetcommands.ResetYawCommand;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team2415.robot.subsystems.VisionSubsystem;

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
	public static VisionSubsystem visionSubsystem;

	public static WiredCatGamepad gamepad;

	private GyroAutonomousTestCommand gyroTest;

	private IMU imu;
	
	BufferedWriter writer;
	
	public static NetworkTable visionTable = NetworkTable.getTable("GRIP/myContoursReport");

	// private Compressor compressor;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();

		

		gamepad = new WiredCatGamepad(0);
		/*
		 * compressor = new Compressor(RobotMap.PCM_ID); //Testing testing 123
		 * SmartDashboard.putBoolean("Is Compressor On?", compressor.enabled());
		 */
		driveSubsystem = new DriveSubsystem();
		visionSubsystem = new VisionSubsystem();

		gyroTest = new GyroAutonomousTestCommand();

		SmartDashboard.putData(Scheduler.getInstance());

		SmartDashboard.putData("Reset Encoders", new ResetEncodersCommand());
		SmartDashboard.putData("Reset Yaw", new ResetYawCommand());
		SmartDashboard.putData("Drive Straight This Amount", 
								new AutoStraightDriveCommand(
										(float)(SmartDashboard.getNumber("Straight Drive Distance", 0)
												)
										)
								);
		
		//Reads values from the NetworkTable for vision stuffs
		try {
			Runtime.getRuntime().exec(new String[]{"/usr/local/frc/JRE/bin/java", "-jar", "grip.jar", "towertargets.grip"});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double[] defaultValue = new double[0];
		while(SmartDashboard.getBoolean("Stream area to Riolog?", false)) {
			double[] areas = visionTable.getNumberArray("area", defaultValue);
			System.out.print("areas: ");
			for (double area : areas) {
				System.out.print(area + "");
			}
			System.out.println();
		}

		

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateStatus();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		AutoStraightDriveCommand com = new AutoStraightDriveCommand(5f);
		com.start();
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

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void updateStatus() {
		Robot.driveSubsystem.updateStatus();
		Robot.visionSubsystem.updateStatus();
		
	}
}
