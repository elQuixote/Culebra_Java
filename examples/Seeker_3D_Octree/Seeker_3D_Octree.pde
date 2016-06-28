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
List<PVector> totTail;
List<PVector> allTrails;
List<ArrayList> allCombinedTrails;
List<culebra.objects.Object> seekerList;
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
int creepCount = 50;
PImage img0, img2;
// --------------------OcTree------------------
Octree octree;
boolean createOctree = true;
boolean showOctree = false;
// -------------------Spawn Stuff--------------------
boolean spawnGround = false;
boolean spawnEdge = false;
// ----------------------Camera-------------------------
Cameras cams;
int camToggle = 0;
// -------------------Environ Stuff--------------------
int nHeight = 1000;
boolean D2 = false;
boolean D3 = true;
// -----------------------Setup-----------------------
public void setup() {
  size(1400, 800, P3D);
  smooth();
  background(0);

  this.triggerTailSeekers = true;

  this.img0 = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");

  this.octree = Data.makeOctree(width, this);

  this.creeperSet = new ArrayList<Creeper>();
  this.seekerList = new ArrayList<culebra.objects.Object>();
  this.allCombinedTrails = new ArrayList<ArrayList>();

  totTail = new ArrayList<PVector>();

  this.cams = new Cameras(this);
  if (this.camToggle < 1) {
    this.camToggle++;
    int[] lookat = new int[] { this.width / 2, this.height / 2, this.nHeight / 2 };
    this.cams.set3DCamera(1500, 100, 10000, lookat, true);
  }

  for (int i = 0; i < creepCount; i++) {

    if (this.spawnGround) {
      this.loc = new PVector(random(width), random(height), 0);
      this.vel = new PVector(random(-1, 1), random(-1, 1), random(0, 2));
    } else if (this.spawnEdge) {
      this.loc = new PVector(width, random(height), random(0, nHeight));
      this.vel = new PVector(random(-1, 1), random(-1, 1), random(0, 2));
    } else {
      this.loc = new PVector(random(width), random(height), random(0, nHeight));
      this.vel = new PVector(random(-1, 1), random(-1, 1), random(-1, 1));
    }      

    this.creep = new Creeper(loc, vel, true, this.D3, this);
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
  drawExtents();

  if (this.aligFlag || this.sepFlag || this.cohFlag || this.flockFlag) Data.updateCreepersTree(this.creeperSet, this.showOctree);
  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  ArrayList groupCombinedData = new ArrayList();
  int i = 0;
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(2.0f, 0.095f, 1.5f);

    this.allTrails = new ArrayList<PVector>();
    ArrayList groupData = new ArrayList();

    if (this.aligFlag) {
      c.behavior.creeperAlignOptimized_3D(100.0f, 0.045f, creeperSet, Data.getOctree(), Data.getTreeNodes(), this.drawConn);
    }
    if (this.cohFlag) {
      c.behavior.creeperCohesionOptimized_3D(100.0f, 0.24f, creeperSet, Data.getOctree(), Data.getTreeNodes(), this.drawConn);
    }
    if (this.chaseOtherTailFlag) {
      allTrails.addAll(c.getTrailPoints());
      groupData.add(i);
      groupData.add(allTrails);
      groupCombinedData.add(groupData);
    }
    if (this.wanderFlag) {
      float change = 100;
      c.behavior.wander3D_Mod2(change, 10.0f, 20.0f);
    }
    if (this.flockFlag) {
      c.behavior.creeperflock(300.0f, 0.24f, 0.09f, 0.045f, 0.0f, creeperSet, Data.getOctree(), Data.getTreeNodes(), this.drawConn);
    }      
    c.bounce(width, height, nHeight);
    c.move(0, 50);
    if (createTrails) {
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 1f, 0f, 0f };
      float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
      c.viz.drawGradientTrails(c.getTrailPoints(), 50, colorA, colorB, 255.0f, 2.0f);
    }     
    pushStyle();
    stroke(255);
    strokeWeight(10);
    point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
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

    Seeker seek = (Seeker)obj;
    seek.setMoveAttributes(2.0f, 0.1f, 1.5f);

    if (this.sepFlag) {
      seek.behavior.creeperSeparateOptimized_3D(100.0f, 0.09f, creeperSet, Data.getOctree(), Data.getTreeNodes(), this.drawConn);
    }
    if (this.chaseOtherTailFlag) {
      seek.behavior.trailFollower(allCombinedTrails, 1500, 20, 10);
    }
    if (this.wanderFlag) {
      float change = 100;
      //c.behavior.wander3D(change, 10.0f, 20.0f, 6.0f);
      //c.behavior.wander3D_subA(change, 10.0f, 20.0f, 6.0f);
      //c.behavior.wander3D_subB(change, 10.0f, 20.0f, 6.0f);
      //c.behavior.wander3D_Mod(change, 10.0f, 20.0f);
      seek.behavior.wander3D_Mod2(change, 10.0f, 20.0f);
      //c.behavior.wander3D_Mod3(change, 10.0f, 20.0f);
    }
    if (this.D3) {
      seek.bounce(width, height, nHeight);
    } else {
      seek.bounce(width, height);
    }
    seek.move(0, 1000);
    if (createTrails) {
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 120.0f, 0.0f, 0.0f };
      float colorB[] = new float[] { 0.0f, 255.0f, 255.0f };
      seek.viz.drawGradientTrails(seek.getTrailPoints(), 1000, colorA, colorB, 255.0f, 2.0f);
    }
    pushStyle();
    stroke(255);
    strokeWeight(4);
    point(seek.getLocation().x, seek.getLocation().y, seek.getLocation().z);
    popStyle();

    i++;
  }
  // --------------------------------
  this.allCombinedTrails.clear();
  this.allTrails.clear();
  this.totTail.clear();  
  if (!this.D3) {
    surface.setSize(width, height);
  }
}
void drawExtents() {
  pushStyle();
  pushMatrix();
  strokeWeight(0.5f);
  stroke(200);
  noFill();
  translate(width / 2, height / 2, this.nHeight / 2);
  box(width, height, this.nHeight);
  popMatrix();
  popStyle();
}
// ---------------------------------------SpawnSeekers------------------------------------------------
// ---------------------------------------------------------------------------------------------------
public void spawnSeekers() {
  for (int i = 0; i < 50; i++) {
    PVector speed;
    if (!this.D3) {
      if (this.spawnEdge) {
        this.loc = new PVector(0, random(height), 0);
        speed = new PVector(1, 0, 0);
      } else {
        this.loc = new PVector(random(width), random(height), 0);
        speed = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
      }
      this.creeperTracker = new Seeker(this.loc, speed, true, this.D3, this);
    } else if (this.D3 && !spawnEdge) {
      if (this.spawnEdge) {
        this.loc = new PVector(random(width), random(height), 0);
        speed = new PVector(random(-1, 1), random(-1, 1), random(0, 2));
      } else {
        this.loc = new PVector(random(width), random(height), random(0, nHeight));
        speed = new PVector(random(-1, 1), random(-1, 1), random(-1, 1));
      }
      this.creeperTracker = new Seeker(this.loc, speed, true, this.D3, this);
    } else {
      this.loc = new PVector(width, random(height), random(0, nHeight));
      speed = new PVector(random(-2, -1), random(-1, 1), random(-2, 2));
      this.creeperTracker = new Seeker(this.loc, speed, true, this.D3, this);
    }
    this.seekerList.add(this.creeperTracker);
  }
}
// ------------------------KEYS----------------------------------
public void keyPressed() {
  if (key == 'r')setup();
  if (key == 't')this.createTrails = !this.createTrails;
  if (key == '1')this.drawConn = !this.drawConn; 
  if (key == 'e')this.spawnEdge = !this.spawnEdge;
  if (key == 'o')this.showOctree = !this.showOctree;
  if (key == 'c')this.cohFlag = !this.cohFlag;
  if (key == 'a')this.aligFlag = !this.aligFlag;
  if (key == 's')this.sepFlag = !this.sepFlag;
  if (key == 'f')this.flockFlag = !this.flockFlag;
  if (key == 'w')this.wanderFlag = !this.wanderFlag;
  if (key == 'l')triggerTailSeekers = !triggerTailSeekers;
  if (key == 'q')chaseOtherTailFlag = !chaseOtherTailFlag;
}