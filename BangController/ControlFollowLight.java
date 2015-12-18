public class ControlFollowLight extends RobotFunctions {
	private static int TIME_STEP = 16;
	private static int MAX_DISTANCE = 100;

	private boolean _typeProportional;
	private boolean _proximityStop;

	public ControlFollowLight(boolean typeProportional, boolean proximityStop) {
		super();
		_typeProportional = typeProportional;
		_proximityStop = proximityStop;
	}

	public void run() {
		while (step(TIME_STEP) != -1) {
			if (_typeProportional){
				doFollowLightProportional();
			} else {
				doFollowLightBang();
			}
		}
	}

	private void doFollowLightProportional(){
		// "proportional controller"
		// get light values for vector
		double[] lightVector = { getLightSensorValue(Sensor.LS_LEFT),
									  getLightSensorValue(Sensor.LS_FRONTLEFT),
									  getLightSensorValue(Sensor.LS_LEFTFRONT),
									  getLightSensorValue(Sensor.LS_RIGHT),
									  getLightSensorValue(Sensor.LS_FRONTRIGHT),
									  getLightSensorValue(Sensor.LS_RIGHTFRONT)};
		
		double[][] controllerMatrix = {{0.01, 0.01, 0.01, 0, 0, 0 }, { 0, 0, 0, 0.01, 0.01, 0.01 }};
		double[] resultVector = Matrix.multiply(controllerMatrix, lightVector);
		int resultVectorLeft = (int)Math.round(resultVector[0]);
		int resultVectorRight = (int)Math.round(resultVector[1]);
		
		// calc wheel speed
		int speedLeft = ProportionalWheels.calcLeftWheelSpeed(resultVectorLeft);
		int speedRight = ProportionalWheels.calcRightWheelSpeed(resultVectorRight);

		// TODO: here we need to modify the resultvector values to transform it to the 0...1000 speed range for the wheels
		// speedUpValue (this one is just a current hack)
		int speedUpValue = 10;
		if (checkMaxDistance() == false){
			setWheelsSpeed(speedLeft * speedUpValue, speedRight * speedUpValue);
		}

		// debug console output
		System.out.println("Lightsensor Values: LEFT[" + (int) lightVector[0] + "] LEFTFRONT[" + (int) lightVector[1] + "] RIGHTFRONT[" + 
		                                                 (int) lightVector[2] + "] RIGHT[" + (int) lightVector[3] + "]");
		System.out.println("Matrix multiply result: LEFT[" + (int) resultVector[0] + "]  RIGHT[" + (int) resultVector[1] + "]");
		System.out.println("Wheel Speed: LEFT[" + speedLeft + "]  RIGHT[" + speedRight + "]");
	}

	private void doFollowLightBang(){
		if (getLightDirection() == 4 
				|| getLightDirection() == 5
				|| getLightDirection() == 6){
			if (checkMaxDistance() == false){
				driveLeft();
			}
		} else if (getLightDirection() == 3 
				|| getLightDirection() == 2
				|| getLightDirection() == 1) {
			if (checkMaxDistance() == false){
				driveRight();
			}
		} else {
			if (checkMaxDistance() == false){
				driveForward();
			}
		}
		
		// debug console output
		System.out.println("Light direction (Sensorindex): " + getLightDirection());
		System.out.println("Current Speed: LEFT[" +  getLeftSpeed() + "] RIGHT[" + getRightSpeed() + "]"); 
	}

	private boolean checkMaxDistance(){
		// check if max distance to obstacle is reached
		boolean maxReached = false;
		if (_proximityStop){
			System.out.println("Distance Values: LEFTFRONT[" + (int) getDistanceSensorValue(Sensor.DS_LEFTFRONT) + 
					"] FRONTLEFT[" + (int) getDistanceSensorValue(Sensor.DS_FRONTLEFT) + 
					"] FRONTRIGHT[" + (int) getDistanceSensorValue(Sensor.DS_FRONTRIGHT) +
					"] RIGHTFRONT[" + (int) getDistanceSensorValue(Sensor.DS_RIGHTFRONT) +"]");

			if (getDistanceSensorValue(Sensor.DS_FRONTLEFT) > MAX_DISTANCE ||
					getDistanceSensorValue(Sensor.DS_FRONTRIGHT) > MAX_DISTANCE	||
					getDistanceSensorValue(Sensor.DS_LEFTFRONT) > MAX_DISTANCE	||
					getDistanceSensorValue(Sensor.DS_RIGHTFRONT) > MAX_DISTANCE){
				maxReached = true;
				stopRobot();
				System.out.println("Robot stopped");
			}
		}
		return maxReached;
	}

	private int getLightDirection(){
		// get light direction (check which sensor is most near to light)
		double small = getLightSensorValue(0);
		int index = 0;

		for (int i = 0; i < 7; i++){
			if (getLightSensorValue(i) < small) {
				small= getLightSensorValue(i);
				index = i;
			}
		}

		return index;
	}
}
