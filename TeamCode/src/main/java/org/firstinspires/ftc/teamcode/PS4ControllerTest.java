package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
@Disabled
public class PS4ControllerTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double rt= gamepad1.right_trigger;
            double lt= gamepad1.left_trigger;
            telemetry.addData("Left Stick Y:", y);
            telemetry.addData("Left Stick X:", x);
            telemetry.addData("Right Stick X:", rx);
            telemetry.addData("Right Trigger:", rt);
            telemetry.addData("Left Trigger:",lt);
            telemetry.update();
        }

    }
}
