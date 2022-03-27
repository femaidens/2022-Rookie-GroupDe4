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
    public static Joystick opJoy = new Joystick(RobotMap.opJoyPort);
    public static JoystickButton extendArmsButton = new JoystickButton (opJoy, 2);
    public static JoystickButton retractArmsButton = new JoystickButton (opJoy, 3);
    public static JoystickButton increaseAngleButton = new JoystickButton (opJoy,4);
    public static JoystickButton decreaseAngleButton = new JoystickButton (opJoy, 1);
    public static JoystickButton climbAutonAlign = new JoystickButton (opJoy, 14); //not on controller; for driver joystick; change to drivejoysticks

    public void bindButtons(){
        extendArmsButton.whenPressed(new ExtendClimbArms());
        retractArmsButton.whileHeld(new RetractClimbArms());
        increaseAngleButton.whileHeld(new ExtendClimbPiston());
        decreaseAngleButton.whenPressed(new RetractClimbPiston());
        climbAutonAlign.whenPressed(new ClimbAutoalign());
    }
}
