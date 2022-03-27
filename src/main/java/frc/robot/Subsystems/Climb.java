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
  static double current_errorLeft, current_errorRight, current_errorRotation = 0.0;
  static double previous_errorLeft, previous_errorRight, previous_errorRotation = 0.0;
  static double integralLeft, integralRight, integralRotation = 0.0;
  static double derivativeLeft, derivativeRight, derivativeRotation = 0.0;
  static double adjustLeft, adjustRight, adjustRotation = 0.0;
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
		climbLeftMotor.set(0.8);
    climbRightMotor.set(0.8);
	}

	public void reverseClimbMotor(){
		climbLeftMotor.set(-0.8);
    climbRightMotor.set(-0.8);
	}

  public void stopClimbMotor(){
    climbLeftMotor.set(0);
    climbRightMotor.set(0);
  }
  
  public void climbAutoAlign(){
    double distance = 5; //change to suit wanted distance from the wall
    double ultLeftDistance = climbUltLeft.getRangeInches();
    double ultRightDistance = climbUltRight.getRangeInches();
    double distance_margin = 0.05; //error from distance we want to be
    double difference_margin = 0.05; //error between left and right ults
    
    //left pid values
    previous_errorLeft = current_errorLeft;
    current_errorLeft = distance - ultLeftDistance; 
    //if error = negative; in front of distance
    //if error = positive; too behind distance
    integralLeft += (current_errorLeft + previous_errorLeft)/2*(time);
    derivativeLeft = (current_errorLeft - previous_errorLeft)/time;
    adjustLeft = KP*current_errorLeft + KI*integralLeft + KD*derivativeLeft;

    //right pid values
    previous_errorRight = current_errorRight;
    current_errorRight = distance - ultRightDistance;
    integralRight += (current_errorRight + previous_errorRight)/2*(time);
    derivativeRight = (current_errorRight - previous_errorRight)/time;
    adjustRight = KP*current_errorRight + KI*integralRight + KD*derivativeRight;

    //left-right pid values
    previous_errorRotation = current_errorRotation;
    current_errorRotation = ultLeftDistance - ultRightDistance;
    integralRotation += (current_errorRight + previous_errorRight)/2*(time);
    derivativeRotation = (current_errorRight - previous_errorRight)/time;
    adjustRotation = KP*current_errorRight + KI*integralRight + KD*derivativeRight;

    while(current_errorRotation != difference_margin){
      if(current_errorRotation > difference_margin){ //left side farther away from the wall; turn right
        mecan.driveCartesian(adjustRotation, 0, 0, 0);
      }
      else if (current_errorRotation < -difference_margin){ //right side farther away from the wall; turns left
        mecan.driveCartesian(-adjustRotation, 0, 0, 0);
      } 
    }

    while(current_errorLeft != distance_margin || current_errorRight != distance_margin){
      
      //for left side
      if(current_errorLeft > distance_margin){ //too far away from distance; wants to move forward
        mecan.driveCartesian(0, 0, adjustLeft, gyro.getAngle());
      }
      else{ //too in front of distance; wants to move backward
        mecan.driveCartesian(0, 0, -adjustLeft, gyro.getAngle());
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
