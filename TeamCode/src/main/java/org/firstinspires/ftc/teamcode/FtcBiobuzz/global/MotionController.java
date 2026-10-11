package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

import java.util.ArrayList;

public class MotionController {

    double controllerOutput = 0;


    public double getControllerOutput(){
        return controllerOutput;
    }

    public void plus(double addend){
        controllerOutput += addend;
    } // fix these


    public void minus(double subtrahend){ // fix these
        controllerOutput -= subtrahend;
    }

    public void times(double scalar){ // fix these
        controllerOutput *= scalar;
    }


    public MotionController fullStateSpace(double curVelocity, double curAcceleration, double targetVelocity, double targetAcceleration){ //work in progress
        return this;
    }


    public double gainSchedule(){ //work in progress
        return 1;
    }

    public double linearQuadraticRegression(){ //work in progress
        return 1;
    }

    public double motionProfile(){ //work in progress
        return 1;
    }

    public class PID{
        double output = 0;
        double curTime = 0;
        double lastTime = 0;
        double curError = 0;
        double lastError = 0;
        double lastIntegral = 0;
        double deltaTime = 0;
        double deltaError = 0;
        double integral = 0;



        public void setTimes(double curTime, double lastTime){
            this.curTime = curTime;
            this.lastTime = lastTime;

        }
        public void setLastError(double lastError){
            this.lastError = lastError;
        }
        public void setCurError(double curError){
            this.curError = curError;
        }
        public void setLastIntegral(double lastIntegral){
            this.lastIntegral = lastIntegral;
        }
        private void updateDeltaTime(double curTime, double lastTime){
            deltaTime = curTime - lastTime;
        }
        private void updateDeltaError(double curError, double lastError){
            deltaError = curError - lastError;
        }





        private double proportionalValue(double kP){
            return kP * curError;
        }

        public double derivativeValue(double kD){
            return kD * deltaError/deltaTime;

        }

        public double integralValue(double kI){
            integral = lastIntegral + (curError * deltaTime);
            return kI * (integral);
        }

        public PID proportional(double kP, double error){
            setCurError(error);
            output = proportionalValue(kP);
            return this;
        }

        public PID pid(double kP, double kI, double kD, double curTime, double curError){
            updateDeltaTime(curTime,lastTime);
            updateDeltaError(curError,lastError);
            this.curError = curError;

            output = proportionalValue(kP) + integralValue(kI) + derivativeValue(kD);
            lastTime = curTime;
            lastError = curError;
            lastIntegral = integral;
            return this;
        }


        public double output(){
            return output;
        }
    }


    public class Feedforward{
        double referenceVelocity = 0;
        double referenceAcceleration = 0;
        double referenceAngle = 0;
        double output = 0;



        public void setVelocityReference(double referenceVelocity){
            this.referenceVelocity = referenceVelocity;
        }

        public void setAccelerationReference(double referenceAcceleration){
            this.referenceAcceleration = referenceAcceleration;
        }

        public void setAngleReference(double referenceAngle){
            this.referenceAngle = referenceAngle;
        }

        public Feedforward feedforward(double kS){
            output = kS*Math.signum(referenceVelocity);
            return this;
        }

        public Feedforward feedforward(double kS, double kV){
            output = kS*Math.signum(referenceVelocity) + kV * referenceVelocity;
            return this;
        }

        public Feedforward feedforward(double kS, double kV, double kA){
            output = kS*Math.signum(referenceVelocity) + kV * referenceVelocity + kA * referenceAcceleration;
            return this;
        }

        public Feedforward feedforward(double kS, double kV, double kA, double kG){
            output = kS*Math.signum(referenceVelocity) + kV * referenceVelocity + kA * referenceAcceleration + kG * Math.cos(referenceAngle);
            return this;
        }

        public double output(){
            return output;
        }

    }



    public class Piecewise{
        ArrayList <Double> controllers = new ArrayList<Double>();
        ArrayList <Double> thresholds = new ArrayList<Double>();


        public Piecewise(double controller){
            controllers.add(controller);
        }

        public Piecewise put(double controller, double threshold){
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
