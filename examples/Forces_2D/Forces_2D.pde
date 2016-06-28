//--Example of force behavior - Attracts and repels the objects depending on what type of force ellipse you draw.

//--left mouse click and drag for attractor force
//--right mouse click and drag for repel force
//--ellipse radii is used for threshold--
//--to clear the forces press the 'x' key

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.viz.*;
import culebra.data.*;

import toxi.geom.*;
import toxi.color.*;

Creeper creep;
List<Creeper> creeperSet;
Seeker creeperTracker;
PVector loc;
PVector vel;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean flockFlag = true;
boolean drawConn = false;
boolean wander = false;
Boolean manualOverride = true;
// -------------------Environ Stuff--------------------
int creepCount = 500;
PImage img0, img2;
// ----------------------------MANUAL OVERRIDES
PVector forceObjectPos;
PVector currentPosition = new PVector();
ArrayList<PVector> forcePts = new ArrayList<PVector>(); 
ArrayList<Float> forceRadii = new ArrayList<Float>(); 
ArrayList<Boolean> forceType = new ArrayList<Boolean>(); 
Boolean resetInput = true;
Boolean forceGuy = true;
// -----------------------Setup-----------------------
public void setup() {
  size(1400, 800, P3D);
  smooth();
  background(0);
  this.creeperSet = new ArrayList<Creeper>();
  this.img0 = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");

  for (int i = 0; i < creepCount; i++) {
    this.loc = new PVector(random(0, width), random(0, height), 0);
    this.vel = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
    this.creep = new Creeper(loc, vel, true, false, this);
    this.creeperSet.add(this.creep);
  }
}
// -----------------------Draw-----------------------
public void draw() {
  background(0);
  pushStyle();
  stroke(255, 0, 0);
  strokeWeight(10);
  popStyle();
  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  if (this.manualOverride) {
    if (resetInput) {
      currentPosition.set(mouseX, mouseY);
      for (int z = 0; z < forcePts.size(); z++) {
        PVector forcePt = forcePts.get(z);
        pushStyle();
        stroke(255, 255, 255);
        noFill();

        ellipse(forcePt.x, forcePt.y, forceRadii.get(z), forceRadii.get(z));
        popStyle();
      }
    }
  }
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(2.5f, 0.1f, 1.5f);
    // --Force--------------------------------
    if (this.resetInput) {
      if (this.forcePts.size() > 0) {
        for (int i = 0; i < this.forcePts.size(); i++) {
          if (this.forceType.get(i)) {
            c.behavior.attract(this.forcePts.get(i), this.forceRadii.get(i), 1.00f, 0.10f);
          } else {
            c.behavior.repel(this.forcePts.get(i), this.forceRadii.get(i), 1.0f, 1.5f);
          }
        }
      }
    }
    if (wander) {
      c.behavior.wander2D(true, false, 2.0f, 80.0f, 20.0f);
    }
    if (flockFlag) {
      c.behavior.creeperflock2D(50, 0.24, 0.09, 0.045, 60, creeperSet, drawConn);
    }
    //c.respawn(width, height);
    c.bounce(width,height);
    c.move(6, 500);
    if (createTrails) {
      if (c instanceof Creeper) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 1f, 0f, 0f };
        float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
        c.viz.drawGradientTrails(c.getTrailPoints(), 500, colorA, colorB, 255.0f, 1.0f);
      }
    }      
    pushStyle();
    stroke(255);
    strokeWeight(4);
    point(c.getLocation().x, c.getLocation().y,  c.getLocation().z);
    popStyle();

    image(img2, width-290, height-85);
    image(img0, 0, height-105);
    textSize(20);
    text("Framerate: " + (frameRate), 80, height - 6);
  }
  surface.setSize(width, height);
}
public void mousePressed() {
  addForceObject();
}

public void mouseReleased() {
  PVector ep = new PVector(mouseX, mouseY);
  float distance = PVector.dist(ep, forceObjectPos);
  float rad = distance * 2;
  forcePts.add(forceObjectPos);
  forceRadii.add(rad);

  if (mouseButton == LEFT) {
    this.forceGuy = true;
  } else {
    this.forceGuy = false; 
  }
  forceType.add(forceGuy);
}
public void addForceObject() {
  PVector loc = new PVector(mouseX, mouseY);
  forceObjectPos = loc;
}
// ------------------------Create Paths----------------------------------
public void keyPressed() {
  if (key == 'r')
    setup();
  if (key == 't')
    this.createTrails = !this.createTrails;
  if (key == '1')
    this.drawConn = !this.drawConn;   
  if (key == 'q') {
    this.manualOverride = !this.manualOverride;
  }
  if (key=='f') {
    this.flockFlag = !this.flockFlag;
  }
  if (key=='w') {
    this.wander = !this.wander;
  }
  if (key == 'x') {
    resetInput = !resetInput;
    this.forcePts = new ArrayList<PVector>(); 
    this.forceRadii = new ArrayList<Float>(); 
    this.forceType = new ArrayList<Boolean>(); 
  }
}