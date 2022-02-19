// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ShooterAutoalign extends Command {
  public ShooterAutoalign() {
    requires(Robot.shooter);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    Robot.shooter.shooterAlign();
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
