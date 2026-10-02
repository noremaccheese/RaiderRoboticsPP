package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;


import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_1;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_2;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_RPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.MAX_POWER;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertToCPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertToRPM;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;

public class FlywheelMechanism {
    BotConstants constants = new BotConstants();



    public void init(HardwareMap h){
        constants.initMotors(h);

    }

    private double feedforward(double kV, double kS, double targetVelocity){
        return targetVelocity * kV + kS * Math.signum(targetVelocity);
    }

    private double proportional(double kP, double targetVelocity){
        return kP * (targetVelocity- convertToRPM((FLYWHEEL_1.getVelocity() + FLYWHEEL_2.getVelocity()/2), FLYWHEEL_CPR));
    }

    public void runFlywheelController(double targetVelocity, Gamepad gamepad, double kV, double kS, double kP){

        double flywheelVelocity = 0;


        if(gamepad.a){
            flywheelVelocity = targetVelocity;
        }
        else if(gamepad.b){
            flywheelVelocity = -targetVelocity;
        }


        FLYWHEEL_1.setVelocity(convertToCPR(flywheelVelocity + feedforward(kV,kS,flywheelVelocity),FLYWHEEL_CPR + proportional(kP,flywheelVelocity)));
        FLYWHEEL_2.setVelocity(convertToCPR(flywheelVelocity + feedforward(kV,kS, flywheelVelocity), FLYWHEEL_CPR)+ proportional(kP,flywheelVelocity));

    }

    public void runFlywheelToPower(double targetPower, Gamepad gamepad){
        double targetVelocity = (targetPower * FLYWHEEL_RPM) / MAX_POWER;
        double flywheelVelocity = 0;

        if(gamepad.a){
            flywheelVelocity = targetVelocity;
        }
        else if(gamepad.b){
            flywheelVelocity = -targetVelocity;
        }

        FLYWHEEL_1.setVelocity(flywheelVelocity);
        FLYWHEEL_2.setVelocity(flywheelVelocity);
    }



}
