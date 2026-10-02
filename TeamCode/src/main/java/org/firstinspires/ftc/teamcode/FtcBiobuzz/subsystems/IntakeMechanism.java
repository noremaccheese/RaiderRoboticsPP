package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.INTAKE;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.INTAKE_SERVO;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;

public class IntakeMechanism {
    BotConstants constants = new BotConstants();
    double intakePower = 0;


    public void init(HardwareMap h){
        constants.initMotors(h);
    }


    public void runIntake(Gamepad gamepad){
        double transferPower = 0;
        if (gamepad.x){
            transferPower = 1;
        }

        if (gamepad.right_bumper){
            intakePower = 1;
        }
        else if(gamepad.left_bumper){
            intakePower = -1;
        }
        else {
            intakePower = 0;
        }


        INTAKE.setPower(intakePower);
        INTAKE_SERVO.setPower(transferPower);


    }




}
