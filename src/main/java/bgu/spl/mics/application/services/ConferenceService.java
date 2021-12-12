package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;

import java.util.LinkedList;
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
    public ConferenceService(String name) {
        super(name);
        this.cfsList = new LinkedBlockingQueue<>();
    }
    public void addToConference(String name){
        cfsList.add(name);
    }

    public LinkedBlockingQueue<String> getCfsList() {
        return cfsList;
    }

    @Override
    protected void initialize() {
        // TODO Implement this

    }
    /**
     * Assiph's comment:In this Service we should send PublishConferenceBroadcast, in a similar way we did in studentservice. Note that
     * this is a Broadcast, we don't wait for an answer, we just update the system.
     */

}
