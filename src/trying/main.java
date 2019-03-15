package trying;

import java.util.Map;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.mapping.NavigationModel;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.Delay;

public class main {
	static final int centimeter= 10; 
	static EV3 brick = (EV3) BrickFinder.getDefault();
	static Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 56).offset(-53.2);
	static Wheel rigthWheel = WheeledChassis.modelWheel(Motor.C, 56).offset(53.2);
	static int speed = 250;
	static int acceleration = 100;
	static int initialX = 0;
	static int initialY = 0;
	static int x;
	static int y;
	private static Waypoint nextWaypoint;
	private static boolean done = false;
	private static Waypoint cornerPlusxy= new Waypoint(1250,1250);
	private static Waypoint cornerNegxy= new Waypoint(-250,-250);
	
	public static void  act() {
Button.ESCAPE.addKeyListener(new lejos.hardware.KeyListener() {

	@Override
	public void keyPressed(Key k) {
		// TODO Auto-generated method stub
		done= true;
		
	}

	@Override
	public void keyReleased(Key k) {
		// TODO Auto-generated method stub
		
	}
			
			
		});

		Chassis chassis = new WheeledChassis(new Wheel[] { leftWheel, rigthWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearAcceleration(acceleration);
		pilot.setLinearSpeed(speed);
		pilot.setAngularAcceleration(acceleration);
		pilot.setAngularSpeed(speed);
		pilot.setMinRadius(30*centimeter);
		
		
		Navigator navi = new Navigator(pilot);
		/*navi.goTo(50*centimeter, 50* centimeter);
		while(!navi.pathCompleted()&&!done)
		{
		navi.followPath();
		}*/
		navi.addWaypoint(100*centimeter,100*centimeter);
		//navi.addWaypoint(100*centimeter,100*centimeter);
		navi.addWaypoint(0,100*centimeter);
		navi.setPoseProvider(new OdometryPoseProvider(pilot));
		navi.addWaypoint(0,0);
		navi.followPath();
		while(!navi.pathCompleted()&&!done&&
				navi.getPoseProvider().getPose().getX()<cornerPlusxy.getX()&&
				navi.getPoseProvider().getPose().getY()<cornerPlusxy.getY()&&
				navi.getPoseProvider().getPose().getY()>cornerNegxy.getY()&&
				navi.getPoseProvider().getPose().getX()>cornerNegxy.getX()
				)
           {
			
			
			/*if(navi.getPoseProvider().getPose().getX()>cornerPlusxy.getX()||
					navi.getPoseProvider().getPose().getY()>cornerPlusxy.getY()||
					navi.getPoseProvider().getPose().getY()<cornerNegxy.getY()||
					navi.getPoseProvider().getPose().getX()<cornerNegxy.getX())
			{
				done =true;
			}*/
			nextWaypoint = navi.getWaypoint();
			
			LCD.drawString("Moving to...", 0, 0);
			LCD.drawString("(" + (int)nextWaypoint.getX() +
			"," + (int)nextWaypoint.getY() + ")", 0, 1);
			LCD.drawString("(" + (int) navi.getPoseProvider().getPose().getX()+
					"," + (int)navi.getPoseProvider().getPose().getY() + ")", 0, 2);
			
			}
		navi.stop();
		while(true)
		{
			LCD.drawString("Moving to...", 0, 0);
			LCD.drawString("(" + (int)nextWaypoint.getX() +
			"," + (int)nextWaypoint.getY() + ")", 0, 1);
			LCD.drawString("(" + (int) navi.getPoseProvider().getPose().getX()+
					"," + (int)navi.getPoseProvider().getPose().getY() + ")", 0, 2);
		}
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		act();
	

	}

}
