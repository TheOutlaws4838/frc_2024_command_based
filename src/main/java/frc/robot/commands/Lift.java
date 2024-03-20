// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.LauncherConstants.LIFT_SPEED;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;

public class Lift extends Command {
  
  private final PWMSparkMax m_lift;
  private final double axisSpeed;
  /** Creates a new Lift. */
  public Lift(double axisSpeed, PWMSparkMax lift) {
    m_lift = lift;
    this.axisSpeed = axisSpeed;

    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(axisSpeed) < 0.1) return; // check if the value of the axis (possitive or negative) is greater then 10%
    m_lift.set(-(axisSpeed * LIFT_SPEED));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
