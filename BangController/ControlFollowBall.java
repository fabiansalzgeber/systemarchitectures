public class ControlFollowBall extends RobotFunctions {
	private static int TIME_STEP = 16;
	private static int MAX_SENSOR_VALUE = 200;

	private boolean _typeProportional;
	
	public ControlFollowBall(boolean typeProportional) {
		super();
		
		_typeProportional = typeProportional;
	}

	public void run() {
		while (step(TIME_STEP) != -1) {
			if (_typeProportional){
				doFollowBallProportional();
			} else {
				doFollowBallBang();
			}
		}
	}
	
	private void doFollowBallProportional(){
		// get distance vector values
		double[] distVector = { getDistanceSensorValue(Sensor.LS_LEFT), getDistanceSensorValue(Sensor.LS_LEFTFRONT), 
				getDistanceSensorValue(Sensor.LS_FRONTLEFT), getDistanceSensorValue(Sensor.LS_FRONTRIGHT),
				getDistanceSensorValue(Sensor.LS_RIGHTFRONT), getDistanceSensorValue(Sensor.LS_RIGHT)};

		double[][] controllerMatrix = { { 0, 0, 0, 1, 0.25, 0 }, { 1, 0.25, 0, 0, 0, 0 } };
		double[] resultVector = Matrix.multiply(controllerMatrix, distVector);
		int resultVectorLeft = (int)Math.round(resultVector[0]);
		int resultVectorRight = (int)Math.round(resultVector[1]);

		// calc wheel speed
		int speedLeft = ProportionalWheels.calcLeftWheelSpeed(resultVectorLeft);
		int speedRight = ProportionalWheels.calcRightWheelSpeed(resultVectorRight);
		int speedUpValue = 10;
		setWheelsSpeed(speedLeft * speedUpValue, speedRight * speedUpValue);

		// debug console output
		System.out.println("Distance sensor values: LEFT[" + (int) distVector[0] + "] LEFTFRONT[" + (int) distVector[1] + "] RIGHTFRONT[" + 
		                                                     (int) distVector[2] + "] RIGHT[" + (int) distVector[3] + "]");
		System.out.println("Matrix multiply result: LEFT[" + (int) resultVector[0] + "]  RIGHT[" + (int) resultVector[1] + "]");
		System.out.println("Wheel Speed: LEFT[" + speedLeft + "]  RIGHT[" + speedRight + "]");
	}
	
	private void doFollowBallBang(){		
		if (getSumRightValue() > getSumLeftValue() && getSumRightValue() > MAX_SENSOR_VALUE) {
			driveRight();
		} else if (getSumLeftValue() > getSumRightValue() && getSumLeftValue() > MAX_SENSOR_VALUE) {
			driveLeft();
		} else {
			driveForward();
		}
		
		// debug console output
		System.out.println("Distance Values: LEFTFRONT[" + (int) getDistanceSensorValue(Sensor.DS_LEFTFRONT) + 
				"] FRONTLEFT[" + (int) getDistanceSensorValue(Sensor.DS_FRONTLEFT) + 
				"] FRONTRIGHT[" + (int) getDistanceSensorValue(Sensor.DS_FRONTRIGHT) +
				"] RIGHTFRONT[" + (int) getDistanceSensorValue(Sensor.DS_RIGHTFRONT) +"]");
		System.out.println("Sum Values: LEFT[" + (int) getSumLeftValue() + "] RIGHT[" + (int) getSumRightValue() + "]"); 
		System.out.println("Current Speed: LEFT[" +  getLeftSpeed() + "] RIGHT[" + getRightSpeed() + "]"); 
	}
	
	private double getSumLeftValue() {
		double sum = (getDistanceSensorValue(Sensor.DS_LEFT) + getDistanceSensorValue(Sensor.DS_LEFTFRONT) + getDistanceSensorValue(Sensor.DS_FRONTLEFT)); 
		return sum;
	}

	private double getSumRightValue() {
		double sum = (getDistanceSensorValue(Sensor.DS_RIGHT) + getDistanceSensorValue(Sensor.DS_RIGHTFRONT) + getDistanceSensorValue(Sensor.DS_FRONTRIGHT));
		return sum;
	}
}
