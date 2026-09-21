package org.firstinspires.ftc.teamcode.CameronPathing.Util;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class Constants {
    public static DcMotor F_LEFT;
    public static DcMotor F_RIGHT;
    public static DcMotor B_LEFT;
    public static DcMotor B_RIGHT;
    public static final double CPR = 384.5 ; //put in motor cpr and wheel diameter
    public static final double DIAMETER = 4.094;
    public static final double CIRCUMFERENCE = Math.PI * DIAMETER;
    public  static IMU imu;

    public static final double STRAFE_KP = 0;
    public static final double STRAFE_KD = 0;
    public static final double FORWARD_KP = 0;
    public static final double FORWARD_KD = 0;
    public static final double HEADING_KP = 0.015;
    public static final double HEADING_KD = 0.001;

    public static final double MAX_POWER = 0.8;
    public static final double END_PATH_TOLERANCE = 1; //inches
    public static final double HEADING_TOLERANCE = 2; //degrees



    public void initMotors(HardwareMap h){
        F_LEFT = h.get(DcMotorEx .class, "fLeft");
        F_RIGHT = h.get(DcMotorEx.class, "fRight");
        B_LEFT = h.get(DcMotorEx.class, "bLeft");
        B_RIGHT = h.get(DcMotorEx.class, "bRight");

        F_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);
        B_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);

        F_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        F_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
