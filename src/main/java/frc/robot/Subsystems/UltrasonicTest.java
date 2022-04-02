// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.RobotMap;
import frc.robot.Commands.GetDistance;

public class UltrasonicTest extends Subsystem {
  public static Ultrasonic ult = new Ultrasonic(RobotMap.ultPort1, RobotMap.ultPort2);

  @Override
  public void initDefaultCommand() {
      // Start the ultrasonic in automatic mode
      setDefaultCommand(new GetDistance());
  }

  public void getInches(){
    Ultrasonic.setAutomaticMode(true);
    System.out.println("distance: " + ult.getRangeInches());
    System.out.println();
  }
}
