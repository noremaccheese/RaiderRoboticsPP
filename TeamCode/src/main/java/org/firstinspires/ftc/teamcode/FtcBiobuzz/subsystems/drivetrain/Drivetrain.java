package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.drivetrain;

import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.B_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.B_RIGHT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.F_LEFT;
import static org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants.F_RIGHT;

import com.pedropathing.controllers.Controller;
import com.pedropathing.controllers.PIDController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CameronPathing.Follower;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.BotConstants;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.MathUtil;
import org.firstinspires.ftc.teamcode.FtcBiobuzz.util.math.Vector2d;

public class Drivetrain {

    BotConstants constants = new BotConstants();
    boolean fieldCentric = true;
    double joystickTolerance = 0.1;
    double deadZone = 0.05;
    MathUtil m = new MathUtil();
    Follower follower;
    double targetHeading;
    double kP = 0;
    double kD = 0;



    public Drivetrain(boolean fieldCentric, Follower follower){
        this.fieldCentric = fieldCentric;
        this.follower = follower;
    }


    public void init(HardwareMap h){
        constants.initMotors(h);
        try {
            targetHeading = follower.getHeading();
        }
        catch (NullPointerException e){
            targetHeading = 0;
        }
    }

    public void setPowers(Vector2d driveVector, double rx){
        double x = driveVector.getX();
        double y = driveVector.getY();

        double fL = y + x + rx;
        double fR = y - x - rx;
        double bL = y - x + rx;
        double bR = y + x - rx;


        fL = Math.abs(fL) > deadZone ? fL: 0;
        fR = Math.abs(fR) > deadZone ? fR: 0;
        bL = Math.abs(bL) > deadZone ? bL: 0;
        bR = Math.abs(bR) > deadZone ? bR: 0;



        double maxPower = Math.max(Math.abs(fL), Math.max(Math.abs(fR), Math.max(Math.abs(bL), Math.abs(bR))));

        if(maxPower > 1){
            F_LEFT.setPower(fL/maxPower);
            F_RIGHT.setPower(fR/maxPower);
            B_LEFT.setPower(bL/maxPower);
            B_RIGHT.setPower(bR/maxPower);
        }
        else {
            F_LEFT.setPower(fL);
            F_RIGHT.setPower(fR);
            B_LEFT.setPower(bL);
            B_RIGHT.setPower(bR);
        }

    }

    public Vector2d smoothDrive(Vector2d driveVector){
        double x = driveVector.getX();
        double y = driveVector.getY();



        if(x == 0 || driveVector.getY() == 0){
            return new Vector2d(x,y);
        }
        else {
            double min = Math.min(Math.abs(x), Math.abs(y));
            double max = Math.max(Math.abs(x), Math.abs(y));

            if(min / max < joystickTolerance) {
                if(min == x){
                    return new Vector2d(0, y);
                }
                else {
                    return new Vector2d(x, 0);
                }
            }
            else {
                return new Vector2d(x,y);
            }
        }


    }



    public void driverControlled(Gamepad gamepad){
        Vector2d driveVector = smoothDrive(new Vector2d(gamepad.left_stick_x, -gamepad.left_stick_y));
        double rx = gamepad.right_stick_x;
        if(rx > 0){
            targetHeading = follower.getHeading();
        }

        double pidValue = headingPID(targetHeading, kP, kD);

        double rotationPower;

        if(rx == 0){
            rotationPower = pidValue;
        }
        else {
            rotationPower = rx;
        }

        if(fieldCentric){
            driveVector = m.rotMatrix(driveVector, follower.getHeading());
        }


        setPowers(driveVector, rotationPower);

    }


    public double headingPID(double targetHeading, double kP, double kD){
        Controller controller = new PIDController(kP, 0, kD);
        return controller.calculate(Math.toRadians(targetHeading), follower.getHeading()) * 100;
    }

}
