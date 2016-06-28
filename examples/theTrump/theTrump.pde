//--Well all objects run away from the trump. Unfortunately this is not always what happens in real life

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

PImage img, img2, img3, img4;
PVector loc;
PVector vel;
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean drawConn = false;
// ---------------------Camera and Dims---------------
boolean D2 = true;
boolean D3 = false;
// -------------------Environ Stuff--------------------
int creepCount = 1000;
int maxTrail = 25;
// ------------------------Trump----------------------
Trump trump;
// -----------------------Setup-----------------------
public void setup() {
  size(1400, 800, P2D);
  smooth();
  background(0);
  
  this.img = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");
  this.img3 = loadImage("trumper2_NH.png");
  this.img4 = loadImage("trumper_NH.png");
  
  this.creeperSet = new ArrayList<Creeper>();

  PVector tloc = new PVector(random(0, width), random(0, height), 0);
  PVector tspeed = new PVector(random(-1, 1), random(-1, 1), 0);
  this.trump = new Trump(tloc, tspeed, true, this.D3, this);

  for (int i = 0; i < creepCount; i++) {
    this.loc = new PVector(random(0, width), random(0, height), 0);
    this.vel = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
    this.creep = new Creeper(loc, vel, true, this.D3, this);
    this.creeperSet.add(this.creep);
  }
}
// -----------------------Draw-----------------------
public void draw() {
  background(0);
  trump.setMoveAttributes(4.0f, 0.169f, 1.5f);
  trump.behavior.wander2D(true, true, 1, 66.0f, 66.0f);
  trump.bounce(width, height);
  if(trump.getSpeed().x<0){
      image(img4, trump.getLocation().x-50, trump.getLocation().y-50);
  }else{
      image(img3, trump.getLocation().x-50, trump.getLocation().y-50);
  }
  trump.move();

  pushStyle();
  stroke(255, 0, 0);
  strokeWeight(15);
  //point(trump.getLocation().x, trump.getLocation().y, trump.getLocation().z);
  popStyle();

  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(2.5f, 0.099f, 0.0015f);
    c.behavior.repelTrump(trump, 200, 20, 20);

    float change = 0.5;
    c.behavior.wander2D(true, true, change, 66.0f, 66.0f);

    float distance = c.getLocation().dist(trump.getLocation());

    //c.respawn(width, height);
    c.bounce(width, height);

    c.move(0, (int)distance/20);
    if (createTrails) {
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 1f, 0f, 0f };
      float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
      c.viz.drawGradientTrails(c.getTrailPoints(), (int)distance, colorA, colorB, 255.0f, 1.0f);
      // --------Draw trails with single color and as single trail--------
      //float colorA[] = new float[] {255f, 255f, 0f};
      //c.viz.drawTrails(c.getTrailPoints(), false, maxTrail, colorA, 255, 1);
    }      
    pushStyle();
    stroke(255);
    strokeWeight(4);
    point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
    popStyle();
  }
  if (this.D2) {
    surface.setSize(width, height);
  }
  image(img2, width-290, height-85);
  image(img, 0, height-105);
  textSize(20);
  text("Framerate: " + (frameRate), 80, height - 6);
}
// ------------------------Keys----------------------------------
public void keyPressed() {
  if (key == 'r')
    setup();
  if (key == 't')
    this.createTrails = !this.createTrails;
}