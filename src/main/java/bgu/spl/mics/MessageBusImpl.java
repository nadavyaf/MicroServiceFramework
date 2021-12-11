package bgu.spl.mics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 *
 *
 */
public class MessageBusImpl implements MessageBus {/** Assiph's comments: should have another field, for the round robin - maybe should be a counter
 that we do % from to the number of Queues that we have.*/
	private ConcurrentHashMap <Class<? extends Message>, BlockingQueue<MicroService>> messageMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap <MicroService, BlockingQueue<Message>> microMap = new ConcurrentHashMap<>();
	private static class SingeltonHolder{//Java things, this way when we import messagebusimpl, it will not create any instance (since the funcion is private), but when we call the function, it will just call the .instance once.
		private static MessageBusImpl instance = new MessageBusImpl();
	}

	public static MessageBusImpl getInstance() {
		return SingeltonHolder.instance;
			}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {/**Assiph's comments: if the message doesn't exist,
	 add it with a new LinkedBlockingQueue (FIFO) and then just add the element into that queue.*/
		messageMap.putIfAbsent(type,new LinkedBlockingQueue<>());
		messageMap.get(type).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		messageMap.putIfAbsent(type,new LinkedBlockingQueue<>());
		messageMap.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {

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
	public <T> Future<T> sendEvent(Event<T> e) { /** Assiph's Comments: Will be used by a studentService (or student,not sure) for example
	 it will enter the event to the right queue using the Messagebus (with get instance) and then use the get method (blocking).*/
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		microMap.putIfAbsent(m,new LinkedBlockingQueue<>());
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
		return !(microMap.get(m) == null);
	}

	public Boolean isMicroServiceEventRegistered(MicroService m,Event e) {
		return messageMap.get(e).contains(m);
	}

	public Boolean isMicroServiceBroadCastRegistered(MicroService m,Broadcast b){
		return messageMap.get(b).contains(m);
	}
}
