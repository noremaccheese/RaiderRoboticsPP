package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;


import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_1;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_2;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.MAX_FLYWHEEL_RPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertRPMToPower;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertToRPM;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;

public class FlywheelMechanism {
    BotConstants constants = new BotConstants();
    double targetVelocity = 0;



    public void init(HardwareMap h){
        constants.initMotors(h);
    }

    private double feedforward(double kV, double kS, double targetVelocity){
        return convertRPMToPower(targetVelocity, MAX_FLYWHEEL_RPM) * kV + kS * Math.signum(targetVelocity);
    }

    private double proportional(double kP, double targetVelocity){
        return kP *  convertRPMToPower(targetVelocity- convertToRPM((FLYWHEEL_1.getVelocity() + FLYWHEEL_2.getVelocity()/2), FLYWHEEL_CPR), MAX_FLYWHEEL_RPM);
    }

    public double flywheelController(double kV, double kS, double kP){
        return feedforward(kV,kS, targetVelocity) + proportional(kP, targetVelocity);
    }

    public void runFlywheelController(double targetVelocity, double kV, double kS, double kP){
        this.targetVelocity = targetVelocity;
        double flywheelPower = flywheelController(kV,kS,kP);
        FLYWHEEL_1.setPower(flywheelPower);
    }

    public void runFlywheelToPower(double targetPower){
        FLYWHEEL_1.setPower(targetPower);
        FLYWHEEL_2.setPower(targetPower);
    }

    public void runFlywheelToVelocity(double targetVelocity){
        FLYWHEEL_1.setVelocity(targetVelocity);
        FLYWHEEL_2.setVelocity(targetVelocity);
    }




}
