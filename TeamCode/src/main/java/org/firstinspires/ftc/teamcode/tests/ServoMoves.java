package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoMoves {
    private Servo shooterServo;

    public ServoMoves(Servo shooterServo) {
        this.shooterServo = shooterServo;
    }

    public void flip() {
        shooterServo.setPosition(0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) { 
        }
        shooterServo.setPosition(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
