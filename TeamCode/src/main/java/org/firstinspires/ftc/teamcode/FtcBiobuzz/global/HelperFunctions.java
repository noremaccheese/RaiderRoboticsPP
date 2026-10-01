package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CIRCUMFERENCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_RIGHT;

import com.qualcomm.robotcore.hardware.DcMotor;

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
        return 0.79 * 0.92 *(getMotorDistance(F_LEFT.getCurrentPosition()) - getMotorDistance(F_RIGHT.getCurrentPosition()) - getMotorDistance(B_LEFT.getCurrentPosition()) + getMotorDistance(B_RIGHT.getCurrentPosition()))/4;
    }

    public void resetEncoders(){
        F_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        F_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
