package culebra.behaviors.types;

import java.util.Random;

import culebra.behaviorInterfaces.NoiseBehavior;
import culebra.data.Data;
import processing.core.*;
/**
 * Noise Behavior Type Class Implements Noise Behavior Interface
 * @author elQuixote
 *
 */
public class Noise implements NoiseBehavior {

	private float multiplier;
	private float scale, strength, velocity, newMap;
	protected boolean mapPS, mapPSC, mapPM;
	
	private Random r;
	private PImage img;
	private PVector loc;
	
	private PVector multSpeed = new PVector();
	private PVector dir = new PVector();

	public PVector perlin2D(float scale, float strength, float multiplier, float velocity, PVector location, PImage img, Boolean mapScale, Boolean mapStrength, Boolean mapMultiplier) {
		this.r = new Random();
		this.multiplier = multiplier;
		this.loc = location;
		this.img = img;
		this.scale = scale;
		this.strength = strength;
		this.velocity = velocity;

		if (this.loc.x <= img.width && this.loc.x >= 0 && this.loc.y >= 0 && this.loc.y <= img.height) {
			this.newMap = Data.getColorValue(this.loc, this.img);
		} else {
			this.newMap = 0;
		}
		if (mapMultiplier) {
			this.multSpeed = Data.getRandomVector(-this.multiplier * this.newMap, this.multiplier * this.newMap, this.r, false);
		} else {
			this.multSpeed = Data.getRandomVector(-this.multiplier, this.multiplier, this.r, false);
		}
		if (mapScale) {
			if (this.newMap == 0.0f) {
				this.newMap = 0.1f;
			}
			this.scale *= this.newMap;
		}
		if (mapStrength) {
			this.strength *= this.newMap;
		}
		if (this.mapPS && this.mapPSC) {
			this.scale *= this.newMap;
			this.strength *= this.newMap;
		}
		double noiseVal = ImprovedPerlinNoise.noise(this.loc.x / (Float) this.scale,
				this.loc.y / (Float) this.scale, 0.0f) * (this.strength);
		double angle = noiseVal;
		double ddX = Math.cos(angle);
		float dX = (float) ddX;
		double ddY = Math.sin(angle);
		float dY = (float) ddY;
		this.dir = new PVector(dX, dY, 0);
		this.dir.mult(this.velocity);
		this.dir.add(this.multSpeed);

		return this.dir;
	}
	public PVector perlin(float scale, float strength, float multiplier, float velocity, PVector location, Boolean D3) {
		this.r = new Random();

		this.multiplier = multiplier;
		this.loc = location;
		this.scale = scale;
		this.strength = strength;
		this.velocity = velocity;

		this.multSpeed = Data.getRandomVector(-this.multiplier, this.multiplier, this.r, D3);
		double noiseVal = 1.0;
		double noiseValZ = 1.0;
		float offset = 0;
		float stepOver = 1;

		if (!D3) {
			noiseVal = ImprovedPerlinNoise.noise(this.loc.x / (Float) this.scale, this.loc.y / (Float) this.scale,
					0.0) * this.strength;
		} else if (D3) {
			noiseVal = ImprovedPerlinNoise.noise(this.loc.x / (Float) this.scale, this.loc.y / (Float) this.scale,
					this.loc.z / (Float) this.scale) * this.strength;
			noiseValZ = ImprovedPerlinNoise.noise(this.loc.x / (Float) this.scale + offset,
					this.loc.y / (Float) this.scale, this.loc.z / (Float) this.scale) * this.strength;
		}
		double angle = noiseVal;
		double angleZ = noiseValZ; 
		this.dir = new PVector();
		if (!D3) {
			double ddX = Math.cos(angle);
			float dX = (float) ddX;
			double ddY = Math.sin(angle);
			float dY = (float) ddY;
			this.dir = new PVector(dX, dY, 0);
		}
		if (D3) {
			double ddX = Math.cos(angleZ) * Math.cos(angle) * stepOver;
			float dX = (float) ddX;
			double ddY = Math.sin(angleZ);
			float dY = (float) ddY;
			double ddZ = Math.cos(angleZ) * Math.sin(angle) * stepOver;
			float dZ = (float) ddZ;
			this.dir = new PVector(dX, dY, dZ);
		}
		this.dir.mult(this.velocity);
		this.dir.add(this.multSpeed);
		
		return this.dir;
	}
}
