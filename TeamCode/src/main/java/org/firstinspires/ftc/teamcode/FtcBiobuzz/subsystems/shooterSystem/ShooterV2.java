package org.firstinspires.ftc.teamcode.FtcBiobuzz.subsystems.shooterSystem;


import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class ShooterV2 {

    private double ffPower;
    private double pidPower;

    Follower follower;
    CRServo turretServo;
    AnalogInput analogInput;


    public ShooterV2(Follower follower, CRServo turretServo, AnalogInput analogInput) {
        this.follower = follower;
        this.turretServo = turretServo;
        this.analogInput = analogInput;
    }


    private enum ShooterState{
        SHOOTING,
        IDLE
    }

    ShooterState shooterState = ShooterState.IDLE;
    Turret turret = new Turret(turretServo, analogInput);
    FlywheelMechanism flywheel = new FlywheelMechanism();
    ShooterSystem shooterSystem = new ShooterSystem();


    public void init(){
        shooterSystem.init(follower, turretServo,analogInput);
    }


    public void runServo(){
        turretServo.setPower(Range.clip((ffPower + pidPower),-1,1));
    }



    private void turretFF(){
        ffPower = turret.runFeedforward();
        runServo();
    }


    private void runTurretPID(){
        pidPower = turret.runTurretToPosition();
        runServo();
    }



    private void spinUpFlywheel(){
        flywheel.initTurret(follower, turretServo, analogInput);
        flywheel.runPedroFlywheel(0.0015,0.0002, flywheel.bestVelocity());
    }

    private void setHoodAngle(){

    }


    private void calculateCorrectedPose(){

    }


    private void shootBalls(){

    }

    public boolean botMoving(){
        return true;
    }

    public boolean readyToShoot(){
        return true;
    }

    public boolean flywheelCorrectRPM(){
        return true;

    }

    public boolean flywheelCorrectable(){
        return true;

    }

    public boolean moreBalls(){
        return true;

    }







    public void runShooter(){
        if(botMoving()){
            turretFF();
        }

        if(readyToShoot()){
            shootBalls();
        }

        else{
            if (!flywheelCorrectRPM()){
                spinUpFlywheel();
                if(!flywheelCorrectable()){
                    return;
                }
            }

            setHoodAngle();
            calculateCorrectedPose();
            runTurretPID();

            if(readyToShoot()){
                shootBalls();
            }
        }

    }

    public void holdTarget(){
        if(botMoving()){
            turretFF();
        }

        runTurretPID();
    }


    public void periodic(Gamepad gamepad){
        shooterSystem.updateFollower();
        if(gamepad.a){
            shooterState = ShooterState.SHOOTING;
        }
        else {
            shooterState = ShooterState.IDLE;
        }


        switch (shooterState){
            case SHOOTING:
                runShooter();
                break;

            case IDLE:
                holdTarget();
                break;
        }
    }




}
