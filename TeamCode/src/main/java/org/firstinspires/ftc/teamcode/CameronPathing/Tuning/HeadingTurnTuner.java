package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;

public class HeadingTurnTuner extends OpMode {
    private double scalar = 0;
    private boolean hasPressed = false;
    private double[] stepSizes = {0.001,0.01,0.1,1};
    private int stepIndex = 0;
    Follower follower = new Follower();
    @Override
    public void init() {
        follower.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {

        if(gamepad1.dpad_up && !hasPressed){
            scalar += stepSizes[stepIndex];
            hasPressed = true;
        }
        else if(gamepad1.dpad_down && !hasPressed){
            scalar -= stepSizes[stepIndex];
            hasPressed = true;
        }

        if(gamepad1.right_bumper && !hasPressed){
            stepIndex = stepIndex >= stepSizes.length -1 ? stepSizes.length -1: stepIndex + 1;
            hasPressed = true;
        }
        else if(gamepad1.left_bumper && !hasPressed){
            stepIndex = stepIndex <= 0 ? 0: stepIndex - 1;
            hasPressed = true;
        }
        boolean beingPressed = gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.right_bumper || gamepad1.left_bumper;
        if(!beingPressed){
            hasPressed = false;
        }
        double targetHeading = follower.getHeading() + gamepad1.right_stick_x * scalar;
        follower.runPath(0,0, targetHeading);

        telemetry.addData("Scalar", scalar);
        telemetry.addData("Step size", stepSizes[stepIndex]);
        telemetry.update();
    }
}
