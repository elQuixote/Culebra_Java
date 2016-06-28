package culebra.objects;

import processing.core.PVector;
import culebra.behaviors.Controller;
import culebra.viz.Viz;
import processing.core.*;
import java.util.ArrayList;
/**
 * Abstract class of Object from which other objects can inherit from. This class defines the basic abstract methods required by any object attempting to interface with the system.
 * @author elQuixote
 *
 */
public abstract class Object{
	
	public Controller behavior = new Controller();
	public Viz viz;
	private PApplet p;
	/**
	 * Constructor
	 * @param parent the PApplet source
	 */
	public Object(PApplet parent){
		this.p = parent;
		this.viz = new Viz(this.p);
	}
	/**
	 * Sets the move attributes for each object. This can be set up during the creation of the object in the sketch setup() method or it can be modified per object in the draw() method.
	 * @param maxspeed the maximum speed each object can have
	 * @param maxforce the maximum force each object can have
	 * @param velocityMultiplier a velocity multiplier to increase the speed
	 */
	public abstract void setMoveAttributes(float maxspeed, float maxforce, float velocityMultiplier);
	/**
	 * Overloaded move method for moving the object. This method allows for a minimum amount of steps to be taken before adding and storing a trail point. You can also specify the max amount of trail points 
	 * stored. This will certainly help with performance over time. This method is also the best to use with the behaviors.selfTrailChasing methods
	 * @param minStepAmount minimum amount of steps that must be taken before we store a trail PVector
	 * @param maxPositions_Stored maximum amount of allowable trail PVectors per object.
	 */
	public abstract void move(int minStepAmount, int maxPositions_Stored);
	/**
	 * Sets the behavior attributes for each object.Once the object instance is initialized we pass the initial values for 
	 * speed, location, instanceability, objtype, and dimension
	 */
	protected abstract void setBehaviorAttributes();
	/**
	 * Gets the superclass of the object
	 * @return the superclass as string
	 */
	public abstract String getSuperClass();
	/**
	 * Getter method for retrieving object location
	 * @return the objects location puller from the controller
	 */
	public PVector getLocation(){
		return behavior.getLoc();
	}
	/**
	 * Setter for objects location
	 * @param newLocation the desired new location
	 */
	public void setLocation(PVector newLocation){
		behavior.setLoc(newLocation);
	}
	/**
	 * Getter for retrieving the object speed
	 * @return the objects speed puller from the controller
	 */
	public PVector getSpeed(){
		return behavior.getSpeed();
	}
	/**
	 * Setter for objects speed
	 * @param newSpeed the desired new speed
	 */
	public void setSpeed(PVector newSpeed){
		behavior.setSpeed(newSpeed);
	}
	/**
	 * Reverses the current objects speed
	 */
	public void reverseSpeed(){
		behavior.getSpeed().mult(-1);
	}
	/**
	 * Retrieves the objects trail PVectors
	 * @return the list of trail PVectors
	 */
	public ArrayList<PVector> getTrailPoints(){
		return behavior.getCreeperTrails();
	}
	/**
	 * Getter for retrieving the objects behavior type
	 * @return the objects behavior type
	 */
	public String getBehaviorType(){
		return behavior.getBehaviorType();
	}
	/**
	 * Getter for retrieving the objectType uses the getClass().getName() method
	 * @return the object type specified from getClass().getName() method
	 */
	public String getObjectType(){
		return behavior.getObjType();
	}
}
