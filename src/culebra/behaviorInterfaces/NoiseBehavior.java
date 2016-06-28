package culebra.behaviorInterfaces;

import processing.core.PImage;
import processing.core.PVector;
/**
 * Noise Behavior Interface - All objects or controllers that want to use Noise behavior must implement this interface
 * @author elQuixote
 *
 */
public interface NoiseBehavior {
	/**
	 * 2D Improved Perlin Noise with image color sampling override for any behavior attribute
	 * @param scale overall scale of the noise
	 * @param strength overall strength of the noise
	 * @param multiplier value adds a jitter type of movement
	 * @param velocity multiplier value for velocity
	 * @param location the object location
	 * @param img the image sample
	 * @param mapScale uses image color data as multiplier for scale value
	 * @param mapStrength uses image color data as multiplier for strength value
	 * @param mapMultiplier uses image color data as multiplier for multiplier value
	 * @return the noise value
	 */
	public PVector perlin2D(float scale, float strength, float multiplier, float velocity, PVector location, PImage img, Boolean mapScale, Boolean mapStrength, Boolean mapMultiplier);
	/**
	 * 2D/3D Improved Perlin Noise
	 * @param scale overall scale of the noise
	 * @param strength overall strength of the noise
	 * @param multiplier value adds a jitter type of movement
	 * @param velocity multiplier value for velocity
	 * @param location the object location
	 * @param D3 if we are in 3D or not
	 * @return the noise value
	 */
	public PVector perlin(float scale, float strength, float multiplier, float velocity, PVector location, Boolean D3);
}
