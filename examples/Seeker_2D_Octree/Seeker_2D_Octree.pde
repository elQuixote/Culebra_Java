//--This example looks at deploying a set of creeper objects and triggering a set of seeker objects to track them. Both objects have their own behavior--

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.viz.*;
import culebra.data.*;
import culebra.geometry.*;

import toxi.geom.*;
import toxi.color.*;
import peasy.*;

Creeper creep;
List<Creeper> creeperSet;
Seeker creeperTracker;

PVector loc;
PVector vel;
// -------------------------Chasers-------------------
List<culebra.objects.Object> seekerList;
List<PVector> totTail;
List<PVector> allTrails;
List<ArrayList> allCombinedTrails;
boolean triggerTailSeekers = true;
boolean chaseOtherTailFlag = true;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean cohFlag = false;
boolean sepFlag = false;
boolean aligFlag = false;
boolean flockFlag = false;
boolean wanderFlag = true;
boolean drawConn = true;
// -------------------Environ Stuff--------------------
int creepCount = 100;
PImage img0, img2;
// --------------------QTree------------------
QuadTree qTree;
boolean createQtree = true;
boolean showqtree = false;
// -------------------Spawn Stuff--------------------
boolean spawnEdge = false;
// ----------------------Camera-------------------------
Cameras cams;
int camToggle = 0;
// -------------------Environ Stuff--------------------
int nHeight = 1000;
boolean D2 = true;
// -----------------------Setup-----------------------
public void setup() {
  size(1880, 920, FX2D);
  smooth();
  background(0);

  this.triggerTailSeekers = true;

  this.img0 = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");

  this.qTree = Data.makeQuadtree(width, this);
  this.creeperSet = new ArrayList<Creeper>();
  this.seekerList = new ArrayList<culebra.objects.Object>();
  this.allCombinedTrails = new ArrayList<ArrayList>();

  totTail = new ArrayList<PVector>();

  for (int i = 0; i < creepCount; i++) {

    if (this.spawnEdge) {
      this.loc = new PVector(0, random(height), 0);
      this.vel = new PVector(1, 0, 0);
    } else {
      this.loc = new PVector(random(0, width), random(0, height), 0);
      this.vel = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
    }
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

  if (this.aligFlag || this.sepFlag || this.cohFlag || this.flockFlag) Data.updateCreepersQTree(this.creeperSet, this.showqtree);

  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  ArrayList groupCombinedData = new ArrayList();
  int i = 0;

  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(2.0f, 0.2f, 1.5f);

    this.allTrails = new ArrayList<PVector>();
    ArrayList groupData = new ArrayList();

    if (this.aligFlag) {
      c.behavior.creeperAlignOptimized_2D(100.0f, 0.045f, creeperSet, Data.getQuadtree(), Data.getQTNodes(), this.drawConn);
    }
    if (this.cohFlag) {
      c.behavior.creeperCohesionOptimized_2D(100.0f, 0.24f, creeperSet, Data.getQuadtree(), Data.getQTNodes(), this.drawConn);
    }
    if (this.chaseOtherTailFlag) {
      allTrails.addAll(c.getTrailPoints());
      groupData.add(i);
      groupData.add(allTrails);
      groupCombinedData.add(groupData);
    }
    if (this.wanderFlag) {
      float change = 2;
      c.behavior.wander2D(true, true, change, 20.0f, 80.0f);
    }
    if (this.flockFlag) {
      c.behavior.creeperflock2DTree(300.0f, 0.2f, 0.09f, 0.045f, creeperSet, Data.getQuadtree(), Data.getQTNodes(), this.drawConn);
    }      
    c.bounce(width, height, nHeight);
    c.move(0, 20);
    if (createTrails) {
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 1f, 0f, 0f };
      float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
      c.viz.drawGradientTrailsFX(c.getTrailPoints(), 20, colorA, colorB, 255.0f, 3.0f);
    }   
    pushStyle();
    stroke(255);
    strokeWeight(4);
    ellipse(c.getLocation().x, c.getLocation().y, 2, 2);
    popStyle();
    i++;

    image(img2, width-290, height-85);
    image(img0, 0, height-105);
    textSize(20);
    text("Framerate: " + (frameRate), 80, height - 6);
  }
  // -----------------------SEEKER SETUP DATA------------------------
  if (this.chaseOtherTailFlag) {
    allCombinedTrails.addAll(groupCombinedData);
    groupCombinedData.clear();
  }
  if (triggerTailSeekers) {
    spawnSeekers();
    triggerTailSeekers = !triggerTailSeekers;
  }
  // -----------------------------------------------------------------
  // -----------------------SEEKER AGENT TYPE------------------------
  // -----------------------------------------------------------------
  for (culebra.objects.Object obj : this.seekerList) {

    Seeker seek = (Seeker)obj; //Downcast to a seeker from the culebra.objects.Object - the list needed to be of type culebra.object.Objects because some methods accept only reference to creeper or culebra.objects.Object types see line 33
    seek.setMoveAttributes(2.0f, 0.1f, 1.5f);

    if (this.sepFlag) {
      seek.behavior.creeperSeparateOptimized_2D(300.0f, 0.25f, creeperSet, Data.getQuadtree(), Data.getQTNodes(), this.drawConn);
    }
    if (this.chaseOtherTailFlag) {
      seek.behavior.trailFollower(allCombinedTrails, 1500, 10, 5);
    }
    if (this.wanderFlag) {
      float change = 2;
      seek.behavior.wander2D(true, false, change, 20.0f, 80.0f);
    }
    seek.bounce(width, height);
    seek.move(0, 500);

    if (createTrails) {
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 120.0f, 0.0f, 0.0f };
      float colorB[] = new float[] { 0.0f, 255.0f, 255.0f };
      seek.viz.drawGradientTrailsFX(seek.getTrailPoints(), 500, colorA, colorB, 255.0f, 2.0f);
    }
    pushStyle();
    stroke(255);
    strokeWeight(4);
    ellipse(seek.getLocation().x, seek.getLocation().y, 2, 2);
    popStyle();
    i++;
  }
  // --------------------------------
  this.allCombinedTrails.clear();
  this.allTrails.clear();
  this.totTail.clear();  

  surface.setSize(width, height);
}
// ---------------------------------------SpawnSeekers------------------------------------------------
// ---------------------------------------------------------------------------------------------------
public void spawnSeekers() {
  for (int i = 0; i < 50; i++) {
    PVector speed;
    if (this.D2) {
      if (this.spawnEdge) {
        this.loc = new PVector(0, random(height), 0);
        speed = new PVector(1, 0, 0);
      } else {
        this.loc = new PVector(random(width), random(height), 0);
        speed = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
      }
      this.creeperTracker = new Seeker(this.loc, speed, true, false, this);
    } 
    this.seekerList.add(this.creeperTracker);
  }
}
// ------------------------KEYS----------------------------------
public void keyPressed() {
  if (key == 'R')setup();
  if (key == 'T')this.createTrails = !this.createTrails;
  if (key == '1')this.drawConn = !this.drawConn;
  if (key == 'E')this.spawnEdge = !this.spawnEdge;
  if (key == 'O')this.showqtree = !this.showqtree;
  if (key == 'C')this.cohFlag = !this.cohFlag;
  if (key == 'A')this.aligFlag = !this.aligFlag;
  if (key == 'S')this.sepFlag = !this.sepFlag;
  if (key == 'F')this.flockFlag = !this.flockFlag;
  if (key == 'W')this.wanderFlag = !this.wanderFlag;
  if (key == 'L')triggerTailSeekers = !triggerTailSeekers;
  if (key == 'Q')chaseOtherTailFlag = !chaseOtherTailFlag;
}