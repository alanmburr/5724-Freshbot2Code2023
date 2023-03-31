package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DriveTrainSubsystem extends SubsystemBase {
    private CANSparkMax leftFront = new CANSparkMax(Constants.leftFrontCANId, MotorType.kBrushed);
    private CANSparkMax leftBack = new CANSparkMax(Constants.leftBackCANId, MotorType.kBrushed);
    private CANSparkMax rightFront = new CANSparkMax(Constants.rightFrontCANId, MotorType.kBrushed);
    private CANSparkMax rightBack = new CANSparkMax(Constants.rightBackCANId, MotorType.kBrushed);

    DifferentialDrive drive = new DifferentialDrive(leftFront, rightFront);
    private double speedMod = 0.75;

    public DriveTrainSubsystem() {
        leftFront.restoreFactoryDefaults();
        leftBack.restoreFactoryDefaults();
        rightFront.restoreFactoryDefaults();
        rightBack.restoreFactoryDefaults();

        leftFront.setIdleMode(IdleMode.kBrake);
        leftBack.setIdleMode(IdleMode.kBrake);
        rightFront.setIdleMode(IdleMode.kBrake);
        rightBack.setIdleMode(IdleMode.kBrake);

        leftBack.follow(leftFront);
        rightBack.follow(rightFront);
    }

    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(-speed * speedMod, rotation);
    }

    public void broMomentum(double s) {
        speedMod = s;
    }
}
