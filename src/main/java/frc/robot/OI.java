// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ExtendPiston;
import frc.robot.Commands.GetDistance;
import frc.robot.Commands.RetractPiston;
import frc.robot.Commands.ShootBall;
import frc.robot.Commands.ShootBall2;
import frc.robot.Commands.SpinMotors;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick driveJoy = new Joystick(RobotMap.driveJoyPort);
    public static JoystickButton ultButton = new JoystickButton(driveJoy, 1);
	public static JoystickButton scoopBall = new JoystickButton(driveJoy, 5);
	public static JoystickButton noScoopBall = new JoystickButton (driveJoy, 6);
    public static JoystickButton shootBall = new JoystickButton (driveJoy, 7);
    public static JoystickButton spinMotors = new JoystickButton (driveJoy, 8);
    public static JoystickButton shooter2 = new JoystickButton (driveJoy, 9);


    public void bindButtons(){
        shootBall.whenPressed(new ShootBall()); //you must have new
        ultButton.whenPressed(new GetDistance());
        scoopBall.whenPressed(new ExtendPiston());
        noScoopBall.whenPressed(new RetractPiston());
        spinMotors.whileHeld(new SpinMotors());
        shooter2.whenPressed(new ShootBall2());
    }
}
