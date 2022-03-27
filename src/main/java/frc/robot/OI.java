// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.CommandGroups.DriveAutonGroup;

public class OI {
    public static Joystick RightJoy = new Joystick(RobotMap.RightJoyPort); //rotational movement 
    public static Joystick LeftJoy = new Joystick(RobotMap.LeftJoyPort); //lateral movement
    public static Joystick OpJoy = new Joystick(RobotMap.OpJoyPort); //operator joystick; ex: climb, auton, shooting
    public static Button driveAuton = new JoystickButton(OpJoy, 14);

    public void bindButtons(){
        driveAuton.whenPressed(new DriveAutonGroup());
    }
}
