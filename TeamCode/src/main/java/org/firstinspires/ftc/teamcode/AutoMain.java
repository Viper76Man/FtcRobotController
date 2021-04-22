package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Auto OpMode", group="Linear OpMode")

public class AutoMain extends LinearOpMode {
    //Declare variables here
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor shooterMotor;
    private Servo shooterServo;
    private ServoMoves servoMoves;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        servoMoves = new ServoMoves(shooterServo);
        waitForStart();
        servoMoves.flip();
    }
}
