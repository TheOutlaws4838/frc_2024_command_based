// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotLift extends Command {
  PWMSparkMax m_lift;
  double speed;
  // PWMSparkMax m_liftRight;
  /** Creates a new RobotLift. */
  public RobotLift(PWMSparkMax lift,double speed ) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_lift = lift;
    this.speed = speed;
    // m_liftRight = liftRight;


  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_lift.set(speed); //set lift speed 

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_lift.set(0); //lift stop
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  
}
