package org.firstinspires.ftc.teamcode.tests;

import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class PostionDriveTest extends LinearOpMode {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    Translation2d m_frontLeftLocation =
            new Translation2d(.2325, .2325);
    Translation2d m_frontRightLocation =
            new Translation2d(.2325, -.2325);
    Translation2d m_backLeftLocation =
            new Translation2d(-.2325, .2325);
    Translation2d m_backRightLocation =
            new Translation2d(-.2325, -.2325);

    // Creating my kinematics object using the wheel locations.
    MecanumDriveKinematics m_kinematics = new MecanumDriveKinematics
            (
                    m_frontLeftLocation, m_frontRightLocation,
                    m_backLeftLocation, m_backRightLocation
            );


    @Override
    public void runOpMode() throws InterruptedException {

    }
}
