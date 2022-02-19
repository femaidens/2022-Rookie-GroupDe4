// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveAuton extends Command {
  public double speed;

  public DriveAuton(double s) {
    requires(Robot.drivetrain);
    speed = s;
  }

  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    Robot.drivetrain.driveAuton(speed);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {}

  @Override
  protected void interrupted() {
    Robot.drivetrain.stopAuton();
  }
}