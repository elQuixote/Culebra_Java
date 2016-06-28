
class MyObject extends Creeper {

  MyObject(PVector a, PVector b, boolean c, boolean d, PApplet parent) {
    //invoke the super class constructor
    super(a, b, c, d, parent);
  }
  
  void drawHead() {
    pushStyle();
    stroke(255);
    strokeWeight(6);
    point(obj.getLocation().x, obj.getLocation().y, obj.getLocation().z);
    popStyle();
  }
  
  void flocking(){
    this.behavior.creeperflock2D(30.0f, 0.24f, 0.09f, 0.045f, objSet, false);
  }
  
  //you can define any other types of behaviors or custom features here.
}