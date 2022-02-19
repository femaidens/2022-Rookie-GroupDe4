// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Commands.ClimbAutoalign;
import frc.robot.Commands.ExtendClimbArms;
import frc.robot.Commands.ExtendClimbPiston;
import frc.robot.Commands.RetractClimbArms;
import frc.robot.Commands.RetractClimbPiston;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick driveJoy = new Joystick(RobotMap.driveJoyPort);
    public static JoystickButton extendArmsButton = new JoystickButton (driveJoy, 10);
    public static JoystickButton retractArmsButton = new JoystickButton (driveJoy, 11);
    public static JoystickButton increaseAngleButton = new JoystickButton (driveJoy,12);
    public static JoystickButton decreaseAngleButton = new JoystickButton (driveJoy, 13);
    public static JoystickButton climbAutonAlign = new JoystickButton (driveJoy, 14);

    public void bindButtons(){
        extendArmsButton.whenPressed(new ExtendClimbArms());
        retractArmsButton.whileHeld(new RetractClimbArms());
        increaseAngleButton.whileHeld(new ExtendClimbPiston());
        decreaseAngleButton.whenPressed(new RetractClimbPiston());
        climbAutonAlign.whenPressed(new ClimbAutoalign());
    }
}
