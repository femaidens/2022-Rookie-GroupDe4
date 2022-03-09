// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.RobotMap;

/** Add your docs here. */
public class DCTest extends Subsystem {
  public DutyCycleEncoder dcTestEncoder = new DutyCycleEncoder(RobotMap.dcTestPort);
  public CANSparkMax dcTestMotor = new CANSparkMax(RobotMap.dcTestMotorPort, CANSparkMax.MotorType.kBrushless);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void spinDCMotor(){
    dcTestMotor.set(0.3);
  }

  public void stopDCMotor(){
    dcTestMotor.set(0);
  }

  public void measureTicks(){
    System.out.println("Motor Distance in Ticks: " + dcTestEncoder.getDistance());
  }
}
