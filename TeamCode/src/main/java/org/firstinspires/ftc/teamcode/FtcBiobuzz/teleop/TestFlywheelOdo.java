package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem.FlywheelMechanism;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem.ShooterV1;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class TestFlywheelOdo extends OpMode {
    boolean hasPressed = false;
    private double[] stepSizes = {5,10,50,100};
    private int stepIndex = 0;
    OdoInfo info = new OdoInfo();
    Follower follower;
    ShooterV1 shooter = new ShooterV1();
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(9, 8.75, 0);

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        shooter.init(hardwareMap);
        info.init(follower);
        telemetry.addLine("Init complete");
    }


    @Override
    public void loop() {
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

        telemetry.addData("target velocity", shooter.getTargetVelocity());
        telemetry.addData("cur velocity", shooter.flywheelVelocity());
        telemetry.addData("Distance", info.distanceToHive());
        telemetry.update();

    }
}
