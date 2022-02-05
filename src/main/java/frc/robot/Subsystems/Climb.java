// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;

public class Climb extends Subsystem {
  public DoubleSolenoid climbRightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.cLeftPiston1Port, RobotMap.cLeftPiston2Port);
  public DoubleSolenoid climbLeftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.cRightPiston1Port, RobotMap.cRightPiston2Port);
	public CANSparkMax climbLeftMotor = new CANSparkMax(RobotMap.climbLeftPort, CANSparkMax.MotorType.kBrushless);
	public CANSparkMax climbRightMotor = new CANSparkMax(RobotMap.climbRightPort, CANSparkMax.MotorType.kBrushless);

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
		climbLeftMotor.set(0.25);
    climbRightMotor.set(0.25);
	}

	public void reverseClimbMotor(){
		climbLeftMotor.set(-0.25);
    climbRightMotor.set(-0.25);
	}
  public void stopClimbMotor(){
    climbLeftMotor.set(0);
    climbRightMotor.set(0);
  }
}
