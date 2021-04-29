package org.firstinspires.ftc.teamcode.servos;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class GripperServos {

        private Servo frontServo;
        private Servo backServo;

        static double GRIPPER_OPEN = .5;
        static double GRIPPER_CLOSE = 1;


        public void init(HardwareMap hwMap) {
            frontServo = hwMap.get(Servo.class, "frontServo");
            backServo = hwMap.get(Servo.class, "backServo");
            frontServo.setDirection(Servo.Direction.REVERSE);

        }

        public void open(){
            frontServo.setPosition(GRIPPER_OPEN);
            backServo.setPosition(GRIPPER_OPEN);


        }

        public void close(){
            frontServo.setPosition(GRIPPER_CLOSE);
            backServo.setPosition(GRIPPER_CLOSE);

        }

}

