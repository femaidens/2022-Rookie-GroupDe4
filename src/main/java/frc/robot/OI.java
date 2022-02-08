// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.DriveAutonGroup;

public class OI {
    public static Joystick driveJoy = new Joystick(RobotMap.driveJoyPort);
    public static Button driveAuton = new JoystickButton(driveJoy, 14);

    public void bindButtons(){
        driveAuton.whenPressed(new DriveAutonGroup());
    }
}
