package org.firstinspires.ftc.teamcode.FtcBiobuzz.global;

public class CustomController {

    double controllerOutput = 0;




    public double getControllerOutput(){
        return controllerOutput;
    }

    /*
    public void plus(double addend){
        controllerOutput += addend;
    } // fix these


    public void minus(double subtrahend){ // fix these
        controllerOutput -= subtrahend;
    }

    public void times(double scalar){ // fix these
        controllerOutput *= scalar;
    }

     */


    public CustomController fullStateSpace(double curVelocity, double curAcceleration, double targetVelocity, double targetAcceleration){ //work in progress
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



    /*public class PiecewiseController {
        ArrayList<Double> controllers = new ArrayList<Double>();
        ArrayList<Double> thresholds = new ArrayList<Double>();


        public PiecewiseController(double controller) {
            controllers.add(controller);
        }

        public PiecewiseController put(double controller, double threshold) {
            controllers.add(controller);
            thresholds.add(threshold);
            return this;
        }


        public double output() {
            double output = Double.NEGATIVE_INFINITY;

            for (int controller = 0; controller < controllers.size(); controller++) {


                double threshold = controller == 0 ? Double.POSITIVE_INFINITY : thresholds.get(controller - 1);

                if (controllers.get(controller) < threshold && controllers.get(controller) > output) {
                    output = controllers.get(controller);
                }
            }
            return output;

        }



    }

     */








}
