//--Mesh Crawling with octree optimization--
//--show mesh by pressing 'd' keyl the commented out variations of the behavior--

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.viz.*;
import culebra.data.*;
import culebra.geometry.Mesh;

import toxi.geom.*;
import toxi.color.*;
import toxi.processing.*;
import peasy.*;

Creeper creep;
List<Creeper> creeperSet;
Seeker creeperTracker;

PVector loc;
PVector vel;
// -------------------Children Stuff--------------------
boolean triggerBabies;
ArrayList<PVector> childSpawners;
ArrayList childSpawnType;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean flockFlag = false;
boolean drawConn = false;
// -------------------Environ Stuff--------------------
int creepCount = 500;
PImage img0, img2;
// --------------------OcTree------------------
Octree octree;
boolean createOctree = true;
boolean showOctree = false;
// -------------------Spawn Stuff--------------------
boolean spawnGround = false;
boolean spawnEdge = true;
// ----------------------Camera-------------------------
PeasyCam cam;
Cameras cams;
CameraState state;
int camToggle = 0;
// -------------------Environ Stuff--------------------
int nHeight = 1000;
// -------------------Mesh Stuff--------------------
culebra.geometry.Mesh mesh;
boolean renderMesh;
// ---------------------- Directory Files -------------------------------------------
/////////////////////////////////////////////////////////////////////////////////////
String siteMeshFile = "Baby_G2.stl";
String siteDisplayMeshFile = "Baby_G3.stl";
// -----------------------Setup-----------------------
public void setup() {
  size(1920, 1080, P3D);
  smooth();
  background(0);
  this.creeperSet = new ArrayList<Creeper>();
  this.img0 = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");

  this.mesh = new culebra.geometry.Mesh(siteMeshFile, siteMeshFile, this);
  
  this.octree = Data.makeOctree(width, this);
  
  this.childSpawners = new ArrayList<PVector>();
  this.childSpawnType = new ArrayList();

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

    this.creep = new Creeper(loc, vel, true, true, this);
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

  if (this.renderMesh)this.mesh.renderMesh();
  Data.updateTree(this.creeperSet, this.showOctree);

  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(6.0f, 0.095f, 1.5f);
    
   
    c.behavior.meshCrawl(this.mesh, 20, c.getLocation(), c.getSpeed(), 100, 2, triggerBabies,true, this.childSpawners, this.childSpawnType);
    c.behavior.separateOptimized_3D(100.0f, 0.09f, creeperSet,Data.getOctree(), Data.getTreeNodes(),this.drawConn);
    
    
    this.childSpawners = c.behavior.getCrawlerChildStartPositions();
    this.childSpawnType = c.behavior.getCrawlerChildSpawnType();   
    
    
    float change = 100;     
    //c.behavior.wander3D(change, 20.0f, 80.0f,6.0f);
    //c.behavior.wander3D_subA(change, 10.0f, 20.0f,6.0f);
    //c.behavior.wander3D_subB(change, 20.0f, 80.0f,6.0f);
    //c.behavior.wander3D_Mod(change, 20.0f, 80.0f);
    //c.behavior.wander3D_Mod2(change, 20.0f, 80.0f);
    //c.behavior.wander3D_Mod3(change, 20.0f, 80.0f);

    //c.bounce(width, height, nHeight);
    c.move(0, 100);
    if (createTrails) {
      if (!(c instanceof BabyCreeper)) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 1f, 0f, 0f };
        float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
        c.viz.drawGradientTrails(c.getTrailPoints(), 1000, colorA, colorB, 255.0f, 1.0f);
      } else if (c instanceof BabyCreeper) {
        if (((BabyCreeper)c).getType() == "a") {
          float colorA[] = new float[] { 120.0f, 0.0f, 0.0f };
          float colorB[] = new float[] { 0.0f, 255.0f, 255.0f };
          c.viz.drawGradientTrails(c.getTrailPoints(), 1000, colorA, colorB, 255.0f, 1.0f);
        } else {
          float colorA[] = new float[] { 255.0f, 0.0f, 120.0f };
          float colorB[] = new float[] { 200.0f / 60.0f, 160.0f / 100.0f, 80.0f / 30.0f };
          c.viz.drawGradientTrails(c.getTrailPoints(), 1000, colorA, colorB, 255.0f, 1.0f);
        }
      }
    }     
    pushStyle();
    stroke(255);
    strokeWeight(4);
    point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
    popStyle();

    image(img2, width-290, height-85);
    image(img0, 0, height-105);
    textSize(20);
    text("Framerate: " + (frameRate), 80, height - 6);
  }
  if (this.childSpawners.size() > 0) {
    newDude();
    this.childSpawners = new ArrayList<PVector>();
    this.childSpawnType = new ArrayList();
  }  
  surface.setSize(width, height);
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
// ---------------------------------------Children Creation-----------------------------------
void newDude() {
  int babyCount = 0;
  for (PVector px : this.childSpawners) {
    PVector speed;
    if (this.spawnEdge) {
      speed = new PVector(1, 0, 0);
    } else {
      speed = new PVector(random(-1, 1), random(-1, 1), 0);
    }
    Creeper a;
    if ((int) this.childSpawnType.get(babyCount) % 2 == 0) {
      a = new BabyCreeper(new PVector(px.x, px.y, px.z), speed, false, "a", true, this);
      this.creeperSet.add(a);
    } else {
      a = new BabyCreeper(new PVector(px.x, px.y, px.z), speed, false, "b", true, this);
      this.creeperSet.add(a);
    }
    babyCount++;
  }
}
// ------------------------Create Paths----------------------------------
public void keyPressed() {
  if (key == 'r')
    setup();
  if (key == 't')
    this.createTrails = !this.createTrails;
  if (key == '1')
    this.drawConn = !this.drawConn;
  if (key == 'b') 
    this.triggerBabies = !this.triggerBabies;    
  if (key == 'e')this.spawnEdge = !this.spawnEdge;
  if (key == 'd')this.renderMesh = !this.renderMesh;
  if (key == 'o') this.showOctree = !this.showOctree;
}