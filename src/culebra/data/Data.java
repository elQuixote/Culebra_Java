package culebra.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import culebra.objects.Creeper;
import culebra.viz.Octree;
import culebra.viz.QuadTree;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PVector;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
/**
 * Data Class - this class performs and creates data regularly used by the rest of the classes.
 * @author elQuixote
 *
 */
public final class Data {
	
	private static Octree octree;
	private static List<Vec3D> treeVecList;
	private static QuadTree qtree;
	private static List<Vec2D> qtreeVecList;
	/**
	 * Creates the toxiclibs octree for 3D particle like objects
	 * @param width the width of the octree
	 * @param p the PApplet source
	 * @return the octree
	 */
	public static Octree makeOctree(int width, PApplet p){
		octree = new Octree(new Vec3D(0, 0, 0), width, p);
		octree.setTreeAutoReduction(true);
		return octree;
	}
	/**
	 * Updates the Octree everyframe with the current creepers positions
	 * @param treeGuys all of the creepers current positions
	 * @param drawTree if enabled, you can visualize the octree
	 */
	public static void updateCreepersTree(List<Creeper> treeGuys, boolean drawTree){
		treeVecList = new ArrayList<Vec3D>();
		octree.empty();
		for (Creeper ag : treeGuys) {
			treeVecList.add(new Vec3D(ag.getLocation().x, ag.getLocation().y, ag.getLocation().z));
		}
		octree.addAll(treeVecList);
		if(drawTree){
			octree.draw();
		}
	}
	/**
	 * For using the parent culebra.objects.Object. Updates the Octree everyframe with the current culebra.objects.Object positions
	 * @param treeGuys all of the culebra.objects.Object current positions
	 * @param drawTree if enabled, you can visualize the octree
	 */
	public static void updateTree(List<culebra.objects.Object> treeGuys, boolean drawTree){
		treeVecList = new ArrayList<Vec3D>();
		octree.empty();
		for (culebra.objects.Object ag : treeGuys) {
			treeVecList.add(new Vec3D(ag.getLocation().x, ag.getLocation().y, ag.getLocation().z));
		}
		octree.addAll(treeVecList);
		if(drawTree){
			octree.draw();
		}
	}
	/**
	 * Gets the Octree nodes
	 * @return the 3D points
	 */
	public static List<Vec3D> getTreeNodes(){
		return treeVecList;
	}
	/**
	 * Gets the Octree object
	 * @return the octree
	 */
	public static Octree getOctree(){
		return octree;
	}
	/**
	 * Creates the toxiclibs quadtree object for 2D particle like objects
	 * @param width the width of the quadtree
	 * @param p the PApplet source 
	 * @return the quadtree
	 */
	public static QuadTree makeQuadtree(int width, PApplet p){
		qtree = new QuadTree(new Vec2D(0, 0), width, p);
		qtree.setTreeAutoReduction(true);
		return qtree;
	}
	/**
	 * Updates the quadtree every frame with creepers current position
	 * @param treeGuys the creepers positions
	 * @param drawTree if enabled the quadtree can be visualized
	 */
	public static void updateCreepersQTree(List<Creeper> treeGuys, boolean drawTree){
		qtreeVecList = new ArrayList<Vec2D>();
		qtree.empty();
		for (Creeper ag : treeGuys) {
			qtreeVecList.add(new Vec2D(ag.getLocation().x, ag.getLocation().y));
		}
		qtree.addAll(qtreeVecList);
		if(drawTree){
			qtree.draw();
		}
	}
	/**
	 * For using the parent culebra.objects.Object. Updates the quadtree every frame with culebra.objects.Object current position
	 * @param treeGuys the culebra.objects.Object positions
	 * @param drawTree if enabled the quadtree can be visualized
	 */
	public static void updateQTree(List<culebra.objects.Object> treeGuys, boolean drawTree){
		qtreeVecList = new ArrayList<Vec2D>();
		qtree.empty();
		for (culebra.objects.Object ag : treeGuys) {
			qtreeVecList.add(new Vec2D(ag.getLocation().x, ag.getLocation().y));
		}
		qtree.addAll(qtreeVecList);
		if(drawTree){
			qtree.draw();
		}
	}
	/**
	 * Get the quadtree nodes
	 * @return the 2D points
	 */
	public static List<Vec2D> getQTNodes(){
		return qtreeVecList;
	}
	/**
	 * Gets the quadtree object
	 * @return the quadtree
	 */
	public static QuadTree getQuadtree(){
		return qtree;
	}
	/**
	 * Samples an image and retrieves the luminance value at a current point
	 * @param location the position to sample
	 * @param image the image to sample from
	 * @return the luminance value (0-1)
	 */
	public static final float getColorValue(PVector location, PImage image){
		double f = Math.floor(location.y) * image.width + Math.floor(location.x);
		Integer i = (int) f;
		Integer col = image.pixels[i];
		float red = col >> 16 & 0xFF; // Very fast to calculate
		float green = col >> 8 & 0xFF; // Very fast to calculate
		float blue = col & 0xFF; // Very fast to calculate
		int greyscale = (int) Math.round(red * 0.222 + green * 0.707 + blue * 0.071);
		return map(greyscale, 0, 255, 0, 1);
	}
	/**
	 * Samples an image and retrieves the luminance value at a current point
	 * @param location the position to sample
	 * @param image the image to sample from
	 * @return the luminance value (0-1)
	 */
	public static final float getSpawnColorValue(PVector location, PImage image) {
		double f = Math.floor(location.y) * image.width + Math.floor(location.x);
		Integer i = (int) f;
		Integer col = image.pixels[i];
		float red = col >> 16 & 0xFF; // Very fast to calculate
		float green = col >> 8 & 0xFF; // Very fast to calculate
		float blue = col & 0xFF; // Very fast to calculate
		int greyscale = (int) Math.round(red * 0.222 + green * 0.707 + blue * 0.071);
		return Data.map(greyscale, 0, 255, 0, 1);
	}
	/**
	 * Creates a random vector from a min and max value
	 * @param minimum the minimum value
	 * @param maximum the maximum value
	 * @param random an instance of the random class object
	 * @param D3 if true then a 3D vector will be created instead of 2D
	 * @return
	 */
	public static final PVector getRandomVector(double minimum, double maximum, Random random, Boolean D3) {
		PVector rando = new PVector();
		double rX = random.nextDouble() * (maximum - minimum) + minimum;
		double rY = random.nextDouble() * (maximum - minimum) + minimum;
		double rZ = random.nextDouble() * (maximum - minimum) + minimum;

		float fX = (float) rX;
		float fY = (float) rY;
		float fZ = (float) rZ;

		rando.x = fX;
		rando.y = fY;
		if (D3) {
			rando.z = fZ;
		} else {
			rando.z = 0.0f;
		}
		return rando;
	}
	/**
	 * Remaps value from a source value to a target value
	 * @param value the value to remap
	 * @param istart source min
	 * @param istop source max
	 * @param ostart target min
	 * @param ostop target max
	 * @return the remapped value
	 */
	public static final float map(float value, float istart, float istop, float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}
	/**
	 * Generates random number float value from a min and a max value
	 * @param minimum the min value
	 * @param maximum the max value
	 * @param random an instance of the random class object
	 * @return the random float value
	 */
	public final static float getRandomNumbers(double minimum, double maximum, Random random) {
		double rX = random.nextDouble() * (maximum - minimum) + minimum;
		float fX = (float) rX;
		return fX;
	}
	/**
	 * Gets the normal point by using scalar projection
	 * @param location predicted next location
	 * @param segmentStartPt segment start point
	 * @param segmentEndPt segment end point
	 * @return the normal point
	 */
	public final static PVector getNormalPoint(PVector location, PVector segmentStartPt, PVector segmentEndPt) {
		PVector ap = PVector.sub(location, segmentStartPt);
		PVector ab = PVector.sub(segmentEndPt, segmentStartPt);
		ab.normalize();
		ab.mult(PVector.dot(ap, ab)); 
		PVector normalPoint = PVector.add(segmentStartPt, ab);
		return normalPoint;
	}
	public static PVector getDirection(List<ArrayList<PVector>> pathList, List<PVector> pathPts, PVector speed, PVector location, int targetInitialPath, float maxSpeed){
		
		PVector predict = speed.copy();
		predict.normalize();
		PVector nextPosPrev = PVector.add(location, predict);
		PVector target = null;
		PVector normal = null;
		float worldRecord = 1000000;
		
		for (int i = 0; i < pathPts.size() - 1; i++) {
			PVector a = pathList.get(targetInitialPath).get(i);
			PVector b = pathList.get(targetInitialPath).get(i + 1);
			PVector normalPoint = Data.getNormalPoint(nextPosPrev, a, b);
			
			if ((normalPoint.x < Math.min(a.x, b.x) || normalPoint.x > Math.max(a.x, b.x))
					|| (normalPoint.y < Math.min(a.y, b.y) || normalPoint.y > Math.max(a.y, b.y))
					|| (normalPoint.z < Math.min(a.z, b.z) || normalPoint.z > Math.max(a.z, b.z))) {
				normalPoint = b.copy();
			}
			float distance = nextPosPrev.dist(normalPoint);
			if (distance < worldRecord) {
				worldRecord = distance;
				normal = normalPoint;
				PVector dir = PVector.sub(b, a);
				dir.normalize();
				dir.mult(10);
				target = normalPoint.copy();
				target.add(dir);
			}
		}
		PVector desired = target.sub(location);
		desired.normalize();
		desired.mult(maxSpeed);

		PVector steer = desired.sub(speed);
		steer.normalize();
		return desired;
	}
}
