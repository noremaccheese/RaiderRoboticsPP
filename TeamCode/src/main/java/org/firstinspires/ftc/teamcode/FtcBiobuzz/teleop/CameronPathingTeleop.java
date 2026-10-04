package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;

public class CameronPathingTeleop extends OpMode {
    Follower follower = new Follower();
    private enum opmodeType{
        FIELD_CENTRIC,
        BOT_CENTRIC,
        NULL
    }

    opmodeType type = opmodeType.NULL;
    double scalar = 1;
    @Override
    public void init() {
        follower.init(hardwareMap);
    }

    @Override
    public void init_loop() {
        while (type == opmodeType.NULL){
            telemetry.addLine("Field centric: a   Bot centric: b");
            if(gamepad1.a){
                type = opmodeType.FIELD_CENTRIC;
            }
            if(gamepad1.b){
                type = opmodeType.BOT_CENTRIC;
            }
        }

        telemetry.addData("Opmode type", type);
        telemetry.addLine("Init complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;

        double targetHeading = follower.getHeading() + rx * scalar;

        if(type == opmodeType.FIELD_CENTRIC){
            follower.runManualDrive(x,y,targetHeading,false);
        }
        if(type == opmodeType.BOT_CENTRIC){
            follower.runManualDrive(x,y,targetHeading,true);
        }



    }
}
