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
    private LinkedList<Model> modelList;
    private LinkedBlockingQueue<Message> MessageQueue=null;
    private final Student student;
    private Callback_PublishConferenceBroadcast publishConferenceCallback;
    private Callback_Terminate terminateCallback;
    private Callback_FinishedBroadcast finished;
    private int currModel;
    private Future<String> future;
    public StudentService(String name, Student student, LinkedList<Model> modelList) {
        super(name);
        this.publishConferenceCallback = new Callback_PublishConferenceBroadcast(this);
        this.terminateCallback = new Callback_Terminate(this);
        this.student = student;
        this.modelList = modelList;
        this.finished = new Callback_FinishedBroadcast(this);
        this.currModel = 0;
        this.future = new Future<String>();
    }

    @Override
    protected void initialize() throws InterruptedException {
        MessageBusImpl.getInstance().register(this);
        this.subscribeBroadcast(PublishConferenceBroadcast.class, this.publishConferenceCallback);
        this.subscribeBroadcast(TerminateBroadcast.class, this.terminateCallback);
        this.subscribeBroadcast(FinishedBroadcast.class, this.finished);
        if(!this.modelList.isEmpty()) {
            this.future = this.sendEvent(new TrainModelEvent(this.getModelList().getFirst()));
        }
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

    public LinkedList<Model> getModelList() {
        return modelList;
    }

    public Student getStudent() {
        return student;
    }

    public void incrementCurrModel(){
        this.currModel++;
    }

    public int getCurrModel() {
        return currModel;
    }

    public Future<String> getFuture() {
        return future;
    }

    public void setFuture(Future<String> future) {
        this.future = future;
    }

    /**
     * Assiph's comments:In here we should create 3 send events that use the message bus (let messagebus be mbs, so we will use
     * mbs.sendEvent()). The 2 events should be TrainModelEvent,TestModelEvent,PublishResultsEvent.
     *
     *
     */
}
