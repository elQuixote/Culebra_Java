//--This example looks at deploying a set of creeper objects and triggering a set of seeker objects to track them and both object types spawn children. Both objects have their own behaviors--
//--This example is performance optmized by the use of Octrees--
//--To Spawn Babies hit the 'b' key--
//--Flock Behavior for the Creepers is triggered by the 'f' key--
//--To Trigger optimized separation between the seekers and its babies and creepers and its babies use the 's' key--
//--if you enable flocking you can disable the network visualization by pressing '1' key--

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
// -------------------Children Stuff--------------------
boolean triggerBabies = true;
ArrayList<PVector> childSpawners;
ArrayList childSpawnType;
ArrayList<PVector> creeperChildSpawners;
ArrayList creeperChildSpawnType;
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

  this.childSpawners = new ArrayList<PVector>();
  this.childSpawnType = new ArrayList();
  this.creeperChildSpawners = new ArrayList<PVector>();
  this.creeperChildSpawnType = new ArrayList();
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

    this.creeperChildSpawners = c.behavior.getChildStartPositions();
    this.creeperChildSpawnType = c.behavior.getChildSpawnType();

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
      if (!(c instanceof BabyCreeper)) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 1f, 0f, 0f };
        float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
        c.viz.drawGradientTrailsFX(c.getTrailPoints(), 20, colorA, colorB, 255.0f, 3.0f);
      } else if (c instanceof BabyCreeper) {
        if (((BabyCreeper)c).getType() == "a") {
          float colorA[] = new float[] { 0.0f, 120.0f, 0.0f };
          float colorB[] = new float[] { 200.0f / 10.0f, 160.0f / 100.0f, 150.0f / 10.0f };
          c.viz.drawGradientTrailsFX(c.getTrailPoints(), 20, colorB, colorA, 255.0f, 3.0f);
        } else {
          float colorA[] = new float[] {255.0f, 215.0f, 0.0f };
          float colorB[] = new float[] {  200f, 1f, 255f };
          c.viz.drawGradientTrailsFX(c.getTrailPoints(), 20, colorA, colorB, 255.0f, 3.0f);
        }
      }
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
      //--internally we are checking the obj type and superclass to if (!obj(instanceof BabySeeker)) then we allow it to replicate otherwise not--so you can specify
      //--true to instanceable parameter and it will only create instances if youre objtype is not babyseeker or if your superclass is not a babyseeker
      seek.behavior.trailFollowerBabyMaker(allCombinedTrails, 1500, 20, 6, this.triggerBabies, 2, true, this.creeperChildSpawners, this.creeperChildSpawnType); 
      this.childSpawners = seek.behavior.getChildStartPositions(); //getting the child start positions and reseting the list internally
      this.childSpawnType = seek.behavior.getChildSpawnType(); //getting the child triggernumber
      seek.behavior.trailFollowerBabyMaker(allCombinedTrails, 1500, 20, 6, this.triggerBabies, 2, true, this.childSpawners, this.childSpawnType);
    }
    if (this.wanderFlag) {
      float change = 2;
      seek.behavior.wander2D(true, false, change, 20.0f, 80.0f);
    }
    seek.bounce(width, height);
    seek.move(0, 500);

    if (createTrails) {
      if (!(seek instanceof BabySeeker)) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 120.0f, 0.0f, 200.0f };
        float colorB[] = new float[] { 100.0f, 0.0f, 255.0f };
        seek.viz.drawGradientTrailsFX(seek.getTrailPoints(), 100, colorA, colorB, 255.0f, 2.0f);
      } else if (seek instanceof BabySeeker) {
        if (((BabySeeker)seek).getType() == "a") {
          float colorA[] = new float[] { 120.0f, 0.0f, 0.0f };
          float colorB[] = new float[] { 0.0f, 255.0f, 255.0f };
          seek.viz.drawGradientTrailsFX(seek.getTrailPoints(), 500, colorA, colorB, 255.0f, 2.0f);
        } else {
          float colorA[] = new float[] { 255.0f, 0.0f, 120.0f };
          float colorB[] = new float[] { 200.0f / 60.0f, 160.0f / 100.0f, 80.0f / 30.0f };
          seek.viz.drawGradientTrailsFX(seek.getTrailPoints(), 500, colorA, colorB, 255.0f, 2.0f);
        }
      }
    }
    pushStyle();
    stroke(255);
    strokeWeight(4);
    ellipse(seek.getLocation().x, seek.getLocation().y, 2, 2);
    popStyle();
    i++;
  }
  //--if the are any child spawners to spawn then spawn them - these will be of type BabySeekers which inherit from Seekers--
  if (this.childSpawners.size() > 0) {
    newDude();
    this.childSpawners = new ArrayList<PVector>();
    this.childSpawnType = new ArrayList();
  }
  //--if the are any creeper child spawners to spawn then spawn them - these will be of type BabyCreepers which inherit from Creeper--
  if (this.creeperChildSpawners.size() > 0) {
    newCreeperDude();
    this.creeperChildSpawners = new ArrayList<PVector>();
    this.creeperChildSpawnType = new ArrayList();
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
// ---------------------------------------SEEKER Children Creation-----------------------------------
void newDude() {
  int babyCount = 0;
  for (PVector px : this.childSpawners) {
    PVector speed;
    if (this.spawnEdge) {
      speed = new PVector(1, 0, 0);
    } else {
      speed = new PVector(random(-1, 1), random(-1, 1), 0);
    }
    Seeker a;
    if ((int) this.childSpawnType.get(babyCount) % 2 == 0) {
      a = new BabySeeker(new PVector(px.x, px.y, px.z), speed, false, "a", false, this);
      this.seekerList.add(a);
    } else {
      a = new BabySeeker(new PVector(px.x, px.y, px.z), speed, false, "b", false, this);
      this.seekerList.add(a);
    }
    babyCount++;
  }
}
// ---------------------------------------CREEPER Children Creation-----------------------------------
void newCreeperDude() {
  int babyCount = 0;
  for (PVector px : this.creeperChildSpawners) {
    PVector speed;
    if (this.spawnEdge) {
      speed = new PVector(1, 0, 0);
    } else {
      speed = new PVector(random(-1, 1), random(-1, 1), 0);
    }
    Creeper a;
    if ((int) this.creeperChildSpawnType.get(babyCount) % 2 == 0) {
      a = new BabyCreeper(new PVector(px.x, px.y, px.z), speed, false, "a", false, this);
      this.creeperSet.add(a);
    } else {
      a = new BabyCreeper(new PVector(px.x, px.y, px.z), speed, false, "b", false, this);
      this.creeperSet.add(a);
    }
    babyCount++;
  }
}
// ------------------------KEYS----------------------------------
public void keyPressed() {
  if (key == 'R')setup();
  if (key == 'T')this.createTrails = !this.createTrails;
  if (key == '1')this.drawConn = !this.drawConn;
  if (key == 'B')this.triggerBabies = !this.triggerBabies;    
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