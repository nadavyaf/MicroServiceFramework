package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.util.LinkedList;
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
    private LinkedList<Model> models;
    private Student student;
    private LinkedBlockingQueue<Message> MessageQueue=null;
    private Callback_PublishConferenceBroadcast publishConference;
    private Callback_Terminate terminate;
    public StudentService(String name,Student student,LinkedList<Model> models) {
        super(name);
        this.student=student;
        this.models=models;
        publishConference = new Callback_PublishConferenceBroadcast();
        terminate = new Callback_Terminate();
    }

    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        this.subscribeBroadcast(TerminateBroadcast.class,terminate);
        this.subscribeBroadcast(PublishConferenceBroadcast.class,publishConference);
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
     * mbs.sendEvent()). The 3 events should be TrainModelEvent,TestModelEvent,PublishResultsEvent.
     *
     *
     */
}
