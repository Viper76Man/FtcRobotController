package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class Intake extends LinearOpMode {

    private DcMotor intakeMotor;
    @Override
    public void runOpMode() {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (gamepad1.a) {
                intakeMotor.setPower(-.78);
            }

            if (gamepad1.b) {
                intakeMotor.setPower(0);
            }

            if (gamepad1.x) {
                intakeMotor.setPower(1);
            }

        }
    }
}



