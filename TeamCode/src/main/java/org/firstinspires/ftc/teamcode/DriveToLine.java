package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous
public class DriveToLine extends LinearOpMode {
    private DcMotor shooterMotor;
    private DcMotor intakeMotor;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private Servo shooterServo;

    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
            frontLeftMotor.setPower(.25);
            frontRightMotor.setPower(.25);
            backLeftMotor.setPower(.25);
            backRightMotor.setPower(.25);
            sleep(2600);

            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);

            shooterMotor.setPower(.73);
            sleep(1500);
            shooterServo.setPosition(0);
            sleep(2000);
            shooterServo.setPosition(1);
            sleep(3000);
            shooterServo.setPosition(0);
            sleep(2000);
            shooterServo.setPosition(1);
            sleep(3000);
            shooterServo.setPosition(0);
            sleep(2000);
            shooterServo.setPosition(1);
            sleep(3000);

        frontLeftMotor.setPower(.25);
        frontRightMotor.setPower(.25);
        backLeftMotor.setPower(.25);
        backRightMotor.setPower(.25);
        sleep(500);

        }
    }

