// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//four pistons
package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;

/** Add your docs here. */
public class Shooter extends Subsystem {
  public static Solenoid leftPiston = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.leftPistonPort);
  public static Solenoid rightPiston = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.rightPistonPort);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void extendShooterPiston(){
    leftPiston.set(true);
    rightPiston.set(true);
  }

  public void retractShooterPiston(){
    leftPiston.set(false);
    rightPiston.set(false);
  }
}
