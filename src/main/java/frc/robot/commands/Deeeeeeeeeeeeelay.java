// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.LauncherConstants.TIMING;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class Deeeeeeeeeeeeelay extends Command {
  public final Timer timer;
  public final  double deeeeeeeeeeeeelay;

  /** Creates a new Deeeeeeeeeeeeelay. */
  public Deeeeeeeeeeeeelay(double deeeeeeeeeeeeelay) {  
    this.deeeeeeeeeeeeelay = deeeeeeeeeeeeelay;
    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

    // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > 5.0;
    }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}


}
