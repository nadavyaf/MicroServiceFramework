package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.ConfrenceInformation;
import bgu.spl.mics.application.objects.Model;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link //PublishConfrenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {
    private LinkedBlockingQueue<Model> cfsList;
    private ConfrenceInformation cfi;
    private Callback_Terminate terminate;
    private Callback_PublishResultsEvent publishEvent;
    private Callback_TickBroadcastConference tick;
    public ConferenceService(String name,ConfrenceInformation cfi) {
        super(name);
        this.cfsList = new LinkedBlockingQueue<>();
        this.cfi=cfi;
        terminate = new Callback_Terminate(this);
        publishEvent = new Callback_PublishResultsEvent(this);
        tick = new Callback_TickBroadcastConference(this);
    }
    public void addToConference(Model model){
        cfsList.add(model);
    }

    public LinkedBlockingQueue<Model> getCfsList() {
        return cfsList;
    }

    public ConfrenceInformation getCfi() {
        return cfi;
    }

    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        this.subscribeBroadcast(TickBroadcast.class,tick);
        this.subscribeBroadcast(TerminateBroadcast.class,terminate);
        this.subscribeEvent(PublishResultsEvent.class,publishEvent);
    }
    /**
     * Assiph's comment:In this Service we should send PublishConferenceBroadcast, in a similar way we did in studentservice. Note that
     * this is a Broadcast, we don't wait for an answer, we just update the system.
     */

}
