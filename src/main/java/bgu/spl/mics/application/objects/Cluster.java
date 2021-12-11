package bgu.spl.mics.application.objects;


/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {


	/**
     * Retrieves the single instance of this class.
     */
	public static Cluster getInstance() {/** Assiph's Comments: A thread safe singleton - you can copy the same idea from Messagebus
	 for creating a singleton.
	 "Note that the GPUs and CPUs communicate via the cluster, they are not
	 allowed to directly call each other methods" - it means here we need to add functions that will make the cpu and the gpu "talk"
	 with each other.*/
	 //TODO: Implement this
		return null;
	}

}
