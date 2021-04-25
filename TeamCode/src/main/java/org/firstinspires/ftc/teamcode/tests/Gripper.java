package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Gripper extends LinearOpMode {
    private Servo backGripper;
    private Servo frontGripper;
    private DcMotor gripperArm;

    @Override
    public void runOpMode() {
        backGripper = hardwareMap.get(Servo.class, "backGripper");
        frontGripper = hardwareMap.get(Servo.class, "frontGripper");
        gripperArm = hardwareMap.get(DcMotor.class, "gripperArm");
        waitForStart();
        while (opModeIsActive()) {
// Open
        if (gamepad1.dpad_right) {
            frontGripper.setPosition(1);
            backGripper.setPosition(-1);
            sleep(500);
// Close
        }
        if (gamepad1.dpad_left) {
            frontGripper.setPosition(0);
            backGripper.setPosition(0.435);
            sleep(500);
        }
        if (gamepad1.dpad_up) {
            gripperArm.setPower(.7);
            sleep(300);
            gripperArm.setPower(0);

        }
        if (gamepad1.dpad_down) {
            gripperArm.setPower(-.7);
            sleep(300);
            gripperArm.setPower(0);
        }

        }
    }
}
