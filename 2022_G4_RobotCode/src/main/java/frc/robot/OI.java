// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.Commands.ShootBall;
import frc.robot.Subsystems.UltrasonicTest;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick driveJoy = new Joystick(RobotMap.driveJoyPort);
    public static JoystickButton ultButton = new JoystickButton(driveJoy, 1);
	public static JoystickButton scoopBall = new JoystickButton(driveJoy, 5);
	public static JoystickButton noScoopBall = new JoystickButton (driveJoy, 6);
    public static JoystickButton shootBall = new JoystickButton (driveJoy, 7);

    public void bindButtons(){
        shootBall.whenPressed(new ShootBall()); //you must have new
    }
}
