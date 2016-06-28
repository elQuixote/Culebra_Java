package culebra.viz;

import processing.core.PApplet;
import toxi.geom.PointQuadtree;
import toxi.geom.Vec2D;

/**
 * Class extends the PointOctree class from Toxiclibs.
 * @author Toxiclibs
 *
 */
public class QuadTree extends PointQuadtree {

	private PApplet p;
	/**
	 * Constructor
	 * @param location
	 * @param width
	 * @param parent
	 */
	public QuadTree(Vec2D location, float width, PApplet parent) {
		super(location, width);
		this.p = parent;
	}
	/**
	 * Draws the quadtree
	 */
	public void draw() {
		drawNode(this);
	}
	/**
	 * Draws the nodes of the quadTree
	 * @param quadTree the quadTree
	 */
	protected void drawNode(PointQuadtree quadTree) {
		if (quadTree.getNumChildren() > 0) {
			this.p.noFill();
			this.p.stroke(200, 20);
			this.p.pushMatrix();
			this.p.translate(quadTree.x, quadTree.y);
			this.p.box(quadTree.getNodeSize());
			this.p.popMatrix();
			PointQuadtree[] childNodes = quadTree.getChildren();
			for (int i = 0; i < 4; i++) {
				if (childNodes[i] != null)
					drawNode(childNodes[i]);
			}
		}
	}
}
