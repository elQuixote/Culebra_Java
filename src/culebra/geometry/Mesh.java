package culebra.geometry;

import toxi.geom.mesh.*;
import toxi.processing.ToxiclibsSupport;
import processing.core.PApplet;
/**
 * Mesh Geometry Class using toxiclibs 
 *
 */
public class Mesh {
	  private TriangleMesh mesh;
	  private TriangleMesh meshDisplay;
	  private PApplet p;
	  private ToxiclibsSupport gfx;
	  /**
	   * 
	   * @param meshToOperate mesh to work on
	   * @param displayMesh mesh to display, this can either by the same mesh as above or a different mesh
	   * @param parent the PApplet source
	   */
	  public Mesh(String meshToOperate, String displayMesh, PApplet parent) {
		this.p = parent;
		this.gfx = new ToxiclibsSupport(parent);
		this.mesh = (TriangleMesh) new STLReader().loadBinary(this.p.sketchPath(meshToOperate), STLReader.TRIANGLEMESH);
		this.mesh.computeVertexNormals();
		this.meshDisplay = (TriangleMesh) new STLReader().loadBinary(this.p.sketchPath(displayMesh), STLReader.TRIANGLEMESH);
		this.meshDisplay.computeVertexNormals();
	  }
	  /**
	   * Renders the Mesh Object
	   */
	  public void renderMesh(){
		this.p.pushStyle();
		this.p.stroke(50, 100);
		this.p.strokeWeight(1);
	    //noFill();
	    //noStroke();
		this.p.fill(100, 255);
		this.gfx.mesh(meshDisplay);
	    this.p.popStyle();
	  }
	  /**
	   * Gets the triangulated mesh object
	   * @return the mesh 
	   */
	  public TriangleMesh getMesh(){
		  return this.mesh;
	  }
}
