package culebra.viz;

import peasy.*;
import processing.core.PApplet;
/**
 * This class builds of Peasy Cam in order to cluster some methods and make them easier to use. 
 * @author PeasyCam
 *
 */
public class Cameras {

	private static PeasyCam cam;
	private static PApplet p;
	/**
	 * Constructor
	 * @param parent the PApplet source
	 */
	public Cameras(PApplet parent) {
		p = parent;
	}
	/**
	 * Sets a 3D camera with the specified settings
	 * @param distance initial distance
	 * @param minimumDistance min distance
	 * @param maximumDistance max distance
	 * @param camTarget the camera target
	 * @param suppressRoll suppress roll?
	 */
	public void set3DCamera(double distance, int minimumDistance, int maximumDistance, int[] camTarget,
			Boolean suppressRoll) {
		cam = new PeasyCam(this.p, distance);
		cam.setMinimumDistance(minimumDistance);
		cam.setMaximumDistance(maximumDistance);
		cam.lookAt(camTarget[0], camTarget[1], camTarget[2]);
		if (suppressRoll) {
			cam.setSuppressRollRotationMode();
		}
	}
	/**
	 * Sets a 2D camera with the specified settings
	 * @param value do we want a 2D camera?
	 */
	public void set2DCamera(Boolean value) {
		p.camera();
		if (cam != null) {
			cam.setActive(value);
		}
	}
	/**
	 * Sets the camera active
	 * @param value active or not?
	 */
	public void setActive(Boolean value) {
		cam.setActive(value);
	}
	/**
	 * Being the HUD
	 */
	public void beginHUD(){
		cam.beginHUD();
	}
	/**
	 * End the HUD
	 */
	public void endHUD(){
		cam.endHUD();
	}
}
