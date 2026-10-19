package org.firstinspires.ftc.teamcode.FtcBiobuzz;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

@TeleOp
public class AxonPositionTest extends OpMode {

    private CRServoImplEx TURRET_SERVO_1;
    private AnalogInput axonInput;
    double lastVoltage = 0;
    double fullRotationVoltage = 0;
    @Override
    public void init() {
        TURRET_SERVO_1 = hardwareMap.get(CRServoImplEx.class, "turretServo1");
        axonInput = hardwareMap.get(AnalogInput.class, "axonFeedback");
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {
        double detectedVoltage = axonInput.getVoltage();
        if(gamepad1.a){
            TURRET_SERVO_1.setPower(0.5);
        }
        if(gamepad1.b){
            if(detectedVoltage > lastVoltage){
                TURRET_SERVO_1.setPower(0.2);
            }
            else{
                TURRET_SERVO_1.setPower(0);
                fullRotationVoltage = lastVoltage;
            }
        }


        lastVoltage = detectedVoltage;
        telemetry.addData("axon input", axonInput);
        telemetry.addData("correct voltage", fullRotationVoltage);
        telemetry.update();

    }
}
