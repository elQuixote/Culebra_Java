package culebra.behaviors.types;

import java.util.ArrayList;
import java.util.List;

import culebra.behaviorInterfaces.TrackingBehavior;
import culebra.data.Data;
import culebra.viz.Path;
import processing.core.PVector;
/**
 * Tracker Behavior Type Class Implements Tracking Behavior Interface
 * @author elQuixote
 *
 */
public class Tracker extends Wanderer implements TrackingBehavior {

	private boolean sepActive = false;
	private boolean instanceable;
	
	private int instanceTriggerCount;
	private int maxChildren;
	
	private ArrayList<PVector>childSpawners;
	private ArrayList<Integer> childSpawnType;
	
	private PVector normalPoint = new PVector();
	private PVector predict = new PVector();
	private PVector nextPosPrev = new PVector();
	private PVector normalPt = new PVector();
	private PVector a = new PVector();
	private PVector b = new PVector();
	private PVector dir = new PVector();
	private PVector returnVec = new PVector();
	private PVector zero = new PVector();
	private PVector target;
	
	private List<PVector> pathPts;

	public Tracker(){
		this.instanceTriggerCount = 0;
		this.childSpawners = new ArrayList<PVector>();
		this.childSpawnType = new ArrayList<Integer>();
	}
	public PVector shapeFollow(ArrayList<PVector> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, Boolean D3) {

		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);

