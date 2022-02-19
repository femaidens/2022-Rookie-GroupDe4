// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//four pistons
package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter extends Subsystem {
  public static Solenoid leftPiston = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.leftPistonPort);
  public static Solenoid rightPiston = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.rightPistonPort);

  //drivetrain
  public static CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftPort, MotorType.kBrushless);
	public static CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightPort, MotorType.kBrushless);
	public static CANSparkMax rearLeft = new CANSparkMax(RobotMap.rearLeftPort, MotorType.kBrushless);
	public static CANSparkMax rearRight = new CANSparkMax(RobotMap.rearRightPort, MotorType.kBrushless);
  public static AnalogGyro gyro = new AnalogGyro(RobotMap.gyroPort);
	public static MecanumDrive mecan = new MecanumDrive (frontLeft, rearLeft, frontRight, rearRight);
  
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
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void extendShooterPiston(){
    leftPiston.set(true);
    rightPiston.set(true);
  }

  public void retractShooterPiston(){
    leftPiston.set(false);
    rightPiston.set(false);
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
        mecan.driveCartesian(0, 0, 0.1 + adjust, 0);
      }
      else{ //crosshair is facing the right direction; wants to turn left
        mecan.driveCartesian(0, 0, 0.1 - adjust, 0);
      }
    while(current_errorTY != 0){
      if(current_errorTY > 0){
        mecan.driveCartesian(0.1 - adjust, 0, 0, 0);
      }
      else{ 
        mecan.driveCartesian(0.1 + adjust, 0, 0, 0);
      }
    }
    }
  }
}
