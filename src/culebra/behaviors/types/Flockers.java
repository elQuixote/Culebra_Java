package culebra.behaviors.types;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;
import culebra.behaviorInterfaces.FlockBehavior;
import culebra.objects.Creeper;
import culebra.objects.Object;
import culebra.viz.Octree;
import culebra.viz.QuadTree;
import toxi.geom.*;

/**
 * Flockers Behavior Type Class Implements Flock Behavior Interface
 * 
 * @author elQuixote
 *
 */
public class Flockers implements FlockBehavior {

	private static ArrayList emptyList;
	private static ArrayList emptyTreeList;

	private Creeper other;
	private culebra.objects.Object otherObject;

	private float searchRad, av, sv, cv;
	private float tailViewAngle, tailCohViewRange, tailSepViewRange;
	private float tailAngle, tailVAngle;

	private List<PVector> otherPtsList;
	private List<PVector> otherMoveValues;
	private List<Float> allDist;
	private List<Creeper> creeperCollection;
	private List<culebra.objects.Object> objectCollection;
	private List<PVector> connList;

	private PVector loc;
	private PVector alignVector, separateVector, cohesionVector;
	private PVector cummVec;
	private PVector tailPerip = new PVector();

	private PVector conv = new PVector();
	private PVector vecRef = new PVector();
	private PVector searchPos = new PVector();
	private PVector blank = new PVector();
	private PVector otherIndex = new PVector();
	private PVector diff = new PVector();
	private PVector scaleVec = new PVector();

	private Vec2D tconv = new Vec2D();
	private Vec3D tconv3D = new Vec3D();

	private Rect rec;
	private Sphere bs;

	public Flockers() {
		this.creeperCollection = new ArrayList<Creeper>();
		this.connList = new ArrayList<PVector>();
	}

	public List<PVector> getNetworkData() {
		return this.connList;
	}

	public void resetConnections() {
		this.connList = new ArrayList<PVector>();
	}

