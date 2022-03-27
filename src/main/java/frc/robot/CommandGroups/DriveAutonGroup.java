// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.DriveAuton;
import frc.robot.Commands.DriveStraight;

public class DriveAutonGroup extends CommandGroup {
  /** Add your docs here. */
  public DriveAutonGroup() {
    addSequential(new DriveAuton(0.5)); // argument value can be any speed; placeholder for now
    addSequential(new DriveStraight(90, 0.3)); // argument value can be any angle; placeholder for now
  }
}
