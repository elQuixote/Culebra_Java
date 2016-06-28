//--Creepers chase their own tails 3D--

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.viz.*;
import culebra.data.*;

import toxi.geom.*;
import toxi.color.*;
import peasy.*;

List<PVector> totTail;

Creeper creep;
List<Creeper> creeperSet;
Seeker creeperTracker;
PVector loc;
PVector vel;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean flockFlag = false;
boolean drawConn = true;
// -------------------Environ Stuff--------------------
int creepCount = 500;
PImage img0, img2;
// -------------------Spawn Stuff--------------------
boolean spawnGround = false;
boolean spawnEdge = false;
// ----------------------Camera-------------------------
Cameras cams;
int camToggle = 0;
// -------------------Environ Stuff--------------------
int nHeight = 1000;
// -----------------------Setup-----------------------
public void setup() {
  size(1400, 800, P3D);
  smooth();
  background(0);
  this.creeperSet = new ArrayList<Creeper>();
  this.img0 = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");
  
  totTail = new ArrayList<PVector>();

  this.cams = new Cameras(this);
  if (this.camToggle < 1) {
    this.camToggle++;
    int[] lookat = new int[] { this.width / 2, this.height / 2, this.nHeight / 2 };
    this.cams.set3DCamera(1500, 100, 10000, lookat, true);
  }

  for (int i = 0; i < creepCount; i++) {
    this.loc = new PVector(random(0, width), random(0, height), 0);
    this.vel = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);


    if (this.spawnGround) {
      this.loc = new PVector(random(width), random(height), 0);
      this.vel = new PVector(random(-1, 1), random(-1, 1), random(0, 2));
    } else if (this.spawnEdge) {
      this.loc = new PVector(random(width), random(height), 0);
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
  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(2.5f, 0.0f, 1.5f);
    totTail.addAll(c.getTrailPoints());
    c.behavior.selfTailChaser(60.0f, 1.5f, 80.0f, 0.00f, 5.00f, totTail);
    c.bounce(width, height, nHeight);
    c.move(6, 20);
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
    point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
    popStyle();

    image(img2, width-290, height-85);
    image(img0, 0, height-105);
    textSize(20);
    text("Framerate: " + (frameRate), 80, height - 6);
  }
  this.totTail.clear();

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
// ------------------------Create Paths----------------------------------
public void keyPressed() {
  if (key == 'r')
    setup();
  if (key == 't')
    this.createTrails = !this.createTrails;
  if (key == '1')
    this.drawConn = !this.drawConn;
}