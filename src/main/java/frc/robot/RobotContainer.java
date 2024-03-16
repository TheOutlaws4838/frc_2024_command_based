// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutonamousCommands;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.PWMDrivetrain;
import frc.robot.subsystems.PWMLauncher;

// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final PWMDrivetrain m_drivetrain = new PWMDrivetrain();
  // private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final PWMLauncher m_launcher = new PWMLauncher();
  // private final CANLauncher m_launcher = new CANLauncher();

  /*
   * The gamepad provided in the KOP shows up like an XBox controller if the mode
   * switch is set to X mode using the
   * switch on the top.
   */
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController = new CommandXboxController(
      OperatorConstants.kOperatorControllerPort);
  // private final PWMSparkMax liftLeft = new PWMSparkMax(3);
  // private final PWMSparkMax liftRight = new PWMSparkMax(4);

  /**
   * The container for the robot. Contains subsystems, OI device4s, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    CameraServer.startAutomaticCapture(0);

    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * accessed via the
   * named factory methods in the Command* classes in
   * edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    // m_drivetrain.setDefaultCommand(
    // new RunCommand(
    // () ->
    // m_drivetrain.arcadeDrive(
    // -m_driverController.getLeftY(), -m_driverController.getRightX()),
    // m_drivetrain));

    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () -> m_drivetrain.tankDrive(
                m_driverController.getLeftY() * 1, m_driverController.getRightY() * 1),
            m_drivetrain));
    // m_drivetrain.setDefaultCommand(
    // new RunCommand(
    // () ->
    // m_drivetrain.controllerdrive(m_driverController.getLeftTriggerAxis(),
    // m_driverController.getRightTriggerAxis(),m_driverController.getLeftX()),
    // m_drivetrain));
    /*
     * Create an inline sequence to run when the operator presses and holds the A
     * (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command
     */
    m_operatorController
        .a()
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher))
                .handleInterrupt(() -> m_launcher.stop()));

    // TODO: uncomment when in use in competition 2
    // m_operatorController.rightBumper().whileTrue(new RobotLift(liftLeft,.3));
    // m_operatorController.leftBumper().whileTrue(new RobotLift(liftRight,.3));
    // m_operatorController.rightTrigger().whileTrue(new RobotLift(liftLeft,-.3));
    // m_operatorController.leftTrigger().whileTrue(new RobotLift(liftRight,-.3));
    // Set up a binding to run the intake command while the operator is pressing and
    // holding the
    // b button
    m_operatorController.b().whileTrue(m_launcher.getIntakeCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_drivetrain);

    return new AutonamousCommands(m_drivetrain);
  }

}
