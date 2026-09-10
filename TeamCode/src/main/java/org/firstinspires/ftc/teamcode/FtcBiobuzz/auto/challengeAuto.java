package org.firstinspires.ftc.teamcode.FtcBiobuzz.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@Autonomous
public class challengeAuto extends OpMode {
    ElapsedTime timer = new ElapsedTime();
    private Follower follower;

    private double lastTime;
    private double targetTime;
    private boolean hasRan = false;

    private DcMotor intakeMotor1;

    //define paths
    private final Pose startPose = new Pose(6,6,Math.toRadians(0));
    private final Pose pickupPose1 = new Pose(136,6,Math.toRadians(0));
    private final Pose alignPose1 = new Pose(118,24,Math.toRadians(0));
    private final Pose alignPose2 = new Pose(6,24,Math.toRadians(90));
    private final Pose pickupPose2 = new Pose(6,66,Math.toRadians(90));
    private final Pose alignPose3 = new Pose(136,36,Math.toRadians(90));
    private final Pose pickupPose3 = new Pose(136,66,Math.toRadians(90));

    private enum RobotState{
        START,
        PICKUP1,
        ALIGN1,
        ALIGN2,
        PICKUP2,
        ALIGN3,
        PICKUP3,
        IDLE,
        OFF
    }

    RobotState robotState = RobotState.START;
    RobotState nextState;

    //create pathchain
    private PathChain pickup1, align1, align2, pickup2, align3, pickup3;

    public void buildPaths(){

        pickup1 = follower.pathBuilder()
                .addPath(new BezierLine(startPose,pickupPose1))
                .setLinearHeadingInterpolation(startPose.getHeading(),pickupPose1.getHeading())
                .build();

        align1 = follower.pathBuilder()
                .addPath(new BezierLine(pickupPose1,alignPose1))
                .setLinearHeadingInterpolation(pickupPose1.getHeading(),alignPose1.getHeading())
                .build();

        align2 = follower.pathBuilder()
                .addPath(new BezierLine(alignPose1,alignPose2))
                .setLinearHeadingInterpolation(alignPose1.getHeading(),alignPose2.getHeading())
                .build();

        pickup2 = follower.pathBuilder()
                .addPath(new BezierLine(alignPose2,pickupPose2))
                .setLinearHeadingInterpolation(alignPose2.getHeading(),pickupPose2.getHeading())
                .build();

        align3 = follower.pathBuilder()
                .addPath(new BezierLine(pickupPose2,alignPose3))
                .setLinearHeadingInterpolation(pickupPose2.getHeading(),alignPose3.getHeading())
                .build();

        pickup3 = follower.pathBuilder()
                .addPath(new BezierLine(alignPose3, pickupPose3))
                .setLinearHeadingInterpolation(alignPose3.getHeading(),pickupPose3.getHeading())
                .build();

    }

    public void autoPathUpdate(){
        switch (robotState){
            case START:
                intakeMotor1.setPower(1);
                robotState = RobotState.PICKUP1;
                break;

            case PICKUP1:
                if(!hasRan){
                    follower.followPath(pickup1, true);
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    lastTime = timer.seconds();
                    targetTime = 0.5;
                    robotState = RobotState.IDLE;
                    nextState = RobotState.ALIGN1;
                    hasRan = false;
                }

                break;

            case ALIGN1:
                if(!hasRan){
                    follower.followPath(align1, true);
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    robotState = RobotState.ALIGN2;
                    hasRan = false;
                }
                break;

            case ALIGN2:
                if(!hasRan){
                    follower.followPath(align2, true);
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    robotState = RobotState.PICKUP2;
                    intakeMotor1.setPower(1);
                    hasRan = false;
                }

                break;

            case PICKUP2:
                if(!hasRan){
                    follower.followPath(pickup2, true);
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    lastTime = timer.seconds();
                    targetTime = 0.5;
                    robotState = RobotState.IDLE;
                    nextState = RobotState.ALIGN3;
                    hasRan = false;

                }

                break;

            case ALIGN3:
                if(!hasRan) {
                    follower.followPath(align3, true);
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    robotState = RobotState.PICKUP3;
                    intakeMotor1.setPower(1);
                    hasRan = false;
                }

                break;

            case PICKUP3:
                if(!hasRan) {
                    follower.followPath(pickup3, true);
                    hasRan = true;
                    return;
                }

                if(!follower.isBusy()){
                    lastTime = timer.seconds();
                    targetTime = 0.5;
                    robotState = RobotState.IDLE;
                    nextState = RobotState.OFF;
                    hasRan = false;
                }

                break;

            case IDLE:
                if((timer.seconds()-lastTime) >= targetTime){
                    robotState = nextState;
                    intakeMotor1.setPower(0);
                }

                break;

            case OFF:
                telemetry.addLine("Auto complete");

                break;
        }



    }


    @Override
    public void init() {
        intakeMotor1 = hardwareMap.get(DcMotor.class, "intakeMotor1");

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
        telemetry.addLine("Init complete");
        telemetry.update();
    }

    @Override
    public void start(){
        timer.reset();
    }

    @Override
    public void loop() {
        follower.update();
        autoPathUpdate();


        telemetry.addData("x",follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading",follower.getPose().getHeading());
        telemetry.addData("Robot state", robotState);
        telemetry.addData("Time", timer.seconds());
        telemetry.update();


    }

}