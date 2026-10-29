package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem;

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

    ShooterSystem shooterSystem = new ShooterSystem();

    public void calculateHoodAngel(boolean lowArc, double v){
        double scalar = 1;
        if(lowArc){
            scalar = -1;
        }
        hoodAngle = Math.atan(powerOf(v,2) + scalar *
                Math.sqrt(powerOf(v,4) - 9.8 *(9.8 * powerOf(shooterSystem.deltaX(),2) + 2 * shooterSystem.deltaY() * powerOf(v,2))))
                / 9.8 * shooterSystem.deltaX();

    }


    private double powerOf(double x, double power){
        for(int i = 1; power > i; i++){
            x *=x;
        }

        return x;
    }


}
