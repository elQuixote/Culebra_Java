//--2D Flocking - this example adds an angle parameter which allows agents to see only within the angle specified--

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
PVector loc;
PVector vel;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean flockFlag = false;
boolean drawConn = true;
// -------------------Environ Stuff--------------------
int creepCount = 500;
PImage img0, img2;
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
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(2.5f, 0.0f, 1.5f);
    c.behavior.creeperflock2D(80.0f, 0.24f, 0.09f, 0.045f, 60, creeperSet, this.drawConn);
    c.bounce(width, height);
    c.move(0, 500); //min number of steps before it records trail position, max number of trail points, good for performance
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
  surface.setSize(width, height);
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