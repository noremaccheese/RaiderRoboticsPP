package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

public class AxonCR {
    CRServo servo;
    AnalogInput analogInput;

    double voltagePerRev = 1;
    double lastServoPosition = 0;

    public AxonCR(CRServo servo, AnalogInput analogInput){
        this.servo = servo;
        this.analogInput = analogInput;
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
    }

    public double servoVelocity(){
        return  servoPosition() - lastServoPosition;
    }

    public void updateLastPosition(){
        lastServoPosition = servoPosition();
    }

    public void setServoPower(double power){
        servo.setPower(power);
        updateLastPosition();
    }



}
