// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveAutonGroup extends CommandGroup {
  /** Add your docs here. */
  public DriveAutonGroup() {
    addSequential(new DriveStraight(0));
    addSequential(new DriveStraight(90));
  }
}
