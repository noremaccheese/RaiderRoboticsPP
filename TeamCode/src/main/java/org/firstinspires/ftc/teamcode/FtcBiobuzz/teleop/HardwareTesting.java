package org.firstinspires.ftc.teamcode.FtcBiobuzz.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.Map;
import java.util.Set;

@TeleOp(name="Hardware Testing")
public class HardwareTesting extends OpMode {
    private Map.Entry<String, DcMotor>[] motors    = new Map.Entry[0];
    private Map.Entry<String, Servo  >[] servos    = new Map.Entry[0];
    private Map.Entry<String, CRServo>[] cr_servos = new Map.Entry[0];

    private Set<Map.Entry<String, TouchSensor>> touch_sensors;
    private Set<Map.Entry<String, ColorSensor>> color_sensors;

    private int out_idx;
    private double power;
    private boolean left_stick_active;

    public void init() {
        motors = hardwareMap.dcMotor.entrySet().toArray(motors);
        servos = hardwareMap.servo.entrySet().toArray(servos);
        cr_servos = hardwareMap.crservo.entrySet().toArray(cr_servos);

        touch_sensors = hardwareMap.touchSensor.entrySet();
        color_sensors = hardwareMap.colorSensor.entrySet();
    }

    public void loop() {
        final int SPECIAL_OUT_INDEXES = 4;

        if (gamepad1.leftBumperWasPressed()) {
            out_idx -= 1;
            if (out_idx < 0) out_idx = 0;
        }

        if (gamepad1.rightBumperWasPressed()) {
            out_idx += 1;
            int indexes = SPECIAL_OUT_INDEXES + motors.length + servos.length + cr_servos.length;
            if (out_idx >= indexes) out_idx = indexes - 1;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            power -= 0.1;
            if (power < -1) power = -1;
        }

        if (gamepad1.dpadRightWasPressed()) {
            power += 0.1;
            if (power > 1) power = 1;
        }

        if (left_stick_active || gamepad1.left_stick_x != 0) {
            power = gamepad1.left_stick_x;
        }
        left_stick_active = gamepad1.left_stick_x != 0;

        double servo_pos = 0.5 + power / 2;
        int motor_idx = out_idx - SPECIAL_OUT_INDEXES;
        int servo_idx = motor_idx - motors.length;
        int cr_servo_idx = servo_idx - servos.length;

        telemetry.addLine("=== Outputs === ");
        if (out_idx == 0) {
            telemetry.addLine("None (Bumpers to Cycle)");
        }

        else if (out_idx == 1) {
            telemetry.addData("Motor Power", power);
            for (Map.Entry<String, DcMotor> m: motors) {
                telemetry.addLine("-> " + m.getKey());
                m.getValue().setPower(power);
            }


        } else if (out_idx == 2) {
            telemetry.addData("Servo Position", servo_pos);
            for (Map.Entry<String, Servo> s: servos) {
                telemetry.addLine("-> " + s.getKey());
                s.getValue().setPosition(servo_pos);

            }
        } else if (out_idx == 3) {
            telemetry.addData("CRServo Power", power);
            for (Map.Entry<String, CRServo> s: cr_servos) {
                telemetry.addLine("-> " + s.getKey());
                s.getValue().setPower(power);


            }
        } else if (motor_idx < motors.length) {
            telemetry.addData("Motor Power", power);
            telemetry.addLine("-> " + motors[motor_idx].getKey());
            motors[motor_idx].getValue().setPower(power);


        } else if (servo_idx - servos.length < servos.length) {
            telemetry.addData("Servo Position", servo_pos);
            telemetry.addLine("-> " + servos[servo_idx].getKey());
            servos[servo_idx].getValue().setPosition(servo_pos);


        } else if (cr_servo_idx < cr_servos.length) {
            telemetry.addData("CRServo Power", power);
            telemetry.addLine("-> " + cr_servos[cr_servo_idx].getKey());
            cr_servos[cr_servo_idx].getValue().setPower(power);
        }

        telemetry.addLine();
        telemetry.addLine("=== Inputs === ");
        telemetry.addData("Gamepad1", gamepad1);
        telemetry.addData("Gamepad2", gamepad2);

        for (Map.Entry<String, TouchSensor> e: touch_sensors) {
            TouchSensor s = e.getValue();
            telemetry.addData("Touch Sensor '%s' <- %f", e.getKey(), s.getValue());
        }

        for (Map.Entry<String, ColorSensor> e: color_sensors) {
            ColorSensor s = e.getValue();
            telemetry.addData("Color Sensor '%s' <- R %d, G %d, B %d, A %d",
                    e.getKey(), s.red(), s.green(), s.blue(), s.alpha());
        }

        telemetry.addLine();
        telemetry.addLine("=== Complete Device Listing === ");
        if (gamepad1.a) {
            for (HardwareMap.DeviceMapping<? extends HardwareDevice> map: hardwareMap.allDeviceMappings) {
                for (Map.Entry<String, ? extends HardwareDevice> entry: map.entrySet()) {
                    telemetry.addData(entry.getValue().getDeviceName(), entry.getKey());
                }
            }
        } else {
            telemetry.addLine("(Press A to see)");
        }
        telemetry.update();
    }
}
