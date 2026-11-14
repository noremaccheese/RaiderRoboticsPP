package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.CameronPathing.FollowerC;

public class DriveAndRotateTest extends OpMode {

    FollowerC followerC = new FollowerC();
    private boolean strafe = false;
    private double pathDistanceX = 0;
    private double pathDistanceY = 0;
    private boolean hasAnswered = false;

    @Override
    public void init() {
        followerC.init(hardwareMap);
        telemetry.addLine("Init complete");
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
        followerC.runPath(pathDistanceX,pathDistanceY,90);
        telemetry.addData("Heading", followerC.getHeading());
        telemetry.addData("Heading Error", followerC.getHeadingError());
        telemetry.addData("Is busy", followerC.isBusy());
        telemetry.addData("Current distance x", curDistanceX());
        telemetry.addData("Current distance y", curDistanceY());
        telemetry.addData("Target distance x", followerC.getTargetDistanceX());
        telemetry.addData("Target distance y", followerC.getTargetDistanceY());
        telemetry.addData("fLeftDistance", followerC.getMotorDistance(FollowerC.whichMotor.FL));
        telemetry.addData("fRightDistance", followerC.getMotorDistance(FollowerC.whichMotor.FR));
        telemetry.addData("bLeftDistance", followerC.getMotorDistance(FollowerC.whichMotor.BL));
        telemetry.addData("bRightDistance", followerC.getMotorDistance(FollowerC.whichMotor.BR));
        telemetry.update();
    }
}
