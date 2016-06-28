package culebra.behaviorInterfaces;

import processing.core.PImage;
import processing.core.PVector;

/**
 * Force Behavior Interface - All objects that want to use the controllers must implement this interface
 * @author elQuixote
 *
 */
public interface ForceBehavior {
	/**
	 * Calculates a steering force towards a target as defined by Daniel Shiffmans implementation of Craig Reynolds steering force.
	 * @param targetVec the target to steer towards
	 */
	public abstract void seek(PVector targetVec);
	/**
	 * Calculates a steering force towards a target as defined by Daniel Shiffmans implementation of Craig Reynolds steering force
	 * @param targetVec the target to steer towards
	 * @param normalize option to normalize the desired parameter
	 */
	public abstract void seek(PVector targetVec, boolean normalize);
	/**
	 * Attracts a object towards a target. Differs from Seek 
	 * @param target target to attract towards
	 * @param threshold if target is within this threshold then attract towards it
	 * @param attractionValue value specifying attraction, this is the magnitude.
	 * @param maxAttraction maximum attraction value
	 */
	public abstract void attract(PVector target, float threshold, float attractionValue, float maxAttraction);
	/**
	 * Attracts a object based on color values of an image. Position is defined by projecting its future position to sample color data.
	 * @param searchProjectionDistance amount defining projection from current position using current speed * this value
	 * @param attractionValue value specifying attraction, this is the magnitude.
	 * @param maxAttraction maximum attraction value
	 * @param affectantValue image luminance value (0-1) used as threshold to enable attraction
	 * @param img the image sample
	 */
	public abstract void attractMap(float searchProjectionDistance, float attractionValue, float maxAttraction, float affectantValue, PImage img);
	/**
	 * Repels a object away from a target.
	 * @param target target to repel away from
	 * @param threshold if target is within this threshold then repel away from it
	 * @param repelValue value specifying repulsion, this is the magnitude.
	 * @param maxRepel maximum repulsion value
	 */
	public abstract void repel(PVector target, float threshold, float repellValue, float maxRepell);
	/**
	 * Repels a object based on color values of an image. Position is defined by projecting its future position to sample color data.
	 * @param searchProjectionDistance amount defining projection from current position using current speed * this value
	 * @param repelValue value specifying repulsion, this is the magnitude.
	 * @param maxRepel maximum repulsion value
	 * @param affectantValue image luminance value (0-1) used as threshold to enable attraction
	 * @param img the image sample
	 */
	public abstract void repelMap(float searchProjectionDistance, float repellValue, float maxRepell, float affectantValue, PImage img);
	/**
	 * Applies the force vector to the acceleration and adds it to the current speed.
	 * @param force vector to add to acceleration
	 */
	public abstract void applyForce(PVector force);
}
