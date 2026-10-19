package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.TURRET_SERVO_1;

import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;

public class Turret {
    Follower follower;
    BotConstants constants = new BotConstants();
    double velocityX;
    double velocityY;
    double omega;
    double botX = 0;
    double botY = 0;
    double botHeading = 0;
    double velocityKV = 0;
    double positionKP = 0;

    double HIVE_X= 0; //put in constants when done
    double HIVE_Y = 0;
    double OFFSET_X = 0;
    double OFFSET_Y = 0;


    public Turret(Follower follower) {
        this.follower = follower;
    }




    private void updateVelocity(){
        Velocity velocity = follower.velocity();
        velocityX = velocity.vx;
        velocityY = velocity.vy;
        omega = Math.toDegrees(velocity.omega);
    }

    private void updatePose(){
        Pose pose = follower.pose();
        botX = pose.x();
        botY = pose.y();
        botHeading = Math.toDegrees(botHeading);
    }

    private double deltaX(){
        return HIVE_X - botX;
    }
    private double deltaY(){
        return HIVE_Y - botY;
    }

    private double calculateVPerpendicular(){
        return (-deltaY() * velocityX) + (deltaX() * velocityY);
    }

    private void offsetCorrection(){
        velocityX = (velocityX*Math.cos(botHeading) + velocityX*Math.sin(botHeading)) + velocityX;
        velocityY = (velocityY*Math.cos(botHeading) - velocityY*Math.sin(botHeading)) + velocityY;
        botX = (botX*Math.cos(botHeading) + botX*Math.sin(botHeading)) + botX;
        botY = (botY*Math.cos(botHeading) - botY*Math.sin(botHeading)) + botY;
    }


    private double calculateDistance(){
        return Math.sqrt(velocityX*velocityX + velocityY*velocityY);
    }

    private double calculateTargetVelocity(){
        return -(omega + (calculateVPerpendicular()/calculateDistance()));
    }



    public void init(HardwareMap h){
        constants.initMotors(h);
    }
    public void runTurret(){
        updatePose();
        updateVelocity();
        offsetCorrection();
        Controller velocityFeedforward = Controller.proportionalFeedforward(velocityKV);
        Controller positionProportional = Controller.proportional(positionKP);


    }

}