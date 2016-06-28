package culebra.viz;

import java.util.ArrayList;
import processing.core.*;
/**
 * Path Class by Daniel Shiffman
 * @author Daniel Shiffman
 *
 */
public class Path extends PApplet {

	private ArrayList<PVector> points;
	private float radius = 20.0f;
	/**
	 * Constructor
	 */
	public Path() {
		points = new ArrayList<PVector>();
	}
	/**
	 * Adds a point to the path
	 * @param x x pos
	 * @param y y pos
	 * @param z z pos
	 */
	public void addPoint(float x, float y, float z) {
		PVector point = new PVector(x, y, z);
		points.add(point);
	}
	/**
	 * Displays the paths on the canvas
	 */
	public void display() {
		
		// Draw thick line for radius
		stroke(175, 0, 0, 50);
		strokeWeight(radius * 2);
		noFill();
		beginShape();
		for (PVector v : points) {
			vertex(v.x, v.y, v.z);
		}
		endShape();

		// Draw thin line for center of path
		stroke(255);
		strokeWeight(1);
		noFill();
		beginShape();
		for (PVector v : points) {
			vertex(v.x, v.y, v.z);
		}
		endShape();
	}
	/**
	 * Gets the path PVectors
	 * @return the list of path PVectors
	 */
	public ArrayList<PVector> getPathPoints(){
		return points;
	}
	/**
	 * Gets the path radius
	 * @return the radius
	 */
	public float getPathRadius(){
		return radius;
	}
	/**
	 * Sets the path radius
	 * @param newRadius the desired radius
	 */
	public void setPathRadius(float newRadius){
		this.radius = newRadius;
	}
}
