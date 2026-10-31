package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

public class InterpLUT {

    LinearInterpolation interpolator = new LinearInterpolation();


    double[][] array =
            {
                    {0, 0},
                    {0, 0},
                    {0, 0},
                    {0, 0}
            };

    double[][] flywheelPoses = // x rpm y distance
            {
                    {0, 0},
                    {0, 0},
                    {0, 0},
                    {0, 0}
            };


    public double interpolateLUT(double[][] lut, double x) {
        int xi = 0;

        double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        double t = 0;

        int length = lut.length-1;


        if (x > lut[length][0]) {
            return lut[length][1];

        }

        if (x < lut[0][0]) {
            return lut[0][1];
        }

        else {
            for (int i = 0; i < lut.length; i++) {
                if (lut[i][0] == x) {
                    return lut[i][1];
                }

                else if (lut[i][0] > x) {
                    xi = i;
                    break;
                }
            }
        }

        x1 = lut[xi - 1][0];
        x2 = lut[xi][0];

        y1 = lut[xi - 1][1];
        y2 = lut[xi][1];


        t = interpolator.interpolation(x1, x2, x);

        return interpolator.interpolate(y1, y2, t);
    }

}




