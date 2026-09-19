package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CameronPathing.follower;

@TeleOp
public class strafeTest extends OpMode {
    follower follower = new follower();


    public void init(){
        follower.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {
        follower.runPath(24,0,0);
    }
}
