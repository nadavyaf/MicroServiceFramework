package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.GPU;

import java.util.HashMap;

/**
 * GPU service is responsible for handling the
 * {@link //TrainModelEvent} and {@link //TestModelEvent},
 * in addition to sending the {@link //DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {/** Assiph's comments: I think this service should run between 3 queues - the timeTickQueue
 the eventQueue and the processedCPUQueue, and check each time if they have something inside. if the timeTick has, then we update the time,
 if the eventQueue has then we check if we are currently working on an event (model==null), and if the processedCPU has, then
 to send to the gpu to work on the processed databatch. In the end of the checking (it should be in an infinite while loop, or until interrupted)
  we should add a wait() method, so we don't implement busy waiting (it will get notified each time something is updated).
 I also think we need to add a thread for this class, so when we initialize it, the thread will start running on it's own.
 *******We also need to add a new Hashmap for the GPUService - subscribedMap which will hold eventTypes and Callbacks, and
 tell each message that comes from the awaitmessage, which callback to do.
 are their call backs. */
    final private GPU gpu;
    private HashMap<Class < ? extends Message>,Callback> callbackMap;
    public GPUService(String name, GPU gpu) {
        super(gpu + " " + "service");
        this.gpu = gpu;
        this.callbackMap = new HashMap<>();
    }
    @Override
    protected void initialize() {
        // TODO Implement this
    }
    public Boolean isEventSubscribed(Event e){
        return MessageBusImpl.getInstance().isMicroServiceEventRegistered(this,e);
    }
    public Boolean isBroadcastSubscribed(Broadcast b){
        return MessageBusImpl.getInstance().isMicroServiceBroadCastRegistered(this,b);
    }

}
/**
 * Assiph's Comment: in GPU and CPU, both of the Services should be used to send and bring messages. So in GPU case, the
 * GPUService should be responsible for bringing the data to the GPU, and putting it in the right Queues, and also after 2 scenarios:
 * 1. The GPU will break the data into DataBatches, the GPUService should send them in a good manner to the cluster (without clogging the Cpus)
 * 2. The GPU should take the processed databatches from the Cluster, and insert them into the LearnQueue. From there it should be
 * the GPU responsibility to process the data.
 * In both cases, the GPUService should call the GPU functions - DivideALL and Divide1000 in case 1, and in case 2 it should
 * call the GPULearn function.
 * Im still not sure if we should change the GPULearn function to get a Queue of Databatches and make the GPU learn them all,
 * or we should keep it as is, and everytime the GPUService gets a new processed Databatch, it will call the function.
 */

