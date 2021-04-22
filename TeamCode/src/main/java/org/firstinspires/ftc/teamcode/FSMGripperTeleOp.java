package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.servos.GripperServos;
import org.firstinspires.ftc.teamcode.servos.ShooterServo;

@TeleOp

public class FSMGripperTeleOp extends OpMode {
    GripperServos GripperServos = new GripperServos();

    @Override
    public void init(){
        GripperServos.init(hardwareMap);
    }

    @Override
    public void start(){
        //Run once on start push
    }

    @Override
    public void loop(){
        if(gamepad1.dpad_right) {
            GripperServos.open();
        }
        if(gamepad1.dpad_left) {
            GripperServos.close();
        }
    }
}