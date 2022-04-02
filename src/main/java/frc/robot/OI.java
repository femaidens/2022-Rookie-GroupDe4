// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.LowerIntake;
//import frc.robot.Commands.ExtendIntake;
import frc.robot.Commands.RetractIntake;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick opJoy = new Joystick(RobotMap.opJoyPort);
	//public static JoystickButton extendIntakeButton = new JoystickButton(opJoy, 5);
	public static JoystickButton retractIntakeButton = new JoystickButton (opJoy, 9); //button number to be changed 
    public static JoystickButton lowerIntakeButton = new JoystickButton (opJoy, 5);



    public void bindButtons(){ //may need to be flipped?
       //extendIntakeButton.whileHeld(new ExtendIntake());
        retractIntakeButton.whenPressed(new RetractIntake());
        lowerIntakeButton.whenPressed(new LowerIntake());
    }
}
