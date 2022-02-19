// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.DriveAuton;
import frc.robot.Commands.ExtendPiston;
import frc.robot.Commands.ShootBall;
import frc.robot.Commands.ShooterAutoalign;

public class AutonGroup extends CommandGroup {
  public AutonGroup() {
    addSequential(new ExtendPiston());
    addSequential(new DriveAuton(0.25));
    addSequential(new ShooterAutoalign());
    addSequential(new ShootBall());
  }
}
