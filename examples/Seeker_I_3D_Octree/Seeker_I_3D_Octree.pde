//--This example looks at deploying a set of creeper objects and triggering a set of seeker objects and its babies to track them. Both objects have their own behaviors--
//--This example is performance optmized by the use of Octrees--
//--To enalbe/disable Spawn Babies hit the 'b' key--
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

  this.octree = Data.makeOctree(width, this); //make the octree

  this.childSpawners = new ArrayList<PVector>();
  this.childSpawnType = new ArrayList();
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

  if (this.aligFlag || this.sepFlag || this.cohFlag || this.flockFlag) Data.updateCreepersTree(this.creeperSet, this.showOctree); //update the octree each frame
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
    //--Here we need to store all the trials for each creeper to pass to the seekers down below--
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
      if (!(c instanceof BabyCreeper)) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 1f, 0f, 0f };
        float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
        c.viz.drawGradientTrails(c.getTrailPoints(), 50, colorA, colorB, 255.0f, 3.0f);
      } else if (c instanceof BabyCreeper) {
        if (((BabyCreeper)c).getType() == "a") {
          float colorA[] = new float[] { 0.0f, 120.0f, 0.0f };
          float colorB[] = new float[] { 200.0f / 10.0f, 160.0f / 100.0f, 150.0f / 10.0f };
          c.viz.drawGradientTrails(c.getTrailPoints(), 50, colorB, colorA, 255.0f, 3.0f);
        } else {
          float colorA[] = new float[] {255.0f, 215.0f, 0.0f };
          float colorB[] = new float[] {  200f, 1f, 255f };
          c.viz.drawGradientTrails(c.getTrailPoints(), 50, colorA, colorB, 255.0f, 3.0f);
        }
      }
    }     
    pushStyle();
    stroke(255);
    strokeWeight(10);
    point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
    popStyle();
    i++;

    image(img2, width-290, height-85);
    image(img0, 0, height-105);
  }
  // -----------------------SEEKER SETUP DATA------------------------
  //--If we want to chase the creepers we need to get all the combined trails from the creepers--
  if (this.chaseOtherTailFlag) {
    allCombinedTrails.addAll(groupCombinedData);
    groupCombinedData.clear();
  }
  if (triggerTailSeekers) {
    spawnSeekers(); //we are triggering the release of the seeker objects
    triggerTailSeekers = !triggerTailSeekers;
  }
  // -----------------------------------------------------------------
  // -----------------------SEEKER AGENT TYPE------------------------
  // ----------------------------------------------------------------- 
  for (culebra.objects.Object obj : this.seekerList) {

    Seeker seek = (Seeker)obj; //Downcast to a seeker from the culebra.objects.Object - the list needed to be of type culebra.object.Objects because some methods accept only reference to creeper or culebra.objects.Object types see line 33
    seek.setMoveAttributes(2.0f, 0.1f, 1.5f);

    if (this.sepFlag) {
      seek.behavior.creeperSeparateOptimized_3D(300.0f, 0.25f, creeperSet, Data.getOctree(), Data.getTreeNodes(), this.drawConn);
    }
    if (this.chaseOtherTailFlag) {
      //--internally we are checking the obj type and superclass to if (!obj(instanceof BabySeeker)) then we allow it to replicate otherwise not--so you can specify
      //--true to instanceable parameter and it will only create instances if youre objtype is not babyseeker or if your superclass is not a babyseeker
      seek.behavior.trailFollowerBabyMaker(allCombinedTrails, 1500, 20, 20, this.triggerBabies, 2, true, this.childSpawners, this.childSpawnType);
      this.childSpawners = seek.behavior.getChildStartPositions(); //getting the child start positions and reseting the list internally
      this.childSpawnType = seek.behavior.getChildSpawnType(); //getting the child triggernumber
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
      if (!(seek instanceof BabySeeker)) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 255.0f, 0f, 0f };
        seek.viz.drawTrails(seek.getTrailPoints(), true, 200, colorA, 255.0f, 1.0f, false);
      } else if (seek instanceof BabySeeker) {
        if (((BabySeeker)seek).getType() == "a") {
          float colorA[] = new float[] { 120.0f, 0.0f, 0.0f };
          float colorB[] = new float[] { 0.0f, 255.0f, 255.0f };
          seek.viz.drawGradientTrails(seek.getTrailPoints(), 1000, colorA, colorB, 255.0f, 2.0f);
        } else {
          float colorA[] = new float[] { 255.0f, 0.0f, 120.0f };
          float colorB[] = new float[] { 200.0f / 60.0f, 160.0f / 100.0f, 80.0f / 30.0f };
          seek.viz.drawGradientTrails(seek.getTrailPoints(), 1000, colorA, colorB, 255.0f, 2.0f);
        }
      }
    }

    pushStyle();
    stroke(255);
    strokeWeight(4);
    point(seek.getLocation().x, seek.getLocation().y, seek.getLocation().z);
    popStyle();

    i++;
  }
  //--if the are any child spawners to spawn then spawn them - these will be of type BabySeekers which inherit from Seekers--
  if (this.childSpawners.size() > 0) {
    newDude();
    this.childSpawners = new ArrayList<PVector>();
    this.childSpawnType = new ArrayList();
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
      a = new BabySeeker(new PVector(px.x, px.y, px.z), speed, false, "a", this.D3, this);
      this.seekerList.add(a);
    } else {
      a = new BabySeeker(new PVector(px.x, px.y, px.z), speed, false, "b", this.D3, this);
      this.seekerList.add(a);
    }
    babyCount++;
  }
}
// ------------------------KEYS----------------------------------
public void keyPressed() {
  if (key == 'r')setup();
  if (key == 't')this.createTrails = !this.createTrails;
  if (key == '1')this.drawConn = !this.drawConn;
  if (key == 'b')this.triggerBabies = !this.triggerBabies;    
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