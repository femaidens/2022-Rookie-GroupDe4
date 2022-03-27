// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;
 
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.Commands.ShooterLimelightComm;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
 
public class ShooterLimelight extends Subsystem {
  public static NetworkTable lmTable = NetworkTableInstance.getDefault().getTable("limelight");
  public static NetworkTableEntry tx = lmTable.getEntry("tx"); //horizontal distance from crosshair
  public static NetworkTableEntry ty = lmTable.getEntry("ty"); //vertical distance from crosshair 
 
  private static final double KP = 0.1;
  private static final double KI = 0.0;
  private static final double KD = 0.0;
  public static double speed;
  private static double min_error = 0.1;
  private static double min_command = 0.0;
  static double current_error = 0.0;
  static double current_errorTY = 0.0;
  static double previous_error = 0.0;
  static double integral = 0.0;
  static double derivative = 0.0;
  static double adjust = 0.0;
  static double time = 0.1;
 
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterLimelightComm());
  }

  public double getTXValue(){
    System.out.println("tx value: " + tx.getDouble(0));
    return tx.getDouble(0);
  }

  public double getTYValue(){
    System.out.println("ty vlaue: " + ty.getDouble(0));
    return ty.getDouble(0);
  }
 
  public void shooterAlign(){
    previous_error = current_error;
    current_error = 0 - Robot.slimelight.getTXValue();
    current_errorTY = 0 - Robot.slimelight.getTYValue();
    integral += (current_error+previous_error)/2*(time);
    derivative = (current_error-previous_error)/time;
    adjust = KP*current_error + KI*integral + KD*derivative;
   
    if (current_error > min_error){
      adjust += min_command;
    }
 
    else if (current_error < -min_error){
      adjust -= min_command;
    }
 
    while(current_error != 0){
      if(current_error > 0){ //crosshair is facing the left direction; wants to turn right
        Shooter2.mecan.driveCartesian(0, 0, 0.1 + adjust, 0);
      }
      else{ //crosshair is facing the right direction; wants to turn left
        Shooter2.mecan.driveCartesian(0, 0, 0.1 - adjust, 0);
      }
    while(current_errorTY != 0){
      if(current_errorTY > 0){
        Shooter2.mecan.driveCartesian(0.1 - adjust, 0, 0, 0);
      }
      else{
        Shooter2.mecan.driveCartesian(0.1 + adjust, 0, 0, 0);
      }
    }
    }
  }
}

