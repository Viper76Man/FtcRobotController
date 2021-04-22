package org.firstinspires.ftc.teamcode.motors;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class CompleteShooter {

    private static DcMotorEx shooterMotor;
    private static double MOTOR_TICKS_PER_REV = 28;
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private static double POWER_SHOT_SPEED = 4020;
    private static double HIGH_GOAL_SPEED = 4260;

    public void init(HardwareMap hwMap) {
        shooterMotor = hwMap.get(DcMotorEx.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public static void highGoalSpeed() {
        shooterMotor.setVelocity(rpmToTicks(HIGH_GOAL_SPEED));
    }

    public void powerShootSpeed() {
        shooterMotor.setVelocity(rpmToTicks(POWER_SHOT_SPEED));
    }

    public void off() {
        shooterMotor.setPower(0);
    }

    private static double rpmToTicks(double rpm) {
        return rpm * MOTOR_TICKS_PER_REV / 60;
    }
}

