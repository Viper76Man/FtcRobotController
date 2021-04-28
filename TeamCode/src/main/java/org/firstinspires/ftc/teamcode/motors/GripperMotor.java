package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class GripperMotor {
    public enum ArmState {
        IN,
        OUT,
    }
    //GripperState variable is declared here so its value persists between Loop() calls
    private ArmState armState = ArmState.IN;
    //Hardware initialized in init()
    private DcMotor wobbleMotor;

    //Arm distance in encoder ticks
    final int RANGE_MOTION = 600;

    private int targetPos = 0;

    public void init(HardwareMap hwMap) {
        wobbleMotor = hwMap.get(DcMotor.class, "wobbleMotor");
        wobbleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void armIn() {
        targetPos = wobbleMotor.getCurrentPosition() + RANGE_MOTION;
        while (armState != ArmState.IN) {
            switch (armState) {
                case OUT:
                    wobbleMotor.setTargetPosition(targetPos);
                    wobbleMotor.setPower(1);
                    wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    if (wobbleMotor.getCurrentPosition() >= targetPos - 10 && wobbleMotor.getCurrentPosition() <= targetPos + 10){
                        wobbleMotor.setPower(0);
                        armState = ArmState.IN;
                        }
                break;

            }
        }
    }

    public void armOut() {
        targetPos = wobbleMotor.getCurrentPosition() - RANGE_MOTION;
        while (armState != ArmState.OUT) {
            switch (armState) {
                case IN:
                    wobbleMotor.setTargetPosition(targetPos);
                    wobbleMotor.setPower(1);
                    wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    if (wobbleMotor.getCurrentPosition() >= targetPos - 10 && wobbleMotor.getCurrentPosition() <= targetPos + 10){
                        wobbleMotor.setPower(0);
                        armState = ArmState.OUT;
                    }
                    break;

            }
        }
    }
}
