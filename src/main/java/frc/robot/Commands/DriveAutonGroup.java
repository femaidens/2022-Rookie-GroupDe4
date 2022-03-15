// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveAutonGroup extends CommandGroup {
  public DriveAutonGroup() {
    addSequential(new DriveAuton(0.1, 0.1));
    addSequential(new TankAutoalign());
  }
}
