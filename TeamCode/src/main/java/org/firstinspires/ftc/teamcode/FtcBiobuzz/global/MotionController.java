package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import java.util.ArrayList;

public class MotionController {

    double controllerOutput = 0;


    public double getControllerOutput(){
        return controllerOutput;
    }

    public void plus(double addend){
        controllerOutput += addend;
    }


    public void minus(double subtrahend){
        controllerOutput -= subtrahend;
    }

    public void times(double scalar){
        controllerOutput *= scalar;
    }


    public MotionController pid(double kP, double kI, double kD){



        return this;
    }

    public MotionController proportional(double kP){
        return this;
    }

    public MotionController feedforward(double kS){
        return this;
    }

    public MotionController feedforward(double kS, double kV){
        return this;
    }

    public MotionController feedforward(double kS, double kV, double kA){
        return this;
    }

    public MotionController feedforward(double kS, double kV, double kA, double kG){
        //double output = Kp * (referenceVelocity - currentVelocity) + Kv * (referenceVelocity);
        return this;
    }


    public MotionController fullStateSpace(double curVelocity, double curAcceleration, double targetVelocity, double targetAcceleration){
        return this;
    }


    public double gainSchedule(){
        return 1;
    }

    public double linearQuadraticRegression(){
        return 1;
    }

    public double motionProfile(){
        return 1;
    }



    public class piecewise{
        ArrayList <Double> controllers = new ArrayList<Double>();
        ArrayList <Double> thresholds = new ArrayList<Double>();


        public piecewise(double controller){
            controllers.add(controller);
        }

        public piecewise put(double controller, double threshold){
            controllers.add(controller);
            thresholds.add(threshold);
            return this;
        }


        public double output(){
            double output = Double.NEGATIVE_INFINITY;

            for(int controller = 0; controller < controllers.size(); controller++){


                double threshold = controller == 0 ? Double.POSITIVE_INFINITY: thresholds.get(controller -1);

                if (controllers.get(controller) < threshold && controllers.get(controller) > output) {
                    output = controllers.get(controller);
                }
            }
            return output;

        }


    }






}
