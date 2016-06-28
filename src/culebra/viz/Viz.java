package culebra.viz;

import java.util.ArrayList;
import java.util.List;
import culebra.data.Data;
import processing.core.PApplet;
import processing.core.PVector;
import toxi.color.*;
/**
 * This class is meant to control all Object visualizations. Include all types of object trail drawing
 * @author elQuixote
 *
 */
public class Viz {

	private static PApplet parent;
	private ArrayList<PVector> instanceTrail;
	private float transparency, strokeweight;
	private float[] baseColor;
	private Boolean gradient;
	private ToneMap tm;
	private int maxTrailCount;
	private int hitCount = 0;
	private boolean fxRenderer = false;
	/**
	 * Constructor
	 * @param p the PApplet source
	 */
	public Viz(PApplet p){
		parent = p;
	}
	/**
	 * Sets the trail data. If you have some stored PVectors that you would like to draw pass them here
	 * @param objectTrailData the desired trail data
	 */
	public void setTrailData(ArrayList<PVector> objectTrailData) {
		this.instanceTrail = objectTrailData;
	}
	/**
	 * Creates the trails for the objects using FX2D
	 * @param drawMultiSegmentTrail do you want incremental drawing from PVector to PVector? Use this for gradient color
	 */
	private void createTrailsFX(Boolean drawMultiSegmentTrail) {
		if (this.instanceTrail.size() > this.maxTrailCount) {
			this.instanceTrail.remove(0);
		}
		if (drawMultiSegmentTrail) {
			if (this.instanceTrail.size() > 0) {
				for (int j = 0; j < this.instanceTrail.size(); j++) {

					if (j != 0) {
						List<PVector> vecList = new ArrayList<PVector>();
						PVector pos = this.instanceTrail.get(j);
						PVector prevpos = this.instanceTrail.get(j - 1);
						vecList.add(pos);
						vecList.add(prevpos);

						if (!gradient) {
							if (this.baseColor == null) {
								parent.strokeWeight(0.5f);
								parent.stroke(255, 255);
							} else {
								parent.strokeWeight(this.strokeweight);
								parent.stroke(parent.color(this.baseColor[0], this.baseColor[1], this.baseColor[2]),
										this.transparency);
							}
							parent.line(pos.x, pos.y, prevpos.x, prevpos.y);
							//parent.line(pos.x, pos.y, prevpos.x, prevpos.y);
						} else {
							int a = this.tm.getARGBToneFor(j / (1.0f * instanceTrail.size()));
							float value = Data.map(j / (1.0f * instanceTrail.size()), 0, 1.0f, 0, this.strokeweight);
							float value2 = Data.map(j / (1.0f * instanceTrail.size()), 0, 1.0f, 10, this.transparency);
							parent.strokeWeight(value);
							parent.stroke(a, value2);
							parent.line(pos.x, pos.y, prevpos.x, prevpos.y);
						}
					}
				}
			}
		} else {
			if (this.baseColor == null) {
				parent.strokeWeight(0.5f);
				parent.stroke(255, 255);
			} else {
				parent.strokeWeight(this.strokeweight);
				parent.stroke(parent.color(this.baseColor[0], this.baseColor[1], this.baseColor[2]),
						this.transparency);
			}		
			parent.beginShape();
			parent.noFill();
			if (this.instanceTrail.size() > 0) {

				for (PVector v : this.instanceTrail) {
					parent.vertex(v.x, v.y);
				}	
			}
			parent.endShape();
		}
	}
	/**
	 * Creates the trails for the objects
	 * @param drawMultiSegmentTrail do you want incremental drawing from PVector to PVector? Use this for gradient color
	 */
	private void createTrails(Boolean drawMultiSegmentTrail) {
		if (this.instanceTrail.size() > this.maxTrailCount) {
			this.instanceTrail.remove(0);
		}
		if (drawMultiSegmentTrail) {
			if (this.instanceTrail.size() > 0) {
				for (int j = 0; j < this.instanceTrail.size(); j++) {

					if (j != 0) {
						List<PVector> vecList = new ArrayList<PVector>();
						PVector pos = this.instanceTrail.get(j);
						PVector prevpos = this.instanceTrail.get(j - 1);
						vecList.add(pos);
						vecList.add(prevpos);

						if (!gradient) {
							if (this.baseColor == null) {
								parent.strokeWeight(0.5f);
								parent.stroke(255, 255);
							} else {
								parent.strokeWeight(this.strokeweight);
								parent.stroke(parent.color(this.baseColor[0], this.baseColor[1], this.baseColor[2]),
										this.transparency);
							}
							parent.line(pos.x, pos.y, pos.z, prevpos.x, prevpos.y, prevpos.z);
							//parent.line(pos.x, pos.y, prevpos.x, prevpos.y);
						} else {
							int a = this.tm.getARGBToneFor(j / (1.0f * instanceTrail.size()));
							float value = Data.map(j / (1.0f * instanceTrail.size()), 0, 1.0f, 0, this.strokeweight);
							float value2 = Data.map(j / (1.0f * instanceTrail.size()), 0, 1.0f, 10, this.transparency);
							parent.strokeWeight(value);
							parent.stroke(a, value2);
							parent.line(pos.x, pos.y, pos.z, prevpos.x, prevpos.y, prevpos.z);
						}
					}
				}
			}
		} else {
			if (this.baseColor == null) {
				parent.strokeWeight(0.5f);
				parent.stroke(255, 255);
			} else {
				parent.strokeWeight(this.strokeweight);
				parent.stroke(parent.color(this.baseColor[0], this.baseColor[1], this.baseColor[2]),
						this.transparency);
			}		
			parent.beginShape();
			parent.noFill();
			if (this.instanceTrail.size() > 0) {

				for (PVector v : this.instanceTrail) {
					parent.vertex(v.x, v.y);
					//parent.vertex(v.x, v.y);
				}	
			}
			parent.endShape();
		}
	}
	/**
	 * Sets the network data. This is used to visualize connectivity between agents displaying flocking behavior. Viz of search radius
	 * @param loc the object location
	 * @param data the desired list of PVectors to visualize
	 */
	public void setNetworkData(PVector loc, List<PVector> data){
		createNetwork(loc,data);
	}
	/**
	 * Creates the interconnectivity network between flocking agents
	 * @param loc the object location  
	 * @param dataList the desired list of PVectors to visualize
	 */
	private void createNetwork(PVector loc, List<PVector> dataList){
		parent.pushStyle();
		parent.stroke(255, 255, 255, 150);
		parent.strokeWeight(0.5f);
		for(PVector p : dataList){
			if(this.fxRenderer){
				parent.line(loc.x, loc.y, p.x, p.y);
			}else{
				parent.line(loc.x, loc.y, loc.z, p.x, p.y, p.z);
			}
		}
		parent.popStyle();
	}
	/**
	 * Overloaded method for drawing object Trails. Default color will be used here
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 */
	public void drawTrails(Boolean drawMultiSegmentTrail) {
		this.maxTrailCount = 10000000;
		createTrails(drawMultiSegmentTrail);
	}
	/**
	 * Overloaded method for drawing object Trails. Default color will be used here but maxTrailSize is added. Trail PVectors will be removed from the tail end once they reach max
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param maxTrailSize the max trail PVectors allowed per object
	 */
	public void drawTrails(Boolean drawMultiSegmentTrail, int maxTrailSize) {
		this.maxTrailCount = maxTrailSize;
		createTrails(drawMultiSegmentTrail);
	}
	/**
	 * Overloaded method for drawing object Trails. Default color will be used here but maxTrailSize is added. Trail PVectors will be removed from the tail end once they reach max
	 * @param trailList the ArrayList of PVector trails for each object
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param maxTrailSize the max trail PVectors allowed per object
	 */
	public void drawTrails(ArrayList<PVector> trailList, Boolean drawMultiSegmentTrail, int maxTrailSize) {
		this.maxTrailCount = maxTrailSize;
		this.gradient = false;
		this.instanceTrail = trailList;
		createTrails(drawMultiSegmentTrail);
	}
	/**
	 * Draws Gradient Object Trails, no max trail size option. 
	 * @param trailList the ArrayList of PVector trails for each object
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param colorA the first color to use in gradient
	 * @param colorB the second color to use in gradient
	 * @param transparency the transparency value
	 * @param strokeWeight the strokeweight value
	 */
	public void drawGradientTrails(ArrayList<PVector> trailList, float[] colorA, float[] colorB,
			float transparency, float strokeWeight) {

		this.maxTrailCount = 10000000;
		this.instanceTrail = trailList;
		this.gradient = true;
		this.transparency = transparency;
		this.strokeweight = strokeWeight;

		if (this.gradient && this.hitCount == 0) {

			TColor gradColorA = TColor.newRGB(colorA[0], colorA[1], colorA[2]);
			TColor gradColorB = TColor.newRGB(colorB[0], colorB[1], colorB[2]);
			ColorGradient gradient = new ColorGradient();
			gradient.addColorAt(0.0f, gradColorA);
			gradient.addColorAt(255.0f, gradColorB);
			this.tm = new ToneMap(0.0f, 1.0f, gradient);

			this.hitCount++;
		}
		createTrails(true);
	}
	/**
	 * Overloaded method Draws Gradient Object Trails with max trail size option and the FX2D Renderer
	 * @param trailList the ArrayList of PVector trails for each object
	 * @param maxTrailSize the max trail PVectors allowed per object
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param colorA the first color to use in gradient
	 * @param colorB the second color to use in gradient
	 * @param transparency the transparency value
	 * @param strokeWeight the strokeweight value
	 * @param fx2D if we are using the FX2D renderer
	 */
	public void drawGradientTrailsFX(ArrayList<PVector> trailList, int maxTrailSize, float[] colorA, float[] colorB,
			float transparency, float strokeWeight) {

		this.maxTrailCount = maxTrailSize;
		this.instanceTrail = trailList;
		this.gradient = true;
		this.transparency = transparency;
		this.strokeweight = strokeWeight;

		if (this.gradient && this.hitCount == 0) {

			TColor gradColorA = TColor.newRGB(colorA[0], colorA[1], colorA[2]);
			TColor gradColorB = TColor.newRGB(colorB[0], colorB[1], colorB[2]);
			ColorGradient gradient = new ColorGradient();
			gradient.addColorAt(0.0f, gradColorA);
			gradient.addColorAt(255.0f, gradColorB);
			this.tm = new ToneMap(0.0f, 1.0f, gradient);

			this.hitCount++;
		}
		this.fxRenderer = true;
		createTrailsFX(true);
	}
	/**
	 * Overloaded method Draws Gradient Object Trails with max trail size option.
	 * @param trailList the ArrayList of PVector trails for each object
	 * @param maxTrailSize the max trail PVectors allowed per object
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param colorA the first color to use in gradient
	 * @param colorB the second color to use in gradient
	 * @param transparency the transparency value
	 * @param strokeWeight the strokeweight value
	 * @param fx2D if we are using the FX2D renderer
	 */
	public void drawGradientTrails(ArrayList<PVector> trailList, int maxTrailSize, float[] colorA, float[] colorB,
			float transparency, float strokeWeight) {

		this.maxTrailCount = maxTrailSize;
		this.instanceTrail = trailList;
		this.gradient = true;
		this.transparency = transparency;
		this.strokeweight = strokeWeight;

		if (this.gradient && this.hitCount == 0) {

			TColor gradColorA = TColor.newRGB(colorA[0], colorA[1], colorA[2]);
			TColor gradColorB = TColor.newRGB(colorB[0], colorB[1], colorB[2]);
			ColorGradient gradient = new ColorGradient();
			gradient.addColorAt(0.0f, gradColorA);
			gradient.addColorAt(255.0f, gradColorB);
			this.tm = new ToneMap(0.0f, 1.0f, gradient);

			this.hitCount++;
		}
		createTrails(true);	
	}
	/**
	 * Draws Colored Object Trails with no max trail size option.
	 * @param trailList the ArrayList of PVector trails for each object
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param color the trail color
	 * @param transparency the transparency value
	 * @param strokeWeight the strokeweight value
	 */
	public void drawTrails(ArrayList<PVector> trailList, Boolean drawMultiSegmentTrail, float[] color,
			float transparency, float strokeWeight) {

		this.maxTrailCount = 10000000;
		this.instanceTrail = trailList;
		this.gradient = false;
		this.transparency = transparency;
		this.strokeweight = strokeWeight;
		this.baseColor = color;

		createTrails(drawMultiSegmentTrail);
	}
	/**
	 * Overloaded method Draws Colored Object Trails with max trail size option.
	 * @param trailList the ArrayList of PVector trails for each object
	 * @param drawMultiSegmentTrail if true trail will be drawn as lines from previous pos to new pos, if false trail will be a single shape through all trail points
	 * @param maxTrailSize the max trail PVectors allowed per object
	 * @param color the trail color
	 * @param transparency the transparency value
	 * @param strokeWeight the strokeweight value
	 * @param fx2D if we are using the FX2D renderer
	 */
	public void drawTrails(ArrayList<PVector> trailList, Boolean drawMultiSegmentTrail, int maxTrailSize, float[] color,
			float transparency, float strokeWeight,boolean fx2D) {

		this.maxTrailCount = maxTrailSize;
		this.instanceTrail = trailList;
		this.gradient = false;
		this.transparency = transparency;
		this.strokeweight = strokeWeight;
		this.baseColor = color;
		if(fx2D){
			this.fxRenderer = true;
			createTrailsFX(drawMultiSegmentTrail);
		}else{
			this.fxRenderer = false;
			createTrails(drawMultiSegmentTrail);
		}
	}
	/**
	 * Modifies the trail stroke and transparency attributes
	 * @param transparency the transparency value (0-255);
	 * @param strokeWeight the stroke value
	 */
	public void setDrawingAttributes(float transparency, float strokeWeight) {
		this.transparency = transparency;
		this.strokeweight = strokeWeight;
	}
}
