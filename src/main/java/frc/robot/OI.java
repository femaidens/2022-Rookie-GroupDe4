// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.DCTestComm;

public class OI {
    public static Joystick DriveJoy = new Joystick(RobotMap.DriveJoyPort); //rotational movement 
    public static Button dcTestButton = new JoystickButton(DriveJoy, 1);

    public void bindButtons(){
        dcTestButton.whileHeld(new DCTestComm());
    }
}
