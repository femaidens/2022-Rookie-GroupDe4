// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.DriveAuton;
import frc.robot.Commands.DriveAutonGroup;
import frc.robot.Commands.TankAutoalign;

public class OI {
    public static Joystick tankJoy = new Joystick(RobotMap.tankJoyPort);
    public static Button driveAuton = new JoystickButton(tankJoy, 3);
    public static Button alignDrive = new JoystickButton(tankJoy, 1);
    
    public void bindButtons(){
        //driveAuton.whenPressed(new DriveAutonGroup());
        driveAuton.whileHeld(new DriveAuton(0.1, 0.1));
        alignDrive.whenPressed(new TankAutoalign(0.05));
    }
}
