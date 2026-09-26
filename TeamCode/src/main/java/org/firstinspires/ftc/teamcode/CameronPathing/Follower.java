package org.firstinspires.ftc.teamcode.CameronPathing;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.END_PATH_TOLERANCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.FORWARD_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.FORWARD_KP;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.HEADING_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.HEADING_KP;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.HEADING_TOLERANCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.MAX_POWER;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.STRAFE_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.STRAFE_KP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.ForwardPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.HeadingPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.StrafePID;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions;

public class Follower extends HelperFunctions {
    Constants constants = new Constants();

    public enum whichMotor{
        FL,
        FR,
        BL,
        BR
    }

    private double targetDistanceY = 0;
    private double targetDistanceX = 0;


    HeadingPID headingPID = new HeadingPID();
    ForwardPID forwardPID = new ForwardPID();
    StrafePID strafePID = new StrafePID();




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
