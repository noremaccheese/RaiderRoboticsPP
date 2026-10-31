package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

public class MathUtil {

    public double powerOf(double x, double power){
        for(int i = 1; power > i; i++){
            x *=x;
        }

        return x;
    }


}
