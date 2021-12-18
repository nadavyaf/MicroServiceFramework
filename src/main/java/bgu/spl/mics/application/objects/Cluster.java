package bgu.spl.mics.application.objects;


import java.lang.reflect.Array;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
private LinkedBlockingQueue<GPU> GPUS = new LinkedBlockingQueue<>();
private LinkedBlockingQueue<CPU> CPUS = new LinkedBlockingQueue<>();
private Statistics statistics=new Statistics();
private ConcurrentHashMap<Integer,LinkedBlockingQueue<DataBatch>> rtx3090 = new ConcurrentHashMap<>();
private ConcurrentHashMap<Integer,LinkedBlockingQueue<DataBatch>> rtx2080 = new ConcurrentHashMap<>();;
private ConcurrentHashMap<Integer,LinkedBlockingQueue<DataBatch>> gtx1080 = new ConcurrentHashMap<>();;
private AtomicInteger rtx3090Counter = new AtomicInteger(0);
private AtomicInteger rtx2080Counter = new AtomicInteger(0);
private AtomicInteger gtx1080Counter = new AtomicInteger(0);
private int[] array = new int[]{1,1,2,1,1,2,4};
private AtomicInteger arrayCounter = new AtomicInteger(0);
private LinkedBlockingQueue<DataBatch> cpuQueue= new LinkedBlockingQueue<DataBatch>();

	public void removeQueue(GPU.Type type, LinkedBlockingQueue<DataBatch> dataList) {
		if (type.equals(GPU.Type.RTX3090)) //need to check if it changes the size
			while (rtx3090.values().remove(dataList)) ;
		if (type.equals(GPU.Type.RTX2080))
			while (rtx2080.values().remove(dataList));
		if (type.equals(GPU.Type.GTX1080))
			while (gtx1080.values().remove(dataList));
	}

	public DataBatch takeCPU() throws InterruptedException {
//		synchronized (Cluster.getInstance().getCpuQueue()) {
//                if (!Cluster.getInstance().getCpuQueue().isEmpty()) {
//                    currDataBatch = Cluster.getInstance().getCpuQueue().take();
//                    updated = true;
//                }
//            }
			DataBatch toProcess = null;
			Boolean took = false;
			int take = array[arrayCounter.get()%7];
			if (take == 1) {
				toProcess = takeFromMap(GPU.Type.RTX3090,rtx3090, rtx3090Counter);
				if (toProcess != null) {
					took = true;
					arrayCounter.getAndIncrement();
				}
				take = 2;
			}
			if (take == 2 && !took) {
				toProcess = takeFromMap(GPU.Type.RTX2080,rtx2080, rtx2080Counter);
				if (toProcess != null) {
					took = true;
					arrayCounter.getAndIncrement();
				}
				take = 4;
			}
			if (take == 4 && !took) {
				toProcess = takeFromMap(GPU.Type.GTX1080,gtx1080, gtx1080Counter);
				if (toProcess!=null)
					arrayCounter.getAndIncrement();
			}
			return toProcess;
	}

	private DataBatch takeFromMap(GPU.Type type,ConcurrentHashMap<Integer,LinkedBlockingQueue<DataBatch>> map,AtomicInteger point) throws InterruptedException {
	DataBatch take = null;
	if (map.isEmpty())
		return null;
	int key = point.get()%map.size();
		if (map.get(key) != null) {
			take = map.get(key).take(); //Can't be null - else would be deleted from the map.
			point.getAndIncrement();
			if (map.get(key).isEmpty()) {
				Cluster.getInstance().removeQueue(type, map.get(key));
			}
		}

	return take;
	}
	/**
     * Retrieves the single instance of this class.
     */
	private static class SingletonHolder {//Java things, this way when we import messagebusimpl, it will not create any instance (since the funcion is private), but when we call the function, it will just call the .instance once.
		private static Cluster instance = new Cluster();
	}

	public static Cluster getInstance() {
		return SingletonHolder.instance;
	}



	public void sendToGPU(DataBatch process) throws InterruptedException {
	process.gotCreatedGpu().insertProcessedCPU(process);
	}
	public void sendToCPU(GPU.Type type,LinkedBlockingQueue process) {
		if (type == GPU.Type.RTX3090)
			rtx3090.putIfAbsent(rtx3090.size(), process);
		if (type == GPU.Type.RTX2080)
			rtx2080.putIfAbsent(rtx2080.size(),process);
		if (type == GPU.Type.GTX1080)
			gtx1080.putIfAbsent(gtx1080.size(),process);
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

	public LinkedBlockingQueue<GPU> getGPUS() {
		return GPUS;
	}

	public LinkedBlockingQueue<CPU> getCPUS() {
		return CPUS;
	}

	public LinkedBlockingQueue<DataBatch> getCpuQueue() {
		return cpuQueue;
	}
}
