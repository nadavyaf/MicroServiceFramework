package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
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
    private Callback_FinishedBroadcast finished;
    private Callback_Terminate terminate;
    private Future <Model> future;
    private int currModel;
    public StudentService(String name,Student student,LinkedList<Model> models) {
        super(name);
        this.student=student;
        this.models=models;
        this.future = new Future<>();
        this.publishConference = new Callback_PublishConferenceBroadcast(this);
        terminate = new Callback_Terminate(this);
        currModel=0;
        this.finished = new Callback_FinishedBroadcast(this);
    }

    @Override
    protected void initialize() throws InterruptedException {
        MessageBusImpl.getInstance().register(this);
        this.subscribeBroadcast(TerminateBroadcast.class,terminate);
        this.subscribeBroadcast(PublishConferenceBroadcast.class,publishConference);
        this.subscribeBroadcast(FinishedBroadcast.class,this.finished);
        if (!models.isEmpty())
        this.future=this.sendEvent(new TrainModelEvent(models.getFirst()));
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

    public LinkedList<Model> getModels() {
        return models;
    }

    public Student getStudent() {
        return student;
    }

    public Callback_PublishConferenceBroadcast getPublishConference() {
        return publishConference;
    }

    public Callback_Terminate getTerminate() {
        return terminate;
    }

    public int getCurrModel() {
        return currModel;
    }

    public void incrementcurrModel(){
        this.currModel++;
    }

    public Future<Model> getFuture() {
        return future;
    }

    public void setFuture(Future<Model> future) {
        this.future = future;
    }
    /**
     * Assiph's comments:In here we should create 3 send events that use the message bus (let messagebus be mbs, so we will use
     * mbs.sendEvent()). The 3 events should be TrainModelEvent,TestModelEvent,PublishResultsEvent.
     *
     *
     */
}
