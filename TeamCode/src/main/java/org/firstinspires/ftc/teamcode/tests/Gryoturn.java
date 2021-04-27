package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@Autonomous
@Disabled
public class Gryoturn extends LinearOpMode {

    BNO055IMU imu;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    Orientation angles;

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
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        gyroTurn(.1, 30);
        sleep(5000);
        gyroTurn(.4,  -75);
        sleep(5000);
    }

    public void gyroTurn ( double power, double angle) {
        if (angle<0){
            frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            while (angles.firstAngle>angle){
                backRightMotor.setPower(power);
                backLeftMotor.setPower(power);
                frontRightMotor.setPower(power);
                frontLeftMotor.setPower(power);
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Heading:", angles.firstAngle);
                telemetry.addData("Roll:", angles.secondAngle);
                telemetry.addData("Pitch:", angles.thirdAngle);
                telemetry.update();
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                if (angle >= angles.firstAngle) {
                    backRightMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    frontRightMotor.setPower(0);
                    frontLeftMotor.setPower(0);
                    sleep(60);
                }
            }
        }
        if (angle>0){
            frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            while (angles.firstAngle < angle){
                backRightMotor.setPower(power);
                backLeftMotor.setPower(power);
                frontRightMotor.setPower(power);
                frontLeftMotor.setPower(power);
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Heading:", angles.firstAngle);
                telemetry.addData("Roll:", angles.secondAngle);
                telemetry.addData("Pitch:", angles.thirdAngle);
                telemetry.update();
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                if (angle <= angles.firstAngle) {
                    backRightMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    frontRightMotor.setPower(0);
                    frontLeftMotor.setPower(0);
                    sleep(60);
                }
            }
        }
    }
}
/*Telemetry
angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
telemetry.addData("Heading:", angles.firstAngle);
telemetry.addData("Roll:", angles.secondAngle);
telemetry.addData("Pitch:", angles.thirdAngle);
telemetry.update();

 */