// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Subsystems.Tankdrive;

public class TankdriveTeleop extends Command {
  public TankdriveTeleop() {
    requires(Robot.tankdrive);
  }

  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    //Robot.comp.start();
    
    Tankdrive.driveTeleop();
    System.out.print("DriveTeleop running");
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {}

  @Override
  protected void interrupted() {}
}
