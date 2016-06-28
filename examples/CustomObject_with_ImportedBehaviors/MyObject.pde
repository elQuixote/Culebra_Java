
import culebra.behaviors.types.Noise; //imports the improved perlin noise class
import culebra.viz.Viz; //imports the culebra viz class
import culebra.data.Data; //import the culebra data class
import java.util.Random;

class MyObject {

  ArrayList<PVector> objectTrails;
  PVector speed, loc;
  PVector dir = new PVector(0, 0, 0);
  PVector acc = new PVector(0, 0, 0);
  float vel = 2.0;
  float max = 4.0;
  Random r;

  Viz vizualization;
  Noise pnoise;

  public MyObject(PVector l, PVector s, PApplet p) {
    speed = s;
    loc = l;
    vizualization = new Viz(p);
    pnoise = new Noise();
    objectTrails = new ArrayList<PVector>();
  }
  public void drawHead() {
    pushStyle();
    stroke(255);
    strokeWeight(6);
    ellipse(loc.x, loc.y, 1, 1);
    popStyle();
  }
  public void run() {
    runNoise();
    move();
    respawn(width,height);
    drawHead();
    drawTrails();
  }
  public void respawn(int width, int depth) {
    r = new Random();
    if (loc.x > width || loc.x < 0 || loc.y > depth || loc.y < 0) {
      float x = Data.getRandomNumbers(0.0, width, r);
      float y = Data.getRandomNumbers(0.0, depth, r);
      loc = new PVector(x, y, 0);
      objectTrails = new ArrayList<PVector>();
    }
  }
  public void runNoise() {
    //--use culebra improved perlin noise class--
    dir = pnoise.perlin(100, 30.0, 1.0, 1.0, loc, false);
  }
  public void move() {
    acc.add(dir);
    speed.add(acc);
    speed.normalize();
    speed.mult(vel);
    speed.limit(max);
    loc.add(speed);
    objectTrails.add(new PVector(loc.x, loc.y, loc.z));
    acc = new PVector();
  }
  public void drawTrails() {
    float colorA[] = new float[] { 1f, 0f, 0f };
    float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
    //--use culebra viz class to draw gradient trails--
    vizualization.drawGradientTrailsFX(objectTrails, 1000, colorA, colorB, 255.0f, 1.0f);
  }
}