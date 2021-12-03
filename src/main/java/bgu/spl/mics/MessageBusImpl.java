package bgu.spl.mics;

import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.example.ServiceCreator;
import sun.awt.image.ImageWatched;

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
 	private Map<String, LinkedBlockingQueue<Event>> Queuemap = new HashMap<>();
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

	

}
