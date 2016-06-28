package culebra.behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import processing.core.*;
import culebra.behaviorInterfaces.Attributes;
import culebra.behaviorInterfaces.ForceBehavior;
import culebra.behaviors.types.*;
import culebra.viz.Octree;
import culebra.data.Data;
import culebra.geometry.Mesh;
import culebra.objects.Creeper;
import culebra.objects.Object;
import culebra.objects.Trump;
import culebra.viz.Path;
import culebra.viz.QuadTree;
import toxi.geom.*;

/**
 * Behavior Controller Class - this is the class which acts as controller for
 * all behaviors classes. This class also builds on behaviors, using image
 * drivers and other features which are not in the stock behavior class.
 * 
 * @author elQuixote
 *
 */
public class Controller implements ForceBehavior, Attributes {

	private String behaviorType = "None";
	private String objType = "None";
	private String childType = "None";
	private String objSuperClass = "None";
	private PVector loc;
	private PVector speed;
	private PVector acc = new PVector();
	private PVector cummVec;
	private boolean instanceable;
	private float maxSpeed;
	private float maxForce;
	private float velocity;
	private ArrayList<PVector> creeperTrails;
	private Boolean D3;
	private Boolean drawConnections;
	private int spawnCount = 0;
	private Random r;
	private Flockers flocker;
	private Wanderer wand;
	private Noise pnoise;
	private Tracker tracker;
	private MeshCrawler crawler;

