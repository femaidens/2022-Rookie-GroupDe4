// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Commands.TankLimelightComm;

public class TankLimelight extends Subsystem {
  public static NetworkTable lmTable = NetworkTableInstance.getDefault().getTable("limelight");
  public static NetworkTableEntry tx = lmTable.getEntry("tx"); //horizontal distance from crosshair
  public static NetworkTableEntry ty = lmTable.getEntry("ty"); //vertical distance from crosshair 
  double targetOffsetAngle_Vertical = ty.getDouble(0.0);

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TankLimelightComm());
  }

  public double getTXValue(){
    System.out.println("tx value: " + tx.getDouble(0));
    return tx.getDouble(0);
  }

  public double getTYValue(){
    System.out.println("ty value: " + ty.getDouble(0));
    return ty.getDouble(0);
  }
  
  public double getDistance(){    
    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 60.0;

    // distance from the center of the Limelight lens to the floor
    double limelightHeight = 27.5;

    // distance from the target to the floor
    double goalHeight = 60.0; //change when we test 

    double angleGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleGoalRadians = angleGoalDegrees * (Math.PI / 180.0);

    //calculate distance
    double mDistanceToGoal = (goalHeight - limelightHeight)/Math.tan(angleGoalRadians);
    return mDistanceToGoal;
  }
}
