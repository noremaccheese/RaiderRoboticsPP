package org.firstinspires.ftc.teamcode.CameronPathing;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.HeadingPID;

public class Follower {

    private DcMotorEx fLeft;
    private DcMotorEx fRight;
    private DcMotorEx bLeft;
    private DcMotorEx bRight;
    private final double CPR = 384.5 ;
    private final double diameter = 4.094;
    private final double circumference = Math.PI * diameter;
    public enum whichMotor{
        FL,
        FR,
        BL,
        BR
    }

    private double targetDistanceY = 0;
    private double targetDistanceX = 0;

    private boolean isBusy = true;

    HeadingPID pid = new HeadingPID();
    public void init(HardwareMap h){
        fLeft = h.get(DcMotorEx.class, "fLeft");
        fRight = h.get(DcMotorEx.class, "fRight");
        bLeft = h.get(DcMotorEx.class, "bLeft");
        bRight = h.get(DcMotorEx.class, "bRight");

        fLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        fLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        pid.init(h);

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

    /*
    int fLeftPosition = fLeft.getCurrentPosition();
        int fRightPosition = fLeft.getCurrentPosition();
        int bLeftPosition = fLeft.getCurrentPosition();
        int bRightPosition = fLeft.getCurrentPosition();

     */
    public double getDistance(double position){
        double revolutions = position/CPR;
        double distance = circumference * revolutions;


        return distance;

    }



    public double forwardPath(double targetDistance, whichMotor whichMotor){

        double direction = Math.signum(targetDistanceY);

        double fLeftPower = 0;
        double fRightPower = 0;
        double bRightPower = 0;
        double bLeftPower = 0;

        double motorPower = 0;



        if(Math.abs(targetDistanceY) >= 48){
            fLeftPower = 0.75*direction;
            fRightPower = 0.75*direction;
            bLeftPower = 0.75*direction;
            bRightPower = 0.75*direction;

        }
        else if(Math.abs(targetDistanceY) >= 24){
            fLeftPower = 0.5*direction;
            fRightPower = 0.5*direction;
            bLeftPower = 0.5*direction;
            bRightPower = 0.5*direction;

        }
        else if(Math.abs(targetDistanceY) > 0){
            fLeftPower = 0.25*direction;
            fRightPower = 0.25*direction;
            bLeftPower = 0.25*direction;
            bRightPower = 0.25*direction;

        }

        else{
            fLeftPower = 0;
            fRightPower = 0;
            bLeftPower = 0;
            bRightPower = 0;

        }

        if(whichMotor == Follower.whichMotor.FL){
            motorPower = fLeftPower;
        }
        if(whichMotor == Follower.whichMotor.FR){
            motorPower = fRightPower;
        }
        if(whichMotor == Follower.whichMotor.BL){
            motorPower = bLeftPower;
        }
        if(whichMotor == Follower.whichMotor.BR){
            motorPower = bRightPower;
        }
        return motorPower;

    }

    public double strafePath(double targetDistance, whichMotor whichMotor){


        double direction = Math.signum(targetDistanceX);


        double fLeftPower = 0;
        double fRightPower = 0;
        double bRightPower = 0;
        double bLeftPower = 0;

        double motorPower = 0;



        if(Math.abs(targetDistanceX) >= 48){
            fLeftPower = 0.75*direction;
            fRightPower = -0.75*direction;
            bLeftPower = -0.75*direction;
            bRightPower = 0.75*direction;

        }
        else if(Math.abs(targetDistanceX) >= 24){
            fLeftPower = 0.5*direction;
            fRightPower = -0.5*direction;
            bLeftPower = -0.5*direction;
            bRightPower = 0.5*direction;

        }
        else if(Math.abs(targetDistanceX) > 0){
            fLeftPower = 0.25*direction;
            fRightPower = -0.25*direction;
            bLeftPower = -0.25*direction;
            bRightPower = 0.25*direction;

        }
        else{
            fLeftPower = 0;
            fRightPower = 0;
            bLeftPower = 0;
            bRightPower = 0;

        }

        if(whichMotor == Follower.whichMotor.FL){
            motorPower = fLeftPower;
        }
        if(whichMotor == Follower.whichMotor.FR){
            motorPower = fRightPower;
        }
        if(whichMotor == Follower.whichMotor.BL){
            motorPower = bLeftPower;
        }
        if(whichMotor == Follower.whichMotor.BR){
            motorPower = bRightPower;
        }
        return motorPower;

    }

    public void runPath(double distanceX, double distanceY, double targetHeading){
        double curDistanceY = (getDistance(fLeft.getCurrentPosition()) + getDistance(fRight.getCurrentPosition()) + getDistance(bLeft.getCurrentPosition()) + getDistance(bRight.getCurrentPosition()))/4;

        targetDistanceY = distanceY-curDistanceY;

        double curDistanceX = (getDistance(fLeft.getCurrentPosition()) - getDistance(fRight.getCurrentPosition()) - getDistance(bLeft.getCurrentPosition()) + getDistance(bRight.getCurrentPosition()))/4;
        targetDistanceX = distanceX - curDistanceX;

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


        double fLeftPower = 0;
        double fRightPower = 0;
        double bLeftPower = 0;
        double bRightPower = 0;




        if(Math.abs(targetDistanceY)>0){

            fLeftPower = forwardPath(distanceY,whichMotor.FL);
            fRightPower = forwardPath(distanceY,whichMotor.FR);
            bLeftPower = forwardPath(distanceY,whichMotor.BL);
            bRightPower =forwardPath(distanceY, whichMotor.BR);

        }

        else if (Math.abs(targetDistanceX)>0){
            fLeftPower = strafePath(distanceX,whichMotor.FL);
            fRightPower = strafePath(distanceX,whichMotor.FR);
            bLeftPower = strafePath(distanceX,whichMotor.BL);
            bRightPower = strafePath(distanceX, whichMotor.BR);
        }
        double headingCorrection = pid.runPID(0.015,0.001,targetHeading);


//kP 0.015
//kD 0.001
        fLeft.setPower(Range.clip(fLeftPower- headingCorrection, -1,1));
        fRight.setPower(Range.clip(fRightPower+headingCorrection, -1,1));
        bLeft.setPower(Range.clip(bLeftPower-headingCorrection, -1,1));
        bRight.setPower(Range.clip(bRightPower+headingCorrection, -1,1));



    }

    public double getMotorDistance(whichMotor whichMotor) {
        double motorDistance = 0;
        if (whichMotor == Follower.whichMotor.FL) {
            motorDistance = getDistance(fLeft.getCurrentPosition());
        }

        if (whichMotor == Follower.whichMotor.FR) {
            motorDistance = getDistance(fRight.getCurrentPosition());
        }

        if (whichMotor == Follower.whichMotor.BL) {
            motorDistance = getDistance(bLeft.getCurrentPosition());
        }
        if (whichMotor == Follower.whichMotor.BR) {
            motorDistance = getDistance(bRight.getCurrentPosition());
        }

        return motorDistance;
    }

    public double curDistanceY(){
        return (getDistance(fLeft.getCurrentPosition()) + getDistance(fRight.getCurrentPosition()) + getDistance(bLeft.getCurrentPosition()) + getDistance(bRight.getCurrentPosition()))/4;
    }


    public double curDistanceX(){
        return (getDistance(fLeft.getCurrentPosition()) - getDistance(fRight.getCurrentPosition()) - getDistance(bLeft.getCurrentPosition()) + getDistance(bRight.getCurrentPosition()))/4;
    }

    public double getTargetDistanceY(){
        return targetDistanceY;
    }

    public double getTargetDistanceX(){
        return targetDistanceX;
    }

    /*public boolean isBusy(){
        isBusy = true;
        if(targetDistanceX == 0 && targetDistanceY == 0 && Math.abs(pid.getError()) > 5){
            isBusy = false;
        }



        return isBusy;
    }

     */
    public boolean isBusy() {
        return Math.abs(targetDistanceX) > 1.0
                || Math.abs(targetDistanceY) > 1.0
                || Math.abs(pid.getError()) > 5.0;
    }

    public double getHeading(){
        return pid.getHeading();
    }
    public double getHeadingError(){
        return pid.getError();
    }


}
