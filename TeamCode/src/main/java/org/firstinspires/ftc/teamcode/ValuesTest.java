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

@Autonomous(name="Value Test", group="Test")
public class ValuesTest extends GodFatherOfAllAutonomous {

    private double distance;
    private double heading1;
    private double heading2;
    private double heading3;
    @Override
    public void runOpMode() {
        initialization();

        waitForStart();


        while(opModeIsActive()) {

            telemetry.addData("Angle 1", heading1);
            telemetry.addData("Angle 2", heading2);
            telemetry.addData("Angle 3", heading3);
            telemetry.update();
            heading1 = 0; heading2 = 0; heading3 = 0;
            runtime.reset();
            while (runtime.milliseconds() < 1000 && heading1 == 0 && heading2 == 0 && heading3 == 0) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading1 = formatAngle(angles.angleUnit, angles.firstAngle);
                heading2 = formatAngle(angles.angleUnit, angles.secondAngle);
                heading3 = formatAngle(angles.angleUnit, angles.thirdAngle);
            }
        }
    }
}