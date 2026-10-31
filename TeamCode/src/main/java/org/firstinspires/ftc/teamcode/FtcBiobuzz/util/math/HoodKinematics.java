package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.OdoInfo.VELOCITY_Y;

public class HoodKinematics {

    double hoodAngle = 70; //cur hood angle

    OdoInfo info = new OdoInfo();
    MathUtil m = new MathUtil();

    double arc = 1;

    public void setArc(double arc){
        this.arc = arc;
    }

    public void velocityHood(double v){
       v += VELOCITY_Y;

        hoodAngle = Math.atan(m.powerOf(v,2) +
                arc *
                Math.sqrt(m.powerOf(v,4) - 9.8 *(9.8 * m.powerOf(info.hiveDX(),2) + 2 * info.hiveDY() * m.powerOf(v,2))))
                / 9.8 * info.hiveDX();
    }





}
