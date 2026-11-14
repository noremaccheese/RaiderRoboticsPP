package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CameronPathing.FollowerC;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions;

@Disabled
public class ForwardTest extends OpMode {
    FollowerC followerC = new FollowerC();
    @Override
    public void init() {
        followerC.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {
        followerC.runPath(0,24,0);
        telemetry.addData("Heading", followerC.getHeading());
        telemetry.addData("Heading Error", followerC.getHeadingError());
        telemetry.addData("Is busy", followerC.isBusy());
        telemetry.addData("Current distance x", curDistanceX());
        telemetry.addData("Current distance y", curDistanceY());
        telemetry.addData("Target distance x", followerC.getTargetDistanceX());
        telemetry.addData("Target distance y", followerC.getTargetDistanceY());
        telemetry.addData("fLeftDistance", HelperFunctions.getMotorDistance(FollowerC.whichMotor.FL));
        telemetry.addData("fRightDistance", HelperFunctions.getMotorDistance(FollowerC.whichMotor.FR));
        telemetry.addData("bLeftDistance", HelperFunctions.getMotorDistance(FollowerC.whichMotor.BL));
        telemetry.addData("bRightDistance", HelperFunctions.getMotorDistance(FollowerC.whichMotor.BR));
        telemetry.update();

    }
}
