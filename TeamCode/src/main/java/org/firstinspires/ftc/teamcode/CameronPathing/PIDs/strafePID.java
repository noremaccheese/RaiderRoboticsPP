package org.firstinspires.ftc.teamcode.CameronPathing.PIDs;



import static org.firstinspires.ftc.teamcode.CameronPathing.helperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.CameronPathing.helperFunctions.getDistance;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CameronPathing.constants;

public class strafePID {

    constants constants = new constants();
    ElapsedTime timer = new ElapsedTime();

    double error = 0;
    double lastError = error;





    public void init(HardwareMap h){
        constants.initMotors(h);
    }


    public double runPID(double kP, double kD, double target){
        double curDistance = curDistanceX();

        lastError = error;
        error = target - curDistance;

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


}
