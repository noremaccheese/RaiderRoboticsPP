package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class FlywheelTest extends OpMode {


    private DcMotor flywheel1;
    private DcMotor flywheel2;
    @Override
    public void init() {
        flywheel1= hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel2= hardwareMap.get(DcMotor.class, "flywheel2");

        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            flywheel1.setPower(1);
            flywheel2.setPower(1);
        }
        if(gamepad1.b){
            flywheel1.setPower(-1);
            flywheel2.setPower(-1);
        }

    }
}
