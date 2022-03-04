// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;

/** Add your docs here. */
public class MeccaAutoalign extends Subsystem {
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
  static double current_error_tx = 0.0;
  static double previous_error_tx = 0.0;
  static double integral = 0.0;
  static double derivative = 0.0;
  static double adjust = 0.0;
  static double time = 0.1;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void meccalign(){
    previous_error_tx = current_error_tx;
    current_error_tx = 0 - Robot.meccaLime.getTXValue(); 
    //negative error value; robot is to the right of the crosshair
    //positive error value; robot is to the left of the crosshair
    integral += (current_error_tx + previous_error_tx)/2*(time);
    derivative = (current_error_tx - previous_error_tx)/time;
    adjust = KP*current_error_tx + KI*integral + KD*derivative;
    
    if (current_error_tx > min_error){
      adjust += min_command;
    }

    else if (current_error_tx < -min_error){
      adjust -= min_command;
    }

    while(current_error_tx != 0){
      if(current_error_tx > 0){ //crosshair is facing the right direction; wants to turn left
        mecan.driveCartesian(0.1 + adjust, 0, 0, gyro.getAngle());
      }
      else{ //crosshair is facing the right direction; wants to turn left
        mecan.driveCartesian(0.1 - adjust, 0, 0, gyro.getAngle());
      }
    }
  }
}