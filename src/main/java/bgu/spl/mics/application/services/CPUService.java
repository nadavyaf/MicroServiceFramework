package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback_Terminate;
import bgu.spl.mics.Callback_TickBroadcastCPU;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Cluster;

/**
 * CPU service is responsible for handling the {@link //DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private CPU cpu;
    private Callback_TickBroadcastCPU callback;
    private Callback_Terminate terminate;
    public CPUService(String name,CPU cpu) {
        super(name);
        this.cpu = cpu;
        callback = new Callback_TickBroadcastCPU(this.cpu);
        terminate = new Callback_Terminate(this);
    }

    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        this.subscribeBroadcast(TickBroadcast.class,callback);
        this.subscribeBroadcast(TerminateBroadcast.class,terminate);
        Cluster.getInstance().addCPU(cpu);
    }

}
