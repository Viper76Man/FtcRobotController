package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.CompleteShooter;
import org.firstinspires.ftc.teamcode.motors.GripperMotor;
import org.firstinspires.ftc.teamcode.motors.IntakeMotor;
import org.firstinspires.ftc.teamcode.motors.MecanumDrive;
import org.firstinspires.ftc.teamcode.servos.GripperServos;
import org.firstinspires.ftc.teamcode.servos.ShooterServo;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")

public class FSMTeleOp extends OpMode {
    ShooterServo shooterServo = new ShooterServo();
    MecanumDrive mecanumDrive = new MecanumDrive();
    CompleteShooter completeShooter = new CompleteShooter();
    IntakeMotor intakeMotor = new IntakeMotor();
    GripperServos gripperServos = new GripperServos();
    GripperMotor gripperMotor = new GripperMotor();

    @Override
    public void init(){
        shooterServo.init(hardwareMap);
        mecanumDrive.init(hardwareMap);
        completeShooter.init(hardwareMap);
        intakeMotor.init(hardwareMap);
        gripperServos.init(hardwareMap);
        gripperMotor.init(hardwareMap);

    }

    @Override
    public void start(){
        //Run once on start push
    }

    @Override
    public void loop(){
        //PS4 controller input
        double y = -gamepad1.left_stick_y; // Remember, this is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        if(gamepad1.left_bumper) {
            shooterServo.singleShot();
        }
        if(gamepad1.left_trigger > 0) {
            shooterServo.trippleShot();
        }

        if (gamepad1.right_trigger > 0){
            completeShooter.powerShootSpeed();
        }

        if (gamepad1.right_bumper){
            completeShooter.highGoalSpeed();
        }

        if (gamepad1.y){
            completeShooter.off();
        }

        if (gamepad1.a){
            intakeMotor.in();
        }

        if (gamepad1.x) {
            intakeMotor.out();
        }

        if (gamepad1.b) {
            intakeMotor.off();
        }
        if(gamepad1.dpad_right) {
            gripperServos.open();
        }
        if(gamepad1.dpad_left) {
            gripperServos.close();
        }

        if(gamepad1.dpad_up){
            gripperMotor.armIn();
        }

        if(gamepad1.dpad_down){
            gripperMotor.armOut();
        }

        mecanumDrive.drive(y,x,rx);
    }
}