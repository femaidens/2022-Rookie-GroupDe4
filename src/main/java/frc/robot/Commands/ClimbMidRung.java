// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbMidRung extends Command {
  public ClimbMidRung() {
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    Robot.climb.autoAlign();
  }

  @Override
  protected void execute() {
    Robot.climb.spinClimbMotor();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
    Robot.climb.stopClimbMotor();
    Robot.climb.reverseClimbMotor();
  }

  @Override
  protected void interrupted() {
    Robot.climb.stopClimbMotor();
  }
}
