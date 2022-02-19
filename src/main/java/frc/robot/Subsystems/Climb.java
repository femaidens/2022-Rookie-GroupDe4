// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//movable arms are in the front of the robot, hooks facing away from wall
//stationary arms in front of movable, but closer to the back of robot

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Ultrasonic;

public class Climb extends Subsystem {
  public DoubleSolenoid climbRightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.cLeftPiston1Port, RobotMap.cLeftPiston2Port);
  public DoubleSolenoid climbLeftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.cRightPiston1Port, RobotMap.cRightPiston2Port);
	public CANSparkMax climbLeftMotor = new CANSparkMax(RobotMap.climbLeftPort, CANSparkMax.MotorType.kBrushless);
	public CANSparkMax climbRightMotor = new CANSparkMax(RobotMap.climbRightPort, CANSparkMax.MotorType.kBrushless);
  public static Ultrasonic climbUltLeft = new Ultrasonic(RobotMap.climbUltLeftPort1, RobotMap.climbUltLeftPort2);
  public static Ultrasonic climbUltRight = new Ultrasonic(RobotMap.climbUltRightPort1, RobotMap.climbUltRightPort2);

  public double ultLeftDistance = climbUltLeft.getRangeInches();
  public double ultRightDistance = climbUltRight.getRangeInches();
  public double margin = 0.05;

  public static double distance;

  //drivetrain
  public static CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftPort, MotorType.kBrushless);
	public static CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightPort, MotorType.kBrushless);
	public static CANSparkMax rearLeft = new CANSparkMax(RobotMap.rearLeftPort, MotorType.kBrushless);
	public static CANSparkMax rearRight = new CANSparkMax(RobotMap.rearRightPort, MotorType.kBrushless);
  public static AnalogGyro gyro = new AnalogGyro(RobotMap.gyroPort);
	public static MecanumDrive mecan = new MecanumDrive (frontLeft, rearLeft, frontRight, rearRight);

  //pid variables
  private static final double KP = 0.0;
  private static final double KI = 0.0;
  private static final double KD = 0.0;
  public static double speed;
  private static double min_error = 0.1;
  private static double min_command = 0.0;
  static double current_errorLeft, current_errorRight = 0.0;
  static double previous_errorLeft, previous_errorRight = 0.0;
  static double integralLeft, integralRight = 0.0;
  static double derivativeLeft, derivativeRight = 0.0;
  static double adjustLeft, adjustRight = 0.0;
  static double time = 0.1;

  @Override
  public void initDefaultCommand() {}

  public void increaseAngle(){ 
		climbRightPiston.set(DoubleSolenoid.Value.kForward);
    climbLeftPiston.set(DoubleSolenoid.Value.kForward);
  }

	public void decreaseAngle(){ 
		climbRightPiston.set(DoubleSolenoid.Value.kReverse);
    climbLeftPiston.set(DoubleSolenoid.Value.kReverse);
	}

	public void spinClimbMotor(){
		climbLeftMotor.set(0.5);
    climbRightMotor.set(0.5);
	}

	public void reverseClimbMotor(){
		climbLeftMotor.set(-0.5);
    climbRightMotor.set(-0.5);
	}

  public void stopClimbMotor(){
    climbLeftMotor.set(0);
    climbRightMotor.set(0);
  }
  
  public void climbAutoAlign(){
    //left pid values
    previous_errorLeft = current_errorLeft;
    current_errorLeft = distance - ultLeftDistance;
    integralLeft += (current_errorLeft + previous_errorLeft)/2*(time);
    derivativeLeft = (current_errorLeft - previous_errorLeft)/time;
    adjustLeft = KP*current_errorLeft + KI*integralLeft + KD*derivativeLeft;

    //right pid values
    previous_errorRight = current_errorRight;
    current_errorRight = distance - ultRightDistance;
    integralRight += (current_errorRight + previous_errorRight)/2*(time);
    derivativeRight = (current_errorRight - previous_errorRight)/time;
    adjustRight = KP*current_errorRight + KI*integralRight + KD*derivativeRight;

    if (current_errorLeft > min_error){
      adjustLeft += min_command;
    }

    else if (current_errorLeft < -min_error){
      adjustLeft -= min_command;
    }

    else if (current_errorRight > min_error){
      adjustRight += min_command;
    }

    else if (current_errorRight < -min_error){
      adjustRight -= min_command;
    }

    while(ultLeftDistance != distance || ultRightDistance != distance){
      
      // for left side
      if(current_errorLeft > 0){
        frontLeft.set(0.25 + adjustLeft);
        rearLeft.set(0.25 + adjustLeft);
      }
      else{
        frontLeft.set(0.25 - adjustLeft);
        rearLeft.set(0.25 - adjustLeft);
      }

      // for right side
      if(current_errorRight > 0){
        frontRight.set(0.25 + adjustRight);
        rearRight.set(0.25 + adjustRight);
      }
      else{
        frontRight.set(0.25 - adjustRight);
        rearRight.set(0.25 - adjustRight);
      }
    }
    
    while(ultLeftDistance != ultRightDistance){
      if(ultLeftDistance - ultRightDistance > margin){ //left side closer to the wall; turns left
        mecan.driveCartesian(0, 0, -0.1, 0);
      }
      else if (ultLeftDistance - ultRightDistance < -margin){ //right side closer to the wall; turns right
        mecan.driveCartesian(0, 0, 0.1, 0);
      } 
    }
  }

  /*
  public void driveStraightForward(){
    if(ultLeftDistance > climbUltRight.getRangeInches()){
      mecan.driveCartesian(0, 0.5, 0.05, 0);
    }
    else{
      mecan.driveCartesian(0, 0.5, -0.05, 0);
    }      
  }

  public void driveStraightBackward(){
    if(ultLeftDistance > climbUltRight.getRangeInches()){
      mecan.driveCartesian(0, -0.5, 0.05, 0);
    }
    else{
      mecan.driveCartesian(0, -0.5, -0.05, 0);
    }   
  }

  public void oldAutoAlign(){
    // ultrasonics must be in the front of of the robot for movements to work

    Ultrasonic.setAutomaticMode(true);
    double minWallDistance = 70; //change as needed (placeholder)    
    double maxWallDistance = 70.2;

    while(climbUltLeft.getRangeInches() > maxWallDistance && climbUltRight.getRangeInches() > maxWallDistance){
      driveStraightForward();
    }

    while(climbUltLeft.getRangeInches() < minWallDistance && climbUltRight.getRangeInches() < minWallDistance){
      driveStraightBackward();
    }
  }
  // old reference code :)
  */
}
