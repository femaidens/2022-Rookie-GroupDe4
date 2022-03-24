// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;

//one motor for conveyor; one for arm extension/retraction

public class Intake extends Subsystem {
	//public DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.piston1Port, RobotMap.piston2Port);
	public CANSparkMax intakeConveyerMotor = new CANSparkMax(RobotMap.intakeConveyerPort, CANSparkMax.MotorType.kBrushless);
	public CANSparkMax intakeArmMotor = new CANSparkMax(RobotMap.intakeArmPort, CANSparkMax.MotorType.kBrushless);
	public DutyCycleEncoder intakeDC = new DutyCycleEncoder(RobotMap.intakeDCPort);

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
	

	public void retractIntake(){ 
		while (intakeDC.getDistance() < 0){
			intakeArmMotor.set(-0.9);
		}
	}

	public void spinConveyerMotor(){ //for spinning conveyor
		intakeConveyerMotor.set(0.25);
	}

	public void stopMotor(){
		intakeArmMotor.set(0);
		intakeConveyerMotor.set(0);
	}

	public void extendIntake(){
		double distance = 6; //change based on testing
		previous_error = current_error;
    	current_error = distance - intakeDC.getDistance();
		double error_margin = 0.02;
		integral += (current_error+previous_error)/2*(time);
		derivative = (current_error-previous_error)/time;
		adjust = KP*current_error + KI*integral + KD*derivative;
		
		if (current_error > min_error){
		adjust += min_command;
		}

		else if (current_error < -min_error){
		adjust -= min_command;
		}
		
		while(current_error != error_margin){ //0 degrees = horizontal axis
			if (current_error > error_margin){ //mean that angle is greater than 37
				intakeArmMotor.set(adjust); //spins forward to decrease angle
			}
			else if (current_error < -error_margin){ // angle is less than 37
				intakeArmMotor.set(-adjust); // spins backwards to increase angle
			}
		}
	}
}
