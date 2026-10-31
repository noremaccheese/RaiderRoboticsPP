package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.FLYWHEEL_CIRCUMFERENCE;

public class ProjectileMath {

    OdoInfo info = new OdoInfo();


    public double bestVelocity(){
        double bestHoodAngle = 2*Math.atan2(info.hiveDY(), info.hiveDX());
        double gravity = 9.8;
        return Math.sqrt(2*gravity*info.hiveDY()* Math.sin(bestHoodAngle))/Math.sin(bestHoodAngle);
    }

    public double launchVelocity(double rpm){
        return rpm * FLYWHEEL_CIRCUMFERENCE * (1/60); // in meters change to real circumference
    }
}
