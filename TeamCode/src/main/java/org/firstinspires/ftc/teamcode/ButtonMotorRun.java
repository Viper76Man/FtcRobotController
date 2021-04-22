package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled

@TeleOp
public class ButtonMotorRun extends LinearOpMode {

    private DcMotor shooterMotor;
    @Override
    public void runOpMode() {
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.a){
                shooterMotor.setPower(.5);
            }
            if (gamepad1.x){
                shooterMotor.setPower(0);
            }
    }
}
}
