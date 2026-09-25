package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants.F_RIGHT;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.headingPID;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.Constants;

@TeleOp
public class headingPIDTuner extends OpMode {
    private double kP = 0;
    private double kD = 0;
    private double[] stepSizes = {0.001,0.01,0.1,1};
    private int stepIndex = 0;
    private boolean hasPressed = false;

    headingPID pid = new headingPID();
    Constants constants = new Constants();

    @Override
    public void init() {
        F_LEFT = hardwareMap.get(DcMotor.class, "fLeft");
        F_RIGHT = hardwareMap.get(DcMotor.class, "fRight");
        B_LEFT = hardwareMap.get(DcMotor.class, "bLeft");
        B_RIGHT = hardwareMap.get(DcMotor.class, "bRight");

        F_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);
        B_LEFT.setDirection(DcMotorSimple.Direction.REVERSE);

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


        if(gamepad1.right_bumper && !hasPressed){
            stepIndex += 1;
            hasPressed = true;
        }
        else if(gamepad1.left_bumper && !hasPressed){
            stepIndex -= 1;
            hasPressed = true;
        }
        if(!gamepad1.right_bumper && !gamepad1.left_bumper){
            hasPressed= false;
        }


        double fLeftPower = -pid.runPID(kP,kD,0);
        double fRightPower = pid.runPID(kP,kD,0);
        double bLeftPower = -pid.runPID(kP,kD,0);
        double bRightPower = pid.runPID(kP,kD,0);



        F_LEFT.setPower(fLeftPower);
        F_RIGHT.setPower(fRightPower);
        B_LEFT.setPower(bLeftPower);
        B_RIGHT.setPower(bRightPower);




        telemetry.addData("kP", kP);
        telemetry.addData("kD", kD);
        telemetry.addData("Step size", stepSizes[stepIndex]);
        telemetry.addData("Heading", pid.getHeading());
        telemetry.update();



    }
}
