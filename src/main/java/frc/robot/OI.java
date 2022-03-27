// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import frc.robot.Commands.ExtendIntake;
import frc.robot.Commands.RetractIntake;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick driveJoy = new Joystick(RobotMap.driveJoyPort);
	//public static JoystickButton extendIntakeButton = new JoystickButton(driveJoy, 5);
	public static JoystickButton retractIntakeButton = new JoystickButton (driveJoy, 5);


    public void bindButtons(){ //may need to be flipped?
       //extendIntakeButton.whileHeld(new ExtendIntake());
        retractIntakeButton.whenPressed(new RetractIntake());
    }
}
