package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CIRCUMFERENCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_RIGHT;




public class HelperFunctions {
    public enum whichMotor{
        FL,
        FR,
        BL,
        BR,
        FW
    }



    public static double getMotorDistance(whichMotor whichMotor) {
        double motorDistance = 0;
        if (whichMotor == HelperFunctions.whichMotor.FL) {
            motorDistance = getMotorDistance(F_LEFT.getCurrentPosition());
        }

        if (whichMotor == HelperFunctions.whichMotor.FR) {
            motorDistance = getMotorDistance(F_RIGHT.getCurrentPosition());
        }

        if (whichMotor == HelperFunctions.whichMotor.BL) {
            motorDistance = getMotorDistance(B_LEFT.getCurrentPosition());
        }
        if (whichMotor == HelperFunctions.whichMotor.BR) {
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

    public static double getMotorVelocity(whichMotor whichMotor){
        double motorVelocity = 0;
        if (whichMotor == HelperFunctions.whichMotor.FL) {
            motorVelocity = F_LEFT.getVelocity();
        }

        if (whichMotor == HelperFunctions.whichMotor.FR) {
            motorVelocity = F_RIGHT.getVelocity();
        }

        if (whichMotor == HelperFunctions.whichMotor.BL) {
            motorVelocity = B_LEFT.getVelocity();
        }
        if (whichMotor == HelperFunctions.whichMotor.BR) {
            motorVelocity = B_RIGHT.getVelocity();
        }
        return motorVelocity;

    }
}
