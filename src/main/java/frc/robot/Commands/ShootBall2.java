// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ShootBall2 extends Command {
  public ShootBall2() {
    requires(Robot.shooter2);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.shooter2.setTicks();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.shooter2.spinDCMotor(); //shortens string
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.shooter2.stopDCMotor();
    Robot.shooter2.pushLatch();
    Robot.shooter2.retractS2Piston();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.shooter2.stopDCMotor();
    Robot.shooter2.retractS2Piston();
  }
}
