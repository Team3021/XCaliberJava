package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ThrustMasterController {
	private static final int TRIGGER = 1;
	
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
}
