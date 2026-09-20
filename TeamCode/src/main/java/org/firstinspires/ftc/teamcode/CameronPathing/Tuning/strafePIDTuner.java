package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;



import static org.firstinspires.ftc.teamcode.CameronPathing.constants.B_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.F_LEFT;
import static org.firstinspires.ftc.teamcode.CameronPathing.constants.F_RIGHT;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.forwardPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.headingPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.strafePID;
import org.firstinspires.ftc.teamcode.CameronPathing.constants;

@TeleOp
public class strafePIDTuner extends OpMode {
    constants constants = new constants();
    private double kP = 0;
    private double kD = 0;
    private double[] stepSizes = {0.001,0.01,0.1,1};
    private int stepIndex = 0;
    private boolean hasPressed = false;

    strafePID pid = new strafePID();


    @Override
    public void init() {
        constants.initMotors(hardwareMap);
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


        double pidValue = pid.runPID(kP,kD,0);

        double fLeftPower = pidValue;
        double fRightPower = -pidValue;
        double bLeftPower = -pidValue;
        double bRightPower = pidValue;



        F_LEFT.setPower(fLeftPower);
        F_RIGHT.setPower(fRightPower);
        B_LEFT.setPower(bLeftPower);
        B_RIGHT.setPower(bRightPower);




        telemetry.addData("kP", kP);
        telemetry.addData("kD", kD);
        telemetry.addData("Step size", stepSizes[stepIndex]);
        telemetry.update();



    }
}
