package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class Shooter extends LinearOpMode {

    private DcMotor shooterMotor;
    private Servo shooterServo;

    @Override
    public void runOpMode() {
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.right_bumper){
                shooterMotor.setPower(.78);
            }
            if (gamepad1.left_bumper){
                shooterServo.setPosition(1);
                shooterServo.setPosition(0);
            }
            if (gamepad1.y){
                shooterMotor.setPower(0);
            }
        }
    }
}