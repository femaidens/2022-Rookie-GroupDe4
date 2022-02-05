// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbHighRung extends Command {
  public ClimbHighRung() {
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    Robot.climb.increaseAngle();
    Robot.climb.spinClimbMotor();
    Robot.climb.decreaseAngle();
    Robot.climb.reverseClimbMotor();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
    Robot.climb.stopClimbMotor();
  }

  @Override
  protected void interrupted() {
    Robot.climb.stopClimbMotor();
  }
}
