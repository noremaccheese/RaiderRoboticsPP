package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;


import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.FLYWHEEL_1;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.FLYWHEEL_2;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.FLYWHEEL_CIRCUMFERENCE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.FLYWHEEL_CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.MAX_FLYWHEEL_RPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.convertRPMToPower;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.convertToCPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.convertToRPM;

import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants;

public class FlywheelMechanism {
    BotConstants constants = new BotConstants();
    double normalizedVelocity;
    Follower follower;
    CRServo turretServo;
    AnalogInput analogInput;

    public void initTurret(Follower follower, CRServo turretServo, AnalogInput analogInput) {
        this.follower = follower;
        this.turretServo = turretServo;
        this.analogInput = analogInput;
    }

    Turret turret = new Turret(follower, turretServo, analogInput);


    public void init(HardwareMap h){
        constants.initMotors(h);
    }

    private double feedforward(double kV, double kS){
        return kV * normalizedVelocity + kS * Math.signum(normalizedVelocity);
    }

    private double proportional(double kP){
        return kP *  (normalizedVelocity - convertRPMToPower(
                convertToRPM(
                        ((FLYWHEEL_1.getVelocity() + FLYWHEEL_2.getVelocity())/2),
                        FLYWHEEL_CPR),
                MAX_FLYWHEEL_RPM));
    }

    private double flywheelController(double kV, double kS, double kP){
        return feedforward(kV,kS) + proportional(kP);
    }

    public void runFlywheelController(double targetVelocity, double kV, double kS, double kP){
        normalizedVelocity = convertRPMToPower(targetVelocity,MAX_FLYWHEEL_RPM);
        double flywheelPower = flywheelController(kV,kS,kP);
        runFlywheelToPower(flywheelPower);
    }

    public void runFlywheelToPower(double targetPower){
        FLYWHEEL_1.setPower(Range.clip(targetPower, -1,1));
        FLYWHEEL_2.setPower(Range.clip(targetPower, -1,1));
    }

    public void runFlywheelToVelocity(double targetVelocity){
        targetVelocity = convertToCPR(targetVelocity,FLYWHEEL_CPR);
        FLYWHEEL_1.setVelocity(targetVelocity);
        FLYWHEEL_2.setVelocity(targetVelocity);
    }

    public void runFlywheelPIDF(double kP, double f, double targetVelocity){
        PIDFCoefficients PIDF = new PIDFCoefficients(kP,0,0,f);
        FLYWHEEL_1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);
        FLYWHEEL_2.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);


        FLYWHEEL_1.setVelocity(convertToCPR(targetVelocity,FLYWHEEL_CPR));
        FLYWHEEL_2.setVelocity(convertToCPR(targetVelocity, FLYWHEEL_CPR));
    }

    public double getCurrentVelocity(){
        return convertToRPM(FLYWHEEL_1.getVelocity(), FLYWHEEL_CPR);
    }

    public void runPedroFlywheel(double kP, double kV, double target){ //kP 0.0015 kV 0.0002
        Controller proportional = Controller.proportional(kP);
        Controller ff = Controller.proportionalFeedforward(kV);
        Controller finalController = Controller.sum(proportional, ff);
        runFlywheelToPower(finalController.calculate(target,target- getCurrentVelocity()));
    }




    public double bestVelocity(){
        double bestHoodAngle = 2*Math.atan2(turret.deltaY(), turret.deltaX());
        double gravity = 9.8;
        return Math.sqrt(2*gravity*turret.deltaY()* Math.sin(bestHoodAngle))/Math.sin(bestHoodAngle);
    }

    public double launchVelocity(double rpm){
        return rpm * FLYWHEEL_CIRCUMFERENCE * (1/60); // in meters change to real circumference
    }


}
