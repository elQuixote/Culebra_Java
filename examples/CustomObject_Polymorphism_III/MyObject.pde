//--MyObject will inherit from Creeper--
class MyObject extends Creeper {

  public MyObject(PVector a, PVector b, boolean c, boolean d, PApplet parent) {
    super(a, b, c, d, parent); //invoke the super class constructor
  }
  public void drawHead() {
    pushStyle();
    stroke(255);
    strokeWeight(6);
    point(obj.getLocation().x, obj.getLocation().y, obj.getLocation().z);
    popStyle();
  }
  public void flocking() {
    //--Since Creeper derrives from culebra.objects.Object and since Object has the controller in it then we can access the behavior controller and its methods.--
    this.behavior.flock2D(30.0f, 0.24f, 0.09f, 0.045f, 360, objSet, false);
    
    //--Another way we can acess the superclass methods is by explicitly invoking an ancestor class’s implementation of a method by prefixing super. to the method call. For example:
    super.behavior.wander2D(2.0, 60.0f, 60.0f);
  }
}