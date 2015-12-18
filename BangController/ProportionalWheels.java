
public class ProportionalWheels {
	private static int MAX_SPEED = 1000;
	
	public static int calcLeftWheelSpeed(int resultVectorLeft){
		int speedLeft = 0;
		
		if (resultVectorLeft > MAX_SPEED) {
			speedLeft = MAX_SPEED;
		} else {
			speedLeft = resultVectorLeft;
		}

		return speedLeft;
	}
	
	public static int calcRightWheelSpeed(int resultVectorRight){
		int speedRight = 0;
		
		if (resultVectorRight > MAX_SPEED) {
			speedRight = MAX_SPEED;
		} else {
			speedRight = resultVectorRight;
		}

		return speedRight;
	}
}
