package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
    public class ServoTester extends OpMode {

    CRServo servo;

    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servo");
    }

    @Override
    public void loop() {
        servo.setPower(1);
    }
}
