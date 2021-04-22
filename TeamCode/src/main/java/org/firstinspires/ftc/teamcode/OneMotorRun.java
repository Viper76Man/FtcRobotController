package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp

public class OneMotorRun extends LinearOpMode {

    private DcMotor motorTest;

    @Override

    public void runOpMode() {
        motorTest = hardwareMap.get(DcMotor.class, "motorTest");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        while (opModeIsActive()) {
            double rt= -gamepad1.right_trigger;
            motorTest.setPower (rt);
            telemetry.addData("Right Trigger", rt);
            telemetry.update();
        }
    }
}
