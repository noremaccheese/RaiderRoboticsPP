package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.CIRCUMFERENCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.F_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.imu;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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
        return 0.85 * (getMotorDistance(F_LEFT.getCurrentPosition()) + getMotorDistance(F_RIGHT.getCurrentPosition()) + getMotorDistance(B_LEFT.getCurrentPosition()) + getMotorDistance(B_RIGHT.getCurrentPosition()))/4;
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

    public static double[] convertCoordinate(double x, double y) {
        double deltaX = x - curDistanceX();
        double deltaY = y - curDistanceY();
        double theta = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


        double robotX = deltaX * Math.cos(theta) + deltaY * Math.sin(theta);
        double robotY = -deltaX * Math.sin(theta) + deltaY * Math.cos(theta);

        return new double[]{robotX, robotY};
    }

    public static double[] convertToBotCentric(double x, double y) {
        double theta = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


        double robotX = x * Math.cos(theta) + y * Math.sin(theta);
        double robotY = -x * Math.sin(theta) + y * Math.cos(theta);

        return new double[]{robotX, robotY};
    }
    public static double convertToRPM(double velocity, double CPR){
        return (velocity/CPR) * 60;
    }

    public static double convertToCPR(double velocity, double CPR){
        return (velocity*CPR)/60;
    }

    public static double getVelocity(boolean forward, double CPR){
        return forward ? convertToCPR((F_LEFT.getVelocity() + F_RIGHT.getVelocity() + B_LEFT.getVelocity() + B_RIGHT.getVelocity())/4, CPR) : convertToCPR((F_LEFT.getVelocity() - F_RIGHT.getVelocity() - B_LEFT.getVelocity() + B_RIGHT.getVelocity())/4, CPR);
    }

    public static double convertRPMToPower(double velocity, double maxRPM){
        return velocity/maxRPM;
    }

    public void eSTOP(){
        throw new RuntimeException();
    }

}
