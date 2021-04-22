package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeMotor {
    private DcMotor intakeMotor;

    public void init(HardwareMap hwMap){
        intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
    }

    public void in(){
        intakeMotor.setPower(-1);
    }

    public void out() {
        intakeMotor.setPower(1);
    }

    public void off(){
        intakeMotor.setPower(0);
    }
}
