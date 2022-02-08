// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import com.revrobotics.RelativeEncoder;
import frc.robot.OI;
import frc.robot.RobotMap;

public class Drivetrain extends Subsystem {
  public static CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftPort, MotorType.kBrushless);
	public static CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightPort, MotorType.kBrushless);
	public static CANSparkMax rearLeft = new CANSparkMax(RobotMap.rearLeftPort, MotorType.kBrushless);
	public static CANSparkMax rearRight = new CANSparkMax(RobotMap.rearRightPort, MotorType.kBrushless);
  public static AnalogGyro gyro = new AnalogGyro(RobotMap.gyroPort);
	public static MecanumDrive mecan = new MecanumDrive (frontLeft, rearLeft, frontRight, rearRight);
  public static RelativeEncoder frontLeftEncoder = frontLeft.getEncoder();
  public static RelativeEncoder frontRightEncoder = frontRight.getEncoder();
  public static RelativeEncoder rearLeftEncoder = rearLeft.getEncoder();
  public static RelativeEncoder rearRightEncoder = rearRight.getEncoder();

  @Override
  protected void initDefaultCommand() {}

  public void driveTeleop(){
    double rightJoyY = OI.driveJoy.getRawAxis(1);
    double rightJoyX = OI.driveJoy.getRawAxis(0);
		double leftJoyX= OI.driveJoy.getRawAxis(4); //basically the zRotation 

    mecan.driveCartesian(rightJoyY, rightJoyX, leftJoyX, gyro.getAngle()); 	
  }

  public void driveStraightDistance(double distance){ //test for distance value in ticks
    double fLPos = frontLeftEncoder.getPosition();
    double fRPos = frontRightEncoder.getPosition();
    
    while(fLPos < distance || fRPos < distance){
      if(fLPos < fRPos){
        frontRight.set(0.5);
        rearLeft.set(0.5);
        frontLeft.set(-0.5);
        rearRight.set(-0.5);
      }
      else{
        frontLeft.set(0.5);
        rearRight.set(0.5);
        frontRight.set(-0.5);
        rearLeft.set(-0.5);
      }
    }
  }

  public void driveStraight(double angle){ //drives straight in any direction
    
    turnDegrees(angle);

    frontRight.set(0.5);
    rearLeft.set(0.5);
    frontLeft.set(0.5);
    rearRight.set(0.5);
  }
  
  public void turnDegrees(double angle){
    if (angle > 180) {
      angle = -1* (360 - angle);
    }
    
    while (gyro.getAngle() != angle) {
      if (gyro.getAngle() < angle){
        mecan.driveCartesian(0, 0, 0.25, gyro.getAngle());
      }

      else {
        mecan.driveCartesian(0, 0, -0.25, gyro.getAngle());
      }
    }
  }

  public void stopAuton(){
    frontRight.set(0);
    frontLeft.set(0);
    rearRight.set(0);
    rearLeft.set(0);
  }
  
}
