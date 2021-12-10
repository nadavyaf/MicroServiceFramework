package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.TestModelEvent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Student is responsible for sending the {@link //TrainModelEvent},
 * {@link TestModelEvent} and {@link //PublishResultsEvent}.
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class StudentService extends MicroService {
    private LinkedBlockingQueue<Message> MessageQueue=null;
    public StudentService(String name) {
        super("Change_This_Name");
        // TODO Implement this
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
        return MessageQueue;
    }

    /**
     * Assiph's comments:In here we should create 3 send events that use the message bus (let messagebus be mbs, so we will use
     * mbs.sendEvent()). The 2 events should be TrainModelEvent,TestModelEvent,PublishResultsEvent.
     *
     *
     */
}
