package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop.testing;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class DrivetrainSubTest extends OpMode {
    Follower follower;
    Drivetrain drivetrain = new Drivetrain(true);
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(9, 8.75, 0);

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        drivetrain.init(hardwareMap, follower);
    }



    @Override
    public void loop() {
        drivetrain.driverControlled(gamepad1);

    }
}
