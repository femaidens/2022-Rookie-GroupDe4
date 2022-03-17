// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Tankdrive extends Subsystem {

  @Override
  public void initDefaultCommand() {
  }
  
  //public static AnalogGyro gyro = new AnalogGyro(RobotMap.gyroPort);
 
  public static CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftPort, MotorType.kBrushless);
  public static CANSparkMax midLeft = new CANSparkMax(RobotMap.midLeftPort, MotorType.kBrushless);
  public static CANSparkMax rearLeft = new CANSparkMax(RobotMap.rearLeftPort, MotorType.kBrushless);
  public static CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightPort, MotorType.kBrushless);
  public static CANSparkMax midRight = new CANSparkMax(RobotMap.midRightPort, MotorType.kBrushless);
  public static CANSparkMax rearRight = new CANSparkMax(RobotMap.rearRightPort, MotorType.kBrushless);

  //pid variables
  private static final double KP = 0.25;
  private static final double KI = 0.0;
  private static final double KD = 0.0;
  public static double speed;
  private static double min_error_tx, min_error_ty = 0.1;
  private static double min_command_tx, min_command_ty = 0.0;
  static double current_error_tx, current_error_ty = 0.0;
  static double previous_error_tx, previous_error_ty = 0.0;
  static double integral_tx, integral_ty = 0.0;
  static double derivative_tx, derivative_ty = 0.0;
  static double adjust_tx, adjust_ty = 0.0;
  static double time = 0.1;
  
  public static void driveTeleop(){
    double leftJoy = -OI.tankJoy.getRawAxis(1);
    double rightJoy = OI.tankJoy.getRawAxis(5);
    frontLeft.set(leftJoy);
    midLeft.set(leftJoy);
    rearLeft.set(leftJoy);
    frontRight.set(rightJoy);
    midRight.set(rightJoy);
    rearRight.set(rightJoy);
  }

  public static void driveAuton(double rightSpeed, double leftSpeed) {
    frontLeft.set(leftSpeed);
    midLeft.set(leftSpeed);
    rearLeft.set(leftSpeed);
    frontRight.set(-rightSpeed);
    midRight.set(-rightSpeed);
    rearRight.set(-rightSpeed);
  }

  public void driveStraight(double speed){
    // for left-right alignment
    previous_error_tx = current_error_tx;
    current_error_tx = Robot.tlimelight.getTXValue();
    integral_tx += (current_error_tx + previous_error_tx)/2*(time);
    derivative_tx = (current_error_tx - previous_error_tx)/time;
    adjust_tx = KP*current_error_tx + KI*integral_tx + KD*derivative_tx;
    
    if (current_error_tx > min_error_tx){
      adjust_tx += min_command_tx;
    }
    else if (current_error_tx < -min_error_tx){
      adjust_tx -= min_command_tx;
    }
    else{
      adjust_tx += 0;
    }
    
    while(current_error_tx != 0){
      if(current_error_tx > 0){ //crosshair is facing the right direction; wants to turn left
        frontRight.set(adjust_tx);
        midRight.set(adjust_tx);
        rearRight.set(adjust_tx);
        frontLeft.set(-adjust_tx);
        midLeft.set(-adjust_tx);
        rearLeft.set(-adjust_tx);
      }
      else{ //crosshair is facing the left direction; wants to turn right
        frontRight.set(-adjust_tx);
        midRight.set(-adjust_tx);
        rearRight.set(-adjust_tx);
        frontLeft.set(adjust_tx);
        midLeft.set(adjust_tx);
        rearLeft.set(adjust_tx);
      }
    }
  }

  public void adjustDistance(){
    double goalDistance = 72.0; //in inches
    double mDistance = Robot.tlimelight.getDistance();

    //for distance alignment
    previous_error_ty = current_error_ty;
    current_error_ty = Robot.tlimelight.getTYValue();
    integral_ty += (current_error_ty + previous_error_ty)/2*(time);
    derivative_ty = (current_error_ty - previous_error_ty)/time;
    adjust_ty = KP*current_error_ty + KI*integral_ty + KD*derivative_ty;
    
    while(mDistance != goalDistance){
      if (current_error_ty > min_error_ty){
        adjust_ty += min_command_ty;
      }
      else if (current_error_ty < -min_error_ty){
        adjust_ty -= min_command_ty;
      }
      else{
        adjust_ty += 0;
      }

      if(mDistance < goalDistance){ //too close to the goal; wants to move back
        frontRight.set(-adjust_ty);
        midRight.set(-adjust_ty);
        rearRight.set(-adjust_ty);          
        frontLeft.set(-adjust_ty);
        midLeft.set(-adjust_ty);
        rearLeft.set(-adjust_ty);
      }
      else{ //too far from the goal; want to move forward     
        frontRight.set(adjust_ty);
        midRight.set(adjust_ty);
        rearRight.set(adjust_ty);
        frontLeft.set(adjust_ty);
        midLeft.set(adjust_ty);
        rearLeft.set(adjust_ty);
      }
    }
  }
  
  public void stopMotors(){
    frontLeft.set(0);
    midLeft.set(0);
    rearLeft.set(0);
    frontRight.set(0);
    midRight.set(0);
    rearRight.set(0);
  }
}
