// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ShooterLimelightComm extends Command {
  public ShooterLimelightComm() {
    requires(Robot.slimelight);
  }

  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    Robot.slimelight.getTXValue();
    Robot.slimelight.getTYValue();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {}

  @Override
  protected void interrupted() {}

public double getTXValue() {
    return 0;
}
}
