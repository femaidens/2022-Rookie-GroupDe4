// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Commands.TankLimelightComm;

/** Add your docs here. */
public class TankLimelight extends Subsystem {
  public static NetworkTable lmTable = NetworkTableInstance.getDefault().getTable("limelight");
  public static NetworkTableEntry tx = lmTable.getEntry("tx"); //horizontal distance from crosshair
  //public static NetworkTableEntry ty = lmTable.getEntry("ty"); //vertical distance from crosshair 

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TankLimelightComm());
  }

  public double getTXValue(){
    System.out.println("tx value: " + tx.getDouble(0));
    return tx.getDouble(0);
  }
}