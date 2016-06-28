//--In this example we will import standalone behavior types, vizualization and data classes to use in our class--

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.data.*;
import toxi.geom.*;
import toxi.color.*;

MyObject obj;
List<MyObject> objSet;

PVector loc;
PVector vel;
// -------------------Environ Stuff--------------------
int objCount = 500;
PImage img0, img2;
// -----------------------Setup-----------------------
public void setup() {
  size(1400, 800, FX2D);
  smooth();
  background(0);
  objSet = new ArrayList<MyObject>();
  img0 = loadImage("LULZ.png");
  img2 = loadImage("SI.png");

  for (int i = 0; i < objCount; i++) {
    loc = new PVector(random(0, width), random(0, height), 0);
    vel = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
    MyObject c = new MyObject(loc, vel,this); 
    objSet.add(c);
  }
}
// -----------------------Draw-----------------------
public void draw() {
  background(0);
  pushStyle();
  stroke(255, 0, 0);
  strokeWeight(10);
  popStyle();

  for (MyObject obj : objSet) {
    obj.run();
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
}