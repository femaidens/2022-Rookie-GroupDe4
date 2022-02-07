// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.OI;
import frc.robot.RobotMap;

public class Drivetrain extends Subsystem {
  public static CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftPort, MotorType.kBrushless);
	public static CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightPort, MotorType.kBrushless);
	public static CANSparkMax rearLeft = new CANSparkMax(RobotMap.rearLeftPort, MotorType.kBrushless);
	public static CANSparkMax rearRight = new CANSparkMax(RobotMap.rearRightPort, MotorType.kBrushless);
  public static AnalogGyro gyro = new AnalogGyro(RobotMap.gyroPort);
	public static MecanumDrive mecan = new MecanumDrive (frontLeft, rearLeft, frontRight, rearRight);


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void driveTeleop(){
    double rightJoyY = OI.driveJoy.getRawAxis(1);
    double rightJoyX = OI.driveJoy.getRawAxis(0);
		double leftJoyX= OI.driveJoy.getRawAxis(4); //basically the zRotation 
    
    mecan.driveCartesian(rightJoyY, rightJoyX, leftJoyX, gyro.getAngle()); 	
  }
  
  public void driveStraightDistance(double distance){
    double countDistance = 0;
    while(countDistance < distance){
      mecan.driveCartesian(0, 0.5, 0, 0);
      countDistance++;
    }
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

  //here goes auton
}
