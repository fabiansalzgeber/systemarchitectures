public class MainController {
	
	public static void main(String[] args) {
		
		/* ********************************************************************
		 * 
		 * FOLLOW WALL LEFT CONTROLLER
		 * World: fabian_World_with_wallobstacles_and_epuck
		 * Options: proportional
		 * 
		 */
		//ControlFollowWall  controller = new ControlFollowWall(false);				// Bang
		ControlFollowWall  controller = new ControlFollowWall(true);				// Proportional
		
		
		/* ********************************************************************
		 * 
		 * FOLLOW LIGHT CONTROLLER
		 * World: World_with_wall_light_and_epuck1_org
		 * Options: proportional, proximity stop
		 * 
		 */
		//ControlFollowLight controller = new ControlFollowLight(false, false); 	// Bang without proximity stop
		//ControlFollowLight controller = new ControlFollowLight(false, false); 	// Bang with proximity stop
		//ControlFollowLight controller = new ControlFollowLight(true, false);		// Proportional without proximity stop
		//ControlFollowLight controller = new ControlFollowLight(true, true);		// Proportional with proximity stop
		
		
		
		/* ********************************************************************
		 * 
		 * FOLLOW BALL CONTROLLER
		 * World: World_with_wall_ball_and_epuck
		 * Options: proportional
		 * 
		 */
		//ControlFollowBall controller = new ControlFollowBall(false);				// Bang
		//ControlFollowBall controller = new ControlFollowBall(true);				// Proportional
		
		
		
		controller.run();
	}
	
}
