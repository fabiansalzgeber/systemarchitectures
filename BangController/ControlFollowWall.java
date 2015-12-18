public class ControlFollowWall extends RobotFunctions {
	private static int TIME_STEP = 16;
	private static int MAX_SENSOR_VALUE = 200;
	
	private boolean _typeProportional;
	
	public ControlFollowWall(boolean typeProportional) {
		super();
		_typeProportional = typeProportional;
	}

	public void run() {
		while (step(TIME_STEP) != -1) {
			if (_typeProportional){
				doFollowWallProportional();
			} else {
				doFollowWallBang();
			}
		}
	}
	
	private void doFollowWallProportional(){
		// get distance vector values
		double distFront = (getDistanceSensorValue(Sensor.LS_FRONTLEFT) + getDistanceSensorValue(Sensor.LS_FRONTRIGHT)) / 2;
		double[] distVector = { getDistanceSensorValue(Sensor.LS_LEFT), distFront, getDistanceSensorValue(Sensor.LS_RIGHT)};
	
		double[][] controllerMatrix = { { 0.1 , 0.2, 0}, { 0, 0.1, 0.65} };
		
		double[] resultVector = Matrix.multiply(controllerMatrix, distVector);
		int resultVectorLeft = (int)Math.round(resultVector[0]);
		int resultVectorRight = (int)Math.round(resultVector[1]);
		
		// calc wheel speed
		int speedUpValue = 10;
		int speedLeft = ProportionalWheels.calcLeftWheelSpeed(resultVectorLeft) * speedUpValue;
		int speedRight = ProportionalWheels.calcRightWheelSpeed(resultVectorRight) * speedUpValue;
		//setWheelsSpeed(speedLeft * speedUpValue, speedRight * speedUpValue);
		setWheelsSpeed(1000 - speedRight, 1000 - speedLeft);

		// debug console output
		System.out.println("Distance sensor values: LEFT[" + (int) distVector[0] + "] FRONT[" + (int) distFront + "] RIGHT[" + (int) distVector[1] + "]");
		System.out.println("Matrix multiply result: LEFT[" + (int) resultVector[0] + "]  RIGHT[" + (int) resultVector[1] + "]");
		System.out.println("Wheel Speed: LEFT[" + speedLeft + "]  RIGHT[" + speedRight + "]");
	}
	
	private void doFollowWallBang(){		
		if (getDistanceSensorValue(Sensor.DS_FRONTLEFT) > MAX_SENSOR_VALUE) {         
			driveRight();
		} else if (getDistanceSensorValue(Sensor.DS_FRONTRIGHT) > MAX_SENSOR_VALUE) { 
			driveLeft();
		} else if (getDistanceSensorValue(Sensor.DS_LEFT) < MAX_SENSOR_VALUE) {  
			driveLeft();
		} else {
			driveForward();
		}
		
		// debug console output
		System.out.println("Distance Values: LEFT[" + (int) getDistanceSensorValue(Sensor.DS_LEFT) + 
				"] FRONTLEFT[" + (int) getDistanceSensorValue(Sensor.DS_FRONTLEFT) + 
				"] FRONTRIGHT[" + (int) getDistanceSensorValue(Sensor.DS_FRONTRIGHT) + "]");
	}
}
