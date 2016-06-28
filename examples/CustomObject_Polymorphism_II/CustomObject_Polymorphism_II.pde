//--Another example of polymorphism, creating your own custom object which inherits from creeper objects and builds from it

//--POLYMORPHISM--
//Polymorphism is the ability for an object of one type to be treated as though it were another type.
//In Java, inheritance provides us one kind of polymorphism.
//An object of a subclass can be treated as though it were an object of its parent class, or any of its ancestor classes. This is also known as upcasting.
//Why is polymorphism useful? It allows us to create more generalized programs that can be extended more easily.

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

import java.util.List;
import culebra.objects.*;
import culebra.data.*;
import toxi.geom.*;
import toxi.color.*;

MyObject obj;
List<Creeper> objSet;

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
  objSet = new ArrayList<Creeper>();
  img0 = loadImage("LULZ.png");
  img2 = loadImage("SI.png");

  for (int i = 0; i < creepCount; i++) {
    loc = new PVector(random(0, width), random(0, height), 0);
    vel = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f), 0);
    if (i <= creepCount/2) {
      Creeper c = new Creeper(loc, vel, true, false, this); //--Use polymorphism to treat my object as if it was a creeper object--
      objSet.add(c);
    } else {
      MyObject c = new MyObject(loc, vel, true, false, this); //--Use polymorphism to treat my object as if it was a creeper object--
      objSet.add(c);
    }
  }
}
// -----------------------Draw-----------------------
public void draw() {
  background(0);
  pushStyle();
  stroke(255, 0, 0);
  strokeWeight(10);
  popStyle();

  for (Creeper d : objSet) {

    d.setMoveAttributes(2.5f, 1.0f, 1.5f);

    if (d instanceof MyObject) { //you can test if an object is a member of a specific type using the instanceof operator
      obj = (MyObject) d; //--Downcast to the subclass (MyObject) through explicit casting--
      //--Draw Head is a method of the MyObject class--By inheriting from the Creeper class you can have access to all the creeper methods plus implement your own methods in your custom class.
      obj.flocking();
      obj.drawHead();
      // --------Draw trails with color and with gradient--------
      float colorA[] = new float[] { 255.0f, 0f, 0f };
      obj.viz.drawTrails(obj.getTrailPoints(), false, colorA, 255.0f, 1.0f);
    } else {
      if (!(d instanceof MyObject)) {  
        float change = 100;
        d.behavior.superWander2D(change, 60.0f, 60.0f, 6.0f, "Parent"); 
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 1f, 0f, 0f };
        float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
        d.viz.drawGradientTrails(d.getTrailPoints(), 500, colorA, colorB, 255.0f, 1.0f);
      }
    }

    d.bounce(width, height);
    d.move(0, 500);
    /*
    //--Alternatively you could downcast right away and have access to all the methods for both classes--
     if (d instanceof  MyObject) {
     obj = (MyObject)d;
     obj.setMoveAttributes(2.5f, 1.0f, 1.5f);
     obj.behavior.flock2D(30.0f, 0.24f, 0.09f, 0.045f, objSet, this.drawConn);
     float change = 100;
     obj.behavior.superWander2D(change, 60.0f, 60.0f, 6.0f, "Parent"); 
     obj.bounce(width, height);
     obj.move(0, 500);
     obj.drawHead();
     }
     */
    //----------------------------------------------------------------
    //-----------------------------------------------
    if (createTrails) {
    }      

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