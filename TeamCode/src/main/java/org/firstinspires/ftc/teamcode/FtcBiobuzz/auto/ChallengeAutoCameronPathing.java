package org.firstinspires.ftc.teamcode.FtcBiobuzz.auto;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;

@Autonomous
public class ChallengeAutoCameronPathing extends OpMode {
    Follower follower = new Follower();

    ElapsedTime timer = new ElapsedTime();
    private double lastTime;
    private final double targetTime = 0.5;

    private DcMotor intake;

    private double intakePower = 0;
    private boolean hasRan = false;


    private enum RobotState{
        START,
        PICKUP1,
        ALIGN1,
        ALIGN2,
        ROTATE1,
        PICKUP2,
        ALIGN3,
        PICKUP3,
        IDLE,
        OFF
    }

    RobotState robotState = RobotState.START;
    RobotState nextState = RobotState.PICKUP1;

    @Override
    public void init() {
        follower.init(hardwareMap);

        intake = hardwareMap.get(DcMotor.class, "intakeMotor1");

        telemetry.addLine("Init complete");
    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void loop() {

        switch (robotState){

            case START:
                intakePower = 1;
                robotState = RobotState.PICKUP1;
                break;

            case PICKUP1:
                //follower.runPath(0,126,0);
                if(!follower.isBusy() && hasRan){
                    robotState = RobotState.IDLE;
                    nextState = RobotState.ALIGN1;
                    lastTime = timer.seconds();
                    follower.resetEncoders();
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(0,126,0);
                break;

            case ALIGN1:
                //follower.runPath(-16,-17,0);
                if(!follower.isBusy()&& hasRan){
                    robotState = RobotState.ROTATE1;
                    follower.resetEncoders();
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(-16,-17,0);

                break;

            case ROTATE1:
                //follower.runPath(0,0,90);
                if(!follower.isBusy()&& hasRan){
                    robotState = RobotState.ALIGN2;
                    follower.resetEncoders();
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(0,0,90);

                break;

            case ALIGN2:
                //follower.runPath(-109.25,0,0);
                if(!follower.isBusy()&& hasRan){
                    robotState = RobotState.PICKUP2;
                    follower.resetEncoders();
                    intakePower=1;
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(-109.25,0,90);

                break;


            case PICKUP2:
                //follower.runPath(0,39,0);
                if(!follower.isBusy()&& hasRan){
                    robotState = RobotState.IDLE;
                    nextState = RobotState.ALIGN3;
                    lastTime = timer.seconds();
                    follower.resetEncoders();
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(0,39,90);
                break;


            case ALIGN3:
                //follower.runPath(126.5,-27,0);
                if(!follower.isBusy()&& hasRan){
                    robotState = RobotState.PICKUP3;
                    follower.resetEncoders();
                    intakePower=1;
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(126.5,-27,90);

                break;

            case PICKUP3:
                //follower.runPath(0,27,0);
                if(!follower.isBusy()&& hasRan){
                    robotState = RobotState.IDLE;
                    nextState = RobotState.OFF;
                    lastTime = timer.seconds();
                    follower.resetEncoders();
                    hasRan = false;
                    return;
                }
                hasRan = true;
                follower.runPath(0,27,90);

                break;

            case IDLE:
                if((timer.seconds()-lastTime) >= targetTime){
                    robotState = nextState;
                    intakePower = 0;
                }

                break;

            case OFF:
                telemetry.addLine("Auto Complete");
                break;
        }

        intake.setPower(intakePower);


        telemetry.addData("Heading", follower.getHeading());
        telemetry.addData("Heading Error", follower.getHeadingError());
        telemetry.addData("Is busy", follower.isBusy());
        telemetry.addData("Robot state", robotState);
        telemetry.addData("Current distance x", curDistanceX());
        telemetry.addData("Current distance y", curDistanceY());
        telemetry.addData("Target distance x", follower.getTargetDistanceX());
        telemetry.addData("Target distance y", follower.getTargetDistanceY());
        telemetry.addData("fLeftDistance", follower.getMotorDistance(Follower.whichMotor.FL));
        telemetry.addData("fRightDistance", follower.getMotorDistance(Follower.whichMotor.FR));
        telemetry.addData("bLeftDistance", follower.getMotorDistance(Follower.whichMotor.BL));
        telemetry.addData("bRightDistance", follower.getMotorDistance(Follower.whichMotor.BR));
        telemetry.update();
    }
}
