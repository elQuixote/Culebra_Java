package culebra.behaviorInterfaces;

import java.util.ArrayList;
import processing.core.PVector;
/**
 * Attributes Interface - All objects that want to use the controllers must implement this interface
 * @author elQuixote
 *
 */
public interface Attributes {
	/**
	 * Getter method for retrieving the objects location
	 * @return the location
	 */
	public abstract PVector getLoc();
	/**
	 * Setter method for specifying a desired location
	 * @param loc desired location
	 */
	public abstract void setLoc(PVector loc);
	/**
	 * Getter method for retrieving the objects speed
	 * @return the speed
	 */
	public abstract PVector getSpeed();
	/**
	 * Setter method for specifying a desired speed
	 * @param speed the desired speed
	 */
	public abstract void setSpeed(PVector speed);
	/**
	 * Getter method for retrieving if the object is instanceable. This relates to baby making objects
	 * @return if it is or not
	 */
	public abstract boolean isInstanceable();
	/**
	 * Setter method for specifying if the object will be instanceable.
	 * @param instanceable the desired value
	 */
	public abstract void setInstanceable(boolean instanceable);
	/**
	 * Getter method for retrieving the object type. Retrieved from getClass().getName()
	 * @return the object type
	 */
	public abstract String getObjType();
	/**
	 * Setter method for specifying the object type, must be from getClass().getName()
	 * @param objType the desired object type
	 */
	public abstract void setObjType(String objType);
	/**
	 * Getter method for retrieving the object child type. As of now this can only be "a" or "b"
	 * @return the child object type
	 */
	public abstract String getObjChildType();
	/**
	 * Setter method for specifying the child object type. Again either "a" or "b"
	 * @param objType the desired child type
	 */
	public abstract void setObjChildType(String objType);
	/**
	 * Getter method for retrieving if the sketch is in 2D or 3D
	 * @return if true then its in 3D
	 */
	public abstract Boolean getD3();
	/**
	 * Setter method for specifying if the sketch will be in 3D
	 * @param d3 the desired dimension
	 */
	public abstract void setD3(Boolean d3);
	/**
	 * Getter method for getting the behavior type. This is currently specified as a string
	 * @return the behavior type
	 */ 
	public abstract String getBehaviorType();
	/**
	 * Setter method for specifying the behavior type as a string
	 * @param behaviorType the desired behavior type.
	 */
	public abstract void setBehaviorType(String behaviorType);
	/**
	 * Getter method for retrieving the Object trails as a list of PVectors
	 * @return the list of PVectors which make up the object trail
	 */
	public abstract ArrayList<PVector> getCreeperTrails();
	/**
	 * Setter method for specifying object trails
	 * @param creeperTrails the desired object trails
	 */
	public abstract void setCreeperTrails(ArrayList<PVector> creeperTrails);
	/**
	 * Getter method for retrieving the object acceleration
	 * @return the acceleration
	 */
	public abstract PVector getAcc();
	/**
	 * Setter method for specifying the object acceleration
	 * @param acc the desired acceleration
	 */
	public abstract void setAcc(PVector acc);
	/**
	 * Getter method for retrieving if we are currently creating flocking connections
	 * @return if we are or not
	 */
	public abstract Boolean getDrawConnections();
	/**
	 * Setter method for specifying if we are going to visualize the flocking behavior connectivity
	 * @param drawConnections if we are or not
	 */
	public abstract void setDrawConnections(Boolean drawConnections);
	/**
	 * Getter method for retrieving the max speed
	 * @return the max speed
	 */
	public abstract float getMaxSpeed();
	/**
	 * Setter method for specifying the max speed
	 * @param maxSpeed the desired max speed
	 */
	public abstract void setMaxSpeed(float maxSpeed);
	/**
	 * Getter method for retrieving the max force value
	 * @return the max force 
	 */
	public abstract float getMaxForce();
	/**
	 * Setter method for specifying the max force value
	 * @param maxForce the desired max force value
	 */
	public abstract void setMaxForce(float maxForce);
	/**
	 * Getter method for retrieving the velocity value
	 * @return the velocity value
	 */
	public abstract float getVelocity();
	/**
	 * Setter method for specifying the velocity
	 * @param velocity the desired velocity
	 */
	public abstract void setVelocity(float velocity);
}
