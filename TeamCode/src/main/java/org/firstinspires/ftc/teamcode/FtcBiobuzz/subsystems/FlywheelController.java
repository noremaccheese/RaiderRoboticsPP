package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;




import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.FLYWHEEL;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions;



public class FlywheelController extends HelperFunctions {
    Constants constants = new Constants();





    public void init(HardwareMap h){
        constants.initMotors(h);
    }




    public double feedforward(double kS, double kV, double desiredVelocity){
        desiredVelocity = (desiredVelocity * CPR)/60;
        kS = kS * Math.signum(desiredVelocity);
        kV = kV * desiredVelocity;
        return kS + kV ;
    }

    public PIDFCoefficients calculatePFF(double kP,double kS,double kV,double desiredVelocity){
        double f = feedforward(kS, kV, desiredVelocity);
        PIDFCoefficients PIDF = new PIDFCoefficients(kP,0,0,f);
        return PIDF;
    }


    public void runPFF(double kP,double kS,double kV,double desiredVelocity){
        FLYWHEEL.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, calculatePFF(kP,kS,kV,desiredVelocity));
        FLYWHEEL.setVelocity((desiredVelocity * CPR)/60);
    }





}
