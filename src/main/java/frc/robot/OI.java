// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.MeccaLimelightComm;

public class OI {
    public static Joystick testJoy = new Joystick(2);
    public static JoystickButton mecTest = new JoystickButton(testJoy, 1);

    public void bindButton(){
        mecTest.whenPressed(new MeccaLimelightComm());
    }
}
