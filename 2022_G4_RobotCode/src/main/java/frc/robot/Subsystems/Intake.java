// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;

//one motor one piston
/** Add your docs here. */
public class Intake extends Subsystem {
	public DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.piston1Port, RobotMap.piston2Port);
	public CANSparkMax intakeMotor = new CANSparkMax(RobotMap.intakePort, CANSparkMax.MotorType.kBrushless);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
	
	public void scoopBallPiston(){ // can rename as extendIntake
		piston.set(DoubleSolenoid.Value.kForward);
  }

	public void pullBackPiston(){ // can rename as retractIntake
		piston.set(DoubleSolenoid.Value.kReverse);
	}

	public void spinMotor(){
		intakeMotor.set(0.25);
	}

	public void stopMotor(){
		intakeMotor.set(0);
	}

}
