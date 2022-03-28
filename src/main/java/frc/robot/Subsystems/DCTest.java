// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.RobotMap;
import frc.robot.Commands.DCTestComm;

/** Add your docs here. */
public class DCTest extends Subsystem {
  public DutyCycleEncoder dcTestEncoder = new DutyCycleEncoder(1);
 //public Encoder dcTestEncoder = new Encoder (0, 1);
  public static CANSparkMax dcTestMotor = new CANSparkMax(RobotMap.dcTestMotorPort, CANSparkMax.MotorType.kBrushless);
  //public static RelativeEncoder test = dcTestMotor.getEncoder();
  //public Encoder dcTestEncoder = new Encoder(0, 1, 2);

  public DCTest() {
    //super();
    //dcTestEncoder.setDistancePerRotation(3.0);
  }

  @Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new DCTestComm());
  }

  public void spinDCMotor(){
    dcTestMotor.set(0.2);
  }

  public void stopDCMotor(){
    dcTestMotor.set(0);
  }

  public void resetDCEncoder(){
    //dcTestEncoder.reset();
  }
  public static void testRegEncoder(){
    //System.out.println(test.getPosition());
  }
  public void measureTicks(){
    //System.out.println("Motor Distance in Ticks: " + dcTestEncoder.getAbsolutePosition());
    System.out.println("Using get " + dcTestEncoder.get());
    //System.out.println("Connected " + dcTestEncoder.isConnected());
    System.out.println("Using getDistance " + dcTestEncoder.getDistance());

  }
}