	public PVector creepersflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, PVector speed, List<Creeper> collection, PVector location, Boolean drawConnectivity) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		float angle = (float) Math.toRadians(viewAngle);

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.creeperCollection.size(); i++) {
			this.other = (Creeper) creeperCollection.get(i);
			float distance = this.loc.dist(this.other.getLocation());
			if (distance > 0 && distance < this.searchRad) {
				tailPerip = PVector.sub(this.other.getLocation(), loc);
				tailAngle = PVector.angleBetween(tailPerip, speed);
				if (tailAngle < 0) {
					tailAngle += (Math.PI * 2);
				}
				if (Math.abs(tailAngle) < angle) {
					if (drawConnectivity) {
						this.conv = this.other.getLocation();
						this.connList.add(this.conv);
					}
					this.otherPtsList.add(this.other.getLocation());
					this.allDist.add(distance);
					this.otherMoveValues.add(this.other.getSpeed());
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------
			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector creepersflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, PVector location, Boolean drawConnectivity) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.creeperCollection.size(); i++) {
			this.other = (Creeper) creeperCollection.get(i);
			float distance = this.loc.dist(this.other.getLocation());
			if (distance > 0 && distance < this.searchRad) {
				if (drawConnectivity) {
					this.conv = this.other.getLocation();
					this.connList.add(this.conv);
				}
				this.otherPtsList.add(this.creeperCollection.get(i).getLocation());
				this.allDist.add(distance);
				this.otherMoveValues.add(this.creeperCollection.get(i).getSpeed());
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------

			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector creepersflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity) {

		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.vecRef = new PVector(this.searchRad / 2, this.searchRad / 2, 0);
		this.vecRef.mult(-1);
		this.searchPos = PVector.add(this.loc, this.vecRef);
		this.rec = new Rect(this.searchPos.x, this.searchPos.y, this.searchRad, this.searchRad);

		emptyTreeList = new ArrayList();
		emptyTreeList = qtree.getPointsWithinRect(rec);
		if (emptyTreeList != null) {
			if (emptyTreeList.size() != 1000000) {
				for (int i = 0; i < emptyTreeList.size(); i++) {
					int index = qtreeData.indexOf((Vec2D) emptyTreeList.get(i));
					if (drawConnectivity) {
						this.tconv = new Vec2D((Vec2D) emptyTreeList.get(i));
						this.conv = new PVector(this.tconv.x, this.tconv.y, 0);
						this.connList.add(this.conv);
					}
					this.other = (Creeper) creeperCollection.get(index);
					float distance = this.loc.dist(this.other.getLocation());

					if (distance != 0) {
						this.otherPtsList.add(this.other.getLocation());
						this.allDist.add(distance);
						if (index != -1) {
							this.otherMoveValues.add(this.other.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------
			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector flock2DAngle(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		float angle = (float) Math.toRadians(viewAngle);

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.vecRef = new PVector(this.searchRad / 2, this.searchRad / 2, 0);
		this.vecRef.mult(-1);
		this.searchPos = PVector.add(this.loc, this.vecRef);
		this.rec = new Rect(this.searchPos.x, this.searchPos.y, this.searchRad, this.searchRad);

		emptyTreeList = new ArrayList();
		emptyTreeList = qtree.getPointsWithinRect(rec);
		if (emptyTreeList != null) {
			if (emptyTreeList.size() != 1000000) {
				for (int i = 0; i < emptyTreeList.size(); i++) {
					int index = qtreeData.indexOf((Vec2D) emptyTreeList.get(i));
					this.other = (Creeper) creeperCollection.get(index);
					float distance = this.loc.dist(this.other.getLocation());
					if (distance != 0) {
						tailPerip = PVector.sub(this.other.getLocation(), loc);
						tailAngle = PVector.angleBetween(tailPerip, this.other.getSpeed());
						if (tailAngle < 0) {
							tailAngle += (Math.PI * 2);
						}
						if (Math.abs(tailAngle) < angle) {
							if (drawConnectivity) {
								this.conv = this.other.getLocation();
								this.connList.add(this.conv);
							}
							this.otherPtsList.add(this.other.getLocation());
							this.allDist.add(distance);
							this.otherMoveValues.add(this.other.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------
			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector creepersflock(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, PVector location, Boolean drawConnectivity) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.creeperCollection.size(); i++) {
			this.other = (Creeper) creeperCollection.get(i);
			float distance = this.loc.dist(this.other.getLocation());
			if (distance > 0 && distance < this.searchRad) {
				if (drawConnectivity) {
					this.conv = this.other.getLocation();
					this.connList.add(this.conv);
				}
				this.otherPtsList.add(this.creeperCollection.get(i).getLocation());
				this.allDist.add(distance);
				this.otherMoveValues.add(this.creeperCollection.get(i).getSpeed());
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------

			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector creepersflock(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, PVector location, Octree octree, List<Vec3D> octreeData,
			Boolean drawConnectivity) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.bs = new Sphere(new Vec3D(this.loc.x, this.loc.y, this.loc.z), this.searchRad);
		emptyList = new ArrayList();
		emptyList = octree.getPointsWithinSphere(bs);
		if (emptyList != null) {
			if (emptyList.size() != 1000000) {
				for (int i = 0; i < emptyList.size(); i++) {
					int index = octreeData.indexOf((Vec3D) emptyList.get(i));
					if (drawConnectivity) {
						this.tconv3D = new Vec3D((Vec3D) emptyList.get(i));
						this.conv = new PVector(this.tconv3D.x, this.tconv3D.y, this.tconv3D.z);
						this.connList.add(this.conv);
					}
					this.other = (Creeper) creeperCollection.get(index);
					float distance = this.loc.dist(this.other.getLocation());
					if (distance != 0) {
						this.otherPtsList.add(this.other.getLocation());
						this.allDist.add(distance);
						if (index != -1) {
							this.otherMoveValues.add(this.other.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------

			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector isolatedCreepersBehaviorsOptimized_2D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, List<Creeper> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity, boolean enableSeparation, boolean enableAlignment, boolean enableCohesion) {

		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.vecRef = new PVector(this.searchRad / 2, this.searchRad / 2, 0);
		this.vecRef.mult(-1);
		this.searchPos = PVector.add(this.loc, this.vecRef);
		this.rec = new Rect(this.searchPos.x, this.searchPos.y, this.searchRad, this.searchRad);

		emptyTreeList = new ArrayList();
		emptyTreeList = qtree.getPointsWithinRect(rec);
		if (emptyTreeList != null) {
			if (emptyTreeList.size() != 1000000) {
				for (int i = 0; i < emptyTreeList.size(); i++) {
					int index = qtreeData.indexOf((Vec2D) emptyTreeList.get(i));
					if (drawConnectivity) {
						this.tconv = new Vec2D((Vec2D) emptyTreeList.get(i));
						this.conv = new PVector(this.tconv.x, this.tconv.y, 0);
						this.connList.add(this.conv);
					}
					this.other = (Creeper) creeperCollection.get(index);
					float distance = this.loc.dist(this.other.getLocation());

					if (distance != 0) {
						this.otherPtsList.add(this.other.getLocation());
						this.allDist.add(distance);
						if (index != -1) {
							this.otherMoveValues.add(this.other.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			if (enableAlignment) {
				// ----------Align-----------------
				alignMethod();
				if (this.alignVector.magSq() > 0) {
					this.alignVector.normalize();
				}
				this.alignVector.mult(this.av);
				this.cummVec.add(this.alignVector);
			}
			if (enableSeparation) {
				// ----------Separate-----------------
				separateMethod();
				if (this.separateVector.magSq() > 0) {
					this.separateVector.normalize();
				}
				this.separateVector.mult(this.sv);
				this.cummVec.add(this.separateVector);
			}
			if (enableCohesion) {
				// ----------Cohesion-----------------
				cohesionMethod();
				if (this.cohesionVector.magSq() > 0) {
					this.cohesionVector.normalize();
				}
				this.cohesionVector.mult(this.cv);
				this.cummVec.add(this.cohesionVector);
			}
			// -----------------------------------
			return this.cummVec;
		}
		return this.blank;
	}

	public PVector isolatedCreepersBehaviorsOptimized_3D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, float viewAngle, List<Creeper> collection, PVector location, Octree octree,
			List<Vec3D> octreeData, Boolean drawConnectivity, boolean enableSeparation, boolean enableAlignment,
			boolean enableCohesion) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.bs = new Sphere(new Vec3D(this.loc.x, this.loc.y, this.loc.z), this.searchRad);
		emptyList = new ArrayList();
		emptyList = octree.getPointsWithinSphere(bs);
		if (emptyList != null) {
			if (emptyList.size() != 1000000) {
				for (int i = 0; i < emptyList.size(); i++) {
					int index = octreeData.indexOf((Vec3D) emptyList.get(i));
					if (drawConnectivity) {
						this.tconv3D = new Vec3D((Vec3D) emptyList.get(i));
						this.conv = new PVector(this.tconv3D.x, this.tconv3D.y, this.tconv3D.z);
						this.connList.add(this.conv);
					}
					this.other = (Creeper) creeperCollection.get(index);
					float distance = this.loc.dist(this.other.getLocation());
					if (distance != 0) {
						this.otherPtsList.add(this.other.getLocation());
						this.allDist.add(distance);
						if (index != -1) {
							this.otherMoveValues.add(this.other.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			if (enableAlignment) {
				// ----------Align-----------------
				alignMethod();
				if (this.alignVector.magSq() > 0) {
					this.alignVector.normalize();
				}
				this.alignVector.mult(this.av);
				this.cummVec.add(this.alignVector);
			}
			if (enableSeparation) {
				// ----------Separate-----------------
				separateMethod();
				if (this.separateVector.magSq() > 0) {
					this.separateVector.normalize();
				}
				this.separateVector.mult(this.sv);
				this.cummVec.add(this.separateVector);
			}
			if (enableCohesion) {
				// ----------Cohesion-----------------
				cohesionMethod();
				if (this.cohesionVector.magSq() > 0) {
					this.cohesionVector.normalize();
				}
				this.cohesionVector.mult(this.cv);
				this.cummVec.add(this.cohesionVector);
			}
			// -----------------------------------
			return this.cummVec;
		}
		return this.blank;
	}

	public PVector isolatedCreeperBehaviors3D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, float viewAngle, List<Creeper> collection, PVector location, Boolean drawConnectivity,
			boolean enableSeparation, boolean enableAlignment, boolean enableCohesion) {
		this.creeperCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.creeperCollection.size(); i++) {
			this.other = (Creeper) creeperCollection.get(i);
			float distance = this.loc.dist(this.other.getLocation());
			if (distance > 0 && distance < this.searchRad) {
				if (drawConnectivity) {
					this.conv = this.other.getLocation();
					this.connList.add(this.conv);
				}
				this.otherPtsList.add(this.creeperCollection.get(i).getLocation());
				this.allDist.add(distance);
				this.otherMoveValues.add(this.creeperCollection.get(i).getSpeed());
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			if (enableAlignment) {
				alignMethod();
				if (this.alignVector.magSq() > 0) {
					this.alignVector.normalize();
				}
				this.alignVector.mult(this.av);
				this.cummVec.add(this.alignVector);
			}
			// ----------Separate-----------------
			if (enableSeparation) {
				separateMethod();
				if (this.separateVector.magSq() > 0) {
					this.separateVector.normalize();
				}
				this.separateVector.mult(this.sv);
				this.cummVec.add(this.separateVector);
			}
			// ----------Cohesion-----------------
			if (enableCohesion) {
				cohesionMethod();
				if (this.cohesionVector.magSq() > 0) {
					this.cohesionVector.normalize();
				}
				this.cohesionVector.mult(this.cv);
				this.cummVec.add(this.cohesionVector);
			}
			// -----------------------------------
			return this.cummVec;
		}
		return this.blank;
	}

	public PVector creepersAlign(float searchRadius, float alignThreshold, List<Creeper> collection, PVector location) {
		return isolatedCreeperBehaviors3D(searchRadius, 0.0f, 0.0f, alignThreshold, 0.0f, collection, location, false,
				false, true, false);
	}

	public PVector creepersCohesion(float searchRadius, float cohesionValue, List<Creeper> collection,
			PVector location) {
		return isolatedCreeperBehaviors3D(searchRadius, cohesionValue, 0.0f, 0.0f, 0.0f, collection, location, false,
				false, false, true);
	}

	public PVector creepersSeparate(float searchRadius, float separateValue, List<Creeper> collection,
			PVector location) {
		return isolatedCreeperBehaviors3D(searchRadius, 0.0f, separateValue, 0.0f, 0.0f, collection, location, false,
				true, false, false);
	}

	public PVector creepersSeparate(List<Creeper> collection, PVector location, float maxSeparation) {
		float desiredseparation = maxSeparation * 2;
		PVector steer = new PVector(0, 0, 0);
		int count = 0;
		// check if we are too close
		for (int i = 0; i < collection.size(); i++) {
			this.other = (Creeper) collection.get(i);
			float d = location.dist(this.other.getLocation());
			if ((d > 0) && (d < desiredseparation)) {
				this.diff = PVector.sub(location, this.other.getLocation());
				this.diff.normalize();
				this.diff = PVector.mult(this.diff, (1.00f / d)); // Weight by
																	// distance
				steer.add(this.diff);
				count++;
			}
		}
		if (count > 0) {
			steer.mult(1.0f / (float) count);
		}
		return steer;
	}

	public PVector flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<culebra.objects.Object> collection, PVector location, Boolean drawConnectivity) {
		return isolatedBehaviors3D(searchRadius, cohesionValue, separateValue, alignValue, 360.0f, collection, location,
				drawConnectivity, true, true, true);
	}

	public PVector flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, PVector speed, List<culebra.objects.Object> collection, PVector location,
			Boolean drawConnectivity) {
		this.objectCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		float angle = (float) Math.toRadians(viewAngle);

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.objectCollection.size(); i++) {
			float distance = this.loc.dist(this.objectCollection.get(i).getLocation());
			if (distance > 0 && distance < this.searchRad) {
				tailPerip = PVector.sub(this.objectCollection.get(i).getLocation(), loc);
				tailAngle = PVector.angleBetween(tailPerip, speed);
				if (tailAngle < 0) {
					tailAngle += (Math.PI * 2);
				}
				if (Math.abs(tailAngle) < angle) {
					if (drawConnectivity) {
						this.conv = this.objectCollection.get(i).getLocation();
						this.connList.add(this.conv);
					}
					this.otherPtsList.add(this.objectCollection.get(i).getLocation());
					this.allDist.add(distance);
					this.otherMoveValues.add(this.objectCollection.get(i).getSpeed());
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------
			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	public PVector flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<culebra.objects.Object> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity) {

		this.objectCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.vecRef = new PVector(this.searchRad / 2, this.searchRad / 2, 0);
		this.vecRef.mult(-1);
		this.searchPos = PVector.add(this.loc, this.vecRef);
		this.rec = new Rect(this.searchPos.x, this.searchPos.y, this.searchRad, this.searchRad);

		emptyTreeList = new ArrayList();
		emptyTreeList = qtree.getPointsWithinRect(rec);
		if (emptyTreeList != null) {
			if (emptyTreeList.size() != 1000000) {
				for (int i = 0; i < emptyTreeList.size(); i++) {
					int index = qtreeData.indexOf((Vec2D) emptyTreeList.get(i));
					if (drawConnectivity) {
						this.tconv = new Vec2D((Vec2D) emptyTreeList.get(i));
						this.conv = new PVector(this.tconv.x, this.tconv.y, 0);
						this.connList.add(this.conv);
					}
					this.otherObject = objectCollection.get(index);

					float distance = this.loc.dist(this.otherObject.getLocation());

					if (distance != 0) {
						this.otherPtsList.add(this.otherObject.getLocation());
						this.allDist.add(distance);
						if (index != -1) {
							this.otherMoveValues.add(this.otherObject.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------
			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	@Override
	public PVector flock(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Object> collection, PVector location, Boolean drawConnectivity) {
		this.objectCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.objectCollection.size(); i++) {
			this.otherObject = objectCollection.get(i);
			float distance = this.loc.dist(this.otherObject.getLocation());
			if (distance > 0 && distance < this.searchRad) {
				if (drawConnectivity) {
					this.conv = this.otherObject.getLocation();
					this.connList.add(this.conv);
				}
				this.otherPtsList.add(this.objectCollection.get(i).getLocation());
				this.allDist.add(distance);
				this.otherMoveValues.add(this.objectCollection.get(i).getSpeed());
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------

			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	@Override
	public PVector flock(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Object> collection, PVector location, Octree octree, List<Vec3D> octreeData,
			Boolean drawConnectivity) {
		this.objectCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.bs = new Sphere(new Vec3D(this.loc.x, this.loc.y, this.loc.z), this.searchRad);
		emptyList = new ArrayList();
		emptyList = octree.getPointsWithinSphere(bs);
		if (emptyList != null) {
			if (emptyList.size() != 1000000) {
				for (int i = 0; i < emptyList.size(); i++) {
					int index = octreeData.indexOf((Vec3D) emptyList.get(i));
					if (drawConnectivity) {
						this.tconv3D = new Vec3D((Vec3D) emptyList.get(i));
						this.conv = new PVector(this.tconv3D.x, this.tconv3D.y, this.tconv3D.z);
						this.connList.add(this.conv);
					}
					this.otherObject = objectCollection.get(index);
					float distance = this.loc.dist(this.otherObject.getLocation());
					if (distance != 0) {
						this.otherPtsList.add(this.otherObject.getLocation());
						this.allDist.add(distance);
						if (index != -1) {
							this.otherMoveValues.add(this.otherObject.getSpeed());
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			alignMethod();
			if (this.alignVector.magSq() > 0) {
				this.alignVector.normalize();
			}
			this.alignVector.mult(this.av);
			// ----------Separate-----------------
			separateMethod();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(this.sv);
			// ----------Cohesion-----------------
			cohesionMethod();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(this.cv);
			// -----------------------------------

			this.cummVec.add(this.alignVector);
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		}
		return this.blank;
	}

	@Override
	public PVector separate(float searchRadius, float separateValue, List<Object> collection, PVector location) {
		return isolatedBehaviors3D(searchRadius, 0.0f, separateValue, 0.0f, 0.0f, collection, location, false, true,
				false, false);
	}

	@Override
	public PVector separate(List<Object> collection, PVector location, float maxSeparation) {
		float desiredseparation = maxSeparation * 2;
		PVector steer = new PVector(0, 0, 0);
		int count = 0;
		// check if we are too close
		for (int i = 0; i < collection.size(); i++) {
			this.otherObject = collection.get(i);
			float d = location.dist(this.otherObject.getLocation());
			if ((d > 0) && (d < desiredseparation)) {
				this.diff = PVector.sub(location, this.otherObject.getLocation());
				this.diff.normalize();
				this.diff = PVector.mult(this.diff, (1.00f / d)); // Weight by
																	// distance
				steer.add(this.diff);
				count++;
			}
		}
		if (count > 0) {
			steer.mult(1.0f / (float) count);
		}
		return steer;
	}

	@Override
	public PVector align(float searchRadius, float alignThreshold, List<Object> collection, PVector location) {
		return isolatedBehaviors3D(searchRadius, 0.0f, 0.0f, alignThreshold, 0.0f, collection, location, false, false,
				true, false);
	}

	@Override
	public PVector cohesion(float searchRadius, float cohesionValue, List<Object> collection, PVector location) {
		return isolatedBehaviors3D(searchRadius, cohesionValue, 0.0f, 0.0f, 0.0f, collection, location, false, false,
				false, true);
	}

	@Override
	public PVector isolatedBehaviorsOptimized_2D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, List<Object> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity, boolean enableSeparation, boolean enableAlignment, boolean enableCohesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PVector isolatedBehaviorsOptimized_3D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, float viewAngle, List<Object> collection, PVector location, Octree octree,
			List<Vec3D> octreeData, Boolean drawSearchConnectivity, boolean enableSeparation, boolean enableAlignment,
			boolean enableCohesion) {
		// TODO Auto-generated method stub
		return null;
	}

	public PVector isolatedBehaviors3D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Object> collection, PVector location, Boolean drawSearchConnectivity,
			boolean enableSeparation, boolean enableAlignment, boolean enableCohesion) {

		this.objectCollection = collection;
		this.searchRad = searchRadius;
		this.av = alignValue;
		this.cv = cohesionValue;
		this.sv = separateValue;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < this.objectCollection.size(); i++) {
			this.otherObject = objectCollection.get(i);
			float distance = this.loc.dist(this.otherObject.getLocation());
			if (distance > 0 && distance < this.searchRad) {
				if (drawSearchConnectivity) {
					this.conv = this.otherObject.getLocation();
					this.connList.add(this.conv);
				}
				this.otherPtsList.add(this.objectCollection.get(i).getLocation());
				this.allDist.add(distance);
				this.otherMoveValues.add(this.objectCollection.get(i).getSpeed());
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Align-----------------
			if (enableAlignment) {
				alignMethod();
				if (this.alignVector.magSq() > 0) {
					this.alignVector.normalize();
				}
				this.alignVector.mult(this.av);
				this.cummVec.add(this.alignVector);
			}
			// ----------Separate-----------------
			if (enableSeparation) {
				separateMethod();
				if (this.separateVector.magSq() > 0) {
					this.separateVector.normalize();
				}
				this.separateVector.mult(this.sv);
				this.cummVec.add(this.separateVector);
			}
			// ----------Cohesion-----------------
			if (enableCohesion) {
				cohesionMethod();
				if (this.cohesionVector.magSq() > 0) {
					this.cohesionVector.normalize();
				}
				this.cohesionVector.mult(this.cv);
				this.cummVec.add(this.cohesionVector);
			}
			// -----------------------------------
			return this.cummVec;
		}
		return this.blank;
	}

	public PVector trailFollow(float tailViewAngle, float tailCohMag, float tailCohViewRange, float tailSepMag,
			float tailSepViewRange, List<PVector> field, Octree octree, List<Vec3D> octreeData, PVector speed,
			PVector location) {
		getAngle();
		this.tailViewAngle = tailViewAngle;
		this.tailCohViewRange = tailCohViewRange;
		this.tailSepViewRange = tailSepViewRange;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		this.bs = new Sphere(new Vec3D(this.loc.x, this.loc.y, this.loc.z), this.searchRad);
		emptyList = new ArrayList();
		emptyList = octree.getPointsWithinSphere(this.bs);
		if (emptyList != null) {
			if (emptyList.size() != 1000000) {
				for (int i = 0; i < emptyList.size(); i++) {
					int index = octreeData.indexOf((Vec3D) emptyList.get(i));
					this.otherIndex = field.get(index);
					float distance = this.loc.dist(this.otherIndex);
					if (distance > 0 && distance < tailCohViewRange) {
						tailPerip = PVector.sub(field.get(i), loc);
						tailAngle = PVector.angleBetween(tailPerip, speed);
						if (tailAngle < 0) {
							tailAngle += (Math.PI * 2);
						}
						if (Math.abs(tailAngle) < tailVAngle) {
							this.otherPtsList.add(this.otherIndex);
							this.allDist.add(distance);
						}
					}
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Separate-----------------
			trailSeparate();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(tailSepMag);
			// ----------Cohesion-----------------
			trailCohesion();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(tailCohMag);
			// -----------------------------------
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		} else {
			return this.blank;
		}
	}

	public PVector trailFollow(float tailViewAngle, float tailCohMag, float tailCohViewRange, float tailSepMag,
			float tailSepViewRange, List<PVector> field, PVector speed, PVector location) {
		getAngle();
		this.tailViewAngle = tailViewAngle;
		this.tailCohViewRange = tailCohViewRange;
		this.tailSepViewRange = tailSepViewRange;
		this.loc = location;

		this.otherPtsList = new ArrayList<PVector>();
		this.otherMoveValues = new ArrayList<PVector>();
		this.allDist = new ArrayList<Float>();

		for (int i = 0; i < field.size(); i++) {
			this.otherIndex = field.get(i);
			float distance = this.loc.dist(this.otherIndex);
			if (distance > 0 && distance < tailCohViewRange) {
				tailPerip = PVector.sub(field.get(i), loc);
				tailAngle = PVector.angleBetween(tailPerip, speed);
				if (tailAngle < 0) {
					tailAngle += (Math.PI * 2);
				}
				if (Math.abs(tailAngle) < tailVAngle) {
					this.otherPtsList.add(this.otherIndex);
					this.allDist.add(distance);
				}
			}
		}
		if (this.otherPtsList.size() > 0) {
			this.cummVec = new PVector();
			// ----------Separate-----------------
			trailSeparate();
			if (this.separateVector.magSq() > 0) {
				this.separateVector.normalize();
			}
			this.separateVector.mult(tailSepMag);
			// ----------Cohesion-----------------
			trailCohesion();
			if (this.cohesionVector.magSq() > 0) {
				this.cohesionVector.normalize();
			}
			this.cohesionVector.mult(tailCohMag);
			// -----------------------------------
			this.cummVec.add(this.separateVector);
			this.cummVec.add(this.cohesionVector);

			return this.cummVec;
		} else {
			return this.blank;
		}
	}

	public void getAngle() {
		double conv = Math.toRadians(tailViewAngle);
		float angl = (float) conv;
		this.tailVAngle = angl;
	}

	public void trailCohesion() {
		this.cohesionVector = new PVector();
		for (int i = 0; i < this.otherPtsList.size(); i++) {
			this.cohesionVector.add(this.otherPtsList.get(i));
		}
		this.scaleVec = PVector.mult(this.cohesionVector, (1.00f / this.otherPtsList.size()));
		float dist = this.loc.dist(this.scaleVec);
		this.cohesionVector = PVector.mult(PVector.sub(this.scaleVec, this.loc), tailCohViewRange / dist);
	}

	public void trailSeparate() {
		this.separateVector = new PVector();
		for (int i = 0; i < this.otherPtsList.size(); i++) {
			this.separateVector.add(PVector.mult(PVector.sub(this.loc, this.otherPtsList.get(i)),
					tailSepViewRange / this.allDist.get(i)));
		}
	}

	public void separateMethod() {
		this.separateVector = new PVector();
		for (int i = 0; i < this.otherPtsList.size(); i++) {
			this.separateVector.add(PVector.mult(PVector.sub(this.loc, this.otherPtsList.get(i)),
					this.searchRad / this.allDist.get(i)));
		}
	}

	public void alignMethod() {
		this.alignVector = new PVector();
		for (int i = 0; i < this.otherPtsList.size(); i++) {
			this.alignVector.add(PVector.mult(this.otherMoveValues.get(i), this.searchRad / this.allDist.get(i)));
		}
	}

	public void cohesionMethod() {
		this.cohesionVector = new PVector();
		for (int i = 0; i < this.otherPtsList.size(); i++) {
			this.cohesionVector.add(this.otherPtsList.get(i));
		}
		this.scaleVec = PVector.mult(this.cohesionVector, (1.00f / this.otherPtsList.size()));
		float dist = this.loc.dist(this.scaleVec);
		this.cohesionVector = PVector.mult(PVector.sub(this.scaleVec, this.loc), this.searchRad / dist);
	}

}
