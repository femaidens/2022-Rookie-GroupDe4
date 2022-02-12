// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Commands.LimelightTest;

public class Limelight extends Subsystem {
  public static NetworkTable lmTable = NetworkTableInstance.getDefault().getTable("limelight");
  public static NetworkTableEntry tx = lmTable.getEntry("tx"); //horizontal distance from crosshair
  public static NetworkTableEntry ty = lmTable.getEntry("ty"); //vertical distance from crosshair 

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new LimelightTest());
  }

  public void getCrosshairDis(){
    System.out.println("tx value: " + tx.getDouble(0) + "\n" + "ty value: " + ty.getDouble(0));
  }
}
