// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.LauncherConstants.DRIVE_SPEED;
import static frc.robot.Constants.LauncherConstants.kLiftLeft;
import static frc.robot.Constants.LauncherConstants.kLiftLeftLimit;
import static frc.robot.Constants.LauncherConstants.kLiftRight;
import static frc.robot.Constants.LauncherConstants.kLiftRightLimit;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutonamousCommands;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.Lift;
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

  private final PWMSparkMax m_lift_left = new PWMSparkMax(kLiftLeft);
  private final PWMSparkMax m_lift_right = new PWMSparkMax(kLiftRight);

  private final DigitalInput m_lift_left_actuator = new DigitalInput(kLiftLeftLimit);
  private final DigitalInput m_lift_right_actuator = new DigitalInput(kLiftRightLimit);
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
                m_driverController.getLeftY() * DRIVE_SPEED, m_driverController.getRightY() * DRIVE_SPEED),
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

    m_operatorController.b().whileTrue(m_launcher.getIntakeCommand());
    
    new RunCommand(() -> {
      new Lift(m_operatorController.getLeftY(), m_lift_left, m_lift_left_actuator);
      new Lift(m_operatorController.getRightY(), m_lift_right, m_lift_right_actuator);
    });
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
