package org.firstinspires.ftc.teamcode.CameronPathing.PIDs;



import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.DRIVE_RPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertRPMToPower;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.getVelocity;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;

public class StrafePID {

    BotConstants constants = new BotConstants();
    ElapsedTime timer = new ElapsedTime();

    double error = 0;
    double lastError = error;





    public void init(HardwareMap h){
        constants.initMotors(h);
    }


    public double runPID(double kP, double kD, double target, boolean drive){
        double curDistance = curDistanceX();
        double curPower = convertRPMToPower(getVelocity(false,CPR), DRIVE_RPM);

        lastError = error;
        error = drive ? target - curPower : target - curDistance;

        double deltaError = error-lastError;
        double proportional = kP * error;
        double derivative = kD * deltaError/timer.seconds();
        double output = Range.clip(proportional + derivative, -1, 1);


        timer.reset();
        return output;
    }

    public double getError(){
        return error;
    }
    public double getCurPower(){
        return convertRPMToPower(getVelocity(false,CPR), DRIVE_RPM);
    }


}
