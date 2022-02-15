// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
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

  public static void driveTeleop(){
    double leftJoy = -OI.tankJoy.getRawAxis(0);
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

  public void driveStraight(double speed, double adjust){
    frontRight.set(speed + adjust*0.01);
    midRight.set(speed + adjust*0.01);
    rearRight.set(speed + adjust*0.01);
    frontLeft.set(-1*(speed - adjust*0.01));
    midLeft.set(-1*(speed - adjust*0.01));
    rearLeft.set(-1*(speed - adjust*0.01));
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
