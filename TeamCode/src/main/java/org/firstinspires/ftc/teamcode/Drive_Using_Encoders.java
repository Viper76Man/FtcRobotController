package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class Drive_Using_Encoders extends LinearOpMode {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;


    static final int MOTOR_TICK_COUNT = 538;
    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Drive(14, .40);
    }
        public void Drive(double distanceWanted, double power){
            double circumference = 3.14 * 3.77953; // 3.77953 is the circumference of our wheel
            double rotationsNeeded = distanceWanted / circumference;
            int drvingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNT);
            backLeftMotor.setTargetPosition(drvingTarget);
            backRightMotor.setTargetPosition(drvingTarget);
            frontRightMotor.setTargetPosition(drvingTarget);
            frontLeftMotor.setTargetPosition(drvingTarget);

            backRightMotor.setPower(power);
            backLeftMotor.setPower(power);
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);

            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy()|| backRightMotor.isBusy()){
                telemetry.addData("Path", "On my way to complete my mission master");
            }
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
        }
}
