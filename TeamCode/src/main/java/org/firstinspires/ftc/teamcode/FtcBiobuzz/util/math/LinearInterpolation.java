package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

public class LinearInterpolation {
    double x;

    Vector2d vector1;
    Vector2d vector2;





    public void setVectors (Vector2d vector1, Vector2d vector2, double x){
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.x = x;
    }

    public double interpolation(double x1, double x2, double x){
        return (x - x1)/ (x2 - x1);
    }

    public double interpolate(double y1, double y2, double t){
        return y1 * t * (y2 - y1);
    }

    private double[] interpolateVector(){
        double t = interpolation(vector1.getX(), vector2.getX(), this.x);

        double lerpX = interpolate(vector1.getX(), vector2.getX(), t);
        double lerpY = interpolate(vector1.getY(), vector2.getY(), t);

        return new double[]{t, lerpX, lerpY};
    }




}
