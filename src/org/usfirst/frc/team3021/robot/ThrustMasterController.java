package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ThrustMasterController {
	private static final int TRIGGER = 1; // The middle trigger on the back of the stick
	private static final int RBUTTON = 4; // The right button on the front of the stick
	private static final int SHOULDER = 3; // The right shoulder button on the back of the stick
	private static final int MIDDLEBUTTON = 2; // The striped middle button on the front of the stick
	
	// Member Attributes
	Joystick JS;
	
	public ThrustMasterController(int port){
		JS = new Joystick(port);
	}
	
	public double getMoveValue(){
		return JS.getY();
	}
	
	public double getTurnValue(){
		return JS.getX();
	}
	
	public boolean isHighGear(){
		return JS.getRawButton(TRIGGER);
	}
	
	public boolean isSpinnerForward(){
		return JS.getRawButton(RBUTTON);
	}
	
	public boolean isSpinnerBackward(){
		return JS.getRawButton(MIDDLEBUTTON);
	}
	
	public boolean isFiring(){
		return JS.getRawButton(SHOULDER);
	}
}

