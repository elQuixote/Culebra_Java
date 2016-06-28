//--SuperSaiyan mode simulated charging up and exploding while keeping network connections together and slowly finding an equlibrium
//--Hold the key 'd' down for a few seconds then release--
//--wait 15-20 seconds and the network will chill out--

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.viz.Path;
import culebra.data.*;
import toxi.geom.*;
import toxi.color.*;

Creeper creep;
List<Creeper> creeperSet;
Seeker creeperTracker;

PVector loc;
PVector vel;
float maxVel = 10;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean flockFlag = false;
boolean drawConn = true;
boolean enableSuperSaneCharge = false;
boolean enableSuperSaneExplode = false;
boolean wanderFlag = false;
// -------------------Environ Stuff--------------------
int creepCount = 500;
PImage img0, img2;
// -----------------------Setup-----------------------
public void setup() {
  size(1820, 980, FX2D);
  smooth();
  background(0);
  maxVel = 10;
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
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(6.0f, 1.0f, 1.5f);
    /*
    if (this.wanderFlag) {
     if (!this.enableSuperSaneExplode) {
     c.setMoveAttributes(6f, 1.0f, 1.5f);
     } else {
     c.setMoveAttributes(10f, 1.0f, 1.5f);
     }
     float change = 2;
     c.behavior.wander2D(true, false, change, 20.0f, 80.0f);
     //c.behavior.flock2D(180.0f, 0.0f, 0.0f, 0.0f, 360, creeperSet, true);
     c.behavior.setBehaviorType("Flocker");
     }
     */
    if (this.enableSuperSaneCharge) {
      c.setMoveAttributes(5.5f, 1.0f, 1.5f);
      c.behavior.creeperCohesion(400.0f, 1.0f, creeperSet);
      c.behavior.creeperSeparate(50.0f, 1.5f, creeperSet);
    }
    if (this.enableSuperSaneExplode) {
      maxVel -= 0.00001;
      if (maxVel < 2.5) maxVel = 2.5;
      c.setMoveAttributes(maxVel, 5.0f, 1.5f);
      c.behavior.creeperflock2D(180.0f, 0.0f, 0.5f, 0.0f, 360, creeperSet, this.drawConn);
    }
    c.bounce(width, height);
    c.move(0, 50);
    if (createTrails) {
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 1f, 0f, 0f };
      float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
      c.viz.drawGradientTrailsFX(c.getTrailPoints(), 50, colorA, colorB, 255.0f, 1.0f);
    }      
    pushStyle();
    stroke(255);
    strokeWeight(4);
    ellipse(c.getLocation().x, c.getLocation().y, 2, 2);
    popStyle();

    image(img2, width-290, height-85);
    image(img0, 0, height-105);
    textSize(20);
    text("Framerate: " + (frameRate), 80, height - 6);
  }
  surface.setSize(width, height);
}
// ------------------------Create Paths----------------------------------
public void keyPressed() {
  if (key == 'R')
    setup();
  if (key == 'T')
    this.createTrails = !this.createTrails;
  if (key == '1')
    this.drawConn = !this.drawConn;   
  if (key == 'Q') {
    this.enableSuperSaneExplode = false;
    this.enableSuperSaneCharge = false;
  }
  if (key == 'D') {
    this.enableSuperSaneCharge = !this.enableSuperSaneCharge;
    this.enableSuperSaneExplode = false;
  }
  if (key == 'W')
    this.wanderFlag = !this.wanderFlag;
}
public void keyReleased() {
  if (key == 'D') {
    this.enableSuperSaneExplode= !this.enableSuperSaneExplode;
    this.enableSuperSaneCharge = false;
  }
}