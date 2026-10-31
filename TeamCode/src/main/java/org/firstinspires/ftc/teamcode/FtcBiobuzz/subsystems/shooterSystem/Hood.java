package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Hood {
    double hoodAngle = 70; //cur hood angle
    Servo hoodServo;
    AnalogInput analogInput;


    public void initHood(Servo hoodServo, AnalogInput analogInput) {
        this.hoodServo = hoodServo;
        this.analogInput = analogInput;
    }


    public void setHoodAngle(double hoodAngle){
        this.hoodAngle = hoodAngle;
    }


    public void runHood(){
        hoodServo.setPosition(hoodAngle);
    }


}
