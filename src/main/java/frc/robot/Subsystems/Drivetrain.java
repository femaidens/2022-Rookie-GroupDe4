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
    //for two joysticks
    double ySpeed = -OI.LeftJoy.getRawAxis(1);
    double xSpeed = OI.LeftJoy.getRawAxis(0);
		double zRotation = OI.RightJoy.getRawAxis(0); //basically the zRotation 
    /*
    //for controllers
    double ySpeed = OI.driveJoy.getRawAxis(1);
    double xSpeed = OI.driveJoy.getRawAxis(0);
		double zRotation = OI.driveJoy.getRawAxis(4); //basically the zRotation 
    */

    //mecan.driveCartesian(zRotation, xSpeed, ySpeed, 0); //no gyro; no field orientation
    mecan.driveCartesian(zRotation, xSpeed, ySpeed, 0); //yes field orientation
    System.out.println("front left voltage: " + frontLeft.getBusVoltage()); //finds voltage for each wheel
    System.out.println("front right voltage: " + frontRight.getBusVoltage());
    System.out.println("rear left voltage: " + rearLeft.getBusVoltage());
    System.out.println("rear right voltage: " + rearRight.getBusVoltage());
  }
  
  /* currently not part of code :)
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
*/
  public void driveAuton(double speed){
    mecan.driveCartesian(0, speed, 0, 0);
  }
  
  public void driveStraight(double angle, double xSpeed){ 
    double minA = angle - 1;
    double maxA = angle + 1;

    while(gyro.getAngle() < minA || gyro.getAngle() > maxA){
      if(gyro.getAngle() > maxA){ //always to the right of wanted angle; want to move left
        mecan.driveCartesian(-0.05, xSpeed, 0, gyro.getAngle());
      }
      else if(gyro.getAngle() < minA){
        mecan.driveCartesian(0.05, xSpeed, 0, gyro.getAngle());
      }
      else{
        mecan.driveCartesian(0, xSpeed, 0, gyro.getAngle());
      }
    }
  }
  
  public void turnDegrees(double angle){
    double minA = angle - 1;
    double maxA = angle + 1;

    if (angle > 180) {
      angle = -1* (360 - angle);
    }
    
    while (gyro.getAngle() < minA || gyro.getAngle() > maxA) {
      if (gyro.getAngle() < minA){
        mecan.driveCartesian(0.05, 0, 0, gyro.getAngle());
        //mecan.driveCartesian(0.05, 0, 0, 0);
      }

      else if (gyro.getAngle() > maxA){
        mecan.driveCartesian(-0.05, 0, 0, gyro.getAngle());
        //mecan.driveCartesian(-0.05, 0, 0, 0);
      }
      
      else{
        mecan.driveCartesian(0, 0, 0, gyro.getAngle());
        //mecan.driveCartesian(0, 0, 0, 0);
      }
    }
  }

  public void stopAuton(){
    mecan.driveCartesian(0, 0, 0, gyro.getAngle());
    //mecan.driveCartesian(0, 0, 0, 0);
  }
}
