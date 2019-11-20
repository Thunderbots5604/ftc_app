package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="TeleOpAlpha", group="Linear Opmode")
public class TeleOpAlpha extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime cooldown = new ElapsedTime();
    private ElapsedTime armCooldown = new ElapsedTime();
    private ElapsedTime armCooldownUp = new ElapsedTime();
    private ElapsedTime armCooldownDown = new ElapsedTime();

    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private Servo clawServo = null;
    private Servo armServo = null;

    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    private boolean positionSetClaw = true;
    private boolean positionSetArm = true;
    @Override
    public void runOpMode() {

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        armServo = hardwareMap.get(Servo.class, "arm_servo");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        cooldown.reset();
        armCooldown.reset();
        clawServo.setPosition(0.1);
        sleep(1000);
        armServo.setPosition(.1);

        while (opModeIsActive()) {
            telemetry.addData("Reversed: ", reversed);
            telemetry.addData("Half Speed" , halfSpeed);
            telemetry.update();

            if((gamepad1.y) && cooldown.seconds() > .5) {
                reversed = !reversed;
                multiplier *= -1;
                cooldown.reset();
            }
            if((gamepad1.x) && cooldown.seconds() > .5) {
                halfSpeed = !halfSpeed;
                if (halfSpeed) {
                    multiplier *= .5;
                }
                else {
                    multiplier *= 2;
                }
                cooldown.reset();
            }

            //Moving Forward
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }

            //Point Turn
            //If right stick is moving on the x axis
            else if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x != 0) {
                leftMotorFront.setPower(Math.abs(multiplier) * gamepad1.right_stick_x);
                leftMotorBack.setPower(Math.abs(multiplier) * gamepad1.right_stick_x);
                rightMotorFront.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x);
                rightMotorBack.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x);
                telemetry.addData("Rgiht stick", gamepad1.right_stick_x);
            }

            //Swerve Turn
            //Left Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && !reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
            }
            //Right Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && !reversed) {
                leftMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                leftMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Reverse Left Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && reversed) {
                leftMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                leftMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Reverse Right Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Claw closing and arm upping
            if (gamepad1.right_bumper && !gamepad1.left_bumper && armCooldown.milliseconds() > 2000) {
                armCooldown.reset();
                armCooldownUp.reset();
                positionSetArm = false;
                positionSetClaw = false;
            }
            if (armCooldownUp.milliseconds() < 1000 && positionSetClaw == false) {
                clawServo.setPosition(1);
                positionSetClaw = true;
            }
            else if (armCooldownUp.milliseconds() > 1000 && armCooldownUp.milliseconds() < 2000 && positionSetArm == false) {
                armServo.setPosition(1);
                positionSetArm = true;
            }
            //Claw opening and arm downing
            if (!gamepad1.right_bumper && gamepad1.left_bumper && armCooldown.milliseconds() > 2000) {
                armCooldown.reset();
                armCooldownDown.reset();
            }
            if (armCooldownDown.milliseconds() < 1000 && positionSetClaw == false) {
                clawServo.setPosition(.1);
                positionSetClaw = true;
            }
            else if (armCooldownDown.milliseconds() > 1000 && armCooldownDown.milliseconds() < 2000 && positionSetArm == false) {
                armServo.setPosition(.1);
                positionSetArm = true;
            }
            //Resets armCooldown
            if (armCooldown.milliseconds() > 2000 && armCooldown.milliseconds() < 3000) {
                positionSetArm = false;
                positionSetClaw = false;
            }
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}