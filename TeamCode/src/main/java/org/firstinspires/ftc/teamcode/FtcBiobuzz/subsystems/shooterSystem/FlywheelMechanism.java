package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem;


import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.FLYWHEEL_CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.MAX_FLYWHEEL_RPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.convertRPMToPower;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.convertToCPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.HelperFunctions.convertToRPM;

import com.pedropathing.controllers.Controller;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants;

public class FlywheelMechanism {
    BotConstants constants = new BotConstants();
    double normalizedVelocity;
    DcMotorEx flywheel;

    public FlywheelMechanism(DcMotorEx flywheel){
        this.flywheel = flywheel;
    }


    public void init(HardwareMap h){
        constants.initMotors(h);
    }

    private double feedforward(double kV, double kS){
        return kV * normalizedVelocity + kS * Math.signum(normalizedVelocity);
    }

    private double proportional(double kP){
        return kP *  (normalizedVelocity - convertRPMToPower(
                convertToRPM(
                        flywheel.getVelocity(),
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
        flywheel.setPower(Range.clip(targetPower, -1,1));
    }

    public void runFlywheelToVelocity(double targetVelocity){
        targetVelocity = convertToCPR(targetVelocity,FLYWHEEL_CPR);
        flywheel.setVelocity(targetVelocity);
    }

    public void runFlywheelPIDF(double kP, double f, double targetVelocity){
        PIDFCoefficients PIDF = new PIDFCoefficients(kP,0,0,f);
        flywheel.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);


        flywheel.setVelocity(convertToCPR(targetVelocity,FLYWHEEL_CPR));
    }

    public double getCurrentVelocity(){
        return convertToRPM(flywheel.getVelocity(), FLYWHEEL_CPR);
    }

    public void runPedroFlywheel(double kP, double kV, double target){ //kP 0.0015 kV 0.0002
        Controller proportional = Controller.proportional(kP);
        Controller ff = Controller.proportionalFeedforward(kV);
        Controller finalController = Controller.sum(proportional, ff);
        runFlywheelToPower(finalController.calculate(target,target- getCurrentVelocity()));
    }




}
