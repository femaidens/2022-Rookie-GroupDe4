// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DCTestComm extends Command {
  public DCTestComm() {
    requires(Robot.dctest);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Robot.dctest.resetDCEncoder();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.dctest.spinDCMotor();
    Robot.dctest.measureTicks(); 
    //Robot.dctest.testRegEncoder();
    //System.out.println("execute");
    //Robot.dctest.dcTestMotor.set(0.3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.dctest.stopDCMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.dctest.stopDCMotor();
  }
}