// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ExtendPiston extends Command {
  public ExtendPiston() {
    requires(Robot.intake);
  }

  @Override
  protected void initialize() {
    Robot.intake.scoopBallPiston();
  }

  @Override
  protected void execute() {}

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {}

  @Override
  protected void interrupted() {}
}
