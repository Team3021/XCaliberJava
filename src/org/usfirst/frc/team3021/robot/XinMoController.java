package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.Joystick;

public class XinMoController {


	// Member Attributes
	Joystick Buttons;

	public XinMoController(int port){
		Buttons = new Joystick(port);
	}

	public boolean isSpinnerForward(){
		return Buttons.getRawButton(2);
	}

	public boolean isFiring(){
		return Buttons.getRawButton(3);
	}

	public boolean isAutomaticAiming(){
		return !Buttons.getRawButton(5);
	}

	public boolean isFlashlightOn(){
		return Buttons.getRawButton(7);
		
	}
	
	public boolean isFeederIn(){
		return Buttons.getRawButton(9);
	}
	
	public boolean isFeederOut(){
		return Buttons.getRawButton(10);
	}
	
	public boolean isShortShot(){
		return Buttons.getRawButton(4);
	}

	public boolean isFastShot(){
		return Buttons.getRawButton(1);
	}
	
	public boolean isLauncherEnabled(){
		return !Buttons.getRawButton(6);
	}

	
	public boolean isLauncherMovingUp(){
		if (Buttons.getRawAxis(1) > 0) {
			return true;
		}
		
		return false;
	}

	public boolean isLauncherMovingDown(){
		if (Buttons.getRawAxis(1) < 0) {
			return true;
		}
		
		return false;
	}
	
}


