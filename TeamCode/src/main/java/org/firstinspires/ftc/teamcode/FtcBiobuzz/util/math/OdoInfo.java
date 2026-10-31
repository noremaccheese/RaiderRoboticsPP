package org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

public class OdoInfo {
    Follower follower;


    public static double VELOCITY_X, VELOCITY_Y, OMEGA;

    public static double BOT_X, BOT_Y, BOT_HEADING;
    double HIVE_X= 48, HIVE_Y = 60; //put in constants when done



    public void init(Follower follower) {
        this.follower = follower;
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
    }


    public double hiveDX(){
        return HIVE_X - BOT_X;
    }
    public double hiveDY(){
        return HIVE_Y - BOT_Y;
    }
}
