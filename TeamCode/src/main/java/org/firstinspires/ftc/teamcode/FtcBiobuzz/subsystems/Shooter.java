package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_1;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_2;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;


public class Shooter {
    FlywheelMechanism flywheel = new FlywheelMechanism();
    BotConstants constants = new BotConstants();
    double targetVelocity = 1250;
    double targetFlywheelVelocity = 0;
    double transferPower = 0.8;
    double intakePower = 0;
    double servoPower = 0;
    double kV = 1;



    public void setTargetVelocity(double targetVelocity){
        this.targetVelocity = targetVelocity;
    }

    public enum shooterState{
        IDLE,
        SPINNING_UP,
        SHOOTING
    }

    shooterState state = shooterState.IDLE;


    public void init(HardwareMap h){
        constants.initMotors(h);
        flywheel.init(h);
    }

    public double flywheelVelocity(){
        return (FLYWHEEL_1.getVelocity() + FLYWHEEL_2.getVelocity())/2;
    }

    public void setState(shooterState state){
        this.state = state;
    }

    public void runShooter(){

        switch (state){


            case IDLE:
                targetFlywheelVelocity = 0;
                transferPower = 0.5;
                intakePower = 0;
                servoPower = 0;
                break;

            case SPINNING_UP:
                targetFlywheelVelocity = targetVelocity;
                transferPower = 0.5;
                intakePower = 0;
                servoPower = 0;
                if(Math.abs(targetFlywheelVelocity- flywheelVelocity()) < 50){
                    state = shooterState.SHOOTING;
                }

                break;


            case  SHOOTING:

                targetFlywheelVelocity = targetVelocity;
                transferPower = 1;
                intakePower = 0.5;
                servoPower = 1;
                if(Math.abs(targetFlywheelVelocity - flywheelVelocity()) > 50){
                    state = shooterState.SPINNING_UP;
                }

                break;

        }

    }
    public void setkV(double kV){
        this.kV = kV;
    }

    public void update(Gamepad gamepad){

        if(gamepad.a && state == shooterState.IDLE){
            state = shooterState.SPINNING_UP;
        }
        else if(!gamepad.a){
            state = shooterState.IDLE;
        }


        runShooter();
        flywheel.runFlywheelToVelocity(targetFlywheelVelocity);
        //flywheel.runFlywheelController(targetFlywheelVelocity,kV,0,0);


    }

    public double intakePower(){
        return intakePower;
    }

    public double transferPower(){
        return transferPower;
    }

    public double getTargetFlywheelVelocity(){
        return targetFlywheelVelocity;
    }


    public double getTargetVelocity(){
        return targetVelocity;
    }
    public double getServoPower(){
        return  servoPower;
    }



}
