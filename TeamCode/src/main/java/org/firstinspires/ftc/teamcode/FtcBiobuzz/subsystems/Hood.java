package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

public class Hood {
    double hoodAngle = 70; //cur hood angle
    Follower follower;
    CRServo turretServo;
    AnalogInput analogInput;

    public void initTurret(Follower follower, CRServo turretServo, AnalogInput analogInput) {
        this.follower = follower;
        this.turretServo = turretServo;
        this.analogInput = analogInput;
    }

    Turret turret = new Turret(follower, turretServo, analogInput);

    public void calculateHoodAngel(boolean lowArc, double v){
        double scalar = 1;
        if(lowArc){
            scalar = -1;
        }
        hoodAngle = Math.atan(powerOf(v,2) + scalar *
                Math.sqrt(powerOf(v,4) - 9.8 *(9.8 * powerOf(turret.deltaX(),2) + 2 * turret.deltaY() * powerOf(v,2))))
                / 9.8 * turret.deltaX();

    }


    private double powerOf(double x, double power){
        for(int i = 1; power > i; i++){
            x *=x;
        }

        return x;
    }


}
