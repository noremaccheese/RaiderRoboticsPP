package org.firstinspires.ftc.teamcode.FtcBiobuzz.auto;





import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;


public class TwoTipSoloRed extends OpMode {
    Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(64, 9, 90); // adjust as needed
    private final Pose path1 = poseFactory.of(9, 9, 180);
    private final Pose point2 = poseFactory.of(45, 127, 90);
    private final Pose point2Control1 = poseFactory.of(13.3552, 66.7039, 0);
    private final Pose point2Segment1Heading = poseFactory.of(45, 127, 180);
    private final Pose point2Segment2Start = poseFactory.of(45, 127, 180);
    private final Pose point2Segment2End = poseFactory.of(45, 127, 90);
    private final Pose point3 = poseFactory.of(45, 134, 90);
    private final Pose point4Start = poseFactory.of(45, 134, 90);
    private final Pose point4 = poseFactory.of(9.2368, 96.8391, 90);
    private final Pose point4Control1 = poseFactory.of(13.067, 56.2925, 0);

    public Path path1() {
        return Paths.line(start, path1).tangent();
    }

    public Path path2() {
        return Paths.curve(path1, point2Control1, point2).heading(Interpolator.piecewise().until(0.4974, Interpolator.constant(point2Segment1Heading)).until(1, Interpolator.linear(point2Segment2Start, point2Segment2End)));
    }

    public Path path3() {
        return Paths.curve(point2, point3).tangent();
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4).linear(point4Start, point4);
    }



    private enum BotState{
        START,
        RUN_MECHANISMS,
        PATH1,
        PATH2,
        PATH3,
        SHOOT_WHILE_STOPPED,
        PATH4

    }

    BotState state = BotState.START;


    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        telemetry.addLine("init complete");
    }


    @Override
    public void loop() {

    }
}
