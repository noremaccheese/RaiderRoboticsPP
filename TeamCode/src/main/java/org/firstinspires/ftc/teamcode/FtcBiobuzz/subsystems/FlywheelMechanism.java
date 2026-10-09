package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;


import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_1;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_2;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.MAX_FLYWHEEL_RPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertRPMToPower;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertToRPM;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;

public class FlywheelMechanism {
    BotConstants constants = new BotConstants();
    double normalizedVelocity = 0;



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
        targetVelocity = convertToRPM(targetVelocity,FLYWHEEL_CPR);
        FLYWHEEL_1.setVelocity(targetVelocity);
        FLYWHEEL_2.setVelocity(targetVelocity);
    }

    public void runFlywheelPIDF(double kP, double f, double targetVelocity){
        PIDFCoefficients PIDF = new PIDFCoefficients(kP,0,0,f);
        FLYWHEEL_1.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);
        FLYWHEEL_2.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, PIDF);


        FLYWHEEL_1.setVelocity(convertToRPM(targetVelocity,FLYWHEEL_CPR));
        FLYWHEEL_2.setVelocity(convertToRPM(targetVelocity, FLYWHEEL_CPR));
    }


}
