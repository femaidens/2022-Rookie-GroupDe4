// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankAutoalign extends Command {

  public TankAutoalign() {
    requires(Robot.tankdrive);
    requires(Robot.tlimelight);
  }

  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    Robot.tankdrive.driveStraight(0.25);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.tankdrive.stopMotors();
  }

  @Override
  protected void interrupted() {
    Robot.tankdrive.stopMotors();
  }
}
