package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.ConfrenceInformation;

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
    private LinkedBlockingQueue<String> cfsList;
    private Callback_Terminate terminateCallback;
    private ConfrenceInformation cfi;
    private Callback_PublishResultsEvent publishResultsCallback;
    private Callback_TickBroadcastConference tickCallback;
    public ConferenceService(String name, ConfrenceInformation cfi) {
        super(name);
        this.cfsList = new LinkedBlockingQueue<>();
        this.terminateCallback = new Callback_Terminate(this);
        this.cfi = cfi;
        this.publishResultsCallback = new Callback_PublishResultsEvent(this);
        this.tickCallback = new Callback_TickBroadcastConference(this);
    }

    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        this.subscribeBroadcast(TerminateBroadcast.class, this.terminateCallback);
        this.subscribeEvent(PublishResultsEvent.class, this.publishResultsCallback);
        this.subscribeBroadcast(TickBroadcast.class, this.tickCallback);
    }

    public LinkedBlockingQueue<String> getCfsList() {
        return cfsList;
    }

    public void addToCfs(String name){
        this.getCfsList().add(name);
    }

    public Callback_Terminate getTerminateCallback() {
        return terminateCallback;
    }

    public ConfrenceInformation getCfi() {
        return cfi;
    }

}
