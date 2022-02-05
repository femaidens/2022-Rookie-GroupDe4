// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//movable arms are in the front of the robot, hooks facing away from wall
//stationary arms in front of movable, but closer to the back of robot

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Ultrasonic;

public class Climb extends Subsystem {
  public DoubleSolenoid climbRightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.cLeftPiston1Port, RobotMap.cLeftPiston2Port);
  public DoubleSolenoid climbLeftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.cRightPiston1Port, RobotMap.cRightPiston2Port);
	public CANSparkMax climbLeftMotor = new CANSparkMax(RobotMap.climbLeftPort, CANSparkMax.MotorType.kBrushless);
	public CANSparkMax climbRightMotor = new CANSparkMax(RobotMap.climbRightPort, CANSparkMax.MotorType.kBrushless);
  public static Ultrasonic climbUltLeft = new Ultrasonic(RobotMap.climbUltLeftPort1, RobotMap.climbUltLeftPort2);
  public static Ultrasonic climbUltRight = new Ultrasonic(RobotMap.climbUltRightPort1, RobotMap.climbUltRightPort2);

  double ultLeftDistance = climbUltLeft.getRangeInches() + 0.05;

  @Override
  public void initDefaultCommand() {}

  public void driveStraightForward(){
    if(ultLeftDistance > climbUltRight.getRangeInches()){
      Drivetrain.mecan.driveCartesian(0, 0.5, 0.05, 0);
    }
    else{
      Drivetrain.mecan.driveCartesian(0, 0.5, -0.05, 0);
    }      
  }

  public void driveStraightBackward(){
    if(ultLeftDistance > climbUltRight.getRangeInches()){
      Drivetrain.mecan.driveCartesian(0, -0.5, 0.05, 0);
    }
    else{
      Drivetrain.mecan.driveCartesian(0, -0.5, -0.05, 0);
    }   
  }
 
  public void autoAlign(){
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
}
