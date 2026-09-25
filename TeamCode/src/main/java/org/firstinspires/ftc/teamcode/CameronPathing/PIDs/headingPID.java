package org.firstinspires.ftc.teamcode.CameronPathing.PIDs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants;


public class headingPID {

    Constants constants = new Constants();
    ElapsedTime timer = new ElapsedTime();
    private IMU imu = constants.imu;
    double error = 0;
    double lastError = error;





    public void init(HardwareMap h){
        constants.initIMU(h);
    }


    public double runPID(double kP, double kD, double targetHeading){
        double curHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        lastError = error;
        error = targetHeading - curHeading;
        while (error > 180) error -= 360;
        while (error < -180) error += 360;
        double deltaError = error-lastError;

        while (deltaError > 180) deltaError -= 360;
        while (deltaError < -180) deltaError += 360;




        double proportional = kP * error;
        double derivative = kD * deltaError/timer.seconds();
        double output = Range.clip(proportional + derivative, -1, 1);


        timer.reset();
        return output;
    }

    public double getHeading(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public double getError(){
        return error;
    }


}


//kP 0.015
//kD 0.001