package culebra.behaviorInterfaces;

import java.util.List;
import processing.core.PVector;
import culebra.viz.Octree;
import culebra.viz.QuadTree;
import culebra.objects.Creeper;
import culebra.objects.Object;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
/**
 * Flock Behavior Interface - All objects or controllers that want to use flocking behavior must implement this interface
 * @author elQuixote
 *
 */
public interface FlockBehavior {
	/**
	 * Method which gets the flocking search network data
	 * @return network data which visualizes the search radius between agents
	 */
	public abstract List<PVector> getNetworkData();
	/**
	 * Method which resets connecting lines visualizing flocking search radius
	 */
	public abstract void resetConnections();
	/**
	 * Overloaded 2D Flocking to use with the culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<culebra.objects.Object> collection, PVector location, Boolean drawConnectivity); 
	/**
	 * Overloaded 2D Flocking to use with the culebra.objects.Object type - this example adds an angle parameter which allows agents to see only within the angle specified
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees
	 * @param speed the object speed
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, PVector speed, List<culebra.objects.Object> collection, PVector location, Boolean drawConnectivity);
	/**
	 * Overloaded 2D Flocking Algorithm to use with the culebra.objects.Object type with optimized search via QuadTree from Toxiclibs
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param qtree use culebra.data.Data class to generate the quadTree - see example files
	 * @param qtreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public PVector flock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<culebra.objects.Object> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity); 
	/**
	 * 2D Flocking Algorithm
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param collection list of creepers
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector creepersflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue, List<Creeper> collection,PVector location, Boolean drawConnectivity);
	/**
	 * Overloaded 2D Flocking Algorithm with optimized search via QuadTree from Toxiclibs - this example adds an angle parameter which allows agents to see only within the angle specified
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees
	 * @param collection list of creepers
	 * @param location the object location
	 * @param qtree use culebra.data.Data class to generate the quadTree - see example files
	 * @param qtreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector creepersflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue, float viewAngle,PVector speed, List<Creeper> collection,PVector location, Boolean drawConnectivity);
	/**
	 * Overloaded 2D Flocking Algorithm with optimized search via QuadTree from Toxiclibs
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param collection list of creepers
	 * @param location the object location
	 * @param qtree use culebra.data.Data class to generate the quadTree - see example files
	 * @param qtreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector creepersflock2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity);
	/**
	 * 3D Flocking Algorithm 
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of creepers.
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius.
	 * @return the new position to seek
	 */
	public abstract PVector creepersflock(float searchRadius, float cohesionValue, float separateValue, float alignValue, float viewAngle, List<Creeper> collection,PVector location, Boolean drawConnectivity);
	/**
	 * Overloaded 3D Flocking Algorithm with optimized search via OcTree from Toxiclibs
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of creepers
	 * @param location the object location
	 * @param octree use culebra.data.Data class to generate the octree - see example files
	 * @param octreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector creepersflock(float searchRadius, float cohesionValue, float separateValue,float alignValue, float viewAngle, List<Creeper> collection,PVector location, Octree octree, List<Vec3D> octreeData,Boolean drawConnectivity);
	/**
	 * 3D Flocking Algorithm to be used with culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of culebra.objects.Object.
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius.
	 * @return the new position to seek
	 */
	public abstract PVector flock(float searchRadius, float cohesionValue, float separateValue, float alignValue, float viewAngle, List<culebra.objects.Object> collection,PVector location, Boolean drawConnectivity);
	/**
	 * Overloaded 3D Flocking Algorithm to be used with culebra.objects.Object type with optimized search via OcTree from Toxiclibs
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param octree use culebra.data.Data class to generate the octree - see example files
	 * @param octreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @return the new position to seek
	 */
	public abstract PVector flock(float searchRadius, float cohesionValue, float separateValue,float alignValue, float viewAngle, List<culebra.objects.Object> collection,PVector location, Octree octree, List<Vec3D> octreeData,Boolean drawConnectivity);
	/**
	 * 3D Trail Chasing Algorithm with optimized search via OcTree from Toxiclibs - Agents will chase all other agents trails. When using this algorithm in your main sketch use the overloaded move method, recommended values are move(6,100)
	 * @param tailViewAngle allowable vision angle in degrees.
	 * @param tailCohMag cohesion value steers towards average positions.  
	 * @param tailCohViewRange cohesion threshold, value within range will enable tailCohMag
	 * @param tailSepMag separation value avoids crowding on trail.
	 * @param tailSepViewRange separation threshold, value within range will enable tailSepMag
	 * @param trailsPts list of all PVectors from all trails - see example file  
	 * @param octree use culebra.data.Data class to generate the octree - see example files
	 * @param octreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param speed the object speed
	 * @param location the object location
	 * @return the new position to seek
	 */
	public abstract PVector trailFollow(float tailViewAngle, float tailCohMag, float tailCohViewRange, float tailSepMag,
			float tailSepViewRange, List<PVector> field, Octree octree, List<Vec3D> octreeData, PVector speed, PVector location);
	/**
	 * 2D/3D Trail Chasing Algorithm - Agents will chase all other agents trails. When using this algorithm in your main sketch use the overloaded move method, recommended values are move(6,100)
	 * @param tailViewAngle allowable vision angle in degrees.
	 * @param tailCohMag cohesion value steers towards average positions.  
	 * @param tailCohViewRange cohesion threshold, value within range will enable tailCohMag
	 * @param tailSepMag separation value avoids crowding on trail.
	 * @param tailSepViewRange separation threshold, value within range will enable tailSepMag
	 * @param field list of all PVectors from all trails - see example file  
	 * @param speed the object speed
	 * @param location the object location
	 * @return the new position to seek
	 */
	public abstract PVector trailFollow(float tailViewAngle, float tailCohMag, float tailCohViewRange, float tailSepMag,
			float tailSepViewRange, List<PVector> field,  PVector speed, PVector location);
	/**
	 * Separation Behavior - avoids crowding neighbors (short range repulsion)
	 * @param searchRadius distance each creeper can see
	 * @param separateValue avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param collection list of other creepers
	 * @param location the object location
	 * @return the new position to seek
	 */ 
	public abstract PVector creepersSeparate(float searchRadius, float separateValue, List<Creeper> collection, PVector location);
	/**
	 * Separation Behavior II  - avoids crowding neighbors (short range repulsion)
	 * @param maxSeparation maxDistance threshold to enable separate
	 * @param location the object location
	 * @param collection list of other creepers
	 * @return the new position to seek
	 */
	public abstract PVector creepersSeparate(List<Creeper> collection, PVector location, float maxSeparation);
	/**
	 * Alignment Behavior steers towards average heading of neighbors.
	 * @param searchRadius distance each creeper can see
	 * @param alignThreshold steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param collection list of other creepers
	 * @param location the object location
	 * @return the new position to seek
	 */
	public abstract PVector creepersAlign(float searchRadius, float alignThreshold, List<Creeper> collection, PVector location);
	/**
	 * Cohesion Behavior steers towards average positions of neighbors (long range attraction)
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param collection list of other creepers
	 * @param location the object location
	 * @return the new position to seek
	 */
	public abstract PVector creepersCohesion(float searchRadius, float cohesionValue, List<Creeper> collection, PVector location);
	/**
	 * 
	 * Provides user with the ability to implement specific 2D flocking attributes in optimized form using quadTree data.
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of creepers
	 * @param location the object location
	 * @param qtree use culebra.data.Data class to generate the qtree - see example files
	 * @param qtreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @param enableSeparation enable optimized separation behavior
	 * @param enableAlignment enable optimized alignment behavior
	 * @param enableCohesion enable optimized cohesion behavior
	 * @return the new position to seek
	 */
	public abstract PVector isolatedCreepersBehaviorsOptimized_2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<Creeper> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity, boolean enableSeparation, boolean enableAlignment, boolean enableCohesion); 
	/**
	 * 
	 * Provides user with the ability to implement specific 3D flocking attributes in optimized form using octree data.
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of creepers
	 * @param location the object location
	 * @param octree use culebra.data.Data class to generate the octree - see example files
	 * @param octreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @param enableSeparation enable optimized separation behavior
	 * @param enableAlignment enable optimized alignment behavior
	 * @param enableCohesion enable optimized cohesion behavior
	 * @return the new position to seek
	 */
	public abstract PVector isolatedCreepersBehaviorsOptimized_3D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, float viewAngle, List<Creeper> collection, PVector location, Octree octree,
			List<Vec3D> octreeData, Boolean drawSearchConnectivity, boolean enableSeparation, boolean enableAlignment, boolean enableCohesion);
	/**
	 * 
	 * Provides user with the ability to implement specific 3D flocking attributes
	 * @param searchRadius distance each creeper can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of creepers
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius
	 * @param enableSeparation enable optimized separation behavior
	 * @param enableAlignment enable optimized alignment behavior
	 * @param enableCohesion enable optimized cohesion behavior
	 * @return the new position to seek
	 */
	public abstract PVector isolatedCreeperBehaviors3D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, float viewAngle, List<Creeper> collection, PVector location, Boolean drawConnectivity,
			boolean enableSeparation, boolean enableAlignment, boolean enableCohesion); 
	/**
	 * Separation Behavior to be used with culebra.objects.Object type - avoids crowding neighbors (short range repulsion)
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param separateValue avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param collection list of other culebra.objects.Object
	 * @param location the object location
	 * @return the new position to seek
	 */ 
	public abstract PVector separate(float searchRadius, float separateValue, List<culebra.objects.Object> collection, PVector location);
	/**
	 * Separation Behavior II to be used with culebra.objects.Object type  - avoids crowding neighbors (short range repulsion)
	 * @param maxSeparation maxDistance threshold to enable separate
	 * @param location the object location
	 * @param collection list of other culebra.objects.Object
	 * @return the new position to seek
	 */
	public abstract PVector separate(List<culebra.objects.Object> collection, PVector location, float maxSeparation);
	/**
	 * Alignment Behavior steers towards average heading of neighbors to be used with culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param alignThreshold steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param collection list of other culebra.objects.Object
	 * @param location the object location
	 * @return the new position to seek
	 */
	public abstract PVector align(float searchRadius, float alignThreshold, List<culebra.objects.Object> collection, PVector location);
	/**
	 * Cohesion Behavior steers towards average positions of neighbors (long range attraction) to be used with culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param collection list of other culebra.objects.Object
	 * @param location the object location
	 * @return the new position to seek
	 */
	public abstract PVector cohesion(float searchRadius, float cohesionValue, List<culebra.objects.Object> collection, PVector location);
	/**
	 * 
	 * Provides user with the ability to implement specific 2D flocking attributes in optimized form using quadTree data for the culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param qtree use culebra.data.Data class to generate the qtree - see example files
	 * @param qtreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @param enableSeparation enable optimized separation behavior
	 * @param enableAlignment enable optimized alignment behavior
	 * @param enableCohesion enable optimized cohesion behavior
	 * @return the new position to seek
	 */
	public abstract PVector isolatedBehaviorsOptimized_2D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			List<culebra.objects.Object> collection, PVector location, QuadTree qtree, List<Vec2D> qtreeData,
			Boolean drawConnectivity, boolean enableSeparation, boolean enableAlignment, boolean enableCohesion); 
	/**
	 * 
	 * Provides user with the ability to implement specific 3D flocking attributes in optimized form using octree data for the culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param octree use culebra.data.Data class to generate the octree - see example files
	 * @param octreeData current list of nodes,use culebra.data.Data class to generate the tree - see example files.
	 * @param drawSearchConnectivity network visualizing search radius
	 * @param enableSeparation enable optimized separation behavior
	 * @param enableAlignment enable optimized alignment behavior
	 * @param enableCohesion enable optimized cohesion behavior
	 * @return the new position to seek
	 */
	public abstract PVector isolatedBehaviorsOptimized_3D(float searchRadius, float cohesionValue, float separateValue,
			float alignValue, float viewAngle, List<culebra.objects.Object> collection, PVector location, Octree octree,
			List<Vec3D> octreeData, Boolean drawSearchConnectivity, boolean enableSeparation, boolean enableAlignment, boolean enableCohesion);
	/**
	 * 
	 * Provides user with the ability to implement specific 3D flocking attributes for the culebra.objects.Object type
	 * @param searchRadius distance each culebra.objects.Object can see
	 * @param cohesionValue cohesion value steers towards average positions of neighbors (long range attraction). Is only enabled for whatever agents are within the search radius.
	 * @param separateValue separate value avoids crowding neighbors (short range repulsion). Is only enabled for whatever agents are within the search radius.
	 * @param alignValue align value steers towards average heading of neighbors. Is only enabled for whatever agents are within the search radius.
	 * @param viewAngle allowable vision angle in degrees.
	 * @param collection list of culebra.objects.Object
	 * @param location the object location
	 * @param drawSearchConnectivity network visualizing search radius
	 * @param enableSeparation enable optimized separation behavior
	 * @param enableAlignment enable optimized alignment behavior
	 * @param enableCohesion enable optimized cohesion behavior
	 * @return the new position to seek
	 */
	public abstract PVector isolatedBehaviors3D(float searchRadius, float cohesionValue, float separateValue, float alignValue,
			float viewAngle, List<Object> collection, PVector location, Boolean drawSearchConnectivity,
			boolean enableSeparation, boolean enableAlignment, boolean enableCohesion);
	/**
	 * Converts the view angle to radians
	 */
	public abstract void getAngle();
	/**
	 * Computes the trail Cohesion vector
	 */
	public abstract void trailCohesion();
	/**
	 * Computes the trail separation vector
	 */
	public abstract void trailSeparate();
	/**
	 * Computes the separation vector
	 */
	public abstract void separateMethod();
	/**
	 * Computes the alignment vector
	 */
	public abstract void alignMethod();
	/**
	 * Computes the cohesion method
	 */
	public abstract void cohesionMethod(); 
}
