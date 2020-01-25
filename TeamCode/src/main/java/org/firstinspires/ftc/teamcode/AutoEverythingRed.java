package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;


//Still Needs Testing.


@Autonomous(name="AutoEverythingRed", group="All")
public class AutoEverythingRed extends GodFatherOfAllAutonomous {
    private String color = null;
    private boolean red = true;
    @Override
    public void runOpMode() {

        initialization();

        waitForStart();

        startBlock(red);
        //Branch off based on blockNumber
        if (blockNumber == 0) {
            runTo(32 + 8 * (blockNumber), allPower, slowPower);
            raiseAndRun(1, 30, allPower, slowPower);
            turnLeft(90, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            dropBlock();
            runTo(12, allPower * .6, slowPower * .7);
            moveFoundation(red);

            strafeLeft(10, allPower, slowPower);
            raiseAndRun(0, -50, allPower * 1.2, slowPower);
            runTo(-50 - 8 * (blockNumber), allPower, slowPower);
            resetArm();
            turnLeft(90, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnRight(90, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(70 + 8 * (blockNumber), allPower, slowPower);
            raiseAndRun(2, 50, allPower, slowPower);
            dropBlock();
            raiseAndRun(0, -30, allPower, slowPower);
        }
        else if (blockNumber == 1) {
            runTo(32 + 8 * (blockNumber), allPower, slowPower);
            raiseAndRun(1, 30, allPower, slowPower);
            turnLeft(90, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            dropBlock();
            runTo(12, allPower * .6, slowPower * .7);
            moveFoundation(red);

            strafeLeft(10, allPower, slowPower);
            raiseAndRun(0, -50, allPower * 1.2, slowPower);
            runTo(-50 - 8 * (blockNumber), allPower, slowPower);
            resetArm();
            turnLeft(90, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnRight(90, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(70 + 8 * (blockNumber), allPower, slowPower);
            raiseAndRun(2, 50, allPower, slowPower);
            dropBlock();
            raiseAndRun(0, -30, allPower, slowPower);
        }
        else {
            runTo(32 + 8 * (blockNumber), allPower, slowPower);
            raiseAndRun(1, 30, allPower, slowPower);
            turnLeft(60, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            dropBlock();
            runTo(12, allPower * .6, slowPower * .7);
            moveFoundation(red);

            strafeLeft(10, allPower, slowPower);
            raiseAndRun(0, -50, allPower * 1.2, slowPower);
            runTo(-50 - 8 * (blockNumber), allPower, slowPower);
            resetArm();
            turnLeft(80, allPower, slowPower);
            turnTo(0, allPower, slowPower);
            runUntil(1.5, allPower);
            pickUpBlock();
            runTo(-3, allPower, slowPower);
            turnRight(80, allPower, slowPower);
            turnTo(-90, allPower, slowPower);
            runTo(70 + 8 * (blockNumber), allPower, slowPower);
            raiseAndRun(2, 50, allPower, slowPower);
            dropBlock();
            raiseAndRun(0, -30, allPower, slowPower);
        }
    }
}



