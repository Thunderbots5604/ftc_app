package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
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

@Autonomous(name="Autonomous_Foundation Red", group="Foundation")
public class AutoFoundationRed extends GodFatherOfAllAutonomous {

    private boolean red = true;

    @Override
    public void runOpMode() {
        foundation = true;
        red = true;
        initialization();

        waitForStart();

        runTo(30, allPower, slowPower);
        runTo(15, allPower * .7, slowPower);
        moveFoundation(red);
        runTo(10, allPower, slowPower);
        runTo(-10, allPower, slowPower);
        turnLeft(90, allPower, slowPower);
        turnTo(0, allPower, slowPower);
        runTo(20, allPower, slowPower);
        strafeLeft(30, allPower, slowPower);
        //Go to far side
        runTo(30, allPower, slowPower);
    }
}