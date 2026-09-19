package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CameronPathing.follower;

@TeleOp
public class forwardTest extends OpMode {
    follower follower = new follower();
    @Override
    public void init() {
        follower.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {
        follower.runPath(0,24,0);
        telemetry.addData("fLeftDistance", follower.getMotorDistance(org.firstinspires.ftc.teamcode.CameronPathing.follower.whichMotor.FL));
        telemetry.addData("fRightDistance", follower.getMotorDistance(org.firstinspires.ftc.teamcode.CameronPathing.follower.whichMotor.FR));
        telemetry.addData("bLeftDistance", follower.getMotorDistance(org.firstinspires.ftc.teamcode.CameronPathing.follower.whichMotor.BL));
        telemetry.addData("bRightDistance", follower.getMotorDistance(org.firstinspires.ftc.teamcode.CameronPathing.follower.whichMotor.BR));
        telemetry.update();

    }
}
