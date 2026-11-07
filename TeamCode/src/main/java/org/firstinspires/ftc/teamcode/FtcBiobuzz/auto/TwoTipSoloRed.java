package org.firstinspires.ftc.teamcode.FtcBiobuzz.auto;





import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.INTAKE;


import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem.ShooterV1;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants;
import org.firstinspires.ftc.teamcode.pedro.Constants;


public class TwoTipSoloRed extends OpMode {
    BotConstants botConstants = new BotConstants();
    Follower follower;
    ShooterV1 shooter = new ShooterV1();

    ElapsedTime timer = new ElapsedTime();


    private boolean hasRan = false;
    private boolean timerHasRan = false;
    double lastTime;
    private boolean pathDone;
    private boolean runIntake = true;
    private boolean runShooter = true;
    private double waitTime = 0;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(64, 9, 90); // adjust as needed
    private final Pose point1 = poseFactory.of(9, 9, 180);
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
        return Paths.line(start, point1).tangent();
    }

    public Path path2() {
        return Paths.curve(point1, point2Control1, point2).heading(Interpolator.piecewise().until(0.4974, Interpolator.constant(point2Segment1Heading)).until(1, Interpolator.linear(point2Segment2Start, point2Segment2End)));
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

    private void runPath(Path curPath){
        if(!hasRan){
            follower.follow(curPath);
            hasRan = true;
            pathDone = false;
            return;
        }
        if(!follower.isBusy()){
            hasRan = false;
            pathDone = true;
        }

    }

    private boolean timeHasPassed(double time){
        if(!timerHasRan){
            lastTime = timer.nanoseconds();
        }


        if(timer.nanoseconds() - lastTime > time * 10e9){
            return true;
        }
        else {
            return false;
        }

    }



    BotState state = BotState.START;
    BotState nextState;


    private void runAutoPath(){
        switch (state){
            case PATH1:
                runPath(path1());
                if(pathDone){
                    runIntake = true;
                    runShooter = false;
                    waitTime = 2;
                    state = BotState.RUN_MECHANISMS;
                    nextState = BotState.PATH2;
                }

                break;

            case PATH2:
                runPath(path2());
                if(pathDone){
                    runIntake = false;
                    runShooter = true;
                    waitTime = 4;
                    state = BotState.RUN_MECHANISMS;
                    nextState = BotState.PATH3;
                }

                break;


            case PATH3:
                runPath(path3());
                if(pathDone){
                    runIntake = true;
                    runShooter = false;
                    waitTime = 2;
                    state = BotState.RUN_MECHANISMS;
                    nextState = BotState.SHOOT_WHILE_STOPPED;
                }

                break;

            case SHOOT_WHILE_STOPPED:
                runShooter = true;
                waitTime = 4;
                state = BotState.RUN_MECHANISMS;
                nextState = BotState.PATH4;
                break;


            case PATH4:
                runPath(path4());
                break;


            case RUN_MECHANISMS:
                if(runIntake){
                    INTAKE.setPower(1);
                }
                else {
                    INTAKE.setPower(0);
                }


                if(runShooter){
                    //add in later

                }
                else {
                    //turn off shooter
                }

                if(timeHasPassed(waitTime)){
                    state = nextState;
                }
                break;

        }


    }


    @Override
    public void init() {
        botConstants.initMotors(hardwareMap);
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        shooter.init(hardwareMap);
        telemetry.addLine("init complete");
    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void loop() {
        runAutoPath();
    }
}
