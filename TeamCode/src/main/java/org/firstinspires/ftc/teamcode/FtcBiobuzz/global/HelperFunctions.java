package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CIRCUMFERENCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_RIGHT;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;


public class HelperFunctions {



    public static double getMotorDistance(Follower.whichMotor whichMotor) {
        double motorDistance = 0;
        if (whichMotor == Follower.whichMotor.FL) {
            motorDistance = getMotorDistance(F_LEFT.getCurrentPosition());
        }

        if (whichMotor == Follower.whichMotor.FR) {
            motorDistance = getMotorDistance(F_RIGHT.getCurrentPosition());
        }

        if (whichMotor == Follower.whichMotor.BL) {
            motorDistance = getMotorDistance(B_LEFT.getCurrentPosition());
        }
        if (whichMotor == Follower.whichMotor.BR) {
            motorDistance = getMotorDistance(B_RIGHT.getCurrentPosition());
        }

        return motorDistance;
    }


    public static double getMotorDistance(double position){
        double revolutions = position/CPR;
        double distance = CIRCUMFERENCE * revolutions;

        return distance;

    }

    public static double curDistanceY(){
        return (getMotorDistance(F_LEFT.getCurrentPosition()) + getMotorDistance(F_RIGHT.getCurrentPosition()) + getMotorDistance(B_LEFT.getCurrentPosition()) + getMotorDistance(B_RIGHT.getCurrentPosition()))/4;
    }


    public static double curDistanceX(){
        return (getMotorDistance(F_LEFT.getCurrentPosition()) - getMotorDistance(F_RIGHT.getCurrentPosition()) - getMotorDistance(B_LEFT.getCurrentPosition()) + getMotorDistance(B_RIGHT.getCurrentPosition()))/4;
    }
}
