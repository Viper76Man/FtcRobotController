package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;

@TeleOp
public class JustMechinumDrive extends LinearOpMode {

    private Gyroscope imu;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    @Override
    public void runOpMode() {
        imu = hardwareMap.get(Gyroscope.class, "imu");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            int fl = frontLeftMotor.getCurrentPosition();
            int fr = frontRightMotor.getCurrentPosition();
            int bl = backLeftMotor.getCurrentPosition();
            int br = backRightMotor.getCurrentPosition();

            frontLeftMotor.setPower(y + x + rx);
            backLeftMotor.setPower(y - x + rx);
            frontRightMotor.setPower(-y + x + rx);
            backRightMotor.setPower(-y - x + rx);



            telemetry.addData("Front Left Motor", fl);
            telemetry.addData("Front Right Motor", fr);
            telemetry.addData("Back Left Motor", bl);
            telemetry.addData("Back Right Motor", br);
            telemetry.update();
        }
    }
}
