package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous
public class AdvancedAuto extends LinearOpMode {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private Servo shooterServo;
    private DcMotor shooterMotor;
    BNO055IMU imu;
    Orientation angles;


    static final int MOTOR_TICK_COUNT = 538;


    @Override
    public void runOpMode() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        shooterServo.setPosition(1);
        sleep(200);

        waitForStart();
        shooterMotor.setPower(.63);
        Strafe();
        Drive(25, .4);
        sleep(500);
        Sqaure(0);
        Shoot();
        sleep(200);
        StrafeLeft(7.5, .3);
        sleep(100);
        Shoot();
        StrafeLeft(7.5, .3);
        sleep(100);
        Shoot();
        Drive(10,.5);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("Gyro angle:", angles.firstAngle);
        telemetry.update();
    }


    public void Shoot() {
        shooterServo.setPosition(0);
        sleep(130);
        shooterServo.setPosition(1);
        sleep(130);
    }


    public void Drive(int distanceWanted, double power) {
        double circumference = 3.14 * 3.77953; // 3.77953 is the circumference of our wheel
        double rotationsNeeded = distanceWanted / circumference;
        int drvingTarget = (int) (rotationsNeeded * MOTOR_TICK_COUNT);

        double frontleftspeed;
        double frontrightspeed;
        double backleftspeed;
        double backrightspeed;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double target = angles.firstAngle;

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeftMotor.setTargetPosition(drvingTarget);
        backRightMotor.setTargetPosition(drvingTarget);
        frontRightMotor.setTargetPosition(drvingTarget);
        frontLeftMotor.setTargetPosition(drvingTarget);

        while (frontLeftMotor.getCurrentPosition() < drvingTarget) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double z = angles.firstAngle;

            frontleftspeed = power + (z - target) / 100;
            frontrightspeed = power - (z - target) / 100;
            backleftspeed = power + (z - target) / 100;
            backrightspeed = power - (z - target) / 100;

            frontleftspeed = Range.clip(frontleftspeed, -1, 1);
            frontrightspeed = Range.clip(frontrightspeed, -1, 1);
            backleftspeed = Range.clip(backleftspeed, -1, 1);
            backrightspeed = Range.clip(backrightspeed, -1, 1);

            frontLeftMotor.setPower(frontleftspeed);
            frontRightMotor.setPower(frontrightspeed);
            backRightMotor.setPower(backrightspeed);
            backLeftMotor.setPower(backleftspeed);

            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
    }

    public void Strafe() {
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeftMotor.setTargetPosition(2700);
        backRightMotor.setTargetPosition(138);
        frontRightMotor.setTargetPosition(2687);
        frontLeftMotor.setTargetPosition(159);
        while (backLeftMotor.getCurrentPosition() < 2700) {
            backRightMotor.setPower(.7);
            backLeftMotor.setPower(.7);
            frontLeftMotor.setPower(.7);
            frontRightMotor.setPower(.7);

            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
    }

    public void StrafeLeft(double distanceWanted, double power) {
        double circumference = 3.14 * 3.77953; // 3.77953 is the circumference of our wheel
        double rotationsNeeded = distanceWanted / circumference;
        int drivingTarget = (int) (rotationsNeeded * MOTOR_TICK_COUNT);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        backLeftMotor.setTargetPosition(drivingTarget);
        backRightMotor.setTargetPosition(drivingTarget);
        frontRightMotor.setTargetPosition(drivingTarget);
        frontLeftMotor.setTargetPosition(drivingTarget);
        while (frontLeftMotor.getCurrentPosition() < drivingTarget) {
            backRightMotor.setPower(power);
            backLeftMotor.setPower(power);
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);

            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
    }

    public void Sqaure(int gyroangle) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        while (angles.firstAngle != gyroangle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Gyro:", angles.firstAngle);
            telemetry.update();
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            if (angles.firstAngle > gyroangle) {
                frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                frontRightMotor.setPower(.6);
                frontLeftMotor.setPower(.6);
                backRightMotor.setPower(.6);
                backLeftMotor.setPower(.6);
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Gyro:", angles.firstAngle);
                telemetry.update();
            }
            else{
                frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                frontRightMotor.setPower(.6);
                frontLeftMotor.setPower(.6);
                backRightMotor.setPower(.6);
                backLeftMotor.setPower(.6);
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Gyro:", angles.firstAngle);
                telemetry.update();
            }
        }
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);

    }
}
