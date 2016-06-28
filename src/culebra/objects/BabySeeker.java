package culebra.objects;

import processing.core.PApplet;
import processing.core.PVector;
/**
 * Baby Seeker class which inherits from the Seeker Class - Object is meant to be used as a child of the Seeker Object 
 * @author elQuixote
 *
 */
public class BabySeeker extends Seeker {
	
	private String babyType = null;
	/**
	 * Constructor
	 * @param loc the location of the baby seeker object
	 * @param speed speed of the baby seeker object
	 * @param instanceable is the baby seeker instanceable
	 * @param babyType specifies which type of baby is created. Use either "a" or "b"
	 * @param In3D specifies if we are in 2D or 3D
	 * @param parent specifies the PApplet source
	 */
	public BabySeeker(PVector loc, PVector speed, boolean instanceable, String babyType, Boolean In3D, PApplet parent) {
		super(loc, speed, instanceable, In3D, parent);
		this.babyType = babyType;
		setChildAttributes();
	}
	/**
	 * Gets the type of baby
	 * @return the baby type as a string 
	 */
	public String getType(){
		return this.babyType;
	}
	private void setChildAttributes(){
		behavior.setObjChildType(this.babyType);
	}
}
