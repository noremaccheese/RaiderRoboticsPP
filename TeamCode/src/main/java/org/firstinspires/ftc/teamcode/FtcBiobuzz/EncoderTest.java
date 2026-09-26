package org.firstinspires.ftc.teamcode.FtcBiobuzz;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class EncoderTest extends OpMode {


    private GoBildaPinpointDriver pinpoint;

    @Override
    public void init() {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(0,2, DistanceUnit.INCH);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.resetPosAndIMU();
        telemetry.addLine("init complete :"+pinpoint.getDeviceID());

    }


    @Override
    public void loop() {
        pinpoint.update();
        telemetry.addData("x", pinpoint.getEncoderX());
        telemetry.addData("y", pinpoint.getEncoderY());
        telemetry.addData("heading", pinpoint.getHeading(AngleUnit.DEGREES));
        telemetry.update();
    }
}
