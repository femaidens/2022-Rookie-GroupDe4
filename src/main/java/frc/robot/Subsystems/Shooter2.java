// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import javax.swing.SpinnerDateModel;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter2 extends Subsystem {
  public DutyCycleEncoder dcEncoder = new DutyCycleEncoder(RobotMap.dcPort);
  public DoubleSolenoid shooter2Piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.leftPistonPort, RobotMap.rightPistonPort);
	public CANSparkMax shooter2Motor = new CANSparkMax(RobotMap.shooter2Port, CANSparkMax.MotorType.kBrushless);
  
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

  public void spinDCMotor(){
    double distance = 5; //experimental value; change according to distance needed
    while(dcEncoder.getDistance() < distance){
      shooter2Motor.set(-0.5);
    }
  }

  public void stopDCMotor(){
    shooter2Motor.set(0);
  }

  /*
  public void setTicks(){
    double ticks = dcEncoder.getDistancePerRotation();
    dcEncoder.setDistancePerRotation(ticks);
  }
  */

  public void shootBall(){
    double shootBallTrigger = OI.opJoy.getRawAxis(3); //right trigger
    if (shootBallTrigger > 0.2){
      shooter2Piston.set(DoubleSolenoid.Value.kForward); //release the latch to shoot
    }
  }

  public void retractS2Piston(){
    shooter2Piston.set(DoubleSolenoid.Value.kReverse); //retracts piston to og place
  }

  public void stopMecanDrive(){
    mecan.driveCartesian(0, 0, 0, gyro.getAngle());
  }

  public void shooterAlign(){
    previous_error = current_error;
    current_error = Robot.slimelight.getTXValue();
    current_errorTY = Robot.slimelight.getTYValue();
    integral += (current_error+previous_error)/2*(time);
    derivative = (current_error-previous_error)/time;
    adjust = KP*current_error + KI*integral + KD*derivative;

    while(current_error != 0){
      if(current_error > 0){ //crosshair is facing the left direction; wants to turn right
        mecan.driveCartesian(adjust, 0, 0, 0); //driveCartesian(z, x, y, gyro);
      }
      else{ //crosshair is facing the right direction; wants to turn left
        mecan.driveCartesian(-adjust, 0, 0, 0);
      }
      while(current_errorTY != 0){
        if(current_errorTY > 0){
          mecan.driveCartesian(0, 0, -adjust, 0); //in front of target; wants to move backward
        }
        else{ 
          mecan.driveCartesian(0, 0, adjust, 0); //behind target; wants to move forward
        }
      }
    }
  }
}
