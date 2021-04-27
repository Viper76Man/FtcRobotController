package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="FSM Gripper Test")
public class FSMGripper extends OpMode {
    //Define Gripper Enums
    public enum GripperState {
        GRIP_OPEN,
        GRIP_CLOSED,
        ARM_OUT,
        ARM_IN
    }

    //GripperState variable is declared here so its value persists between Loop() calls
    GripperState gripperState = GripperState.ARM_OUT;

    //Hardware initialized in init()
    public Servo backServo;
    public Servo frontServo;
    public DcMotor wobbleMotor;

    //Open and closed positions of both servos
    final double BACK_GRIP_OPEN = .5;
    final double BACK_GRIP_CLOSE = 0;
    final double FRONT_GRIP_OPEN = .5;
    final double FRONT_GRIP_CLOSE = 1;

    //Arm distance in encoder ticks
    final int ARM_LOC_OUT = -500;
    final int ARM_LOC_IN = 500;

    @Override
    public void init(){
        //Hardware init code, could do something like this to shorten it public void init(HardwareMap hwMap)
        backServo = hardwareMap.get(Servo.class, "backServo");
        frontServo = hardwareMap.get(Servo.class, "frontServo");
        wobbleMotor = hardwareMap.get(DcMotor.class, "wobbleMotor");

        wobbleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Send all phone telemetry calls to dashboard also
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start(){
        //Run once on start push
    }

    @Override
    public void loop(){
        int armPosition = wobbleMotor.getCurrentPosition();
        telemetry.addData("Status", "Looping");
        telemetry.addData("Encoder Position", armPosition);
        telemetry.update();
        switch (gripperState){
            case GRIP_CLOSED:
                if (gamepad1.x){
                    backServo.setPosition(BACK_GRIP_OPEN);
                    frontServo.setPosition(FRONT_GRIP_OPEN);
                    gripperState = GripperState.GRIP_OPEN;
                }
                break;
            case GRIP_OPEN:
                if (gamepad1.y){
                    backServo.setPosition(BACK_GRIP_CLOSE);
                    frontServo.setPosition(FRONT_GRIP_CLOSE);
                    gripperState = GripperState.GRIP_CLOSED;
                }
                break;
            case ARM_OUT:
                if (gamepad1.a) {
                    wobbleMotor.setTargetPosition(ARM_LOC_IN);
                    wobbleMotor.setPower(100);
                    wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    gripperState = GripperState.ARM_IN;
                }
                break;
            case ARM_IN:
                if (gamepad1.b) {
                    wobbleMotor.setTargetPosition(ARM_LOC_OUT);
                    wobbleMotor.setPower(100);
                    wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    gripperState = GripperState.ARM_OUT;
                }
                break;
            default:
                //should never be reached as GripperState is never null
                gripperState = GripperState.GRIP_CLOSED;
        }

        //This always runs here no cases
        //Should move the button press out here?

    }
}
