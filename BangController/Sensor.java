
public enum Sensor {
	// distance sensors
	DS_LEFT(5),
	DS_LEFTFRONT(6),
	DS_FRONTLEFT(7),
	DS_RIGHT(2),
	DS_RIGHTFRONT(1),
	DS_FRONTRIGHT(0),
	DS_BACKLEFT(4),
	DS_BACKRIGHT(3),
	
	// light sensors
	LS_LEFT(5),
	LS_LEFTFRONT(6),
	LS_FRONTLEFT(7),
	LS_RIGHT(2),
	LS_RIGHTFRONT(1),
	LS_FRONTRIGHT(0),
	LS(4),
	LS_BACKRIGHT(3),
	
	// ground sensors
	GS_LEFT(0),
	GS_CENTER(1),
	GS_RIGHT(2);
	

	private final int _sensorIndex;
	
	Sensor(int name){
		_sensorIndex = name;
	}
	
	public int toInt(){
		return _sensorIndex;
	}
}