		this.target = null;
		float worldRecord = 1000000;
			for (int i = 0; i < pathList.size() - 1; i++) {
				this.a = pathList.get(i);
				this.b = pathList.get(i + 1);
				//this.normalPt = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (!D3) {
					if (this.normalPt.x < Math.min(a.x, b.x) || this.normalPt.x > Math.max(a.x, b.x)) {
						this.normalPt = b.copy();
					}
				} else {
					if ((this.normalPt.x < Math.min(a.x, b.x) || this.normalPt.x > Math.max(a.x, b.x))
							|| (this.normalPt.y < Math.min(a.y, b.y) || this.normalPt.y > Math.max(a.y, b.y))
							|| (this.normalPt.z < Math.min(a.z, b.z) || this.normalPt.z > Math.max(a.z, b.z))) {
						this.normalPt = b.copy();
					}
				}
				float distance = this.nextPosPrev.dist(this.normalPt);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(b, a);
					this.dir.normalize();
					this.dir.mult(10);
					this.target = this.normalPt.copy();
					this.target.add(this.dir);
				}
			}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				this.returnVec = this.target;
				return this.target;
			} else {
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = this.zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public static PVector follow(List<ArrayList<PVector>> pathList, List<PVector> pathPts, PVector speed, PVector location, int targetInitialPath, float maxSpeed){
		
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
	public PVector multiShapeFollow(List<ArrayList<PVector>> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, Boolean D3) {

		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);

		this.target = null;
		float worldRecord = 1000000;
		for (int z = 0; z < pathList.size(); z++) {
			List<PVector> pathPts = pathList.get(z);
			for (int i = 0; i < pathPts.size() - 1; i++) {
				this.a = pathList.get(z).get(i);
				this.b = pathList.get(z).get(i + 1);
				//this.normalPoint = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (!D3) {
					if (this.normalPoint.x < Math.min(a.x, b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x)) {
						this.normalPoint = this.b.copy();
					}
				} else {
					if ((this.normalPoint.x < Math.min(a.x, b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x))
							|| (this.normalPoint.y < Math.min(this.a.y, this.b.y) || normalPoint.y > Math.max(this.a.y, this.b.y))
							|| (this.normalPoint.z < Math.min(this.a.z, this.b.z) || normalPoint.z > Math.max(this.a.z, this.b.z))) {
						this.normalPoint = this.b.copy();
					}
				}
				float distance = nextPosPrev.dist(this.normalPoint);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(b, a);
					this.dir.normalize();
					this.dir.mult(10);
					this.target = normalPoint.copy();
					this.target.add(this.dir);
				}
			}
		}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				returnVec = target;
				return target;
			} else {
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = this.zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public PVector multiShapeFollowBabyMaker(List<ArrayList<PVector>> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, boolean triggerSeekers, int babyCount, boolean canInstance, ArrayList<PVector> childList, ArrayList childTypeList, Boolean D3) {
		
		this.childSpawners = childList;
		this.childSpawnType = childTypeList;
		this.instanceable = canInstance;
		this.maxChildren = babyCount;
		
		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);

		this.target = null;
		float worldRecord = 1000000;
		for (int z = 0; z < pathList.size(); z++) {
			this.pathPts = pathList.get(z);
			for (int i = 0; i < this.pathPts.size() - 1; i++) {
				this.a = pathList.get(z).get(i);
				this.b = pathList.get(z).get(i + 1);
				//this.normalPoint = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (!D3) {
					if (this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x)) {
						this.normalPoint = this.b.copy();
					}
				} else {
					if ((this.normalPoint.x < Math.min(a.x, b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x))
							|| (this.normalPoint.y < Math.min(a.y, b.y) || this.normalPoint.y > Math.max(this.a.y, this.b.y))
							|| (this.normalPoint.z < Math.min(a.z, b.z) || this.normalPoint.z > Math.max(this.a.z, this.b.z))) {
						this.normalPoint = this.b.copy();
					}
				}
				float distance = this.nextPosPrev.dist(this.normalPoint);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(this.b, this.a);
					this.dir.normalize();
					this.dir.mult(10);
					this.target = this.normalPoint.copy();
					this.target.add(this.dir);
				}
			}
		}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				this.returnVec = target;
				return target;
			} else {
				if (triggerSeekers && this.instanceable && this.instanceTriggerCount < this.maxChildren) {
					this.childSpawners.add(new PVector(loc.x, loc.y,loc.z));	
					this.childSpawnType.add(this.instanceTriggerCount);
					this.instanceTriggerCount++;
				} 
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public PVector pathFollow(ArrayList<Path> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, Boolean D3) {

		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);

		this.target = null;
		float worldRecord = 1000000;		
		for (int z = 0; z < pathList.size(); z++) {
			for (int i = 0; i < pathList.get(z).getPathPoints().size() - 1; i++) {
				this.a = pathList.get(z).getPathPoints().get(i);
				this.b = pathList.get(z).getPathPoints().get(i + 1);
				//this.normalPoint = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (!D3) {
					if (this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x)) {
						this.normalPoint = this.b.copy();
					}
				} else {
					if ((normalPoint.x < Math.min(a.x, b.x) || normalPoint.x > Math.max(this.a.x, this.b.x))
							|| (this.normalPoint.y < Math.min(a.y, b.y) || this.normalPoint.y > Math.max(this.a.y, this.b.y))
							|| (this.normalPoint.z < Math.min(a.z, b.z) || this.normalPoint.z > Math.max(this.a.z, this.b.z))) {
						this.normalPoint = this.b.copy();
					}
				}
				float distance = this.nextPosPrev.dist(this.normalPoint);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(this.b, this.a);
					this.dir.normalize();
					this.dir.mult(10);
					this.target = this.normalPoint.copy();
					this.target.add(this.dir);
				}
			}
		}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				this.returnVec = this.target;
				return this.target;
			} else {
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = this.zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public PVector pathFollowBaby(ArrayList<Path> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, boolean triggerSeekers, int babyCount, boolean canInstance, ArrayList<PVector> childList, ArrayList childTypeList, Boolean D3) {
		this.childSpawners = childList;
		this.childSpawnType = childTypeList;
		this.instanceable = canInstance;
		this.maxChildren = babyCount;
		
		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);

		this.target = null;
		float worldRecord = 1000000;
		for (int z = 0; z < pathList.size(); z++) {
			for (int i = 0; i < pathList.get(z).getPathPoints().size() - 1; i++) {
				this.a = pathList.get(z).getPathPoints().get(i);
				this.b = pathList.get(z).getPathPoints().get(i + 1);
				//this.normalPoint = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (!D3) {
					if (this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x)) {
						this.normalPoint = this.b.copy();
					}
				} else {
					if ((this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x))
							|| (this.normalPoint.y < Math.min(this.a.y, this.b.y) || normalPoint.y > Math.max(this.a.y, this.b.y))
							|| (this.normalPoint.z < Math.min(this.a.z, this.b.z) || normalPoint.z > Math.max(this.a.z, this.b.z))) {
						this.normalPoint = this.b.copy();
					}
				}
				float distance = this.nextPosPrev.dist(this.normalPoint);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(this.b, this.a);
					this.dir.normalize();
					this.dir.mult(10);
					this.target = this.normalPoint.copy();
					this.target.add(this.dir);
				}
			}
		}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				this.returnVec = this.target;
				return this.target;
			} else {
				if (triggerSeekers && this.instanceable && this.instanceTriggerCount < this.maxChildren) {
					this.childSpawners.add(new PVector(loc.x, loc.y,loc.z));	
					this.childSpawnType.add(this.instanceTriggerCount);
					this.instanceTriggerCount++;
				} 
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = this.zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public PVector trailFollow(List<ArrayList> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, Boolean D3) {

		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);
		
		this.target = null;
		float worldRecord = 1000000;
		for (int z = 0; z < pathList.size(); z++) {
			ArrayList data = (ArrayList) pathList.get(z);
			ArrayList<PVector> vecData = (ArrayList<PVector>) data.get(1);
			for (int i = 0; i < vecData.size() - 1; i++) {
				this.a = (PVector) vecData.get(i);
				this.b = (PVector) vecData.get(i + 1);
				//this.normalPoint = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (D3) {
					if (this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x)) {
						this.normalPoint = this.b.copy();
					}
				} else {
					if ((this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x))
							|| (this.normalPoint.y < Math.min(this.a.y, this.b.y) || normalPoint.y > Math.max(this.a.y, this.b.y))
							|| (this.normalPoint.z < Math.min(this.a.z, this.b.z) || normalPoint.z > Math.max(this.a.z, this.b.z))) {
						this.normalPoint = this.b.copy();
					}
				}
				float distance = this.nextPosPrev.dist(this.normalPoint);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(this.b, this.a);
					this.dir.normalize();
					this.dir.mult(10);
					this.target = this.normalPoint.copy();
					this.target.add(this.dir);
				}
			}
		}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				// seek(target);
				this.returnVec = this.target;
				return this.target;
			} else {
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = this.zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public PVector trailFollowBabyMaker(List<ArrayList> pathList, float maxDist, float amp, float pathRad, PVector speed,
			PVector loc, boolean triggerSeekers, int babyCount, boolean canInstance, ArrayList<PVector> childList, ArrayList childTypeList, Boolean D3) {
		
		this.childSpawners = childList;
		this.childSpawnType = childTypeList;
		this.instanceable = canInstance;
		this.maxChildren = babyCount;

		this.predict = speed.copy();
		this.predict.normalize();
		this.predict.mult(amp);
		this.nextPosPrev = PVector.add(loc, this.predict);

		this.target = null;
		float worldRecord = 1000000;		
		for (int z = 0; z < pathList.size(); z++) {
			ArrayList data = (ArrayList) pathList.get(z);
			ArrayList<PVector> vecData = (ArrayList<PVector>) data.get(1);
			for (int i = 0; i < vecData.size() - 1; i++) {
				this.a = (PVector) vecData.get(i);
				this.b = (PVector) vecData.get(i + 1);
				//this.normalPoint = getNormalPoint(this.nextPosPrev, this.a, this.b);
				this.normalPoint = Data.getNormalPoint(this.nextPosPrev, this.a, this.b);
				if (!D3) {
					if (this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x)) {
						this.normalPoint = this.b.copy();
					}
				} else {
					if ((this.normalPoint.x < Math.min(this.a.x, this.b.x) || this.normalPoint.x > Math.max(this.a.x, this.b.x))
							|| (this.normalPoint.y < Math.min(this.a.y, this.b.y) || this.normalPoint.y > Math.max(this.a.y, this.b.y))
							|| (this.normalPoint.z < Math.min(this.a.z, this.b.z) || this.normalPoint.z > Math.max(this.a.z, this.b.z))) {
						this.normalPoint = this.b.copy();
					}
				}
				float distance = this.nextPosPrev.dist(this.normalPoint);
				if (distance < worldRecord) {
					worldRecord = distance;
					this.dir = PVector.sub(this.b, this.a); 
					this.dir.normalize();
					this.dir.mult(10);
					this.target = this.normalPoint.copy();
					this.target.add(this.dir);
				}
			}
		}
		// remove if you want to just go to the line
		// maxDist = 10000000000000f;
		this.returnVec = new PVector();
		if (worldRecord < maxDist) {
			this.sepActive = true;
			if (worldRecord > pathRad) {
				this.returnVec = this.target;
				return this.target;
			} else {
				if (triggerSeekers && this.instanceable && this.instanceTriggerCount < this.maxChildren) {
					this.childSpawners.add(new PVector(loc.x, loc.y,loc.z));	
					this.childSpawnType.add(this.instanceTriggerCount);
					this.instanceTriggerCount++;
				} 
				this.zero = new PVector(0, 0, 0);
				this.zero.mult(3);
				this.returnVec = zero;
				return this.zero;
			}
		}
		return this.returnVec;
	}
	public ArrayList<PVector> getChildStartPositions(){	
		ArrayList<PVector> returnedList = this.childSpawners;
		this.childSpawners = new ArrayList<PVector>();
		return returnedList;
	}
	public ArrayList<Integer> getChildSpawnType(){	
		ArrayList<Integer> returnedList = this.childSpawnType;
		this.childSpawnType = new ArrayList<Integer>();
		return returnedList;
	}
	public void resetChildStartPositions(){
		this.childSpawners = new ArrayList<PVector>();
	}
	public void resetChildSpawnType(){
		this.childSpawnType = new ArrayList<Integer>();
	}
	public boolean isSeparateActive(){
		return this.sepActive;
	}
	public void setSeparateInactive(){
		this.sepActive = false;
	}	
	public void resetTriggerCount(){
		this.instanceTriggerCount = 0;
	}	
}
