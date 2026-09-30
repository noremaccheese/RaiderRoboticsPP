package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions;

@TeleOp
public class HoldTest extends OpMode {
    Follower follower = new Follower();


    @Override
    public void init() {
        follower.init(hardwareMap);
        telemetry.addLine("Init complete");
    }


    @Override
    public void loop() {
        follower.runPath(0,0,0);
        telemetry.addData("Heading", follower.getHeading());
        telemetry.addData("Heading Error", follower.getHeadingError());
        telemetry.addData("Is busy", follower.isBusy());
        telemetry.addData("Current distance x", curDistanceX());
        telemetry.addData("Current distance y", curDistanceY());
        telemetry.addData("Target distance x", follower.getTargetDistanceX());
        telemetry.addData("Target distance y", follower.getTargetDistanceY());
        telemetry.addData("fLeftDistance", HelperFunctions.getMotorDistance(Follower.whichMotor.FL));
        telemetry.addData("fRightDistance", HelperFunctions.getMotorDistance(Follower.whichMotor.FR));
        telemetry.addData("bLeftDistance", HelperFunctions.getMotorDistance(Follower.whichMotor.BL));
        telemetry.addData("bRightDistance", HelperFunctions.getMotorDistance(Follower.whichMotor.BR));
        telemetry.update();
    }
}
