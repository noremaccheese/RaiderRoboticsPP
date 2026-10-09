package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;


import java.util.ArrayList;
import java.util.Arrays;

public class BotConstants {


    public static ArrayList<String> tunerTypes = new ArrayList<>(Arrays.asList(
            "NULL",
            "HEADING_TUNER",
            "DRIVE_FORWARD",
            "AUTO_FORWARD",
            "DRIVE_STRAFE",
            "AUTO_STRAFE",
            "FLYWHEEL_P+FF",
            "FLYWHEEL_PIDF"
    ));


    public static DcMotorEx F_LEFT;
    public static DcMotorEx F_RIGHT;
    public static DcMotorEx B_LEFT;
    public static DcMotorEx B_RIGHT;
    public static DcMotorEx FLYWHEEL_1;
    public static DcMotorEx FLYWHEEL_2;
    public static DcMotor INTAKE;
    public static DcMotor TRANSFER_MOTOR;
    public static CRServo INTAKE_SERVO;
    public static final double CPR = 384.5 ; //put in motor cpr and wheel diameter
    public static final double DIAMETER = 4.094;
    public static final double CIRCUMFERENCE = Math.PI * DIAMETER;
    public  static IMU imu;

    public static final double STRAFE_KP = 0.04;
    public static final double STRAFE_KD = 0.001;
    public static final double FORWARD_KP = 0.032;
    public static final double FORWARD_KD = 0.001;
    public static final double HEADING_KP = 0.015;
    public static final double HEADING_KD = 0.001;
    public static final double DRIVE_STRAFE_KP = 0;
    public static final double DRIVE_STRAFE_KD = 0;
    public static final double DRIVE_FORWARD_KP = 0;
    public static final double DRIVE_FORWARD_KD = 0;


    public static final double MAX_POWER = 0.8;
    public static final double MAX_FLYWHEEL_RPM = 6000;
    public static final double END_PATH_TOLERANCE = 1; //inches
    public static final double HEADING_TOLERANCE = 2; //degrees

    public static final double FLYWHEEL_KP = 0;
    public static final double FLYWHEEL_CPR = 28;
    public static final double FLYWHEEL_RPM = 6000;
    public static final double DRIVE_RPM = 435;

    public void initMotors(HardwareMap h){
        F_LEFT = h.get(DcMotorEx .class, "fLeft");
        F_RIGHT = h.get(DcMotorEx.class, "fRight");
        B_LEFT = h.get(DcMotorEx.class, "bLeft");
        B_RIGHT = h.get(DcMotorEx.class, "bRight");
        FLYWHEEL_1 = h.get(DcMotorEx.class, "flywheel1");
        FLYWHEEL_2 = h.get(DcMotorEx.class, "flywheel2");
        INTAKE = h.get(DcMotor.class, "intakeMotor1");
        TRANSFER_MOTOR = h.get(DcMotor.class, "transferMotor");

        INTAKE_SERVO = h.get(CRServo.class, "intakeServo");

        INTAKE_SERVO.setDirection(CRServo.Direction.REVERSE);
        F_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);
        B_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);
        FLYWHEEL_1.setDirection(DcMotorSimple.Direction.REVERSE);

        F_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLYWHEEL_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLYWHEEL_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        F_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLYWHEEL_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLYWHEEL_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initIMU(HardwareMap h){
        imu = h.get(IMU.class, "IMU");
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
