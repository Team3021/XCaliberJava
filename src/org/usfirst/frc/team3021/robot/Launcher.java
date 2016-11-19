package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Launcher {
	//Member Attributes
	double  shortShot = 13.91; //"14.81 is golden" -Shivang, increase by 2 degrees
	double longShot = 11.56;
	double autoShot = 20;
	CANTalon RightRear;
	CANTalon RightFront;
	CANTalon LeftRear;
	CANTalon LeftFront;

	Solenoid lowBar;
	Solenoid GearShifter;
	Joystick Buttons;

	RobotDrive SpeedBase;
	Encoder leftEnc;
	Encoder rightEnc;
	Timer StopWatch;
	Joystick JS;
	AnalogGyro navX;
	double distance;
	double circum;
	boolean Shift;
	
	boolean fire;
	boolean test;
	CANTalon launchWheel;
	Talon lAct;
	AnalogPotentiometer lFeedback;
	Solenoid puncher;
	Relay spike;

	Timer launchStopwatch;
	
	public Launcher() {
		fire = false;
		Buttons = new Joystick(1);
		JS = new Joystick(0);
		launchWheel = new CANTalon(5);
		lAct = new Talon(1);
		//old code  32.4 degrees
		//NEW BOT 30.7
		lFeedback = new AnalogPotentiometer(0, 30.7,0.0); //second number full range with base (0 degrees) at lowest point
		puncher = new Solenoid(2); //In competition it is 2
		spike = new Relay(0);
		launchStopwatch = new Timer();
	}
	
	void Auto() throws InterruptedException {

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




		//while(lFeedback.get() != autoShot){
			//lAct.set(1.0 * (lFeedback.get() - autoShot));
			//Thread.sleep(1);
						//System.out.println("You have entered the second if=under\n\n");

			//if(XCaliber.overArching < 6){
			//System.out.printf("%f\n\n",XCaliber.overArching);
			//launchWheel.set(0.85);
			//Thread.sleep(1);
	//unique //speed

			//}else{

				//launchWheel.set(0);
				//Thread.sleep(1);

			//}

			//if(XCaliber.overArching > 3 && XCaliber.overArching <=5 ){
				//puncher.set(true);
				//Thread.sleep(1);
				//System.out.println("Hey the puncher activated");

			//}else{
				//puncher.set(false);
				//	}

		}
		}



		//extern bool AutoCondition;
		/*		System.out.printf("AUTOCONDITIONAL: %d\n", AutoCondition);
				if(!AutoCondition){
					launchWheel.set(-1);
				}else{
					System.out.println("puncher true");
					puncher.set(true);
					Wait(1);
					launchWheel.set(0);
					puncher.set(false);
			}
//					else{
//					launchWheel.set(0);
//					puncher.set(false);
//				}*/
	//}

	void Feeder(){
		if(Buttons.getRawButton(9)){
			spike.set(Relay.Value.kReverse);

		}else if(Buttons.getRawButton(10)){
			spike.set(Relay.Value.kForward);
		}else{
			spike.set(Relay.Value.kOff);
		}


	}

	void Act(){
		lAct.set(Buttons.getRawAxis(1));
		//System.out.printf("Short Shot Value: %lf\n\n" + lFeedback.get()); //Use to find angle
		if(Buttons.getRawButton(2)){
			launchWheel.set(LauncherSpeed());
			if(Buttons.getRawButton(4) && lFeedback.get() != shortShot){
				lAct.set(1.0 * (lFeedback.get() - shortShot));
				//System.out.println("You have entered the second if=under\n\n");
			}else if(!Buttons.getRawButton(4) && lFeedback.get() != longShot){
				lAct.set(1.0 * (lFeedback.get() - longShot));

			}else{
				lAct.set(0);
				//System.out.println("You have entered the else of the second\n\n");

			}

		}


	}

	double LauncherSpeed(){
		if(Buttons.getRawButton(1)){
				return 1;  // .95

			}else{
				return 0.75;
			}
	}



	public void TeleOp() throws InterruptedException{ //CHANGE FOR COMPETITION; REVERSE POLARITY OF MOTOR VALUES SO IT SHOOTS RIGHT WAY

		fire=JS.getRawButton(2);
		if(fire==true){
			System.out.println("Button 2 pressed on joystick!");
		}

		if(!(Buttons.getRawButton(6))){
			Act();
		}

		Feeder();

		//System.out.printf("L feedback: %f\n\n", lFeedback.get()); //yellow & white are ground and 5V; blue is signal

		/*if(Buttons.getRawButton(4)){

			if(Buttons.getRawButton(1)){
				launchWheel.set(1);
			}else{
				launchWheel.set(0.85);
			}

			if(fire){
				puncher.set(true);
			}else{
				puncher.set(false);
			}


		}else*/

		if(JS.getRawButton(2)) {
			launchWheel.set(0.5);
			Thread.sleep(1);
			System.out.println("Button 2 pressed on Joystick!");
		}else if(JS.getRawButton(3) && JS.getRawButton(4)){
			puncher.set(true);
			Thread.sleep(1);
			System.out.println("Buttons 3 and 4 pressed on Joystick!");
		}else if(JS.getRawButton(4)){
			launchWheel.set(-0.9);
			Thread.sleep(1);
			System.out.println("Button 4 pressed on Joystick!");
		}else if(Buttons.getRawButton(3) && Buttons.getRawButton(2)){
			puncher.set(true);
			Thread.sleep(1);
			System.out.println("Buttons 2 and 3 pressed on panel!");
		}else if(!fire && !Buttons.getRawButton(2)){
			launchWheel.set(0);
			Thread.sleep(1);
			puncher.set(false);
			Thread.sleep(1);
			launchStopwatch.reset();


		}
	}
}
