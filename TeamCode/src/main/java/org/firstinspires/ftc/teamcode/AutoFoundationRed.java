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

@Autonomous(name="Autonomous_Foundation Red", group="Autonomous Competition")
public class AutoFoundationRed extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();

        telemetry.addData("Should be set up", "so that it can go straight and not hit the foundation.");
        waitForStart();

        //position to move foundation into base
        runTo(60, allPower);
        accurateTurnRight(90, .25);
        runTo(24, allPower);
        accurateTurnRight(180, .25);
        //RAMMING SPEED
        runTo(80, 1); //Add a bit of distance to overcome any resistance
        //Go back to the line
        runTo(-10, allPower);
        accurateTurnRight(270, .25);
        runTo(48, allPower);
        //This hasn't been tested yet. Hopefully Accurate Turn works.
    }
}
