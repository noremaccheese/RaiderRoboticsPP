package org.firstinspires.ftc.teamcode.CameronPathing;

import static org.firstinspires.ftc.teamcode.CameronPathing.constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.END_PATH_TOLERANCE;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.FORWARD_KD;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.FORWARD_KP;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.F_RIGHT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.HEADING_KD;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.HEADING_KP;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.HEADING_TOLERANCE;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.MAX_POWER;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.STRAFE_KD;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.STRAFE_KP;
import static org.firstinspires.ftc.teamcode.CameronPathing.helperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.CameronPathing.helperFunctions.curDistanceY;
import static org.firstinspires.ftc.teamcode.CameronPathing.helperFunctions.getDistance;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.forwardPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.headingPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.strafePID;

public class follower {
    constants constants = new constants();

    public enum whichMotor{
        FL,
        FR,
        BL,
        BR
    }

    private double targetDistanceY = 0;
    private double targetDistanceX = 0;


    headingPID headingPID = new headingPID();
    forwardPID forwardPID = new forwardPID();
    strafePID strafePID = new strafePID();




    public void init(HardwareMap h){
        constants.initMotors(h);
        headingPID.init(h);
        forwardPID.init(h);
        strafePID.init(h);
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



    public void runPath(double distanceX, double distanceY, double targetHeading){


        targetDistanceY = distanceY-curDistanceY();
        targetDistanceX = distanceX - curDistanceX();




        double headingCorrection = headingPID.runPID(HEADING_KP,HEADING_KD,targetHeading);
        double forwardPIDValue = forwardPID.runPID(FORWARD_KP,FORWARD_KD,targetDistanceY);
        double strafePIDValue = strafePID.runPID(STRAFE_KP,STRAFE_KD,targetDistanceX);



        F_LEFT.setPower(Range.clip(forwardPIDValue + strafePIDValue - headingCorrection, -MAX_POWER,MAX_POWER));
        F_RIGHT.setPower(Range.clip(forwardPIDValue - strafePIDValue +headingCorrection, -MAX_POWER,MAX_POWER));
        B_LEFT.setPower(Range.clip(forwardPIDValue - strafePIDValue -headingCorrection, -MAX_POWER,MAX_POWER));
        B_RIGHT.setPower(Range.clip(forwardPIDValue  + strafePIDValue +headingCorrection, -MAX_POWER,MAX_POWER));



    }

    public double getMotorDistance(whichMotor whichMotor) {
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


    public double getTargetDistanceY(){
        return targetDistanceY;
    }

    public double getTargetDistanceX(){
        return targetDistanceX;
    }


    public boolean isBusy() {
        return Math.abs(targetDistanceX) > END_PATH_TOLERANCE
                || Math.abs(targetDistanceY) > END_PATH_TOLERANCE
                || Math.abs(headingPID.getError()) > HEADING_TOLERANCE;
    }

    public double getHeading(){
        return headingPID.getHeading();
    }
    public double getHeadingError(){
        return headingPID.getError();
    }


}
