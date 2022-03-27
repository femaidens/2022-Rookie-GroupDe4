// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ShooterAutoalign;
import frc.robot.Commands.ShooterReload;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick opJoy = new Joystick(RobotMap.opJoyPort);
	public static JoystickButton alignShooter = new JoystickButton(opJoy, 6);
    public static JoystickButton reloadShooter = new JoystickButton(opJoy, 7);

    public void bindButtons(){
        alignShooter.whenPressed(new ShooterAutoalign());
        reloadShooter.whenPressed(new ShooterReload());
    }
}
