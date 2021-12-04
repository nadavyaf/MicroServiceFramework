package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.objects.GPU;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * GPU service is responsible for handling the
 * {@link //TrainModelEvent} and {@link //TestModelEvent},
 * in addition to sending the {@link //DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
    final private GPU gpu;
    public GPUService(String name, GPU gpu) {
        super("Change_This_Name");
        // TODO Implement this
        this.gpu = gpu;
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
    public LinkedBlockingQueue<Message> getMessageQueue() {
        return gpu.getEventQueue();
    }
}
