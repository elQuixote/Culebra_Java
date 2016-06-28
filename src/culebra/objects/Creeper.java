package culebra.objects;

import java.util.ArrayList;
import java.util.Random;
import culebra.data.Data;
import culebra.objects.Object;
import processing.core.*;
/**
 * Creeper Objects are the main implementation of the abstract Objects. They are able to implement any type of behavior and are meant as a do all type of object.
 * @author elQuixote
 *
 */
public class Creeper extends Object {

	private PVector loc, speed;
	private String objType, superClassName;
	private Random r;
	private int currentStep;
	private boolean instanceable, dimension;

	/**
	 * Constructor
	 * @param loc the location of the creeper object
	 * @param speed speed of the creeper object
	 * @param instanceable is the creeper instanceable
	 * @param In3D specifies if we are in 2D or 3D
	 * @param parent specifies the PApplet source
	 */
	public Creeper(PVector loc, PVector speed, Boolean instanceable, Boolean In3D, PApplet parent) {
		super(parent);
		this.loc = loc;
		this.speed = speed;
		this.instanceable = instanceable;
		this.objType = this.getClass().getName();
		this.superClassName = this.getClass().getAnnotatedSuperclass().getType().getTypeName().toString();
		this.dimension = In3D;
		setBehaviorAttributes();
	}
	@Override
	protected void setBehaviorAttributes() {
		this.behavior.setLoc(this.loc);
		this.behavior.setSpeed(this.speed);
		this.behavior.setInstanceable(instanceable);
		this.behavior.setObjType(this.objType);
		this.behavior.setD3(this.dimension);
		this.behavior.setSuperClass(this.superClassName);
	}
	public void move() {
		this.r = new Random();
		if (behavior.getBehaviorType() == "Perlin") {
			behavior.getLoc().add(behavior.getSpeed());
			behavior.getCreeperTrails().add(new PVector(behavior.getLoc().x, behavior.getLoc().y, behavior.getLoc().z));
			behavior.setAcc(new PVector());
		}
		if (behavior.getBehaviorType() == "Wander" || behavior.getBehaviorType() == "Tracker"
				|| behavior.getBehaviorType() == "Seeker") {
			behavior.getLoc().add(behavior.getSpeed());
			behavior.getCreeperTrails().add(new PVector(behavior.getLoc().x, behavior.getLoc().y, behavior.getLoc().z));
			behavior.setAcc(new PVector());
		} else {
			if (behavior.getDrawConnections() != null) {
				if (behavior.getDrawConnections()) {
					drawSearchNetwork();
				}
			}
			behavior.getLoc().add(behavior.getSpeed());
			behavior.getCreeperTrails().add(new PVector(behavior.getLoc().x, behavior.getLoc().y, behavior.getLoc().z));
			behavior.setAcc(new PVector());
		}
	}
	@Override
	public void move(int minStepAmount, int maxPositions_Stored) {
		this.r = new Random();
		if (behavior.getDrawConnections() != null) {
			if (behavior.getDrawConnections()) {
				drawSearchNetwork();
			}
		}
		behavior.getLoc().add(behavior.getSpeed());
	    this.currentStep++;
	    if (this.currentStep > minStepAmount) {
	    	behavior.getCreeperTrails().add(behavior.getLoc().copy());
	    	this.currentStep = 0;
	    }		
		if (this.behavior.getCreeperTrails().size() > maxPositions_Stored) {
			this.behavior.getCreeperTrails().remove(0);
		}
		behavior.setAcc(new PVector());
	}
	/**
	 * Method to specify if you would like to visualize the connectivity for any agents displaying flocking behavior. 
	 */
	public void drawSearchNetwork() {
		if (this.getBehaviorType() == "Flocker") {
			this.viz.setNetworkData(this.loc, this.behavior.getNetworkData());
		}
	}
	/**
	 * 2D Respawn method for when objects reach the sketch or any defined boundary
	 * @param width the max width the object can travel
	 * @param depth the max height the object can travel
	 */
	public void respawn(int width, int depth) {
		if (loc.x > width || this.loc.x < 0 || this.loc.y > depth || this.loc.y < 0) {
			float x = Data.getRandomNumbers(0.0, width, this.r);
			float y = Data.getRandomNumbers(0.0, depth, this.r);
			this.loc = new PVector(x, y, 0);
			this.behavior.setCreeperTrails(new ArrayList<PVector>());
			this.behavior.resetConnections();
			setBehaviorAttributes();
		}
	}
	/**
	 * 3D Respawn method for when object reached the sketch or any defined boundary
	 * @param width the max width the object can travel 
	 * @param depth the max depth the object can travel
	 * @param height the max height the object can travel
	 * @param spawnOnGround do you want all the new respawns to spawn on the ground?
	 * @param spawnRandomly do you want all the new respawns to spawn randomly in space?
	 */
	public void respawn(int width, int depth, int height, boolean spawnOnGround, boolean spawnRandomly) {
		if (loc.x > width || this.loc.x < 0 || this.loc.y > depth || this.loc.y < 0 || this.loc.z > height
				|| this.loc.z < 0) {
			if (spawnRandomly) {
				float x = Data.getRandomNumbers(0.0, width, this.r);
				float y = Data.getRandomNumbers(0.0, depth, this.r);
				float z = Data.getRandomNumbers(0.0, height, this.r);
				this.loc = new PVector(x, y, z);
			} else if (spawnOnGround) {
				float x = Data.getRandomNumbers(0.0, width, this.r);
				float y = Data.getRandomNumbers(0.0, depth, this.r);
				this.loc = new PVector(x, y, 0);
			}
			this.behavior.setCreeperTrails(new ArrayList<PVector>());
			this.behavior.resetConnections();
			setBehaviorAttributes();
		}
	}
	/**
	 * 2D bounce method for boundary
	 * @param width the width to bounce from
	 * @param depth the depth to bounce from
	 */
	public void bounce(int width, int depth) {
		if (loc.x > width) {
			speed.x = speed.x * -1;
		}
		if (loc.x < 0) {
			speed.x = speed.x * -1;
		}
		if (loc.y > depth) {
			speed.y = speed.y * -1;
		}
		if (loc.y < 0) {
			speed.y = speed.y * -1;
		}
	}
	/**
	 * 3D bounce method for boundary
	 * @param width the width to bounce from
	 * @param depth the depth to bounce from
	 * @param height the height to bounce from
	 * @see bounce(int width, int depth)
	 */
	public void bounce(int width, int depth, int height) {
		if (loc.x > width) {
			speed.x = speed.x * -1;
		}
		if (loc.x < 0) {
			speed.x = speed.x * -1;
		}
		if (loc.y > depth) {
			speed.y = speed.y * -1;
		}
		if (loc.y < 0) {
			speed.y = speed.y * -1;
		}
		if (loc.z > height) {
			speed.z = speed.z * -1;
		}
		if (loc.z < 0) {
			speed.z = speed.z * -1;
		}
	}
	@Override
	public void setMoveAttributes(float maxspeed, float maxforce, float velocityMultiplier) {
		behavior.setMaxSpeed(maxspeed);
		behavior.setMaxForce(maxforce);
		behavior.setVelocity(velocityMultiplier);
	}
	@Override
	public String getSuperClass() {
		return this.getClass().getAnnotatedSuperclass().getType().getTypeName().toString();
	}
}
