package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.BOT_HEADING;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.BOT_X;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.BOT_Y;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.OMEGA;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.VELOCITY_X;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.VELOCITY_Y;

public class TurretSOTM {

    double hiveX;
    double hiveY;

    OdoInfo info = new OdoInfo();

    public TurretSOTM(double hiveX, double hiveY){
        this.hiveX = hiveX;
        this.hiveY = hiveY;
    }



    public double calculateVPerpendicular(){
        return (-info.hiveDY() * VELOCITY_X) + (info.hiveDX() * VELOCITY_Y);
    }

    public void offsetCorrection(double offsetX, double offsetY){
        BOT_X = (offsetX*Math.cos(BOT_HEADING) + offsetY*Math.sin(BOT_HEADING)) + BOT_X;
        BOT_Y = (offsetX*Math.cos(BOT_HEADING) - offsetY*Math.sin(BOT_HEADING)) + BOT_Y;
    }


    public double calculateDistance(){
        return Math.max(Math.hypot(info.hiveDX(), info.hiveDY()), 1);
    }




    public double calculateTargetVelocity(){
        return -(OMEGA + (calculateVPerpendicular()/calculateDistance()));
    }

    public double calculateTargetPosition(){
        return Math.atan2(info.hiveDX(),info.hiveDY());
    }



    public double flightTime(double launchVelocity, double launchAngle){
        return (2*launchVelocity*Math.sin(launchAngle)/9.8);
    }

    public double velocityCorrection(){
        return calculateTargetVelocity() - (calculateVPerpendicular()* flightTime(0,0)); // add in angle
    }
}
