package org.firstinspires.ftc.teamcode.CameronPathing;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class constants {
    public static DcMotor fLeft;
    public static DcMotor fRight;
    public static DcMotor bLeft;
    public static DcMotor bRight;
    public static final double CPR = 384.5 ; //put in motor cpr and wheel diameter
    public static final double diameter = 4.094;
    public  static IMU imu;
    public static final double circumference = Math.PI * diameter;


    public void initMotors(HardwareMap h){
        fLeft = h.get(DcMotorEx .class, "fLeft");
        fRight = h.get(DcMotorEx.class, "fRight");
        bLeft = h.get(DcMotorEx.class, "bLeft");
        bRight = h.get(DcMotorEx.class, "bRight");

        fLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        fLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        fLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void initIMU(HardwareMap h){
        imu = h.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        imu.initialize(parameters);
        imu.resetYaw();
    }


}
