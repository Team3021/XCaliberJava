package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Launcher {
	//Member Attributes
	double  shortShot = 13.91; //"14.81 is golden" -Shivang, increase by 2 degrees
	double longShot = 11.56;
	double autoShot = 20;

	XinMoController Buttons;
	ThrustMasterController JS;
		
	CANTalon launchWheel;
	Talon lAct;
	AnalogPotentiometer lFeedback;
	Solenoid puncher;
	Relay spike;

	Timer launchStopwatch;
	
	public Launcher() {
		Buttons = new XinMoController(1);
		JS = new ThrustMasterController(0);
		launchWheel = new CANTalon(5);
		lAct = new Talon(1);
		//old code  32.4 degrees
		//NEW BOT 30.7
		lFeedback = new AnalogPotentiometer(0, 30.7,0.0); //second number full range with base (0 degrees) at lowest point
		puncher = new Solenoid(2); //In competition it is 2
		spike = new Relay(0);
		launchStopwatch = new Timer();
	}
	
	
	public void autonomous() throws InterruptedException {
		switch(XCaliber.AutoMode){
		case 2:
			if(XCaliber.overArching < 4){
				lAct.set(1.0 * (lFeedback.get() - autoShot));
				Thread.sleep(1);

			}
			else if(XCaliber.overArching >= 4 && XCaliber.overArching <5){

				launchWheel.set(0.8);
				Thread.sleep(1);

			}
			else if(XCaliber.overArching >= 5 && XCaliber.overArching <8){

				puncher.set(true);
				Thread.sleep(1);


			}
			else {
				launchWheel.set(0);
				Thread.sleep(1);
				puncher.set(false);

			}

			break;
		}
	}

	
	private double getLauncherSpeed(){
		if (Buttons.isFastShot()) {
			return -0.9;  // .95

		}
		else {
			return -0.75;
		}
	}

	
	private void runFeeder(){
		if (Buttons.isFeederIn()) {
			spike.set(Relay.Value.kReverse);

		}
		else if (Buttons.isFeederOut()) {
			spike.set(Relay.Value.kForward);
		}
		else {
			spike.set(Relay.Value.kOff);
		}


	}

	
	private void adjustLauncherAngle(){
		// Sets launch angle if manual aiming is disabled.
		
		if (Buttons.isLauncherMovingUp()) {
			lAct.set(1);
		} 
		else if (Buttons.isLauncherMovingDown()) {
			lAct.set(-1);
		} 
		else {
			lAct.set(0);
		}
		
		if(Buttons.isSpinnerForward()){
			
			if(Buttons.isShortShot() && lFeedback.get() != shortShot && Buttons.isAutomaticAiming()){
				lAct.set(1.0 * (lFeedback.get() - shortShot));
			
			}else if(!Buttons.isShortShot() && lFeedback.get() != longShot && Buttons.isAutomaticAiming()){
				lAct.set(1.0 * (lFeedback.get() - longShot));

			}

		}


	}

	
	private void runSpinner() {
		if (JS.isSpinnerBackward()) {
			launchWheel.set(0.5);
		}
		else if (JS.isSpinnerForward()) {
			launchWheel.set(getLauncherSpeed());
		}
		else if (Buttons.isSpinnerForward() && Buttons.isLauncherEnabled()) {
			launchWheel.set(getLauncherSpeed());
		}
		else {
			launchWheel.set(0);
		}
	}

	
	private void shoot() {
		if (JS.isSpinnerForward() && JS.isFiring()){
			puncher.set(true);
		}
		else if (Buttons.isSpinnerForward() && Buttons.isFiring() && Buttons.isLauncherEnabled()) {
			puncher.set(true);
		}
		else {
			puncher.set(false);
		}
	}

	
	public void teleOp() throws InterruptedException{ //CHANGE FOR COMPETITION; REVERSE POLARITY OF MOTOR VALUES SO IT SHOOTS RIGHT WAY
		runFeeder();
		
		adjustLauncherAngle();

		runSpinner();
		
		shoot();
	}

}
