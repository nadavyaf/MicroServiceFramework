package bgu.spl.mics;

import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.example.ServiceCreator;

import javax.swing.plaf.metal.MetalIconFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 *
 *
 */
public class MessageBusImpl implements MessageBus {
 	private Map<MicroService, LinkedBlockingQueue<Message>> Queuemap = new HashMap<>();
	private static MessageBusImpl firstInstance = null;
	public static MessageBusImpl getInstance() {
		if (firstInstance == null) { //if we didn't create an object yet, we continue, else we return the object.
			synchronized (MessageBusImpl.class) {//here we make sure that when we create the object, it will only create it once no matter what. also notice that this synchronized works only if we don't have an object yet(when we have an object, it will return the object).
						if (firstInstance == null) {//from here, only 1 thread at a time will enter the
							firstInstance = new MessageBusImpl();
						}
					}
				}
		return firstInstance;
			}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	/**
	 *
	 * Assiph's thoughts: send this Event (or broadcast in a different function) and in this block you will also use the function
	 * complete with 2 args: 1.this event, 2.function the initializes the right microservice (it will be a callback to this service
	 * so when the service finishes, you get a result, and also it runs on a different thread and the thread of the messagebus can just
	 * continue).
	 * when we get the result, we will create a Future object, use result on it and return it, this should return it to the student in the
	 * right way. ***the student will get it in the main (CRMSRunner) or in it's StudentService, not sure yet.***
	 */
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isMicroServiceRegistered(MicroService m){
		return null;
	}

	public Boolean isMicroServiceEventRegistered(MicroService m,Event e) {
		return null;
	}

	public Boolean isMicroServiceBroadCastRegistered(MicroService m,Broadcast b){
		return null;
	}


}
