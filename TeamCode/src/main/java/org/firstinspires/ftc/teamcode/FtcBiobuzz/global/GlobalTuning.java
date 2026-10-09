package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.DRIVE_FORWARD_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.DRIVE_FORWARD_KP;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_1;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_2;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.FLYWHEEL_CPR;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.F_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.HEADING_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.HEADING_KP;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.STRAFE_KD;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.STRAFE_KP;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.BotConstants.tunerTypes;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.convertToRPM;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceX;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.global.HelperFunctions.curDistanceY;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.ForwardPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.HeadingPID;
import org.firstinspires.ftc.teamcode.CameronPathing.PIDs.StrafePID;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.FlywheelMechanism;

import java.util.Objects;

@TeleOp
public class GlobalTuning extends OpMode {

    HeadingPID headingPID = new HeadingPID(HEADING_KP,HEADING_KD);
    ForwardPID forwardPID = new ForwardPID(DRIVE_FORWARD_KP,DRIVE_FORWARD_KD);
    StrafePID strafePID = new StrafePID(STRAFE_KP,STRAFE_KD);
    FlywheelMechanism flywheelMechanism = new FlywheelMechanism();
    BotConstants constants = new BotConstants();

    int tunerSelected = 0;
    String type = tunerTypes.get(tunerSelected);

    double targetVelocity = 1000;
    double kP = 0;
    double kD = 0;
    double kV = 0;
    double kS = 0;
    double pidValue = 0;
    double headingPIDValue = 0;
    private double[] stepSizes = {0.001,0.01,0.1,1};
    private double[] powerStepSizes = {5,10,50,100};
    private int stepIndex = 0;
    private boolean hasPressed = false;
    double fLeftPower = 0;
    double fRightPower = 0;
    double bLeftPower = 0;
    double bRightPower = 0;
    double targetPower = 0.5;
    double targetDistance = 24;
    boolean runFlywheel = false;
    boolean runHeadingPID = false;



    public void choseTuner(){
        telemetry.addLine("Selected tuner");

        for(int i = 0; i < tunerTypes.size(); i++){
            if(Objects.equals(tunerTypes.get(i), tunerTypes.get(tunerSelected))){
                telemetry.addLine(">" + tunerTypes.get(i));
            }
            else{
                telemetry.addLine(tunerTypes.get(i));
            }

        }


        if(gamepad1.dpad_up && !hasPressed){
            tunerSelected = Range.clip(tunerSelected + 1, 0, tunerTypes.size() -1);
            hasPressed = true;

        }
        if(gamepad1.dpad_down && !hasPressed){
            tunerSelected = Range.clip(tunerSelected - 1, 0, tunerTypes.size() -1);
            hasPressed = true;
        }

        if(!gamepad1.dpad_down && !gamepad1.dpad_up && hasPressed){
            hasPressed = false;
        }

        type = tunerTypes.get(tunerSelected);

    }

    @Override
    public void init() {
        headingPID.init(hardwareMap);
        forwardPID.init(hardwareMap);
        strafePID.init(hardwareMap);
        flywheelMechanism.init(hardwareMap);

        constants.initMotors(hardwareMap);
        constants.initIMU(hardwareMap);

        telemetry.addLine("Init complete");
    }

    @Override
    public void init_loop() {
        choseTuner();
    }

