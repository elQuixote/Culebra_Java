package culebra.behaviorInterfaces;

import processing.core.PVector;
/**
 * Wandering Behavior Interface - All objects or controllers that want to use Wandering behavior must implement this interface
 * @author elQuixote
 *
 */
public interface WanderBehavior {
	/**
	 * 2D Wandering Algorithm - "Agent predicts its future location as a fixed distance in front of it (in the direction of its velocity), draws a circle with radius r at that location, 
	 * and picks a random point along the circumference of the circle. That random point moves randomly around the circle in each frame of animation. And that random point is the 
	 * vehicles target, its desired vector pointing in that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * @param change the incremented change value used to get the polar coordinates.
	 * @param wanderR the radius for the circle
	 * @param wanderD the distance for the wander circle, this is a projection value in the direction of the objects speed vector. 
	 * @param location the objects location
	 * @param speed the objects speed
	 * @param randomize if true then the change value will be randomly selected from -change value to change value each frame
	 * @param addHeading if true adds the heading to the wandertheta
	 * @return the PVector to seek
	 */
	public abstract PVector wander(float change, float wanderR, float wanderD, PVector location,
			PVector speed, boolean randomize, boolean addHeading);
	/**
	 * 3D Wandering Algorithm - Type "MOD" uses no Z value
	 * These variations are best used with tracking behaviors.
	 * Wandering Algorithm - "Agent predicts its future location as a fixed distance in front of it (in the direction of its velocity), draws a circle with radius r at that location, 
	 * and picks a random point along the circumference of the circle. That random point moves randomly around the circle in each frame of animation. And that random point is the 
	 * vehicles target, its desired vector pointing in that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * @param change the incremented change value used to get the polar coordinates.
	 * @param wanderR the radius for the circle
	 * @param wanderD the distance for the wander circle, this is a projection value in the direction of the objects speed vector.
	 * @param location the objects location
	 * @param speed the objects speed
	 * @param type the Wander type, must be either "mod_B" or "mod_C" or "mod_D"
	 * @param randomize if true then the change value will be randomly selected from -change value to change value each frame
	 * @return the PVector to seek
	 */
	public abstract PVector wander3D(float change, float wanderR, float wanderD, PVector location,
			PVector speed, String type, boolean randomize);
	/**
	 * Expanded 2D/3D Wandering Algorithm - Type "Primary" using triggers to create a "weaving" type movement.
	 * Wandering Algorithm - "Agent predicts its future location as a fixed distance in front of it (in the direction of its velocity), draws a circle with radius r at that location, 
	 * and picks a random point along the circumference of the circle. That random point moves randomly around the circle in each frame of animation. And that random point is the 
	 * vehicles target, its desired vector pointing in that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * @param rotationTrigger this value is compared against each movement step. If rotationTrigger value > iteration count then we will reverse the change value.
	 * @param change NON incremented change value used to get the polar coordinates. As opposed to other wander examples this one does not increment the theta value, we simply use whichever value 
	 * is given and use the trigger to specify which direction the rotation will occur.
	 * @param wanderR the radius for the circle
	 * @param wanderD the distance for the wander circle, this is a projection value in the direction of the objects speed vector.
	 * @param type the Wander type, must be either "Primary" or "sub_A" or "sub_B"
	 * @param objectType the object type, must be from getClass().getName(), this will change later on but for now this will work
	 * @param superClass the object superclass, must be from obj.getClass().getAnnotatedSuperclass().getType().getTypeName().toString(), this will change later on but for now this will work
	 * @param babyType the baby type, must be either "a" or "b".
	 * @param location the object location
	 * @param speed the object speed
	 * @param D3 if it is in 2D or 3D, true for 3D
	 * @return the PVector to seek
	 */
	public abstract PVector wanderExpanded(float rotationTrigger, float change, float wanderR, float wanderD, String type,
			String objectType, String superClass, String babyType, PVector location, PVector speed, boolean D3);
	/**
	 * Expanded 2D/3D Wandering Algorithm - Type "Primary" using triggers to create a "weaving" type movement. USE WITH NON CREEPER DERIVED OBJECTS, if you create your own object use this and pass the objtype below.
	 * Wandering Algorithm - "Agent predicts its future location as a fixed distance in front of it (in the direction of its velocity), draws a circle with radius r at that location, 
	 * and picks a random point along the circumference of the circle. That random point moves randomly around the circle in each frame of animation. And that random point is the 
	 * vehicles target, its desired vector pointing in that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * @param rotationTrigger this value is compared against each movement step. If rotationTrigger value > iteration count then we will reverse the change value.
	 * @param change NON incremented change value used to get the polar coordinates. As opposed to other wander examples this one does not increment the theta value, we simply use whichever value 
	 * is given and use the trigger to specify which direction the rotation will occur.
	 * @param wanderR the radius for the circle
	 * @param wanderD the distance for the wander circle, this is a projection value in the direction of the objects speed vector.
	 * @param type the Wander type, must be either "Primary" or "sub_A" or "sub_B"
	 * @param objectType to use with generic objects not derrived from culebra.objects.Creeper. Input "Parent" for parent objects and "Child" for child objects
	 * @param babyType the baby type, must be either "a" or "b".
	 * @param location the object location
	 * @param speed the object speed
	 * @param D3 if it is in 2D or 3D, true for 3D
	 * @return the PVector to seek
	 */
	public abstract PVector wanderExpandedGenerics(float wanderTVal, float change, float wanderR, float wanderD, String type,
			String objectType, String babyType, PVector location, PVector speed, boolean D3);
}
