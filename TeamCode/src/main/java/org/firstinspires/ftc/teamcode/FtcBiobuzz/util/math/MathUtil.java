package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

public class MathUtil {

    public double powerOf(double x, double power){
        for(int i = 1; power > i; i++){
            x *=x;
        }

        return x;
    }

    public Vector2d rotMatrix(Vector2d vector, double theta){
        double x = vector.getX();
        double y = vector.getY();

        double x2 = x * Math.cos(theta) - y * Math.sin(theta);
        double y2 = x * Math.sin(theta) + y * Math.cos(theta);

        return new Vector2d(x2,y2);
    }


}
