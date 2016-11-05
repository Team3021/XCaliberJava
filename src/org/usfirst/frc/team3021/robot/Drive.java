package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Drive {
	// Member Attributes--next several blocks
	private final int diameter = 4;
	private final double pi = 3.14159265359;
	
	private CANTalon RightRear;
	private CANTalon RightFront;
	private CANTalon LeftRear;
	private CANTalon LeftFront;

	private Solenoid lowBar;
	private Solenoid GearShifter;
	private Joystick Buttons;

	private RobotDrive SpeedBase;
	private Encoder leftEnc;
	private Encoder rightEnc;
	private Timer StopWatch;
	private Joystick JS;
	private AnalogGyro navX;
	private double distance;
	private double circum;
	private boolean Shift;
	
	//TODO: Please refer to the C++ code about Wait/Thread.sleep method calls. Some calls use a value less than one millisecond.
	
	public Drive(){
		JS = new Joystick(0);
		Buttons = new Joystick(1);

		lowBar = new Solenoid(1);
		LeftFront = new CANTalon(2);
		LeftRear = new CANTalon(4);
		RightFront = new CANTalon(1);
		RightRear = new CANTalon(3);
		GearShifter = new Solenoid(0);

		SpeedBase=new RobotDrive(LeftFront, LeftRear, RightFront, RightRear);

		SpeedBase.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		SpeedBase.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		SpeedBase.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		SpeedBase.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		// Commented out in the C++ code
		//navX = new AnalogGyro(0);
		//navX ->Calibrate();

	    StopWatch = new Timer();

	    distance=0;
	    Shift = false;
	    rightEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	    leftEnc = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	    //rightEnc->SetMaxPeriod(0.1);
		rightEnc.setMinRate(10);
		circum = diameter * pi;
		
		rightEnc.setDistancePerPulse(circum / 256);
		//rightEnc.setMaxPeriod(0.1);
		rightEnc.setMinRate(10);
		circum = diameter * pi;


		//leftEnc.setMaxPeriod(0.1);
		leftEnc.setMinRate(10);
		leftEnc.setDistancePerPulse(circum / 256);

	}

	public void auto() {
		switch(XCaliber.AutoMode){
		case 1:
			//while(abs(rightEnc->Get()) <= 256){
			//rightEnc->Reset();
			//printf("OVERARCHING: %f\n\n", overArching);
			//SpeedBase->SetLeftRightMotorOutputs(0.3,0.3);
			if(XCaliber.overArching < 5.0){
				LeftFront.set(-0.75);
				LeftRear.set(-0.75);
				RightFront.set(0.75);
				RightRear.set(0.75);
			}
			else if(XCaliber.overArching >= 5.0){
				LeftFront.set(0);
				LeftRear.set(0);
				RightFront.set(0);
				RightRear.set(0);
				//AutoCondition=true;
			}
			//using CANTalons allowed for aspeed control signature allowing me to use Set() still with Cantalons
			//Huh? --N ^
			//SpeedBase->SetLeftRightMotorOutputs(0.8,0.85);
			//distance = rightEnc -> Count();
			//printf("distance: %f\n", rightEnc->Get());

			//}
			//SpeedBase->SetLeftRightMotorOutputs(0,0);
			//Wait(100);

			break;
			/*	case 2:
				if(overArching < 5){
				AutoCondition= true;
				LeftFront.set(0.0);
				LeftRear.set(0.0);
				RightFront.set(0.0);
				RightRear.set(0.0);
				Wait(1);
			}else if(overArching >= 5 && overArching <9){
				AutoCondition=false;
				LeftFront.set(0.8);
				LeftRear.set(0.8);
				RightFront.set(-0.2);
				RightRear.set(-0.2);
				Wait(0.1);
				AutoCondition=true;
			}
			else if(overArching >= 9 && overArching <= 11){
				LeftFront.set(0.71);
				LeftRear.set(0.71);
				RightFront.set(-0.71);
				RightRear.set(-0.71);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AutoCondition=false;
			}
			else {
				LeftFront->Set(0);
				LeftRear->Set(0);
				RightFront->Set(0);
				RightRear->Set(0);
				Wait(0.0001);
			}
			break;*/
		case 3:
			if(XCaliber.overArching < 3.0) {
				LeftFront.set(1.0);
				LeftRear.set(1.0);
				RightFront.set(-1.0);
				RightRear.set(-1.0);
			}
			else if(XCaliber.overArching >= 3.0) {
				LeftFront.set(0);
				LeftRear.set(0);
				RightFront.set(0);
				RightRear.set(0);
			}
			break;
		case 4:
			if(XCaliber.overArching < 2.5){
				LeftFront.set(1.0);
				LeftRear.set(1.0);
				RightFront.set(-1.0);
				RightRear.set(-1.0);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(XCaliber.overArching >= 2.5 && XCaliber.overArching <9){
				LeftFront.set(0);
				LeftRear.set(0);
				RightFront.set(0);
				RightRear.set(0);
				//In the C++ code, this statement waited for 1 ten-thousandth of a second (0.0001 seconds). 
				//Unfortunately, this is not possible in Java.
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				XCaliber.autoCondition=true;
			}else if(XCaliber.overArching >= 9 && XCaliber.overArching <= 11){
				LeftFront.set(-1);
				LeftRear.set(-1);
				RightFront.set(1);
				RightRear.set(1);
				//In the C++ code, this statement waited for 1 ten-thousandth of a second (0.0001 seconds). 
				//Unfortunately, this is not possible in Java.
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				XCaliber.autoCondition=false;
			}
			else {
				LeftFront.set(0);
				LeftRear.set(0);
				RightFront.set(0);
				RightRear.set(0);
				//In the C++ code, this statement waited for 1 ten thousandth of a second (0.0001 seconds). 
				//Unfortunately, this is not possible in Java.
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;

		default:
			System.out.printf("No Autonomous Chosen");
			break;
		}
	}
	
	/*public void AutoPeriodic(){
		System.out.printf("%i\n", rightEnc->GetRaw());
		if(abs(rightEnc.getRaw()) > 256) {
			//SpeedBase.setLeftRightMotorOutputs(0,0);
			LeftFront.set(0);
			LeftRear.set(0);
			RightFront.set(0);
			RightRear.set(0);
		}
	}*/
	
	public void obstacleOne(){
		if(Buttons.getRawButton(4)){
			lowBar.set(true);
		}

	}

	public void teleOp(){
		SpeedBase.arcadeDrive(JS, true);
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Shift = JS.getRawButton(1);	// Shift - safety button
		if (Shift) {
			// Engage pneumatic shifter
			GearShifter.set(true);
			System.out.printf("shift true\n");
		}
		else {
			// Disengage pnuematic shifter
			GearShifter.set(false);
		}

	}

}
