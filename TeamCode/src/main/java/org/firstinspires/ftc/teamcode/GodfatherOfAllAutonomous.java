package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
@Disabled
@Autonomous(name="No Use", group="Autonomous Competition")
public class GodfatherOfAllAutonomous extends LinearOpMode {

    // Declare OpMode members.
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftMotorFront = null;
    public DcMotor leftMotorBack = null;
    public DcMotor rightMotorFront = null;
    public DcMotor rightMotorBack = null;
    public DcMotor crater = null;
    public DcMotor horizontal = null;
    public DcMotor crane1 = null;
    public DcMotor crane2 = null;

    public CRServo wheel = null;
    public Servo elevator = null;
    public DistanceSensor distance = null;
    public final double INCHES_PER_TICK = .0223147377;
    public final double DEGREES_PER_TICK = .17106201;

    public int location = -1;
    public int objects = 0;
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    public static final String VUFORIA_KEY = "AfDTG3b/////AAABmQnAWkqPDkVztdcIurPa8w5lO6BWlTpltO5r1m4s9oA9w3cfFdIBqJ4rjZB0We4YHcRMdc5LkWwOvJk+xcDr2VFpLP8m6zEqLWlrVNQvBnNxmGO8BeZ+xRQeQ34AgPhrqQQqeheWJbfoOn+lekIz2ZMm9f+j+1ng/X0vKDHyFGfxbtXbJuUx4Qh6E3t0esH0b3VQtbuJiOOTpWi9xFAqBsHWp+DQbwub+a6HZV5q42OabnOAyr0GZ7u1vJZs+I/Vlnf7qEMLD4RTIYA5OmMyzOdl5aikZqDSgG223ETSwcbwd3QFKewYE3oXXxkpI0vmsxCiaqBJ1oL9e6n0RXbC8Zdvn2VYwh6oSemcpp+fSjGa";

    public VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        horizontal = hardwareMap.get(DcMotor.class, "horizontal");
        crater = hardwareMap.get(DcMotor.class, "crater");
        wheel = hardwareMap.get(CRServo.class, "wheel");
        crane1 = hardwareMap.get(DcMotor.class, "crane_a");
        crane2 = hardwareMap.get(DcMotor.class, "crane_b");
        elevator = hardwareMap.get(Servo.class, "elevator");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        distance = hardwareMap.get(DistanceSensor.class, "distance");

        waitForStart();