    public void controls(){
        boolean rightTrigger = gamepad1.right_trigger > 0.5;
        boolean leftTrigger = gamepad1.left_trigger > 0.5;

        if(gamepad1.dpad_up && !hasPressed){
            kP += stepSizes[stepIndex];
            hasPressed = true;
        }
        else if(gamepad1.dpad_down && !hasPressed){
            kP -= stepSizes[stepIndex];
            hasPressed = true;
        }

        if(gamepad1.dpad_right && !hasPressed){
            kD += stepSizes[stepIndex];
            hasPressed = true;
        }
        else if(gamepad1.dpad_left && !hasPressed) {
            kD -= stepSizes[stepIndex];
            hasPressed = true;
        }






        if(gamepad1.y && !hasPressed){
            kV += stepSizes[stepIndex];
            hasPressed = true;
        }
        else if(gamepad1.a && !hasPressed){
            kV -= stepSizes[stepIndex];
            hasPressed = true;
        }

        if(gamepad1.b && !hasPressed){
            kS += stepSizes[stepIndex];
            hasPressed = true;
        }
        else if(gamepad1.x && !hasPressed) {
            kS -= stepSizes[stepIndex];
            hasPressed = true;
        }

        if(gamepad1.right_bumper && !hasPressed){
            stepIndex = stepIndex >= stepSizes.length - 1? stepSizes.length - 1: stepIndex + 1;
            hasPressed = true;
        }
        else if(gamepad1.left_bumper && !hasPressed){
            stepIndex = stepIndex <= 0 ? 0: stepIndex - 1;
            hasPressed = true;
        }


        if(rightTrigger && !hasPressed) {
            targetVelocity += powerStepSizes[stepIndex];
            hasPressed = true;
        }
        if(leftTrigger && !hasPressed){
            targetVelocity -= powerStepSizes[stepIndex];
            hasPressed = true;
        }

        if(-gamepad1.right_stick_y > 0.5){
            runFlywheel = true;
        }
        else{
            runFlywheel = false;
        }


        boolean beingPressed = gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_left || gamepad1.dpad_right || gamepad1.right_bumper || gamepad1.left_bumper || gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y || leftTrigger || rightTrigger;
        if(!beingPressed){
            hasPressed = false;
        }
    }
    @Override
    public void loop() {



        switch (type){
            case "HEADING_TUNER":
                headingPID.setkP(kP);
                headingPID.setkD(kD);
                pidValue = headingPID.runPID(0);


                fLeftPower = -pidValue;
                fRightPower = pidValue;
                bLeftPower = -pidValue;
                bRightPower = pidValue;

                runHeadingPID = true;
                break;

            case "DRIVE_STRAFE":
                strafePID.setkP(kP);
                strafePID.setkD(kD);
                pidValue = gamepad1.a ? strafePID.runPID(targetPower, true): 0;



                fLeftPower = pidValue;
                fRightPower = -pidValue;
                bLeftPower = -pidValue;
                bRightPower = pidValue;

                telemetry.addData("Target power", targetPower);
                telemetry.addData("Current power", strafePID.getCurPower());

                runHeadingPID = true;
                break;

            case "DRIVE_FORWARD":
                forwardPID.setkP(kP);
                forwardPID.setkD(kD);
                pidValue = gamepad1.a ? forwardPID.runPID(targetPower, true): 0;



                fLeftPower = pidValue;
                fRightPower = pidValue;
                bLeftPower = pidValue;
                bRightPower = pidValue;

                telemetry.addData("Target power", targetPower);
                telemetry.addData("Current power", forwardPID.getCurPower());

                runHeadingPID = true;
                break;

            case "AUTO_FORWARD":
                forwardPID.setkP(kP);
                forwardPID.setkD(kD);

                pidValue = gamepad1.a ? forwardPID.runPID(targetDistance, false): 0;



                fLeftPower = pidValue;
                fRightPower = pidValue;
                bLeftPower = pidValue;
                bRightPower = pidValue;


                telemetry.addData("Target Distance", targetDistance);
                telemetry.addData("Distance travelled", curDistanceY());

                runHeadingPID = true;
                break;


            case "AUTO_STRAFE":
                strafePID.setkP(kP);
                strafePID.setkD(kD);

                pidValue = gamepad1.a ? strafePID.runPID(targetDistance, false): 0;



                fLeftPower = pidValue;
                fRightPower = -pidValue;
                bLeftPower = -pidValue;
                bRightPower = pidValue;


                telemetry.addData("Target Distance", targetDistance);
                telemetry.addData("Distance travelled", curDistanceX());

                runHeadingPID = true;
                break;

            case "FLYWHEEL_P+FF":
                if(runFlywheel){
                    flywheelMechanism.runFlywheelController(targetVelocity,kV,kS,kP);
                }
                else{
                    flywheelMechanism.runFlywheelController(0,kV,kS,kP);
                }

                telemetry.addData("Current velocity", convertToRPM((FLYWHEEL_1.getVelocity() + FLYWHEEL_2.getVelocity())/2,FLYWHEEL_CPR));
                telemetry.addData("Target velocity", targetVelocity);

                break;

            case "FLYWHEEL_PIDF":
                if(runFlywheel){
                    flywheelMechanism.runFlywheelPIDF(kP,kS,targetVelocity);
                }
                else{
                    flywheelMechanism.runFlywheelPIDF(kP,kS,0);
                }

                telemetry.addData("Current velocity", convertToRPM((FLYWHEEL_1.getVelocity() + FLYWHEEL_2.getVelocity())/2,FLYWHEEL_CPR));
                telemetry.addData("Target velocity", targetVelocity);




                break;




            case "NULL":
                telemetry.addLine("YO WHAT THE HECK. YOU WERE SUPPOSED TO SELECT IN INIT");
        }

        if(runHeadingPID){
            headingPID.setkP(HEADING_KP);
            headingPID.setkD(HEADING_KD);
            headingPIDValue = headingPID.runPID(0);
        }

        fLeftPower -= headingPIDValue;
        fRightPower += headingPIDValue;
        bLeftPower -= headingPIDValue;
        bRightPower += headingPIDValue;

        double maxMotorPower = Math.max(Math.max(Math.abs(bLeftPower),Math.abs(bRightPower)),Math.max(Math.abs(fLeftPower),Math.abs(fRightPower)));
        if(maxMotorPower <1){maxMotorPower = 1;}



        F_LEFT.setPower(fLeftPower/maxMotorPower);
        F_RIGHT.setPower(fRightPower/maxMotorPower);
        B_LEFT.setPower(bLeftPower/maxMotorPower);
        B_RIGHT.setPower(bRightPower/maxMotorPower);


        telemetry.addData("kP", kP);
        telemetry.addData("kD", kD);
        telemetry.addData("kV", kV);
        telemetry.addData("kS", kS);
        telemetry.addData("Step size", stepSizes[stepIndex]);
        telemetry.addData("Power step size", powerStepSizes[stepIndex]);
        telemetry.addData("Heading", headingPID.getHeading());
        telemetry.addLine("Current tuner" + type);
        telemetry.update();

    }
}
