// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankAutoalign extends Command {
  private static final double KP = 0.1;
  private static final double KI = 0.0;
  private static final double KD = 0.0;
  public static double speed;
  private static double min_error = 0.1;
  private static double min_command = 0.0;
  static double current_error = 0.0;
  static double previous_error = 0.0;
  static double integral = 0.0;
  static double derivative = 0.0;
  static double adjust = 0.0;
  static double time = 0.1;

  public TankAutoalign(double s) {
    requires(Robot.tankdrive);
    requires(Robot.tlimelight);
    speed = s;
  }

  @Override
  protected void initialize() {}

  @Override
  protected void execute() {
    previous_error = current_error;
    current_error = Robot.tlimelight.getTXValue();
    integral += (current_error+previous_error)/2*(time);
    derivative = (current_error-previous_error)/time;
    adjust = KP*current_error + KI*integral + KD*derivative;
    
    if (current_error > min_error){
      adjust += min_command;
    }
    else if (current_error < -min_error){
      adjust -= min_command;
    }
    Robot.tankdrive.driveStraight(speed, adjust);
  }

  @Override
  protected boolean isFinished() {
    return true;
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
