package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class XCaliber extends IterativeRobot {
	// Member Attributes
	static int AutoMode;
	static double overArching;
	static boolean autoCondition;
	Relay flashlight;
	Timer StopWatch; //TODO: Revise this attribute; it currently sets a null value with no initialization
	Joystick Buttons; //TODO: Revise this as well
	Compressor myCompressor;
	Drive myDrive;
	Launcher myLauncher;
	// TODO: To be implemented with their respective classes
	/**
	Lifter myLifter;
	Camera myCamera;
	*/
	
	// Porting Complete!
	public XCaliber() {
		super();
		Buttons = new Joystick(1);
		StopWatch = new Timer();
		autoCondition = false;
		myDrive = new Drive();
		// TODO: To be implemented with their respective classes
    	myLauncher = new Launcher();
//    	//myLifter = new Lifter();
//    	//myCamera = new Camera();
    	myCompressor = new Compressor();
    	flashlight = new Relay(1, Relay.Direction.kForward);
	}
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	// Porting Complete!
    public void robotInit() {
    	AutoMode=0;
    	// The code from initialize() in the c++ code has been moved to the constructor; XCaliber().
    	myCompressor.start();
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    
    // Porting Complete!
    public void autonomousInit() {
    	StopWatch.reset();
    	StopWatch.start();
    	if (SmartDashboard.getBoolean("DB/Button 1", false)) {
    		AutoMode = 1;
    		SmartDashboard.putString("DB/String 0", "Auto Mode 1");
    	}
    	else if(SmartDashboard.getBoolean("DB/Button 2", false)) {
    		AutoMode = 2;
    		SmartDashboard.putString("DB/String 0", "Auto Mode 2");
    	}
    	else if(SmartDashboard.getBoolean("DB/Button 3", false)) {
    		AutoMode = 3;
    		SmartDashboard.putString("DB/String 0", "Auto Mode 3");
    	}
    	else {
    		AutoMode = 0;
    		SmartDashboard.putString("DB/String 0", "Auto Mode 0");
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    
    // Porting complete--except for the launcher and camera methods.
    public void autonomousPeriodic() {
    	overArching = StopWatch.get();
    	// As explained by Mr. Mazur, this statement is not required in IterativeRobot.
    	//Thread.sleep(1);
    	myDrive.auto();
    	System.out.printf("Total overarching: ", overArching);
    	// Not yet implemented; TODO: Implement Launcher and Drive[COMPLETE!]
    	//myLauncher.auto();

    }
    
    // Porting Complete! (That was easy.)
    @Override
    public void teleopInit() {
    	// Does nothing in the C++ robot code.
    }
    
    /**
     * This function is called periodically during operator control
     */
    
    //Porting Complete--except for implementation myCamera and Lifter.
    public void teleopPeriodic() {
    	myDrive.teleOp();
    	System.out.println(flashlight.get());
    	// Controls the flashlight. Reads the middle switch at the top of the control panel.
    	if(Buttons.getRawButton(7)) {
    		flashlight.set(Relay.Value.kForward);
    	}
    	else {
    		flashlight.set(Relay.Value.kOff);
    	}
    	
    	try {
			myLauncher.TeleOp();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// Not yet implemented; TODO: Implement Launcher and Camera
    	//myCamera.teleOp(); // Not used in C++ code.
    	//myLifter.teleOp(); // Not used in C++ code.
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	// Not a method in the C++ code.
    }
    
}
