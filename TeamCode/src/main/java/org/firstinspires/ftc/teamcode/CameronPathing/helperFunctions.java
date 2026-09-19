package org.firstinspires.ftc.teamcode.CameronPathing;

import static org.firstinspires.ftc.teamcode.CameronPathing.constants.CPR;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.bLeft;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.bRight;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.circumference;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.fLeft;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.fRight;

public class helperFunctions {



    public static double getMotorDistance(follower.whichMotor whichMotor) {
        double motorDistance = 0;
        if (whichMotor == follower.whichMotor.FL) {
            motorDistance = getDistance(fLeft.getCurrentPosition());
        }

        if (whichMotor == follower.whichMotor.FR) {
            motorDistance = getDistance(fRight.getCurrentPosition());
        }

        if (whichMotor == follower.whichMotor.BL) {
            motorDistance = getDistance(bLeft.getCurrentPosition());
        }
        if (whichMotor == follower.whichMotor.BR) {
            motorDistance = getDistance(bRight.getCurrentPosition());
        }

        return motorDistance;
    }


    public static double getDistance(double position){
        double revolutions = position/CPR;
        double distance = circumference * revolutions;

        return distance;

    }

    public static double curDistanceY(){
        return (getDistance(fLeft.getCurrentPosition()) + getDistance(fRight.getCurrentPosition()) + getDistance(bLeft.getCurrentPosition()) + getDistance(bRight.getCurrentPosition()))/4;
    }


    public static double curDistanceX(){
        return (getDistance(fLeft.getCurrentPosition()) - getDistance(fRight.getCurrentPosition()) - getDistance(bLeft.getCurrentPosition()) + getDistance(bRight.getCurrentPosition()))/4;
    }
}
