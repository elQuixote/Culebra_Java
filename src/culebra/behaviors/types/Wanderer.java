package culebra.behaviors.types;

import java.util.Random;

import culebra.behaviorInterfaces.WanderBehavior;
import culebra.data.Data;
import processing.core.PVector;
/**
 * Wanderer Behavior Type Class Implements Wander Behavior Interface, the base Wander behavior is from Craig Reynolds and Daniel Shiffman
 * @author elQuixote
 *
 */
public class Wanderer extends Flockers implements WanderBehavior {
	
	private Random r;
	private int stepCounter = 0;
	private float wanderThetaval;
	protected PVector loc, speed;
	
	public Wanderer() {
		this.wanderThetaval = 0.0f;
	}
	public PVector wander(float change, float wanderR, float wanderD, PVector location,
			PVector speed, boolean randomize, boolean addHeading) {
		if(!randomize){
			this.wanderThetaval = 0.0f;
		}
		wanderThetaval += change;
		PVector circleloc = speed.copy();
		circleloc.normalize();
		circleloc.mult(wanderD); 
		circleloc.add(location); 
		float h, j, k, l;
		
		PVector circleOffSet;
		double wr, wr2;
		if (addHeading) {
			h = speed.heading();
			wr = wanderR * Math.cos(wanderThetaval + h);
			wr2 = wanderR * Math.sin(wanderThetaval + h);
		} else {
			wr = wanderR * Math.cos(wanderThetaval);
			wr2 = wanderR * Math.sin(wanderThetaval);
		}
		circleOffSet = new PVector((float) wr, (float) wr2, 0.0f);
		j = 0;
		k = 0;
		l = 0;
		PVector target = circleloc.add(circleOffSet);
		return target;
	}
	public PVector wander3D(float change, float wanderR, float wanderD, PVector location,
			PVector speed, String type, boolean randomize) {
		this.loc = location;
		this.speed = speed;
		this.r = new Random();

		if (randomize) {
			float val = Data.getRandomNumbers(-change, change, this.r);
			wanderThetaval += val; 
		} else {
			wanderThetaval += change;
		}
		PVector circleLoc = new PVector(this.speed.x, this.speed.y, this.speed.z);
		circleLoc.normalize(); 
		circleLoc.mult(wanderD);
		circleLoc.add(this.loc);
		PVector circleOffSet = new PVector(0, 0, 0);
		if (type == "mod_B") {
			double x = wanderR * Math.cos(wanderThetaval);
			double y = wanderR * Math.sin(wanderThetaval);
			circleOffSet = new PVector((float) x, (float) y, 0.0f);
		} else if (type == "mod_C") {
			float val = Data.getRandomNumbers(wanderR * Math.cos(wanderThetaval), wanderR * Math.sin(wanderThetaval), this.r);
			double x = wanderR * Math.cos(wanderThetaval);
			double y = wanderR * Math.sin(wanderThetaval);
			circleOffSet = new PVector((float) x, (float) y, val);

		} else if (type == "mod_D") {
			float valX = Data.getRandomNumbers(wanderR * Math.cos(wanderThetaval), wanderR * Math.sin(wanderThetaval),
					this.r);
			float valY = Data.getRandomNumbers(wanderR * Math.cos(wanderThetaval), wanderR * Math.sin(wanderThetaval),
					this.r);
			float valZ = Data.getRandomNumbers(wanderR * Math.cos(wanderThetaval), wanderR * Math.sin(wanderThetaval),
					this.r);
			circleOffSet = new PVector(valX, valY, valZ);
		}
		PVector target = circleLoc.add(circleOffSet);
		PVector steer = target.sub(this.loc);
		steer.normalize();
		steer.mult(1);

		return steer;
	}
	public PVector wanderExpanded(float wanderTVal, float change, float wanderR, float wanderD, String type,
			String objectType,String superClass, String babyType, PVector location, PVector speed, boolean D3) {
		this.loc = location;
		this.speed = speed;
		this.r = new Random();
		
		float wandertheta = 0.0f;
		if (this.stepCounter < wanderTVal) {
			if (objectType == "culebra.objects.Creeper" || objectType == "culebra.objects.Seeker" || superClass == "culebra.objects.Creeper" || superClass == "culebra.objects.Seeker") {
				wandertheta = change;
			} else if (objectType == "culebra.objects.BabyCreeper" || objectType == "culebra.objects.BabySeeker" || superClass == "culebra.objects.BabyCreeper" || superClass == "culebra.objects.BabySeeker" ) {
				if (babyType == "b") {
					wandertheta = change;
				} else {
					wandertheta = -change;
				}
			}
			this.stepCounter++;
		} else if (this.stepCounter >= wanderTVal && this.stepCounter < wanderTVal * 2) {
			if (objectType == "culebra.objects.Creeper" || objectType == "culebra.objects.Seeker" || superClass == "culebra.objects.Creeper" || superClass == "culebra.objects.Seeker") {
				wandertheta = -change;
			} else if (objectType == "culebra.objects.BabyCreeper" || objectType == "culebra.objects.BabySeeker" || superClass == "culebra.objects.BabyCreeper" || superClass == "culebra.objects.BabySeeker" ) {
				if (babyType == "b") {
					wandertheta = -change;
				} else {
					wandertheta = change;
				}
			}
			this.stepCounter++;
		} else {
			this.stepCounter = 0;
		}
		PVector circleloc = this.speed.copy(); 
		circleloc.normalize(); 
		circleloc.mult(wanderD); 
		circleloc.add(this.loc); 
		float h, headingXY, headingYZ, headingXZ;
		PVector circleOffSet;
		if (!D3) {
			h = this.speed.heading();
			double x = wanderR * Math.cos(wandertheta + h);
			double y = wanderR * Math.sin(wandertheta + h);
			circleOffSet = new PVector((float) x, (float) y, 0);
			headingXZ = 0;
			headingYZ = 0;
			headingXY = 0;
		} else {
			headingXY = (float) Math.atan2(this.speed.y, this.speed.x);
			headingXZ = (float) Math.atan2(this.speed.z, this.speed.x);
			headingYZ = (float) Math.atan2(this.speed.y, this.speed.z);
			if (type == "sub_A") {
				double x = wanderR * Math.cos(wandertheta + headingXY);
				double y = wanderR * Math.sin(wandertheta + headingXY);
				double z = wanderR * Math.cos(wandertheta + headingXZ);
				circleOffSet = new PVector((float) x, (float) y, (float) z);
			} else if (type == "sub_B") {
				double x = wanderR * Math.cos(wandertheta + headingXY);
				double y = wanderR * Math.sin(wandertheta + headingXY);
				double z = (wanderR * Math.cos(wandertheta + headingYZ)
						+ wanderR * Math.sin(wandertheta + headingXZ) / 2);
				circleOffSet = new PVector((float) x, (float) y, (float) z);
			} else {
				float val = Data.getRandomNumbers(wanderR * Math.cos(wandertheta + (headingYZ + headingXZ)),
						wanderR * Math.sin(wandertheta + (headingYZ + headingXZ)), this.r);
				double x = wanderR * Math.cos(wandertheta + headingXY);
				double y = wanderR * Math.sin(wandertheta + headingXY);
				circleOffSet = new PVector((float) x, (float) y, val);
			}
		}
		PVector target = PVector.add(circleloc, circleOffSet);
		return target;
	}
	public PVector wanderExpandedGenerics(float wanderTVal, float change, float wanderR, float wanderD, String type,
			String objectType, String babyType, PVector location, PVector speed, boolean D3) {
		this.loc = location;
		this.speed = speed;
		this.r = new Random();
		
		float wandertheta = 0.0f;
		if (this.stepCounter < wanderTVal) {
			if (objectType == "Parent") {
				wandertheta = change;
			} else if (objectType == "Child") {
				if (babyType == "b") {
					wandertheta = change;
				} else {
					wandertheta = -change;
				}
			}
			this.stepCounter++;
		} else if (this.stepCounter >= wanderTVal && this.stepCounter < wanderTVal * 2) {
			if (objectType == "Parent") {
				wandertheta = -change;
			} else if (objectType == "Child") {
				if (babyType == "b") {
					wandertheta = -change;
				} else {
					wandertheta = change;
				}
			}
			this.stepCounter++;
		} else {
			this.stepCounter = 0;
		}
		PVector circleloc = this.speed.copy();
		circleloc.normalize(); 
		circleloc.mult(wanderD);
		circleloc.add(this.loc); 
		float h, headingXY, headingYZ, headingXZ;
		PVector circleOffSet;
		if (!D3) {
			h = this.speed.heading();
			double x = wanderR * Math.cos(wandertheta + h);
			double y = wanderR * Math.sin(wandertheta + h);
			circleOffSet = new PVector((float) x, (float) y, 0);
			headingXZ = 0;
			headingYZ = 0;
			headingXY = 0;
		} else {
			headingXY = (float) Math.atan2(this.speed.y, this.speed.x);
			headingXZ = (float) Math.atan2(this.speed.z, this.speed.x);
			headingYZ = (float) Math.atan2(this.speed.y, this.speed.z);
			if (type == "sub_A") {
				double x = wanderR * Math.cos(wandertheta + headingXY);
				double y = wanderR * Math.sin(wandertheta + headingXY);
				double z = wanderR * Math.cos(wandertheta + headingXZ);
				circleOffSet = new PVector((float) x, (float) y, (float) z);
			} else if (type == "sub_B") {
				double x = wanderR * Math.cos(wandertheta + headingXY);
				double y = wanderR * Math.sin(wandertheta + headingXY);
				double z = (wanderR * Math.cos(wandertheta + headingYZ)
						+ wanderR * Math.sin(wandertheta + headingXZ) / 2);
				circleOffSet = new PVector((float) x, (float) y, (float) z);
			} else {
				float val = Data.getRandomNumbers(wanderR * Math.cos(wandertheta + (headingYZ + headingXZ)),
						wanderR * Math.sin(wandertheta + (headingYZ + headingXZ)), this.r);
				double x = wanderR * Math.cos(wandertheta + headingXY);
				double y = wanderR * Math.sin(wandertheta + headingXY);
				circleOffSet = new PVector((float) x, (float) y, val);
			}
		}
		PVector target = PVector.add(circleloc, circleOffSet);
		return target;
	}
}
