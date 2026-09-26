package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;




import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.FLYWHEEL;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions;



public class FlywheelControllerCustom extends HelperFunctions {
    Constants constants = new Constants();

    ElapsedTime timer = new ElapsedTime();
    private double curVelocity = 0;


    public void init(HardwareMap h){
        constants.initMotors(h);
    }



    public double proportional(double desiredVelocity, double kP){
        double error = desiredVelocity - curVelocity;
        return error*kP;
    }

    public double feedforward(double kS, double kV, double desiredVelocity){
        kS = kS * Math.signum(desiredVelocity);
        kV = kV * desiredVelocity;
        return kS + kV;
    }



    public void runController(double kP, double kS, double kV, double desiredVelocity){
        curVelocity = FLYWHEEL.getVelocity();
        double flywheelPower = Range.clip(proportional(desiredVelocity,kP) + feedforward(kS,kV,desiredVelocity), -0.9,0.9);
        FLYWHEEL.setPower(flywheelPower);

    }




}
