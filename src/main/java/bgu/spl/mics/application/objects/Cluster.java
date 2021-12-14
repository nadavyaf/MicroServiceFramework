package bgu.spl.mics.application.objects;


import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

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
private LinkedBlockingQueue<DataBatch> cpuQueue= new LinkedBlockingQueue<DataBatch>();
	/**
     * Retrieves the single instance of this class.
     */
	private static class SingletonHolder {//Java things, this way when we import messagebusimpl, it will not create any instance (since the funcion is private), but when we call the function, it will just call the .instance once.
		private static Cluster instance = new Cluster();
	}

	public static Cluster getInstance() {
		return SingletonHolder.instance;
	}



	public void sendToGPU(DataBatch process) {
	process.gotCreatedGpu().insertProcessedCPU(process);
	}
	public void sendToCPU(DataBatch process) {
		cpuQueue.add(process);
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

	public LinkedList<GPU> getGPUS() {
		return GPUS;
	}

	public LinkedList<CPU> getCPUS() {
		return CPUS;
	}

	public LinkedBlockingQueue<DataBatch> getCpuQueue() {
		return cpuQueue;
	}
}
