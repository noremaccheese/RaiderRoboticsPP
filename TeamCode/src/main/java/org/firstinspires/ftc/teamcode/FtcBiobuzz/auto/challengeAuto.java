package org.firstinspires.ftc.teamcode.FtcBiobuzz.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.pedropathing.api.Paths.*;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class challengeAuto extends OpMode {
    ElapsedTime timer = new ElapsedTime();
    private double lastTime;
    private double targetTime;
    private boolean hasRan = false;

    private DcMotor intake;



    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();




    //make Poses
    private final Pose startPose = p.of(6,6,0);
    private final Pose pickupPose1 = p.of(136,6,0);
    private final Pose alignPose1 = p.of(118,24,0);
    private final Pose alignPose2 = p.of(6,24,90);
    private final Pose pickupPose2 = p.of(6,66,90);
    private final Pose alignPose3 = p.of(136,36,90);
    private final Pose pickupPose3 = p.of(136,66,90);




    //create paths
    private Path pickup1(){
        return line(startPose,pickupPose1).tangent();
    }
    private Path align1(){
        return line(pickupPose1,alignPose1).constant(0);
    }
    private Path align2(){
        return line(alignPose1,alignPose2).linear(0,90);
    }
    private Path pickup2(){
        return line(alignPose2,pickupPose2).tangent();
    }
    private Path align3(){
        return line(pickupPose2,alignPose3).constant(90);
    }
    private Path pickup3(){
        return line(alignPose3,pickupPose3).tangent();
    }




    //set up state machine
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


    public void autoPathUpdate(){
        switch (robotState){
            case START:
                intake.setPower(1);
                robotState = RobotState.PICKUP1;
                break;

            case PICKUP1:
                if(!hasRan){
                    follower.follow(pickup1());
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
                    follower.follow(align1());
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
                    follower.follow(align2());
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    robotState = RobotState.PICKUP2;
                    intake.setPower(1);
                    hasRan = false;
                }

                break;

            case PICKUP2:
                if(!hasRan){
                    follower.follow(pickup2());
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
                    follower.follow(align3());
                    hasRan = true;
                    return;
                }
                if(!follower.isBusy()){
                    robotState = RobotState.PICKUP3;
                    intake.setPower(1);
                    hasRan = false;
                }

                break;

            case PICKUP3:
                if(!hasRan) {
                    follower.follow(pickup3());
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
                    intake.setPower(0);
                }

                break;

            case OFF:
                telemetry.addLine("Auto complete");

                break;
        }



    }

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        intake = hardwareMap.get(DcMotor.class, "intakeMotor1");

    }


    @Override
    public void start() {
        timer.reset();
    }


    @Override
    public void loop() {
        follower.update();
        autoPathUpdate();



        telemetry.addData("x",follower.pose().x());
        telemetry.addData("y", follower.pose().y());
        telemetry.addData("heading",follower.pose().heading());
        telemetry.addData("Robot state", robotState);
        telemetry.addData("Time", timer.seconds());
        telemetry.update();

    }

}