	/**
	 * Constructor - Initializes all the Behavior Instances & relevant fields
	 */
	public Controller() {
		this.setCreeperTrails(new ArrayList<PVector>());
		this.flocker = new Flockers();
		this.wand = new Wanderer();
		this.pnoise = new Noise();
		this.tracker = new Tracker();
		this.crawler = new MeshCrawler();
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------FLOCKING CONTROLLERS--------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * Method which resets connecting lines visualizing flocking search radius
	 */
	public void resetConnections() {
		this.flocker.resetConnections();
	}

	/**
	 * Method which gets the flocking search network data
	 * 
	 * @return network data which visualizes the search radius between agents
	 */
	public List<PVector> getNetworkData() {
		List<PVector> tansferList = this.flocker.getNetworkData();
		this.flocker.resetConnections();
		return tansferList;
	}

	/**
	 * 2D Flocking Algorithm
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of creepers
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.creepersflock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Flocking - this example adds an angle parameter which
	 * allows agents to see only within the angle specified
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees
	 * @param collection
	 *            list of creepers
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.creepersflock2D(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				this.getSpeed(), collection, this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Optimized Flocking with Toxiclibs QuadTree setup.
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of creepers
	 * @param qTree
	 *            quadtree, use culebra.data.Data class to generate the tree -
	 *            see example files
	 * @param qtreeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperflock2DTree(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, QuadTree qTree, List<Vec2D> qtreeList, Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.creepersflock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), qTree, qtreeList, drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Optimized Flocking with Toxiclibs QuadTree setup and angle parameter
	 * restricting agent visibility based on angle.
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees
	 * @param collection
	 *            list of creepers
	 * @param qTree
	 *            quadtree, use culebra.data.Data class to generate the tree -
	 *            see example files
	 * @param qtreeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperflock2DAngleTree(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, QuadTree qTree, List<Vec2D> qtreeList,
			Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock2DAngle(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				collection, this.getLoc(), qTree, qtreeList, drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Flocking Algorithm with image color sampling override for any behavior
	 * attribute
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of creepers
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 * @param img
	 *            the image sample
	 * @param mapAlignment
	 *            uses image color data as multiplier for alignment value
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param mapCohesion
	 *            uses image color data as multiplier for cohesion value
	 */
	public void creeperflock2DMap(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, Boolean drawSearchConnectivity, PImage img, Boolean mapAlignment,
			Boolean mapSeparation, Boolean mapCohesion) {
		this.setBehaviorType("Flocker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			if (mapAlignment) {
				alignValue *= val;
			}
			if (mapSeparation) {
				separateValue *= val;
			}
			if (mapCohesion) {
				cohesionValue *= val;
			}
		}
		this.cummVec = this.flocker.creepersflock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), drawSearchConnectivity);

		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Flocking Algorithm with image color sampling override for
	 * any behavior attribute
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of creepers
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 * @param img
	 *            the image sample
	 * @param mapAlignment
	 *            uses image color data as multiplier for alignment value
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param mapCohesion
	 *            uses image color data as multiplier for cohesion value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void creeperflock2DMap(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, boolean drawSearchConnectivity, PImage img, boolean mapAlignment,
			boolean mapSeparation, boolean mapCohesion, float minValue, float maxValue) {
		this.setBehaviorType("Flocker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);
			if (mapAlignment) {
				alignValue *= mappedValue;
			}
			if (mapSeparation) {
				separateValue *= mappedValue;
			}
			if (mapCohesion) {
				cohesionValue *= mappedValue;
			}
		}
		this.cummVec = this.flocker.creepersflock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Flocking Algorithm with image color sampling override for
	 * any behavior attribute
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of creepers
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 * @param img
	 *            the image sample
	 * @param mapAlignment
	 *            uses image color data as multiplier for alignment value
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param mapCohesion
	 *            uses image color data as multiplier for cohesion value
	 * @param mapViewAngle
	 *            uses image color data as multiplier for view angle value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void creeperflock2DMap(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, boolean drawSearchConnectivity, PImage img, boolean mapAlignment,
			boolean mapSeparation, boolean mapCohesion, boolean mapViewAngle, float minValue, float maxValue) {
		this.setBehaviorType("Flocker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);
			if (mapAlignment) {
				alignValue *= mappedValue;
			}
			if (mapSeparation) {
				separateValue *= mappedValue;
			}
			if (mapCohesion) {
				cohesionValue *= mappedValue;
			}
			if (mapViewAngle) {
				viewAngle *= mappedValue;
			}
		}
		this.cummVec = this.flocker.creepersflock2D(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				this.getSpeed(), collection, this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 3D Flocking Algorithm
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separate value avoids crowding neighbors (short range
	 *            repulsion). Is only enabled for whatever agents are within the
	 *            search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees.
	 * @param collection
	 *            list of creepers.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius.
	 */
	public void creeperflock(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.creepersflock(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				collection, this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 3D Flocking Algorithm with optimized search via OcTree from
	 * Toxiclibs
	 * 
	 * @param cohesionThreshold
	 *            threshold distance for cohesion behavior to be enabled
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separate value avoids crowding neighbors (short range
	 *            repulsion). Is only enabled for whatever agents are within the
	 *            search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees.
	 * @param collection
	 *            list of creepers
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperflock(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, Octree ocTree, List<Vec3D> treeList,
			boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.creepersflock(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				collection, this.getLoc(), ocTree, treeList, drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 3D Trail Chasing Algorithm with optimized search via OcTree from
	 * Toxiclibs - Agents will chase all other agents trails. When using this
	 * algorithm in your main sketch use the overloaded move method, recommended
	 * values are move(6,100)
	 * 
	 * @param tailViewAngle
	 *            allowable vision angle in degrees.
	 * @param tailCohMag
	 *            cohesion value steers towards average positions.
	 * @param tailCohViewRange
	 *            cohesion threshold, value within range will enable tailCohMag
	 * @param tailSepMag
	 *            separation value avoids crowding on trail.
	 * @param tailSepViewRange
	 *            separation threshold, value within range will enable
	 *            tailSepMag
	 * @param trailsPts
	 *            list of all PVectors from all trails - see example file
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 */
	public void selfTailChaser(float tailViewAngle, float tailCohMag, float tailCohViewRange, float tailSepMag,
			float tailSepViewRange, List<PVector> trailsPts, Octree ocTree, List<Vec3D> treeList) {
		this.setBehaviorType("SelfChaser");
		this.cummVec = this.flocker.trailFollow(tailViewAngle, tailCohMag, tailCohViewRange, tailSepMag,
				tailSepViewRange, trailsPts, ocTree, treeList, this.getSpeed(), this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * 2D/3D Trail Chasing Algorithm - Agents will chase all other agents
	 * trails. When using this algorithm in your main sketch use the overloaded
	 * move method, recommended values are move(6,100)
	 * 
	 * @param tailViewAngle
	 *            allowable vision angle in degrees.
	 * @param tailCohMag
	 *            cohesion value steers towards average positions.
	 * @param tailCohViewRange
	 *            cohesion threshold, value within range will enable tailCohMag
	 * @param tailSepMag
	 *            separation value avoids crowding on trail.
	 * @param tailSepViewRange
	 *            separation threshold, value within range will enable
	 *            tailSepMag
	 * @param trailsPts
	 *            list of all PVectors from all trails - see example file
	 */
	public void selfTailChaser(float tailViewAngle, float tailCohMag, float tailCohViewRange, float tailSepMag,
			float tailSepViewRange, List<PVector> trailsPts) {
		this.setBehaviorType("SelfChaser");
		this.cummVec = this.flocker.trailFollow(tailViewAngle, tailCohMag, tailCohViewRange, tailSepMag,
				tailSepViewRange, trailsPts, this.getSpeed(), this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Alignment Behavior steers towards average heading of neighbors.
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param alignValue
	 *            steers towards average heading of neighbors. Is only enabled
	 *            for whatever agents are within the search radius.
	 * @param collection
	 *            list of other creepers
	 */
	public void creeperAlign(float searchRadius, float alignValue, List<Creeper> collection) {
		this.cummVec = flocker.creepersAlign(searchRadius, alignValue, collection, this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 2D Alignment Behavior steers towards average heading of
	 * neighbors, uses Quad data.
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param alignValue
	 *            steers towards average heading of neighbors. Is only enabled
	 *            for whatever agents are within the search radius.
	 * @param collection
	 *            list of other creepers
	 * @param qtree
	 *            use culebra.data.Data class to generate the quadtree - see
	 *            example files
	 * @param qtreeData
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperAlignOptimized_2D(float searchRadius, float alignValue, List<Creeper> collection, QuadTree qtree,
			List<Vec2D> qtreeData, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedCreepersBehaviorsOptimized_2D(searchRadius, 0.0f, 0.0f, alignValue, collection,
				this.getLoc(), qtree, qtreeData, drawSearchConnectivity, false, true, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 3D Alignment Behavior steers towards average heading of
	 * neighbors, uses Octree data.
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param alignValue
	 *            steers towards average heading of neighbors. Is only enabled
	 *            for whatever agents are within the search radius.
	 * @param collection
	 *            list of other creepers
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperAlignOptimized_3D(float searchRadius, float alignValue, List<Creeper> collection, Octree ocTree,
			List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedCreepersBehaviorsOptimized_3D(searchRadius, 0.0f, 0.0f, alignValue, 0.0f,
				collection, this.getLoc(), ocTree, treeList, drawSearchConnectivity, false, true, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Separation Behavior - avoids crowding neighbors (short range repulsion)
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param separateValue
	 *            avoids crowding neighbors (short range repulsion). Is only
	 *            enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of other creepers
	 */
	public void creeperSeparate(float searchRadius, float separateValue, List<Creeper> collection) {
		this.cummVec = flocker.creepersSeparate(searchRadius, separateValue, collection, this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Separation Behavior II - avoids crowding neighbors (short range
	 * repulsion)
	 * 
	 * @param maxSeparation
	 *            maxDistance threshold to enable separate
	 * @param collection
	 *            list of other creepers
	 */
	public void creeperSeparate(float maxSeparation, List<Creeper> collection) {
		this.cummVec = flocker.creepersSeparate(collection, this.getLoc(), maxSeparation);
		if (this.cummVec.magSq() > 0) {
			// Steering = Desired - Velocity
			this.cummVec.normalize();
			this.cummVec.mult(this.getMaxSpeed());
			this.cummVec.sub(this.getSpeed());
			this.cummVec.limit(this.getMaxForce());
		}
		this.cummVec.mult(3);
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 2D Separation Behavior - avoids crowding neighbors (short range
	 * repulsion)
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param separateValue
	 *            avoids crowding neighbors (short range repulsion). Is only
	 *            enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of other creepers
	 * @param qtree
	 *            use culebra.data.Data class to generate the quadtree - see
	 *            example files
	 * @param qtreeData
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperSeparateOptimized_2D(float searchRadius, float separateValue, List<Creeper> collection,
			QuadTree qtree, List<Vec2D> qtreeData, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedCreepersBehaviorsOptimized_2D(searchRadius, 0.0f, separateValue, 0.0f,
				collection, this.getLoc(), qtree, qtreeData, drawSearchConnectivity, true, false, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 3D Separation Behavior - avoids crowding neighbors (short range
	 * repulsion)
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param separateValue
	 *            avoids crowding neighbors (short range repulsion). Is only
	 *            enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of other creepers
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperSeparateOptimized_3D(float searchRadius, float separateValue, List<Creeper> collection,
			Octree ocTree, List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedCreepersBehaviorsOptimized_3D(searchRadius, 0.0f, separateValue, 0.0f, 0.0f,
				collection, this.getLoc(), ocTree, treeList, drawSearchConnectivity, true, false, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Separation Algorithm with image color sampling override for any
	 * behavior attribute
	 * 
	 * @param maxSeparation
	 *            maxDistance threshold to enable separate
	 * @param collection
	 *            list of other creepers
	 * @param img
	 *            the image sample
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 */
	public void creeperSeparate2DMap(float maxSeparation, List<Creeper> collection, PImage img, Boolean mapSeparation) {
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			if (mapSeparation) {
				maxSeparation *= val;
			}
		}
		this.cummVec = flocker.creepersSeparate(collection, this.getLoc(), maxSeparation);
		if (this.cummVec.magSq() > 0) {
			// Steering = Desired - Velocity
			this.cummVec.normalize();
			this.cummVec.mult(this.getMaxSpeed());
			this.cummVec.sub(this.getSpeed());
			this.cummVec.limit(this.getMaxForce());

		}
		this.cummVec.mult(3);
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Separation Algorithm with image color sampling override for
	 * any behavior attribute with color value remapping
	 * 
	 * @param maxSeparation
	 *            maxDistance threshold to enable separate
	 * @param collection
	 *            list of creepers
	 * @param img
	 *            the image sample
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void creeperSeparate2DMap(float maxSeparation, List<Creeper> collection, PImage img, boolean mapSeparation,
			float minValue, float maxValue) {
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);
			if (mapSeparation) {
				maxSeparation *= mappedValue;
			}
		}
		this.cummVec = flocker.creepersSeparate(collection, this.getLoc(), maxSeparation);
		if (this.cummVec.magSq() > 0) {
			// Steering = Desired - Velocity
			this.cummVec.normalize();
			this.cummVec.mult(this.getMaxSpeed());
			this.cummVec.sub(this.getSpeed());
			this.cummVec.limit(this.getMaxForce());
		}
		this.cummVec.mult(3);
		applyForce(this.cummVec);
	}

	/**
	 * Cohesion Behavior steers towards average positions of neighbors (long
	 * range attraction)
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            steers towards average positions of neighbors (long range
	 *            attraction). Is only enabled for whatever agents are within
	 *            the search radius.
	 * @param collection
	 *            list of other creepers
	 */
	public void creeperCohesion(float searchRadius, float cohesionValue, List<Creeper> collection) {
		this.cummVec = flocker.creepersCohesion(searchRadius, cohesionValue, collection, this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 2D Cohesion Behavior steers towards average positions of
	 * neighbors (long range attraction)
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            steers towards average positions of neighbors (long range
	 *            attraction). Is only enabled for whatever agents are within
	 *            the search radius.
	 * @param collection
	 *            list of other creepers
	 * @param qtree
	 *            use culebra.data.Data class to generate the quadtree - see
	 *            example files
	 * @param qtreeData
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperCohesionOptimized_2D(float searchRadius, float cohesionValue, List<Creeper> collection,
			QuadTree qtree, List<Vec2D> qtreeData, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedCreepersBehaviorsOptimized_2D(searchRadius, cohesionValue, 0.0f, 0.0f,
				collection, this.getLoc(), qtree, qtreeData, drawSearchConnectivity, false, false, true);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 3D Cohesion Behavior steers towards average positions of
	 * neighbors (long range attraction)
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            steers towards average positions of neighbors (long range
	 *            attraction). Is only enabled for whatever agents are within
	 *            the search radius.
	 * @param collection
	 *            list of other creepers
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void creeperCohesionOptimized_3D(float searchRadius, float cohesionValue, List<Creeper> collection,
			Octree ocTree, List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedCreepersBehaviorsOptimized_3D(searchRadius, cohesionValue, 0.0f, 0.0f, 0.0f,
				collection, this.getLoc(), ocTree, treeList, drawSearchConnectivity, false, false, true);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Flocking Algorithm for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Object> collection, Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Flocking for use with culebra.objects.Object type - this
	 * example adds an angle parameter which allows agents to see only within
	 * the angle specified
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue, float viewAngle,
			List<Object> collection, Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock2D(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				this.getSpeed(), collection, this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Optimized Flocking for use with culebra.objects.Object type with
	 * Toxiclibs QuadTree setup.
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param qTree
	 *            quadtree, use culebra.data.Data class to generate the tree -
	 *            see example files
	 * @param qtreeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void flock2DTree(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<culebra.objects.Object> collection, QuadTree qTree, List<Vec2D> qtreeList,
			Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), qTree, qtreeList, drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Optimized Flocking with Toxiclibs QuadTree setup and angle parameter
	 * restricting agent visibility based on angle.
	 * 
	 * @param searchRadius
	 *            distance each creeper can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees
	 * @param collection
	 *            list of creepers
	 * @param qTree
	 *            quadtree, use culebra.data.Data class to generate the tree -
	 *            see example files
	 * @param qtreeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void flock2DAngleTree(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Creeper> collection, QuadTree qTree, List<Vec2D> qtreeList,
			Boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock2DAngle(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				collection, this.getLoc(), qTree, qtreeList, drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Flocking Algorithm with image color sampling override for any behavior
	 * attribute for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 * @param img
	 *            the image sample
	 * @param mapAlignment
	 *            uses image color data as multiplier for alignment value
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param mapCohesion
	 *            uses image color data as multiplier for cohesion value
	 */
	public void flock2DMap(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Object> collection, Boolean drawSearchConnectivity, PImage img, Boolean mapAlignment,
			Boolean mapSeparation, Boolean mapCohesion) {
		this.setBehaviorType("Flocker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			if (mapAlignment) {
				alignValue *= val;
			}
			if (mapSeparation) {
				separateValue *= val;
			}
			if (mapCohesion) {
				cohesionValue *= val;
			}
		}
		this.cummVec = this.flocker.flock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), drawSearchConnectivity);

		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Flocking Algorithm with image color sampling override for
	 * any behavior attribute for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 * @param img
	 *            the image sample
	 * @param mapAlignment
	 *            uses image color data as multiplier for alignment value
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param mapCohesion
	 *            uses image color data as multiplier for cohesion value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void flock2DMap(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Object> collection, boolean drawSearchConnectivity, PImage img, boolean mapAlignment,
			boolean mapSeparation, boolean mapCohesion, float minValue, float maxValue) {
		this.setBehaviorType("Flocker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);
			if (mapAlignment) {
				alignValue *= mappedValue;
			}
			if (mapSeparation) {
				separateValue *= mappedValue;
			}
			if (mapCohesion) {
				cohesionValue *= mappedValue;
			}
		}
		this.cummVec = this.flocker.flock2D(searchRadius, cohesionValue, separateValue, alignValue, collection,
				this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Flocking Algorithm with image color sampling override for
	 * any behavior attribute for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separateValue separate value avoids crowding neighbors (short
	 *            range repulsion). Is only enabled for whatever agents are
	 *            within the search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 * @param img
	 *            the image sample
	 * @param mapAlignment
	 *            uses image color data as multiplier for alignment value
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param mapCohesion
	 *            uses image color data as multiplier for cohesion value
	 * @param mapViewAngle
	 *            uses image color data as multiplier for view angle value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void flock2DMap(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Object> collection, boolean drawSearchConnectivity, PImage img, boolean mapAlignment,
			boolean mapSeparation, boolean mapCohesion, boolean mapViewAngle, float minValue, float maxValue) {
		this.setBehaviorType("Flocker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);
			if (mapAlignment) {
				alignValue *= mappedValue;
			}
			if (mapSeparation) {
				separateValue *= mappedValue;
			}
			if (mapCohesion) {
				cohesionValue *= mappedValue;
			}
			if (mapViewAngle) {
				viewAngle *= mappedValue;
			}
		}
		this.cummVec = this.flocker.flock2D(searchRadius, cohesionValue, separateValue, alignValue, viewAngle,
				this.getSpeed(), collection, this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 3D Flocking Algorithm for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separate value avoids crowding neighbors (short range
	 *            repulsion). Is only enabled for whatever agents are within the
	 *            search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees.
	 * @param collection
	 *            list of culebra.objects.Object.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius.
	 */
	public void flock(float searchRadius, float cohesionValue, float separateValue, float alignValue, float viewAngle,
			List<Object> collection, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock(searchRadius, cohesionValue, separateValue, alignValue, viewAngle, collection,
				this.getLoc(), drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 3D Flocking Algorithm for use with culebra.objects.Object type
	 * with optimized search via OcTree from Toxiclibs
	 * 
	 * @param cohesionValue
	 *            cohesion value steers towards average positions of neighbors
	 *            (long range attraction). Is only enabled for whatever agents
	 *            are within the search radius.
	 * @param separateValue
	 *            separate value avoids crowding neighbors (short range
	 *            repulsion). Is only enabled for whatever agents are within the
	 *            search radius.
	 * @param alignValue
	 *            align value steers towards average heading of neighbors. Is
	 *            only enabled for whatever agents are within the search radius.
	 * @param viewAngle
	 *            allowable vision angle in degrees.
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void flock(float searchRadius, float cohesionValue, float separateValue, float alignValue, float viewAngle,
			List<Object> collection, Octree ocTree, List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = this.flocker.flock(searchRadius, cohesionValue, separateValue, alignValue, viewAngle, collection,
				this.getLoc(), ocTree, treeList, drawSearchConnectivity);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Alignment Behavior steers towards average heading of neighbors for use
	 * with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param alignValue
	 *            steers towards average heading of neighbors. Is only enabled
	 *            for whatever agents are within the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 */
	public void align(float searchRadius, float alignValue, List<Object> collection) {
		this.cummVec = flocker.align(searchRadius, alignValue, collection, this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 2D Alignment Behavior steers towards average heading of
	 * neighbors, uses Quad data, for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param alignValue
	 *            steers towards average heading of neighbors. Is only enabled
	 *            for whatever agents are within the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param qtree
	 *            use culebra.data.Data class to generate the quadtree - see
	 *            example files
	 * @param qtreeData
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void alignOptimized_2D(float searchRadius, float alignValue, List<Object> collection, QuadTree qtree,
			List<Vec2D> qtreeData, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedBehaviorsOptimized_2D(searchRadius, 0.0f, 0.0f, alignValue, collection,
				this.getLoc(), qtree, qtreeData, drawSearchConnectivity, false, true, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 3D Alignment Behavior steers towards average heading of
	 * neighbors, uses Octree data, for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param alignValue
	 *            steers towards average heading of neighbors. Is only enabled
	 *            for whatever agents are within the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void alignOptimized_3D(float searchRadius, float alignValue, List<Object> collection, Octree ocTree,
			List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedBehaviorsOptimized_3D(searchRadius, 0.0f, 0.0f, alignValue, 0.0f, collection,
				this.getLoc(), ocTree, treeList, drawSearchConnectivity, false, true, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Separation Behavior for use with culebra.objects.Object type - avoids
	 * crowding neighbors (short range repulsion)
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param separateValue
	 *            avoids crowding neighbors (short range repulsion). Is only
	 *            enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 */
	public void separate(float searchRadius, float separateValue, List<Object> collection) {
		this.cummVec = flocker.separate(searchRadius, separateValue, collection, this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Separation Behavior II for use with culebra.objects.Object type - avoids
	 * crowding neighbors (short range repulsion)
	 * 
	 * @param maxSeparation
	 *            maxDistance threshold to enable separate
	 * @param collection
	 *            list of other culebra.objects.Object
	 */
	public void separate(float maxSeparation, List<Object> collection) {
		this.cummVec = flocker.separate(collection, this.getLoc(), maxSeparation);
		if (this.cummVec.magSq() > 0) {
			// Steering = Desired - Velocity
			this.cummVec.normalize();
			this.cummVec.mult(this.getMaxSpeed());
			this.cummVec.sub(this.getSpeed());
			this.cummVec.limit(this.getMaxForce());
		}
		this.cummVec.mult(3);
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 2D Separation Behavior for use with culebra.objects.Object type
	 * - avoids crowding neighbors (short range repulsion)
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param separateValue
	 *            avoids crowding neighbors (short range repulsion). Is only
	 *            enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param qtree
	 *            use culebra.data.Data class to generate the quadtree - see
	 *            example files
	 * @param qtreeData
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void separateOptimized_2D(float searchRadius, float separateValue, List<Object> collection, QuadTree qtree,
			List<Vec2D> qtreeData, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedBehaviorsOptimized_2D(searchRadius, 0.0f, separateValue, 0.0f, collection,
				this.getLoc(), qtree, qtreeData, drawSearchConnectivity, true, false, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 3D Separation Behavior for use with culebra.objects.Object type
	 * - avoids crowding neighbors (short range repulsion)
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param separateValue
	 *            avoids crowding neighbors (short range repulsion). Is only
	 *            enabled for whatever agents are within the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void separateOptimized_3D(float searchRadius, float separateValue, List<Object> collection, Octree ocTree,
			List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedBehaviorsOptimized_3D(searchRadius, 0.0f, separateValue, 0.0f, 0.0f, collection,
				this.getLoc(), ocTree, treeList, drawSearchConnectivity, true, false, false);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * 2D Separation Algorithm with image color sampling override for any
	 * behavior attribute for use with culebra.objects.Object type
	 * 
	 * @param maxSeparation
	 *            maxDistance threshold to enable separate
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param img
	 *            the image sample
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 */
	public void separate2DMap(float maxSeparation, List<Object> collection, PImage img, Boolean mapSeparation) {
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			if (mapSeparation) {
				maxSeparation *= val;
			}
		}
		this.cummVec = flocker.separate(collection, this.getLoc(), maxSeparation);
		if (this.cummVec.magSq() > 0) {
			this.cummVec.normalize();
			this.cummVec.mult(this.getMaxSpeed());
			this.cummVec.sub(this.getSpeed());
			this.cummVec.limit(this.getMaxForce());

		}
		this.cummVec.mult(3);
		applyForce(this.cummVec);
	}

	/**
	 * Overloaded 2D Separation Algorithm with image color sampling override for
	 * any behavior attribute with color value remapping for use with
	 * culebra.objects.Object type
	 * 
	 * @param maxSeparation
	 *            maxDistance threshold to enable separate
	 * @param collection
	 *            list of culebra.objects.Object
	 * @param img
	 *            the image sample
	 * @param mapSeparation
	 *            uses image color data as multiplier for separation value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void separate2DMap(float maxSeparation, List<Object> collection, PImage img, boolean mapSeparation,
			float minValue, float maxValue) {
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);
			if (mapSeparation) {
				maxSeparation *= mappedValue;
			}
		}
		this.cummVec = flocker.separate(collection, this.getLoc(), maxSeparation);
		if (this.cummVec.magSq() > 0) {
			this.cummVec.normalize();
			this.cummVec.mult(this.getMaxSpeed());
			this.cummVec.sub(this.getSpeed());
			this.cummVec.limit(this.getMaxForce());
		}
		this.cummVec.mult(3);
		applyForce(this.cummVec);
	}

	/**
	 * Cohesion Behavior steers towards average positions of neighbors (long
	 * range attraction) for use with culebra.objects.Object type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            steers towards average positions of neighbors (long range
	 *            attraction). Is only enabled for whatever agents are within
	 *            the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 */
	public void cohesion(float searchRadius, float cohesionValue, List<Object> collection) {
		this.cummVec = flocker.cohesion(searchRadius, cohesionValue, collection, this.getLoc());
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 2D Cohesion Behavior steers towards average positions of
	 * neighbors (long range attraction) for use with culebra.objects.Object
	 * type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            steers towards average positions of neighbors (long range
	 *            attraction). Is only enabled for whatever agents are within
	 *            the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param qtree
	 *            use culebra.data.Data class to generate the quadtree - see
	 *            example files
	 * @param qtreeData
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void cohesionOptimized_2D(float searchRadius, float cohesionValue, List<Object> collection, QuadTree qtree,
			List<Vec2D> qtreeData, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedBehaviorsOptimized_2D(searchRadius, cohesionValue, 0.0f, 0.0f, collection,
				this.getLoc(), qtree, qtreeData, drawSearchConnectivity, false, false, true);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	/**
	 * Optimized 3D Cohesion Behavior steers towards average positions of
	 * neighbors (long range attraction) for use with culebra.objects.Object
	 * type
	 * 
	 * @param searchRadius
	 *            distance each culebra.objects.Object can see
	 * @param cohesionValue
	 *            steers towards average positions of neighbors (long range
	 *            attraction). Is only enabled for whatever agents are within
	 *            the search radius.
	 * @param collection
	 *            list of other culebra.objects.Object
	 * @param ocTree
	 *            use culebra.data.Data class to generate the octree - see
	 *            example files
	 * @param treeList
	 *            current list of nodes,use culebra.data.Data class to generate
	 *            the tree - see example files.
	 * @param drawSearchConnectivity
	 *            network visualizing search radius
	 */
	public void cohesionOptimized_3D(float searchRadius, float cohesionValue, List<Object> collection, Octree ocTree,
			List<Vec3D> treeList, boolean drawSearchConnectivity) {
		this.setBehaviorType("Flocker");
		this.cummVec = flocker.isolatedBehaviorsOptimized_3D(searchRadius, cohesionValue, 0.0f, 0.0f, 0.0f, collection,
				this.getLoc(), ocTree, treeList, drawSearchConnectivity, false, false, true);
		if (drawSearchConnectivity) {
			setDrawConnections(true);
		} else {
			setDrawConnections(false);
		}
		applyForce(this.cummVec);
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------WANDER
	// CONTROLLERS----------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * 2D Wandering Algorithm - "Agent predicts its future location as a fixed
	 * distance in front of it (in the direction of its velocity), draws a
	 * circle with radius r at that location, and picks a random point along the
	 * circumference of the circle. That random point moves randomly around the
	 * circle in each frame of animation. And that random point is the vehicles
	 * target, its desired vector pointing in that direction" - Daniel Shiffman
	 * on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 */
	public void wander2D(Boolean randomize, Boolean addHeading, float change, float wanderR, float wanderD) {
		this.r = new Random();
		this.setBehaviorType("Wander");
		if (randomize) {
			change = Data.getRandomNumbers(-change, change, r);
		}
		PVector target = this.wand.wander(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), randomize,
				addHeading);
		seek(target);
	}

	/**
	 * Overloaded 2D Wander Algorithm - by default this one randmoizes the
	 * change value from -change to change and incorporates the heading 2D
	 * Wandering Algorithm - "Agent predicts its future location as a fixed
	 * distance in front of it (in the direction of its velocity), draws a
	 * circle with radius r at that location, and picks a random point along the
	 * circumference of the circle. That random point moves randomly around the
	 * circle in each frame of animation. And that random point is the vehicles
	 * target, its desired vector pointing in that direction" - Daniel Shiffman
	 * on Craig Reynolds Wandering Behavior
	 * 
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 */
	public void wander2D(float change, float wanderR, float wanderD) {
		this.setBehaviorType("Wander");
		PVector target = this.wand.wander(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), true, true);
		seek(target);
	}

	/**
	 * Mapped 2D Wandering Algorithm with image color sampling override for any
	 * behavior attribute 2D Wandering Algorithm - "Agent predicts its future
	 * location as a fixed distance in front of it (in the direction of its
	 * velocity), draws a circle with radius r at that location, and picks a
	 * random point along the circumference of the circle. That random point
	 * moves randomly around the circle in each frame of animation. And that
	 * random point is the vehicles target, its desired vector pointing in that
	 * direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param img
	 *            the image sample
	 * @param mapChange
	 *            uses image color data as multiplier for wander change value
	 * @param mapWanderR
	 *            uses image color data as multiplier for wander circle radius
	 *            value
	 * @param mapWanderD
	 *            uses image color data as multiplier for wander circle distance
	 *            value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void wander2DMap(Boolean randomize, Boolean addHeading, float change, float wanderR, float wanderD,
			PImage img, Boolean mapChange, Boolean mapWanderR, Boolean mapWanderD, float minValue, float maxValue) {
		this.r = new Random();
		this.setBehaviorType("Wander");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0.0f, 1.0f, minValue, maxValue);
			if (mapChange) {
				change *= mappedValue;
			}
			if (mapWanderR) {
				wanderR *= mappedValue;
			}
			if (mapWanderD) {
				wanderD *= mappedValue;
			}
		}
		if (randomize) {
			change = Data.getRandomNumbers(-change, change, r);
		}
		PVector target = wand.wander(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), randomize, addHeading);
		seek(target);
	}

	/**
	 * Overloaded Mapped 2D Wandering Algorithm with image color sampling
	 * override for any behavior attribute. No remapping capabilities in this
	 * method, min and max image values are fixed 2D Wandering Algorithm -
	 * "Agent predicts its future location as a fixed distance in front of it
	 * (in the direction of its velocity), draws a circle with radius r at that
	 * location, and picks a random point along the circumference of the circle.
	 * That random point moves randomly around the circle in each frame of
	 * animation. And that random point is the vehicles target, its desired
	 * vector pointing in that direction" - Daniel Shiffman on Craig Reynolds
	 * Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param img
	 *            the image sample
	 * @param mapChange
	 *            uses image color data as multiplier for wander change value
	 * @param mapWanderR
	 *            uses image color data as multiplier for wander circle radius
	 *            value
	 * @param mapWanderD
	 *            uses image color data as multiplier for wander circle distance
	 *            value
	 */
	public void wander2DMap(Boolean randomize, Boolean addHeading, float change, float wanderR, float wanderD,
			PImage img, Boolean mapChange, Boolean mapWanderR, Boolean mapWanderD) {
		this.r = new Random();
		this.setBehaviorType("Wander");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			if (mapChange) {
				change *= val;
			}
			if (mapWanderR) {
				wanderR *= val;
			}
			if (mapWanderD) {
				wanderD *= val;
			}
		}
		if (randomize) {
			change = Data.getRandomNumbers(-change, change, r);
		}
		PVector target = wand.wander(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), randomize, addHeading);
		seek(target);
	}

	/**
	 * 2D Wandering Controlled Spawn Algorithm with image color sampling for
	 * spawn location 2D Wandering Algorithm - "Agent predicts its future
	 * location as a fixed distance in front of it (in the direction of its
	 * velocity), draws a circle with radius r at that location, and picks a
	 * random point along the circumference of the circle. That random point
	 * moves randomly around the circle in each frame of animation. And that
	 * random point is the vehicles target, its desired vector pointing in that
	 * direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param img
	 *            the image sample
	 */
	public void wanderSpawn2DMap(boolean randomize, boolean addheading, float change, float wanderR, float wanderD,
			PImage img) {
		this.r = new Random();
		this.setBehaviorType("Wander");
		if (this.spawnCount == 0) {
			float spawnVal = Data.getSpawnColorValue(this.getLoc(), img);
			while (spawnVal > 0.5 && this.spawnCount == 0) {
				float x = Data.getRandomNumbers(0.0, 1400, this.r);
				float y = Data.getRandomNumbers(0.0, 750, this.r);
				this.setLoc(new PVector(x, y, 0));
				spawnVal = Data.getSpawnColorValue(this.getLoc(), img);
				if (spawnVal <= 0.5) {
					this.setCreeperTrails(new ArrayList<PVector>());
					break;
				}
			}
		}
		this.spawnCount++;
		PVector target = wand.wander(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), randomize, addheading);
		seek(target);
	}

	/**
	 * Expanded 2D Wandering Algorithm using triggers to create a "weaving" type
	 * movement. USE WITH NON CREEPER DERIVED OBJECTS, if you create your own
	 * object use this and pass the objtype below. 2D Wandering Algorithm -
	 * "Agent predicts its future location as a fixed distance in front of it
	 * (in the direction of its velocity), draws a circle with radius r at that
	 * location, and picks a random point along the circumference of the circle.
	 * That random point moves randomly around the circle in each frame of
	 * animation. And that random point is the vehicles target, its desired
	 * vector pointing in that direction" - Daniel Shiffman on Craig Reynolds
	 * Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            NON incremented change value used to get the polar
	 *            coordinates. As opposed to other wander examples this one does
	 *            not increment the theta value, we simply use whichever value
	 *            is given and use the trigger to specify which direction the
	 *            rotation will occur.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param rotationTrigger
	 *            this value is compared against each movement step. If
	 *            rotationTrigger value > iteration count then we will reverse
	 *            the change value.
	 * @param objType
	 *            to use with generic objects not derrived from
	 *            culebra.objects.Creeper. Input "Parent" for parent objects and
	 *            "Child" for child objects
	 */
	public void superWander2D(float change, float wanderR, float wanderD, float rotationTrigger, String objType) {
		String type = "primary";
		this.setBehaviorType("Wander");
		PVector target = wand.wanderExpandedGenerics(rotationTrigger, change, wanderR, wanderD, type, objType,
				this.getObjChildType(), this.getLoc(), this.getSpeed(), this.D3);
		seek(target);
	}

	/**
	 * Overloaded Expanded 2D Wandering Algorithm using triggers to create a
	 * "weaving" type movement 2D Wandering Algorithm - "Agent predicts its
	 * future location as a fixed distance in front of it (in the direction of
	 * its velocity), draws a circle with radius r at that location, and picks a
	 * random point along the circumference of the circle. That random point
	 * moves randomly around the circle in each frame of animation. And that
	 * random point is the vehicles target, its desired vector pointing in that
	 * direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            NON incremented change value used to get the polar
	 *            coordinates. As opposed to other wander examples this one does
	 *            not increment the theta value, we simply use whichever value
	 *            is given and use the trigger to specify which direction the
	 *            rotation will occur.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param rotationTrigger
	 *            this value is compared against each movement step. If
	 *            rotationTrigger value > iteration count then we will reverse
	 *            the change value.
	 */
	public void superWander2D(float change, float wanderR, float wanderD, float rotationTrigger) {
		String type = "primary";
		this.setBehaviorType("Wander");
		PVector target = wand.wanderExpanded(rotationTrigger, change, wanderR, wanderD, type, this.getObjType(),
				this.getSuperClass(), this.getObjChildType(), this.getLoc(), this.getSpeed(), this.D3);
		seek(target);
	}

	/**
	 * Expanded 3D Wandering Algorithm - Type "Primary" using triggers to create
	 * a "weaving" type movement. Wandering Algorithm - "Agent predicts its
	 * future location as a fixed distance in front of it (in the direction of
	 * its velocity), draws a circle with radius r at that location, and picks a
	 * random point along the circumference of the circle. That random point
	 * moves randomly around the circle in each frame of animation. And that
	 * random point is the vehicles target, its desired vector pointing in that
	 * direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            NON incremented change value used to get the polar
	 *            coordinates. As opposed to other wander examples this one does
	 *            not increment the theta value, we simply use whichever value
	 *            is given and use the trigger to specify which direction the
	 *            rotation will occur.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param rotationTrigger
	 *            this value is compared against each movement step. If
	 *            rotationTrigger value > iteration count then we will reverse
	 *            the change value.
	 */
	public void wander3D(float change, float wanderR, float wanderD, float rotationTrigger) {
		String type = "primary";
		this.setBehaviorType("Wander");
		PVector target = wand.wanderExpanded(rotationTrigger, change, wanderR, wanderD, type, this.getObjType(),
				this.getSuperClass(), this.getObjChildType(), this.getLoc(), this.getSpeed(), this.D3);
		seek(target);
	}

	/**
	 * Expanded 3D Wandering Algorithm - Type "B" using triggers to create a
	 * "weaving" type movement. Type B uses a different assortment of Heading
	 * variations creating a differnt type of behavior. These variations are
	 * best used with tracking behaviors. Wandering Algorithm - "Agent predicts
	 * its future location as a fixed distance in front of it (in the direction
	 * of its velocity), draws a circle with radius r at that location, and
	 * picks a random point along the circumference of the circle. That random
	 * point moves randomly around the circle in each frame of animation. And
	 * that random point is the vehicles target, its desired vector pointing in
	 * that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            NON incremented change value used to get the polar
	 *            coordinates. As opposed to other wander examples this one does
	 *            not increment the theta value, we simply use whichever value
	 *            is given and use the trigger to specify which direction the
	 *            rotation will occur.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param rotationTrigger
	 *            this value is compared against each movement step. If
	 *            rotationTrigger value > iteration count then we will reverse
	 *            the change value.
	 */
	public void wander3D_subA(float change, float wanderR, float wanderD, float rotationTrigger) {
		String type = "sub_A";
		this.setBehaviorType("Wander");
		PVector target = wand.wanderExpanded(rotationTrigger, change, wanderR, wanderD, type, this.getObjType(),
				this.getSuperClass(), this.getObjChildType(), this.getLoc(), this.getSpeed(), this.D3);
		seek(target);
	}

	/**
	 * Expanded 3D Wandering Algorithm - Type "C" using triggers to create a
	 * "weaving" type movement. Type B uses a different assortment of Heading
	 * variations creating a differnt type of behavior. These variations are
	 * best used with tracking behaviors. Wandering Algorithm - "Agent predicts
	 * its future location as a fixed distance in front of it (in the direction
	 * of its velocity), draws a circle with radius r at that location, and
	 * picks a random point along the circumference of the circle. That random
	 * point moves randomly around the circle in each frame of animation. And
	 * that random point is the vehicles target, its desired vector pointing in
	 * that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param randomize
	 *            if true then the change value will be randomly selected from
	 *            -change value to change value each frame
	 * @param addHeading
	 *            if true adds the heading to the wandertheta
	 * @param change
	 *            NON incremented change value used to get the polar
	 *            coordinates. As opposed to other wander examples this one does
	 *            not increment the theta value, we simply use whichever value
	 *            is given and use the trigger to specify which direction the
	 *            rotation will occur.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 * @param rotationTrigger
	 *            this value is compared against each movement step. If
	 *            rotationTrigger value > iteration count then we will reverse
	 *            the change value.
	 */
	public void wander3D_subB(float change, float wanderR, float wanderD, float rotationTrigger) {
		String type = "sub_B";
		this.setBehaviorType("Wander");
		PVector target = wand.wanderExpanded(rotationTrigger, change, wanderR, wanderD, type, this.getObjType(),
				this.getSuperClass(), this.getObjChildType(), this.getLoc(), this.getSpeed(), this.D3);
		seek(target);
	}

	/**
	 * 3D Wandering Algorithm - Type "MOD" uses no Z value These variations are
	 * best used with tracking behaviors. Wandering Algorithm - "Agent predicts
	 * its future location as a fixed distance in front of it (in the direction
	 * of its velocity), draws a circle with radius r at that location, and
	 * picks a random point along the circumference of the circle. That random
	 * point moves randomly around the circle in each frame of animation. And
	 * that random point is the vehicles target, its desired vector pointing in
	 * that direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 */
	public void wander3D_Mod(float change, float wanderR, float wanderD) {
		this.setBehaviorType("Wander");
		String type = "mod_B";
		boolean randomizeIt = true;
		PVector target = wand.wander3D(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), type, randomizeIt);
		applyForce(target);
	}

	/**
	 * 3D Wandering Algorithm - Type "MOD2" uses randomized sin and cos values
	 * with the wandertheta for the Z value These variations are best used with
	 * tracking behaviors. Wandering Algorithm - "Agent predicts its future
	 * location as a fixed distance in front of it (in the direction of its
	 * velocity), draws a circle with radius r at that location, and picks a
	 * random point along the circumference of the circle. That random point
	 * moves randomly around the circle in each frame of animation. And that
	 * random point is the vehicles target, its desired vector pointing in that
	 * direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 */
	public void wander3D_Mod2(float change, float wanderR, float wanderD) {
		this.setBehaviorType("Wander");
		String type = "mod_C";
		boolean randomizeIt = true;
		PVector target = wand.wander3D(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), type, randomizeIt);
		applyForce(target);
	}

	/**
	 * 3D Wandering Algorithm - Type "MOD3" uses randomized sin and cos values
	 * with the wandertheta for all PVector components These variations are best
	 * used with tracking behaviors. Wandering Algorithm - "Agent predicts its
	 * future location as a fixed distance in front of it (in the direction of
	 * its velocity), draws a circle with radius r at that location, and picks a
	 * random point along the circumference of the circle. That random point
	 * moves randomly around the circle in each frame of animation. And that
	 * random point is the vehicles target, its desired vector pointing in that
	 * direction" - Daniel Shiffman on Craig Reynolds Wandering Behavior
	 * 
	 * @param change
	 *            the incremented change value used to get the polar
	 *            coordinates.
	 * @param wanderR
	 *            the radius for the circle
	 * @param wanderD
	 *            the distance for the wander circle, this is a projection value
	 *            in the direction of the objects speed vector.
	 */
	public void wander3D_Mod3(float change, float wanderR, float wanderD) {
		this.setBehaviorType("Wander");
		String type = "mod_D";
		boolean randomizeIt = true;
		PVector target = wand.wander3D(change, wanderR, wanderD, this.getLoc(), this.getSpeed(), type, randomizeIt);
		applyForce(target);
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------FORCE
	// CONTROLLERS-----------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * Calculates a steering force towards a target as defined by Daniel
	 * Shiffmans implementation of Craig Reynolds steering force.
	 * 
	 * @param targetVec
	 *            the target to steer towards
	 */
	public void seek(PVector targetVec) {
		PVector desired = targetVec.sub(this.getLoc());
		desired.normalize();
		desired.mult(this.getMaxSpeed());
		PVector steer = desired.sub(this.getSpeed());
		steer.limit(this.getMaxForce());
		applyForce(steer);
	}

	/**
	 * Calculates a steering force towards a target as defined by Daniel
	 * Shiffmans implementation of Craig Reynolds steering force.
	 * 
	 * @param targetVec
	 *            the target to steer towards
	 * @param amplitude
	 *            amount to multiply the steer vectory by
	 */
	public void seek(PVector targetVec, float amplitude) {
		PVector desired = targetVec.sub(this.getLoc());
		desired.normalize();
		desired.mult(this.getMaxSpeed());
		PVector steer = desired.sub(this.getSpeed());
		steer.limit(this.getMaxForce());
		steer.mult(amplitude);
		applyForce(steer);
	}

	/**
	 * Calculates a steering force towards a target as defined by Daniel
	 * Shiffmans implementation of Craig Reynolds steering force
	 * 
	 * @param targetVec
	 *            the target to steer towards
	 * @param normalize
	 *            option to normalize the desired parameter
	 */
	public void seek(PVector targetVec, boolean normalize) {
		PVector desired = targetVec.sub(this.getLoc());
		if (normalize) {
			desired.normalize();
		}
		desired.mult(this.getMaxSpeed());
		PVector steer = desired.sub(this.getSpeed());
		steer.limit(this.getMaxForce());
		steer.mult(3);
		applyForce(steer);
	}

	/**
	 * Attracts a object towards a target. Differs from Seek
	 * 
	 * @param target
	 *            target to attract towards
	 * @param threshold
	 *            if target is within this threshold then attract towards it
	 * @param attractionValue
	 *            value specifying attraction, this is the magnitude.
	 * @param maxAttraction
	 *            maximum attraction value
	 */
	public void attract(PVector target, float threshold, float attractionValue, float maxAttraction) {
		float distanceT = target.dist(this.getLoc());
		if (distanceT > 0 && distanceT < threshold) {
			PVector desired = PVector.sub(target, getLoc());
			desired.normalize();
			desired.mult(attractionValue);
			PVector steerTarget = PVector.sub(desired, this.getSpeed());
			steerTarget.setMag(maxAttraction);
			this.getSpeed().add(steerTarget);
		}
	}

	/**
	 * Attracts a object based on color values of an image. Position is defined
	 * by projecting its future position to sample color data.
	 * 
	 * @param searchProjectionDistance
	 *            amount defining projection from current position using current
	 *            speed * this value
	 * @param attractionValue
	 *            value specifying attraction, this is the magnitude.
	 * @param maxAttraction
	 *            maximum attraction value
	 * @param affectantValue
	 *            image luminance value (0-1) used as threshold to enable
	 *            attraction
	 * @param img
	 *            the image sample
	 */
	public void attractMap(float searchProjectionDistance, float attractionValue, float maxAttraction,
			float affectantValue, PImage img) {
		PVector predict = this.getSpeed().copy();
		predict.normalize();
		predict.mult(searchProjectionDistance);
		PVector nextPosPrev = PVector.add(this.getLoc(), predict);
		if (nextPosPrev.x <= img.width && nextPosPrev.x >= 0 && nextPosPrev.y >= 0 && nextPosPrev.y <= img.height) {
			float val = Data.getColorValue(nextPosPrev, img);
			if (val <= affectantValue) {
				PVector desired = PVector.sub(nextPosPrev, getLoc());
				desired.normalize();
				desired.mult(attractionValue);
				PVector steerTarget = PVector.sub(desired, this.getSpeed());
				steerTarget.setMag(maxAttraction);
				this.getSpeed().add(steerTarget);
			}
		}
	}

	/**
	 * Repels a object away from a target.
	 * 
	 * @param target
	 *            a Trump Object to repel away from
	 * @param threshold
	 *            if target is within this threshold then repel away from it
	 * @param repelValue
	 *            value specifying repulsion, this is the magnitude.
	 * @param maxRepel
	 *            maximum repulsion value
	 */
	public void repelTrump(Trump target, float threshold, float repelValue, float maxRepel) {
		float distanceT = target.getLocation().dist(getLoc());
		if (distanceT > 0 && distanceT < threshold) {
			PVector desired = PVector.sub(target.getLocation(), getLoc());
			desired.normalize();
			desired.mult(repelValue);
			PVector steerTarget = PVector.sub(desired, this.getSpeed());
			steerTarget.limit(maxRepel);
			steerTarget.mult(-1);
			this.getSpeed().add(steerTarget);
		}
	}

	/**
	 * Repels a object away from a target.
	 * 
	 * @param target
	 *            target to repel away from
	 * @param threshold
	 *            if target is within this threshold then repel away from it
	 * @param repelValue
	 *            value specifying repulsion, this is the magnitude.
	 * @param maxRepel
	 *            maximum repulsion value
	 */
	public void repel(PVector target, float threshold, float repelValue, float maxRepel) {
		float distanceT = target.dist(getLoc());
		if (distanceT > 0 && distanceT < threshold) {
			PVector desired = PVector.sub(target, getLoc());
			desired.normalize();
			desired.mult(repelValue);
			PVector steerTarget = PVector.sub(desired, this.getSpeed());
			steerTarget.limit(maxRepel);
			steerTarget.mult(-1);
			this.getSpeed().add(steerTarget);
		}
	}

	/**
	 * Repels a object based on color values of an image. Position is defined by
	 * projecting its future position to sample color data.
	 * 
	 * @param searchProjectionDistance
	 *            amount defining projection from current position using current
	 *            speed * this value
	 * @param repelValue
	 *            value specifying repulsion, this is the magnitude.
	 * @param maxRepel
	 *            maximum repulsion value
	 * @param affectantValue
	 *            image luminance value (0-1) used as threshold to enable
	 *            attraction
	 * @param img
	 *            the image sample
	 */
	public void repelMap(float searchProjectionDistance, float repelValue, float maxRepel, float affectantValue,
			PImage img) {
		PVector predict = this.getSpeed().copy();
		predict.normalize();
		predict.mult(searchProjectionDistance);
		PVector nextPosPrev = PVector.add(this.getLoc(), predict);
		if (nextPosPrev.x <= img.width && nextPosPrev.x >= 0 && nextPosPrev.y >= 0 && nextPosPrev.y <= img.height) {
			float val = Data.getColorValue(nextPosPrev, img);
			if (val < affectantValue) {
				PVector desired = PVector.sub(nextPosPrev, this.getLoc());
				desired.normalize();
				desired.mult(repelValue);
				PVector steerTarget = PVector.sub(desired, this.getSpeed());
				steerTarget.limit(maxRepel);
				steerTarget.mult(-1);
				this.getAcc().add(steerTarget);
			}
		}
	}

	/**
	 * Applies the force vector to the acceleration and adds it to the current
	 * speed.
	 * 
	 * @param force
	 *            vector to add to acceleration
	 */
	public void applyForce(PVector force) {
		this.getAcc().add(force);
		this.getSpeed().add(this.getAcc());
		this.getSpeed().limit(this.getMaxSpeed());
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------TRACKING
	// CONTROLLERS--------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * Path Following Algorithm - Requires Path class defined by Daniel Shiffman
	 * - see example files
	 * 
	 * @param pathList
	 *            list of Path Objects
	 * @param pathThreshold
	 *            distance threshold enabling agents to see paths
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param pathRadius
	 *            the radius of the path
	 */
	public void pathFollower(ArrayList<Path> pathList, float pathThreshold, float projectionDistance,
			float pathRadius) {
		this.setBehaviorType("Tracker");
		PVector target = tracker.pathFollow(pathList, pathThreshold, projectionDistance, pathRadius, this.getSpeed(),
				this.getLoc(), this.getD3());
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Single Shape Following Algorithm - Requires list of PVectors defining a
	 * single "path"
	 * 
	 * @param shapePtList
	 *            list of PVectors defining a single shape
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shape
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shape
	 */
	public void shapeTracker(ArrayList<PVector> shapePtList, float shapeThreshold, float projectionDistance,
			float shapeRadius) {
		this.setBehaviorType("Tracker");
		PVector target = tracker.shapeFollow(shapePtList, shapeThreshold, projectionDistance, shapeRadius,
				this.getSpeed(), this.getLoc(), this.getD3());
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Multi Shape Following Algorithm - Requires list of Arraylist of PVectors
	 * defining a each shapes points. - see example files
	 * 
	 * @param multiShapeList
	 *            list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shapes
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shapes
	 */
	public void multiShapeTracker(List<ArrayList<PVector>> multiShapeList, float shapeThreshold,
			float projectionDistance, float shapeRadius) {
		this.setBehaviorType("Tracker");
		PVector target = tracker.multiShapeFollow(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
				this.getSpeed(), this.getLoc(), this.getD3());
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Multi Shape Following Algorithm with image color sampling override for
	 * any shape attributes - Requires list of Arraylist of PVectors defining a
	 * each shapes points. - see example files
	 * 
	 * @param multiShapeList
	 *            list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shapes
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shapes
	 * @param img
	 *            the image sample
	 * @param mapRadii
	 *            uses image color data as multiplier for mapRadii value
	 * @param mapScalarProjects
	 *            uses image color data as multiplier for mapScalarProjects
	 *            value
	 * @param mapDistanceThreshold
	 *            uses image color data as multiplier for mapDistanceThreshold
	 *            value
	 */
	public void multiShapeTrackerMap(List<ArrayList<PVector>> multiShapeList, float shapeThreshold,
			float projectionDistance, float shapeRadius, PImage img, boolean mapRadii, boolean mapScalarProjects,
			boolean mapDistanceThreshold) {
		this.setBehaviorType("Tracker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			if (mapRadii) {
				shapeRadius *= val;
			}
			if (mapScalarProjects) {
				projectionDistance *= val;
			}
			if (mapDistanceThreshold) {
				shapeThreshold *= val;
			}
		}
		PVector target = tracker.multiShapeFollow(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
				this.getSpeed(), this.getLoc(), this.getD3());
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Overloaded Multi Shape Following Algorithm with image color sampling
	 * override for any shape attributes and remaping of color values - Requires
	 * list of Arraylist of PVectors defining a each shapes points. - see
	 * example files
	 * 
	 * @param multiShapeList
	 *            list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shapes
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shapes
	 * @param img
	 *            the image sample
	 * @param mapRadii
	 *            uses image color data as multiplier for mapRadii value
	 * @param mapScalarProjects
	 *            uses image color data as multiplier for mapScalarProjects
	 *            value
	 * @param mapDistanceThreshold
	 *            uses image color data as multiplier for mapDistanceThreshold
	 *            value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void multiShapeTrackerMap(List<ArrayList<PVector>> multiShapeList, float shapeThreshold,
			float projectionDistance, float shapeRadius, PImage img, Boolean mapRadii, Boolean mapScalarProjects,
			Boolean mapDistanceThreshold, float minValue, float maxValue) {
		this.setBehaviorType("Tracker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);

			if (mapRadii) {
				shapeRadius *= mappedValue;
			}
			if (mapScalarProjects) {
				projectionDistance *= mappedValue;
			}
			if (mapDistanceThreshold) {
				shapeThreshold *= mappedValue;
			}
		}
		PVector target = tracker.multiShapeFollow(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
				this.getSpeed(), this.getLoc(), this.getD3());
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * MultiShape Following Algorithm capable of spawning children - see example
	 * files
	 * 
	 * @param multiShapeList
	 *            list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shapes
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shapes
	 * @param triggerBabies
	 *            if true agent is now allowed to spawn any babies stored
	 * @param maxChildren
	 *            the max number of children each agent can create
	 * @param instanceable
	 *            if the child is instanceable it can reproduce. Only objects
	 *            which inherit from the culebra.objects.Object class are
	 *            instanceable. Child objects cannot produce more children
	 * @param childList
	 *            list of stored children to spawn next. use (current
	 *            object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList
	 *            list of values used to alter types of children. use (current
	 *            object).behaviors.getChildSpawnType() to get it.
	 */
	public void multiShapeTrackerBabyMaker(List<ArrayList<PVector>> multiShapeList, float shapeThreshold,
			float projectionDistance, float shapeRadius, boolean triggerBabies, int maxChildren, boolean instanceable,
			ArrayList<PVector> childList, ArrayList childTypeList) {
		this.setBehaviorType("Tracker");
		PVector target;
		if ((this.getObjType() != "culebra.objects.BabyCreeper" && this.getObjType() != "culebra.objects.BabySeeker")
				&& (this.getSuperClass() != "culebra.objects.BabyCreeper"
						&& this.getSuperClass() != "culebra.objects.BabySeeker")) {
			target = tracker.multiShapeFollowBabyMaker(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, instanceable, childList, childTypeList,
					this.getD3());
		} else {
			target = tracker.multiShapeFollowBabyMaker(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, false, childList, childTypeList,
					this.getD3());
		}
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * MultiShape Following Algorithm capable of spawning children - see example
	 * files
	 * 
	 * @param multiShapeList
	 *            list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shapes
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shapes
	 * @param triggerBabies
	 *            if true agent is now allowed to spawn any babies stored
	 * @param maxChildren
	 *            the max number of children each agent can create
	 * @param instanceable
	 *            if the child is instanceable it can reproduce. Only objects
	 *            which inherit from the culebra.objects.Object class are
	 *            instanceable. Child objects cannot produce more children
	 * @param objTypeOverride
	 *            input type to override objtype in even of custom object. Use
	 *            "Parent" as the string override for objects you want to be
	 *            able to spawn children
	 * @param childList
	 *            list of stored children to spawn next. use (current
	 *            object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList
	 *            list of values used to alter types of children. use (current
	 *            object).behaviors.getChildSpawnType() to get it.
	 */
	public void multiShapeTrackerBabyMaker(List<ArrayList<PVector>> multiShapeList, float shapeThreshold,
			float projectionDistance, float shapeRadius, boolean triggerBabies, int maxChildren, boolean instanceable,
			String objTypeOverride, ArrayList<PVector> childList, ArrayList childTypeList) {
		this.setBehaviorType("Tracker");
		PVector target;
		if (objTypeOverride == "Parent") {
			target = tracker.multiShapeFollowBabyMaker(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, instanceable, childList, childTypeList,
					this.getD3());
		} else {
			target = tracker.multiShapeFollowBabyMaker(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, false, childList, childTypeList,
					this.getD3());
		}
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * MultiShape Following Algorithm capable of spawning children with image
	 * color sampling override for any shape attributes and remaping of color
	 * values - see example files
	 * 
	 * @param multiShapeList
	 *            list of Arraylists of PVectors defining each shapes points
	 * @param shapeThreshold
	 *            distance threshold enabling agents to see shapes
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param shapeRadius
	 *            the radius of the shapes
	 * @param triggerBabies
	 *            if true agent is now allowed to spawn any babies stored
	 * @param maxChildren
	 *            the max number of children each agent can create
	 * @param instanceable
	 *            if the child is instanceable it can reproduce. Only objects
	 *            which inherit from the culebra.objects.Object class are
	 *            instanceable. Child objects cannot produce more children
	 * @param childList
	 *            list of stored children to spawn next. use (current
	 *            object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList
	 *            list of values used to alter types of children. use (current
	 *            object).behaviors.getChildSpawnType() to get it.
	 * @param img
	 *            the image sample
	 * @param mapRadii
	 *            uses image color data as multiplier for mapRadii value
	 * @param mapScalarProjects
	 *            uses image color data as multiplier for mapScalarProjects
	 *            value
	 * @param mapDistanceThreshold
	 *            uses image color data as multiplier for mapDistanceThreshold
	 *            value
	 * @param minValue
	 *            minimum value to remap color data
	 * @param maxValue
	 *            maximum value to remap color data
	 */
	public void multiShapeTrackerMapBabyMaker(List<ArrayList<PVector>> multiShapeList, float shapeThreshold,
			float projectionDistance, float shapeRadius, boolean triggerBabies, int maxChildren, boolean instanceable,
			ArrayList<PVector> childList, ArrayList childTypeList, PImage img, Boolean mapRadii,
			Boolean mapScalarProjects, Boolean mapDistanceThreshold, float minValue, float maxValue) {

		this.setBehaviorType("Tracker");
		if (this.getLoc().x <= img.width && this.getLoc().x >= 0 && this.getLoc().y >= 0
				&& this.getLoc().y <= img.height) {
			float val = Data.getColorValue(this.getLoc(), img);
			float mappedValue = Data.map(val, 0, 1.0f, minValue, maxValue);

			if (mapRadii) {
				shapeRadius *= mappedValue;
			}
			if (mapScalarProjects) {
				projectionDistance *= mappedValue;
			}
			if (mapDistanceThreshold) {
				shapeThreshold *= mappedValue;
			}
		}
		PVector target;
		if ((this.getObjType() != "culebra.objects.BabyCreeper" && this.getObjType() != "culebra.objects.BabySeeker")
				&& (this.getSuperClass() != "culebra.objects.BabyCreeper"
						&& this.getSuperClass() != "culebra.objects.BabySeeker")) {
			target = tracker.multiShapeFollowBabyMaker(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, instanceable, childList, childTypeList,
					this.getD3());
		} else {
			target = tracker.multiShapeFollowBabyMaker(multiShapeList, shapeThreshold, projectionDistance, shapeRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, false, childList, childTypeList,
					this.getD3());
		}
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Path Following Algorithm capable of spawning children - Requires Path
	 * class defined by Daniel Shiffman - see example files
	 * 
	 * @param pathList
	 *            list of Path Objects
	 * @param pathThreshold
	 *            distance threshold enabling agents to see paths
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param pathRadius
	 *            the radius of the path
	 * @param triggerBabies
	 *            if true agent is now allowed to spawn any babies stored
	 * @param maxChildren
	 *            the max number of children each agent can create
	 * @param instanceable
	 *            if the child is instanceable it can reproduce. Only objects
	 *            which inherit from the culebra.objects.Object class are
	 *            instanceable. Child objects cannot produce more children
	 * @param childList
	 *            list of stored children to spawn next. use (current
	 *            object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList
	 *            list of values used to alter types of children. use (current
	 *            object).behaviors.getChildSpawnType() to get it.
	 */
	public void pathFollowerBabyMaker(ArrayList<Path> pathList, float pathThreshold, float projectionDistance,
			float pathRadius, boolean triggerBabies, int maxChildren, boolean instanceable,
			ArrayList<PVector> childList, ArrayList childTypeList) {
		this.setBehaviorType("Tracker");
		PVector target;
		if ((this.getObjType() != "culebra.objects.BabyCreeper" && this.getObjType() != "culebra.objects.BabySeeker")
				&& (this.getSuperClass() != "culebra.objects.BabyCreeper"
						&& this.getSuperClass() != "culebra.objects.BabySeeker")) {
			target = tracker.pathFollowBaby(pathList, pathThreshold, projectionDistance, pathRadius, this.getSpeed(),
					this.getLoc(), triggerBabies, maxChildren, instanceable, childList, childTypeList, this.getD3());
		} else {
			target = tracker.pathFollowBaby(pathList, pathThreshold, projectionDistance, pathRadius, this.getSpeed(),
					this.getLoc(), triggerBabies, maxChildren, false, childList, childTypeList, this.getD3());
		}
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Other Object Trails Following Algorithm - Meant for Seeker or sub Seeker
	 * types of objects. These objects will chase the trails of other objects -
	 * see example files
	 * 
	 * @param trailsList
	 *            list of Path Objects
	 * @param trailThreshold
	 *            distance threshold enabling agents to see paths
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param trailRadius
	 *            the radius of the trails
	 */
	public void trailFollower(List<ArrayList> trailsList, float trailThreshold, float projectionDistance,
			float trailRadius) {
		this.setBehaviorType("Seeker");
		PVector target = tracker.trailFollow(trailsList, trailThreshold, projectionDistance, trailRadius,
				this.getSpeed(), this.getLoc(), this.getD3());
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	/**
	 * Other Object Trails Following Algorithm capable of spawning children -
	 * Meant for Seeker or sub Seeker types of objects. These objects will chase
	 * the trails of other objects
	 * 
	 * @param trailsList
	 *            list of Path Objects
	 * @param trailsThreshold
	 *            distance threshold enabling agents to see paths
	 * @param projectionDistance
	 *            Reynolds "point ahead on the path" to seek
	 * @param trailsRadius
	 *            the radius of the path
	 * @param triggerBabies
	 *            if true agent is now allowed to spawn any babies stored
	 * @param maxChildren
	 *            the max number of children each agent can create
	 * @param instanceable
	 *            if the child is instanceable it can reproduce. Only objects
	 *            which inherit from the culebra.objects.Object class are
	 *            instanceable. Child objects cannot produce more children
	 * @param childList
	 *            list of stored children to spawn next. use (current
	 *            object).behaviors.getChildStartPositions() to get them
	 * @param childTypeList
	 *            list of values used to alter types of children. use (current
	 *            object).behaviors.getChildSpawnType() to get it.
	 */
	public void trailFollowerBabyMaker(List<ArrayList> trailsList, float trailsThreshold, float projectionDistance,
			float trailsRadius, boolean triggerBabies, int maxChildren, boolean instanceable,
			ArrayList<PVector> childList, ArrayList childTypeList) {
		this.setBehaviorType("SeekTracker");
		PVector target;
		if (this.getObjType() != "culebra.objects.BabySeeker" && this.getSuperClass() != "culebra.objects.BabySeeker") {
			target = tracker.trailFollowBabyMaker(trailsList, trailsThreshold, projectionDistance, trailsRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, instanceable, childList, childTypeList,
					this.getD3());
		} else {
			target = tracker.trailFollowBabyMaker(trailsList, trailsThreshold, projectionDistance, trailsRadius,
					this.getSpeed(), this.getLoc(), triggerBabies, maxChildren, false, childList, childTypeList,
					this.getD3());
		}
		if (target.magSq() != 0.0f) {
			seek(target);
		}
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------NOISE
	// CONTROLLERS-----------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * 2D/3D Improved Perlin Noise
	 * 
	 * @param scale
	 *            overall scale of the noise
	 * @param strength
	 *            overall strength of the noise
	 * @param multiplier
	 *            value adds a jitter type of movement
	 * @param velocity
	 *            multiplier value for velocity
	 */
	public void perlin(float scale, float strength, float multiplier, float velocity) {
		this.setBehaviorType("Perlin");
		PVector dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		applyForce(dir);
	}

	/**
	 * 2D Improved Perlin Noise with image color sampling override for any
	 * behavior attribute
	 * 
	 * @param scale
	 *            overall scale of the noise
	 * @param strength
	 *            overall strength of the noise
	 * @param multiplier
	 *            value adds a jitter type of movement
	 * @param velocity
	 *            multiplier value for velocity
	 * @param img
	 *            the image sample
	 * @param mapScale
	 *            uses image color data as multiplier for scale value
	 * @param mapStrength
	 *            uses image color data as multiplier for strength value
	 * @param mapMultiplier
	 *            uses image color data as multiplier for multiplier value
	 */
	public void perlin2DMap(float scale, float strength, float multiplier, float velocity, PImage img, boolean mapScale,
			boolean mapStrength, boolean mapMultiplier) {
		this.r = new Random();
		this.setBehaviorType("Perlin");
		PVector dir = pnoise.perlin2D(scale, strength, multiplier, velocity, this.getLoc(), img, mapScale, mapStrength,
				mapMultiplier);
		applyForce(dir);
	}

	/**
	 * 2D/3D Modified Improved Perlin Noise Type A. Randomized remapped Scale
	 * value adjustments.
	 * 
	 * @param scale
	 *            overall scale of the noise
	 * @param strength
	 *            overall strength of the noise
	 * @param multiplier
	 *            value adds a jitter type of movement
	 * @param velocity
	 *            multiplier value for velocity
	 * @param modMultiplier
	 *            multiplier value multiplied to the scale
	 * @param modScaleDivider
	 *            number to divide the result of the scale * modMultiplier
	 */
	public void noiseModified_A(float scale, float strength, float multiplier, float velocity, float modMultiplier,
			float modScaleDivider) {
		this.r = new Random();
		scale = Data.getRandomNumbers(scale, scale * modMultiplier / modScaleDivider, this.r);
		PVector dir;
		if (this.getD3()) {
			dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		} else {
			dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		}
		applyForce(dir);
	}

	/**
	 * 2D/3D Modified Improved Perlin Noise Type B. Randomized remapped Scale
	 * value adjustments
	 * 
	 * @param scale
	 *            overall scale of the noise
	 * @param strength
	 *            overall strength of the noise
	 * @param multiplier
	 *            value adds a jitter type of movement
	 * @param velocity
	 *            multiplier value for velocity
	 * @param modMultiplier
	 *            multiplier value multiplied to the scale
	 */
	public void noiseModified_B(float scale, float strength, float multiplier, float velocity, float modMultiplier) {
		this.r = new Random();
		scale = Data.getRandomNumbers(-scale, scale * modMultiplier, this.r);
		PVector dir;
		if (this.getD3()) {
			dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		} else {
			dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		}
		applyForce(dir);
	}

	/**
	 * 2D/3D Modified Improved Perlin Noise Type C. Randomized remapped Strength
	 * value adjustments
	 * 
	 * @param scale
	 *            overall scale of the noise
	 * @param strength
	 *            overall strength of the noise
	 * @param multiplier
	 *            value adds a jitter type of movement
	 * @param velocity
	 *            multiplier value for velocity
	 * @param modMultiplier
	 *            multiplier value multiplied to the strength
	 */
	public void noiseModified_C(float scale, float strength, float multiplier, float velocity, float modMultiplier) {
		this.r = new Random();
		strength = Data.getRandomNumbers(strength, strength * modMultiplier, this.r);
		PVector dir;
		if (this.getD3()) {
			dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		} else {
			dir = pnoise.perlin(scale, strength, multiplier, velocity, this.getLoc(), this.getD3());
		}
		applyForce(dir);
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------MESHCRAWL
	// CONTROLLERS--------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * Mesh Crawling allows agent to move along a mesh object
	 * 
	 * @param mesh
	 *            the mesh object
	 * @param meshThreshold
	 *            min distance current position needs to be from mesh in order
	 *            to move to it.
	 * @param location
	 *            the current object location
	 * @param speed
	 *            the current object speed
	 * @param amplitude
	 *            the amount to project the current location along the current
	 *            speed to get the predicted next location
	 * @param maxChildren
	 *            the max number of children each agent can create
	 * @param triggerBabies
	 *            if true agent is now allowed to spawn any babies stored
	 */
	public void meshCrawl(Mesh mesh, float meshThreshold, PVector location, PVector speed, float amplitude,
			int maxChildren, boolean triggerBabies, boolean instanceable, ArrayList<PVector> childList,
			ArrayList childTypeList) {
		this.setBehaviorType("Crawler");
		PVector dir;
		if ((this.getObjType() != "culebra.objects.BabyCreeper" && this.getObjType() != "culebra.objects.BabySeeker")
				|| (this.getSuperClass() != "culebra.objects.BabyCreeper"
						&& this.getSuperClass() != "culebra.objects.BabySeeker")) {
			dir = crawler.meshWalk(mesh, meshThreshold, location, speed, amplitude, maxChildren, triggerBabies,
					instanceable, childList, childTypeList);
		} else {
			dir = crawler.meshWalk(mesh, meshThreshold, location, speed, amplitude, maxChildren, triggerBabies, false,
					childList, childTypeList);
		}
		applyForce(dir);
	}

	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ------------GETTERS /
	// SETTERS-----------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------
	/**
	 * Gets the object location
	 * 
	 * @return the object location
	 */
	public PVector getLoc() {
		return this.loc;
	}

	/**
	 * Sets the object location
	 * 
	 * @param loc
	 *            the desired location
	 */
	public void setLoc(PVector loc) {
		this.loc = loc;
	}

	/**
	 * Gets the object speed
	 * 
	 * @return the objects speed
	 */
	public PVector getSpeed() {
		return speed;
	}

	/**
	 * Sets the object speed
	 * 
	 * @param speed
	 *            the desired speed
	 */
	public void setSpeed(PVector speed) {
		this.speed = speed;
	}

	/**
	 * Checks if the object is instanceable
	 * 
	 * @return if it is instanceable
	 */
	public boolean isInstanceable() {
		return instanceable;
	}

	/**
	 * Sets the objects instanceable property
	 * 
	 * @param instanceable
	 *            if true then object can reproduce
	 */
	public void setInstanceable(boolean instanceable) {
		this.instanceable = instanceable;
	}

	/**
	 * Checks if the separation property is active, this can be used to parent
	 * of children objects to enable separation behavior without affecting the
	 * children behavior. So parents will separate from children objects
	 * 
	 * @return if it is or not
	 */
	public boolean isSeparateActive() {
		return this.tracker.isSeparateActive();
	}

	/**
	 * Checks if the separation property is active, this can be used to parent
	 * of children objects to enable separation behavior without affecting the
	 * children behavior. So parents will separate from children objects
	 * 
	 * @return if it is or not
	 */
	public boolean isCrawlerSeparateActive() {
		return this.crawler.isSeparateActive();
	}

	/**
	 * Resets the trigger count for multi object babymaking
	 */
	public void resetTriggerCount() {
		this.tracker.resetTriggerCount();
	}

	/**
	 * Gets the child start positions if any
	 * 
	 * @return the start positions
	 */
	public ArrayList<PVector> getChildStartPositions() {
		ArrayList<PVector> transferList = this.tracker.getChildStartPositions();
		this.tracker.resetChildStartPositions();
		return transferList;
	}

	/**
	 * Gets the child spawn types if any
	 * 
	 * @return the child spawn type
	 */
	public ArrayList<Integer> getChildSpawnType() {
		ArrayList<Integer> returnedList = this.tracker.getChildSpawnType();
		this.tracker.resetChildSpawnType();
		return returnedList;
	}

	/**
	 * Gets the child start positions if any
	 * 
	 * @return the start positions
	 */
	public ArrayList<PVector> getCrawlerChildStartPositions() {
		ArrayList<PVector> transferList = this.crawler.getChildStartPositions();
		this.crawler.resetChildStartPositions();
		return transferList;
	}

	/**
	 * Gets the child spawn types if any
	 * 
	 * @return the child spawn type
	 */
	public ArrayList<Integer> getCrawlerChildSpawnType() {
		ArrayList<Integer> returnedList = this.crawler.getChildSpawnType();
		this.crawler.resetChildSpawnType();
		return returnedList;
	}

	/**
	 * Gets the object type by using getClass().getName() method
	 * 
	 * @return the object type
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * Sets the object type, must use the getClass().getName() method
	 * 
	 * @param objType
	 *            the object type
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * Gets the super class type by using
	 * obj.getClass().getAnnotatedSuperclass().getType().getTypeName().toString(
	 * );
	 * 
	 * @return the superclass of the object as string
	 */
	public String getSuperClass() {
		return objSuperClass;
	}

	/**
	 * Sets the object superclass, must use the
	 * obj.getClass().getAnnotatedSuperclass().getType().getTypeName().toString(
	 * ) method
	 * 
	 * @param superClass
	 *            the object superclass
	 * 
	 */
	public void setSuperClass(String superClass) {
		this.objSuperClass = superClass;
	}

	/**
	 * Gets the child object type as specified string, must be either "a" or "b"
	 * 
	 * @return the child type
	 */
	public String getObjChildType() {
		return childType;
	}

	/**
	 * Sets the child object type, must be either "a" or "b"
	 * 
	 * @param objType
	 *            the child type
	 */
	public void setObjChildType(String objType) {
		this.childType = objType;
	}

	/**
	 * Gets the Dimension value, D3 means 3D
	 * 
	 * @return if we are in 3D or not
	 */
	public Boolean getD3() {
		return D3;
	}

	/**
	 * Sets the dimension type as true for 3D or false for 2D
	 * 
	 * @param d3
	 *            Do you want it in 3D?
	 */
	public void setD3(Boolean d3) {
		D3 = d3;
	}

	/**
	 * Gets the behavior type as string
	 * 
	 * @return behavior type
	 */
	public String getBehaviorType() {
		return behaviorType;
	}

	/**
	 * Sets the behavior type as string
	 * 
	 * @param behaviorType
	 *            the behavior specified
	 */
	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	/**
	 * Gets the Creeper Trails to pass to Viz class for trail drawing
	 * 
	 * @return the trails
	 */
	public ArrayList<PVector> getCreeperTrails() {
		return creeperTrails;
	}

	/**
	 * Sets the Creeper Trails
	 * 
	 * @param creeperTrails
	 *            the trails you would like to use
	 */
	public void setCreeperTrails(ArrayList<PVector> creeperTrails) {
		this.creeperTrails = creeperTrails;
	}

	/**
	 * Gets the object acceleration
	 * 
	 * @return acceleration value
	 */
	public PVector getAcc() {
		return acc;
	}

	/**
	 * Sets the object acceleration
	 * 
	 * @param acc
	 *            the desired acceleration value
	 */
	public void setAcc(PVector acc) {
		this.acc = acc;
	}

	/**
	 * Checks if we are drawing connections, used for visualizing the search
	 * radius between agents engenged in flocking behavior
	 * 
	 * @return if we are using them or not
	 */
	public Boolean getDrawConnections() {
		return this.drawConnections;
	}

	/**
	 * Do you want to see the search connections?
	 * 
	 * @param drawConnections
	 *            if you want to draw them or not
	 */
	public void setDrawConnections(Boolean drawConnections) {
		this.drawConnections = drawConnections;
	}

	/**
	 * Gets the max speed
	 * 
	 * @return the maximum speed allowed
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Sets the max speed
	 * 
	 * @param maxSpeed
	 *            the desired max speed
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Gets the max force
	 * 
	 * @return the value
	 */
	public float getMaxForce() {
		return maxForce;
	}

	/**
	 * Sets the max force
	 * 
	 * @param maxForce
	 *            the desired max force value
	 */
	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}

	/**
	 * Gets the velocity
	 * 
	 * @return the value
	 */
	public float getVelocity() {
		return velocity;
	}

	/**
	 * Sets the velocity value
	 * 
	 * @param velocity
	 *            the desired velocity value
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
}
