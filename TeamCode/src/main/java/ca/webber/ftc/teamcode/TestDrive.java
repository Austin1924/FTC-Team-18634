package ca.webber.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TestDrive", group = "")
public class TestDrive extends LinearOpMode {

    private DcMotor left;
    private DcMotor right;
    private double coefficient;
    final double NINETYDEGREES = 90 * Math.PI / 180;
    private boolean isTankDrive = false, goingRight, stopped = true, xPressed = false;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        // Reverse the right drive motor in order to move the robot in the correct direction with the same powers.

        right.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        if (opModeIsActive()) {

            while (opModeIsActive()) {

                // Code in order to toggle robot being stopped and not.
                if (gamepad1.x && xPressed)
                    continue;
                //
                if (gamepad1.x && !xPressed) {
                    xPressed = true;
                }
                if (!gamepad1.x && xPressed) {
                    stopped = !stopped;
                    xPressed = false;
                    continue;
                }

                if (isTankDrive) {

                } else {

                    // The Y axis of a joystick ranges from -1 in its topmost position
                    // to +1 in its bottommost position. We negate this value so that
                    // the topmost position corresponds to maximum forward power.

                    telemetry.addData("Stopped?", stopped);
                    telemetry.addData("Coefficient without stopped", ((gamepad1.left_stick_y < 0) ? 1: -1) * ((-1 * gamepad1.right_stick_y + 1) / 2));
                    //Variable coefficient is the power multiplier of the robot.
                    /*First part of the coefficient is to determine whether the robot should move in the first place, with 0 being not moving, and 1 being moving.
                    This value is multiplied by the value which indicates if the y-component of the left joystick is up or down. It is -1 if it is up and 1 if it is down.
                    The last part is the heart of this coefficient. The right stick sets the power for the robot and takes it to 0 to 1. the center of the right stick
                    is considered as 0.5 power, top as 1, and bottom as 0.*/
                    coefficient = ((stopped) ? 0: 1) * ((gamepad1.left_stick_y <= 0) ? 1: -1) * ((-1 * gamepad1.right_stick_y + 1) / 2);

                    telemetry.addData("Coefficient", coefficient);
                    System.out.println("Here is a statement on the console");

                    //telemetry.addData("Gamepad1 right stick y", gamepad1.right_stick_y);
                    telemetry.addData("Gamepad1 left stick y", gamepad1.left_stick_y);
                    System.out.println("Here is a statement on the console v2!");

                    //If the x-component of the joystick is moved towards the right, then the left wheel is set to the power(coefficient) and the right wheel is set to a power value of 1 plus the x-value of the right turn.
                    if (gamepad1.left_stick_x < 0) {
                        left.setPower(coefficient);
                        /*right.setPower(coefficient * (1 - ( Math.atan(Math.abs(gamepad1.left_stick_x / gamepad1.left_stick_y)) / NINETYDEGREES ) ) );*/
                        right.setPower(coefficient * (1 + gamepad1.left_stick_x));
                        //If the x-component of the joystick is moved towards the left, then the right wheel is set to the power(coefficient) and the left wheel is set to a power value of 1 minus the x-value of the left turn.
                    } else {
                        right.setPower(coefficient);
                        /*left.setPower(coefficient * (1 - ( Math.atan(Math.abs(gamepad1.left_stick_x / gamepad1.left_stick_y)) / NINETYDEGREES ) ) );*/
                        left.setPower(coefficient * (1 - gamepad1.left_stick_x));
                    }

                    telemetry.addData("Left Pow", left.getPower());
                    telemetry.addData("Right Pow", right.getPower());
                    telemetry.update();


                }
            }
        }
    }
}