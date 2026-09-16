package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.HeadingPID;

@TeleOp
public class HeadingPIDTuner extends OpMode {
    private double kP = 0;
    private double kD = 0;
    private double[] stepSizes = {0.001,0.01,0.1,1};
    private int stepIndex = 0;

    HeadingPID pid = new HeadingPID();


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

        fLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        pid.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {

        if (gamepad1.dpad_up){
            kP += stepSizes[stepIndex];
        }
        else if (gamepad1.dpad_down){
            kP -= stepSizes[stepIndex];
        }
        else if (gamepad1.dpad_right){
            kD += stepSizes[stepIndex];
        }
        else if (gamepad1.dpad_left){
            kD -= stepSizes[stepIndex];
        }


        if(gamepad1.right_bumper){
            stepIndex += 1;
        }
        else if(gamepad1.left_bumper){
            stepIndex -= 1;
        }


        double fLeftPower = -pid.runPID(kP,kD,0);
        double fRightPower = pid.runPID(kP,kD,0);
        double bLeftPower = -pid.runPID(kP,kD,0);
        double bRightPower = pid.runPID(kP,kD,0);



        fLeft.setPower(fLeftPower);
        fRight.setPower(fRightPower);
        bLeft.setPower(bLeftPower);
        bRight.setPower(bRightPower);





        telemetry.addData("Heading", pid.getHeading());
        telemetry.update();



    }
}
