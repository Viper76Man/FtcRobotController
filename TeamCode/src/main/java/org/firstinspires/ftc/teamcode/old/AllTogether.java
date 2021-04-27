package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class AllTogether extends LinearOpMode {
    private DcMotor shooterMotor;
    private DcMotor intakeMotor;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor knockdownMotor;
    private Servo shooterServo;

    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        knockdownMotor = hardwareMap.get(DcMotor.class,"knockdownMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.right_bumper){
                shooterMotor.setPower(1);
            }
            if (gamepad1.right_trigger>0){
                shooterMotor.setPower(.67);
            }
            if (gamepad1.y){
                shooterMotor.setPower(0);
            }
            if (gamepad1.a) {
                intakeMotor.setPower(-1);
                knockdownMotor.setPower(1);
            }

            if (gamepad1.left_bumper){
                shooterServo.setPosition(0);
            }
            else{
                shooterServo.setPosition(1);
            }

            if (gamepad1.b) {
                intakeMotor.setPower(0);
                knockdownMotor.setPower(0);
            }

            if (gamepad1.x) {
                intakeMotor.setPower(1);
            }
            if(gamepad1.left_trigger>0){
                shooterServo.setPosition(1);
                sleep(120);
                shooterServo.setPosition(0);
                sleep(130);
                shooterServo.setPosition(1);
                sleep(120);
                shooterServo.setPosition(0);
                sleep(130);
                shooterServo.setPosition(1);
                sleep(120);
                shooterServo.setPosition(0);
                sleep(130);
            }
            double y = -gamepad1.left_stick_y; // Remember, this is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            frontLeftMotor.setPower(y + x + rx);
            backLeftMotor.setPower(y - x + rx);
            frontRightMotor.setPower(-y + x + rx);
            backRightMotor.setPower(-y - x + rx);
        }
    }
}

