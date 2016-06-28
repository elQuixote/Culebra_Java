package culebra.objects;

import processing.core.PApplet;
import processing.core.PVector;
/**
 * The Trump Object is an object from which all other objects want to repel from and quickly. Again I have not locked out the behavior capabilities of this object yet but will do so soon.
 * It can currently do as it wishes, much like in real life.
 * @author elQuixote
 *
 */
public class Trump extends Creeper{
	/**
	 * Constructor
	 * @param loc the location of the creeper object
	 * @param speed speed of the creeper object
	 * @param instanceable is the creeper instanceable
	 * @param In3D specifies if we are in 2D or 3D
	 * @param parent specifies the PApplet source
	 */
	public Trump(PVector loc, PVector speed, Boolean instanceable, Boolean In3D, PApplet parent) {
		super(loc, speed, instanceable, In3D, parent);
	}
}
