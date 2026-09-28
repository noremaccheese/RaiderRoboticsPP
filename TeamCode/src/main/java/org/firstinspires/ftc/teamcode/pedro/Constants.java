package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fLeft");
        c.frontRightName.set("fRight");
        c.backLeftName.set("bLeft");
        c.backRightName.set("bRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-0.18048449764101523);
        c.yPodOffset.set(1.7354196263110546);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.33138669917566954);
                Controller secondaryTranslationalForward = Controller.proportional(0.12243853052972645);
                Controller primaryTranslationalLateral = Controller.proportional(0.5265637694551792);
                Controller secondaryTranslationalLateral = Controller.proportional(0.19455124277063718);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01333447453388276));
                c.brake.set(Controller.proportionalFeedforward(0.011334303353800345));

                c.headingFeedback.set(Controller.proportional(7.182780830385286));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05920713560368972, 0.007665061141592494));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07671697504659586, 0.07984352641313623));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001654739001433698, 0.0013816347777023984));

                c.maxAchievableForwardVelocity.set(64.85669451228753);
                c.maxAchievableStrafeVelocity.set(52.016370118475024);
                c.naturalForwardDeceleration.set(40.93273546511781);
                c.naturalStrafeDeceleration.set(58.19029625667342);
            }
    );
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);



        return null;
    }
}

/*
these are from pedro pathing 2
idk why im keeping these here we probaly could delete


public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(8.42)


            ;

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("fRight")
            .rightRearMotorName("bRight")
            .leftRearMotorName("bLeft")
            .leftFrontMotorName("fLeft")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(0)
            .strafePodX(-1)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
 */