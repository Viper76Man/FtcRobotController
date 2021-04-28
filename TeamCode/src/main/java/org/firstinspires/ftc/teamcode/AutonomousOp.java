package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.drive.RRMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

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

    @Override
    public void runOpMode() {
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
            tfod.setZoom(2.5, 16/9);
        }

        //Pick SampleMecanumDrive for dashboard and RRMecanumDrive for no dashboard
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //RRMecanumDrive drive = new RRMecanumDrive(hardwareMap);

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        FtcDashboard.getInstance().startCameraStream(tfod, 0);

        while(!isStarted()){
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


        waitForStart();
            tfod.shutdown();

            while(opModeIsActive()){
                telemetry.addData("Disks Detected", diskDetect);
                telemetry.update();

                //0 disks zone A front
                if (diskDetect == 0) {
                    //Drive to square A
                    //Arm out
                    //Servo open
                    //Arm in
                    //Drive high goal shot
                    //Shoot high goals
                    //Drive 2nd wobble
                    //Arm down
                    //Servo Close
                    //Arm up
                    //drive to square A
                    //Arm Down
                    //Servo open
                    //Arm up
                    //Drive to white line
                    //Stop
                }

                //1 disk zone B middle
                if (diskDetect == 1) {
                    //Drive to square B
                    //Arm out
                    //Servo open
                    //Arm in
                    //Drive high goal shot
                    //Shoot high goals
                    //Drive 2nd wobble
                    //Arm down
                    //Servo Close
                    //Arm up
                    //drive to square B
                    //Arm Down
                    //Servo open
                    //Arm up
                    //Drive to white line
                    //Stop
                }

                //4 disk zone C back
                if (diskDetect == 4) {
                    //Drive to square C
                    //Arm out
                    //Servo open
                    //Arm in
                    //Drive high goal shot
                    //Shoot high goals
                    //Drive 2nd wobble
                    //Arm down
                    //Servo Close
                    //Arm up
                    //drive to square C
                    //Arm Down
                    //Servo open
                    //Arm up
                    //Drive to white line
                    //Stop
                }
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
