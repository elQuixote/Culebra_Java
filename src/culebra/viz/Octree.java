package culebra.viz;

import toxi.geom.*;
import processing.core.*;

/**
 * Class extends the PointOctree class from Toxiclibs.
 * @author Toxiclibs
 *
 */
public class Octree extends PointOctree {

	private PApplet p;
	/**
	 * Constructor
	 * @param position 
	 * @param width
	 * @param parent
	 */
	public Octree(Vec3D position, float width, PApplet parent) {
		super(position, width);
		this.p = parent;
	}
	/**
	 * Draws the octree
	 */
	public void draw() {
		drawNode(this);
	}
	/**
	 * Draws the octree
	 * @param tree the octree
	 */
	protected void drawNode(PointOctree tree) {
		if (tree.getNumChildren() > 0) {
			this.p.noFill();
			// stroke(n.getDepth(), 20);
			this.p.stroke(200, 20);
			this.p.pushMatrix();
			this.p.translate(tree.x, tree.y, tree.z);
			this.p.box(tree.getNodeSize());
			this.p.popMatrix();
			PointOctree[] childNodes = tree.getChildren();
			for (int i = 0; i < 8; i++) {
				if (childNodes[i] != null)
					drawNode(childNodes[i]);
			}
		}
	}
}