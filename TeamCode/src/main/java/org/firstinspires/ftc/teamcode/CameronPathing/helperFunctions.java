package org.firstinspires.ftc.teamcode.CameronPathing;

import static org.firstinspires.ftc.teamcode.CameronPathing.constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.CIRCUMFRENCE;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.CPR;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.F_RIGHT;


public class helperFunctions {



    public static double getMotorDistance(follower.whichMotor whichMotor) {
        double motorDistance = 0;
        if (whichMotor == follower.whichMotor.FL) {
            motorDistance = getDistance(F_LEFT.getCurrentPosition());
        }

        if (whichMotor == follower.whichMotor.FR) {
            motorDistance = getDistance(F_RIGHT.getCurrentPosition());
        }

        if (whichMotor == follower.whichMotor.BL) {
            motorDistance = getDistance(B_LEFT.getCurrentPosition());
        }
        if (whichMotor == follower.whichMotor.BR) {
            motorDistance = getDistance(B_RIGHT.getCurrentPosition());
        }

        return motorDistance;
    }


    public static double getDistance(double position){
        double revolutions = position/CPR;
        double distance = CIRCUMFRENCE * revolutions;

        return distance;

    }

    public static double curDistanceY(){
        return (getDistance(F_LEFT.getCurrentPosition()) + getDistance(F_RIGHT.getCurrentPosition()) + getDistance(B_LEFT.getCurrentPosition()) + getDistance(B_RIGHT.getCurrentPosition()))/4;
    }


    public static double curDistanceX(){
        return (getDistance(F_LEFT.getCurrentPosition()) - getDistance(F_RIGHT.getCurrentPosition()) - getDistance(B_LEFT.getCurrentPosition()) + getDistance(B_RIGHT.getCurrentPosition()))/4;
    }
}
