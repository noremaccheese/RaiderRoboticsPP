package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class FlywheelPIDF {
    private DcMotor intakeMotor1;
    private DcMotor ballPusher;
    private DcMotorEx eject1;
    private static final double CPR = 84; //Put in counts per revolution
    private static final double MAX_RPM = 1700;



    private double curTargetVelocity = 0;

    private double P = 2.5;
    private double F = 10.6;
    private double error = 0;
    private double curVelocity = 0;







    public void init(HardwareMap haMap){
        eject1 = haMap.get(DcMotorEx.class, "ejectionMotor1");
        intakeMotor1 = haMap.get(DcMotor.class, "intakeMotor1");
        ballPusher = haMap.get(DcMotor.class, "ballPusher");


        // Reset the motor encoder so that it reads zero ticks
        eject1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //eject1.setDirection(DcMotorSimple.Direction.REVERSE);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        eject1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        //setup PIDF

        PIDFCoefficients PIDF = new PIDFCoefficients(P,0,0,F);
        eject1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, PIDF);

    }

    public double getP(){
        return P;
    }
    public double getF(){
        return F;
    }
    public double getError(){
        return error;
    }
    public double getCurVelocity(){
        return curVelocity;
    }
    public double getCurTargetVelocity(){
        return curTargetVelocity;
    }

    public void update(Gamepad gamepad, double targetVelocity){
        //PIDF TUNING

        curTargetVelocity = targetVelocity;



        PIDFCoefficients PIDF = new PIDFCoefficients(P,0,0,F);
        eject1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, PIDF);



        eject1.setVelocity((curTargetVelocity * CPR)/60);


        curVelocity = eject1.getVelocity();
        error = curTargetVelocity - curVelocity;


        //PIDF TUNING




    }

}