package org.firstinspires.ftc.teamcode.old;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;

import com.arcrobotics.ftclib.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class Drivetest extends LinearOpMode {
    private MecanumDrive driveTrain;

    private MotorEx fL, fR, bL, bR;

    // the motors used here are goBILDA Yellow Jackets, 312 rpm

    @Override
    public void runOpMode() throws InterruptedException {

        fL = new MotorEx(hardwareMap, "frontLeftMotor", MotorEx.GoBILDA.RPM_312);
        fR = new MotorEx(hardwareMap, "frontRightMotor", MotorEx.GoBILDA.RPM_312);
        bL = new MotorEx(hardwareMap, "backLeftMotor", MotorEx.GoBILDA.RPM_312);
        bR = new MotorEx(hardwareMap, "backRightMotor", MotorEx.GoBILDA.RPM_312);

        driveTrain = new MecanumDrive(
                fL, fR, bL, bR
        );

        waitForStart();

        driveWithVector(new Vector2d(0,12));
        sleep(1000);
        driveWithVector(new Vector2d(0,0));

    }

    private void driveWithVector(Vector2d vector) {
        double[] speeds = normalize(new double[]{vector.getX(), vector.getY()});
        driveTrain.driveRobotCentric(speeds[0], speeds[1],0);
    }

    /**
     * Normalize the wheel speeds if any value is greater than 1
     */
    private double[] normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }

        return wheelSpeeds;
    }
}
