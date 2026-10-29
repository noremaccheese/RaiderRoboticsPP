package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

public class ShooterSystem {
    Follower follower;
    CRServo turretServo;
    AnalogInput analogInput;

    public static double OFFSET_X, OFFSET_Y;


    public static double VELOCITY_X, VELOCITY_Y, OMEGA;

    public static double BOT_X, BOT_Y, BOT_HEADING;
    double HIVE_X= 48, HIVE_Y = 60; //put in constants when done



    public void init(Follower follower, CRServo turretServo, AnalogInput analogInput) {
        this.follower = follower;
        this.turretServo = turretServo;
        this.analogInput = analogInput;
    }

    public void updateFollower(){
        Pose pose = follower.pose();
        Velocity velocity = follower.velocity();


        VELOCITY_X = velocity.vx;
        VELOCITY_Y = velocity.vy;
        OMEGA = velocity.omega;


        BOT_X = pose.x();
        BOT_Y = pose.y();
        BOT_HEADING = pose.heading();


        offsetCorrection();
    }



    public double deltaX(){
        return HIVE_X - BOT_X;
    }
    public double deltaY(){
        return HIVE_Y - BOT_Y;
    }

    public double calculateVPerpendicular(){
        return (-deltaY() * VELOCITY_X) + (deltaX() * VELOCITY_Y);
    }

    public void offsetCorrection(){
        BOT_X = (OFFSET_X*Math.cos(BOT_HEADING) + OFFSET_Y*Math.sin(BOT_HEADING)) + BOT_X;
        BOT_Y = (OFFSET_X*Math.cos(BOT_HEADING) - OFFSET_Y*Math.sin(BOT_HEADING)) + BOT_Y;
    }


    public double calculateDistance(){
        return Math.max(Math.hypot(deltaX(), deltaY()), 1);
    }




    public double calculateTargetVelocity(){
        return -(OMEGA + (calculateVPerpendicular()/calculateDistance()));
    }

    public double calculateTargetPosition(){
        double deltaX = HIVE_X - BOT_X;
        double deltaY = HIVE_Y - BOT_Y;
        return Math.atan2(deltaX,deltaY);
    }



    public double flightTime(double launchVelocity, double launchAngle){
        return (2*launchVelocity*Math.sin(launchAngle)/9.8);
    }

    public double velocityCorrection(){
        return calculateTargetVelocity() - (calculateVPerpendicular()* flightTime(0,0)); // add in angle
    }



}
