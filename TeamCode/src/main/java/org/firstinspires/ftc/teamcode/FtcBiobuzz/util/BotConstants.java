package org.firstinspires.ftc.teamcode.FtcBiobuzz.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.pedro.Constants;

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
    public static DcMotorEx FLYWHEEL_POLLEN;
    public static DcMotorEx FLYWHEEL_NECTAR;
    public static DcMotor INTAKE;
    //public static DcMotor TRANSFER_MOTOR;
    //public static CRServo INTAKE_SERVO;
    public static CRServoImplEx TURRET_POLLEN;
    public static CRServoImplEx TURRET_NECTAR;
    public static Servo HOOD_POLLEN;
    public static Servo HOOD_NECTAR;
    public static Servo FLOWER_MECH1;
    public static Servo FLOWER_MECH2;

    public static final double CPR = 384.5 ; //put in motor cpr and wheel diameter
    public static final double DRIVE_WHEEL_DIAMETER = 4.094;
    public static final double DRIVE_WHEEL_CIRCUMFERENCE = Math.PI * DRIVE_WHEEL_DIAMETER;
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
    public static final double FLYWHEEL_CIRCUMFERENCE = 0.1; // in meters change to real circumference
    public static final double DRIVE_RPM = 435;

    public void initMotors(HardwareMap h){
        F_LEFT = h.get(DcMotorEx .class, "fLeft");
        F_RIGHT = h.get(DcMotorEx.class, "fRight");
        B_LEFT = h.get(DcMotorEx.class, "bLeft");
        B_RIGHT = h.get(DcMotorEx.class, "bRight");
        FLYWHEEL_POLLEN = h.get(DcMotorEx.class, "flywheelPollen");
        FLYWHEEL_NECTAR = h.get(DcMotorEx.class, "flywheelNectar");
        INTAKE = h.get(DcMotor.class, "intake");
        //TRANSFER_MOTOR = h.get(DcMotor.class, "transferMotor");

        TURRET_POLLEN = h.get(CRServoImplEx.class, "turretPollen");
        TURRET_NECTAR = h.get(CRServoImplEx.class, "turretNectar");
        HOOD_POLLEN = h.get(Servo.class, "hoodPollen");
        HOOD_NECTAR = h.get(Servo.class, "hoodPollen");
        FLOWER_MECH1 = h.get(Servo.class, "flowerMech1");
        FLOWER_MECH2 = h.get(Servo.class, "flowerMech2");

        FLOWER_MECH2.setDirection(Servo.Direction.REVERSE);
        F_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);
        B_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);

        F_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLYWHEEL_POLLEN.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLYWHEEL_NECTAR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        F_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        F_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_LEFT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        B_RIGHT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLYWHEEL_POLLEN.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLYWHEEL_NECTAR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initFollower(Follower follower, Pose start, HardwareMap h){
        follower = Constants.create(h);
        follower.setPose(start);
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
