package org.firstinspires.ftc.teamcode.CameronPathing.Tuning;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.F_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.HEADING_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.HEADING_KP;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceY;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.ForwardPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.HeadingPID;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions;

@TeleOp
public class ForwardPIDTuner extends OpMode {
    BotConstants constants = new BotConstants();
    private double kP = 0;
    private double kD = 0;
    private double[] stepSizes = {0.001,0.01,0.1,1};
    private int stepIndex = 0;
    private boolean hasPressed = false;
    public double targetDistance = 24;
    public double targetPower = 0.5;
    HelperFunctions helperFunctions = new HelperFunctions();

    ForwardPID pid = new ForwardPID(0,0);
    HeadingPID headingPID = new HeadingPID(HEADING_KP,HEADING_KD);
    private enum tunerType{
        AUTO,
        DRIVE,
        NULL
    }
    tunerType type = tunerType.NULL;


    @Override
    public void init() {
        constants.initMotors(hardwareMap);
        constants.initIMU(hardwareMap);
        pid.init(hardwareMap);
        telemetry.addLine("Init complete");
    }

    @Override
    public void init_loop() {
        while (type == tunerType.NULL){
            telemetry.addLine("Drive tuner: a   Auto tuner: b");
            if(gamepad1.a){
                type = tunerType.DRIVE;
            }
            if (gamepad1.b){
                type = tunerType.AUTO;
            }
        }

        if(gamepad1.x){
            type = tunerType.NULL;
        }

        telemetry.addData("Tuner type", type);
        telemetry.addLine("If you want to switch types press x");
        telemetry.addLine("Init complete");
        telemetry.update();

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


        pid.setkP(kP);
        pid.setkD(kD);

        double pidValue = 0;
        double headingPIDValue = headingPID.runPID(0);

        if(type == tunerType.AUTO){
            if(gamepad1.a){
                pidValue = pid.runPID(targetDistance, false);
            }
            else if(gamepad1.b){
                helperFunctions.resetEncoders();
            }
        }

        if(type == tunerType.DRIVE && gamepad1.a){
            pidValue = pid.runPID(targetPower, true);
        }




        double fLeftPower = pidValue - headingPIDValue;
        double fRightPower = pidValue + headingPIDValue;
        double bLeftPower = pidValue - headingPIDValue;
        double bRightPower = pidValue + headingPIDValue;



        F_LEFT.setPower(fLeftPower);
        F_RIGHT.setPower(fRightPower);
        B_LEFT.setPower(bLeftPower);
        B_RIGHT.setPower(bRightPower);



        if(type == tunerType.DRIVE){
            telemetry.addData("Target power", targetPower);
            telemetry.addData("Current power", pid.getCurPower());

        }

        telemetry.addData("kP", kP);
        telemetry.addData("kD", kD);
        telemetry.addData("Step size", stepSizes[stepIndex]);
        telemetry.addData("Target Distance", targetDistance);
        telemetry.addData("Distance travelled", curDistanceY());
        telemetry.addData("Error", pid.getError());
        telemetry.update();



    }
}
