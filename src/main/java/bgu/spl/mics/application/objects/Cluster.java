package bgu.spl.mics.application.objects;


import java.util.LinkedList;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
private LinkedList<GPU> GPUS = new LinkedList<>();
private LinkedList<CPU> CPUS = new LinkedList<>();
private Statistics statistics=new Statistics();
	/**
     * Retrieves the single instance of this class.
     */
	private static class SingletonHolder {//Java things, this way when we import messagebusimpl, it will not create any instance (since the funcion is private), but when we call the function, it will just call the .instance once.
		private static Cluster instance = new Cluster();
	}

	public static Cluster getInstance() {
		return SingletonHolder.instance;
	}
	/** Assiph's Comments: A thread safe singleton - you can copy the same idea from Messagebus
	 for creating a singleton.
	 "Note that the GPUs and CPUs communicate via the cluster, they are not
	 allowed to directly call each other methods" - it means here we need to add functions that will make the cpu and the gpu "talk"
	 with each other.*/


	public void sendToGPU(DataBatch process) {
	process.gotCreatedGpu().insertProcessedCPU(process);
	}
	public void sendToCPU(DataBatch process) {// Still not sure, need to think of a smart way to distribute the GPU Batches.

	}
	public void addGPU(GPU gpu){
		GPUS.add(gpu);
	}
	public void addCPU(CPU cpu)
	{
		CPUS.add(cpu);
	}

	public Statistics getStatistics() {
		return statistics;
	}
}
