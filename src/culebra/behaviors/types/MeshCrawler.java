package culebra.behaviors.types;

import toxi.geom.*;
import toxi.geom.mesh.*;
import java.util.ArrayList;
import culebra.geometry.Mesh;
import processing.core.PVector;
/**
 * Mesh Crawler Behavior Class 
 * @author elQuixote
 *
 */
public class MeshCrawler {
	
	private boolean sepActive = false;
	private boolean instanceable;
	private int instanceTriggerCount;
	private int maxChildren;
	
	private ArrayList<PVector>childSpawners;
	private ArrayList<Integer> childSpawnType;
	
	private PVector predict = new PVector();
	private PVector nextPosPrev = new PVector();
	private PVector newVec = new PVector();
	private PVector zero = new PVector();
	
	private Vec3D tvec = new Vec3D();
	private Vec3D cp = new Vec3D();
	private Vec3D delta = new Vec3D();
	/**
	 * Constructor
	 */
	public MeshCrawler(){
		this.instanceTriggerCount = 0;
		this.childSpawners = new ArrayList<PVector>();
		this.childSpawnType = new ArrayList<Integer>();
	}
	/**
	 * Walks along the mesh
	 * @param mesh the mesh object
	 * @param meshThreshold min distance current position needs to be from mesh in order to move to it.
	 * @param loc the object location
	 * @param speed the object speed
	 * @param amp projection value
	 * @param maxBabyCount the max number of children each agent can create  
	 * @param triggerBabies if true agent is now allowed to spawn any babies stored
	 * @param canInstance if the child is instanceable it can reproduce. Only objects which inherit from the culebra.objects.Object class are instanceable. Child objects cannot produce more children
	 * @param childList list of stored children to spawn next. use (current object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList list of values used to alter types of children. use (current object).behaviors.getChildSpawnType() to get it.
	 * @return the new vector
	 */
	public PVector meshWalk(Mesh mesh, float meshThreshold,PVector loc, PVector speed, float amp, int maxBabyCount, boolean triggerBabies,boolean canInstance,ArrayList<PVector> childList, ArrayList childTypeList) {
		
		this.childSpawners = childList;
		this.childSpawnType = childTypeList;
		this.instanceable = canInstance;
		this.maxChildren = maxBabyCount;
		
		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);

		this.nextPosPrev = PVector.add(loc, this.predict);
		this.tvec = new Vec3D(this.nextPosPrev.x, this.nextPosPrev.y, this.nextPosPrev.z);
		Vertex v = mesh.getMesh().getClosestVertexToPoint(this.tvec);
		this.cp = new Vec3D(v.x, v.y, v.z);

		this.delta = this.cp.sub(this.tvec);
		this.newVec = new PVector(this.delta.x, this.delta.y, this.delta.z);
		if (this.newVec.magSq() < meshThreshold) {
			this.sepActive = true;
			if (triggerBabies && this.instanceable && this.instanceTriggerCount < this.maxChildren) { 
				childSpawners.add(new PVector(loc.x, loc.y, loc.z));
				childSpawnType.add(this.instanceTriggerCount);
				this.instanceTriggerCount++;
			} 
			this.zero = new PVector(0, 0, 0);
			this.zero.mult(3);
			return zero;
		}
		this.newVec.normalize();
		this.newVec.mult(3);
		return this.newVec;
	}
	/**
	 * Gets the list of stored children to spawn next
	 * @return the start positions 
	 */
	public ArrayList<PVector> getChildStartPositions(){	
		ArrayList<PVector> fuckList = this.childSpawners;
		this.childSpawners = new ArrayList<PVector>();
		return fuckList;
	}
	/**
	 * Gets the list of values used to alter types of children
	 * @return the child spawn type
	 */
	public ArrayList<Integer> getChildSpawnType(){	
		ArrayList<Integer> returnedList = this.childSpawnType;
		this.childSpawnType = new ArrayList<Integer>();
		return returnedList;
	}
	/**
	 * Resets the ChildStartPositions list
	 */
	public void resetChildStartPositions(){
		this.childSpawners = new ArrayList<PVector>();
	}
	/**
	 * Resets the childSpawnType list
	 */
	public void resetChildSpawnType(){
		this.childSpawnType = new ArrayList<Integer>();
	}
	/**
	 * Checks if the separation property is active, this can be used to parent of children objects to enable separation behavior without affecting the children behavior. So 
	 * parents will separate from children objects
	 * @return if it is or not
	 */
	public boolean isSeparateActive(){
		return this.sepActive;
	}
	/**
	 * Sets the separate active value
	 */
	public void setSeparateInactive(){
		this.sepActive = false;
	}	
}
