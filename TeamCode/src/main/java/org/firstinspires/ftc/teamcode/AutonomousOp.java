package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.drive.RRMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.motors.CompleteShooter;
import org.firstinspires.ftc.teamcode.motors.GripperMotor;
import org.firstinspires.ftc.teamcode.motors.IntakeMotor;
import org.firstinspires.ftc.teamcode.servos.GripperServos;
import org.firstinspires.ftc.teamcode.servos.ShooterServo;

import java.util.List;

@Autonomous
public class AutonomousOp extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY = "AY236CH/////AAABmS9jKkBx8ER7txA47r0067MtfpR0ls4O90T2SJZNFPEItLI10TBjv68Sx1SVy9q+bcNAyfIU116iE1DDrgitpNHNYIkh8PqQDIFYeH6dR9SHWCrqZ8Ro5lsNXn5BicDg4oKxrLoeQKd6bohytVJdqz2c4X1KeslfGaVuVyV8OCqPwYaf6/qm7nvPIMXyfawChgXPaYDoOeizgLCi/UHDfWqB3OVioeTAembUYNao9JNZAGO7CSwIs3cbFRFRm70ujKpA7Qo5j3cPUpXCQDYGPmMHDT5cp37m9fH866+/mFfCGY5GuTgK2pxJ+XplSFZtWK6HFQDER4ZC3Q+t25XyfMpmKeZ5+nRbslGdRIT7w6Co";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    private int diskDetect = 0;

    GripperServos gripperServos = new GripperServos();
    GripperMotor gripperMotor = new GripperMotor();
    CompleteShooter completeShooter = new CompleteShooter();
    ShooterServo shooterServo = new ShooterServo();
    IntakeMotor intakeMotor = new IntakeMotor();

    @Override
    public void runOpMode() {

        gripperServos.init(hardwareMap);
        gripperMotor.init(hardwareMap);
        shooterServo.init(hardwareMap);
        completeShooter.init(hardwareMap);
        intakeMotor.init(hardwareMap);

        gripperServos.close();
        sleep(1000);
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 16 / 9);
        }

        //Pick SampleMecanumDrive for dashboard and RRMecanumDrive for no dashboard
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //RRMecanumDrive drive = new RRMecanumDrive(hardwareMap);

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        FtcDashboard.getInstance().startCameraStream(tfod, 0);

        while (!isStarted()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;

                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());

                        // check label to see which target zone to go after.
                        if (recognition.getLabel().equals("Single")) {
                            telemetry.addData("Target Zone", "B");
                            diskDetect = 1;
                        } else if (recognition.getLabel().equals("Quad")) {
                            telemetry.addData("Target Zone", "C");
                            diskDetect = 4;
                        } else {
                            telemetry.addData("Target Zone", "A");
                            diskDetect = 0;
                        }
                    }

                    telemetry.update();

                }
            }
        }
        Pose2d startPose = new Pose2d(-60, -39, Math.toRadians(0));
        drive.setPoseEstimate(startPose);


        waitForStart();
        if (isStopRequested()) return;

        tfod.shutdown();
        //0 disks zone A front
        if (diskDetect == 0) {
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-24, -49), Math.toRadians(0))
                    .splineTo(new Vector2d(14, -40.5), Math.toRadians(0))

                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end(), true)
                    .splineTo(new Vector2d(-0, -42), Math.toRadians(180))
                    .build();


            drive.followTrajectory(traj1);
            gripperMotor.armOut();
            sleep(250);
            gripperServos.open();
            sleep(250);
            completeShooter.highGoalSpeed();


            drive.followTrajectory(traj2);
            sleep(250);
            shooterServo.singleShot();
            sleep(200);
            shooterServo.singleShot();
            sleep(200);
            shooterServo.singleShot();
            sleep(140);

            drive.turn(Math.toRadians(180));

            startPose = traj2.end().plus(new Pose2d(0, 0, Math.toRadians(180)));

            Trajectory traj3 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-24, -49), Math.toRadians(180))
                    .splineTo(new Vector2d(-39, -49), Math.toRadians(180))
                    .build();

            drive.followTrajectory(traj3);

            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .strafeRight(8)
                    .build();

            drive.followTrajectory(traj4);

            gripperServos.close();
            sleep(250);
            gripperMotor.armIn();
            sleep(250);

            drive.turn(Math.toRadians(180));

            startPose = traj4.end().plus(new Pose2d(0, 0, Math.toRadians(-180)));

            Trajectory traj5 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(5, -40.5), Math.toRadians(0))

                    .build();
            drive.followTrajectory(traj5);

            gripperMotor.armOut();
            sleep(250);
            gripperServos.open();
            sleep(250);

            Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                    .splineTo(new Vector2d(11, -40.5), Math.toRadians(0))
                    .build();
            drive.followTrajectory(traj6);

        }

        //1 disk zone B middle
        if (diskDetect == 1) {
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-24, -49), Math.toRadians(0))
                    .splineTo(new Vector2d(38, -15.5), Math.toRadians(0))

                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end(), true)
                    .splineTo(new Vector2d(-0, -42), Math.toRadians(180))
                    .build();


            drive.followTrajectory(traj1);
            gripperMotor.armOut();
            sleep(250);
            gripperServos.open();
            sleep(250);
            completeShooter.highGoalSpeed();


            drive.followTrajectory(traj2);
            sleep(250);
            shooterServo.singleShot();
            sleep(200);
            shooterServo.singleShot();
            sleep(200);
            shooterServo.singleShot();
            sleep(140);

            drive.turn(Math.toRadians(180));

            startPose = traj2.end().plus(new Pose2d(0, 0, Math.toRadians(180)));

            Trajectory traj3 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-24, -49), Math.toRadians(180))
                    .splineTo(new Vector2d(-39, -49), Math.toRadians(180))
                    .build();

            drive.followTrajectory(traj3);

            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .strafeRight(8)
                    .build();

            drive.followTrajectory(traj4);

            gripperServos.close();
            sleep(250);
            gripperMotor.armIn();
            sleep(250);

            drive.turn(Math.toRadians(180));

            intakeMotor.in();

            startPose = traj4.end().plus(new Pose2d(0, 0, Math.toRadians(-180)));

            Trajectory traj5 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(29, -15.5), Math.toRadians(0))

                    .build();
            drive.followTrajectory(traj5);

            gripperMotor.armOut();
            sleep(250);
            gripperServos.open();
            sleep(250);

            Trajectory traj6 = drive.trajectoryBuilder(traj5.end(), true)
                    .splineTo(new Vector2d(2, -28.5), Math.toRadians(180))
                    .build();
            completeShooter.powerShootSpeed();
            drive.followTrajectory(traj6);
            sleep(250);
            shooterServo.singleShot();
            sleep(140);

            Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
                    .splineTo(new Vector2d(11, -28.5), Math.toRadians(0))
                    .build();
            drive.followTrajectory(traj7);

        }

        //4 disk zone C back
        if (diskDetect == 4) {
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-24, -49), Math.toRadians(0))
                    .splineTo(new Vector2d(60, -39), Math.toRadians(0))

                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end(), true)
                    .splineTo(new Vector2d(-0, -42), Math.toRadians(180))
                    .build();


            drive.followTrajectory(traj1);
            gripperMotor.armOut();
            sleep(250);
            gripperServos.open();
            sleep(250);
            completeShooter.highGoalSpeed();


            drive.followTrajectory(traj2);
            sleep(250);
            shooterServo.singleShot();
            sleep(200);
            shooterServo.singleShot();
            sleep(200);
            shooterServo.singleShot();
            sleep(140);

            drive.turn(Math.toRadians(180));

            startPose = traj2.end().plus(new Pose2d(0, 0, Math.toRadians(180)));

            Trajectory traj3 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-24, -49), Math.toRadians(180))
                    .splineTo(new Vector2d(-39, -49), Math.toRadians(180))
                    .build();

            drive.followTrajectory(traj3);

            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .strafeRight(8)
                    .build();

            drive.followTrajectory(traj4);

            gripperServos.close();
            sleep(250);
            gripperMotor.armIn();
            sleep(250);

            drive.turn(Math.toRadians(180));

            startPose = traj4.end().plus(new Pose2d(0, 0, Math.toRadians(-180)));

            Trajectory traj5 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(52, -39), Math.toRadians(0))

                    .build();
            drive.followTrajectory(traj5);

            gripperMotor.armOut();
            sleep(250);
            gripperServos.open();
            sleep(250);

            Trajectory traj6 = drive.trajectoryBuilder(traj5.end(), true)
                    .splineTo(new Vector2d(11, -39), Math.toRadians(180))
                    .build();
            drive.followTrajectory(traj6);
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

}
