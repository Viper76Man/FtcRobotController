package org.firstinspires.ftc.teamcode.servos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

public class ShooterServo {
    private enum FlipperState {
        FLIPOUT,
        FLIPIN,
        FLIPDONE
    }

    //FlipperState variable is declared here so its value persists between Loop() calls
    private FlipperState flipperState = FlipperState.FLIPOUT;

    private Servo shooterServo;

    private static double SHOOTER_OPEN = 1;
    private static double SHOOTER_CLOSE = 0;
    private static double SHOOTER_TIME = 130;

    private ElapsedTime shootTimer = new ElapsedTime();

    public void init(HardwareMap hwMap) {
        shooterServo = hwMap.get(Servo.class, "shooterServo");
        shooterServo.setDirection(Servo.Direction.REVERSE);
        shooterServo.setPosition(SHOOTER_CLOSE);
        shootTimer.reset();
    }

    public void singleShot(){
        shootTimer.reset();
        flipperState = flipperState.FLIPOUT;

        //Run the machine state until completed
        while (flipperState != FlipperState.FLIPDONE){
            switch (flipperState) {
                case FLIPOUT:
                    shooterServo.setPosition(SHOOTER_OPEN);
                    if (shootTimer.milliseconds() >= SHOOTER_TIME){
                        flipperState = FlipperState.FLIPIN;
                    }
                break;

                case FLIPIN:
                shooterServo.setPosition(SHOOTER_CLOSE);
                flipperState = flipperState.FLIPDONE;
                break;
            }
        }
    }

    public void trippleShot(){
        shootTimer.reset();
        flipperState = flipperState.FLIPOUT;
        int shotCount = 1;


        //Run the machine state until completed shot count sets to FLIPDONE
        while (flipperState != FlipperState.FLIPDONE){
            switch (flipperState) {
                case FLIPOUT:
                    shooterServo.setPosition(SHOOTER_OPEN);
                    if (shootTimer.milliseconds() >= SHOOTER_TIME){
                        flipperState = FlipperState.FLIPIN;
                        shootTimer.reset();
                    }
                break;

                case FLIPIN:
                    shooterServo.setPosition(SHOOTER_CLOSE);
                    if (shootTimer.milliseconds() >= SHOOTER_TIME){
                        flipperState = FlipperState.FLIPOUT;
                        shotCount++;
                        shootTimer.reset();
                    }
                    else if (shotCount == 3){
                        flipperState = FlipperState.FLIPDONE;
                    }
                break;
                }
            }
        }
    }
