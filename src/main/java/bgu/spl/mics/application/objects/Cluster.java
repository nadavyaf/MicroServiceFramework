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
	private LinkedList<GPU> gpus = new LinkedList<>();
	private LinkedList<CPU> cpus = new LinkedList<>();
	private Statistics statistics = new Statistics();
	private LinkedBlockingQueue<DataBatch> cpuQueue = new LinkedBlockingQueue<>();


	private static class SingletonHolder {//Java things, this way when we import messagebusimpl, it will not create any instance (since the funcion is private), but when we call the function, it will just call the .instance once.
		private static Cluster instance = new Cluster();
	}


	/**
     * Retrieves the single instance of this class.
     */
	public static Cluster getInstance(){
		return SingletonHolder.instance;
	}

	public void sendGPU(DataBatch data){
		GPU gpu = data.getCreatedGPU();
		gpu.insertProcessedCPU(data);
	}

	public void sendCPU(DataBatch data){
		cpuQueue.add(data);
	}

	public void addGPU(GPU gpu){
		gpus.add(gpu);
	}

	public void addCPU(CPU cpu){
		cpus.add(cpu);
	}

	public LinkedList<GPU> getGpus() {
		return gpus;
	}

	public LinkedList<CPU> getCpus() {
		return cpus;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public LinkedBlockingQueue<DataBatch> getCpuQueue() {
		return cpuQueue;
	}
}
