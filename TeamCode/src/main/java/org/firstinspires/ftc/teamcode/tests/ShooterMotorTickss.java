package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class ShooterMotorTickss extends OpMode {

    private DcMotor shooterMotor;

    @Override
    public void init() {
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        shooterMotor.getCurrentPosition();
        telemetry.addData("Shooter-Ticks:", shooterMotor.getCurrentPosition());
        telemetry.update();
    }
}
