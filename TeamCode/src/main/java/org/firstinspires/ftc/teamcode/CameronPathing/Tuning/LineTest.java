package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;

@TeleOp
public class LineTest extends OpMode {
    Follower follower = new Follower();
    private boolean forward = true;
    private final double lineDistance = 24;

    public void runForwardPath(){
        follower.runPath(0,lineDistance,0);
    }
    public void runBackwardPath(){
        follower.runPath(0,-lineDistance,0);
    }

    public void init(){
        follower.init(hardwareMap);
        telemetry.addLine("init complete");
    }


    public void loop(){
        if(forward){
            runForwardPath();
        }
        else if(!forward){
            runBackwardPath();
        }

        if (!follower.isBusy()){
            forward = !forward;
        }

        telemetry.addData("Heading", follower.getHeading());
        telemetry.addData("Heading Error", follower.getHeadingError());
        telemetry.addData("Is busy", follower.isBusy());
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
