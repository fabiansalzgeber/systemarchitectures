import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public abstract class RobotFunctions extends DifferentialWheels {
	private DistanceSensor[] sensors; // Array with all distance sensors
	private LightSensor[] lightsensors; // Array with all light sensors
	
	private static int MIN_SPEED = 0; 		// min. motor speed
	private static int MAX_SPEED = 1000; 	// max. motor speed
	
	public RobotFunctions(){
		super();
		
		sensors = new DistanceSensor[] { getDistanceSensor("ps0"),
				getDistanceSensor("ps1"), getDistanceSensor("ps2"),
				getDistanceSensor("ps3"), getDistanceSensor("ps4"),
				getDistanceSensor("ps5"), getDistanceSensor("ps6"),
				getDistanceSensor("ps7") };
		
		lightsensors = new LightSensor[] { getLightSensor("ls0"), 
                  getLightSensor("ls1"), getLightSensor("ls2"),
                  getLightSensor("ls3"), getLightSensor("ls4"),
                  getLightSensor("ls5"), getLightSensor("ls6"),
                  getLightSensor("ls7") };
		
		for (int i=0; i<8; i++) { sensors[i].enable(10); }
		for (int i=0; i<8; i++) { lightsensors[i].enable(10); }
	}
	
	public RobotFunctions(int minSpeed, int maxSpeed){
		super();
		MIN_SPEED = minSpeed;
		MAX_SPEED = maxSpeed;
	}
	
	public void driveRight() {
		setSpeed(MAX_SPEED, MIN_SPEED);
	}

	public void driveLeft() {
		setSpeed(MIN_SPEED, MAX_SPEED);
	}
	
	public void driveLeftFast() {
		setSpeed(-500, 1000);
	}

	public void driveForward() {
		setSpeed(MAX_SPEED, MAX_SPEED);
	}
	
	public void driveBack() {
		setSpeed(MAX_SPEED * (-1), MAX_SPEED * (-1));
	}
	
	public void stopRobot() {
		setSpeed(MIN_SPEED, MIN_SPEED);
	}
	
	public void setWheelsSpeed(int leftWheel, int rightWheel){
		setSpeed(leftWheel, rightWheel);
	}
	
	public double getDistanceSensorValue(Sensor sensorName){
		return sensors[sensorName.toInt()].getValue();
	}
	
	public double getDistanceSensorValue(int index){
		return sensors[index].getValue();
	}
	
	public double getLightSensorValue(Sensor sensorName){
		return lightsensors[sensorName.toInt()].getValue();
	}
	
	public double getLightSensorValue(int index){
		return lightsensors[index].getValue();
	}
}
