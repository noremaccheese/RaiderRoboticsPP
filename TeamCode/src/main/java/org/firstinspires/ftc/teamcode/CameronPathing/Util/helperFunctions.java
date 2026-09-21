package org.firstinspires.ftc.teamcode.CameronPathing.Util;

import static org.firstinspires.ftc.teamcode.CameronPathing.Util.Constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.Util.Constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.CameronPathing.Util.Constants.CIRCUMFERENCE;
import static org.firstinspires.ftc.teamcode.CameronPathing.Util.Constants.CPR;
import static org.firstinspires.ftc.teamcode.CameronPathing.Util.Constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.Util.Constants.F_RIGHT;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;


public class helperFunctions {



    public static double getMotorDistance(Follower.whichMotor whichMotor) {
        double motorDistance = 0;
        if (whichMotor == Follower.whichMotor.FL) {
            motorDistance = getDistance(F_LEFT.getCurrentPosition());
        }

        if (whichMotor == Follower.whichMotor.FR) {
            motorDistance = getDistance(F_RIGHT.getCurrentPosition());
        }

        if (whichMotor == Follower.whichMotor.BL) {
            motorDistance = getDistance(B_LEFT.getCurrentPosition());
        }
        if (whichMotor == Follower.whichMotor.BR) {
            motorDistance = getDistance(B_RIGHT.getCurrentPosition());
        }

        return motorDistance;
    }


    public static double getDistance(double position){
        double revolutions = position/CPR;
        double distance = CIRCUMFERENCE * revolutions;

        return distance;

    }

    public static double curDistanceY(){
        return (getDistance(F_LEFT.getCurrentPosition()) + getDistance(F_RIGHT.getCurrentPosition()) + getDistance(B_LEFT.getCurrentPosition()) + getDistance(B_RIGHT.getCurrentPosition()))/4;
    }


    public static double curDistanceX(){
        return (getDistance(F_LEFT.getCurrentPosition()) - getDistance(F_RIGHT.getCurrentPosition()) - getDistance(B_LEFT.getCurrentPosition()) + getDistance(B_RIGHT.getCurrentPosition()))/4;
    }
}
