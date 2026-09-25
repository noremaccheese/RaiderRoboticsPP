package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.helperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.helperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;

public class driveAndRotateTest extends OpMode {

    Follower follower = new Follower();
    private boolean strafe = false;
    private double pathDistanceX = 0;
    private double pathDistanceY = 0;
    private boolean hasAnswered = false;

    @Override
    public void init() {
        follower.init(hardwareMap);
    }

    @Override
    public void init_loop() {
        if(!hasAnswered){
            telemetry.addLine("If strafe, push a. Otherwise, press b");
            if (gamepad1.a){
                pathDistanceX = 24;
                hasAnswered = true;
            }
            else if (gamepad1.b){
                pathDistanceY = 24;
                hasAnswered = true;
            }

            if(hasAnswered){
                telemetry.addLine("Init complete");
            }
        }
    }

    @Override
    public void loop() {
        follower.runPath(pathDistanceX,pathDistanceY,90);
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
