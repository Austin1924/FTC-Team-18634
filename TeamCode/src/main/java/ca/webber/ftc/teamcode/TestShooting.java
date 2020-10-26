package ca.webber.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TestShooting", group = "")
public class TestShooting extends LinearOpMode {

    private DcMotor shooter1;
    private DcMotor right;

    @Override
    public void runOpMode() {
        shooter1 = hardwareMap.dcMotor.get("shooter1");
        right = hardwareMap.dcMotor.get("right");

        waitForStart();
        if (opModeIsActive()) {

            while (opModeIsActive()) {

                telemetry.addData("Power", (gamepad1.left_stick_y + 1) / 2);                    //If the x-component of the joystick is moved towards the right, then the left wheel is set to the power(coefficient) and the right wheel is set to a power value of 1 plus the x-value of the right turn.

                right.setPower(-1 * ((-gamepad1.left_stick_y + 1) / 2));

                telemetry.update();
            }
        }
    }
}