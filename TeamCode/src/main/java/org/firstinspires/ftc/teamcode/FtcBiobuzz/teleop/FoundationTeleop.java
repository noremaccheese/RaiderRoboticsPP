package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.INTAKE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.INTAKE_SERVO;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.TRANSFER_MOTOR;


import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp

public class FoundationTeleop extends OpMode {
    double kV = 0.6;
    boolean hasPressed = false;
    private Follower follower;
    private BotConstants constants = new BotConstants();
    private Shooter shooter = new Shooter();
    double intakePower = 0;
    double transferPower = 0;
    private double[] stepSizes = {5,10,50,100};
    private int stepIndex = 0;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        shooter.init(hardwareMap);
        constants.initMotors(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                gamepad1.left_stick_x,
                -gamepad1.left_stick_y,
                gamepad1.right_stick_x,
                follower.pose().heading()
        );


        ManualDrive.driveOrHold(follower, powers);

        if(gamepad1.right_trigger >= 0.5 && !hasPressed){
            shooter.setTargetVelocity(shooter.getTargetVelocity() + 50);
            //kV += stepSizes[stepIndex];
            hasPressed = true;
        }
        if(gamepad1.left_trigger >= 0.5 && !hasPressed){
            shooter.setTargetVelocity(shooter.getTargetVelocity() - 50);
            //kV -= stepSizes[stepIndex];
            hasPressed = true;
        }

        if(gamepad1.dpad_up && !hasPressed){
            stepIndex = stepIndex >= stepSizes.length - 1? stepSizes.length - 1: stepIndex + 1;
            hasPressed = true;
        }
        else if(gamepad1.dpad_down && !hasPressed){
            stepIndex = stepIndex <= 0 ? 0: stepIndex - 1;
            hasPressed = true;
        }

        if((gamepad1.left_trigger < 0.5 && gamepad1.right_trigger < 0.5 && hasPressed && !gamepad1.dpad_up && !gamepad1.dpad_down)){
            hasPressed = false;
        }



        follower.update();
        shooter.update(gamepad1);

        if(gamepad1.left_bumper){
            intakePower = -0.5;
        }
        else if(gamepad1.right_bumper){
            intakePower = 1;
        }
        else {
            intakePower = shooter.intakePower();
        }





        double transferPower = gamepad1.x ? -1: shooter.transferPower();
        double servoPower = shooter.getServoPower();


        INTAKE.setPower(intakePower);
        TRANSFER_MOTOR.setPower(transferPower);
        INTAKE_SERVO.setPower(servoPower);


        Pose robotPose = follower.pose(); // returns a Pose object

        telemetry.addData("kv", kV);
        telemetry.addData("step size", stepSizes[stepIndex]);
        telemetry.addData("transfer power", transferPower);
        telemetry.addData("Servo power", servoPower);
        telemetry.addData("Target velocity", shooter.getTargetFlywheelVelocity());
        telemetry.addData("Flywheel rpm", shooter.flywheelVelocity());
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

    }
}
