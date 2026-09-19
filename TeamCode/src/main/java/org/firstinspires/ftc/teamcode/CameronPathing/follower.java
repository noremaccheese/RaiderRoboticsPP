package org.firstinspires.ftc.teamcode.CameronPathing;

import static org.firstinspires.ftc.teamcode.CameronPathing.constants.bLeft;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.bRight;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.fLeft;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.fRight;
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

    private boolean isBusy = true;

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
        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        fLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }





    public void runPath(double distanceX, double distanceY, double targetHeading){


        targetDistanceY = distanceY-curDistanceY();
        targetDistanceX = distanceX - curDistanceX();

        if(targetDistanceY < 0 && distanceY >= 0){
            targetDistanceY = 0;
        }
        else if (targetDistanceY > 0 && distanceY <= 0) {
            targetDistanceY = 0;
        }
        if(targetDistanceX < 0 && distanceX >= 0){
            targetDistanceX = 0;
        }
        else if (targetDistanceX > 0 && distanceX <= 0) {
            targetDistanceX = 0;
        }



        double headingCorrection = headingPID.runPID(0.015,0.001,targetHeading);
        double forwardPIDValue = forwardPID.runPID(0,0,targetDistanceY);
        double strafePIDValue = strafePID.runPID(0,0,targetDistanceX);



        fLeft.setPower(Range.clip(forwardPIDValue + strafePIDValue - headingCorrection, -1,1));
        fRight.setPower(Range.clip(forwardPIDValue - strafePIDValue +headingCorrection, -1,1));
        bLeft.setPower(Range.clip(forwardPIDValue - strafePIDValue -headingCorrection, -1,1));
        bRight.setPower(Range.clip(forwardPIDValue  + strafePIDValue +headingCorrection, -1,1));



    }

    public double getMotorDistance(whichMotor whichMotor) {
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


    public double getTargetDistanceY(){
        return targetDistanceY;
    }

    public double getTargetDistanceX(){
        return targetDistanceX;
    }


    public boolean isBusy() {
        return Math.abs(targetDistanceX) > 1.0
                || Math.abs(targetDistanceY) > 1.0
                || Math.abs(headingPID.getError()) > 5.0;
    }

    public double getHeading(){
        return headingPID.getHeading();
    }
    public double getHeadingError(){
        return headingPID.getError();
    }


}
