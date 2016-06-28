package culebra.behaviorInterfaces;

import processing.core.PVector;

import java.util.*;

import culebra.viz.*;
/**
 * Tracking Behavior Interface - All objects or controllers that want to use Tracking behavior must implement this interface
 * @author elQuixote
 *
 */
public interface TrackingBehavior {
	/**
	 * Checks if the separation property is active, this can be used to parent of children objects to enable separation behavior without affecting the children behavior. So 
	 * parents will separate from children objects
	 * @return if it is or not
	 */
	public abstract boolean isSeparateActive();
	/**
	 * Gets the list of stored children to spawn next
	 * @return the start positions 
	 */
	public abstract ArrayList<PVector> getChildStartPositions();
	/**
	 * Gets the list of values used to alter types of children
	 * @return the child spawn type
	 */
	public abstract ArrayList getChildSpawnType();
	/**
	 * Resets the childstartPositions list
	 */
	public abstract void resetChildStartPositions();
	/**
	 * Resets the childspawnType list
	 */
	public abstract void resetChildSpawnType();
	/**
	 * Set the Seperate active value
	 */
	public abstract void setSeparateInactive();
	/**
	 * Single Shape Following Algorithm - Requires list of PVectors defining a single "path"
	 * @param shapePtList list of PVectors defining a single shape
	 * @param maxDist distance threshold enabling agents to see shape
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param shapeRad the radius of the shape
	 * @param speed the object speed
	 * @param loc the object location
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector shapeFollow(ArrayList<PVector> shapeList, float maxDist, float amp, float shapeRad, PVector speed,
			PVector loc, Boolean D3);
	/**
	 * Multi Shape Following Algorithm - Requires list of Arraylist of PVectors defining a each shapes points. - see example files
	 * @param multiShapeList list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold distance threshold enabling agents to see shapes
	 * @param projectionDistance Reynolds "point ahead on the path" to seek
	 * @param shapeRadius the radius of the shapes
	 */
	/**
	 * Multi Shape Following Algorithm - Requires list of Arraylist of PVectors defining a each shapes points. - see example files
	 * @param multiShapeList list of Arraylists of PVectors defining each shapes points
	 * @param maxDist distance threshold enabling agents to see shapes
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param shapeRadius the radius of the shapes
	 * @param speed the object speed
	 * @param loc the object location
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector multiShapeFollow(List<ArrayList<PVector>> multiShapeList, float maxDist, float amp, float shapeRadius, PVector speed,
			PVector loc, Boolean D3);
	/**
	 * MultiShape Following Algorithm capable of spawning children - see example files
	 * @param multiShapeList list of Arraylists of PVectors defining each shapes points
	 * @param maxDist distance threshold enabling agents to see shapes
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param shapeRadius the radius of the shapes
	 * @param speed the object speed
	 * @param loc the object location
	 * @param triggerBabies if true agent is now allowed to spawn any babies stored
	 * @param maxBabyCount the max number of children each agent can create
	 * @param canInstance if the child is instanceable it can reproduce. Only objects which inherit from the culebra.objects.Object class are instanceable. Child objects cannot produce more children
	 * @param childList list of stored children to spawn next. use (current object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList list of values used to alter types of children. use (current object).behaviors.getChildSpawnType() to get it.
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector multiShapeFollowBabyMaker(List<ArrayList<PVector>> multiShapeList, float maxDist, float amp, float shapeRadius, PVector speed,
			PVector loc, boolean triggerBabies, int maxBabyCount, boolean canInstance, ArrayList<PVector> childList, ArrayList childTypeList, Boolean D3);
	/**
	 * Path Following Algorithm - Requires Path class defined by Daniel Shiffman - see example files
	 * @param pathList list of Path Objects
	 * @param maxDist distance threshold enabling agents to see paths
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param pathRad the radius of the path
	 * @param speed the object speed
	 * @param loc the object location
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector pathFollow(ArrayList<Path> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, Boolean D3);
	/**
	 * Path Following Algorithm capable of spawning children - Requires Path class defined by Daniel Shiffman - see example files
	 * @param pathList list of Path Objects
	 * @param maxDist distance threshold enabling agents to see paths
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param pathRad the radius of the path
	 * @param speed the object speed
	 * @param loc the object location
	 * @param triggerBabies if true agent is now allowed to spawn any babies stored
	 * @param maxBabyCount the max number of children each agent can create
	 * @param canInstance if the child is instanceable it can reproduce. Only objects which inherit from the culebra.objects.Object class are instanceable. Child objects cannot produce more children
	 * @param childList list of stored children to spawn next. use (current object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList list of values used to alter types of children. use (current object).behaviors.getChildSpawnType() to get it.
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector pathFollowBaby(ArrayList<Path> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, boolean triggerBabies, int maxBabyCount, boolean canInstance, ArrayList<PVector> childList, ArrayList childTypeList, Boolean D3);
	/**
	 * Other Object Trails Following Algorithm - Meant for Seeker or sub Seeker types of objects. These objects will chase the trails of other objects - see example files
	 * @param pathList list of Path Objects
	 * @param maxDist distance threshold enabling agents to see paths
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param pathRad the radius of the trails
	 * @param speed the object speed
	 * @param loc the object location
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector trailFollow(List<ArrayList> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, Boolean D3);
	/**
	 * Other Object Trails Following Algorithm capable of spawning children - Meant for Seeker or sub Seeker types of objects. These objects will chase the trails of other objects
	 * @param pathList list of Path Objects
	 * @param maxDist distance threshold enabling agents to see paths
	 * @param amp Reynolds "point ahead on the path" to seek
	 * @param pathRad the radius of the path
	 * @param speed the object speed
	 * @param loc the object location
	 * @param triggerBabies if true agent is now allowed to spawn any babies stored
	 * @param maxBabyCount the max number of children each agent can create
	 * @param canInstance if the child is instanceable it can reproduce. Only objects which inherit from the culebra.objects.Object class are instanceable. Child objects cannot produce more children
	 * @param childList list of stored children to spawn next. use (current object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList list of values used to alter types of children. use (current object).behaviors.getChildSpawnType() to get it.
	 * @param D3 if we are in 3D or not
	 * @return new PVector to seek
	 */
	public abstract PVector trailFollowBabyMaker(List<ArrayList> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, boolean triggerBabies, int maxBabyCount, boolean canInstance, ArrayList<PVector> childList, ArrayList childTypeList, Boolean D3);
}
