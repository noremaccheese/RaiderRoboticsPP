package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class challengeDriveCodePractice extends OpMode {
    private DcMotor intake; //shorthand to just intake
    private DcMotor fLeft;
    private DcMotor fRight;
    private DcMotor bLeft;
    private DcMotor bRight;

    @Override
    public void init() {
        fLeft = hardwareMap.get(DcMotor.class, "fLeft");
        fRight = hardwareMap.get(DcMotor.class, "fRight");
        bLeft = hardwareMap.get(DcMotor.class, "bLeft");
        bRight = hardwareMap.get(DcMotor.class, "bRight");
        intake = hardwareMap.get(DcMotor.class, "intakeMotor1");

        fLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bLeft.setDirection(DcMotorSimple.Direction.REVERSE);



        telemetry.addLine("init complete");
        telemetry.update();
    }


    @Override
    public void loop() {


        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;
        double intakePower = 0;

        if(gamepad1.right_bumper){
            intakePower = 1;
        }
        else if(gamepad1.left_bumper){
            intakePower = -1;
        }

        double fLeftPower = y + x + rx;
        double fRightPower = y - x - rx;
        double bRightPower = y + x - rx;
        double bLeftPower = y - x + rx;



        fLeft.setPower(fLeftPower);
        fRight.setPower(fRightPower);
        bRight.setPower(bRightPower);
        bLeft.setPower(bLeftPower);
        intake.setPower(intakePower);

        telemetry.addData("LeftStickX ", x);
        telemetry.addData("LeftStickY", y);
        telemetry.addData("RightStickX", rx);

        telemetry.addData("fLeftPower", fLeftPower);
        telemetry.addData("fRightPower", fRightPower);
        telemetry.addData("bRightPower", bRightPower);
        telemetry.addData("bLeftPower", bLeftPower);
        telemetry.update();









    }
}