        detach();
    }
    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);


        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.

        if (tfod != null) {
            tfod.activate();
        }

    }


    public void detach() {

        while (distance.getDistance(DistanceUnit.MM) > 100) {
            telemetry.addLine("Phase: Lowering Part 1");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane1.setPower(1);
            crane2.setPower(-1);
        }
        runtime.reset();
        while (distance.getDistance(DistanceUnit.MM) > 46) {
            telemetry.addLine("Phase: Lowering Part 2");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane1.setPower(.75);
            crane2.setPower(-.75);
        }
        runtime.reset();
        while(runtime.milliseconds() < 300) {
            crane1.setPower(.75);
            crane2.setPower(-.75);
        }
        runtime.reset();
        while(runtime.milliseconds() < 150) {
            crane1.setPower(-.55);
            crane2.setPower(.55);
        }
        crane1.setPower(0);
        crane2.setPower(0);
        sleep(100);
        if(distance.getDistance(DistanceUnit.MM) < 80) {
            runTo(1, .25);
            sleep(100);
            turnRight(120, .55);
            //160 deg is 180 deg
            runTo(10, .45);
            turnRight(32, .45);
        }
    }
    public int tfodDetection(double timeOut) {
        runtime.reset();
        while (runtime.seconds() < timeOut) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.

                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    objects = updatedRecognitions.size();
                    if (updatedRecognitions.size() == 2) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;

                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getTop();
                                telemetry.addLine("Right");
                                telemetry.addData("getTop?", recognition.getTop());
                                telemetry.update();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getTop();
                                telemetry.addLine("Center");
                                telemetry.addData("getTop?", recognition.getTop());
                                telemetry.update();
                            } else {
                                silverMineral2X = (int) recognition.getTop();
                                telemetry.addLine("Left");
                                telemetry.addData("getTop?", recognition.getTop());
                                telemetry.update();
                            }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1) {
                            if (goldMineralX < silverMineral1X) {
                                telemetry.addData("Gold Mineral Position", "Center");
                                telemetry.update();
                                location = 1;
                            } else if (goldMineralX > silverMineral1X) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                telemetry.update();
                                location = 0;
                            } else {
                                telemetry.addData("Gold Mineral Position", "Left");
                                telemetry.update();
                                location = 2;
                            }
                        }
                        else {
                            telemetry.addData("Gold Mineral Position", "Left");
                            telemetry.update();
                            location = 2;
                        }
                    }
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
        if (location == 0) {
            turnRight(14, .45);
            runTo(-45, .45);
        }
        else if (location == 1) {
            turnLeft(10, .45);
            runTo(-25, .45);
        }
        else {
            location = 2;
            turnLeft(38, .45);
            runTo(-42, .45);
        }
        return location;
    }

    public void runTo(double inches, double power) {
        //.75 is max accurate
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        inches *= .925925;
        int negative = -1;
        int targetPosition = (int)(inches / INCHES_PER_TICK);
        if (inches > 0) {
            while ((leftMotorFront.getCurrentPosition() < targetPosition) && (rightMotorFront.getCurrentPosition() < targetPosition)) {
                telemetry.addData("Target Position", targetPosition);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(negative * power);
                leftMotorBack.setPower(negative * power);
                rightMotorFront.setPower(negative * power);
                rightMotorBack.setPower(negative * power);
            }
            runtime.reset();
            while(runtime.milliseconds() < 500) {
                leftMotorFront.setPower(-1 * negative * .1);
                leftMotorBack.setPower(-1 * negative * .1);
                rightMotorFront.setPower(-1 * negative * .1);
                rightMotorBack.setPower(-1 * negative * .1);
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        else {
            negative = 1;
            while ((leftMotorFront.getCurrentPosition() > targetPosition) && (rightMotorFront.getCurrentPosition() > targetPosition)) {
                telemetry.addData("Target Position", targetPosition);
                telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
                telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
                telemetry.update();
                leftMotorFront.setPower(negative * power);
                leftMotorBack.setPower(negative * power);
                rightMotorFront.setPower(negative * power);
                rightMotorBack.setPower(negative * power);
            }
            runtime.reset();
            while(runtime.milliseconds() < 500) {
                leftMotorFront.setPower(-1 * negative * .1);
                leftMotorBack.setPower(-1 * negative * .1);
                rightMotorFront.setPower(-1 * negative * .1);
                rightMotorBack.setPower(-1 * negative * .1);
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
    public void turnLeft(double degrees, double power) {
        degrees *= 1.27;

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int targetTurn = (int)(degrees / DEGREES_PER_TICK);

        while ((leftMotorFront.getCurrentPosition() < targetTurn) && (rightMotorFront.getCurrentPosition() > -targetTurn)) {
            telemetry.addData("Target Position", targetTurn);
            telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(-power);
            leftMotorBack.setPower(-power);
            rightMotorFront.setPower(power);
            rightMotorBack.setPower(power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            leftMotorFront.setPower(.1);
            leftMotorBack.setPower(.1);
            rightMotorFront.setPower(-.1);
            rightMotorBack.setPower(-.1);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void turnRight(double degrees, double power) {
        degrees *= 1.27;

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int targetTurn = (int)(degrees / DEGREES_PER_TICK);

        while ((leftMotorFront.getCurrentPosition() > -targetTurn) && (rightMotorFront.getCurrentPosition() < targetTurn)) {
            telemetry.addData("Target Position", targetTurn);
            telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(power);
            leftMotorBack.setPower(power);
            rightMotorFront.setPower(-power);
            rightMotorBack.setPower(-power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            leftMotorFront.setPower(-.1);
            leftMotorBack.setPower(-.1);
            rightMotorFront.setPower(.1);
            rightMotorBack.setPower(.1);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}

