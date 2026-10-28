package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants;

public class Turret {
    Follower follower;
    BotConstants constants = new BotConstants();
    double velocityX, velocityY, omega = 0;
    double botX, botY, botHeading;
    double velocityKV = 0;
    double positionKP = 0;

    double HIVE_X= 48, HIVE_Y = 60; //put in constants when done
    double offsetX = 0, offsetY = 0; //put in offsets later
    CRServo turretServo;
    AnalogInput analogInput;
    double voltagePerRev = 1;
    double lastServoPosition;


    public Turret setOffsets(double offsetX, double offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }

    public Turret(Follower follower, CRServo turretServo, AnalogInput analogInput) {
        this.follower = follower;
        this.turretServo = turretServo;
        this.analogInput = analogInput;
    }


    private void updateFollower(){
        Pose pose = follower.pose();
        Velocity velocity = follower.velocity();


        velocityX = velocity.vx;
        velocityY = velocity.vy;
        omega = velocity.omega;


        botX = pose.x();
        botY = pose.y();
        botHeading = pose.heading();


        offsetCorrection();
    }



    public double deltaX(){
        return HIVE_X - botX;
    }
    public double deltaY(){
        return HIVE_Y - botY;
    }

    private double calculateVPerpendicular(){
        return (-deltaY() * velocityX) + (deltaX() * velocityY);
    }

    public void offsetCorrection(){
        botX = (offsetX*Math.cos(botHeading) + offsetY*Math.sin(botHeading)) + botX;
        botY = (offsetX*Math.cos(botHeading) - offsetY*Math.sin(botHeading)) + botY;
    }


    private double calculateDistance(){
        return Math.max(Math.hypot(deltaX(), deltaY()), 1);
    }

    private double calculateTargetVelocity(){
        return -(omega + (calculateVPerpendicular()/calculateDistance()));
    }

    private double calculateTargetPosition(){
        double deltaX = HIVE_X - botX;
        double deltaY = HIVE_Y - botY;
        return Math.atan2(deltaX,deltaY);
    }

    public double servoPosition(){
        double radians = (analogInput.getVoltage()/voltagePerRev) * (2 * Math.PI);

        if((radians - lastServoPosition) > 1){
            radians += (Math.PI * 2);
        }
        else if((radians - lastServoPosition) < -1){
            radians -= (Math.PI * 2);
        }


        return radians;
        //add in heading correction
    }

    public double flightTime(double launchVelocity, double launchAngle){
        return (2*launchVelocity*Math.sin(launchAngle)/9.8);
    }

    private double velocityCorrection(){
        return calculateTargetVelocity() - (calculateVPerpendicular()* flightTime(0,0)); // add in angle
    }


    public void init(HardwareMap h){
        constants.initMotors(h);
        lastServoPosition = servoPosition();
    }

    public double runFeedforward(){
        updateFollower();
        Controller velocityFeedforward = Controller.proportionalFeedforward(velocityKV);

        double turretVelocity = servoPosition() - lastServoPosition;
        double velocityValue = velocityFeedforward.calculate(velocityCorrection(), turretVelocity);
        lastServoPosition = servoPosition();

        return velocityValue;
    }

    public double runTurretToPosition(){
        updateFollower();

        Controller positionProportional = Controller.proportional(positionKP);
        double positionValue = positionProportional.calculate(calculateTargetPosition(), servoPosition());
        lastServoPosition = servoPosition();

        return positionValue;
    }



}