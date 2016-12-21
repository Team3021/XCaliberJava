package org.usfirst.frc.team3021.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;

public class Camera {
	//Member Attributes
	Joystick JS;
	int cam0;
	int cam1;
	int curCam;
	Image frame;
	private boolean btn_down;
	
	Camera(){
		cam0 = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		cam1 = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		curCam = cam0;
		JS = new Joystick(0);
		
		NIVision.IMAQdxConfigureGrab(cam0);
		NIVision.IMAQdxStartAcquisition(cam0);
		
		CameraServer.getInstance().setQuality(100);
		System.out.println("Camera starting.");
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	}

	void TeleOp(){
		if(JS.getRawButton(6) && !btn_down){
			btn_down = true;
			System.out.println("Switch cam\n");
			if (curCam == cam1) {
				NIVision.IMAQdxStopAcquisition(cam1);
				
				NIVision.IMAQdxConfigureGrab(cam0);
				NIVision.IMAQdxStartAcquisition(cam0);
				curCam = cam0;
			} else if (curCam == cam0) {
				NIVision.IMAQdxStopAcquisition(cam0);
				
				NIVision.IMAQdxConfigureGrab(cam1);
				NIVision.IMAQdxStartAcquisition(cam1);
				curCam = cam1;
			}
			CameraServer.getInstance().setQuality(100);
		} else if (!JS.getRawButton(6)) {
			btn_down = false;
		}
		updateCam();
	}

	void updateCam(){
    	NIVision.IMAQdxGrab(curCam, frame, 1);
    	CameraServer.getInstance().setImage(frame);
	}

}
