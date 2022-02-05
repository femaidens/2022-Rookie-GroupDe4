// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import frc.robot.RobotMap;

public class Shooter2 extends Subsystem {

  public DutyCycleEncoder dcEncoder = new DutyCycleEncoder(RobotMap.dcPort);
  public DoubleSolenoid shooter2Piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.sPistonPort1, RobotMap.sPistonPort2);
	public CANSparkMax shooter2Motor = new CANSparkMax(RobotMap.shooter2Port, CANSparkMax.MotorType.kBrushless);

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
  public void pushLatch(){
    shooter2Piston.set(DoubleSolenoid.Value.kForward); //release the latch
  }
  
  public void retractS2Piston(){
    shooter2Piston.set(DoubleSolenoid.Value.kReverse);
  }
}
